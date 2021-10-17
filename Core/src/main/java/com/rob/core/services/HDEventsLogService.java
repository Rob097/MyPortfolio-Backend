package com.rob.core.services;

import java.sql.SQLException;
import java.util.Calendar;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rob.core.models.HDEventsLog;
import com.rob.core.repositories.IHDEventsLogRepository;

@org.springframework.stereotype.Service
public class HDEventsLogService implements IHDEventsLogService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public HDEventsLogService() {
		super();
	}
	
	@Autowired
	private IHDEventsLogRepository hdEventsLogRepository;
	
	/*@Autowired
	private IOwnerService ownerService;*/
	
	/**
	 * Inserimento di un log
	 */
	@Override
	public HDEventsLog create(HDEventsLog data)  {
		Validate.notNull(data, "Parametro obbligatorio mancante: log.");
		if (data.getUserId() == null) {
			Integer userId = /*SecurityContextHolder.getContext().getAuthentication().getName();*/1;
			data.setUserId(userId);
		}
		if (data.getDate() == null) {
			data.setDate(Calendar.getInstance());
		}
		
		try {
			
			return hdEventsLogRepository._create(data);
			
		} catch (SQLException e) {
			log.error("Errore scrittura SYS_HD_LOG.",e);
			throw new RuntimeException("Errore scrittura SYS_HD_LOG.",e);
		}	
	}


}
