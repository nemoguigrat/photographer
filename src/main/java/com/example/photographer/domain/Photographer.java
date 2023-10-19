package com.example.photographer.domain;

import com.example.photographer.service.dto.photographer.request.PhotographerUpdateRequest;
import com.example.photographer.support.domain.BaseEntity;
import com.example.photographer.support.domain.DataType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Photographer extends BaseEntity {

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "technique_info_id")
    @Setter
    TechniqueInfo techniqueInfo;

    @Column
    String firstname;

    @Column
    String surname;

    @Column
    String middleName;

    @Column
    LocalDate birthdate;

    @Column
    String phone;

    @Type(type = DataType.JSONB)
    @Column(name = "contacts", columnDefinition = DataType.JSONB)
    Map<String, String> contacts;

    @Column
    String email;

    @Column
    boolean trainee;

    @Column
    String description;

    @Column
    Integer score;

    @Lob
    @Column
    byte[] image;

    @OneToOne(mappedBy = "photographer")
    User user;

    public void updateFrom(PhotographerUpdateRequest request) {
        this.firstname = request.getFirstname();
        this.surname = request.getSurname();
        this.middleName = request.getMiddleName();
        this.birthdate = request.getBirthdate();
        this.contacts = request.getContacts();
        this.phone = request.getPhone();
    }
}