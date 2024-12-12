package com.amigoscode.spring_data_jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.ForeignKey;
import java.time.LocalDateTime;
import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity(name = "Book")
@Table(name = "book")
@Data
@NoArgsConstructor
public class Book {

    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "book_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;


    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAt;


    @Column(
            name = "book_name",
            nullable = false
    )
    private String bookName;


    @ManyToOne
    @JoinColumn(
            name = "student_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_book_fk" )
    )
    private Student student;


    public Book(String bookName,
                LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.bookName = bookName;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", bookName='" + bookName + '\'' +
                ", student=" + student +
                '}';
    }
}