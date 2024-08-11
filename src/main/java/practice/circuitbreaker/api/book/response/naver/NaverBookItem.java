package practice.circuitbreaker.api.book.response.naver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Builder;
import practice.circuitbreaker.api.book.response.BookResponse;

@Builder
public record NaverBookItem(String isbn, String title, String author, String publisher, String pubdate, String image,
							String description) {

	public BookResponse toBookResponse() {
		return new BookResponse(this.isbn(), this.title(), this.author(), this.publisher(),
			LocalDate.parse(this.pubdate(), DateTimeFormatter.ofPattern("yyyyMMdd")), this.image(), this.description());
	}
}