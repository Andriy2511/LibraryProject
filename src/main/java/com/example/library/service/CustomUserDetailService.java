package com.example.library.service;

import com.example.library.model.Reader;
import com.example.library.model.Role;
import com.example.library.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final ReaderRepository readerRepository;

    @Autowired
    public CustomUserDetailService(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Reader reader = readerRepository.findAllByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        //return new User(reader.getUsername(), reader.getPassword(), mapRolesToGrantedAuthorities(reader.getRoles()));
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                System.out.println(reader.getRoles());
                return mapRolesToGrantedAuthorities(reader.getRoles());
            }

            @Override
            public String getPassword() {
                System.out.println(reader.getPassword());
                return reader.getPassword();
            }

            @Override
            public String getUsername() {
                System.out.println(reader.getUsername());
                return reader.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                System.out.println(true);
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                System.out.println(true);
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                System.out.println(true);
                return true;
            }

            @Override
            public boolean isEnabled() {
                System.out.println(true);
                return true;
            }

            public String getFullName() {
                return username;
            }
        };
    }

    private Collection<GrantedAuthority> mapRolesToGrantedAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
