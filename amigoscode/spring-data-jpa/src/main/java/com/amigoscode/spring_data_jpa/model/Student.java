package com.amigoscode.spring_data_jpa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import static jakarta.persistence.GenerationType.SEQUENCE;

import java.util.ArrayList;
import java.util.List;


@Entity(name = "Student")
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(name = "student_email_unique", columnNames = "email")
        }
)
@Data
@NoArgsConstructor
public class Student 
{
        @Id
        @SequenceGenerator(
                name = "student_sequence",
                sequenceName = "student_sequence",
                allocationSize = 1
        )
        @GeneratedValue(
                strategy = SEQUENCE,
                generator = "student_sequence"
        )
        @Column(
                name = "id",
                updatable = false
        )
        private Long id;


        @Column(
                name = "first_name",
                nullable = false,
                columnDefinition = "TEXT"
        )
        private String firstName;


        @Column(
                name = "last_name",
                nullable = false,
                columnDefinition = "TEXT"
        )
        private String lastName;


        @Column(
                name = "email",
                nullable = false,
                columnDefinition = "TEXT"
        )
        private String email;


        @Column(
                name = "age",
                nullable = false
        )
        private Integer age;

        
        @OneToOne(mappedBy = "student", orphanRemoval = true, 
                  cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
        private StudentIdCard studentIdCard;


        @OneToMany(mappedBy = "student", orphanRemoval = true,
                   cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
        private List<Book> books = new ArrayList<>();
        


        public Student(String firstName, String lastName, String email, Integer age) 
        {
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.age = age;
        }


        public void addBook(Book book) 
        {
                if (!this.books.contains(book)) 
                {
                    this.books.add(book);
                    book.setStudent(this);
                }
        }
        

        public void removeBook(Book book) 
        {
                if (this.books.contains(book)) 
                {
                    this.books.remove(book);
                    book.setStudent(null);
                }
        }

        public List<Book> getBooks()
        {
                return books;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", age=" + age +
                    '}';
        }
}