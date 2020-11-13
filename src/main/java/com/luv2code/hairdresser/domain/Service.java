package com.luv2code.hairdresser.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Setter
@Table(name = "SERVICE")
@ToString
public class Service extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "duration")
    private Integer duration;

    @ManyToMany
    @JoinTable(
            name = "ORDER_SERVICE",
            joinColumns = {
                    @JoinColumn(name = "service_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "order_id")
            })
    private List<Order> orders;

}
