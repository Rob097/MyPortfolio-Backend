package com.rob.core.utils.java;

import static java.lang.Math.abs;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;

import com.rob.core.utils.db.SortEnum;


/**
 * Libreria di funzioni di utilita' generale (fra cui conversione e formattazione
 * di date e di numeri, etc.).
 */
@SuppressWarnings("javadoc")
public class Commons {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(Commons.class);
	
	/** Minima data consentita */
	public static final String SYS_DATAMIN = "01/01/1900";

	/** Massima data consentita */
	public static final String SYS_DATAMAX = "31/12/2999";

	/** Minima data/ora consentita */
	public static final String SYS_LONGDATAMIN = "01/01/1900 00:00:00";

	/** Massima data/ora consentita */
	public static final String SYS_LONGDATAMAX = "31/12/2999 23:59:59";

	/** Formato data standard (dd/MM/yyyy) */
	public static final String FORMAT_DATE = "dd/MM/yyyy";

	/** Formato data breve (dd/MM/yy) */
	public static final String FORMAT_SIMPLE_DATE = "dd/MM/yy";
	
	
	/** Formato data breve (dd-MM-yy) */
	public static final String FORMAT_SIMPLE_DATE_2 = "dd-MM-yy";
	/** Formato data breve (dd-MM-yyyy) */
	public static final String FORMAT_SIMPLE_DATE_3 = "dd-MM-yyyy";

	/** Formato ora standard (HH:mm:ss) */
	public static final String FORMAT_TIME = "HH:mm:ss";

	/** Formato ora breve (HH:mm) */
	public static final String FORMAT_SIMPLE_TIME = "HH:mm";

	/** Formato data/ora standard (dd/MM/yyyy HH:mm:ss) */
	public static final String FORMAT_DATETIME = FORMAT_DATE + " " + FORMAT_TIME;

	/** Formato data/ora breve (dd/MM/yyyy HH:mm) */
	public static final String FORMAT_SIMPLE_DATETIME = FORMAT_DATE + " " + FORMAT_SIMPLE_TIME;

	/** Formato data standard HL7 (yyyyMMdd) */
	public static final String FORMAT_DATE_HL7 = "yyyyMMdd";

	/** Formato data/ora standard HL7 (yyyyMMddHHmm) */
	public static final String FORMAT_DATETIME_HL7 = "yyyyMMddHHmm";
	
	/** Formato data (yyyy-MM-dd) */
	public static final String FORMAT_DATE_MINUS_SEPARATOR = "yyyy-MM-dd";
	
	/** Formato data (yyyy-MM-dd HH:mm:ss) */
	public static final String FORMAT_DATE_TIME_MINUS_SEPARATOR = FORMAT_DATE_MINUS_SEPARATOR + " " + FORMAT_TIME;
	
	/** Formato data/ora standard HL7 (yyyyMMddHHmmss) */
	public static final String FORMAT_FULLDATETIME_HL7 = "yyyyMMddHHmmss";

	/** Formato data/ora standard HL7 (ddMMyyyyHHmmss) */
	public static final String FORMAT_FULLDATETIME = "ddMMyyyyHHmmss";
	
	public static final String FORMAT_FULLDATETIME_DEMA = "yyyyMMdd HHmmss";
	
	/** Formato anno 4 cifre */
	public static final String FORMAT_YEAR = "yyyy";
	
	/** Label del token per il recupero delle UO di appartenza dell'utente **/
	public static final String SERVICE_LABEL_UE_APPARTENENZA = "APPARTENENZA";
	
	/** Label del token per il recupero delle UO di afferenza dell'utente in caso si tratti di responsabile di dipartimento **/
	public static final String SERVICE_LABEL_UE_AFFERENZA = "AFFERENZA";

	/** Charset (as string) attualmente utilizzato nell'applicazione*/
	public static final String CURRENT_ENCODING = "ISO-8859-1";
	
	/** Charset attualmente utilizzato nell'applicazione */
	public static final Charset CURRENT_CHARSET = Charset.forName(CURRENT_ENCODING);
	
	/** Charset UTF-8 */
	public static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");
	/** Colors */
	public static final String BOOKED_IMAGE ="core/images/blu.gif";
	public static final String BOOKED_COLOR ="BBCCFF";
	
	public static final String ERROR_IMAGE ="core/images/red.gif";
	public static final String ERROR_COLOR ="FFB8A1";
	
	public static final String WARNING_IMAGE ="core/images/yellow2.gif";
	public static final String WARNING_COLOR ="FFFFBF";
	
	public static final String DELETED_IMAGE ="core/images/red.gif";
	public static final String DELETED_COLOR ="FFB8A1";
	
	public static final String SUSPENDED_IMAGE ="core/images/gray.gif";
	public static final String SUSPENDED_COLOR ="E0E0E0";
	
	public static final String EROGATED_IMAGE ="core/images/green.gif";
	public static final String EROGATED_COLOR ="CCFFBB";
	
	public static final String MOVED_IMAGE ="core/images/blue.gif";
	public static final String MOVED_COLOR ="3776CB";
	
	public static final String NOT_EROGATED_IMAGE ="core/images/yellow2.gif";
	public static final String NOT_EROGATED_COLOR ="FFFFBF";

	public static final String NOT_CONFIRMED_IMAGE ="core/images/orange.gif";
	public static final String NOT_CONFIRMED_COLOR ="FFB600";
	
	public static final String WL_BOOKING_ACTIVE_IMAGE="core/images/violet.gif";
	public static final String WL_BOOKING_ACTIVE_COLOR="A020F0";
	
	public static final String WL_BOOKING_DELETED_IMAGE="core/images/red_locked.gif";
	public static final String WL_BOOKING_DELETED_COLOR="FF0000";
	
	public static final String PARTIALLY_EROGATED_IMAGE ="core/images/dark_blue.gif";
	public static final String PARTIALLY_EROGATED_COLOR ="0000CC";
	
	public static final String YELLOW_WARNING_IMAGE = "core/images/yellow_warning.gif";
	public static final String YELLOW_WARNING = "f9d74d";
	
	/** Unita' di misura per il risultato della funzione dateDiff */
	public enum DateDiffUnit {
		SECOND, MINUTE, HOUR, DAY, FULLDAY
	}
	
	public static final String JSON_EMPTY = "{}";
	
	public static final String[] TRUE_VALUES = new String[] {"TRUE","1","SI","S","YES","Y"};
	
	
	/**
	 * AC Fecit 12/06/2014
	 * In seguito alla modifica del token, causa interfacciamento AOUI VERONA
	 * 
	 * Questo metodo si occupa di verificare una stringa sia numerica o meno
	 * @param value
	 * @return True nel cso in cui stringa numerica, False altrimenti.
	 */
	public static boolean isNumeric(String value){
		boolean isNumeric = false;
		
		String regex = "^[0-9]+$";
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        
        if(matcher.find()){
        	isNumeric = true;
        }

    	return isNumeric;
	}

	/**
	 * Metodo che restituisce la data odierna secondo il formato specificato nel
	 * parametro passato.
	 * 
	 * @param formatDate
	 *          Formato della data.
	 * @return Data odierna formattata sencondo il formato specificato.
	 */
	public static String now(String formatDate) {

		Date today = new Date();
		DateFormat formatter = new SimpleDateFormat(formatDate);

		return formatter.format(today).toString();
	}
	

