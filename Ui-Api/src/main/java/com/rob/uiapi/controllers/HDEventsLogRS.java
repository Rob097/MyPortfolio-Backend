package com.rob.uiapi.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rob.core.database.HDEventsLogSearchCriteria;
import com.rob.core.models.HDEventsLog;
import com.rob.core.repositories.IHDEventsLogRepository;
import com.rob.core.services.IHDEventsLogService;
import com.rob.uiapi.controllers.views.IView;
import com.rob.uiapi.controllers.views.Normal;
import com.rob.uiapi.dto.mappers.HDEventsLogMapper;
import com.rob.uiapi.dto.mappers.HDEventsLogRMapper;
import com.rob.uiapi.dto.models.HDEventsLogQ;
import com.rob.uiapi.dto.models.HDEventsLogR;
import com.rob.uiapi.utils.MetadataResource;
import com.rob.uiapi.utils.Sort;
import com.rob.uiapi.utils.UIApiConstants;
import com.rob.uiapi.utils.UIApiRS;
import com.rob.uiapi.utils.UIApiUtils;
import com.rob.uiapi.utils.messages.MessageResource;
import com.rob.uiapi.utils.messages.MessageResources;
import com.rob.core.utils.db.Range;
import com.rob.core.utils.db.RangeUtils;
import com.rob.core.utils.java.IntegerList;
import com.rob.core.utils.java.StringList;

import javassist.NotFoundException;

@RestController
@RequestMapping("/hdEventsLog")
public class HDEventsLogRS implements UIApiRS<HDEventsLogR, HDEventsLogQ> {

	@Autowired
	private IHDEventsLogRepository hdEventsLogRepository;
	
	@Autowired
	private IHDEventsLogService hdEventsLogService;
	
	@Autowired
	private HDEventsLogRMapper rMapper;
	
	@Autowired
	private HDEventsLogMapper mapper;
	
	private static final int MAX_RESULT_SIZE = 100;
	private static final int DEFAULT_RESULT_SIZE = 20;
	
	
	
	/**
	 * Ricerca log evento in base ai criteri specificati
	 * 
	 * @param parameters I criteri di ricerca
	 * @param sort I criteri di ordinamento
	 * @param view I criteri di verbosità
	 * @params requestedRange I criteri di paginazione
	 * @return L'elenco dei log eventi che soddisfano i criteri di ricerca.
	 * @throws Exception In caso di errore
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<MessageResources<HDEventsLogR>> find(
			HDEventsLogQ parameters,
			@RequestParam(name = SORT, required = false) Sort sort,
			@RequestParam(name = VIEW, required = false, defaultValue = Normal.name) IView view, 
			@RequestHeader(name = RANGE, required = false) Range requestedRange
		) throws Exception {
		
		//Limitazione automatica dei risultati
		Range range = RangeUtils.limit(requestedRange, MAX_RESULT_SIZE, DEFAULT_RESULT_SIZE, UIApiConstants.RANGE_ITEMS_UNIT);
		//Range esteso per avere un risultato in più di quanto richiesto
		Range extendedRange = RangeUtils.extend(range);
		
		
		HDEventsLogSearchCriteria criteria = new HDEventsLogSearchCriteria();
		//criteria.setFetch(applyViewCriteria(view));
		criteria.setRange(extendedRange);
		
		criteria = applySearchCriteria(parameters, criteria);
		//Validate.isTrue(criteria.isValidCriteria(), "Almeno un parametro di ricerca deve essere valorizzato");
		
		List<HDEventsLog> list = hdEventsLogRepository.findByCriteria(criteria);
		List<HDEventsLogR> result = list.stream().map(rMapper::map).collect(Collectors.toList());

		return UIApiUtils.buildRangeAwareSuccessResponse(result, range, requestedRange);
		
	}

	/**
	 * Ricerca un log in base all'id
	 * 
	 * @param id Identificativo budget.
	 * @return Il log cercato in base all'id indicato; viene wrappato all'interno di un {@link MessageResource} e sarà quindi accessibile nella proprietà <code>content</code>
	 * 
	 * @throws Exception in caso di errore
	 */
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<MessageResource<HDEventsLogR>> get(
			@PathVariable("id") String id, 
			HDEventsLogQ parameters, 
			@RequestParam(name = VIEW, required = false) IView view
		)throws Exception {
		
		Validate.notEmpty(id, "Parametro obbligatorio mancante: id.");
		
		HDEventsLog dataDB = hdEventsLogRepository.findById(id/*, applyViewCriteria(view)*/);
		
		if (dataDB == null) {
			throw new NotFoundException("Log con id " + id + " non trovato");
		}
		
		HDEventsLogR logR = rMapper.map(dataDB);
		
		MessageResource<HDEventsLogR> result = new MessageResource<HDEventsLogR>(logR);
		
		return new ResponseEntity<MessageResource<HDEventsLogR>>(result, HttpStatus.OK);

	}

