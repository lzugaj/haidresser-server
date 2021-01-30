package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.luv2code.hairdresser.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CERTIFICATE")
@EqualsAndHashCode(callSuper = true)
public class Certificate extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "receipt_on")
    private LocalDate receiptDate;

    @Column(name = "created_at")
    private LocalDateTime creationDate;

    @Column(name = "photo_file_path")
    private String photoFilePath;

    @ManyToMany
    @JsonBackReference
    @JoinTable(name = "person_certificate",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id"))
    private List<Person> people;

}
