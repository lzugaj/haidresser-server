package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.luv2code.hairdresser.domain.base.BaseEntity;
import com.luv2code.hairdresser.domain.enums.IndentType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Table(name = "INDENT")
public class Indent extends BaseEntity {

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "reservation_time_from")
    private LocalTime reservationTimeFrom;

    @Column(name = "reservation_time_to")
    private LocalTime reservationTimeTo;

    @Column(name = "is_active")
    @Enumerated(EnumType.STRING)
    private IndentType status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonManagedReference
    @ManyToMany(mappedBy = "indents")
    private List<Accommodation> accommodations;

}
