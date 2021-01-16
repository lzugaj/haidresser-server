package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.luv2code.hairdresser.domain.base.BaseEntity;
import com.luv2code.hairdresser.domain.enums.RoleType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Setter
@Table(name = "ROLE")
public class Role extends BaseEntity implements Serializable {

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleType name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @ToString.Exclude
    @JsonBackReference
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<User> users;

    @ManyToMany
    @ToString.Exclude
    @JsonBackReference
    @JoinTable(
            name = "employer_role",
            joinColumns = @JoinColumn(name = "employer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Employer> employers;

}
