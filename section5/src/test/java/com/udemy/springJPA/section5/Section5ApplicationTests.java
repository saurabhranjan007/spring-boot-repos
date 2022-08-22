package com.udemy.springJPA.section5;

import entities.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import repos.StudentRepository;

import java.util.Collections;

import static org.junit.Assert.*;

@SpringBootTest
class Section5ApplicationTests {

//	we mark with @Autowired, so that it automatically gets injected.
	@Autowired
	private StudentRepository repository;

//	testing the Student Repository.
	@Test
	public void testSaveStudent() {
		//		since we have already defined an interface, so we will be able to use the CRUD operations
		//		w/o actually defining it and that is because of Spring JPA

		//		Creating a new instance of Student, (creating and inserting a new entry of student in DB).
		Student student = new Student();

		student.setId(1l);
		student.setName("Saurabh");
		student.setScore(96);

		repository.save(student);
		repository.delete(student);

//		updating this new student instance
//		student.setScore("");
//		student.repository.deleteAllById(1l);

//		we use .get() - because findById() - gives us some optional output that we really don't require.
//		Student savedStudent =  repository.findById(1l).get();


//		asserting the test to check if the "savedStudent" is null?
//		assertNotNull(student);

//		checking for null value
		assertEquals(null, student);
	}
}
