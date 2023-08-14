package com.example.library.service.implementation;

import com.example.library.model.Reader;
import com.example.library.model.Role;
import com.example.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IReaderService implements com.example.library.service.IReaderService {
    ReaderRepository readerRepository;

    @Autowired
    public IReaderService(ReaderRepository readerRepository){
        this.readerRepository = readerRepository;
    }

    @Override
    public void registerUser(Reader reader){
        readerRepository.save(reader);
    }

    @Override
    public boolean isUserExistCheckByUsername(String username){
        return readerRepository.existsByUsername(username);
    }

    @Override
    public boolean isUserExistCheckByEmail(String email){
        return readerRepository.existsByEmail(email);
    }

    @Override
    public Reader findReaderByName(String username){
        return readerRepository.findAllByUsername(username).get();
    }

    @Override
    public Reader findReaderById(Long id){
        return readerRepository.findById(id).orElse(null);
    }

    @Override
    public void saveReader(Reader reader){
        readerRepository.save(reader);
    }

    @Override
    public List<Reader> findAllReadersByRoles(List<Role> roleList) {
        return readerRepository.findAllByRolesIn(roleList);
    }
}
