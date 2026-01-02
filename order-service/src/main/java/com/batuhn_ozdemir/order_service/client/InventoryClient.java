package com.batuhn_ozdemir.order_service.client;

import com.batuhn_ozdemir.order_service.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @PostMapping("/api/inventory/check-stock")
    List<InventoryResponse> checkStock(@RequestBody List<String> skuCodes);

    @PutMapping("/api/inventory/reduce")
    void reduceStock(@RequestParam String skuCode, @RequestParam Integer quantity);
}
