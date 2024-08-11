package practice.circuitbreaker.api.book.response.aladin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Builder;
import practice.circuitbreaker.api.book.response.BookResponse;

@Builder
public record AladinBookItem(String isbn, String title, String author, String publisher, String pubDate, String cover,
							 String description) {

	public BookResponse toBookResponse() {
		return new BookResponse(this.isbn(), this.title(), this.author(), this.publisher(),
			LocalDate.parse(this.pubDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")), this.cover(), this.description());
	}
}