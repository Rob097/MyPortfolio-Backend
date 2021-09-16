package com.rob.uiapi.dto.models;

import java.time.LocalDate;


public class HDEventsLogR {
	private String id;
	private String code;
	private LocalDate date;
	private String payload;
	private String identifier;
	private String ownerId;
	
	
	
	
	/**
	 * Identificativo log.
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Codice tipologia log.
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Data di registrazione del log.
	 * 
	 * @return
	 */
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	/**
	 * Descrizione evento del log.
	 * 
	 * @return
	 */
	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	/**
	 * Identificatore dell'entita' del log.
	 * 
	 * @return
	 */
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	/**
	 * Identificatore dell'owner (multi-tenant).
	 * 
	 * @return
	 */
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}	
}
