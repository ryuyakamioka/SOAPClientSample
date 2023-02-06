package com.example.demo;

import com.example.demo.api.client.FreewayAPI.dto.CCCreditService;
import com.example.demo.api.client.FreewayAPI.dto.ObjectFactory;
import com.example.demo.api.client.FreewayAPI.dto.ReplyMessage;
import com.example.demo.api.client.FreewayAPI.dto.RequestMessage;
import com.example.demo.api.client.FreewayAPI.dto.Submit;
import com.example.demo.api.client.FreewayAPI.dto.SubmitResponse;
import com.example.demo.api.client.FreewayAPI.dto.VoidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class FreewayClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(FreewayClient.class);

    private static final String URI = "https://cs.uat.freedompay.com/Freeway/Service.asmx";
    private static final String SOAP_ACTION_URI = "http://freeway.freedompay.com/Submit";
    private static final String STORE_ID = "";
    private static final String TERMINAL_ID = "";


    public String freewayVoid(String orderRequestId) {
        ObjectFactory factory = new ObjectFactory();

        VoidService service = factory.createVoidService();
        service.setRun("true");

        RequestMessage requestMessage = factory.createRequestMessage();
        requestMessage.setVoidService(service);

        requestMessage.setStoreId(STORE_ID);
        requestMessage.setTerminalId(TERMINAL_ID);
        requestMessage.setOrderRequestID(orderRequestId);

        Submit submit = factory.createSubmit();
        submit.setRequest(requestMessage);

        SubmitResponse reply = sendMessage(submit);

        return reply.getSubmitResult().getDecision();
    }

    public String freewayRefund(String orderRequestId) {
        ObjectFactory factory = new ObjectFactory();

        CCCreditService service = factory.createCCCreditService();
        service.setRun("true");

        RequestMessage requestMessage = factory.createRequestMessage();
        requestMessage.setCcCreditService(service);

        requestMessage.setStoreId(STORE_ID);
        requestMessage.setTerminalId(TERMINAL_ID);
        requestMessage.setOrderRequestID(orderRequestId);

        Submit submit = factory.createSubmit();
        submit.setRequest(requestMessage);

        SubmitResponse reply = sendMessage(submit);

        return reply.getSubmitResult().getDecision();
    }

    public SubmitResponse sendMessage(Submit submit) {

        WebServiceTemplate webServiceTemplate = getWebServiceTemplate();
        Object response = null;

        try {
            response = webServiceTemplate.marshalSendAndReceive(URI, submit,
                    new SoapActionCallback(SOAP_ACTION_URI));
        } catch ( SoapFaultClientException e ) {
            // Faultエラーが返ってきた場合の処理
            System.out.println(e.getSoapFault().getFaultDetail());

        } catch ( WebServiceException e ) {
            // その他のSOAP通信エラーが発生した場合の処理
            System.out.println(e.getMessage());
        }

        SubmitResponse voidReply = (SubmitResponse) response;

        return voidReply;
    }
}
