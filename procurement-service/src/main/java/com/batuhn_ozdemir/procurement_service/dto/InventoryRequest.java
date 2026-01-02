package com.batuhn_ozdemir.procurement_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryRequest {
    private String skuCode;
    private Integer quantity;
    private String productName;
    private BigDecimal salePrice;
}
