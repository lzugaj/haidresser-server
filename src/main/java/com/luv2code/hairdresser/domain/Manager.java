package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MANAGER")
@EqualsAndHashCode(callSuper = true)
public class Manager extends Person implements Serializable {

    @Column(name = "oib")
    private String oib;

    @Column(name = "is_employer")
    private Boolean isEmployer;

    @JsonBackReference
    @OneToMany(mappedBy = "manager")
    private List<Salon> salons;

}
