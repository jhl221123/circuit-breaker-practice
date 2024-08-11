package practice.circuitbreaker.api.book;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import practice.circuitbreaker.api.book.request.BookFindRequest;
import practice.circuitbreaker.api.book.response.BookListResponse;
import practice.circuitbreaker.client.BookSearchClient;

@RequiredArgsConstructor
@Service
public class BookService {
	private final BookSearchClient bookSearchClient;

	@Transactional(readOnly = true)
	public BookListResponse findBookList(BookFindRequest request) {
		String keyword = request.keyword().trim();
		int page = request.page();
		int size = 20;
		int startPage = startPage(size, page);

		if (keyword.isEmpty()) {
			return new BookListResponse(0, List.of());
		}

		return bookSearchClient.findBookByKeyword(keyword, startPage, size);
	}

	private Integer startPage(Integer size, Integer page) {
		return size * (page - 1) + 1;
	}
}