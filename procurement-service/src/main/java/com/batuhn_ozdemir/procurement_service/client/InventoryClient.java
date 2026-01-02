package com.batuhn_ozdemir.procurement_service.client;

import com.batuhn_ozdemir.procurement_service.dto.InventoryRequest;
import com.batuhn_ozdemir.procurement_service.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @PostMapping("/api/inventory/check-stock")
    List<InventoryResponse> checkStock(@RequestBody List<String> skuCodes);

    @PostMapping("/api/inventory")
    InventoryResponse createInventory(@RequestBody InventoryRequest inventoryRequest);
}