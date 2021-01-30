package com.luv2code.hairdresser.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.luv2code.hairdresser.domain.base.BaseEntity;
import com.luv2code.hairdresser.domain.enums.GenderType;
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
@Table(name = "PERSON")
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity implements Serializable {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Column(name = "photo_file_path")
    private String photoFilePath;

    @Column(name = "registered_at")
    private LocalDateTime registrationDate;

    @JsonManagedReference
    @ManyToMany(mappedBy = "people")
    private List<Role> roles;

    @JsonManagedReference
    @ManyToMany(mappedBy = "people")
    private List<Certificate> certificates;

}
