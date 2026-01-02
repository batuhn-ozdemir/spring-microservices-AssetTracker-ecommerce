package com.batuhn_ozdemir.procurement_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "t_procurement_line_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcurementLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}