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

            // Fiyat veya İsim değişmiş olabilir, onları da güncelle (Opsiyonel ama önerilir)
            inventory.setProductName(inventoryRequest.getProductName());
            inventory.setSalePrice(inventoryRequest.getSalePrice());

            inventoryRepository.save(inventory);
        } else {
            // SENARYO B: Ürün yok -> Sıfırdan yeni kayıt oluştur
            Inventory inventory = new Inventory();
            inventory.setSkuCode(inventoryRequest.getSkuCode());
            inventory.setQuantity(inventoryRequest.getQuantity());
            inventory.setProductName(inventoryRequest.getProductName()); // Yeni Alan
            inventory.setSalePrice(inventoryRequest.getSalePrice());     // Yeni Alan

            inventoryRepository.save(inventory);
        }
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> checkStock(List<String> skuCodes) {

        // Veritabanından toplu çekim
        List<Inventory> inventoryList = inventoryRepository.findBySkuCodeIn(skuCodes);

        // İstek yapılan her bir SKU kodu için response oluştur
        return skuCodes.stream().map(sku -> {

            // Memory içindeki listeden ilgili ürünü bul
            Inventory inventory = inventoryList.stream()
                    .filter(value -> value.getSkuCode().equals(sku))
                    .findFirst()
                    .orElse(null);

            // Ürün varsa ve adedi 0'dan büyükse stokta var say
            boolean inStock = inventory != null && inventory.getQuantity() > 0;

            return InventoryResponse.builder()
                    .skuCode(sku)
                    .isInStock(inStock)
                    // Eğer ürün bulunamazsa null dönmemek için kontrol ekliyoruz
                    .productName(inventory != null ? inventory.getProductName() : null)
                    .salePrice(inventory != null ? inventory.getSalePrice() : null)
                    .quantity(inventory != null ? inventory.getQuantity() : 0)
                    .build();

        }).toList();
    }
}