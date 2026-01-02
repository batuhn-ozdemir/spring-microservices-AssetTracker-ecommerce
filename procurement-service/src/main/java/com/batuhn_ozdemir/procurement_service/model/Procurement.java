package com.batuhn_ozdemir.procurement_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "t_procurement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Procurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String procurementNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ProcurementLineItems> procurementLineItemsList;
}