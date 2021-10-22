package com.rob.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


public class Properties extends java.util.Properties{
	
	private static final long serialVersionUID = -4259272132135580295L;

	private ClassLoader loader = Thread.currentThread().getContextClassLoader();
	
	public Properties() {
		super();
	}
	
	public Properties(String name) {
		super();
		
		try (InputStream resourceStream = loader.getResourceAsStream(name)) {
		    this.load(resourceStream);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	
	/* CONSTANTS */
	public static String PATH_COOKIES = "/";
	//public static String ALLOWED_ORIGIN = "http://localhost:4200";
	public static String ALLOWED_ORIGIN = "https://myportfolio-6a771.web.app";
	
	public static String Id_AUTHORITIES = "authority";
	
	public static String CLAIM_KEY_AUTHORITIES = "roles";
	public static String USER_ID_ATTRIBUTE = "userId";
	public static String ENTITY_ID_ATTRIBUTE = "entity_id";
	
	public static String TOKEN_COOKIE_NAME = "token";
	public static String REMEMBER_COOKIE_NAME = "rememberMe";
	
	public static String OWNER_JOB = "Owner";
	
	//ROLES
	public static String ROLE_BASIC = "BASIC";
	public static String ROLE_ADMIN = "ADMIN";
	public static String ROLE_EDITOR = "EDITOR";
	
	public static List<String> ENCRYPT_EXCEPTIONS = Arrays.asList("_class", "_id", "password", "username", "roles", "rolesId", "roleName", "permissionsId");
	
	/*
	 
	  @Autowired
    public Properties(
    		@Value("${jwtExpirationMs}") int jwtExpirationMs,
    		@Value("${jwtExpirationMsRememberMe}") long jwtExpirationMsRememberMe,
    		@Value("${jwtSecret}") String jwtSecret,
    		@Value("${jwtHeader}") String tokenHeader,
    		@Value("${jwtConstant}") String tokenConstant,
    		@Value("${spring.datasource.host}") String domainNameCookies,
    		@Value("${spring.datasource.name}") String database,
    		@Value("${spring.datasource.url}") String connectionUri,
    		@Value("${spring.datasource.ssl}") String ssl,
    		@Value("${spring.datasource.username}") String connectionUsername,
    		@Value("${spring.datasource.password}") String connectionPassword,
    		@Value("${secret.enabled}") String encryptEnabled,
    		@Value("${secret.password}") String encryptPassword,
    		@Value("${secret.algorithm}") String encryptAlgorithm,
    		@Value("${secret.salt}") String encryptSalt,
    		@Value("${secret.iv}") String encryptIv 
    		) {
    	this.jwtExpirationMs = jwtExpirationMs;
    	this.jwtExpirationMsRememberMe = jwtExpirationMsRememberMe;
    	this.jwtSecret = jwtSecret;
    	this.tokenHeader = tokenHeader;
    	this.tokenConstant = tokenConstant;
    	this.domainNameCookies = "";//domainNameCookies;
    	this.database = "";//database;
    	this.connectionUri = connectionUri;
    	this.ssl = "";//ssl;
    	this.connectionUsername = "";//connectionUsername;
    	this.connectionPassword = "";//connectionPassword;
    	this.encryptEnabled = encryptEnabled;
    	this.encryptPassword = encryptPassword;
    	this.encryptAlgorithm = encryptAlgorithm;
		this.encryptSalt = encryptSalt;
		this.encryptIv = encryptIv;
    }
	  
	 */
}
