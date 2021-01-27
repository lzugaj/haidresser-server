package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.luv2code.hairdresser.domain.base.BaseEntity;
import com.luv2code.hairdresser.domain.enums.StatusType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SALON")
@EqualsAndHashCode(callSuper = true)
public class Salon extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "oib")
    private String oib;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "additional_street_number")
    private String additionalStreetNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusType status;

    @Column(name = "number_of_employees")
    private Integer numberOfEmployees;

    @Column(name = "registered_at")
    private LocalDateTime registrationDate;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "county_id", nullable = false)
    private County county;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @JsonBackReference
    @OneToMany(mappedBy = "salon")
    private List<Employer> employers;

    @JsonBackReference
    @OneToMany(mappedBy = "salon")
    private List<Indent> indents;

    @JsonBackReference
    @ManyToMany
    @JoinTable(name = "SALON_ACCOMMODATION",
            joinColumns = @JoinColumn(name = "salon_id"),
            inverseJoinColumns = @JoinColumn(name = "accommodation_id"))
    private List<Accommodation> accommodations;

}
