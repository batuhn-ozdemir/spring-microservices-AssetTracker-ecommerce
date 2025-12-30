package com.batuhn_ozdemir.inventory_service.controller;

import com.batuhn_ozdemir.inventory_service.dto.InventoryRequest;
import com.batuhn_ozdemir.inventory_service.dto.InventoryResponse;
import com.batuhn_ozdemir.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createInventory(@RequestBody InventoryRequest inventoryRequest) {
        inventoryService.createInventory(inventoryRequest);
    }

    @PostMapping("/check-stock")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> checkStock(@RequestBody List<String> skuCodes) {
        return inventoryService.checkStock(skuCodes);
    }
}
