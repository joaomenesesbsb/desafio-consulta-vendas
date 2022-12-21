package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> searchByName(String name, String inicio, String fim, Pageable pageable) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateInicio = LocalDate.parse(inicio,formatter);
		LocalDate dateFim = LocalDate.parse(fim,formatter);
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		if (dateFim == null){
			dateFim = today;
		}
		if (dateInicio == null){
			dateInicio = today.minusYears(1);
		}
		Page<Sale> result = repository.searchByName(name, dateInicio, dateFim, pageable);
		return result.map(x -> new SaleMinDTO(x));
	}

}
