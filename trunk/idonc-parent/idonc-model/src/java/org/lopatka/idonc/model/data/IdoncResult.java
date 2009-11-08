package org.lopatka.idonc.model.data;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



@Entity(name="RESULTS")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class IdoncResult implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Lob @Basic(fetch=FetchType.EAGER)
    @Column(name="VAL")
    private Long val;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

	public Long getVal() {
		return val;
	}

	public void setVal(Long val) {
		this.val = val;
	}

}
