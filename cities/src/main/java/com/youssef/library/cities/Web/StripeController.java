package com.youssef.library.cities.Web;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.youssef.library.cities.Entities.CheckoutPayment;
import com.youssef.library.cities.ExceptionHandlers.ServerErrorException;
import com.youssef.library.cities.ExceptionHandlers.StripeCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("stripe")
@CrossOrigin("*")
public class StripeController {
    private static void init(){
        Stripe.apiKey = "sk_test_51Pb0Lc2KEqYcHYvzDIC7XHMHiwmX9d6xodjdB0H9KLK7TRFQcvlDKXeCxsk5dvGyXppsp661F26NNeDeWaUawqpI005519gtza";
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_LIBRARIAN') or hasAuthority('SCOPE_ROLE_VISITOR')")
    @PostMapping("/payment")
    public ResponseEntity<HashMap<String , String>> getCheckoutSession(@RequestBody CheckoutPayment paymentDetails){
        init();
        HashMap<String, String> response = new HashMap<>();
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(paymentDetails.getSuccessUrl())
                    .setCancelUrl(paymentDetails.getCancelUrl())
                    .putMetadata("customer_id",paymentDetails.getCustomer())
                    .putMetadata("sessionType", String.valueOf(paymentDetails.getSessionType()))
                    .putMetadata("book_id",paymentDetails.getBook().getId())
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("USD")
                                                    .setUnitAmount(paymentDetails.getAmount())
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(paymentDetails.getBook().getTitle())
                                                                    .addImage(paymentDetails.getBook().getCover())
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    ).setPaymentIntentData(
                            SessionCreateParams.PaymentIntentData.builder()
                                    .putMetadata("customer_id", paymentDetails.getCustomer())
                                    .putMetadata("sessionType", String.valueOf(paymentDetails.getSessionType()))
                                    .putMetadata("book_id", paymentDetails.getBook().getId())
                                    .build()
                    )
                    .build();
            Session session = Session.create(params);
            response.put("id",session.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(StripeException e){
              throw new StripeCustomException(e.getMessage());
        }catch(Exception e){
            throw new ServerErrorException("Internal Server Error , Try again later");
        }
    }

}
