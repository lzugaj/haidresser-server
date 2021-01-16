package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.luv2code.hairdresser.domain.base.BasePerson;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMPLOYER")
@EqualsAndHashCode(callSuper = true)
public class Employer extends BasePerson implements Serializable {

    @ToString.Exclude
    @JsonManagedReference
    @ManyToMany(mappedBy = "employers")
    private List<Role> roles;

}
