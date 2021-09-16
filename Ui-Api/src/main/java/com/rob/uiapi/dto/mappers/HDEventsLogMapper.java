package com.rob.uiapi.dto.mappers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.rob.core.models.HDEventsLog;
import com.rob.uiapi.dto.models.HDEventsLogR;
import com.rob.core.utils.java.IMapper;

@Component
public class HDEventsLogMapper implements IMapper<HDEventsLogR, HDEventsLog> {

	@Override
	public HDEventsLog map(HDEventsLogR input) {
		return this.map(input, null);
	}

	@Override
	public HDEventsLog map(HDEventsLogR input, HDEventsLog output) {
		if (input == null) {
			return null;
		}
		
		if (output == null) {
			output = new HDEventsLog(); 
		}
		
		//Setto i campi obbligatori
		output.setId(input.getId());
		//output.setDate(CoreUtils.toCalendar(input.getDate()));
		if(StringUtils.isNotBlank(input.getCode())) {
			output.setCode(input.getCode());
		}
		if(StringUtils.isNotBlank(input.getPayload())) {
			output.setPayload(input.getPayload());
		}
		if(StringUtils.isNotBlank(input.getOwnerId())) {
			output.setOwnerId(input.getOwnerId());
		}
		
		return output;
		
	}

}
