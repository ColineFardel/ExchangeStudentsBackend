package com.example.ExchangeStudentsBackend.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FAQRepository extends CrudRepository<FAQ, Long> {
	List<FAQ> findByStatus(@Param("status") String status);
}
