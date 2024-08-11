package practice.circuitbreaker.api.book;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import practice.circuitbreaker.api.book.request.BookFindRequest;
import practice.circuitbreaker.api.book.response.BookListResponse;
import practice.circuitbreaker.api.book.response.BookResponse;
import practice.circuitbreaker.client.BookSearchClient;

@RequiredArgsConstructor
@RestController
public class BookController {
	private final BookService bookService;
	private final BookSearchClient bookSearchClient;

	@GetMapping("/books")
	public ResponseEntity<BookListResponse> findBookList(@Valid @ModelAttribute BookFindRequest request) {
		return ResponseEntity.status(HttpStatus.OK).body(bookService.findBookList(request));
	}

	@GetMapping("/books/{isbn}")
	public ResponseEntity<Optional<BookResponse>> findBookList(@PathVariable("isbn") String isbn) {
		return ResponseEntity.status(HttpStatus.OK).body(bookSearchClient.findBookByIsbn(isbn));
	}
}