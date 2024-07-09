package dev.patika.library_rest_api.dao;

import dev.patika.library_rest_api.entities.BookBorrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookBorrowingRepo extends JpaRepository<BookBorrowing, Integer> {
}
