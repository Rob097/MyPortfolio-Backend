package com.rob.core.database;

import com.rob.core.models.HDEventsLog;
import com.rob.core.utils.db.PreparedStatementBuilder;
import com.rob.core.utils.db.QueryFactory;

public class HDEventsLogManagerQuery extends QueryFactory {

	/**
	 * Metodo per la ricerca dei dati tramite i criteri definiti
	 * @param criteria
	 * @return
	 */
	public PreparedStatementBuilder sqlFindByCriteria(HDEventsLogSearchCriteria criteria) {
		PreparedStatementBuilder psb = new PreparedStatementBuilder();
		//psb.setValidCriteria(criteria.isValidCriteria());
		
		if (criteria.getRange() != null) {
			psb.setRange(criteria.getRange());
		}

		psb.append(" SELECT ");
		
		if(criteria.isCount()) {
			psb.append(" COUNT(*) ");
		}else {		
			psb.append(getSqlFields(HDEventsLog.Field.values(), "L"));
		}		
		
		psb.append(" FROM SYS_HD_LOG L ");
		
		if(criteria.isJoinSysUser()) {
			psb.append(" INNER JOIN SYS_USERS U ");
			psb.append(" ON L.ENTRY_USER_ID = U.USER_ID");
		}
		
		psb.append(" WHERE 1=1 ");
		
		if (criteria.getLevelIds() != null && !criteria.getLevelIds().isEmpty()) {
			psb.append(" AND L.ERR_LEVEL IN ").addBindVariable("ERR_LEVEL", criteria.getLevelIds());
		}		
		
		if (criteria.getUserIds() != null && !criteria.getUserIds().isEmpty()) {
			psb.append(" AND L.ENTRY_USER_ID IN ").addBindVariable("ENTRY_USER_ID", criteria.getUserIds());
		}

		if (criteria.getFromDateTime() != null) {
			psb.append(" AND L.ENTRY_DATETIME >= ").addBindVariable("fromDate", criteria.getFromDateTime());
		}
		if (criteria.getToDateTime() != null) {
			psb.append(" AND L.ENTRY_DATETIME <= ").addBindVariable("toDate", criteria.getToDateTime());
		}
		
		if (criteria.getCodes() != null && !criteria.getCodes().isEmpty()) {
			psb.append(" AND L.CODE IN ").addBindVariable("CODE", criteria.getCodes());
		}
		
		if (criteria.getOwnerIds() != null && !criteria.getOwnerIds().isEmpty()) {
			psb.append(" AND L.OWNER_ID IN ").addBindVariable("OWNER_IDS", criteria.getOwnerIds());
		}

		return psb;
		
	}
	
	
	/**
	 * Metodo per l'inserimento del dato
	 * @param data
	 * @return
	 */
	public PreparedStatementBuilder sqlInsert(HDEventsLog data) {
		PreparedStatementBuilder psb = new PreparedStatementBuilder();
		
		psb.append(" INSERT INTO ").append(HDEventsLog.Table).append(" (");
		
		psb.append("  LOG_ID");
		psb.append(", CODE");
		psb.append(", TITLE");
		psb.append(", ENTRY_USER_ID");
		psb.append(", ENTRY_DATETIME");
		psb.append(", PAYLOAD");
		psb.append(", IDENTIFIER");
		psb.append(", OWNER_ID");
		
		psb.append(" ) VALUES ( ");
		
		psb.addBindVariable("LOG_ID", data.getId());
		psb.append(" , ").addBindVariable("CODE", data.getCode());
		psb.append(" , ").addBindVariableWithCase("TITLE", data.getTitle(), false);
		psb.append(" , ").addBindVariable("ENTRY_USER_ID", data.getUserId());
		psb.append(" , ").addBindVariable("ENTRY_DATETIME", data.getDate());
		psb.append(" , ").addBindVariableWithCase("PAYLOAD", data.getPayload(), false);
		psb.append(" , ").addBindVariableWithCase("IDENTIFIER", data.getIdentifier(), false);
		psb.append(" , ").addBindVariableWithCase("OWNER_ID", data.getOwnerId(), false);
		
		psb.append(" ) ");
		
		return psb;
		
	}
	
//	/**
//	 * Metodo per l'aggiornamento del dato
//	 * @param data
//	 * @return
//	 */
//	public PreparedStatementBuilder sqlUpdate(HDEventsLog data) {
//		PreparedStatementBuilder psb = new PreparedStatementBuilder();
//
//		psb.append(" UPDATE ").append(HDEventsLog.Table);
//		psb.append(" SET ");
//		psb.append(" LOG_ID = ").addBindVariable("LOG_ID", data.getId());;
//		
//		if (data.getLevel() != null) {
//			psb.append(" ,ERR_LEVEL = ").addBindVariable("ERR_LEVEL", data.getLevelId());
//		}
//		if (data.getCode() != null) {
//			psb.append(" ,CODE = ").addBindVariable("CODE", data.getCode());
//		}
//		if (data.getUserId() != null) {
//			psb.append(" ,ENTRY_USER_ID = ").addBindVariable("ENTRY_USER_ID", data.getUserId());
//		}
//		if (data.getDate() != null) {
//			psb.append(" ,ENTRY_DATETIME = ").addBindVariable("ENTRY_DATETIME", data.getDate());
//		}
//		if (data.getPayload() != null) {
//			psb.append(" ,PAYLOAD = ").addBindVariable("PAYLOAD", data.getPayload());
//		}
//
//		psb.append("  WHERE LOG_ID = ").addBindVariable("LOG_ID", data.getId());		
//		
//		return psb;
//	}
}
