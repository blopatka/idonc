package org.lopatka.idonc.web.model.data;

import java.util.List;

import org.lopatka.idonc.web.model.user.IdoncUser;

public class IdoncPart {

	private int id;
	private Long number;
	private String name;
	private List<IdoncLongData> longDataList;

	private List<IdoncResult> results;
	private List<IdoncUser> usersProcessing;
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

	// public byte[] getBlob() {
	// return blob;
	// }
	// public void setBlob(byte[] blob) {
	// this.blob = blob;
	// }
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
}
