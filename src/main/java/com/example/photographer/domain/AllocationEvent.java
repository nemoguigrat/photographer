package com.example.photographer.domain;

import com.example.photographer.support.domain.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@FieldNameConstants
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllocationEvent extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    @Column
    String eventFrom;

    @Column
    Instant eventTime;

    @Column
    String code;

    @Column
    String message;

}
