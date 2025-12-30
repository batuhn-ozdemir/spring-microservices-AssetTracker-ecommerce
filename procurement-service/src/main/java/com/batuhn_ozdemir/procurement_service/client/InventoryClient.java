package com.batuhn_ozdemir.procurement_service.client;

import com.batuhn_ozdemir.procurement_service.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

// name: Eureka'da kayıtlı olan Inventory Service ismi (Birebir aynı olmalı)
@FeignClient(name = "inventory-service")
public interface InventoryClient {

    // InventoryController'daki metodun imzasının aynısı (Path ve Parametreler)
    @PostMapping("/api/inventory/check-stock")
    List<InventoryResponse> checkStock(@RequestBody List<String> skuCodes);
}