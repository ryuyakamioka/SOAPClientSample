package com.example.demo;

import com.example.demo.api.client.FreewayAPI.dto.ReplyMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SOAP 通信クライアントのサンプル
 * 参考：
 * https://spring.pleiades.io/guides/gs/consuming-web-service/
 */
@SpringBootApplication
@RestController
public class DemoApplication {

	@RequestMapping("/hello")
	public String index() {
		return "HELLO";
	}

	@Bean
	@RequestMapping("/void")
	public String freewayVoid(FreewayClient client) {
		String replyMessage = client.freewayVoid("");
		return replyMessage;
	}

	@Bean
	@RequestMapping("/refund")
	public String freewayRefund(FreewayClient client) {
		String replyMessage = client.freewayRefund("");
		return replyMessage;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