	/** Add to specify date the specify number of day
	 * @param date -> date
	 * @param numberOfDay -> number of day to add
	 * @return correct date
	 */
	public static Date addDay(Date date, int numberOfDay)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, numberOfDay);
		return c.getTime();
	}
	
	public static Date subtractDay(Date date, int numberOfDay)
	{
		if (numberOfDay == 0) {
			return date;
		}
		if (numberOfDay > 0) {
			numberOfDay = -numberOfDay;
		}
		return addDay(date, numberOfDay);
	}

	/**
	 * Restituisce una stringa contenente la data nel formato esteso (es. 01
	 * Gennaio 2005)
	 * 
	 * @param in
	 *          Il Calendar da convertire
	 * @return La data convertita
	 */
	public static String calendarToVerboseString(Calendar in) {

		if (in == null) {
			return "";
		}

		String[] month = new String[] { "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre" };

		return in.get(Calendar.DAY_OF_MONTH) + " " + month[in.get(Calendar.MONTH)] + " " + in.get(Calendar.YEAR);
	}

	/**
	 * Formatta un double per la corretta visualizzazione di valuta Euro
	 * 
	 * @param amount
	 *          importo da formattare
	 * @return Stringa formattata per la visualizzazione in euro
	 */
	public static String formatCurrency(double amount) {

		int decimalPos = 2;
		double absAmount = Math.abs(amount);
		NumberFormat nfc = NumberFormat.getInstance(Locale.ITALIAN);

		// analisi decimali
		if (absAmount < (10 / 1936.27)) {

			// da 0 a 9 Lire 5 decimali
			decimalPos = 5;
		} else if (absAmount < (100 / 1936.27)) {

			// da 10 99 Lire 4 decimali
			decimalPos = 4;
		} else if (absAmount < (1000 / 1936.27)) {

			// da 100 a 999 Lire 3 decimali
			decimalPos = 3;
			// settaggio range minimo e massimo dei decimali
		}

		nfc.setMinimumFractionDigits(2);
		nfc.setMaximumFractionDigits(decimalPos);
		nfc.setGroupingUsed(false);

		return nfc.format(amount);
	}
	
	/** Restituisce l'eta' della persona in anni */
	public static int getAgeYears(GregorianCalendar birthDate) {
		if (birthDate==null) {
			return -1;
		}
		
		int age;
		GregorianCalendar today = new GregorianCalendar();
		GregorianCalendar birthVer = new GregorianCalendar();
		resetTime(today);

		// calcolo dell'eta' (considerando la presenza degli anni bisestili)
		age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
		birthVer.setTime(birthDate.getTime());
		birthVer.set(Calendar.YEAR, today.get(Calendar.YEAR));

		// Se il compleanno non c'e' ancora stato in questo anno allora toglie un
		// anno al risultato
		if (today.getTimeInMillis() < birthVer.getTimeInMillis()) {
			age--;
		}

		return age;
	}
	
	
	/** Restituisce l'eta' della persona in anni */
	public static int getAgeYears(Calendar birthDate) {
		if (birthDate==null) {
			return -1;
		}
		
		GregorianCalendar gBirth = new GregorianCalendar();
		gBirth.setTime(birthDate.getTime());		
		return getAgeYears(gBirth);
	}
	
	/**
	 * Restituisce l'eta' della persona in anni
	 * 
	 * @param birthDate
	 *          String data di nascita
	 * @return int L'eta' della persona
	 */
	public static int getAgeYears(String birthDate) {

		if (StringUtils.isEmpty(birthDate)) {
			return -1;
		}

		// data di nascita
		GregorianCalendar birth = new GregorianCalendar();
		try {
			birth.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(birthDate));
		} catch (ParseException pe) {
			Logger logger = org.slf4j.LoggerFactory.getLogger("");
			logger.error("Errore nella formattazione della data, durante il calcolo dell'eta'" + pe.getMessage());
			return 0;
		}
		
		return getAgeYears(birth);		
	}
	
	/** Restituisce l'eta' della persona al parametro AGE_FORMAT 0 eta' in anni - se 1 eta' in mesi
	 * 
	 * @param birthDate
	 *          String data di nascita formato "dd/MM/yyyy"
	 * @return int L'eta' della persona
	 */
	public static int getAgeInMonths(String birthDate, String ageFormat) {
		//Data non fornita
		if (StringUtils.isEmpty(birthDate)) {
			return -1;
		}

		// Parse data di nascita
		GregorianCalendar birth = new GregorianCalendar();
		try {
			birth.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(birthDate));
		} catch (ParseException pe) {
			Logger logger = org.slf4j.LoggerFactory.getLogger("");
			logger.error("Errore nella formattazione della data, durante il calcolo dell'eta'" + pe.getMessage());
			return 0;
		}
		
		//Restituisce l'eta' del paziente
		return getAgeInMonths(birth, ageFormat);
	}
	
	/**
	 * Restituisce l'eta' della persona al parametro AGE_FORMAT 0 eta' in anni - se 1 eta' in mesi
	 * 
	 * @param birth
	 *          data di nascita
	 * @return int L'eta' della persona
	 */
	public static int getAgeInMonths(Date birth, String ageFormat) {
		if (birth==null) {
			return -1;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(birth);
		
		//Restituisce l'eta' del paziente
		return getAgeInMonths(birth, ageFormat);
	}
	

	/**
	 * Restituisce l'eta' della persona al parametro AGE_FORMAT 0 eta' in anni - se 1 eta' in mesi
	 * 
	 * @param birth
	 *          data di nascita
	 * @return int L'eta' della persona
	 */
	public static int getAgeInMonths(Calendar birth, String ageFormat) {

		int result = -1;
		if(ageFormat.equals("1")) {
			result = getAgeInMonths(birth);			
		}else if(ageFormat.equals("0")) {
			result = getAgeInYears(birth);
		}
		
		return result;
	}
	
	
	/**
	 * Restituisce l'eta' della persona in mesi
	 * 
	 * @param birth
	 *          data di nascita
	 * @return int L'eta' della persona
	 */
	public static int getAgeInMonths(Calendar _birthDate) {
		
		LocalDate birthDate = toLocalDate(_birthDate);
		LocalDate today = toLocalDate(Calendar.getInstance());
				
		long monthsBetween = ChronoUnit.MONTHS.between(birthDate, today);

		//Note, however, that large numbers (usually larger than 2147483647 and smaller than -2147483648) will lose some of the bits and would be represented incorrectly.
		return (int)monthsBetween;
	}
	
	/**
	 * Restituisce l'eta' della persona in anni
	 * 
	 * @param birth
	 *          data di nascita
	 * @return int L'eta' della persona
	 */
	public static int getAgeInYears(Calendar _birthDate) {
		
		LocalDate birthDate = toLocalDate(_birthDate);
		LocalDate today = toLocalDate(Calendar.getInstance());
				
		long yearsBetween = ChronoUnit.YEARS.between(birthDate, today);

		//Note, however, that large numbers (usually larger than 2147483647 and smaller than -2147483648) will lose some of the bits and would be represented incorrectly.
		return (int)yearsBetween;
	}

	/**
	 * Restituisce l'eta' della persona (solo persone fisiche).
	 * 
	 * @param birthDate
	 *          String data di nascita
	 * @return String L'eta' della persona (solo persone fisiche).
	 */
	public static String getAge(String birthDate) {
		return getAge(birthDate, false);
	}

	/**
	 * Restituisce l'eta' della persona (solo persone fisiche).
	 * 
	 * @param birthDate
	 *          String data di nascita
	 * @param longFormat
	 *          boolean imposta la scrittura estesa dell'unita di misura dell'eta
	 *          (mesi, giorni, anni)
	 * @return String L'eta' della persona (solo persone fisiche).
	 */
	public static String getAge(String birthDate, boolean longFormat) {
		return getAge(birthDate, longFormat, null);
	}

	/**
	 * Restituisce l'et� della persona (solo persone fisiche).
	 * 
	 * @param birthDate
	 *          String data di nascita
	 * @param longFormat
	 *          boolean imposta la scrittura estesa dell'unita di misura dell'eta'
	 *          (mesi, giorni, anni)
	 * @param deathDate la data di decesso
	 * @return String L'et� della persona (solo persone fisiche).
	 */
	public static String getAge(String birthDate, boolean longFormat, String deathDate) {

		int age;
		GregorianCalendar today = new GregorianCalendar();
		GregorianCalendar birth = new GregorianCalendar();
		GregorianCalendar birthVer = new GregorianCalendar();
		String ageResult = "0";

		//Imposto la data odierna alla data di decesso
		if(StringUtils.isNotEmpty(deathDate)){
			try {
				today.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(deathDate));	
			} catch (ParseException e) {
				Logger logger = org.slf4j.LoggerFactory.getLogger("");
				logger.error("Errore nella formattazione della data decesso, durante il calcolo dell'eta'" + e.getMessage());
				today = new GregorianCalendar();
			}
		}
		
		// data di nascita
		try {
			birth.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(birthDate));
		} catch (ParseException pe) {
			Logger logger = org.slf4j.LoggerFactory.getLogger("");
			logger.error("Errore nella formattazione della data, durante il calcolo dell'eta'" + pe.getMessage());
			return "0";
		}

		// calcolo dell'eta' (considerando la presenza degli anni bisestili)
		age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
		birthVer.setTime(birth.getTime());
		birthVer.set(Calendar.YEAR, today.get(Calendar.YEAR));

		// Se il compleanno non c'e' ancora stato in questo anno allora toglie un
		// anno al risultato
		if (today.getTimeInMillis() < birthVer.getTimeInMillis()) {
			age--;
		}

		if (age < 0) {
			age = 0;
		}
		ageResult = String.valueOf(age);

		if (age < 2) {

			// se l'eta' e' inferiore a 2 anni calcolo in mesi
			int months = (today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)) * 12;
			int addmonths = (today.get(Calendar.MONTH) + 1) - (birth.get(Calendar.MONTH) + 1);

			// Il calcolo precedente potrebbe dare risultato negativo
			months += addmonths;

			// Verfica che il compleanno non sia gia' passato in questo mese
			if (today.getTimeInMillis() < birthVer.getTimeInMillis()) {
				// months--;
			}

			if (months < 0) {
				months = 0;
			}
			ageResult = months + (longFormat ? " mesi" : "m");

			// se i mesi sono inferiori a due, scrive l'eta' in giorni
			if (months < 2) {

				long interval = today.getTimeInMillis() - birth.getTimeInMillis();
				int days = new Long(interval / 86400000L).intValue();

				if (days < 0) {
					days = 0;
				}
				ageResult = days + (longFormat ? " giorni" : "g");
			}
		} else {
			ageResult = age + " anni";
		}

		return ageResult;
	}

	
	/**Il risultato avr� la data della variabile input "dt" e l'orario della variabile input "time"
	 * 
	 * @param dt
	 * @param time
	 * @return
	 */
	public static Calendar setTime(Calendar dt, Calendar time) {
		if (dt==null || time==null) {
			return null;
		}
	
		dt.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
		dt.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
		dt.set(Calendar.SECOND, time.get(Calendar.SECOND));
		dt.set(Calendar.MILLISECOND, time.get(Calendar.MILLISECOND));
		
		return dt;
	}

	
	/**Restituisce l'orario preso da un oggetto Date sfruttando l'oggetto Calendar
	 * Utilizzare per sostituire il deprecato "date.getHours()"
	 * @param date
	 * @return
	 */
	public static int getHours(Date date) {
		if (date==null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);  
		return cal.get(Calendar.HOUR_OF_DAY);
	}
		
	/**Modifica i minuti in un oggetto date e restituisce nuova istanza di oggetto
	 * Utilizzare per sostituire il deprecato "date.setMinutes(int)"
	 * 
	 * @param date
	 */
	public static Date setHours(Date date, int hours) {
		if (date==null) {
			return date;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);  
			cal.set(Calendar.HOUR_OF_DAY, hours);
			return cal.getTime();
		}
	}
	
	
	/**Restituisce i minuti presi da un oggetto Date sfruttando l'oggetto Calendar
	 * Utilizzare per sostituire il deprecato "date.getMinutes()"
	 * @param date
	 * @return
	 */
	public static int getMinutes(Date date) {
		if (date==null) {
			return 0;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);  
			return cal.get(Calendar.MINUTE);
		}
	}
	
	/**Modifica i minuti in un oggetto date e restituisce nuova istanza di oggetto
	 * Utilizzare per sostituire il deprecato "date.setMinutes(int)"
	 * 
	 * @param date
	 */
	public static Date setMinutes(Date date, int minutes) {
		if (date==null) {
			return date;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);  
			cal.set(Calendar.MINUTE, minutes);
			return cal.getTime();
		}
	}

	/**
	 * Imposta il massimo valore per i campi ora, minuti, secondi di un Date.
	 * 
	 * @param in
	 *          Il <code>Date</code> da modificare
	 */
	public static Date setMaxTime(Date in) {
		if (in != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(in);
			dayEnd(calendar);
			in.setTime(calendar.getTimeInMillis());
		}
		return in;
	}

	/**
	 * Il metodo effettua un arrotondamento di legge ad un importo double con +
	 * decimali.
	 * 
	 * @param amount
	 *          Importo da arrotondare.
	 * @return Importo arrotondato secondo i termini di legge.
	 */
	public static double round(double amount) {

		double absAmount = Math.abs(amount);
		BigDecimal bd = new BigDecimal(String.valueOf(amount));

		// default 2 posizioni decimali
		int decimalPos = 2;

		// analisi decimali
		if (absAmount < (10 / 1936.27)) {

			// da 0 a 9 Lire 5 decimali
			decimalPos = 5;
		} else if (absAmount < (100 / 1936.27)) {

			// da 10 99 Lire 4 decimali
			decimalPos = 4;
		} else if (absAmount < (1000 / 1936.27)) {

			// da 100 a 999 Lire 3 decimali
			decimalPos = 3;
		}

		return bd.setScale((decimalPos+1), BigDecimal.ROUND_HALF_UP).setScale(decimalPos, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/** Genera una data e converte risultato in un determinato formato */
	public static String format(Integer year, Integer month, Integer day, String format) {
		Calendar in = Calendar.getInstance();
		resetTime(in);
		
		if (day!=null) {
			in.set(Calendar.DAY_OF_MONTH,day);
		}
		
		if (month!=null) {
			in.set(Calendar.MONTH,month);
		}
		
		if (year!=null) {
			in.set(Calendar.YEAR,year);
		}
		
		return format(in.getTime(), format);
	}

	/**
	 * Formatta una data/ora in un determinato formato
	 * 
	 * @param in
	 *          data da formattare
	 * @param format
	 *          formnato in cui formattare la data
	 * @return stringa contenente la data formattata secondo il parametro format
	 */
	public static String format(Date in, String format) {

		if (in == null) {
			return "";
		}
		return DateFormatUtils.format(in, format);
	}
	
	public static String format(Calendar in, String format) {
		if (in == null) {
			return "";
		}
		return format(in.getTime(),format);
	}
	
	/**
	 * Formatta una data nel formato dd/MM/yyyy
	 * 
	 * @param in
	 *          data da formattare
	 * @return stringa contenente la data formattata secondo il formato dd/MM/yyyy
	 */
	public static String formatDate(Date in) {
		return format(in, FORMAT_DATE);
	}
	public static String formatDate(long timestamp) {
		return DateFormatUtils.format(timestamp, FORMAT_DATE);
	}
	public static String formatDate(Calendar cal) {
		if (cal == null) {
			return StringUtils.EMPTY;
		}else{
			return formatDate(cal.getTime());
		}
	}

	/** Formatta una data nel formato HH:mm */
	public static String formatHour(Date in) {
		return format(in, FORMAT_SIMPLE_TIME);
	}

	/** Formatta una calendar nel formato HH:mm */
	public static String formatHour(Calendar cal) {
		if (cal == null) {
			return StringUtils.EMPTY;
		}else{
			return formatHour(cal.getTime());
		}
	}
	
	/**
	 * Formatta una data nel formato dd/MM/yyyy HH:mm:ss
	 * 
	 * @param in
	 *          data da formattare
	 * @return stringa contenente la data formattata secondo il formato dd/MM/yyyy
	 *         HH:mm:ss
	 */
	public static String formatDateTime(Date in) {
		return format(in, FORMAT_DATETIME);
	}
	
	public static String formatDateTime(long timestamp) {
		return DateFormatUtils.format(timestamp, FORMAT_DATETIME);
	}
	
	
	/**
	 * Formatta una data nel formato dd-MM-yy
	 * 
	 * @param in
	 *          data da formattare
	 * @return stringa contenente la data formattata secondo il formato dd/MM/yyyy
	 *         HH:mm:ss
	 */
	public static String formatSimpleDateTime(Date in) {
		return format(in, FORMAT_SIMPLE_DATE_2);
	}
	public static String formatSimpleDateTime(long timestamp) {
		return DateFormatUtils.format(timestamp, FORMAT_SIMPLE_DATE_2);
	}


	/**
	 * Formatta una data nel formato HH:mm:ss
	 * 
	 * @param in
	 *          data da formattare
	 * @return stringa contenente la data formattata secondo il formato HH:mm:ss
	 */
	public static String formatTime(Date in) {
		return format(in, FORMAT_TIME);
	}
	public static String formatTime(long timestamp) {
		return DateFormatUtils.format(timestamp, FORMAT_TIME);
	}
	public static String formatTime(Calendar in) {
		if (in==null) {
			return null;
		}
		return formatTime(in.getTime());
	}
	
	/**
	 * Formatta una data nel formato HH:mm
	 * 
	 * @param in
	 *          data da formattare
	 * @return stringa contenente la data formattata secondo il formato HH:mm
	 */
	public static String formatSimpleTime(Date in) {
		return format(in, FORMAT_SIMPLE_TIME);
	}

	/**
	 * Controlla se il parametro in ingresso � una data valida nel formato
	 * dd/MM/yyyy
	 * 
	 * @param in
	 *          Stringa contenente la data da validare nel formato dd/MM/yyyy
	 * @return un valore booleano con l'esito del controllo
	 */
	public static boolean isDate(String in) {
		return GenericValidator.isDate(in, FORMAT_DATE, true);
	}

	/**
	 * Controlla se il parametro in ingresso � una data valida nel formato
	 * dd/MM/yyyy HH:mm:ss
	 * 
	 * @param in
	 *          Stringa contenente la data da validare nel formato dd/MM/yyyy
	 *          HH:mm:ss
	 * @return un valore booleano con l'esito del controllo
	 */
	public static boolean isDateTime(String in) {
		return GenericValidator.isDate(in, FORMAT_DATETIME, true);
	}

	/**
	 * Controlla se il parametro in ingresso � una data valida nel formato
	 * HH:mm:ss
	 * 
	 * @param in
	 *          Stringa contenente la data da validare nel formato HH:mm:ss
	 * @return un valore booleano con l'esito del controllo
	 */
	public static boolean isTime(String in) {
		return GenericValidator.isDate(in, FORMAT_TIME, true);
	}
	
	/**
   * Trasforma una Stringa di formato "dd/MM/yyyy" in Calendar e imposta l'ora a 00:00:00.
   *  Viene tentata una seconda conversione per verificare se il dato �
   * in formato "yyyyMMdd" (nel caso di provenienza dei dati da SISS)
   * 
   * @param in La stringa da trasormare
   * @return null se la stringa in input e' null, altrimenti il Calendar
   *         corrispondente
   * @throws ParseException se la stringa in input non contiene un formato
   *           corretto. E' buona prassi lasciar uscire questa eccezione per
   *           avvertire il chiamante del problema di "formato".
   */
	public static Calendar stringToSimpleDate(String in) throws ParseException {
		if (StringUtils.isEmpty(in)) {
			return null;
		}

		Calendar ret = GregorianCalendar.getInstance();

		try {
			ret.setTime(Commons.parseDate(in));
		} catch (ParseException e) {
			ret.setTime(Commons.parse(in, "yyyyMMdd"));
		}

		resetTime(ret);
		return ret;
	}

	/**
	 * Effettua il parse di una data nel formato dd/MM/yy
	 * 
	 * @param in
	 *          stringa contenente la data su cui effettuare il parse
	 * @return data se il parametro in ingresso � NULL o "" (blank) viene
	 *         restituito un valore NULL
	 * @throws ParseException
	 *           se si verifica un errore durante il parse della data
	 */
	public static Date parseSimpleDate(String in) throws ParseException {
		return parse(in, FORMAT_SIMPLE_DATE);
	}

	/**
	 * Effettua il parse di una data nel formato dd/MM/yyyy
	 * 
	 * @param in
	 *          stringa contenente la data su cui effettuare il parse
	 * @return data se il parametro in ingresso � NULL o "" (blank) viene
	 *         restituito un valore NULL
	 * @throws ParseException
	 *           se si verifica un errore durante il parse della data
	 */
	public static Date parseDate(String in) throws ParseException {
		return parse(in, FORMAT_DATE);
	}

	/** 01/01/1900 as date*/
	public static Date getMinDate() {
		try {
			return Commons.parseDate(Commons.SYS_DATAMIN);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null; //essendo la formattazione di una costante, non andr� mai in errore..
	}
	
	/**01/01/1900 00:00:00*/
	public static Date getMinHour() {
		try {
			return Commons.parse(SYS_LONGDATAMIN, Commons.FORMAT_DATETIME);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null; //essendo la formattazione di una costante, non andr� mai in errore..
	}
	
	/** 01/01/1900 00:00:00*/
	public static Calendar getMinHourCalendar() {
		return Commons.dateToCalendar(getMinHour());
	}
	
	public static Date getMaxHour() {
		try {
			return Commons.parse("01/01/1900 23:59:59", Commons.FORMAT_DATETIME);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null; //essendo la formattazione di una costante, non andr� mai in errore..
	}
	

	/** 01/01/1900 as Calendar*/
	public static Calendar getMinCalendar() {
		Date dt = getMinDate();
		Calendar result = Calendar.getInstance();
		result.setTime(dt);
		return result;
	}
	
	/** 31/12/2999 as date*/
	public static Date getMaxDate() {
		try {
			return Commons.parseDate(Commons.SYS_DATAMAX);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null; //essendo la formattazione di una costante, non andr� mai in errore..
	}

	/**
	 * Torna true se la data in input � not null e
	 * coincide con {@link #getMaxDate()}
	 * @param input
	 * @return
	 */
	public static boolean isMaxDate(Date input){
		if (input == null){
			return false;
		}

		return input.equals(getMaxDate());
	}
	
	/** 31/12/2999 as Calendar*/
	public static Calendar getMaxCalendar() {
		Date dt = getMaxDate();
		Calendar result = Calendar.getInstance();
		result.setTime(dt);
		return result;
	}

	/**
	 * Torna true se il calendar in input � not null e
	 * coincide con {@link #getMaxCalendar()}
	 * @param input
	 * @return
	 */
	public static boolean isMaxCalendar(Calendar input){
		if (input == null){
			return false;
		}

		return input.equals(getMaxCalendar());
	}

	/** Restituisce la data massima tra quelle fornite */
	public static Date getMaxDate(Date dt1, Date dt2) {
		if (dt1==null) {
			return dt2;
		}
		if (dt2==null) {
			return dt1;
		}
		//Se dt1 � maggiore di dt2
		if (dt1.after(dt2)) {
			return dt1;
		}
		return dt2;
	}
	
	/** Restituisce la data massima tra quelle fornite */
	public static Calendar getMaxDate(Calendar dt1, Calendar dt2) {
		if (dt1==null) {
			return dt2;
		}
		if (dt2==null) {
			return dt1;
		}
		//Se dt1 � maggiore di dt2
		if (dt1.after(dt2)) {
			return dt1;
		}
		return dt2;
	}
	
	
	/** Restituisce la data minima tra quelle fornite */
	public static Date getMinDate(Date dt1, Date dt2) {
		if (dt1==null) {
			return dt2;
		}
		if (dt2==null) {
			return dt1;
		}
		//Se dt1 � minore di dt2
		if (dt1.before(dt2)) {
			return dt1;
		}
		return dt2;
	}
	
	/** Restituisce la data minima tra quelle fornite */
	public static Calendar getMinDate(Calendar dt1, Calendar dt2) {
		if (dt1==null) {
			return dt2;
		}
		if (dt2==null) {
			return dt1;
		}
		//Se dt1 � minore di dt2
		if (dt1.before(dt2)) {
			return dt1;
		}
		return dt2;
	}
	
	/**
	 * Effettua il parse di una data nel formato HH:mm:ss
	 * 
	 * @param in
	 *          stringa contenente la data su cui effettuare il parse
	 * @return data se il parametro in ingresso � NULL o "" (blank) viene
	 *         restituito un valore NULL
	 * @throws ParseException
	 *           se si verifica un errore durante il parse della data
	 */
	public static Date parseTime(String in) throws ParseException {
		return parse(in, FORMAT_TIME);
	}

	/**
	 * Effettua il parse di una data nel formato specificato
	 * 
	 * @param in
	 *          data da formmattare
	 * @param format
	 *          formato della data
	 * @return data, se il parametro in ingresso � NULL o "" (blank) viene
	 *         restituito un valore NULL
	 * @throws ParseException
	 *           se si verifica un errore durante il parse della data
	 */
	public static Date parse(String in, String format) throws ParseException {

		if (StringUtils.isEmpty(in)) {
			return null;
		}

		return new SimpleDateFormat(format).parse(in);
	}
	
	/**Effettua il parse di una data nel formato specificato e converte il risultato in calendar */	
	public static Calendar parseCalendar(String in, String format) throws ParseException {
		return dateToCalendar(parse(in, format));
	}

	/**
	 * Effettua il parse di una data nel formato HH:mm
	 * 
	 * @param in
	 *          stringa contenente la data su cui effettuare il parse
	 * @return data se il parametro in ingresso � NULL o "" (blank) viene
	 *         restituito un valore NULL
	 * @throws ParseException
	 *           se si verifica un errore durante il parse della data
	 */
	public static Date parseSimpleTime(String in) throws ParseException {
		return parse(in, FORMAT_SIMPLE_TIME);
	}

	/**
	 * Effettua il parse di una data nel formato dd/MM/yyyy HH:mm:ss
	 * 
	 * @param in
	 *          stringa contenente la data su cui effettuare il parse
	 * @return data se il parametro in ingresso e' NULL o "" (blank) viene
	 *         restituito un valore NULL
	 * @throws ParseException
	 *           se si verifica un errore durante il parse della data
	 */
	public static Date parseDateTime(String in) throws ParseException {
		return parse(in, FORMAT_DATETIME);
	}

	/**
	 * Effettua il parse di una data nel formato dd/MM/yyyy HH:mm
	 * 
	 * @param in
	 *          stringa contenente la data su cui effettuare il parse
	 * @return data se il parametro in ingresso e' NULL o "" (blank) viene
	 *         restituito un valore NULL
	 * @throws ParseException
	 *           se si verifica un errore durante il parse della data
	 */
	public static Date parseSimpleDateTime(String in) throws ParseException {
		return parse(in, FORMAT_SIMPLE_DATETIME);
	}
	
	/**
	 * Converte un Date in un calendar
	 * 
	 * @param in
	 *          Il date da convertire
	 * @return null se l'oggetto in input e' null, altrimenti il Calendar
	 *         convertito
	 */
	public static Calendar dateToCalendar(Date in) {

		if (in == null) {
			return null;
		}

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(in);

		return cal;
	}
	
	/**Converte un oggetto Date in Instant */
	public static Instant dateToInstant(Date in) {
		//NB: Per qualche motivo java.sql.Date.toInstant() restituisce java.lang.UnsupportedOperationException*/
		if (in == null) {
			return null;
		}
		Calendar inCal = dateToCalendar(in);
		return inCal.toInstant();
	}
	
	/**
	 * Ritorna la differenza fra la prima e la seconda data espressa nell'unita' di
	 * misura specificata.
	 * 
	 * @param unit
	 *          L'unita' di misura nella quale esprimere il risultato
	 * @param date1
	 *          La prima data
	 * @param date2
	 *          La seconda data
	 * 
	 * @return La differenza fra la prima e la seconda data.
	 */
	public static long dateDiff(DateDiffUnit unit, Calendar date1, Calendar date2) {
		//Validazione input
		if (date1==null) {
			return 0;
		}
		if (date2==null) {
			return 0;
		}
		return dateDiff(unit, date1.getTime(), date2.getTime());
	}
	
	
	/**
	 * Ritorna la differenza fra la prima e la seconda data espressa nell'unita' di
	 * misura specificata.*/
	public static long dateDiff(DateDiffUnit unit, String date1, String date2) throws ParseException {
		String[] formats = { Commons.FORMAT_DATE, Commons.FORMAT_TIME, Commons.FORMAT_DATETIME };
		Date theDate1 = DateUtils.parseDate(date1, formats);
		Date theDate2 = DateUtils.parseDate(date2, formats);
		return dateDiff(unit, theDate1, theDate2);
	}
	
	/**
	 * Ritorna la differenza fra la prima e la seconda data espressa nell'unita' di
	 * misura specificata.
	 * 
	 * @param unit
	 *          L'unita' di misura nella quale esprimere il risultato
	 * @param date1
	 *          La prima data
	 * @param date2
	 *          La seconda data
	 * 
	 * @return La differenza fra la prima e la seconda data.
	 */
	public static long dateDiff(DateDiffUnit unit, Date date1, Date date2) {
		//Validazione input
		if (date1==null) {
			return 0;
		}
		if (date2==null) {
			return 0;
		}
		
		Calendar calendarDate1 = GregorianCalendar.getInstance();
		Calendar calendarDate2 = GregorianCalendar.getInstance();

		calendarDate1.setTime(date1);
		calendarDate2.setTime(date2);

		// restituisce la differenza tra due date in giorni
		// senza prendere in considerazione l'ora .
		if (unit.equals(DateDiffUnit.FULLDAY)) {
			// azzera l'orario della prima data
			calendarDate1.set(Calendar.HOUR_OF_DAY, 0);
			calendarDate1.set(Calendar.MINUTE, 0);
			calendarDate1.set(Calendar.SECOND, 0);

			// imposta l'orario della seconda data alle 23:59:59 per far calcolare il
			// giorno pieno
			calendarDate2.set(Calendar.HOUR_OF_DAY, 23);
			calendarDate2.set(Calendar.MINUTE, 59);
			calendarDate2.set(Calendar.SECOND, 59);
		}

		// Offset da aggiungere alla differenza dei secondi calcolati, per poter
		// gestire l'ora legale. Per le date rientranti nel periodo di orario legale
		// il valore dell'offset sara' pari a 3600000 millisecondi, 0 nel periodo di
		// orario solare.
		//
		// Es. differenza in giorni tra il 01/03 e il 31/03 dara' come offset:
		// per il 01/03 (cal1) = 0 per il 31/03 (cal2) = 3600000 nel nostro caso
		// quindi si avra'
		//
		// offset = 3600000 - 0 e quindi offset = 3600000 (1 ora)
		//
		// Es. differenza in giorni tra il 01/10 e il 31/10 dara' come offset:
		// per il 01/10 (cal1) = 3600000 per il 31/10 (cal2) = 0 nel nostro caso
		// quindi si avra'
		//
		// offset = 0 - 3600000 e quindi offset = -3600000 (-1 ora).

		int offset = (calendarDate2.get(Calendar.DST_OFFSET) - calendarDate1.get(Calendar.DST_OFFSET));

		// Differenza fra gli orari espressa in millisecondi
		long diff = (calendarDate2.getTimeInMillis() - calendarDate1.getTimeInMillis()) + offset;

		switch (unit) {
		case SECOND:
			diff /= DateUtils.MILLIS_PER_SECOND;
			break;

		case MINUTE:
			diff /= DateUtils.MILLIS_PER_MINUTE;
			break;

		case HOUR:
			diff /= DateUtils.MILLIS_PER_HOUR;
			break;

		case DAY:
			diff /= DateUtils.MILLIS_PER_DAY;
			break;

		case FULLDAY:
			diff /= DateUtils.MILLIS_PER_DAY;
			break;
		}

		return diff;
	}

	/**
	 * Concatena la data del primo parametro con l'ora del secondo parametro.
	 * 
	 * @param date
	 *          {@link Date} da cui recuperare la parte data da concatenare
	 * @param time
	 *          {@link Date} da cui recuperare la parte ora da concatenare
	 * 
	 * @return Una data con data e ora concatenate
	 */
	public static Date joinDateAndTime(Date date, Date time) {
		Date result = null;
		String formatted = Commons.formatDate(date) + " " + Commons.formatTime(time);

		try {
			result = Commons.parseDateTime(formatted);
		} catch (ParseException e) {
			result = null;
		}

		return result;
	}

	/**
	 * Concatena la data del primo parametro con l'ora del secondo parametro.
	 * 
	 * @param date
	 *          {@link Date} da cui recuperare la parte data da concatenare
	 * @param time
	 *          {@link Date} da cui recuperare la parte ora da concatenare
	 * 
	 * @return Una data con data e ora concatenate
	 */
	public static Calendar joinDateAndTime(Calendar date, Calendar time) {
		return dateToCalendar(joinDateAndTime(date.getTime(), time.getTime()));
	}

	/**
	 * Converte una stringa esadecimale in array di byte.
	 * 
	 * @param hex
	 *          La stringa esadecimale
	 * @return L'array di byte
	 */
	public static byte[] hexToByteArray(String hex) {

		byte[] byteArray = new byte[hex.length() / 2];

		for (int i = 0; i < hex.length(); i += 2) {
			byteArray[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
		}

		return byteArray;
	}

	/**
	 * Converte un array di byte in stringa esadecimale.
	 * 
	 * @param byteArray
	 *          L'array di byte
	 * @return La stringa esadecimale
	 */
	public static String byteArrayToHex(byte[] byteArray) {

		StringBuffer res = new StringBuffer(byteArray.length * 2);

		for (int i = 0; i < byteArray.length; i++) {
			if ((byteArray[i] & 0xff) < 0x10) {
				res.append("0");
			}
			res.append(Long.toString(byteArray[i] & 0xff, 16).toUpperCase());
		}

		return res.toString();
	}

	
	
	/**
	 * Converte in formato numerico i numeri di telefono , eliminando gli /
	 * @param phoneNumber
	 * @return
	 */
	public static String formatPhoneNumber(String phoneNumber) {
		
		String retPhoneNumber = "";
		
		try{
		
			retPhoneNumber = phoneNumber.replace("/", "").trim();
		
		}catch(Exception ex){
			Logger logger = org.slf4j.LoggerFactory.getLogger("");
			logger.error("Errore durante la conversione numerica del numero di telefono " + ex.getMessage());
		}
		
		return retPhoneNumber;
	}
	
	/**
	 * Verifica che la tessera sanitaria sia lombarda (000AA000) , in caso contrario ritorna nulla
	 * viene utilizzata per la notifica siss - ci � stato richiesto di non valorizzare la tessera sui
	 * messaggi di notifica a meno che non sia ua tessera siss 
	 * @param ssnNumber
	 * @return
	 */
	public static boolean validateSissSsnNumber(String ssnNumber) {
		
		boolean isSissSsnNumber  = false;
		
		try{
			
			// i primi 3 caratteri devono essere numerici
			if(StringUtils.isNumeric(ssnNumber.substring(0,1)) && StringUtils.isNumeric(ssnNumber.substring(1,2)) && StringUtils.isNumeric(ssnNumber.substring(2,3))){
				// il terzo e quarto carattere  devono essere  alfanumerici 
				if(StringUtils.isAlphanumeric(ssnNumber.substring(3,4)) && StringUtils.isAlphanumeric(ssnNumber.substring(4,5))){
					// gli ultimi 3 caratteri devono essere numerici
					if(StringUtils.isNumeric(ssnNumber.substring(5,6)) && StringUtils.isNumeric(ssnNumber.substring(6,7)) && StringUtils.isNumeric(ssnNumber.substring(7,8))){
						isSissSsnNumber = true;
					}
				}
			}
		
		}catch(Exception ex){
			Logger logger = org.slf4j.LoggerFactory.getLogger("");
			logger.error("Errore durante la convalida della tessera sanitaria " + ex.getMessage());
		}
		
		return isSissSsnNumber;
	}

	
	/**
	 * AC Fecit 25/07/2014
	 * 
	 * Recupera dei dati da una stringa composta nel seguente modo: fieldName=a;b;c;d;|fieldName=e;f;g;|....
	 * @param values
	 * @param fieldName
	 * @return
	 * @throws IMException
	 */
	public static String getValueByFieldName(String values, String fieldName){
		String fieldValue = "";
		
		if(values != null && !values.isEmpty() && fieldName != null && !fieldName.isEmpty()){
			
			fieldName = fieldName.toUpperCase();
			
			Pattern pattern = Pattern.compile(fieldName+"=([a-zA-Z0-9_.'����������; ]+)"); 
		    Matcher matcher = pattern.matcher(values);
		    
		    if(matcher.find()){
		    	fieldValue = matcher.group(1);
		    }
		    
		}
		
		return fieldValue;
	}

	/** Applica l'attuale encoding ad una stringa UTF-8*/
	public static String encodeString(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		
		byte ptext[] = str.getBytes(UTF_8_CHARSET); 
		return new String(ptext, CURRENT_CHARSET);
	}
	
	/** Converte una stringa CSV in un array di numeri interi */
	public static Integer[] csvToIntegerArray (String csv)
	{
		if (StringUtils.isEmpty(csv)) {
			return new Integer[0];
		}
		
		String[] strArray = String.valueOf(csv).split(",");
		Integer[] intArray = new Integer[strArray.length];
		for (int i = 0; i < strArray.length; i++) {
			if (StringUtils.isNotEmpty(strArray[i])) {
				intArray[i]=Integer.valueOf(strArray[i]);
			}
		}
		return intArray;
	}
	
	/** Date to Calendar */
	public static Calendar toCalendar(Date in) {
		if (in==null) {
			return null;
		}
		
		Calendar c = Calendar.getInstance();
		c.setTime(in);
		return c;
	}
	
	public static String truncateString(String input, int length){
		String result = input;
		if(StringUtils.isNotEmpty(input) && input.length() > length){
			result = input.substring(0, length);
		}
		return result;
	}

	public static String htmlEncode(String toEncodeValue) {	
		try {
			return URLEncoder.encode(toEncodeValue, CURRENT_ENCODING);
		} catch (Exception e) {
			return GlobalConstants.STRING_EMPTY;			
		}
	}
	
	public static String htmlDecode(String rawValue) {
		try {
			return URLDecoder.decode(rawValue, CURRENT_ENCODING);
		} catch (Exception e) {
			return GlobalConstants.STRING_EMPTY;
		}		
	}	
	/**
	 * Ritorna un comparator che compara due stringhe parsate 
	 * in data con Commons.parseDate()
	 * @return
	 */
	public static Comparator<String> getDateComparator(){
		return new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				Date d1;
				Date d2;
				try {
					d1 = Commons.parseDate(o1);
				} catch (ParseException e) {
					logger.warn("data '"+o1+"' non valida");
					return 1;
				}
				try {
				    d2 = Commons.parseDate(o2);
				} catch (ParseException e) {
					logger.warn("data '"+o2+"' non valida");
					return -1;
				}
				
				return (d1.after(d2))? 1 : -1;
			}
		};
	}
	
	/** Metodo che confronta due date di calendario */
	public static int compareCalendar(Calendar cal1, Calendar cal2) {
		if (cal1==null || cal2==null) {
			return 0;
		}
		if (cal1.equals(cal2)) {
			return 0;
		}
		else if (cal1.after(cal2)) {
			return 1;
		} else {
			return -1;
		}
	}
	
	/** Metodo che confronta due date di calendario */
	public static int compareCalendar(Calendar cal1, Calendar cal2, SortEnum sortDirection) {
		if (sortDirection==null || sortDirection==SortEnum.ASC) {
			return compareCalendar(cal1, cal2);
		} else {
			return compareCalendar(cal2, cal1);
		}
	}
	
	/** Metodo che confronta due stringhe */
	public static int compareString(String str1, String str2) {
		if (str1==null || str2==null) {
			return 0;
		}
		if (StringUtils.isEmpty(str1) || StringUtils.isEmpty(str2)) {
			return 0;
		}
		if (str1.equalsIgnoreCase(str2)) {
			return 0;		
		} else {
			return str1.compareTo(str2);
		}
	}
	
	/** Metodo che confronta due stringhe */
	public static int compareString(String str1, String str2, SortEnum sortDirection) {
		if (sortDirection==null || sortDirection==SortEnum.ASC) {
			return compareString(str1, str2);
		} else {
			return compareString(str2, str1);
		}
	}
	
	/** Metodo che confronta due variabili Boolean */
	public static int compareBoolean(Boolean bool1, Boolean bool2) {
		if (bool1==null || bool2==null) {
			return 0;
		}
		if (bool1==bool2) {
			return 0;		
		} else {
			if (bool1) {
				return -1;
			} else {
				return 1;
			}
		}
	}
	
	/** Metodo che confronta due variabili Boolean */
	public static int compareBoolean(Boolean bool1, Boolean bool2, SortEnum sortDirection) {
		if (sortDirection==null || sortDirection==SortEnum.ASC) {
			return compareBoolean(bool1, bool2);
		} else {
			return compareBoolean(bool2, bool1);
		}
	}
		
	/** Metodo che confronta due variabili Integer */
	public static int compareInteger(Integer int1, Integer int2) {
		if (int1==null || int2==null) {
			return 0;
		}
		if (int1.equals(int2)) {
			return 0;		
		} else {
			return int1.compareTo(int2);
		}
	}
	
	/** Metodo che confronta due variabili Integer */
	public static int compareInteger(Integer int1, Integer int2, SortEnum sortDirection) {
		if (sortDirection==null || sortDirection==SortEnum.ASC) {
			return compareInteger(int1, int2);
		} else {
			return compareInteger(int2, int1);
		}
	}
	
	/** Metodo che confronta due variabili Double */
	public static int compareDouble(Double db1, Double db2) {
		if (db1==null || db2==null) {
			return 0;
		}
		if (db1.equals(db2)) {
			return 0;		
		} else {
			return db1.compareTo(db2);
		}
	}
	
	/** Metodo che confronta due variabili Double */
	public static int compareDouble(Double db1, Double db2, SortEnum sortDirection) {
		if (sortDirection==null || sortDirection==SortEnum.ASC) {
			return compareDouble(db1, db2);
		} else {
			return compareDouble(db1, db2);
		}
	}
	
	public static Double stringCurrencyToDouble(String sCurrency) {
		Double result = null;
		try {
			result = Double.parseDouble(sCurrency.replace(',','.'));
		}catch(Exception e){
			result = null;
		}
		return result;
	}
	
	/**Insieme di intersezione tra due liste di interi */
	public static IntegerList intersect(IntegerList listA, IntegerList listB){
		IntegerList result = new IntegerList();
		if (!listA.isEmpty() && !listB.isEmpty()){
			
			//Si assicura che la lista B contenga un maggior numero di valori 
			//(invertendo le liste, se necessario)
			if (listA.size()>listB.size()) {
				switchIntList(listA, listB);
			}
			
			//Estrae i valori in comune tra le due liste
			for(Integer valA : listA) {
				if (listB.contains(valA)) {
					result.add(valA);
				}	
			}
		}
		return result;
	}
	
	/**Massimo comun divisore tra due interi 
	 * (Vds. Algoritmo di Euclide) */
	public static int mcd(int a, int b) {
		if (b == 0) {
			return a;  
		} else {
			return mcd(b, a % b);
		}
	}
	
	/**Massimo comun divisore di una lista di interi 
	 * (Vds. Algoritmo di Euclide) */
	public static int mcd(Set<Integer> set) {
		//Validazione input
		if (set==null || set.isEmpty()) {
			return 1;
		}
		
		//Converte Set in lista (per semplificare navigazione)
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(set);
		
		if (list.size()==1) {
			if (list.get(0)==null) {
				return 1;
			} else {
				return list.get(0).intValue();
			}
		}
		
		int result = 0;
		for (Integer i : list) {
			result = mcd(i, result);
		}
		
		//Condizione di sicurezza (non dovrebbe mai succedere..)
		if (result==0) {
			return 1;
		}
		return result;
	}
	
	
	
	public static void switchIntList(IntegerList listA, IntegerList listB){
		IntegerList listC = new IntegerList();
		listC.addAll(listA);
		listA.removeAllElements();
		listA.addAll(listB);
		listB.removeAllElements();
		listB.addAll(listC);
		
	}
	/**
	 * Metodo che permette la serializzazione in formato JSON di oggetti complessi
	 * con attributi dichiarati non di tipo primitivo. (Esempio attributi che rappresentano a loro volta un Object).
	public static String toJSONString(Object obj) {

		Gson gson = new Gson();
		String json = gson.toJson(obj);  
		return json;
	}*/
	
	
	/** Crea una copia dell'oggetto */
	public static Object deepCopy(Object inputObj) throws IOException, ClassNotFoundException {

		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream(100);
		java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
		oos.writeObject(inputObj);
		byte buf[] = baos.toByteArray();
		oos.close();

		java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(buf);
		java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais);
		Object newObj = ois.readObject();
		ois.close();

		return newObj;
	}
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String marshalLog(Class classObj, Object obj, QName rootElement) {
		//il Qname � quello dell'xml type ePrescriptionRequestType
		String value = null;
		try {
			//JAXBContext context = JAXBContext.newInstance(classObj); //senza cache � troppo onerosa
			JAXBContext context = JaxbUtil.getJaxbContext(classObj);
			Marshaller m = context.createMarshaller();
			StringWriter sw = new StringWriter();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			m.marshal(new JAXBElement(rootElement, classObj, obj), sw);
			sw.close();
			value = sw.toString();
		} catch (Exception e) {
			logger.error("Si � verificato un errore", e);
}
		return value;
	}*/
	
	/*public static String dumpClass(Object obj) {
		String result = "";
		if (obj != null) {
			try {
				result = ReflectionToStringBuilder.toString(obj, ReflectionToStringStyle.instance);
			}catch(Exception e){
				e.printStackTrace();
				result = "Exception in Commons.dumpClass(): "+e.getLocalizedMessage();
			}
		}
		return result;
	}*/
	
	/*public static Pair<Date, Date> getFullDayDates(Date in) {
		Pair<Date, Date> result = new Pair<Date, Date>();
		result.setFirstElem(getDayStart(in != null ? in : new Date()));
		result.setSecondElem(getDayEnd(in != null ? in : new Date()));
		return result;
	}
	
	public static Pair<Calendar, Calendar> getFullDayCalendars(Calendar in) {
		Pair<Calendar, Calendar> result = new Pair<Calendar, Calendar>();
		result.setFirstElem(dayStart(in != null ? in : Calendar.getInstance()));
		result.setSecondElem(dayEnd(in != null ? in : Calendar.getInstance()));
		return result;
	}*/

	/**
	 * calcola la data di inizio e fine in base al mese e l'anno passato
	 * se la data di inizio calcolata � minore della data odierna viene resituito oggi come data inizio
	 *
	 * TODO aggiungere controllo su date passate?
	 * 
	 * @param month 
	 * @param year
	 * @return coppia: il primo elemento la data inizio, la seconda la data fine
	 * @throws ParseException
	 
	public static Pair<Calendar,Calendar> calculateStartEndDate(Integer month, Integer year) throws ParseException{
		String meseString = null;
		int monthDays = 0;
		String dateStartString = null;
		String dateEndString = null;
		Calendar startDate = Calendar.getInstance();
		if (month.intValue() < 10) {
			meseString = "0" + month;
		} else {
			meseString = "" + month;
		}
		// Calcola la data iniziale in formato stringa
		 dateStartString = "01/" + meseString + "/" + year;
		// Calcola la data finale in formato stringa
		startDate.setTime(Commons.parseSimpleDate(dateStartString));
		monthDays = startDate.getActualMaximum(Calendar.DAY_OF_MONTH);
		dateEndString = monthDays + "/" + meseString + "/" + year;
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(Commons.parseSimpleDate(dateEndString));
		Calendar today = Commons.dayStart(GregorianCalendar.getInstance());
		if (startDate.before(today)) {
			startDate = today;
		}
		return new Pair<Calendar, Calendar>(startDate, endDate);
	}*/
	
	/**
	 * converte il calendar passato in una data in formato ISO 8601
	 * @param date
	 * @return
	 */
	public static String formatCalendarToISO_8601Date(Calendar date){
		//TimeZone tz = TimeZone.getTimeZone("UTC");
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
		//df.setTimeZone(tz);
		return df.format(date.getTime());
	}
	
	/**
	 * 2016-10-17T06:00:00.000Z
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static Date parseISO_8601Date(String date) throws ParseException{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault());
		return df.parse(date);
	}

	/** Restituisce la data minima tra quelle passate in input */
	public static Calendar findMinCalendar(Calendar...calendars) {
		Calendar result = null;
		try 
		{			
			for (Calendar cal : calendars) {
				//Non considero le date non valorizzate
				if (cal==null) {
					continue;
				}
				
				//Scelgo come risultato "provvisorio" la prima data valorizzata
				if (result==null) {
					result = cal;
					continue;
				}

				//Confronto la data "provvisoria" col valore corrente e prendo il MINORE
				//Se il risultato "provvisorio" fosse dopo il valore corrente, sceglie il valore corrente
				if (result.after(cal)) {
					result = cal;
				}
			}

		} catch (Exception e) {
			logger.error("errore nel tentativo di calcolare il valore minimo.");
		}
		
		//Clono il risultato per evitare anomalie
		if (result!=null) {
			result = (Calendar) result.clone();
		}
		return result;
	}
	
	/** Restituisce la data minima tra quelle passate in input */
	public static Calendar findMaxCalendar(Calendar...calendars) {
		Calendar result = null;
		try 
		{			
			for (Calendar cal : calendars) {
				//Non considero le date non valorizzate
				if (cal==null) {
					continue;
				}
				
				//Scelgo come risultato "provvisorio" la prima data valorizzata
				if (result==null) {
					result = cal;
					continue;
				}

				//Confronto la data "provvisoria" col valore corrente e prendo il MAGGIORE
				//Se il risultato "provvisorio" fosse dopo il valore corrente, sceglie il valore corrente
				if (result.before(cal)) {
					result = cal;
				}
			}

		} catch (Exception e) {
			logger.error("errore nel tentativo di calcolare il valore massimo.");
		}
		
		//Clono il risultato per evitare anomalie
		if (result!=null) {
			result = (Calendar) result.clone();
		}
		return result;
	}

	/** Restituisce il numero intero minimo tra quelle passate in input */
	public static Integer getMinInteger(Integer...integers) {
		Integer result = null;
		try 
		{			
			for (Integer integer : integers) {
				//Non considero le date non valorizzate
				if (integer==null) {
					continue;
				}
				
				//Scelgo come risultato "provvisorio" la prima data valorizzata
				if (result==null) {
					result = integer;
					continue;
				}

				//Confronto il numero "provvisorio" col valore corrente e prendo il minore
				if (result>integer) {
					result = integer;
				}
			}

		} catch (Exception e) {
			logger.error("errore nel tentativo di calcolare il valore minimo.");
		}
		
		//Clono il risultato per evitare anomalie
		if (result!=null) {
			result = new Integer(result.intValue());
		}
		return result;
	}
	
	/** Restituisce il numero intero massimo tra quelle passate in input */
	public static Integer getMaxInteger(Integer...integers) {
		Integer result = null;
		try 
		{			
			for (Integer integer : integers) {
				//Non considero le date non valorizzate
				if (integer==null) {
					continue;
				}
				
				//Scelgo come risultato "provvisorio" la prima data valorizzata
				if (result==null) {
					result = integer;
					continue;
				}

				//Confronto il numero "provvisorio" col valore corrente e prendo il maggiore
				if (result<integer) {
					result = integer;
				}
			}

		} catch (Exception e) {
			logger.error("errore nel tentativo di calcolare il valore minimo.");
		}
		
		//Clono il risultato per evitare anomalie
		if (result!=null) {
			result = new Integer(result.intValue());
		}
		return result;
	}
	
	
	/** Restituisce la data massima tra quelle passate in input */
	public static Calendar getMaxDate(Calendar...calendars) {
		Calendar result = null;
		try 
		{			
			for (Calendar cal : calendars) {
				//Non considero le date non valorizzate
				if (cal==null) {
					continue;
				}
				
				//Scelgo come risultato "provvisorio" la prima data valorizzata
				if (result==null) {
					result = cal;
					continue;
				}

				//Confronto la data "provvisoria" col valore corrente e prendo il minore
				//Se il risultato "provvisorio" fosse dopo il valore corrente, sceglie il valore corrente
				if (result.before(cal)) {
					result = cal;
				}
			}

		} catch (Exception e) {
			logger.error("errore nel tentativo di calcolare il valore massimo.");
		}
		
		//Clono il risultato per evitare anomalie
		if (result!=null) {
			result = (Calendar) result.clone();
		}
		return result;
	}

	
	public static boolean isSameDay(Calendar cal1, Calendar cal2){
		if(cal1 == null || cal2 == null){
			return false;
		}
		else{
			return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
			                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		}
	}
		

	/**
	 * {@linkplain #intervalsOverlap(Calendar, Calendar, Calendar, Calendar, boolean)}
	 * @param i1Start
	 * @param i1End
	 * @param i2Start
	 * @param i2End
	 * @return
	 * @throws ParseException
	 */
	public static boolean intervalsOverlap(Calendar i1Start, Calendar i1End, Calendar i2Start, Calendar i2End) throws ParseException {
		return intervalsOverlap(i1Start, i1End, i2Start, i2End, true);
	}

	/** Converte una stringa in hash numerico */
	public static long toHash(long baseHash, String value) {
		if (StringUtils.isEmpty(value)) {
			return 0;
		}
		
		long hash = baseHash;		
		for (int i = 0; i < value.length(); i++) {
		    hash = hash*31 + value.charAt(i);
		}
		return hash;
	}
	
	/** Converte una stringa in hash numerico */
	public static long toHash(long baseHash,StringList values) {
		long hash = 0;
		
		if (values!=null) {
			for (String value : values) {
				hash += toHash(baseHash,value);
			}
		}

		return hash;
	}
	
	/** Converte una data in hash numerico */
	public static long toHash(long baseHash,Integer value) {
		long hash = 0;
		if (value==null) {
			return hash;
		}		
		hash += toHash(baseHash,String.valueOf(value));
		return hash;
	}
	
	/** Converte una data in hash numerico */
	public static long toHash(long baseHash,Date value) {
		long hash = 0;
		if (value==null) {
			return hash;
		}		
		hash += toHash(baseHash,formatDateTime(value));
		return hash;
	}
	
	/** Converte una data in hash numerico */
	public static long toHash(long baseHash,Calendar value) {
		long hash = 0;
		if (value==null) {
			return hash;
		}		
		hash += toHash(baseHash,formatDateTime(value.getTime()));
		return hash;
	}
	
	/** Converte una stringa in oggetto Integer gestendo valori null */
	public static Integer toInteger(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return new Integer(value);
	}
	
	/** Converte un long in oggetto Integer gestendo valori null 
	 * NON gestisce eventuale overflow nella conversione */
	public static Integer toInteger(Long value) {
		if (value==null) {
			return null;
		}
		return new Integer(String.valueOf(value));
	}
	
	/** Converte una stringa in oggetto Long gestendo valori null */
	public static Long toLong(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return new Long(value);
	}
	
	/** Se oggetto firstChoice not null resestituisce quello, in caso contrario restituisce valore secondChoice */
	public static <T> T coalesce(T firstChoice,T secondChoice) {
		if (firstChoice!=null) {
			return firstChoice;
		} else {
			return secondChoice;
		}
	}


	/**
	 * {@linkplain #intervalsOverlap(Date, Date, Date, Date, boolean)}
	 * @param i1Start
	 * @param i1End
	 * @param i2Start
	 * @param i2End
	 * @param handleNullsAsToday
	 * @return
	 * @throws ParseException
	 */
	public static boolean intervalsOverlap(Calendar i1Start, Calendar i1End, Calendar i2Start, Calendar i2End, boolean handleNullsAsToday) throws ParseException {
		return intervalsOverlap(i1Start.getTime(), i1End.getTime(), i2Start.getTime(), i2End.getTime(), handleNullsAsToday);
	}

	
	/**
	 * Restituisce true se i due intervalli di Date passati in input si sovrappongono<br>
	 *  
	 * @param i1Start data inizio primo intervallo  
	 * @param i1End data fine primo intervallo
	 * @param i2Start data inizio secondo intervallo
	 * @param i2End data fine secondo intervallo
	 * @param handleNulls	se true indica di trattare il limite di data come il pi� piccolo o il pi� grande possibile
	 * @return
	 * @throws ParseException 
	 */
	public static boolean intervalsOverlap(Date i1Start, Date i1End, Date i2Start, Date i2End, boolean handleNulls) throws ParseException {
		
		if ((i1Start==null && i1End==null) || (i2Start==null && i2End==null)) {
			return false;
		}
		
		if (handleNulls) {
			if (i1Start==null) {
				i1Start = parseDate(SYS_DATAMIN);
			} 
			if (i1End==null) {
				i1End = parseDate(SYS_DATAMAX);
			}
			if (i2Start==null) {
				i2Start = parseDate(SYS_DATAMIN);
			}
			if (i2End==null) {
				i2End = parseDate(SYS_DATAMAX);
			}
		}
		
		return ( i1Start.compareTo(i2End) <= 0  && i1End.compareTo(i2Start) >= 0 ); 
	}
	
	

	/**
	 * {@linkplain #timeIntervalsOverlap(Calendar, Calendar, Calendar, Calendar, boolean)}
	 * @param i1Start
	 * @param i1End
	 * @param i2Start
	 * @param i2End
	 * @return
	 * @throws ParseException
	 */
	public static boolean timeIntervalsOverlap(Calendar i1Start, Calendar i1End, Calendar i2Start, Calendar i2End) throws ParseException {
		return timeIntervalsOverlap(i1Start, i1End, i2Start, i2End, true);
	}

	/**
	 * {@linkplain #timeIntervalsOverlap(Date, Date, Date, Date, boolean)}
	 * @param i1Start
	 * @param i1End
	 * @param i2Start
	 * @param i2End
	 * @param handleNulls
	 * @return
	 * @throws ParseException
	 */
	public static boolean timeIntervalsOverlap(Calendar i1Start, Calendar i1End, Calendar i2Start, Calendar i2End, boolean handleNulls) throws ParseException {
		return timeIntervalsOverlap(i1Start.getTime(), i1End.getTime(), i2Start.getTime(), i2End.getTime(), true);
	}	

	/**
	 * Controlla la sovrapposizione di due fascie orare dello stesso giorno<br>
	 * NB: Per sicurezza alle date viene troncata la parte relativa a giorno, mese ed anno<br> 
	 *   
	 * @param i1Start data inizio primo intervallo  
	 * @param i1End data fine primo intervallo
	 * @param i2Start data inizio secondo intervallo
	 * @param i2End data fine secondo intervallo
	 * @param handleNulls	se true indica di trattare il limite di data come il pi� piccolo o il pi� grande possibile
	 * @return
	 * @throws ParseException
	 */
	public static boolean timeIntervalsOverlap(Date i1Start, Date i1End, Date i2Start, Date i2End, boolean handleNulls) throws ParseException {
		return intervalsOverlap(getResettedDate(i1Start), getResettedDate(i1End), getResettedDate(i2Start), getResettedDate(i2End), handleNulls);
	}	
	

	
	/**
	 * Restituisce true se le due liste si intersecano, ovvero se ci sono date in comune<br><br>
	 * <b>
	 * NB: Gli oggetti vengono confrontati nativamente con metodo equals quindi<br> 
	 * si da per scontato gli oggetti Date devo essere uguali (compresi ora, minuti e secondi)<br>
	 * </b>
	 *   
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static boolean dateListIntersects(List<Date> l1, List<Date> l2) {
		
		if (l1==null || l2==null) {
			return false;
		}
		
		if (l1.size()==0 || l2.size()==0) {
			return false;
		}
		
		boolean intersect=false;
		for (Date d:l1) {
				if (l2.contains(d)) {
					intersect=true;
					break;
				}
		}
		
		if (!intersect) {
			for (Date d:l2) {
				if (l1.contains(d)) {
					intersect=true;
					break;
				}
			}
		}
		
		return intersect;
	}	

	/**Ritorna una data che rappresenta la somma algebrica fra la data e il numero di unit� temporali specificati
	* 
	* @param field: Calendar constants (Calendar.SECOND,Calendar.MINUTE,Calendar.HOUR,Calendar.DATE,Calendar.MONTH,Calendar.YEAR) 
	* @param number: Delta da addizionare (pu� essere negativo)
	* @param date: Data da cui patire
	* @return La nuova data calcolata
	*/
	public static Date dateAdd(int field, int number, Date date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.add(field, number);
		return cal.getTime();
	}
	
	/**Ritorna una data che rappresenta la somma algebrica fra la data e il numero di unit� temporali specificati
	* 
	* @param field: Calendar constants (Calendar.SECOND,Calendar.MINUTE,Calendar.HOUR,Calendar.DATE,Calendar.MONTH,Calendar.YEAR) 
	* @param number: Delta da addizionare (pu� essere negativo)
	* @param date: Data da cui patire
	* @return La nuova data calcolata
	*/
	public static Calendar calendarAdd(int field, int number, Calendar date) {
		if (date==null) {
			return null;
		}
		return Commons.dateToCalendar(dateAdd(field, number, date.getTime()));
	}
	

	/**Ritorna una data che rappresenta la somma algebrica fra la data e il numero di unit� temporali specificati
	* 
	* @param field: Calendar constants (Calendar.SECOND,Calendar.MINUTE,Calendar.HOUR,Calendar.DATE,Calendar.MONTH,Calendar.YEAR) 
	* @param number: Delta da addizionare (pu� essere negativo)
	* @param date: Data da cui patire
	* @return La nuova data calcolata
	*/
	public static Calendar dateAdd(int field, int number, Calendar date) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date.getTime());
		cal.add(field, number);
		return cal;
	}
	
	/**Restituisce true i due periodi sono sovrapposti.
	 * Se ignoreEqual==true, non considera "sovrapposti" gli estremi che si toccano (inizio di un periodo su fine dell'altro) */
	public static boolean between(Calendar from1, Calendar to1, Calendar from2, Calendar to2, boolean ignoreEqual) {
		//Gestiamo equals utilizzando le stringhe, per evitare errori (equals su date in certi casi da risultati imprevisti)
		String from1Str = Commons.format(from1, Commons.FORMAT_DATETIME);
		String to1Str = Commons.format(to1, Commons.FORMAT_DATETIME);
		String from2Str = Commons.format(from2, Commons.FORMAT_DATETIME);
		String to2Str = Commons.format(to2, Commons.FORMAT_DATETIME);
		
		//Validazione input
		if (from1==null || to1==null || from2==null || to2==null) {
			return false;
		}
		
		//Se uno dei periodi inizia dopo la fine dell'altro, inutile procedere
		if (from1.after(to2) || from2.after(to1)) {
			return false;
		}
		
		//Se l'inizio del primo periodo � compreso nel secondo..
		//Se l'inizio di un periodo � compresa nell'altro (estremi esclusi)
		if (from1.after(from2) && from1.before(to2)) {
			return true;
		}
		if (from2.after(from1) && from2.before(to1)) {
			return true;
		}

		//Se la fine di un periodo � compresa nell'altro (estremi esclusi)
//between(Calendar val, Calendar from, Calendar to, boolean ignoreEqual)
		if (to1.after(from2) && to1.before(to2)) {
			return true;
		}
		if (to2.after(from1) && to2.before(to1)) {
			return true;
		}

		//se inizio o fine corrispondono, sono sicuramente sovrapposti
		if (from1Str.equals(from2Str) || to1Str.equals(to2Str)) {
			return true;
		}
		
		//Verifico gli estremi (se l'inizio di un periodo corrisponde con la fine dell'altro)
		if (!ignoreEqual) {
			if (from1Str.equals(to2Str) || from2Str.equals(to1Str)) {
				return true;
			}			
		}
		
		return false;
	}
	
	/**Calcola la percentuale di un numero intero (per difetto)
	 * 
	 * @param value: valore di riferimento
	 * @param percentage: percentuale da calcolare
	 * @return
	 */
	public static int percentage(int value, int percentage) {
		if (percentage<=0) {
			return 0;
		}
		if (value==0) {
			return 0;
		}
		return (int)(value*(percentage/100.0f));
	}
	
	/**Converte una weekDayMask in una lista di giorni della settimana
	 * 1=lun, 2=mart, 3=merc, 4=giov, 5=ven, 6=sab, 7=dom
	 * 
	 * Se la maschera comprende "tutti" o "nessuno", il risultato � una lista vuota
	 * (si parte dal principio che "tutti" � da ignorare in quanto non un filtro e "nessuno" � da ignorare in quanto non ha senso)
	 * 
	 * @param weekDayMask
	 * @return
	 */
	public static Set<Integer> getWeekDays(String weekDayMask) {
		Set<Integer> result = new HashSet<>();
		final String emptyMap = "0000000";
//		final String fullMap = "1111111";
		
		if (weekDayMask==null) {
			return result;
		}
		if (weekDayMask.equals(emptyMap)) {
			return result;
		}
//		if (weekDayMask.equals(fullMap)) {
//			return result;
//		}
		
		//La mask prevede che il primo giorno sia il luned�; pertanto la posizione nella stringa corrisponde al giorno 
		for (int i = 0; i < weekDayMask.length(); i++) {
			if (weekDayMask.substring(i, (i + 1)).equals("1")) {
				result.add(i+1);
			}
		}
		
		return result;
	}
	
	/**Converte una weekDayMask in una lista di giorni della settimana
	 * 1=lun, 2=mart, 3=merc, 4=giov, 5=ven, 6=sab, 7=dom
	 * 
	 * 
	 * @param weekDayMask
	 * @return
	 */
	public static Set<Integer> getWeekDaysForUpdate(String weekDayMask) {
		Set<Integer> result = new HashSet<>();
		final String emptyMap = "0000000";
		
		if (weekDayMask==null) {
			return result;
		}
		if (weekDayMask.equals(emptyMap)) {
			return result;
		}
		
		//La mask prevede che il primo giorno sia il luned�; pertanto la posizione nella stringa corrisponde al giorno 
		for (int i = 0; i < weekDayMask.length(); i++) {
			if (weekDayMask.substring(i, (i + 1)).equals("1")) {
				result.add(i+1);
			}
		}
		
		return result;
	}
	
	/**Restituisce il giorno della settimana nel formato di riferimento 1=luned�, 7=domenica
	 * 
	 * @param rifDate
	 * @return
	 */
	public static Integer getWeekDay(Date rifDate) {
		return getWeekDay(dateToCalendar(rifDate));
	}
	
	/**Verifica appartenenza di una data ad una weekday mask
	 * true=la data � compresa nella mask
	 * 
	 * @param rifDate
	 * @param weekDayMask
	 * @return
	 */
	public static boolean checkWeekDayMask(Date rifDate,String weekDayMask) {
		return checkWeekDayMask(getWeekDay(rifDate),getWeekDays(weekDayMask));
	}
	
	/**Verifica appartenenza di una data (formato 1=lun, 7=dom) ad una weekday mask
	 * true=la data � compresa nella mask
	 * 
	 * @param rifDate
	 * @param weekDayMask
	 * @return
	 */
	public static boolean checkWeekDayMask(int weekDay,String weekDayMask) {
		return checkWeekDayMask(weekDay,getWeekDays(weekDayMask));
	}
	
	/**Verifica appartenenza di una data ad una weekday mask (convertita in set)
	 * true=la data � compresa nella mask
	 * 
	 * @param rifDate
	 * @param weekDayMask
	 * @return
	 */
	public static boolean checkWeekDayMask(Date rifDate,Set<Integer> weekDays) {
		return checkWeekDayMask(getWeekDay(rifDate), weekDays);
	}
	
	/**Verifica appartenenza di una data (formato 1=lun, 7=dom) ad una weekday mask (convertita in set)
	 * true=la data � compresa nella mask
	 * 
	 * @param rifDate
	 * @param weekDayMask
	 * @return
	 */
	public static boolean checkWeekDayMask(int weekDay,Set<Integer> weekDays) {
		if (weekDay<=0 || weekDay>7) {
			return false; //fuori range
		}
		if (weekDays==null || weekDays.isEmpty()) {
			return true; //inteso come "nessun filtro", cio� tutto
		}
		if (weekDays.contains(weekDay)) {
			return true;
		}
		return false;
	}
	
	/**Restituisce il giorno della settimana nel formato di riferimento 1=luned�, 7=domenica
	 * 
	 * @param rifDate
	 * @return
	 */
	public static Integer getWeekDay(Calendar rifDate) {
		if (rifDate==null) {
			return null;
		}
		
		//Giorno della settimana in formato 1-7 (1=Domenica, 2=Luned�, 7=Sabato)
		int dayOfWeek = rifDate.get(Calendar.DAY_OF_WEEK);
		
		//Converto il risultato in formato 1-7 (1=Luned�, 7=Domenica)
		dayOfWeek = dayOfWeek-1;
		if (dayOfWeek==0) {
			dayOfWeek=7;
		}

		return dayOfWeek;
	}

	/**Didide due numeri ed arrotonda per eccesso*/
	public static long roundUp(long num, long divisor) {
	    int sign = (num > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
	    return sign * (abs(num) + abs(divisor) - 1) / abs(divisor);
	}
	
	/**Didide due numeri ed arrotonda per eccesso*/
	public static int roundUp(int num, int divisor) {
	    int sign = (num > 0 ? 1 : -1) * (divisor > 0 ? 1 : -1);
	    return sign * (abs(num) + abs(divisor) - 1) / abs(divisor);
	}


	/** Converte weekDayMask formato IE-OPERA in weekDayBitMask 
	 * 
 	* @param weekDayBitMask: Bitmask per indicare in quali giorni della settimana.
	 * Luned� = 1
	 * Marted� = 2 
	 * Mercoled� = 4
	 * Gioved� = 8
	 * Venerd� = 16
	 * Sabato = 32
	 * Domenica = 64
	 * 
	 * @return Stringa di 7 caratteri, dove 1� rappresenta LUNEDI e 7� la DOMENICA
	 */
	public static String toWeekDayMask(Integer weekDayBitMask) {
		if (weekDayBitMask==null || weekDayBitMask<=0) {
			return "1111111";
		}
		StringBuilder result = new StringBuilder();
		result.append(((weekDayBitMask.intValue()&1)/1));
		result.append(((weekDayBitMask.intValue()&2)/2));
		result.append(((weekDayBitMask.intValue()&4)/4));
		result.append(((weekDayBitMask.intValue()&8)/8));
		result.append(((weekDayBitMask.intValue()&16)/16));
		result.append(((weekDayBitMask.intValue()&32)/32));
		result.append(((weekDayBitMask.intValue()&64)/64));
		return result.toString();
		
		//ALTERNATIVA (BUGGATA) presa da rest services
		//sbaglia di un giorno (converte luned� su marterd�, etc..)	
		// inserisco il pad degli zero a sinistra
		//String leftPad = StringUtils.leftPad(Integer.toBinaryString(weekDayBitMask), 7, '0');
		// rovescio la stringa dei valori
		//String weekDays = new StringBuilder(leftPad).reverse().toString();
		// faccio partire la settimana da domenica
		//return weekDays.substring(weekDays.length() - 1) + weekDays.substring(0, weekDays.length() - 1);
	}
	
	/**Converte weekDayBitMask in weekDayMask formato IE-OPERA
	 * 
	 * @param weekDayMask:  Stringa di 7 caratteri, dove 1� rappresenta LUNEDI e 7� la DOMENICA
	 * 
	 * @return Bitmask per indicare in quali giorni della settimana.
	 * Luned� = 1
	 * Marted� = 2 
	 * Mercoled� = 4
	 * Gioved� = 8
	 * Venerd� = 16
	 * Sabato = 32
	 * Domenica = 64
	 */
	public static Integer toWeekDayBitMask(String weekDayMask) {
		if (StringUtils.isBlank(weekDayMask) || weekDayMask.length()!=7) {
			weekDayMask = "1111111";
		}
		int result = 0;
		//Luned�
		if (weekDayMask.substring(0, 1).equals("1")) {
			result += 1;
		}
		//Marted�
		if (weekDayMask.substring(1, 2).equals("1")) {
			result += 2;
		}
		//Mercoled�
		if (weekDayMask.substring(2, 3).equals("1")) {
			result += 4;
		}
		//Gioved�
		if (weekDayMask.substring(3, 4).equals("1")) {
			result += 8;
		}
		//Venerd�
		if (weekDayMask.substring(4, 5).equals("1")) {
			result += 16;
		}
		//Sabato
		if (weekDayMask.substring(5, 6).equals("1")) {
			result += 32;
		}
		//Domenica
		if (weekDayMask.substring(6, 7).equals("1")) {
			result += 64;
		}
		
		return result;
		
//		//SOLUZIONE BUGGATA: CONVERTE IL MARTEDI' ('0100000') in 1 (luned�, '10000000')
//		String binary = new StringBuilder(weekDayMask).reverse().toString();
//		try{
//			return Integer.parseInt(binary, 2);
//		} catch (NumberFormatException ex){
//			return 0;
//		}
		
	}
	
	/**Confonto tra due stringhe*/
	public static boolean equals(String v1, String v2, boolean withCase) {
		if (v1 == null) {
			if (v2 != null) {
				return false;
			}
		} else if (withCase) {
			if (!v1.equals(v2)) {
				return false;
			}
		} else if (!v1.equalsIgnoreCase(v2)) {
			return false;
		}
		return true;
	}
	
	/**Confonto tra due interi*/
	public static boolean equals(Integer v1, Integer v2) {
		if (v1 == null) {
			if (v2 != null) {
				return false;
			}
		} else if (!v1.equals(v2)) {
			return false;
		}
		return true;
	}
	
	/**Confronto tra date di calendario, convertiti in stringa per aggirare problemi di confronto tra oggetti calendar
	 * Essendo una classe astratta, l'equals tra due istanze di classe che la implementano potrebbe dare false anche se entrambe puntano alla stessa data
	 *  
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static boolean equals(Calendar cal1, Calendar cal2) {
		return equals(cal1, cal2, Commons.FORMAT_DATETIME);
	}

	public static boolean equals(Calendar cal1, Calendar cal2, String format) {
		String cal1Str = Commons.format(cal1, format);
		String cal2Str = Commons.format(cal2, format);
		return cal1Str.equals(cal2Str);
	}
	
	/**Restituisce un nuovo oggetto Calendar equivalente a quello passato in input*/
	public static Calendar getCalendar(Calendar in) {
		if (in==null) {
			return null;
		}
		Calendar out = Calendar.getInstance();
		out.setTime(in.getTime());
		return out;
	}
	
	/**Restituisce un nuovo oggetto Calendar equivalente a quello passato in input*/
	public static Calendar getCalendar(Date in) {
		if (in==null) {
			return null;
		}
		Calendar out = Calendar.getInstance();
		out.setTime(in);
		return out;
	}
	
	/**********************************************************************/
	/********** METODI PER IMPOSTARE ORA 00:00:00:000  ********************/		
	/**********************************************************************/
	
	/**Data odierna con orario impostato a 00:00:00:000 */
	public static Date getToday() {
		return Commons.getDayStart(new Date());
	}
	
	/** Fornisce una NUOVA data con orario impostato a 00:00:00:000 */
	public static Date getDayStart(Date in) {
		//Verifica input
		if (in == null) {
			return null;
		}
		Calendar out = Calendar.getInstance();
		out.setTime(in);
		return resetTime(out).getTime();
	}	


	/** Fornisce una NUOVA data con orario impostato a 00:00:00:000 */
	public static Calendar getDayStart(Calendar in)
	{
		//Verifica input
		if (in == null) {
			return null;
		}
		Calendar out = Calendar.getInstance();
		out.setTime(in.getTime());
		return resetTime(out);
	}
			
	/** AZZERA i campi ora, minuti, secondi di un Calendar impostandoli a
	 * 00:00:00.000 per ottenere l'inizio del giorno corrente.
	 * 
	 * @param in
	 *          Il Calendar da azzerare
	 * @return L'oggetto fornito in input con i campi opportunamente modificati
	 */
	public static Calendar dayStart(Calendar in) {
		if (in != null) {
			in.set(Calendar.HOUR_OF_DAY, 0);
			in.set(Calendar.MINUTE, 0);
			in.set(Calendar.SECOND, 0);
			in.set(Calendar.MILLISECOND, 0);
		}
		return in;
	}
	
	/** AZZERA i campi ora, minuti, secondi di un Calendar impostandoli a
	 * 00:00:00.000 per ottenere l'inizio del giorno corrente.
	 * 
	 * @param in
	 *          Il Calendar da azzerare
	 * @return L'oggetto fornito in input con i campi opportunamente modificati
	 */
	public static Calendar resetTime(Calendar in) {
		return dayStart(in);
	}
	
	
	/**Use getStartDate() */
	@Deprecated
	public static Date resetTime(Date in) {
		return getDayStart(in);
	}


	/**Use getStartDate() */
	@Deprecated
	public static Date dayStart(Date in) {
		return getDayStart(in);
	}

	
	/**********************************************************************/
	/********** METODI PER IMPOSTARE ORA 23:59:59:999  ********************/	
	/**********************************************************************/

	
	/** Fornisce una NUOVA data in cui l'orario � impostato a 23:59:59:999*/
	public static Date getDayEnd(Date in)
	{
		//Verifica input
		if (in == null) {
			return null;
		}
		Calendar out = Calendar.getInstance();
		out.setTime(in);
		return dayEnd(out).getTime();		
	}	


	/** Fornisce una NUOVA data in cui l'orario � impostato a 23:59:59:999*/
	public static Calendar getDayEnd(Calendar in)
	{
		//Verifica input
		if (in == null) {
			return null;
		}
		Calendar out = Calendar.getInstance();
		out.setTime(in.getTime());
		return dayEnd(out);
	}
	
	/** AZZERA i campi ora, minuti, secondi di un Calendar impostandoli a
	 * 23:59:59.999 per ottenere la fine del giorno corrente.
	 * 
	 * @param in
	 *          Il Calendar da impostare
	 * @return L'oggetto fornito in input con i campi opportunamente modificati
	 */
	public static Calendar dayEnd(Calendar in) {

		if (in != null) {
			in.set(Calendar.HOUR_OF_DAY, 23);
			in.set(Calendar.MINUTE, 59);
			in.set(Calendar.SECOND, 59);
			in.set(Calendar.MILLISECOND, 999);
		}

		return in;
	}
	

	
	/**********************************************************************/
	/********** METODI PER IMPOSTARE DATA 01/01/1900  *********************/		
	/**********************************************************************/
	
	/**Fornisce una NUOVA data da cui la data � impostata a 1/1/1900.
	 * 
	 * @param in
	 *          Il Date da azzerare
	 * @return Clone dell'oggetto fornito in input con i campi opportunamente modificati
	 * @throws ParseException
	 *           se si verifica un errore durante il parse dell'input
	 */
	public static Date getResettedDate(Date in) {
		if (in==null) {
			return null;
		}
		Calendar out = Calendar.getInstance();
		out.setTime(in);
		out.set(Calendar.YEAR, 1900);
		out.set(Calendar.MONTH, Calendar.JANUARY);
		out.set(Calendar.DATE, 1);
		return out.getTime();
	}

	/**Fornisce una NUOVA data da cui la data � impostata a 1/1/1900.
	 * 
	 * @param in
	 *          Il Calendar da azzerare
	 * @return Clone dell'oggetto fornito in input con i campi opportunamente modificati
	 */
	public static Calendar getResettedDate(Calendar in) {
		//Verifica input
		if (in == null) {
			return null;
		}
		Calendar out = Calendar.getInstance();
		out.setTime(in.getTime());
		return resetDate(out);
	}
	
	/**Resetta ed imposta la data a 1/1/1900 nell'oggetto in input.
	 * 
	 * @param in
	 *          Il Calendar da azzerare
	 * @return L'oggetto fornito in input con i campi opportunamente modificati
	 */
	public static Calendar resetDate(Calendar in) {
		//Verifica input
		if (in == null) {
			return null;
		}
		in.set(Calendar.YEAR, 1900);
		in.set(Calendar.MONTH, Calendar.JANUARY);
		in.set(Calendar.DATE, 1);
		return in;
	}

	/**Trova l'ennesimo giorno della settimana in un mese (es: 2� luned� del mese) */
	public static Calendar getDayOfMonth(int weekday, int month, int year, int occurrence) {
		Calendar cal = resetTime(Calendar.getInstance());
		
		//Primo giorno del mese 
		cal.set(year, month, 1);
		
		//Richiesto ultimo giorno del mese
		if (occurrence>=5) {
			return getLastDayOfMonth(weekday, month, year);
		}
		
		int currPos = 0;
		
		//Scansiona il mese trovando il giorno richiesto
		while(cal.get(Calendar.MONTH)==month) {
			if (getWeekDay(cal).intValue() == weekday) {
				currPos+=1;
			}
			if (currPos==occurrence) {
				return cal;
			}
			cal.add(Calendar.DATE, 1);
		}
		
		//Non dovrebbe mai arrivare qui..
		return null;
		
	}
	
	/** Find the last weekday of month in the specify month and year
	 * @param weekday: 1=luned�, 7=domenica.. vds getWeekDay(cal)
	 * @param month: 0=gennaio, 11=dicembre
	 * @param year
	 * @return
	 */
	public static Calendar getLastDayOfMonth(int weekday, int month, int year) {
		Calendar cal = resetTime(Calendar.getInstance());

		//Primo giorno del mese seguente
		cal.set(year, month + 1, 1);

		//Ultimo giorno del mese in corso
		cal.add(Calendar.DATE, -1);

		//Procede a ritroso trovando il giorno della settimana richiesto
		while(getWeekDay(cal).intValue() != weekday) {
			cal.add(Calendar.DATE, -1);
		}
		
		//Restituisce il giorno trovato
		return cal;
	}
	
	/** Find the last date of month in the specify month and year
	 * @param date
	 * @return
	 */
	public static Date getLastDateOfMonth(Date date) {
		date = DateUtils.addMonths(date, 1);
		Calendar cal = dateToCalendar(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return addDay(cal.getTime(), -1);
	}

	/**
	 * Trasforma il nome di una property in un nome di environment variable.
	 * In sostanza rimuove i caratteri "-", sostiuisci i caratteri "." con "_"
	 * e trasforma tutto in uppercase.
	 * @param propertyName
	 * @return
	 */
	public static String propertyNameToEnvName(String propertyName){
		return propertyName.replace("-", "")
				.replace(".", "_")
				.toUpperCase();
	}

	public static String getPropertyFromSystemOrEnv(String propertyName){
		// Recupera dalle system properties
		String output = System.getProperty(propertyName);
		if (StringUtils.isEmpty(output)) {
			//Se non � presente la system variable proviamo con l'environment variable
			String appHomeENV = Commons.propertyNameToEnvName(propertyName);
			output = System.getenv(appHomeENV);
		}
		return output;
	}
	
	public static boolean toBoolean(String s) {
		return s!=null && ArrayUtils.contains(TRUE_VALUES, StringUtils.upperCase(s));
	}
	
	/**Converte oggetto Clob in stringa*/
	public static String clobToString(Clob data) {
		
		if (data == null) {
		      return null;
		}

        StringBuffer result = new StringBuffer();

	    try {
	        BufferedReader bufferRead = new BufferedReader(data.getCharacterStream());
	      
	        String strng;

	        while ((strng=bufferRead.readLine())!=null) {
	        	result.append(strng);
	        }
	      
	    } catch (SQLException e) {
	        // handle this exception
	    } catch (IOException e) {
	        // handle this exception
	    }
	    return result.toString();
	}
	
	public static Integer toIntegerSafe(String value) {
		if(StringUtils.isBlank(value)) {
			return null;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			logger.warn("Conversione del valore:" + value + "ad int fallita.", e);
			return null;
		}
	}
	
	/**
	 * Restituisce il nome esteso del giorno di una settimana a partire dal suo valore Integer (riferirsi a {@link DayOfWeek})
	 * @param dayOfWeek
	 * @return il giorno della settimana in formato stringa localizzato, se il parametro � un {@link DayOfWeek}, null altrimenti
	 
	public static String toDayOfWeekString(Integer dayOfWeek) {
		if(dayOfWeek==null || dayOfWeek<DayOfWeek.MONDAY.ordinal() || dayOfWeek>DayOfWeek.SUNDAY.ordinal()) {
			return null;
		}
		String localeString = PropertiesResourceBundle.getInstance().getCurrentLocale();
		if(StringUtils.isEmpty(localeString)) {
			// fallback
			localeString = Locale.ITALY.toString();
		}
		return DayOfWeek.of(dayOfWeek).getDisplayName(TextStyle.FULL,new Locale(localeString.substring(0,2)));
	}*/
	
	/*public static byte[] exportToPDF(JasperPrint report) throws JRException {
		Validate.notNull(report, "Parametro obbligatorio mancante: report.");
		List<JasperPrint> prints = Arrays.asList(report);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JRPdfExporter pdfExporter = new JRPdfExporter();
		pdfExporter.setExporterInput(SimpleExporterInput.getInstance(prints));
		pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		pdfExporter.exportReport();
		return outputStream.toByteArray();
	}
	
	public static byte[] exportToXLS(JasperPrint report, SimpleXlsReportConfiguration configuration) throws JRException {
		Validate.notNull(report, "Parametro obbligatorio mancante: report.");
		List<JasperPrint> prints = Arrays.asList(report);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JRXlsExporter xlsExporter = new JRXlsExporter();
		xlsExporter.setExporterInput(SimpleExporterInput.getInstance(prints));
		xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
		
		if(configuration==null) {
			configuration = new SimpleXlsReportConfiguration();
			configuration.setRemoveEmptySpaceBetweenRows(Boolean.TRUE);
			configuration.setWhitePageBackground(Boolean.FALSE);
		}
		xlsExporter.setConfiguration(configuration);
		xlsExporter.exportReport();
		return outputStream.toByteArray();
	}
	
	public static boolean isHourInInterval(Calendar target, Calendar start, Calendar end) {
		String targetHour = Commons.format(target, Commons.FORMAT_SIMPLE_TIME);
		String startHour = Commons.format(start, Commons.FORMAT_SIMPLE_TIME);
		String endHour = Commons.format(end, Commons.FORMAT_SIMPLE_TIME);
		
        return ((targetHour.compareTo(startHour) >= 0)
                && (targetHour.compareTo(endHour) <= 0));
    }*/
	
	/**
	 * Converte un Calendar in una LocalDate.
	 * Se Calendar è null verrà usato defaultCalendar per la conversione.
	 * La conversione tornerà una localDate basata sulla zone passata come parametro.
	 * Se zoneId è null, verrà usata la zoneId restituita dal metodo ZoneId.systemDefault()
	 * @param calendar
	 * @param defaultCalendar
	 * @param zoneId
	 * @return LocalDate
	 */
	public static LocalDate toLocalDate(Calendar calendar){
		return toLocalDate(calendar, null, null);
	}
	public static LocalDate toLocalDate(Calendar calendar, Calendar defaultCalendar, ZoneId zoneId){
		if (calendar == null) {
			calendar = defaultCalendar;
		}

		if (calendar == null) {
			return null;
		}

		if (zoneId == null){
			zoneId = ZoneId.systemDefault();
		}

		return calendar.toInstant().atZone(zoneId).toLocalDate();

	}
}
