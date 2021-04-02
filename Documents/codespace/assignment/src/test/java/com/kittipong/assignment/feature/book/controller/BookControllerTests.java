package com.kittipong.assignment.feature.book.controller;

import com.kittipong.assignment.common.exception.BookStoreServiceApiException;
import com.kittipong.assignment.common.exception.BookStoreServiceDataNotFoundException;
import com.kittipong.assignment.feature.book.controller.domain.BookNodeResponse;
import com.kittipong.assignment.feature.book.controller.domain.BookResponseController;
import com.kittipong.assignment.feature.book.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@TestComponent
public class BookControllerTests {
    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;



    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Get_Books_Controller_Success()  throws BookStoreServiceApiException, BookStoreServiceDataNotFoundException {
        //Given
        BookResponseController book1 = new BookResponseController(1,"name","author",100.50,false);
        BookResponseController book2 = new BookResponseController(2,"name2","author2",200.50,true);
        List<BookResponseController> allBooks = Arrays.asList(book1, book2);
        given(bookService.getBooks()).willReturn(allBooks);
        //When
        ResponseEntity<?> result = bookController.getBooks();
        BookNodeResponse response = (BookNodeResponse) result.getBody();


        //Then
        Assertions.assertEquals(HttpStatus.OK,result.getStatusCode());
        Assertions.assertTrue(response.getBooks().containsAll(allBooks));

    }


}
