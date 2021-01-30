package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.luv2code.hairdresser.domain.enums.StatusType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
@EqualsAndHashCode(callSuper = true)
public class User extends Person implements Serializable {

    @Column(name = "number_of_reservations")
    private Integer numberOfReservations;

    @Column(name = "has_reserved_indent")
    private Boolean hasReservedIndent;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusType status;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Indent> indents;

}
