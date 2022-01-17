package codegym.service;

import codegym.model.Student;

import java.util.List;
import java.util.Optional;

public interface IStudentService {

    public List<Student> fillAll();
    public void save(Student student);
    public void delete(long id);
    public Student finById(long id);







}
