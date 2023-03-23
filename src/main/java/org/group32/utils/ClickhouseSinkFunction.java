package org.group32.utils;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.group32.pojo.*;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseConnectionImpl;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;

public class ClickhouseSinkFunction<T extends POJO> extends RichSinkFunction<T> {
    private ClickHouseConnection conn = null;

    private ClickhouseSinkFunction() {
    }

    @Override
    public void open(Configuration parameters) {
        try {
            super.open(parameters);
            String url = "jdbc:clickhouse://localhost:8123/dm";
            ClickHouseProperties properties = new ClickHouseProperties();
            properties.setUser("");
            properties.setPassword("");
            properties.setSessionId("default-session-id");

            conn = new ClickHouseConnectionImpl(url, properties);
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
    public void invoke(T value, Context context) throws Exception {
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
            System.out.println(stmt);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ClickhouseSinkFunction makeUtil(Class<? extends POJO> tclass) {
        if (tclass == DmVTrContractMx.class) {
            return new ClickhouseSinkFunction<DmVTrContractMx>();
        } else if (tclass == DmVTrDjkMx.class) {
            return new ClickhouseSinkFunction<DmVTrDjkMx>();
        } else if (tclass == DmVTrDsfMx.class) {
            return new ClickhouseSinkFunction<DmVTrDsfMx>();
        } else if (tclass == DmVTrDuebillMx.class) {
            return new ClickhouseSinkFunction<DmVTrDuebillMx>();
        } else if (tclass == DmVTrEtcMx.class) {
            return new ClickhouseSinkFunction<DmVTrEtcMx>();
        } else if (tclass == DmVTrGrwyMx.class) {
            return new ClickhouseSinkFunction<DmVTrGrwyMx>();
        } else if (tclass == DmVTrGzdfMx.class) {
            return new ClickhouseSinkFunction<DmVTrGzdfMx>();
        } else if (tclass == DmVTrHuanbMx.class) {
            return new ClickhouseSinkFunction<DmVTrHuanbMx>();
        } else if (tclass == DmVTrHuanxMx.class) {
            return new ClickhouseSinkFunction<DmVTrHuanxMx>();
        } else if (tclass == DmVTrSaMx.class) {
            return new ClickhouseSinkFunction<DmVTrSaMx>();
        } else if (tclass == DmVTrSbybMx.class) {
            return new ClickhouseSinkFunction<DmVTrSbybMx>();
        } else if (tclass == DmVTrSdrqMx.class) {
            return new ClickhouseSinkFunction<DmVTrSdrqMx>();
        } else if (tclass == DmVTrSjyhMx.class) {
            return new ClickhouseSinkFunction<DmVTrSjyhMx>();
        } else if (tclass == VTrShopMx.class) {
            return new ClickhouseSinkFunction<VTrShopMx>();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        DmVTrHuanbMx huanbMx = new DmVTrHuanbMx();
        huanbMx.setAcctNo("12");
        ClickhouseSinkFunction<DmVTrHuanbMx> util = makeUtil(huanbMx.getClass());
        util.open(null);
        util.invoke(huanbMx, null);
        util.close();
    }
}
