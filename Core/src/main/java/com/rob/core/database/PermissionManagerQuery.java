package com.rob.core.database;

import com.rob.core.models.Permission;
import com.rob.core.utils.db.PreparedStatementBuilder;
import com.rob.core.utils.db.QueryFactory;

public class PermissionManagerQuery extends QueryFactory {

	/**
	 * Metodo per la ricerca dei dati tramite i criteri definiti
	 * @param criteria
	 * @return
	 */
	public PreparedStatementBuilder sqlFindByCriteria(PermissionSearchCriteria criteria) {
		PreparedStatementBuilder psb = new PreparedStatementBuilder();
		//psb.setValidCriteria(criteria.isValidCriteria());
		
		if (criteria.getRange() != null) {
			psb.setRange(criteria.getRange());
		}

		psb.append(" SELECT ");
		
		if(criteria.isCount()) {
			psb.append(" COUNT(*) ");
		}else {		
			psb.append(getSqlFields(Permission.Field.values(), "PRM"));
		}		
		
		psb.append(" FROM " + Permission.Table + " PRM ");
		
		/*if(criteria.isJoinSysUser()) {
			psb.append(" INNER JOIN SYS_USERS U ");
			psb.append(" ON L.ENTRY_USER_ID = U.USER_ID");
		}*/
		
		psb.append(" WHERE 1=1 ");
		
		if (criteria.getIds()!= null && !criteria.getIds().isEmpty()) {
			psb.append(" AND PRM.ID IN ").addBindVariable("PERMISSION_ID", criteria.getIds());
		}

		/*if (StringUtils.isNoneBlank(criteria.getRoleId())) {
			psb.append(" AND USR.USERNAME = ").addBindVariable("ROLE_ID", criteria.getRoleId());
		}*/

		return psb;
		
	}
	
	/**
	 * Metodo per l'inserimento del dato
	 * @param data
	 * @return
	 */
	public PreparedStatementBuilder sqlCreate(Permission data) {
		PreparedStatementBuilder psb = new PreparedStatementBuilder();
		
		psb.append(" INSERT INTO ").append(Permission.Table).append(" (");
		
		psb.append("  ID");
		psb.append(", NAME");
		psb.append(", DESCRIPTION");
		
		psb.append(" ) VALUES ( ");
		
		psb.addBindVariable("ID", data.getId());
		psb.append(" , ").addBindVariable("NAME", data.getName());
		psb.append(" , ").addBindVariable("DESCRIPTION", data.getDescription());
		
		psb.append(" ) ");
		
		return psb;
		
	}
	
}
