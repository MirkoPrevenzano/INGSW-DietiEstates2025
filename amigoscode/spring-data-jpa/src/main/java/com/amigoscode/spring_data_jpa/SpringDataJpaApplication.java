package com.amigoscode.spring_data_jpa;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.amigoscode.spring_data_jpa.model.*;
import com.amigoscode.spring_data_jpa.repository.BookRepository;
import com.amigoscode.spring_data_jpa.repository.StudentIdCardRepository;
import com.amigoscode.spring_data_jpa.repository.StudentRepository;


@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, 
										StudentIdCardRepository studentIdCardRepository,
										BookRepository bookRepository,
										StudentService studentService)
	{
		return args -> 
		{
			System.out.println("Adding students maria and ahmed");
			Student maria = new Student(
				"Maria",
				"Jones",
				"maria.jones@amigoscode.edu",
				21
			);
			Student ahmed = new Student(
				"Ahmed",
				"Ali",
				"ahmed.ali@amigoscode.edu",
				18
			);
			studentRepository.saveAll(List.of(maria, ahmed));
            System.out.println(studentRepository.findById(1L).get());

 

			System.out.print("\nNumber of students: ");
			System.out.println(studentRepository.count());



			studentRepository
				.findById(2L)
				.ifPresentOrElse(
						System.out::println,
						() -> System.out.println("\nStudent with ID 2 not found"));
			studentRepository
				.findById(3L)
				.ifPresentOrElse(
						System.out::println,
						() -> System.out.println("\nStudent with ID 3 not found"));



			System.out.println("\nSelect all students");
			List<Student> students = studentRepository.findAll();
			students.forEach(System.out::println);



			System.out.println("\nDeleting maria");
			studentRepository.deleteById(1L);



			System.out.print("\nNumber of students: ");
			System.out.println(studentRepository.count());



			System.out.println("\nAdding maria2");
			Student maria2 = new Student
			(
				"Maria",
				"Jones",
				"maria2.jones@amigoscode.edu",
				25
			);
			studentRepository.save(maria2);



			studentRepository.findStudentByEmail("ahmed.ali@amigoscode.edu")
							 .ifPresentOrElse(System.out::println,
											  () -> System.out.println(
												"\nStudent with email ahmed.ali@amigoscode.edu not found"));



			studentRepository.selectStudentWhereFirstNameAndAgeGreaterOrEqual("Maria",21)
							 .forEach(System.out::println);



			System.out.println("\nDeleting Maria 2");
			studentRepository.deleteStudentById(3L);



			System.out.println("\nAdding studentIdCard of student maria3 (will be added also the student maria3)");
			Student maria3 = new Student
			(
				"Maria",
				"Jones",
				"maria3.jones@amigoscode.edu",
				25
			);
           StudentIdCard studentIdCard = new StudentIdCard(
                    "123456789");
		   studentIdCard.setStudent(maria3);	
		   studentIdCardRepository.save(studentIdCard);



		   System.out.println("\nDeleting student maria3 (will be removed also her studentIdCard)");
		   studentRepository.deleteById(4L);



		   System.out.println("\nAdding student maria4 (will be added also her studentIdCard and her books)");
		   Book book1 = new Book("Clean Code", LocalDateTime.now().minusDays(4));
		   Book book2 = new Book("Think and Grow Rich", LocalDateTime.now());
		   Book book3 = new Book("Spring Data JPA", LocalDateTime.now().minusYears(1));
		   Student maria4 = new Student
		   (
			   "Maria",
			   "Jones",
			   "maria4.jones@amigoscode.edu",
			   25
		   );
		   maria4.addBook(book1);
		   maria4.addBook(book2);
		   maria4.addBook(book3);
		   StudentIdCard studentIdCard2 = new StudentIdCard("987654321", maria4);
		   maria4.setStudentIdCard(studentIdCard2);
		   studentRepository.save(maria4);



		   System.out.println("\nFinding all the books of student maria4");

		   studentRepository.findById(5L)
		   .ifPresent(s -> {
			   System.out.println(s);
			   System.out.println("fetch book lazy...");
			   List<Book> books = maria4.getBooks();
			   books.forEach(book -> {
				   System.out.println(s.getFirstName() + " borrowed " + book.getBookName());
			   });
		   });
		};
	}
}
