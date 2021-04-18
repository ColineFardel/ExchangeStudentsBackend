package com.example.ExchangeStudentsBackend.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EventRepository extends CrudRepository<Event, Long> {
	List<Event> findByDate(@Param("date") String date);
}
