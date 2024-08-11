package practice.circuitbreaker.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import practice.circuitbreaker.api.book.response.BookListResponse;
import practice.circuitbreaker.api.book.response.BookResponse;
import practice.circuitbreaker.api.book.response.aladin.AladinBookApiResponse;
import practice.circuitbreaker.api.book.response.aladin.AladinBookItem;

@Component
public class AladinBookSearchClient implements BookSearchClient {

	private final String ALADIN_CLIENT_TTBKEY;

	public AladinBookSearchClient(@Value("${aladin-client-ttbkey}") String aladinClientKey) {
		this.ALADIN_CLIENT_TTBKEY = aladinClientKey;
	}

	public BookListResponse findBookByKeyword(String keyword, Integer page, Integer size) {
		RestClient restClient = createRestClient();

		AladinBookApiResponse aladinBookApiResponse = restClient.get()
			.uri(uriBuilder -> uriBuilder.path("/ttb/api/ItemSearch.aspx")
				.queryParam("ttbkey", ALADIN_CLIENT_TTBKEY)
				.queryParam("Query", keyword)
				.queryParam("start", page)
				.queryParam("output", "JS")
				.queryParam("Version", "20131101")
				.build()
			)
			.retrieve()
			.body(AladinBookApiResponse.class);

		List<BookResponse> bookResponses = new ArrayList<>();
		Integer totalResult = 0;
		if (aladinBookApiResponse != null && aladinBookApiResponse.item() != null) {
			totalResult = aladinBookApiResponse.totalResults();
			for (AladinBookItem item : aladinBookApiResponse.item()) {
				BookResponse bookResponse = item.toBookResponse();
				bookResponses.add(bookResponse);
			}
		}
		Integer maxPage = totalResult != 0 ? (totalResult / size) + 1 : 0;
		return new BookListResponse(maxPage, bookResponses);
	}

	@Override
	public Optional<BookResponse> findBookByIsbn(String isbn) {
		System.out.println("isbn = " + isbn);
		RestClient restClient = createRestClient();
		AladinBookApiResponse aladinBookApiResponse = restClient.get()
			.uri(uriBuilder -> uriBuilder.path("/ttb/api/ItemLookUp.aspx")
				.queryParam("ttbkey", ALADIN_CLIENT_TTBKEY)
				.queryParam("ItemId", isbn)
				.queryParam("output", "JS")
				.queryParam("Version", "20131101")
				.build()
			)
			.retrieve()
			.body(AladinBookApiResponse.class);

		if (aladinBookApiResponse != null && aladinBookApiResponse.item() != null) {
			return aladinBookApiResponse.item().stream()
				.map(AladinBookItem::toBookResponse)
				.findAny();
		} else {
			return Optional.empty();
		}
	}

	private RestClient createRestClient() {
		return RestClient.builder().baseUrl("http://www.aladin.co.kr").build();
	}
}
