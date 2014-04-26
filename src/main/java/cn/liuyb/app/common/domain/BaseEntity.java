package cn.liuyb.app.common.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for
 * objects needing this property.
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BaseEntity extends DomainModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public boolean isNew() {
        return (this.id == null);
    }

}
