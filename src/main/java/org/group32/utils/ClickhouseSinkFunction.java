package org.group32.utils;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.group32.pojo.*;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;

public class ClickhouseSinkFunction extends RichSinkFunction<POJO> {
    private ClickHouseConnection conn = null;

    @Override
    public void open(Configuration parameters) {
        try {
            super.open(parameters);
            String url = "jdbc:clickhouse://localhost:8123/dm";
            ClickHouseProperties properties = new ClickHouseProperties();
            properties.setUser("");
            properties.setPassword("");
            properties.setSessionId("default-session-id");

            conn = new ClickHouseDataSource(url, properties).getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        super.close();
        if (conn != null) conn.close();
    }

    @Override
    public void invoke(POJO value, Context context) throws Exception {
        try {
            PreparedStatement stmt = conn.prepareStatement(SqlStatement.getSQl(value.getClass()));
            Field[] fields = value.getClass().getDeclaredFields();
            int index = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType() == String.class) {
                    stmt.setString(index, (String) field.get(value));
                } else if (field.getType() == double.class) {
                    stmt.setDouble(index, (double) field.get(value));
                } else if (field.getType() == int.class) {
                    stmt.setInt(index, (int) field.get(value));
                } else if (field.getType() == long.class) {
                    stmt.setLong(index, (long) field.get(value));
                } else throw new RuntimeException("no such type value");
                index++;
            }
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
