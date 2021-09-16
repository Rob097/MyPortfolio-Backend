package com.rob.core.repositories;

import java.sql.SQLException;
import java.util.List;

import com.rob.core.database.HDEventsLogSearchCriteria;
import com.rob.core.models.HDEventsLog;

public interface IHDEventsLogRepository {

	/** Ricerca log per id */
	HDEventsLog findById(String id) throws SQLException;
	/** Ricerca log per id (con fetch) */
	//HDEventsLog findById(String id, Fetch fetch) throws SQLException;
	/** Ricerca singolo log per i criteri definiti */
	HDEventsLog findSingleByCriteria(HDEventsLogSearchCriteria criteria) throws SQLException;
	/** Ricerca log per i criteri definiti */
	List<HDEventsLog> findByCriteria(HDEventsLogSearchCriteria criteria) throws SQLException;
	
	
	/** Inserimento log */
	HDEventsLog _create(HDEventsLog data) throws SQLException;
//	/** Aggiornamento log */
//	HDEventsLog _update(HDEventsLog data) throws SQLException;
	
}
