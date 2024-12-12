package com.amigoscode.spring_data_jpa;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amigoscode.spring_data_jpa.model.Book;
import com.amigoscode.spring_data_jpa.model.Student;
import com.amigoscode.spring_data_jpa.repository.StudentRepository;

import jakarta.transaction.Transactional;


@Service
public class StudentService 
{
    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public List<Book> findBooksByIdServ(Long id)
    {
        Student st = studentRepository.findById(id).get();
        List<Book> books = st.getBooks();
        books.forEach(b-> {System.out.println(b.getBookName());});
        return books;
    }
    
}
