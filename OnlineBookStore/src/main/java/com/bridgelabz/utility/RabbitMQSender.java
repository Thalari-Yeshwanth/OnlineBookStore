package com.bridgelabz.utility;
import com.bridgelabz.response.EmailObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("rmq.rube.exchange")
	private String exchange;

	@Value("rube.key")
	private String routingkey;

	public boolean send(EmailObject message) {
		rabbitTemplate.convertAndSend(exchange, routingkey, message);
		return true;
	}
}
