package com.example.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "readers")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private boolean isBlocked;
    private String email;

    public Reader(String username, String password, boolean isBlocked, String email) {
        this.username = username;
        this.password = password;
        this.isBlocked = isBlocked;
        this.email = email;
    }

    @ManyToMany
    @JoinTable(
            name = "readers_roles",
            joinColumns = @JoinColumn(name = "reader_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private List<Role> roles;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Message> messages;

    @OneToMany(mappedBy = "reader")
    @ToString.Exclude
    private List<Order> orders;
}
