package com.example.library.service;

import com.example.library.model.Reader;
import com.example.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderService {
    ReaderRepository readerRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository){
        this.readerRepository = readerRepository;
    }

    /**
     * The method saves the user to the database
     * @param reader An object of the Reader class which should have the following parameters: username, password, email, role
     */
    public void registerUser(Reader reader){
        readerRepository.save(reader);
    }

    /**
     * The method checks if the user with current name exists in the database
     * @param username Users username
     * @return If the user exists the method returns true, otherwise, it returns false
     */
    public boolean isUserExistCheckByUsername(String username){
        return readerRepository.existsByUsername(username);
    }

    /**
     * The method checks if the user with current email exists in the database
     * @param email Users email
     * @return If the user exists the method returns true, otherwise, it returns false
     */
    public boolean isUserExistCheckByEmail(String email){
        return readerRepository.existsByEmail(email);
    }


}
