package com.batuhn_ozdemir.inventory_service.service;

import com.batuhn_ozdemir.inventory_service.dto.InventoryRequest;
import com.batuhn_ozdemir.inventory_service.dto.InventoryResponse;
import com.batuhn_ozdemir.inventory_service.model.Inventory;
import com.batuhn_ozdemir.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional
    public void createInventory(InventoryRequest inventoryRequest) {
        Optional<Inventory> existingInventory = inventoryRepository.findBySkuCode(inventoryRequest.getSkuCode());

        if (existingInventory.isPresent()) {
            Inventory inventory = existingInventory.get();
            inventory.setQuantity(inventory.getQuantity() + inventoryRequest.getQuantity());

            inventory.setProductName(inventoryRequest.getProductName());
            inventory.setSalePrice(inventoryRequest.getSalePrice());

            inventoryRepository.save(inventory);
        } else {
            Inventory inventory = new Inventory();
            inventory.setSkuCode(inventoryRequest.getSkuCode());
            inventory.setQuantity(inventoryRequest.getQuantity());
            inventory.setProductName(inventoryRequest.getProductName());
            inventory.setSalePrice(inventoryRequest.getSalePrice());

            inventoryRepository.save(inventory);
        }
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> checkStock(List<String> skuCodes) {

        List<Inventory> inventoryList = inventoryRepository.findBySkuCodeIn(skuCodes);

        return skuCodes.stream().map(sku -> {

            Inventory inventory = inventoryList.stream()
                    .filter(value -> value.getSkuCode().equals(sku))
                    .findFirst()
                    .orElse(null);

            boolean inStockVar = inventory != null && inventory.getQuantity() > 0;

            return InventoryResponse.builder()
                    .skuCode(sku)
                    .inStock(inStockVar)
                    .productName(inventory != null ? inventory.getProductName() : null)
                    .salePrice(inventory != null ? inventory.getSalePrice() : null)
                    .quantity(inventory != null ? inventory.getQuantity() : 0)
                    .build();

        }).toList();
    }

    @Transactional
    public void decreaseStock(String skuCode, Integer quantity) {
        Inventory inventory = inventoryRepository.findBySkuCode(skuCode)
                .orElseThrow(() -> new RuntimeException("Asset not found: " + skuCode));

        if (inventory.getQuantity() < quantity) {
            throw new RuntimeException("Out of stock! Asset: " + skuCode);
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
    }
}