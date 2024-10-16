package com.youssef.library.cities.Service.Visitor;

import com.youssef.library.cities.Entities.Visitor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VisitorService {
    Visitor saveVisitor(Visitor visitor);
    List<Visitor> getAllVisitors();
}
