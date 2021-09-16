package com.rob.uiapi.dto.models;

import java.time.LocalDate;
import java.util.Set;

public class HDEventsLogQ {
	private String id;
	private Set<Integer> levelIds;
	private Set<String> codeIds;
	private Set<String> userIds;
	private LocalDate fromDateTime;
	private LocalDate toDateTime;
	
	/**
	 * Identificativo log.
	 * @return
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	/**
	 * Filtra per livello errore:
	 * <ul>
	 * 		<li>0 = debug</li>
	 * 		<li>1 = info</li>
	 * 		<li>2 = warning</li>
	 * 		<li>3 = error</li>
	 * 		<li>4 = fatal</li>
	 * </ul>
	 * @return
	 */
	public Set<Integer> getLevelIds() {
		return levelIds;
	}

	public void setLevelIds(Set<Integer> levelIds) {
		this.levelIds = levelIds;
	}
	
	/** Filtro per code */
	public Set<String> getCodeIds() {
		return codeIds;
	}
	public void setCodeIds(Set<String> codeIds) {
		this.codeIds = codeIds;
	}
	
	/** Filtro per user */
	public Set<String> getUserIds() {
		return userIds;
	}
	public void setUserIds(Set<String> userIds) {
		this.userIds = userIds;
	}
	
	/**Filtro inizio periodo ricerca */
	public LocalDate getFromDateTime() {
		return fromDateTime;
	}
	public void setFromDateTime(LocalDate fromDateTime) {
		this.fromDateTime = fromDateTime;
	}
	
	/**Filtro fine periodo ricerca */
	public LocalDate getToDateTime() {
		return toDateTime;
	}
	public void setToDateTime(LocalDate toDateTime) {
		this.toDateTime = toDateTime;
	}
}
