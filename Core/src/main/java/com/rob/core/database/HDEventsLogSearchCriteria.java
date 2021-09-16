package com.rob.core.database;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.rob.core.utils.db.Range;
import com.rob.core.utils.java.IntegerList;
import com.rob.core.utils.java.StringList;
import com.rob.core.utils.java.ValueObject;

public class HDEventsLogSearchCriteria extends ValueObject {

	/**Limitatore risultati*/
	private Range range;
	//private Fetch fetch;
	
	/**Filtri*/	
	private String id;
	/**Filtro per scegliere se richiedere i valori o il numero di record*/
	private boolean isCount = false;
	/**Filtro per scegiliere il livello di errore*/
	private IntegerList levelIds;
	/**Filtro per codice evento*/
	private StringList codes;
	/**Filtro per id utente*/
	private StringList userIds;

	/** Data ed ora inizio periodo ricerca */
	private Calendar fromDateTime;

	/**  Data ed ora fine periodo ricerca  */
	private Calendar toDateTime;
	
	/**Filtro per ownerId (multi-tenant)*/
	private StringList ownerIds;
	
	
	/**GETTER e SETTER: Limitatore risultati
	 * @return */

	public Range getRange() {
		return range;
	}
	public void setRange(Range range) {
		this.range = range;
	}
	public void setMaxRows(Integer maxRows) {
		if (maxRows==null || maxRows <= 0) {
			this.range = null;
		} else {
			this.range = new Range(Range.ROWS, 0, maxRows - 1);
		}
	}

	/*public Fetch getFetch() {
		if(fetch==null) {
			return FetchBuilder.none();
		}
		return fetch;
	}
	public void setFetch(Fetch fetch) {
		this.fetch = fetch;
	}*/
	
	
	/** JOIN 
	 * @return */
	public boolean isJoinSysUser() {
		if (this.userIds != null && !this.userIds.isEmpty()) {
			return true;
		}

		return false;
	}
	
	public boolean isValidCriteria() {
		if (StringUtils.isNotBlank(id)) {
			return true;
		}
		if (this.fromDateTime!=null && (this.codes!=null && this.codes.isNotEmpty())) {
			return true;
		}
		if (this.fromDateTime!=null && this.toDateTime!=null) {
			return true;
		}
		if (this.userIds!=null && !this.userIds.isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	
	/**GETTER e SETTER: Filtri
	 * @return */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	
	public boolean isCount() {
		return isCount;
	}
	public void setCount(boolean isCount) {
		this.isCount = isCount;
	}
	
	
	public IntegerList getLevelIds() {
		return levelIds;
	}
	public void setLevelIds(IntegerList levelIds) {
		this.levelIds = levelIds;
	}
	public void setLevelId(Integer levelId) {
		levelIds = null;
		if (levelId != null) {
			levelIds = new IntegerList();
			levelIds.add(levelId);
		}
	}
	
	
	public StringList getCodes() {
		return codes;
	}
	public void setCodes(StringList codes) {
		this.codes = codes;
	}
	public void setCode(String code) {
		if (code == null) {
			this.codes = null;
		}
		this.codes = new StringList();
		this.codes.add(code);
	}
	
	
	public StringList getUserIds() {
		return userIds;
	}
	public void setUserIds(StringList userIds) {
		this.userIds = userIds;
	}
	public void setUserId(String userId) {
		if (userId == null) {
			this.userIds = null;
		}
		this.userIds = new StringList();
		this.userIds.add(userId);
	}
	
	
	public Calendar getFromDateTime() {
		return fromDateTime;
	}
	public void setFromDateTime(Calendar fromDateTime) {
		this.fromDateTime = fromDateTime;
	}
	public void setFromDateTime(Date fromDateTime) {
		setFromDateTime(dateToCalendar(fromDateTime));
	}
	
	public Calendar getToDateTime() {
		return toDateTime;
	}
	public void setToDateTime(Calendar toDateTime) {
		this.toDateTime = toDateTime;
	}
	public void setToDateTime(Date toDateTime) {
		setToDateTime(dateToCalendar(toDateTime));
	}
	
	public StringList getOwnerIds() {
		return ownerIds;
	}
	public void setOwnerIds(StringList ownerIds) {
		this.ownerIds = ownerIds;
	}
	public void setOwnerId(String ownerIds) {
		if (ownerIds == null) {
			this.ownerIds = null;
		}
		this.ownerIds = new StringList();
		this.ownerIds.add(ownerIds);
	}
	

}
