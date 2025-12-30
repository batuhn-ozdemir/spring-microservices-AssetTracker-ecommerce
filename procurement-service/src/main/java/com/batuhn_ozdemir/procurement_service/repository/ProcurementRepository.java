package com.batuhn_ozdemir.procurement_service.repository;

import com.batuhn_ozdemir.procurement_service.model.Procurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcurementRepository extends JpaRepository<Procurement, Long> {
}