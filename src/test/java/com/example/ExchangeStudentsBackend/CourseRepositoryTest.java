package com.example.ExchangeStudentsBackend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.ExchangeStudentsBackend.model.Course;
import com.example.ExchangeStudentsBackend.model.CourseRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CourseRepositoryTest {
	@Autowired
	CourseRepository courserepo;

	// Add a new Course
	@Test
	public void createCourse() {
		Course course = new Course("Name", "Teacher", "Uni");
		courserepo.save(course);
		assertThat(course.getId()).isNotNull();
	}

	// Delete Course
	@Test
	public void deleteCourse() {
		List<Course> courses = (List<Course>) courserepo.findAll();
		assertThat(courses).hasSizeGreaterThan(0);
		int temp = courses.size();

		courserepo.deleteById(courses.get(0).getId());
		assertThat(courserepo.findAll()).hasSizeLessThan(temp);
	}

	// Modify Course
	@Test
	public void modifyCourse() {
		Course course = new Course("Name", "Teacher", "University");
		courserepo.save(course);
		assertThat(course.getId()).isNotNull();

		Course modifiedCourse = courserepo.findById(course.getId()).get();
		modifiedCourse.setTeacher("New teacher");
		courserepo.save(modifiedCourse);

		assertThat(courserepo.findById(course.getId()).get().getTeacher()).contains("New teacher");
	}
}
