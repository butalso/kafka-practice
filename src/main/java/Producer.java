import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Scanner;

public class Producer {
    private static final String brokerList = "localhost:9092";
    private static final String topic = "wenyue-demo";
    private static Logger logger = LoggerFactory.getLogger(Producer.class);

    private static Properties initConfig() {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return props;
    }

    public static void main(String[] args) {
        Properties props = initConfig();
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            System.out.println(input);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, input);
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    logger.error("send failed, err: %+v", exception);
                } else {
                    logger.info("send success: %+v", metadata);
                }
                System.out.println(metadata);
                System.out.println(exception);
            });
        }
        System.out.println("finish");
    }
}
