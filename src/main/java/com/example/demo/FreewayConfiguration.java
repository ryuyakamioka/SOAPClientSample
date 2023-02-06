package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;

import java.io.IOException;
import java.net.HttpURLConnection;

@Configuration
public class FreewayConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.example.demo.api.client.FreewayAPI.dto");
        return marshaller;
    }

    @Bean
    public FreewayClient freewayClient(Jaxb2Marshaller marshaller) {
        FreewayClient client = new FreewayClient();
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        client.getWebServiceTemplate().setMessageSender(new HttpUrlConnectionMessageSender() {
            @Override
            protected void prepareConnection(HttpURLConnection connection) throws IOException {
                connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                super.prepareConnection(connection);
            }
        });
        return client;
    }
}
