package com.kittipong.assignment.feature.book.service;

import com.kittipong.assignment.common.exception.BookStoreServiceApiException;
import com.kittipong.assignment.common.exception.BookStoreServiceDataNotFoundException;
import com.kittipong.assignment.feature.book.controller.domain.BookResponseController;
import com.kittipong.assignment.feature.book.dao.BookDao;
import com.kittipong.assignment.feature.book.dao.domain.BookResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@TestComponent
public class BookServiceTests {
    @InjectMocks
    BookService bookService;
    @Mock
    BookDao bookDao;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Get_Books_Service_Success()throws BookStoreServiceApiException, BookStoreServiceDataNotFoundException {
        //given
        BookResponse book1 = new BookResponse(1,100.50,"DDD","author");
        BookResponse book2 = new BookResponse(2,200.50,"CCC","author2");
        BookResponse book3 = new BookResponse(3,100.50,"BBB","author3");
        BookResponse book4 = new BookResponse(4,200.50,"AAA","author4");
        List<BookResponse> recommendBooks = Arrays.asList(book2,book3);
        List<BookResponse> allBooks = Arrays.asList(book1,book2,book3,book4);
        given(bookDao.getRecommendBooks()).willReturn(recommendBooks);
        given(bookDao.getAllBooks()).willReturn(allBooks);
        //When
        List<BookResponseController> resultBooks = bookService.getBooks();


        //Then
        Assertions.assertEquals(resultBooks.size(), 4);
        Assertions.assertEquals(resultBooks.get(0).getId(),3);
        Assertions.assertEquals(resultBooks.get(1).getId(),2);
        Assertions.assertEquals(resultBooks.get(2).getId(),4);
        Assertions.assertEquals(resultBooks.get(3).getId(),1);
    }

    @Test
    void Get_Books_Service_Fail_No_Book()throws BookStoreServiceApiException {
        //Given

        List<BookResponse> recommendBooks = new ArrayList<>();
        List<BookResponse> allBooks = new ArrayList<>();
        given(bookDao.getRecommendBooks()).willReturn(recommendBooks);
        given(bookDao.getAllBooks()).willReturn(allBooks);
        //When
        try {
            List<BookResponseController> resultBooks = bookService.getBooks();
        }
        catch(BookStoreServiceDataNotFoundException ex){
            //Then
            Assertions.assertEquals(HttpStatus.NOT_FOUND,ex.getHttpStatus());
        }

    }

    @Test
    void Get_Books_Service_Success_Without_Reccommend_Book() throws BookStoreServiceApiException, BookStoreServiceDataNotFoundException {
        //Given
        BookResponse book1 = new BookResponse(1,100.50,"DDD","author");
        BookResponse book2 = new BookResponse(2,200.50,"CCC","author2");
        BookResponse book3 = new BookResponse(3,100.50,"BBB","author3");
        BookResponse book4 = new BookResponse(4,200.50,"AAA","author4");
        List<BookResponse> recommendBooks = new ArrayList<>();
        List<BookResponse> allBooks = Arrays.asList(book1,book2,book3,book4);
        given(bookDao.getRecommendBooks()).willReturn(recommendBooks);
        given(bookDao.getAllBooks()).willReturn(allBooks);
        //When

        List<BookResponseController> resultBooks = bookService.getBooks();
        //Then
        Assertions.assertEquals(resultBooks.size(), 4);
        Assertions.assertEquals(resultBooks.get(0).getId(),4);
        Assertions.assertEquals(resultBooks.get(1).getId(),3);
        Assertions.assertEquals(resultBooks.get(2).getId(),2);
        Assertions.assertEquals(resultBooks.get(3).getId(),1);

    }
}
