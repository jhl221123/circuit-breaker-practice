package practice.circuitbreaker.client;

import java.util.Optional;

import practice.circuitbreaker.api.book.response.BookListResponse;
import practice.circuitbreaker.api.book.response.BookResponse;

public interface BookSearchClient {
	BookListResponse findBookByKeyword(String keyword, Integer page, Integer size);
	Optional<BookResponse> findBookByIsbn(String isbn);
}