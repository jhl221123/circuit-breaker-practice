package practice.circuitbreaker.domain.book;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import practice.circuitbreaker.api.book.response.BookResponse;
import practice.circuitbreaker.client.BookSearchClient;

@AllArgsConstructor
@Component
public class BookReader {

	private BookRepository bookRepository;
	private BookSearchClient bookSearchClient;

	public Book findBookOrSave(String isbn) {
		return bookRepository.findById(isbn).orElseGet(() -> processBookNotExist(isbn));
	}

	private Book processBookNotExist(String isbn) {
		return bookSearchClient.findBookByIsbn(isbn)
			.map(BookResponse::toEntity)
			.map(bookRepository::save)
			.orElseThrow(() -> new RuntimeException("ErrorCode.NotFound"));
	}
}
