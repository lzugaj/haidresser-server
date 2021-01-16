package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.luv2code.hairdresser.domain.base.BasePerson;
import com.luv2code.hairdresser.domain.enums.StatusType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
@EqualsAndHashCode(callSuper = true)
public class User extends BasePerson implements Serializable {

    @Column(name = "number_of_reservations")
    private Integer numberOfReservations;

    @Column(name = "status")
    private StatusType status;

    @Column(name = "has_reserved_indent")
    private Boolean hasReservedIndent;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Indent> indents;

    @ToString.Exclude
    @JsonManagedReference
    @ManyToMany(mappedBy = "users")
    private List<Role> roles;

}
