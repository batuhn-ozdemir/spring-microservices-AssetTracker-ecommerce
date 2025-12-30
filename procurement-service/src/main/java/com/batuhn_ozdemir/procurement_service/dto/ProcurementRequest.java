package com.batuhn_ozdemir.procurement_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcurementRequest {
    private List<ProcurementLineItemsDto> procurementLineItemsDtoList;
}