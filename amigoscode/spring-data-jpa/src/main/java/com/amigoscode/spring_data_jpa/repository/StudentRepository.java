package com.amigoscode.spring_data_jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.amigoscode.spring_data_jpa.model.Student;
import jakarta.transaction.Transactional;


@Repository
public interface StudentRepository extends JpaRepository<Student,Long>
{

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);


   @Query
    (
        value = "SELECT * FROM student WHERE first_name = :firstName AND age >= :age",
        nativeQuery = true
    )
    List<Student> selectStudentWhereFirstNameAndAgeGreaterOrEqual(String firstName, Integer age);


    @Transactional
    @Modifying
    @Query("DELETE FROM Student u WHERE u.id = ?1")
    int deleteStudentById(Long id);
}