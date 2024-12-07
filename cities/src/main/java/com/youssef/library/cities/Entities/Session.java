package com.youssef.library.cities.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.youssef.library.cities.Enums.SessionStatus;
import com.youssef.library.cities.Enums.SessionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
@Table(name = "SESSION")
public class Session {
    @Id
    private String id;
    private Duration originalTime;
    private Duration remainingTime;
    @Enumerated(EnumType.STRING)
    private SessionType sessionType;
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;
    private Long price;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;
    private Long currentPage = 0L;
}
