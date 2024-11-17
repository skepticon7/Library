package com.youssef.library.cities.Web;

import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.model.checkout.Session;
import com.youssef.library.cities.DTOs.Session.SessionDTO;
import com.youssef.library.cities.DTOs.Session.SessionDtoMapper;
import com.youssef.library.cities.Enums.SessionType;
import com.youssef.library.cities.Service.Session.SessionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.HashMap;

@RestController
@AllArgsConstructor
public class StripeWebhookController {

    private static final Logger log = LoggerFactory.getLogger(StripeWebhookController.class);
//    private SessionService sessionService;
//    private final SimpMessagingTemplate messagingTemplate;

    private static final String WebHook_Secret = "whsec_IJBhIrA3R5MPCzuSAgbiWCnMavyUPmuS";
    @PostMapping("/webhook")
    public ResponseEntity<String> confirmedPayment(@RequestBody String payload , @RequestHeader("Stripe-Signature") String sigHeader){
            try{
                String endpoint_key = WebHook_Secret;
                Event event = Webhook.constructEvent(payload,sigHeader,endpoint_key);
                String outcome;
                switch (event.getType()){
                    case "payment_intent.succeeded":
                        PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();

                        // Retrieve metadata from the Payment Intent
                        String customerId = paymentIntent.getMetadata().get("customer_id");
                        String sessionTypeStr = paymentIntent.getMetadata().get("sessionType");
                        String bookId = paymentIntent.getMetadata().get("book_id");
                        long amount = paymentIntent.getAmount();
                        System.out.println("customer Id : " + customerId);
                        System.out.println("session type : " + sessionTypeStr);
                        System.out.println("book Id : " + bookId);
                        System.out.println("amount : " + amount);
                        SessionDTO sessionDTO = new SessionDTO();
                        sessionDTO.setPrice(amount / 100); // Amount in dollars
                        SessionType sessionType = sessionTypeStr.equals("halfPrice") ? SessionType.half : sessionTypeStr.equals("onePrice") ? SessionType.one : SessionType.oneHalf;
                        sessionDTO.setSessionType(sessionType);
                        sessionDTO.setSessionType(sessionType);
                        sessionDTO.setOriginalTime(sessionDTO.getSessionType().equals(SessionType.half) ?
                                Duration.ofMinutes(30L) :
                                sessionDTO.getSessionType().equals(SessionType.one) ?
                                        Duration.ofHours(1L) :
                                        Duration.ofHours(1).plusMinutes(30L));
                        sessionDTO.setRemainingTime(sessionDTO.getOriginalTime());
//                        messagingTemplate.convertAndSend("/topic/paymentStatus","paymentSuccessful");
//                        sessionService.saveSession(SessionDtoMapper.toEntity(sessionDTO),bookId,customerId);
                        return ResponseEntity.ok("Success");
                    default:
                        throw new IllegalStateException("Unexpected value: " + event.getType());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
}
