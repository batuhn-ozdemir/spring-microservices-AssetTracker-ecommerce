package com.batuhn_ozdemir.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InventoryRequest {

    @NotBlank(message = "SKU Code boş olamaz")
    private String skuCode;
    @Min(value = 0, message = "Stok adedi 0'dan küçük olamaz")
    private int quantity;

    public InventoryRequest(String skuCode, int quantity) {
        this.skuCode = skuCode;
        this.quantity = quantity;
    }
}
