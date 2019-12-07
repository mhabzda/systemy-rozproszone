import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static utils.HospitalUtils.*;

public class Administrator {
    public static void main(String[] argv) throws Exception {
        System.out.println("Administrator");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "*");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Log: " + message);
            }
        };

        channel.basicConsume(queueName, true, consumer);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Enter message:");
            String message = reader.readLine();
            channel.basicPublish(ADMIN_EXCHANGE_NAME, "*", null, message.getBytes("UTF-8"));
        }
    }
}
