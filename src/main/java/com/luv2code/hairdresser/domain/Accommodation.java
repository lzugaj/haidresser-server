package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "ACCOMMODATION")
public class Accommodation extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price;

    @Column(name = "duration")
    private Integer duration;

    @JsonBackReference
    @ManyToMany
    @JoinTable(
            name = "INDENT_ACCOMMODATION",
            joinColumns = {
                    @JoinColumn(name = "accommodation_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "indent_id")
            })
    private List<Indent> indents;

}
