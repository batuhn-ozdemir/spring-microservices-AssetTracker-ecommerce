package com.batuhn_ozdemir.procurement_service.controller;

import com.batuhn_ozdemir.procurement_service.dto.ProcurementRequest;
import com.batuhn_ozdemir.procurement_service.service.ProcurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/procurement")
@RequiredArgsConstructor
public class ProcurementController {
    private final ProcurementService procurementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProcurement(@RequestBody ProcurementRequest procurementRequest) {
        procurementService.createProcurement(procurementRequest);
    }
}
