package com.luv2code.hairdresser.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Setter
@Table(name = "ROLE")
@ToString
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "role")
    private List<User> users;

}
