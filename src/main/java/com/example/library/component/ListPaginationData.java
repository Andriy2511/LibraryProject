package com.example.library.component;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * This component sets default values for list pagination properties
 */
@Component
@Data
public class ListPaginationData {
    int pageSize = 10;
    int page = 0;
    Long totalRecords = 10L;
    String lastVisitedPage = "";
}
