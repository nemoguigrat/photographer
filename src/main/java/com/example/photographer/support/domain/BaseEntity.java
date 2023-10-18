package com.example.photographer.support.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
@NoArgsConstructor
@FieldNameConstants
@AllArgsConstructor
@TypeDef(name = DataType.JSONB, typeClass = JsonBinaryType.class)
public abstract class BaseEntity {

    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = HibernateSequence.NAME)
    @SequenceGenerator(name = HibernateSequence.NAME, allocationSize = HibernateSequence.ALLOCATION_SIZE)
    Long id;

    @Version
    Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseEntity)) {
            return false;
        }
        BaseEntity other = (BaseEntity) o;
        return null != id && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
