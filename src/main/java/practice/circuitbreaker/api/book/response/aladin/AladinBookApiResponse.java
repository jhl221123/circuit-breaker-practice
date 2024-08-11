package practice.circuitbreaker.api.book.response.aladin;

import java.util.List;

import lombok.Builder;

@Builder
public record AladinBookApiResponse(Integer totalResults, List<AladinBookItem> item) {
}