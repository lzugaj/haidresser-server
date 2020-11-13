package com.luv2code.hairdresser.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Setter
@Table(name = "ORDERS")
@ToString
public class Order extends BaseEntity {

    @Column(name = "reservation_date")
    private Date reservationDate;

    @Column(name = "reservation_time")
    private Time reservationTime;

    @Column(name = "canceled_reservation_date")
    private Date canceledReservationDate;

    @Column(name = "canceled_reservation_time")
    private Time canceledReservationTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(mappedBy = "orders")
    private List<Service> services;

}
