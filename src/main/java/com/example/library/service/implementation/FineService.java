package com.example.library.service.implementation;

import com.example.library.model.Fine;
import com.example.library.model.Reader;
import com.example.library.repository.FineRepository;
import com.example.library.service.IFineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FineService implements IFineService {

    private final FineRepository fineRepository;

    @Autowired
    public FineService(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    @Override
    public void saveFine(Fine fine) {
        log.info("Fine is: {}", fine);
        log.info("Order is: {}", fine.getOrder());
        //fineRepository.save(fine);
        fineRepository.insertFine(fine.getOrder().getId(), fine.getFineCost(), fine.isPaid());
    }

    @Override
    public List<Fine> findAllFinesWithPagination(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return fineRepository.findAll(pageable).getContent();
    }

    @Override
    public Long getFinesCount() {
        return fineRepository.count();
    }

    @Override
    public Long getFinesCountByReader(Reader reader) {
        return (long) fineRepository.findUnreturnedFinesForReader(reader.getId()).size();
    }

    @Override
    public List<Fine> findAllFinesByReaderWithPagination(int page, int pageSize, Long readerId) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return fineRepository.findAllFinesForReaderById(readerId, pageable);
    }

    @Override
    public Fine getFineById(Long fineId) {
        return fineRepository.findFirstFineByOrderId(fineId).get();
    }

    @Override
    public void updateFine(Fine fine) {
        fineRepository.updateFine(fine.getOrder().getId(), fine.getFineCost(), fine.isPaid());
    }
}
