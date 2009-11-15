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
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



@Entity(name="RESULTS")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class IdoncResult implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Lob @Basic(fetch=FetchType.EAGER)
    @Column(name="VALUE")
    private String value;

    @OneToOne(optional = false, mappedBy = "result")
    private IdoncPart parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getValue() {
		return value;
	}

	public void setValue(String val) {
		this.value = val;
	}

	public IdoncPart getParent() {
		return parent;
	}

	public void setParent(IdoncPart parent) {
		this.parent = parent;
	}


}
