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
    @Column(name = "order_id")
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;

    private Double fineCost;
    private boolean isPaid;
}
