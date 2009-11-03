package org.lopatka.idonc.model.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.validator.NotNull;
import org.lopatka.idonc.model.user.IdoncUser;

@Entity
@Table(name = "PROJECTS", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IdoncProject implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "PROJECT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @Column(name = "WEBSITE")
    private String website;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "COMPUTATION_CLASS_NAME")
    private String computationClassName;

    @OneToMany
    @Cascade({CascadeType.ALL})
    @JoinTable(name= "PROJECT_PARTS_TO_PROCESS",
    		joinColumns = {@JoinColumn(name="PROJECT_ID")},
    		inverseJoinColumns = @JoinColumn(name = "PART_ID"))
    private List<IdoncPart> partsToProcess;

    @OneToMany
    @Cascade({CascadeType.ALL})
    @JoinTable(name = "PROJECT_PROCESSED_PARTS",
    		joinColumns = {@JoinColumn(name = "PROJECT_ID")},
    		inverseJoinColumns = @JoinColumn(name = "PART_ID"))
    private List<IdoncPart> processedParts;


    @OneToMany(cascade={javax.persistence.CascadeType.ALL}, fetch=FetchType.EAGER)
    @JoinColumn(name="PROJECT_ID")
    @Cascade(value=CascadeType.DELETE_ORPHAN)
    @IndexColumn(name="USER_POSITION")
    private List<IdoncUser> activeUsers;

    public IdoncProject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<IdoncPart> getPartsToProcess() {
        return partsToProcess;
    }

    public void setPartsToProcess(List<IdoncPart> partsToProcess) {
        this.partsToProcess = partsToProcess;
    }

    public List<IdoncPart> getProcessedParts() {
        return processedParts;
    }

    public void setProcessedParts(List<IdoncPart> processedParts) {
        this.processedParts = processedParts;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<IdoncUser> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(List<IdoncUser> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public void addActiveUser(IdoncUser activeUser) {
    	this.activeUsers.add(activeUser);
    }

	public String getComputationClassName() {
		return computationClassName;
	}

	public void setComputationClassName(String computationClassName) {
		this.computationClassName = computationClassName;
	}

}
