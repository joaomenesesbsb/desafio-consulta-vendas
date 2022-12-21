package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

   @Query("SELECT new com.devsuperior.dsmeta.dto.Sale(obj.id, obj.date, obj.amount, obj.seller.name) " +
            "FROM Sale obj " +
            "WHERE UPPER(obj.name) LIKE UPPER(CONCAT('%',:name, '%')) AND obj.date BETWEEN :dateInicio AND :dateFim")
    Page<Sale> searchByName(String name, LocalDate dateInicio, LocalDate dateFim, Pageable pageable);
}
