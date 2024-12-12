package com.amigoscode.spring_boot_tutorial.student;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController 
{   
    @Autowired
    private final StudentService studentService;
    public StudentController(StudentService studentService) 
    {
        this.studentService = studentService;
    }

    
    @GetMapping
	public List<Student> getStudents()
	{
        return studentService.getStudents();
    } 


    @PostMapping
    public void addNewStudent(@RequestBody Student student) 
    {
        studentService.addNewStudent(student);
    }


    @DeleteMapping(path = "{studentID}")
    public void deleteStudent(@PathVariable("studentID") Long studentID)
    {
        studentService.deleteStudent(studentID);
    }


    @PutMapping(path = "{studentID}")
    public void updateStudent(
        @PathVariable("studentID") Long studentID,
        @RequestParam(required = false) String name, 
        @RequestParam(required = false) String email            
                             )
    {
        studentService.updateStudent(studentID,name,email);
    }
}