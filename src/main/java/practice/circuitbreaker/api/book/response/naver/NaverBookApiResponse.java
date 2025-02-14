package practice.circuitbreaker.api.book.response.naver;

import java.util.List;

import lombok.Builder;

@Builder
public record NaverBookApiResponse(Integer total, List<NaverBookItem> items) {
}