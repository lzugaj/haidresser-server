package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.luv2code.hairdresser.domain.base.BaseEntity;
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
@Table(name = "USER")
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "number_of_reservations")
    private Integer numberOfReservations;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "has_reserved_indent")
    private Boolean hasReservedIndent;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @JsonBackReference
    @OneToMany(mappedBy = "user")
    private List<Indent> indents;

}
