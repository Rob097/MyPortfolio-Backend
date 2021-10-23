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

import com.rob.core.database.UserSearchCriteria;
import com.rob.core.fetch.UserFetchHandler;
import com.rob.core.fetch.modules.Fetch;
import com.rob.core.fetch.modules.FetchBuilder;
import com.rob.core.models.User;
import com.rob.core.repositories.IUserRepository;
import com.rob.core.utils.db.Range;
import com.rob.core.utils.db.RangeUtils;
import com.rob.uiapi.controllers.views.IView;
import com.rob.uiapi.controllers.views.Normal;
import com.rob.uiapi.controllers.views.Synthetic;
import com.rob.uiapi.dto.mappers.UserRMapper;
import com.rob.uiapi.dto.models.UserQ;
import com.rob.uiapi.dto.models.UserR;
import com.rob.uiapi.utils.MetadataResource;
import com.rob.uiapi.utils.Sort;
import com.rob.uiapi.utils.UIApiConstants;
import com.rob.uiapi.utils.UIApiRS;
import com.rob.uiapi.utils.UIApiUtils;
import com.rob.uiapi.utils.messages.MessageResource;
import com.rob.uiapi.utils.messages.MessageResources;

import javassist.NotFoundException;


@RestController
@RequestMapping("/user")
public class UserRS implements UIApiRS<UserR, UserQ> {

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private UserRMapper userRMapper;
	
	/*@Autowired
	private UserMapper userMapper;*/
	
	private static final int MAX_RESULT_SIZE = 100;
	private static final int DEFAULT_RESULT_SIZE = 20;

	
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageResources<UserR>> find(
			UserQ parameters,
			@RequestParam(name = SORT, required = false) Sort sort,
			@RequestParam(name = VIEW, required = false, defaultValue = Normal.name) IView view, 
			@RequestHeader(name = RANGE, required = false) Range requestedRange
	) throws Exception {


		//Limitazione automatica dei risultati
		Range range = RangeUtils.limit(requestedRange, MAX_RESULT_SIZE, DEFAULT_RESULT_SIZE, UIApiConstants.RANGE_ITEMS_UNIT);

		UserSearchCriteria criteria = new UserSearchCriteria();
		//criteria.setFetch(applyViewCriteria(view));
		criteria.setRange(range);
		
		criteria = applySearchCriteria(parameters, criteria);
		Validate.isTrue(criteria.isValidCriteria(), "Almeno un parametro di ricerca deve essere valorizzato");
		
		List<User> list = userRepository.findByCriteria(criteria);
		List<UserR> result = list.stream().map(userRMapper::map).collect(Collectors.toList());

		return UIApiUtils.buildRangeAwareSuccessResponse(result, range, requestedRange);

	}

	@Override
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageResource<UserR>> get(
			@PathVariable("id") String id, 
			UserQ parameters, 
			@RequestParam(name = VIEW, required = false) IView view
	) throws Exception {
		Validate.notEmpty(id, "Parametro obbligatorio mancante: id.");
		
		User user = userRepository.findById(id, applyViewCriteria(view));
		
		if (user == null) {
			throw new NotFoundException("User con id " + id + " non trovato");
		}
		
		UserR userR = userRMapper.map(user);
		
		MessageResource<UserR> result = new MessageResource<UserR>(userR);
		
		return new ResponseEntity<MessageResource<UserR>>(result, HttpStatus.OK);
	}

	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageResource<UserR>> create(@RequestBody MetadataResource<UserR> resource) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	@PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageResource<UserR>> update(@PathVariable("id") String id, @RequestBody MetadataResource<UserR> resource) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResponseEntity<MessageResources<UserR>> deleteAll(UserQ parameters, IView view) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResponseEntity<MessageResource<UserR>> delete(String id, UserQ parameters, IView view) throws Exception {
		throw new UnsupportedOperationException();
	}
	
	private UserSearchCriteria applySearchCriteria(UserQ parameters, UserSearchCriteria criteria) {
		if (parameters == null) {
			return criteria;
		}
		
		if (StringUtils.isNotBlank(parameters.getId())) {
			criteria.setId(parameters.getId());
		}
		
		if (StringUtils.isNotBlank(parameters.getUsername())) {
			criteria.setUsername(parameters.getUsername());
		}
		
		return criteria;
		
	}
	
	/**
	 * Inizializzazione fetch
	 * @param view
	 * @return
	 */
	private Fetch applyViewCriteria(IView view) {
		if (view == null) {
			return FetchBuilder.none();
		}

		FetchBuilder fetchBuilder = new FetchBuilder();
		
		if (view.isAtLeast(Synthetic.value)) {
			fetchBuilder.addOption(UserFetchHandler.FETCH_ROLES_PERMISSIONS);
		}
		
		return fetchBuilder.build();
	}

}
