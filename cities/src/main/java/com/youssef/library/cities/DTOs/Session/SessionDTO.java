package com.youssef.library.cities.DTOs.Session;

import com.youssef.library.cities.DTOs.Book.BookDTO;
import com.youssef.library.cities.Entities.Book;
import com.youssef.library.cities.Enums.SessionStatus;
import com.youssef.library.cities.Enums.SessionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDTO {
    private String id;
    private Duration originalTime;
    private Duration remainingTime;
    private SessionType sessionType;
    private SessionStatus sessionStatus;
    private Long price;
    private Book book;
    private Long currentPage;
}
