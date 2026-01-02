package com.batuhn_ozdemir.inventory_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
    private String skuCode;
    @JsonProperty("isInStock")
    private Boolean inStock;
    private String productName;
    private BigDecimal salePrice;
    private Integer quantity;
}
