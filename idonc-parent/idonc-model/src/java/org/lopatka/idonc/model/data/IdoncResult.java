package org.lopatka.idonc.model.data;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

public class IdoncResult implements Serializable {
    private static final long serialVersionUID = 5579955974727356642L;


    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @OneToMany
    @JoinTable(
        name="RESULT_LONG_DATA_ELEMENTS",
        joinColumns={@JoinColumn(name="PART_ID")},
        inverseJoinColumns=@JoinColumn(name="LONG_DATA_ID")
    )
    private List<IdoncLongData> longDataList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<IdoncLongData> getLongDataList() {
        return longDataList;
    }

    public void setLongDataList(List<IdoncLongData> longDataList) {
        this.longDataList = longDataList;
    }

    
}
