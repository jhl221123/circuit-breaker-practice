package practice.circuitbreaker.api.book.response;

import java.util.List;

import lombok.Builder;

@Builder
public record BookListResponse(Integer maxPage, List<BookResponse> bookList) {
}