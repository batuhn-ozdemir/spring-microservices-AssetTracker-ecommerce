package com.batuhn_ozdemir.inventory_service.service;

import com.batuhn_ozdemir.inventory_service.dto.InventoryRequest;
import com.batuhn_ozdemir.inventory_service.dto.InventoryResponse;
import com.batuhn_ozdemir.inventory_service.model.Inventory;
import com.batuhn_ozdemir.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional
    public void createInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = new Inventory();

        inventory.setSkuCode(inventoryRequest.getSkuCode());
        inventory.setQuantity(inventoryRequest.getQuantity());

        inventoryRepository.save(inventory);
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> checkStock(List<String> skuCodes) {

        List<Inventory> inventoryList = inventoryRepository.findBySkuCodeIn(skuCodes);

        return skuCodes.stream().map(sku -> {

            Inventory inventory = inventoryList.stream()
                    .filter(value -> value.getSkuCode().equals(sku))
                    .findFirst()
                    .orElse(null);

            boolean inStock = inventory != null && inventory.getQuantity() > 0;

            return InventoryResponse.builder()
                    .skuCode(sku)
                    .isInStock(inStock)
                    .build();

        }).toList();
    }
}
