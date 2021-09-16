package com.rob.core.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.rob.core.database.HDEventsLogManagerQuery;
import com.rob.core.database.HDEventsLogSearchCriteria;
import com.rob.core.models.HDEventsLog;
import com.rob.core.utils.db.PreparedStatementBuilder;

@Repository
public class HDEventsLogRepository implements IHDEventsLogRepository {
	
	/*@Autowired
	private IModuleService sysModuleService;
	
	@Autowired 
	private IOwnerService ownerService;*/
	
	@Autowired
	private DataSource dataSource;
	
	/*@Autowired
	private HDEventsLogFetchHandler fetchHandler;*/
	
	private final HDEventsLogManagerQuery queryFactory;
	
	public HDEventsLogRepository() {
		queryFactory = new HDEventsLogManagerQuery();
	}
	

	/**
	 * Ricerca log per id
	 
	@Override
	public HDEventsLog findById(String id) throws SQLException {
		Validate.notEmpty(id, "Parametro obbligatorio mancante: identificativo log.");

		return this.findById(id, FetchBuilder.none());
	}*/

	/**
	 * Ricerca log per id (con fetch)
	 */
	@Override
	public HDEventsLog findById(String id/*, Fetch fetch*/) throws SQLException {
		Validate.notEmpty(id, "Parametro obbligatorio mancante: identificativo log.");
		
		HDEventsLogSearchCriteria criteria = new HDEventsLogSearchCriteria();
		criteria.setId(id);
		//criteria.setFetch(fetch);
		
		return this.findSingleByCriteria(criteria);
	}

	/**
	 * Ricerca singolo log per i criteri definiti
	 */
	@Override
	public HDEventsLog findSingleByCriteria(HDEventsLogSearchCriteria criteria) throws SQLException {
		Validate.notNull(criteria, "Parametro obbligatorio mancante.");
		if(criteria.getRange() == null) {
			criteria.setMaxRows(1);
		}
		List<HDEventsLog> results = this.findByCriteria(criteria);
		if (results != null && !results.isEmpty()) {
			return results.iterator().next();
		}
		
		return null;
	}

	/**
	 * Ricerca log per i criteri definiti
	 */
	@Override
	public List<HDEventsLog> findByCriteria(HDEventsLogSearchCriteria criteria) throws SQLException {
		Validate.notNull(criteria, "Parametro obbligatorio mancante.");
		List<HDEventsLog> results = new ArrayList<>();
		//Fetch fetch = criteria.getFetch();
		
		/*if (SYSGeneralOptionEnum.MULTI_TENANT_ENABLED.getValueAsBoolean()) {
			criteria.setOwnerId(ownerService.getLoggedUserOwnerId());
		}*/
		
		try 
		(PreparedStatementBuilder bld = queryFactory.sqlFindByCriteria(criteria))
		{
			Connection conn = DataSourceUtils.getConnection(dataSource);
			ResultSet rst = bld.executeQuery(conn);
			
			while(rst.next()) {
				HDEventsLog log = new HDEventsLog(rst);
				//fetchHandler.handle(log, fetch, rst);
				results.add(log);
			}	
		}
		
		return results;
	}

	/**
	 * Inserimento log
	 */
	@Override
	public HDEventsLog _create(HDEventsLog data) throws SQLException {
		Validate.notNull(data, "Oggetto non valido.");
		Validate.notNull(data.getUserId(), "Parametro obbligatorio mancante: Identificativo utente");
		Validate.notNull(data.getDate(), "Parametro obbligatorio mancante: Data registrazione log");
		//Validate.notNull(data.getLevel(), "Parametro obbligatorio mancante: livello evento");
		
		/*String id = this.sysModuleService.getCounterMaster(SYSCounterEnum.LOG_ID.getId(), 100);
		data.setId(id);*/		
		
		try 
		(PreparedStatementBuilder psb = this.queryFactory.sqlInsert(data)) 
		{
			Connection con = DataSourceUtils.getConnection(dataSource);
			psb.executeUpdate(con);
		}
		
		return data;
	}

//	/**
//	 * Aggiornamento log
//	 */
//	@Override
//	public HDEventsLog _update(HDEventsLog data) throws SQLException {
//		Validate.notNull(data, "Oggetto non valido.");
//		Validate.notNull(data.getId(), "Parametro obbligatorio mancante: identificativo log.");
//		Validate.notNull(data.getUserId(), "Parametro obbligatorio mancante: Identificativo utente");
//		Validate.notNull(data.getDate(), "Parametro obbligatorio mancante: Data registrazione log");
//		
//		try 
//		(PreparedStatementBuilder psb = this.queryFactory.sqlUpdate(data)) 
//		{
//			Connection con = DataSourceUtils.getConnection(dataSource);
//			psb.executeUpdate(con);
//		}
//		
//		return data;
//	}

}
