package com.example.library.component;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
public class CatalogPaginationData {
    private String sortingField = "title";
    private int page = 0;
    private int pageSize = 5;
    private int totalPages = 1;
}
