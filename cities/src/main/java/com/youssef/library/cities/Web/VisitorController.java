package com.youssef.library.cities.Web;

import com.youssef.library.cities.DTOs.Visitor.VisitorDTO;
import com.youssef.library.cities.DTOs.Visitor.VisitorDtoMapper;
import com.youssef.library.cities.Entities.Visitor;
import com.youssef.library.cities.Service.Visitor.VisitorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("visitor")
@AllArgsConstructor
public class VisitorController {

    private VisitorService visitorService;
    @PostMapping("/addVisitor")
    public ResponseEntity<VisitorDTO> addVisitor(@RequestBody @Valid VisitorDTO visitor) {
        Visitor newVisitor = visitorService.saveVisitor(VisitorDtoMapper.toEntity(visitor));
        return new ResponseEntity<>(VisitorDtoMapper.toDto(newVisitor), HttpStatus.CREATED);
    }

    @GetMapping("/getAllVisitors")
    public ResponseEntity<List<VisitorDTO>> getAllVisitors() {
        List<Visitor> allVisitors = visitorService.getAllVisitors();
        List<VisitorDTO> mappedVisitors = allVisitors
                .stream()
                .map(VisitorDtoMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(mappedVisitors, HttpStatus.OK);
    }
}
