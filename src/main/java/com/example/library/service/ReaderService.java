package com.example.library.service;

import com.example.library.DTO.ReaderDTO;
import com.example.library.model.Reader;
import com.example.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {
    ReaderRepository readerRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ReaderService(ReaderRepository readerRepository, BCryptPasswordEncoder passwordEncoder){
        this.readerRepository = readerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * The method saves the user to the database
     * @param readerDTO An object of the ReaderDTO class which should have the following parameters: username, password, email
     */
    public void registerUser(ReaderDTO readerDTO){
        Reader reader = new Reader();
        reader.setUsername(readerDTO.getUsername());
        reader.setPassword(passwordEncoder.encode(readerDTO.getPassword()));
        reader.setEmail(readerDTO.getEmail());
        reader.setBlocked(false);

        readerRepository.save(reader);
    }

    /**
     * The method finds user by his username
     * @param username Users username
     * @return List of readers
     */
    public List<Reader> findUserByUsername(String username){
        return readerRepository.findAllByUsername(username);
    }

    /**
     * The method finds user by his email
     * @param email Users email
     * @return List of readers
     */
    public List<Reader> findUserByEmail(String email){
        return readerRepository.findAllByEmail(email);
    }

    /**
     * The method checks if the user with current name exists in the database
     * @param username Users username
     * @return If the user exists the method returns true, otherwise, it returns false
     */
    public boolean isUserExistCheckByUsername(String username){
        List<Reader> readerList = findUserByUsername(username);
        return readerList.size() >= 1;
    }

    /**
     * The method checks if the user with current email exists in the database
     * @param email Users email
     * @return If the user exists the method returns true, otherwise, it returns false
     */
    public boolean isUserExistCheckByEmail(String email){
        List<Reader> readerList = findUserByEmail(email);
        return readerList.size() >= 1;
    }
}
