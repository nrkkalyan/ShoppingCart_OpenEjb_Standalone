package com.nrk.cart.persistence.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class BaseTimeEntity extends BaseEntity {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIMESTAMP", nullable = false, updatable = false)
    private Date createdTimestamp = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_TIMESTAMP")
    private Date lastModifiedTimestamp;

    public Date getCreatedTimestamp() {
        return createdTimestamp;
    }

    public Date getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    void setCreatedTimestamp(final Date createdDateTime) {
        this.createdTimestamp = createdDateTime;
    }

    void setLastModifiedTimestamp(final Date lastModifiedTimestamp) {
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

}
