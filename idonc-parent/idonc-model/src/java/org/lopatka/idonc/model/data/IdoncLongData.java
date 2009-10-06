package org.lopatka.idonc.model.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="LONG_DATA")
public class IdoncLongData implements Serializable {
    
    private static final long serialVersionUID = 1L;

	@Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="VAL")
	private Long val;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getValue() {
		return val;
	}

	public void setValue(Long value) {
		this.val = value;
	}
}
