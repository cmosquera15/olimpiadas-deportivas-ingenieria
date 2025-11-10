package com.ingenieria.olimpiadas.olimpiadas_deportivas.util;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public final class PaginationUtils {

    private PaginationUtils() {}

    public static <T> Page<T> toPage(List<T> all, Pageable pageable) {
        if (all == null || all.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        int start = Math.min((int) pageable.getOffset(), all.size());
        int end   = Math.min(start + pageable.getPageSize(), all.size());
        List<T> content = (start > end) ? Collections.emptyList() : all.subList(start, end);
        return new PageImpl<>(content, pageable, all.size());
    }
}
