package com.youssef.library.cities.Web;

import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.stripe.model.checkout.Session;
import com.youssef.library.cities.DTOs.Session.SessionDTO;
import com.youssef.library.cities.DTOs.Session.SessionDtoMapper;
import com.youssef.library.cities.Enums.SessionType;
import com.youssef.library.cities.Service.Session.SessionService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private SessionService sessionService;

    private static final String WebHook_Secret = "whsec_wQonuWYfVqalaYHbkSEDjLVGMgBuEBr8";
    @PostMapping("/webhook")
    public com.youssef.library.cities.Entities.Session confirmedPayment(@RequestBody String payload , @RequestHeader("Stripe-Signature") String sigHeader){
            HashMap<String,String> response = new HashMap<>();
            try{
                String endpoint_key = WebHook_Secret;
                Event event = Webhook.constructEvent(payload,sigHeader,endpoint_key);
                switch (event.getType()){
                    case "checkout.session.completed":
                        Session session = (Session) event.getData().getObject();
                        String visitorId = session.getMetadata().get("customer_id");
                        String bookId = session.getMetadata().get("book_id");
                        SessionType sessionType = session.getMetadata().get("sessionType").equals("halfPrice") ? SessionType.half : session.getMetadata().get("sessionType").equals("onePrice") ? SessionType.one : SessionType.oneHalf;
                        SessionDTO sessionDTO = new SessionDTO();
                        System.out.println(session.getAmountTotal() / 100);
                        sessionDTO.setPrice(session.getAmountTotal() / 100);
                        sessionDTO.setSessionType(sessionType);
                        sessionDTO.setOriginalTime(sessionDTO.getSessionType().equals(SessionType.half) ?
                                Duration.ofMinutes(30L) :
                                sessionDTO.getSessionType().equals(SessionType.one) ?
                                        Duration.ofHours(1L) :
                                        Duration.ofHours(1).plusMinutes(30L));
                        sessionDTO.setRemainingTime(sessionDTO.getOriginalTime());
                        return sessionService.saveSession(SessionDtoMapper.toEntity(sessionDTO),bookId,visitorId);
                    default:
                        throw new IllegalStateException("Unexpected value: " + event.getType());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
}
