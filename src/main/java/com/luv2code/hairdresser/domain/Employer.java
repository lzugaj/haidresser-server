package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EMPLOYER")
@EqualsAndHashCode(callSuper = true)
public class Employer extends Person implements Serializable {

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "salon_id", nullable = false)
    private Salon salon;

    @JsonBackReference
    @OneToMany(mappedBy = "employer")
    private List<Indent> indents;

}
