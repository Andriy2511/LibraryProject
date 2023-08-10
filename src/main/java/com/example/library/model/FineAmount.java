package com.example.library.model;

import lombok.*;

import javax.persistence.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fines_amount")
public class FineAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;

    @OneToMany(mappedBy = "amount")
    @ToString.Exclude
    private List<Fine> fines;
}
