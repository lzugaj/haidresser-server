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

    @Column(name = "reserved_on")
    private LocalDate reservationDate;

    @Column(name = "reserved_from")
    private LocalTime reservationTimeFrom;

    @Column(name = "reserved_to")
    private LocalTime reservationTimeTo;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private IndentType status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "employer_id", nullable = false)
    private Employer employer;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "salon_id", nullable = false)
    private Salon salon;

    @JsonManagedReference
    @ManyToMany(mappedBy = "indents")
    private List<Accommodation> accommodations;

}
