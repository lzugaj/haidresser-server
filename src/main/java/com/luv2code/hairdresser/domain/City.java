package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.luv2code.hairdresser.domain.base.BaseEntity;
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
@Table(name = "CITY")
@EqualsAndHashCode(callSuper = true)
public class City extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "postal_code")
    private String postalCode;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "county_id", nullable = false)
    private County county;

    @JsonBackReference
    @OneToMany(mappedBy = "city")
    private List<Salon> salons;

}
