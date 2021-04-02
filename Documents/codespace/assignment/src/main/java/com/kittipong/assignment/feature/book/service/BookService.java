package com.kittipong.assignment.feature.book.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.kittipong.assignment.common.exception.BookStoreServiceApiException;
import com.kittipong.assignment.common.exception.BookStoreServiceDataNotFoundException;
import com.kittipong.assignment.feature.book.controller.domain.BookResponseController;
import com.kittipong.assignment.feature.book.dao.BookDao;
import com.kittipong.assignment.feature.book.dao.domain.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;
    @Cacheable("getBooks")
    public List<BookResponseController> getBooks() throws BookStoreServiceApiException, BookStoreServiceDataNotFoundException {
      List<BookResponse> allBooks = bookDao.getAllBooks();
      List<BookResponse> recommendedBooks = bookDao.getRecommendBooks();
      if(allBooks.size()==0)
          throw new BookStoreServiceDataNotFoundException(HttpStatus.NOT_FOUND);

      List<BookResponseController> recommendedBooksController = recommendedBooks.stream()
              .sorted(Comparator.comparing(BookResponse::getBookName))
              .map(book -> new BookResponseController(book.getId(),book.getBookName(), book.getAuthorName(), book.getPrice(), true))
              .collect(Collectors.toList());
      List<BookResponseController> allBooksController = allBooks.stream()
              .sorted(Comparator.comparing(BookResponse::getBookName))
              .map(book -> new BookResponseController(book.getId(), book.getBookName(), book.getAuthorName(), book.getPrice(),false))
              .collect(Collectors.toList());
        recommendedBooksController.addAll(allBooksController);
        return recommendedBooksController.stream().filter( distinctByKey(BookResponseController::getId)).collect( Collectors.toList() );

    }

    private  <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
