package com.kittipong.assignment.feature.book.controller;

import com.kittipong.assignment.common.exception.BookStoreServiceApiException;
import com.kittipong.assignment.common.exception.BookStoreServiceDataNotFoundException;
import com.kittipong.assignment.feature.book.controller.domain.BookResponseController;
import com.kittipong.assignment.feature.book.controller.domain.BookNodeResponse;
import com.kittipong.assignment.feature.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @GetMapping(value = "/books")
    public ResponseEntity<?> getBooks() throws BookStoreServiceApiException, BookStoreServiceDataNotFoundException {
        List<BookResponseController> allBooks = bookService.getBooks();
        BookNodeResponse bookNodeResponse = new BookNodeResponse(allBooks);
        return ResponseEntity.ok(bookNodeResponse);

    }
}
