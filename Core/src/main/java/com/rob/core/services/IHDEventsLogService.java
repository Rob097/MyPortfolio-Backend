package com.rob.core.services;

import com.rob.core.models.HDEventsLog;

public interface IHDEventsLogService {

	/** Inserimento di un log */
	HDEventsLog create(HDEventsLog data);
	
}
