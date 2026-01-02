package com.batuhn_ozdemir.procurement_service.service;

import com.batuhn_ozdemir.procurement_service.client.InventoryClient;
import com.batuhn_ozdemir.procurement_service.dto.InventoryRequest;
import com.batuhn_ozdemir.procurement_service.dto.ProcurementRequest;
import com.batuhn_ozdemir.procurement_service.model.Procurement;
import com.batuhn_ozdemir.procurement_service.model.ProcurementLineItems;
import com.batuhn_ozdemir.procurement_service.repository.ProcurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcurementService {
    private final ProcurementRepository procurementRepository;
    private final InventoryClient  inventoryClient;

    @Transactional
    public void createProcurement(ProcurementRequest procurementRequest) {
        Procurement procurement = new Procurement();
        procurement.setProcurementNumber(UUID.randomUUID().toString());

        List<ProcurementLineItems> procurementLineItems = procurementRequest.getProcurementLineItemsDtoList()
                .stream()
                .map(dto -> {
                    ProcurementLineItems item = new ProcurementLineItems();
                    item.setSkuCode(dto.getSkuCode());
                    item.setPrice(dto.getPrice());
                    item.setQuantity(dto.getQuantity());
                    return item;
                }).toList();

        procurement.setProcurementLineItemsList(procurementLineItems);

        procurementRepository.save(procurement);

        procurementRequest.getProcurementLineItemsDtoList().forEach(dto -> {

            BigDecimal salePrice = dto.getPrice().multiply(BigDecimal.valueOf(1.2));

            InventoryRequest inventoryRequest = InventoryRequest.builder()
                    .skuCode(dto.getSkuCode())
                    .quantity(dto.getQuantity())
                    .productName(dto.getSkuCode())
                    .salePrice(salePrice)
                    .build();

            inventoryClient.createInventory(inventoryRequest);
        });
    }
}

