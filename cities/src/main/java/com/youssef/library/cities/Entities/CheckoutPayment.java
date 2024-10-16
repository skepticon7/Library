package com.youssef.library.cities.Entities;

import com.youssef.library.cities.DTOs.Book.BookDTO;
import com.youssef.library.cities.Enums.SessionType;
import lombok.Data;

@Data
public class CheckoutPayment {
    private String successUrl;
    private String cancelUrl;
    private long amount;
    private String customer;
    private String sessionType;
    private BookDTO book;
}
