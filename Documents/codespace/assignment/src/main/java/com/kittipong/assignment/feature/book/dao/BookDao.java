package com.kittipong.assignment.feature.book.dao;


import com.kittipong.assignment.common.exception.BookStoreServiceApiException;
import com.kittipong.assignment.feature.book.dao.domain.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Repository
public class BookDao {

    @Value("${book.uri}")
    private String uris;
    @Value("${recommend.endpoint}")
    private String recommendEndpoint;

    @Autowired private RestTemplate restTemplate;

    public List<BookResponse> getRecommendBooks() throws BookStoreServiceApiException {
        String uri = uris.concat(recommendEndpoint);
        return getBook(uri);
    }

    public List<BookResponse> getAllBooks() throws BookStoreServiceApiException {
        return getBook(uris);
    }

    private List<BookResponse> getBook(String uri) throws BookStoreServiceApiException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        try {
            HttpEntity entity = new HttpEntity<>(httpHeaders);
            HttpEntity <List<BookResponse>> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<>() {
                    } );
            return responseEntity.getBody();

        }catch (HttpClientErrorException e){
            throw new BookStoreServiceApiException(e.getStatusCode());
        }
    }
}
