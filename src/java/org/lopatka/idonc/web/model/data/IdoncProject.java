package org.lopatka.idonc.web.model.data;

import java.util.List;

public class IdoncProject {

	private int id;
	private String name;
	private String website;
	private String description;
	private List<IdoncPart> partsToProcess;
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
