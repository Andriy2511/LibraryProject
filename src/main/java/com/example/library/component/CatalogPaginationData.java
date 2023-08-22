package com.example.library.component;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class CatalogPaginationData {
    private String sortingField = "title";
    private int page = 0;
    private int pageSize = 5;
    private int totalPages = 1;
}
