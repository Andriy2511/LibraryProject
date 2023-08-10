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
@Table(name = "fines")
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="amount_id")
    private FineAmount amount;

    private Double fineCost;
    private boolean isActive;
    private boolean isPaid;

    @ManyToMany
    @JoinTable(
            name = "orders_fines",
            joinColumns = @JoinColumn(name = "fine_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    @ToString.Exclude
    private List<Order> orders;
}
