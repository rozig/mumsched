package mum.swe.democrud.service;

import java.util.List;

import mum.swe.democrud.model.Student;

public interface StudentService {
	 List<Student> findAll();
	 Student save(Student student);
	 Student findOne(Long id);
	 void delete(Long id);
}
