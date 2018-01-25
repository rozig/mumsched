package mum.swe.democrud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mum.swe.democrud.model.Student;
import mum.swe.democrud.repository.StudentRepository;
import mum.swe.democrud.service.StudentService;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;
	
	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	@Override
	public Student save(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public Student findOne(Long id) {
		return studentRepository.findOne(id);
	}

	@Override
	public void delete(Long id) {
		studentRepository.delete(id);
	}

}
