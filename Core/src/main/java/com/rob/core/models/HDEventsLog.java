package com.rob.core.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import com.rob.core.models.SYS.User;
import com.rob.core.utils.db.QueryFactory;
import com.rob.core.utils.java.ValueObject;
import com.rob.core.utils.java.WithID;

public class HDEventsLog extends ValueObject implements WithID<String> {

	public static final String Table = "SYS_HD_LOG";

	/** Campi previsti in tabella SYS_HD_LOG */
	public enum Field {

		// LOG_ID NUMBER(38,0) No
		LOG_ID("LOG_ID"),

		// TITLE VARCHAR2(1000) Yes
		TITLE("TITLE"),
				
		// CODE VARCHAR2(30) Yes
		CODE("CODE"),

		// ENTRY_USER_ID VARCHAR2(50) No
		ENTRY_USER_ID("ENTRY_USER_ID"),

		// ENTRY_DATETIME DATE No
		ENTRY_DATETIME("ENTRY_DATETIME"),

		// PAYLOAD VARCHAR2(4000) Yes
		PAYLOAD("PAYLOAD"),
		
		// IDENTIFIER VARCHAR2(1000) Yes
		IDENTIFIER("IDENTIFIER"),
		
		// OWNER_ID VARCHAR2(16 BYTE) Yes
		OWNER_ID("OWNER_ID");

		private String value;

		Field(String value) {
			this.value = value;
		}

		public String getValue(String prefix) {
			return QueryFactory.getFieldName(this, prefix);
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

	/** Identificativo record. */
	private String id;

	/** Codice tipologia evento. */
	private String code;

	/** Utente (Rif. SYS_USERS). */
	private User user;

	/** Data registrazione evento. */
	private Calendar date;

	/** Descrizione evento. */
	private String payload;
	
	/** Titolo log */
	private String title;
	
	/** Identificazione entita' evento. */
	private String identifier;
	
	/** Gestione multitenant. */
	private String ownerId;

	/** Costruttore oggetto */
	public HDEventsLog() {
		super();
	}
	
	
	/** Costruttore alternativo oggetto 
	 * @param code */
	public HDEventsLog(String code) {
		super();
		this.code = code;
	}



	/** Costuttore di classe partendo da ResultSet 
	 * @param rst 
	 * @throws SQLException */
	public HDEventsLog(ResultSet rst) throws SQLException {
		this(rst, "");
	}

	/** Costruttore oggetto dato resultSet 
	 * @param rst 
	 * @param prefix 
	 * @throws SQLException */
	public HDEventsLog(ResultSet rst, String prefix) throws SQLException {
		super();

		// LOG_ID NUMBER(38,0) No
		this.setId(rst.getString(Field.LOG_ID.getValue(prefix)));

		// CODE VARCHAR2(30) Yes
		this.setCode(rst.getString(Field.CODE.getValue(prefix)));

		// TITLE VARCHAR2(1000) Yes
		this.setTitle(rst.getString(Field.TITLE.getValue(prefix)));

		// ENTRY_DATETIME DATE No
		this.setDate(this.dateToCalendar(rst.getDate(Field.ENTRY_DATETIME.getValue(prefix))));

		// PAYLOAD VARCHAR2(4000) Yes
		this.setPayload(rst.getString(Field.PAYLOAD.getValue(prefix)));
		
		// IDENTIFIER VARCHAR2(1000) Yes
		this.setIdentifier(rst.getString(Field.IDENTIFIER.getValue(prefix)));
		
		// OWNER_ID VARCHAR2(16 BYTE) Yes
		this.setOwnerId(rst.getString(Field.OWNER_ID.getValue(prefix)));
	}
	
	/**COSTRUTTORE STATICO OGGETTO
	 * @param id 
	 * @return */
	public static HDEventsLog byId(String id) {
		if (id == null) {
			return null;
		}
		
		HDEventsLog eventLog = new HDEventsLog();
		eventLog.setId(id);
		
		return eventLog;
	}	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HDEventsLog other = (HDEventsLog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public User getUser() {
		return user;
	}
	public Integer getUserId() {
		if (user == null) {
			return null;
		}
		return user.getId();
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setUserId(Integer userId) {
		this.user = User.byId(userId);
	}
	

	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getPayload() {
		return payload;
	}
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

}
