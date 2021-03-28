package com.example.ExchangeStudentsBackend.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ChatRepository extends CrudRepository<Chat, Long> {
	List<Chat> findByTopic(@Param("topic") Topic topic);

	List<Chat> findByTopicAndDate(@Param("topic") Topic topic, @Param("date") String date);

	List<Chat> findByCourse(@Param("course") Course course);

	List<Chat> findByCourseAndDate(@Param("course") Course course, @Param("date") String date);
}
