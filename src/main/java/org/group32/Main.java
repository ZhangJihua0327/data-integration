package org.group32;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.group32.pojo.*;
import org.group32.utils.ClickhouseSinkFunction;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static Map<String, Class<? extends POJO>> pojoMap = new HashMap<String, Class<? extends POJO>>();

    static {
        pojoMap.put("contract", DmVTrContractMx.class);
        pojoMap.put("djk", DmVTrDjkMx.class);
        pojoMap.put("dsf", DmVTrDsfMx.class);
        pojoMap.put("duebill", DmVTrDuebillMx.class);
        pojoMap.put("etc", DmVTrEtcMx.class);
        pojoMap.put("grwy", DmVTrGrwyMx.class);
        pojoMap.put("gzdf", DmVTrGzdfMx.class);
        pojoMap.put("huanb", DmVTrHuanbMx.class);
        pojoMap.put("huanx", DmVTrHuanxMx.class);
        pojoMap.put("sa", DmVTrSaMx.class);
        pojoMap.put("sbyb", DmVTrSbybMx.class);
        pojoMap.put("sdrq", DmVTrSdrqMx.class);
        pojoMap.put("sjyh", DmVTrSjyhMx.class);
        pojoMap.put("shop", VTrShopMx.class);
    }

    static final List<String> eventTypes = new ArrayList<String>(pojoMap.keySet());

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(5000);
        // env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties kafkaProps = new Properties();
        kafkaProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        kafkaProps.put(ConsumerConfig.GROUP_ID_CONFIG, "ab1sdfasffAF2b");
        kafkaProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        String topic = "test";

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<String>(topic, new SimpleStringSchema(),
                kafkaProps);

        DataStreamSource<String> source = env.addSource(consumer);
        List<DataStream<String>> dataStreams = spiltByEventType(source);
        for (int i = 0; i < dataStreams.size(); i++) {
            Class<? extends POJO> tclass = pojoMap.get(eventTypes.get(i));
            ClickhouseSinkFunction util = ClickhouseSinkFunction.makeUtil(tclass);
            SingleOutputStreamOperator<? extends POJO> operator = createFlinkMapOperator(dataStreams.get(i), tclass);
            operator.addSink(util);
        }
        try {
            env.execute("Flink Streaming Java API Skeleton");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<DataStream<String>> spiltByEventType(DataStream<String> dataSource) {
        return eventTypes.stream().map(eventType -> dataSource.filter((FilterFunction<String>) s -> {
            HashMap<String, String> json = JSON.parseObject(s, HashMap.class);
            return (json != null) && json.get("eventType").equalsIgnoreCase(eventType);
        })).collect(Collectors.toList());
    }

    public static <T extends POJO> SingleOutputStreamOperator<T> createFlinkMapOperator(
            DataStream<String> subDataStream, final Class<T> tClass) {
        MapFunction<String, T> mp = s -> {
            HashMap<String, JSONObject> json = JSON.parseObject(s, HashMap.class);
            String str = json.get("eventBody").toJSONString();
            T pojo = JSON.parseObject(str, tClass);
            System.out.println(str);
            return pojo;
        };
        return subDataStream.map(mp);
    }
}