	/**
	 * Crea nuovo log evento
	 * 
	 * @param resource L'oggetto che rappresenta il log da salvare
	 * 
	 * @throws Exception in caso di errore
	 */
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<MessageResource<HDEventsLogR>> create(@RequestBody MetadataResource<HDEventsLogR> resource) throws Exception {

		Validate.notNull(resource, "Nessuna risorsa passata come parametro");
		HDEventsLogR input = resource.getContent();
		Validate.notNull(input, "Nessuna risorsa passata come parametro nel body");
		
		HDEventsLog data = mapper.map(input);
			
		data = hdEventsLogService.create(data);
		
		if (data != null) {
			MessageResource<HDEventsLogR> result = new MessageResource<>(rMapper.map(data));
			return new ResponseEntity<MessageResource<HDEventsLogR>>(result, HttpStatus.OK);
		}
		
		//throw new UserReadableException("Errore durante l'inserimento del log.");
		throw new Exception("Errore durante l'inserimento del log.");
		
	}

	/**
	 * Aggiorno un log evento
	 * 
	 * @param resource L'oggetto che rappresenta il log da aggiornare
	 * 
	 * @throws Exception in caso di errore
	 */
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public ResponseEntity<MessageResource<HDEventsLogR>> update(
			@PathVariable("id") String id,
			@RequestBody MetadataResource<HDEventsLogR> resource
		)throws Exception {

		//UPDATE DEI LOGS NON PREVISTO
		throw new UnsupportedOperationException();
		
//		Validate.notNull(resource, "Nessuna risorsa passata come parametro");
//		HDEventsLogR input = resource.getContent();
//		Validate.notNull(input, "Nessuna risorsa passata come parametro nel body");
//		
//		HDEventsLog data = mapper.map(input);
//			
//		data = HDEventsLogService.update(data);
//		
//		if (data != null) {
//			MessageResource<HDEventsLogR> result = new MessageResource<>(rMapper.map(data));
//			return new ResponseEntity<MessageResource<HDEventsLogR>>(result, HttpStatus.OK);
//		}
//		
//		throw new UserReadableException("Errore durante l'aggiornamento del log.");
		
	}

	/**
	 * Metodo non supportato
	 */
	@Override
	public ResponseEntity<MessageResources<HDEventsLogR>> deleteAll(HDEventsLogQ parameters, IView view)
			throws Exception {
		throw new UnsupportedOperationException();
	}

	/**
	 * Metodo non supportato
	 */
	@Override
	public ResponseEntity<MessageResource<HDEventsLogR>> delete(String id, HDEventsLogQ parameters, IView view)
			throws Exception {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Inizializzazione criteri di ricerca
	 * @param parameters
	 * @param criteria
	 * @return
	 */
	private HDEventsLogSearchCriteria applySearchCriteria(HDEventsLogQ parameters, HDEventsLogSearchCriteria criteria) {
		if (parameters == null) {
			return criteria;
		}
		
		if (StringUtils.isNotBlank(parameters.getId())) {
			criteria.setId(parameters.getId());
		}
		
		/*if (parameters.getFromDateTime() != null) {
			criteria.setFromDateTime(CoreUtils.toCalendar(parameters.getFromDateTime()));
		}
		
		if (parameters.getToDateTime() != null) {
			criteria.setToDateTime(CoreUtils.toCalendar(parameters.getToDateTime()));
		}*/
		
		if (parameters.getLevelIds() != null && !parameters.getLevelIds().isEmpty()) {
			criteria.setLevelIds(new IntegerList(parameters.getLevelIds()));
		}
		
		if (parameters.getCodeIds() != null && !parameters.getCodeIds().isEmpty()) {
			criteria.setCodes(new StringList(parameters.getCodeIds()));
		}
		
		if (parameters.getUserIds() != null && !parameters.getUserIds().isEmpty()) {
			criteria.setUserIds(new StringList(parameters.getUserIds()));
		}
		
		return criteria;
	}
	
	/**
	 * Inizializzazione fetch
	 * @param view
	 * @return
	 
	private Fetch applyViewCriteria(IView view) {
		if (view == null) {
			return FetchBuilder.none();
		}

		FetchBuilder fetchBuilder = new FetchBuilder();
		
		if (view.isAtLeast(Synthetic.value)) {
			fetchBuilder.addOption(HDEventsLogFetchHandler.FETCH_USER);
		}
		
		return fetchBuilder.build();
	}*/

}
