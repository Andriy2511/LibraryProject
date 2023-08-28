package com.example.library.service;

import com.example.library.model.Fine;
import com.example.library.model.Reader;

import java.util.List;

public interface IFineService {

    void saveFine(Fine fine);

    List<Fine> findAllFinesWithPagination(int page, int pageSize);

    Long getFinesCount();

    Long getFinesCountByReader(Reader reader);

    List<Fine> findAllFinesByReaderWithPagination(int page, int pageSize, Long readerId);

    Fine getFineById(Long fineId);

    void updateFine(Fine fine);
}
