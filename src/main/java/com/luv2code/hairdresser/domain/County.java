package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.luv2code.hairdresser.domain.base.BaseEntity;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COUNTY")
@EqualsAndHashCode(callSuper = true)
public class County extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "county")
    private List<City> cities;

    @JsonBackReference
    @OneToMany(mappedBy = "county")
    private List<Salon> salons;

}
