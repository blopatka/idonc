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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "PROJECTS", uniqueConstraints = @UniqueConstraint(columnNames = {"NAME"}))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IdoncProject implements Serializable {

    private static final long serialVersionUID = 2588813544137432822L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "NAME")
    @NotNull
    private String name;
    @Column(name = "WEBSITE")
    private String website;
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany
    @JoinTable(name = "PROJECT_PARTS_TO_PROCESS",
    joinColumns = {@JoinColumn(name = "PROJECT_ID")},
    inverseJoinColumns = @JoinColumn(name = "PART_ID"))
    private List<IdoncPart> partsToProcess;
    @OneToMany
    @JoinTable(name = "PROJECT_PROCESSED_PARTS",
    joinColumns = {@JoinColumn(name = "PROJECT_ID")},
    inverseJoinColumns = @JoinColumn(name = "PART_ID"))
    private List<IdoncPart> processedParts;

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
}
