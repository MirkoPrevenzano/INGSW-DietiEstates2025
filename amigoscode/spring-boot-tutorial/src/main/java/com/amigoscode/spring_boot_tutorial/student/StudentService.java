package com.amigoscode.spring_boot_tutorial.student;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
public class StudentService 
{
    @Autowired
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository)
    {
        this.studentRepository = studentRepository;
    }


    public List<Student> getStudents()
    {
        return studentRepository.findAll();
    }


    public void addNewStudent(Student student)
    {
        Optional<Student> studentToAdd = studentRepository.findStudentByEmail(student.getEmail());
        
        if(studentToAdd.isPresent())
            throw new IllegalStateException("email taken");

        studentRepository.save(student);
    }


    public void deleteStudent(Long studentID) 
    {
        Optional<Student> studentToDelete = studentRepository.findById(studentID);

        if(studentToDelete.isPresent())
            studentRepository.delete(studentToDelete.get());
        else
            throw new IllegalStateException("student does not exists");

    }


    @Transactional
    public void updateStudent(Long studentID, String name, String email) 
    {
        Optional<Student> studentToUpdate = studentRepository.findById(studentID);

        if(studentToUpdate.isEmpty())
            throw new IllegalStateException("student does not exists");

        if(name != null && name.length() > 0 && !Objects.equals(name, studentToUpdate.get().getName()))
            studentToUpdate.get().setName(name);

        if(email != null && email.length() > 0 && !Objects.equals(email, studentToUpdate.get().getEmail()))
        {
            Optional<Student> studentByEmail = studentRepository.findStudentByEmail(email);

            if(studentByEmail.isPresent())
                throw new IllegalStateException("email taken");
            
            studentToUpdate.get().setEmail(email);    
        }
    }
}
