package com.rob.uiapi.dto.mappers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.rob.core.models.HDEventsLog;
import com.rob.uiapi.dto.models.HDEventsLogR;
import com.rob.core.utils.java.IMapper;

@Component
public class HDEventsLogRMapper implements IMapper<HDEventsLog, HDEventsLogR> {

	@Override
	public HDEventsLogR map(HDEventsLog input) {
		return this.map(input, null);
	}

	@Override
	public HDEventsLogR map(HDEventsLog input, HDEventsLogR output) {
		if (input == null) {
			return null;
		}
		
		if (output == null) {
			output = new HDEventsLogR();
		}
		
		//Setto i parametri obbligatori
		output.setId(input.getId());
		
		//Setto i campi non obbligatori
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
