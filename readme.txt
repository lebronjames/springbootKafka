Springboot与kafka集成
一、pom.xml增加kafka依赖spring-kafka集成包
二、application.yml
spring:
  kafka:
  # 指定kafka 代理地址，可以多个
    bootstrap-servers: 10.5.2.242:9098
    consumer:
    # 指定默认消费者group id
      group-id: myGroup
    # 指定默认topic id
    template:
      default-topic: my-replicated-topic
    # 指定listener 容器中的线程数，用于提高并发量
    listener:
      concurrency: 3
    # 每次批量发送消息的数量
    producer:
      batch-size: 1024
三、configuration 启用kafka
@Configuration
@EnableKafka
public class KafkaConfiguration {
}
四、消息生产者Producer
@Component
public class ProducerService {

	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	public void send() {
		kafkaTemplate.send("my-replicated-topic1", "hello topic1-----");
		kafkaTemplate.send("my-replicated-topic2", "hello topic2-----");
		
		kafkaTemplate.metrics();
		
		kafkaTemplate.execute(new KafkaOperations.ProducerCallback<String, String, Object>() {
            @Override
            public Object doInKafka(Producer<String, String> producer) {
                //这里可以编写kafka原生的api操作
                return null;
            }
        });

        //消息发送的监听器，用于回调返回信息
        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
            @Override
            public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {

            }

            @Override
            public void onError(String topic, Integer partition, String key, String value, Exception exception) {

            }

            @Override
            public boolean isInterestedInSuccess() {
                return false;
            }
        });
    }
}
五、消息消费者Consumer
@Component
public class ConsumerService {

	@KafkaListener(topics= {"my-replicated-topic1","my-replicated-topic2"})
	public void processMessage(String content) {
		System.out.println("-------------:"+content);
	}
}