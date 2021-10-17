package com.rob.core.database;

import org.apache.commons.lang3.StringUtils;

import com.rob.core.models.User;
import com.rob.core.utils.db.PreparedStatementBuilder;
import com.rob.core.utils.db.QueryFactory;

public class UserManagerQuery extends QueryFactory {

	/**
	 * Metodo per la ricerca dei dati tramite i criteri definiti
	 * @param criteria
	 * @return
	 */
	public PreparedStatementBuilder sqlFindByCriteria(UserSearchCriteria criteria) {
		PreparedStatementBuilder psb = new PreparedStatementBuilder();
		//psb.setValidCriteria(criteria.isValidCriteria());
		
		if (criteria.getRange() != null) {
			psb.setRange(criteria.getRange());
		}

		psb.append(" SELECT ");
		
		if(criteria.isCount()) {
			psb.append(" COUNT(*) ");
		}else {		
			psb.append(getSqlFields(User.Field.values(), "USR"));
		}		
		
		psb.append(" FROM " + User.Table + " USR ");
		
		/*if(criteria.isJoinSysUser()) {
			psb.append(" INNER JOIN SYS_USERS U ");
			psb.append(" ON L.ENTRY_USER_ID = U.USER_ID");
		}*/
		
		psb.append(" WHERE 1=1 ");
		
		if (StringUtils.isNoneBlank(criteria.getId())) {
			psb.append(" AND USR.ID = ").addBindVariable("USER_ID", criteria.getId());
		}

		if (StringUtils.isNoneBlank(criteria.getUsername())) {
			psb.append(" AND USR.USERNAME = ").addBindVariable("USERNAME", criteria.getUsername());
		}
		
		if (StringUtils.isNoneBlank(criteria.getEmail())) {
			psb.append(" AND USR.EMAIL = ").addBindVariable("EMAIL", criteria.getEmail());
		}

		return psb;
		
	}
	
	/**
	 * Metodo per l'inserimento del dato
	 * @param data
	 * @return
	 */
	public PreparedStatementBuilder sqlCreate(User data) {
		PreparedStatementBuilder psb = new PreparedStatementBuilder();
		
		psb.append(" INSERT INTO ").append(User.Table).append(" (");
		
		psb.append("  ID");
		psb.append(", USERNAME");
		psb.append(", PASSWORD");
		psb.append(", EMAIL");
		psb.append(", NAME");
		psb.append(", SURNAME");
		
		psb.append(" ) VALUES ( ");
		
		psb.addBindVariable("ID", data.getId());
		psb.append(" , ").addBindVariable("USERNAME", data.getUsername());
		psb.append(" , ").addBindVariableWithCase("PASSWORD", data.getPassword(), false);
		psb.append(" , ").addBindVariable("EMAIL", data.getEmail());
		psb.append(" , ").addBindVariable("NAME", data.getName());
		psb.append(" , ").addBindVariable("SURNAME", data.getSurname());
		
		psb.append(" ) ");
		
		return psb;
		
	}
	
	/**
	 * Metodo per l'inserimento del dato
	 * @param userId 
	 * @param roleId 
	 * @param data
	 * @return
	 */
	public PreparedStatementBuilder sqlCreateRoleRelation(int userId, int roleId) {
		PreparedStatementBuilder psb = new PreparedStatementBuilder();
		
		psb.append(" INSERT INTO ").append("user_roles").append(" (");
		
		psb.append("  USER_ID");
		psb.append(", ROLE_ID");
		
		psb.append(" ) VALUES ( ");
		
		psb.addBindVariable("USER_ID", userId);
		psb.append(" , ").addBindVariable("ROLE_ID", roleId);
		
		psb.append(" ) ");
		
		return psb;
		
	}
	
}
