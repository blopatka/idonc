package org.lopatka.idonc.model.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.lopatka.idonc.model.user.IdoncUser;

@Entity(name="PARTS")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class IdoncPart implements Serializable, Comparable<IdoncPart> {

    private static final long serialVersionUID = 1L;

	@Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name="NUMBER")
	private Long number;

    @Column(name="NAME")
	private String name;

	@OneToMany
	@Cascade({CascadeType.ALL})
    @JoinTable(
        name="PART_LONG_DATA_ELEMENTS",
        joinColumns={@JoinColumn(name="PART_ID")},
        inverseJoinColumns=@JoinColumn(name="LONG_DATA_ID")
    )
    @LazyToOne(LazyToOneOption.FALSE)
    private List<IdoncLongData> longDataList;

	@OneToMany
	@Cascade({CascadeType.ALL})
    @JoinTable(
        name="PART_RESULTS",
        joinColumns={@JoinColumn(name="PART_ID")},
        inverseJoinColumns=@JoinColumn(name="RESULT_ID")
        )
    @LazyToOne(LazyToOneOption.FALSE)
    private List<IdoncResult> results;

    @OneToMany
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinTable(
        name="PART_USERS",
        joinColumns={@JoinColumn(name="PART_ID")},
        inverseJoinColumns=@JoinColumn(name="USER_ID")
    )
    private List<IdoncUser> usersProcessing;

    @OneToMany
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinTable(
        name="LOCKED_USERS",
        joinColumns={@JoinColumn(name="PART_ID")},
        inverseJoinColumns=@JoinColumn(name="USER_ID")
    )
    private List<IdoncUser> lockedUsers;


	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<IdoncLongData> getLongDataList() {
		return longDataList;
	}

	public void setLongDataList(List<IdoncLongData> longDataList) {
		this.longDataList = longDataList;
	}

	public List<IdoncResult> getResults() {
		return results;
	}

	public void setResults(List<IdoncResult> results) {
		this.results = results;
	}

	public List<IdoncUser> getUsersProcessing() {
		return usersProcessing;
	}

	public void setUsersProcessing(List<IdoncUser> usersProcessing) {
		this.usersProcessing = usersProcessing;
	}

	public List<IdoncUser> getLockedUsers() {
		return lockedUsers;
	}

	public void setLockedUsers(List<IdoncUser> lockedUsers) {
		this.lockedUsers = lockedUsers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public int compareTo(IdoncPart o) {
		if(this == o) {
			return 0;
		}

		if(this.id < o.id) {
			return -1;
		}

		if(this.id > o.id) {
			return 1;
		}

		return 0;
	}

}
