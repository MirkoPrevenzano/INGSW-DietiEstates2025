package com.amigoscode.spring_boot_tutorial.student;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long>
{
    Optional<Student> findStudentByEmail(String email);
}