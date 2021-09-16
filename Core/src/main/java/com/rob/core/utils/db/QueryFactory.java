package com.rob.core.utils.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.rob.core.utils.java.Commons;
import com.rob.core.utils.java.IntegerList;
import com.rob.core.utils.java.StringList;

/**
 * La classe implementa le funzioni necessarie per formattare i dati prima di
 * salvarli in un RDBMS attraverso le query. Sono definiti dei metodi che
 * consentono effettuare una serie di controlli sui dati in modo che essi
 * possano venire letti e/o salvati correttamente (ad esempio la gestione degli
 * apici e dei valori null). Ogniqualvolta si va a leggere o a scrivere il
 * valore di un campo del DBMS attraverso una query è importante chiamare il
 * metodo checkRead() o checkWrite() pena il cattivo funzionamento delle query
 * stesse.
 */
@SuppressWarnings("javadoc")
public class QueryFactory {


	/**
   * Costruttore standard della classe.
   */
	public QueryFactory() {
	}

	/** Formato della data/ora da utilizzare */
	protected static final String FORMAT_DATETIME = "dd/MM/yyyy HH:mm:ss";

	/** Formato della data da utilizzare */
	protected static final String FORMAT_DATE = "dd/MM/yyyy";

	/** Formato dell'ora da utilizzare */
	protected static final String FORMAT_TIME = "HH:mm:ss";

	
	public static final String REGEXP_SPLIT_TO_TABLE_COL = "val";
	/**
   * Costante usata per indicare alla castExpression() un cast da stringa a
   * smallint.
   */
	public static final int SQL_SMALLINT = 0;

	/**
   * Costante usata per indicare alla castExpression() un cast da stringa a
   * intero.
   */
	public static final int SQL_INTEGER = 1;

	/**
   * Costante usata per indicare alla castExpression() un cast da stringa a
   * real.
   */
	public static final int SQL_REAL = 2;

	/**
   * Costante usata per indicare alla castExpression() un cast da stringa a
   * floating point.
   */
	public static final int SQL_FLOAT = 3;

	/**
   * Costante usata per indicare alla castExpression() un cast da stringa a
   * varchar.
   */
	public static final int SQL_VARCHAR = 4;

	/**
   * Costante usata per indicare alla castExpression() un cast da stringa a
   * dataora.
   */
	public static final int SQL_TIMESTAMP = 5;

	/**
   * Costante usata per indicare alla castExpression() un cast da stringa data.
   */
	public static final int SQL_DATE = 6;

	/**
   * Costante usata per indicare alla castExpression() un cast da stringa a ora.
   */
	public static final int SQL_TIME = 7;

	/**
   * Costante usata per indicare una operazione svolta sui giorni.
   */
	public static final int SQL_DAY = 8;

	/**
   * Costante usata per indicare una operazione svolta sui mesi.
   */
	public static final int SQL_MONTH = 9;

	/**
   * Costante usata per indicare una operazione svolta sugli anni.
   */
	public static final int SQL_YEAR = 10;

	/**
   * Costante usata per indicare una operazione svolta sui giorni della
   * settimana.
   */
	public static final int SQL_WEEKDAY = 11;

	/**
   * Costante usata per indicare una operazione svolta sulle ore.
   */
	public static final int SQL_HOUR = 12;

	/** Costante usata per indicare una operazione svolta sui minuti. */
	public static final int SQL_MINUTE = 14;

	/** Costante usata per indicare una operazione svolta sui secondi. */
	public static final int SQL_SECOND = 15;	
	
	/** Costante usata per indicare il numero massimo di elementi presenti in una singola clausola IN. */
	private static final int IN_CLAUSE_MAX_ELEMENT = 200;
	
	/** Costante usata per indicare il numero massimo di elementi totali presenti in una serie di clausole IN separate da OR/AND. */
	private static final int IN_CLAUSE_MAX_TOTAL_ELEMENT = 1000;
	
	/** Costante usata per messaggio di errore in caso di superamento del limite massimo massimo di elementi totali presenti in una serie di clausole IN separate da OR/AND. */
	private static final String IN_CLAUSE_MAX_TOTAL_ELEMENT_ERROR = "Superato il limite massimo di "+ IN_CLAUSE_MAX_TOTAL_ELEMENT + " elementi per clausole IN" ;

	/**
   * Restituisce una stringa contenente il carattere-jolly da utilizzare per
   * specificare zero o più caratteri nelle query SQL. Il carattere tornato
   * dipende ovviamente dall' dbms.
   * 
   * @return Carattere jolly per la ricerca parziale con il LIKE.
   */
	public String anyChar() {
		return ("%");
	}

	/**
   * Restituisce una stringa contenente il carattere-jolly da utilizzare per
   * specificare un qualsiasi carattere nelle query SQl, in base al server dbms.
   * 
   * @return Carattere jolly SQL per la ricerca il LIKE.
   */
	public String anySingleChar() {
		return ("");
	}

	public String getNextValSyntax(String syntax){

		  String query = null;
		  query=" nextval('"+syntax+"')";
		  return query;
	  }
	

	public String getSysDateSyntax(){
		  String query = " NOW() ";
		  return query;
	  }

	public String getDualSyntax(){
		
		String query = "";
		query = " FROM DUAL";
	    return query;
	}
	
	
	public String concatDateAndTime(String date, String time){
		return date+" + "+time;
	}
	
	
	/**
   * Permette di effettuare conversioni di tipo dei dati a partire da una
   * espressione stringa.
   * 
   * @param expression Espressione su cui fare il cast.
   * @param resultType Tipo di cast da eseguire.
   * @return Stringa contenente il comando SQL necessario per effettuare il cast
   *         nel dialetto SQL del DBMS selezionato.
	 * @throws Exception 
   */
	public String castExpression(String expression, int resultType) throws Exception {
		
			switch (resultType) {
			case SQL_DATE:
				return ("TO_DATE(TO_CHAR(" + expression + ",'DD/MM/YYYY'),'DD/MM/YYYY')");
			case SQL_TIME:
				return ("TO_TIMESTAMP(TO_CHAR(" + expression + ",'HH24:MI:SS'),'HH24:MI:SS')");
			case SQL_TIMESTAMP:
				throw new Exception();
			case SQL_VARCHAR:
				throw new Exception();
			case SQL_SMALLINT:
				throw new Exception();
			case SQL_INTEGER:
				throw new Exception();
			case SQL_REAL:
				throw new Exception();
			case SQL_DAY:
				throw new Exception();
			case SQL_MONTH:
				throw new Exception();
			case SQL_YEAR:
				return ("EXTRACT(year from " + expression + ")");
			case SQL_WEEKDAY:
				// il risultato di EXTACT dow e' diverso dal risultato TO_CHAR 'D' di Oracle: utilizzare la funzione FN_GET_WEEKDAY
				// return ("EXTRACT(dow from " + expression + ")::varchar");
				throw new Exception();
			case SQL_HOUR:
				throw new Exception();
			case SQL_MINUTE:
				throw new Exception();
			case SQL_SECOND:
				throw new Exception();
			default:
				return "";
			}
	}

	/**
   * Restituisce una stringa contenente il carattere necessario, in base al tipo
   * di server DBMS, per il concatenamento delle stringhe nelle query SQL.
   * 
   * @return Carattere di concatenazione delle stringhe.
   */
	public String concString() {
		return ("||");
	}
	
	public String dateAdd(int datePart, String queryIncrement, String dataField) {
		
			switch (datePart) {
			case SQL_DAY:
				return (dataField + "+ interval '1 day' * " + queryIncrement);
			case SQL_MONTH:
				return (dataField + "+ interval '1 month' * " + queryIncrement);
			case SQL_YEAR:
				return (dataField + "+ interval '1 year' * " + queryIncrement);
			case SQL_HOUR:
				return (dataField + "+ interval '1 hour' * " + queryIncrement);
			case SQL_MINUTE:
				return (dataField + "+ interval '1 minute' * " + queryIncrement);
			case SQL_SECOND:
				return (dataField + "+ interval '1 second' * " + queryIncrement);
			default:
				return ("");
			}
	}

	/**
   * Restituisce il comando SQL che permette di sommare un intervallo ad una
   * data.
   * 
   * @param datePart Unità di misura dell'incremento (anni,mesi, giorni, ore,
   *          minuti secondi) Si possono usare le costanti SQL_DAY, SQL_MONTH,
   *          etc.
   * @param increment Quantità da aggiungere alla parte specificata da DatePart.
   * @param dataField Data cui va sommanto l'incremento.
   * @return Espressione SQL che effettua l'incremento della data secondo quanto
   *         specificato nei parametri di ingresso.
   * 
   * @throws UnsupportedDatabaseException Nel caso che il dbmsType non sia fra
   *           quelli riconosciuti
   */
	public String dateAdd(int datePart, long increment, String dataField) {		
			switch (datePart) {
			case SQL_DAY:
				return (dataField + "+ interval '" + increment + "' day");
			case SQL_MONTH:
				return (dataField + "+ interval '" + increment + "' month");
			case SQL_YEAR:
				return (dataField + "+ interval '" + increment + "' year");
			case SQL_HOUR:
				return (dataField + "+ interval '" + increment + "' hour");
			case SQL_MINUTE:
				return (dataField + "+ interval '" + increment + "' minute");
			case SQL_SECOND:
				return (dataField + "+ interval '" + increment + "' second");
			default:
				return ("");
			}
	}

	/**
   * Vedi {@link #dateAdd(int datePart, long increment, String dataField)}.
   * 
   * @param datePart Unità di misura dell'incremento (anni,mesi, giorni, ore,
   *          minuti secondi) Si possono usare le costanti SQL_DAY, SQL_MONTH,
   *          etc.
   * @param increment Quantità da aggiungere alla parte specificata da DatePart.
   * @param dateTime data/ora a cui va sommato l'incremento
   * @return espressione SQL che effettua l'incremento della data secondo quanto
   *         specificato nei parametri di ingresso.
   */
	public String dateAdd(int datePart, long increment, Calendar dateTime) {
		if (dateTime == null)
			return null;
		return dateAdd(0, 0, dateTime.getTime());
	}

	/**
   * Vedi {@link #dateAdd(int datePart, long increment, String dataField)}.
   * 
   * @param datePart Unità di misura dell'incremento (anni,mesi, giorni, ore,
   *          minuti secondi) Si possono usare le costanti SQL_DAY, SQL_MONTH,
   *          etc.
   * @param increment Quantità da aggiungere alla parte specificata da DatePart.
   * @param dateTime data/ora a cui va sommato l'incremento
   * @return espressione SQL che effettua l'incremento della data secondo quanto
   *         specificato nei parametri di ingresso.
   */
	public String dateAdd(int datePart, long increment, Date dateTime) {
		if (dateTime == null)
			return null;
		DateFormat df = new SimpleDateFormat(FORMAT_DATETIME);
		return dateAdd(0, 0, df.format(dateTime));
	}

	/**
   * Restituisce il comando SQL, in base al DB adottato, che esegue la
   * differenza tra due date espressa nella unità di misura voluta (anni, mesi,
   * giorni, etc.).
   * 
   * @param datePart Unità di misura con cui viene espressa la differenza (anni,
   *          mesi, giorni, ore, minuti, secondi). Si possono usare le costanti
   *          SQL_DAY, SQL_MONTH, etc.
   * @param dataField1 data da cui viene effettuata la sottrazione.
   * @param dataField2 data da sottrarre. quando è false no.
   * @return String Stringa preformattata SQL che esegue e la differenza tra
   *         date.
	 * @throws Exception 
   * 
   * @throws UnsupportedDatabaseException Nel caso che il dbmsType non sia fra
   *           quelli riconosciuti
   */
	public String dateDiff(int datePart, String dataField1, String dataField2) throws Exception {
		//la funzione datediff e` nella lista delle funzioni necessarie all'applicativo,
		//ogni database ha la sua implementazione. 
		switch (datePart) {
		case SQL_DAY:
			return ("DATEDIFF("+castAs("'DD'", getVarchar()+"(2)")+", "+ castAs(dataField1, getTimestamp()) + ", " + castAs(dataField2, getTimestamp()) + ")");
		case SQL_MONTH:
			return ("DATEDIFF("+castAs("'MM'", getVarchar()+"(2)")+"," + castAs(dataField1, getTimestamp()) + "," + castAs(dataField2, getTimestamp()) + ")");
		case SQL_YEAR:
			return ("DATEDIFF("+castAs("'YY'", getVarchar()+"(2)")+"," + castAs(dataField1, getTimestamp()) + "," + castAs(dataField2, getTimestamp()) + ")");
		case SQL_HOUR:
			return ("DATEDIFF("+castAs("'HH'", getVarchar()+"(2)")+"," + castAs(dataField1, getTimestamp()) + "," + castAs(dataField2, getTimestamp()) + ")");
		case SQL_MINUTE:
			return ("DATEDIFF("+castAs("'MI'", getVarchar()+"(2)")+"," + castAs(dataField1, getTimestamp()) + "," + castAs(dataField2, getTimestamp()) + ")");
		case SQL_SECOND:
			return ("DATEDIFF("+castAs("'SS'", getVarchar()+"(2)")+"," + castAs(dataField1, getTimestamp()) + "," + castAs(dataField2, getTimestamp()) + ")");
		default:
			throw new Exception();
		}
	}

	/**
   * Vedi {@link #dateDiff(int datePart, String dataField1, String dataField2)}.
   * 
   * @param datePart Unità di misura con cui viene espressa la differenza (anni,
   *          mesi, giorni, ore, minuti, secondi). Si possono usare le costanti
   *          SQL_DAY, SQL_MONTH, etc.
   * @param dataField1 data da cui viene effettuata la sottrazione.
   * @param dataField2 data da sottrarre. quando è false no.
   * @return String Stringa preformattata SQL che esegue e la differenza tra
   *         date.
	 * @throws Exception 
   */
	public String dateDiff(int datePart, Calendar dataField1, String dataField2) throws Exception {
		return dateDiff(datePart, writeDate(dataField1), dataField2);
	}

	/**
   * Vedi {@link #dateDiff(int datePart, String dataField1, String dataField2)}.
   * 
   * @param datePart Unità di misura con cui viene espressa la differenza (anni,
   *          mesi, giorni, ore, minuti, secondi). Si possono usare le costanti
   *          SQL_DAY, SQL_MONTH, etc.
   * @param dataField1 data da cui viene effettuata la sottrazione.
   * @param dataField2 data da sottrarre. quando è false no.
   * @return String Stringa preformattata SQL che esegue e la differenza tra
   *         date.
	 * @throws Exception 
   */
	public String dateDiff(int datePart, String dataField1, Calendar dataField2) throws Exception {
		return dateDiff(datePart, dataField1, writeDate(dataField2));
	}

	/**
   * Vedi {@link #dateDiff(int datePart, String dataField1, String dataField2)}.
   * 
   * @param datePart Unità di misura con cui viene espressa la differenza (anni,
   *          mesi, giorni, ore, minuti, secondi). Si possono usare le costanti
   *          SQL_DAY, SQL_MONTH, etc.
   * @param dataField1 data da cui viene effettuata la sottrazione.
   * @param dataField2 data da sottrarre. quando è false no.
   * @return String Stringa preformattata SQL che esegue e la differenza tra
   *         date.
	 * @throws Exception 
   */
	public String dateDiff(int datePart, Calendar dataField1, Calendar dataField2) throws Exception {
		return dateDiff(datePart, writeDate(dataField1), writeDate(dataField2));
	}

	/**
   * Il metodo formatta i campi passati come parametri in un funzione per la
   * verifica dell'overlap di due segmenti temporali,ignorando l'uguaglianza di
   * inizio/fine.
   * 
   * @param fieldFromA Indica la data inizio del segmento A.
   * @param fieldToA Indica la data fine del segmento A.
   * @param fieldFromB Indica la data inizio del segmento B.
   * @param fieldToB Indica la data fine del segmento B.
   * @return la clausola costruita per la verifica dell'overLap.
   */
	public String dateOverLap(String fieldFromA, String fieldToA, String fieldFromB, String fieldToB) {
		// richiamo la funzione con la ricerca di intersezione reale
		return dateOverLap(fieldFromA, fieldToA, fieldFromB, fieldToB, true);
	}

	/**
   * Il metodo formatta i campi passati come parametri in un funzione SQL per la
   * verifica dell'overlap di due segmenti temporali. a b c d |-------|
   * |-------| overLap = (a < d) And (b > c)
   * 
   * @param fieldFromA Indica la data inizio del segmento A.
   * @param fieldToA Indica la data fine del segmento A.
   * @param fieldFromB Indica la data inizio del segmento B.
   * @param fieldToB Indica la data fine del segmento B.
   * @param strictOverLap True:Il metodo verifica la reale intersezione dei
   *          segmenti, ignorando l'uguaglianza di inizio/fine.
   * @return la clausola costruita per la verifica dell'overLap.
   */
	public String dateOverLap(String fieldFromA, String fieldToA, String fieldFromB, String fieldToB, boolean strictOverLap) {
		//(fieldFromA <= fieldToB)  and  (fieldToA >= fieldFromB)
		StringBuilder query = new StringBuilder();
		query.append(" ( ")
		     .append(fieldFromA).append(strictOverLap ? " < " : " <= ").append(fieldToB).append(" AND ")
		     .append(fieldToA).append(strictOverLap ? " > " : " >= ").append(fieldFromB)
		     .append(" ) ");
		return query.toString();
	}

	/**
   * Vedi
   * {@link #dateOverLap(String fieldFromA, String fieldToA, String fieldFromB, String fieldToB,boolean strictOverLap)}.
   * 
   * @param fieldFromA Indica la data inizio del segmento A.
   * @param fieldToA Indica la data fine del segmento A.
   * @param fieldFromB Indica la data inizio del segmento B.
   * @param fieldToB Indica la data fine del segmento B.
   * @param strictOverLap True:Il metodo verifica la reale intersezione dei
   *          segmenti, ignorando l'uguaglianza di inizio/fine.
   * @return la clausola costruita per la verifica dell'overLap.
   */
	public String dateOverLap(Calendar fieldFromA, Calendar fieldToA, Calendar fieldFromB, Calendar fieldToB, boolean strictOverLap) {
		return dateOverLap(writeDate(fieldFromA), writeDate(fieldToA), writeDate(fieldFromB), writeDate(fieldToB), strictOverLap);
	}
	
  
	public String dateOverLap(Date fieldFromA, Date fieldToA, Date fieldFromB, Date fieldToB, boolean strictOverLap) {
		return dateOverLap(writeDate(fieldFromA), writeDate(fieldToA), writeDate(fieldFromB), writeDate(fieldToB), strictOverLap);
	}	

	/**
	 * Con le query con PreparedStaementBuilder usare : psb.addBindVariabile("", Commons.dayEnd(date));
   * Vedi {@link #dayEnd(Calendar date)}.
   * 
   * @param in Stringa contenente la data a cui aggiungere l'ora di fine
   *          giornata.
   * @return String Stringa contenente il comando SQL per impostare alla
   *         mezzanotte l'orario della data passata come parametro.
   */
	/*
	public String dayEnd(Date in) {
		return dayEnd(Commons.formatDate(in));
	}
*/
	/**
	 * Con le query con PreparedStaementBuilder usare : psb.addBindVariabile("", Commons.dayEnd(date));
   * Restituisce una stringa SQL che provvede a formattare la data passata come
   * parametro in modo che abbia un orario coincidente con la mezzandotte.
   * 
   * @param in Stringa contenente la data a cui aggiungere l'ora di fine
   *          giornata.
   * @return String Stringa contenente il comando SQL per impostare alla
   *         mezzanotte l'orario della data passata come parametro.
   */
	/*
	public String dayEnd(Calendar in) {
		if (in == null)
			return null;
		return dayEnd(in.getTime());
	}
*/
	/**
	 * Con le query con PreparedStaementBuilder usare : psb.addBindVariabile("", Commons.dayEnd(date));
   * Restituisce una stringa SQL che provvede a formattare la data passata come
   * parametro in modo che abbia un orario coincidente con la mezzandotte.
   * 
   * @param in Stringa contenente la data a cui aggiungere l'ora di fine
   *          giornata.
   * @return String Stringa contenente il comando SQL per impostare alla
   *         mezzanotte l'orario della data passata come parametro.
   * 
   * @throws IllegalArgumentException Viene sollevata una Exception se la data
   *           passata come parametro è non valida (non è "dd/MM/yyyy").
   * 
   * @throws UnsupportedDatabaseException Nel caso che il dbmsType non sia fra
   *           quelli riconosciuti
   */
	/*
	private String dayEnd(String in) {
		DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
		if (dbms == Commons.DB_SQLSERVER) {
			try {
				formatter1.parse(in);
				return ("'" + formatter1.format(formatter1.parse(in)) + " 11:59:59PM'");
			} catch (Exception e) {
				throw new IllegalArgumentException("Formato della data non valido!");
			}
		} else if (dbms == Commons.DB_ORACLE) {
			try {
				formatter1.parse(in);
				return ("TO_DATE('" + formatter1.format(formatter1.parse(in)) + " 23:59:59','DD/MM/YYYY HH24:MI:SS')");
			} catch (Exception e1) {
				throw new IllegalArgumentException("Formato della data non valido!");
			}
		} else if (dbms == Commons.DB_POSTGRESQL) {
			try {
				formatter1.parse(in);
				return ("TO_TIMESTAMP('" + formatter1.format(formatter1.parse(in)) + " 23:59:59','DD/MM/YYYY HH24:MI:SS')");
			} catch (Exception e1) {
				throw new IllegalArgumentException("Formato della data non valido!");
			}
		} else {
			throw new UnsupportedDatabaseException();
		}
	}
*/
	
	/**
   * Vedi {@link #dayStart(Calendar date)}.
   * Con le query con PreparedStaementBuilder usare : psb.addBindVariabile("", date, true);
   * @param in Stringa contenente la data a cui aggiungere l'ora di inizio
   *          giornata.
   * @return String Stringa SQL che provvede a formattare la data passata come
   *         parametro in modo che abbia orario pari a quello di inizio
   *         giornata.
   * @throws IllegalArgumentException Genera una Exception se il formato della
   *           data passata come parametro è errato (non è "dd/MM/yyyy").
   */
	
	/*public String dayStart(Date in) {
		return dayStart(Commons.formatDate(in));
	}
*/
	/**
	 * Con le query con PreparedStaementBuilder usare : psb.addBindVariabile("", date, true);
   * Restituisce una stringa SQL che provvede a formattare la data, passata come
   * parametro, in modo che abbia un orario coincidente a quello di inizio
   * giornata.
   * 
   * @param in Stringa contenente la data a cui aggiungere l'ora di inizio
   *          giornata.
   * @return String Stringa SQL che provvede a formattare la data passata come
   *         parametro in modo che abbia orario pari a quello di inizio
   *         giornata.
   * @throws IllegalArgumentException Genera una Exception se il formato della
   *           data passata come parametro è errato (non è "dd/MM/yyyy").
   */
	
	/*public String dayStart(Calendar in) {
		if (in == null)
			return null;
		return dayStart(in.getTime());
	}
*/
	/**
	 * Con le query con PreparedStaementBuilder usare : psb.addBindVariabile("", date, true);
   * Restituisce una stringa SQL che provvede a formattare la data, passata come
   * parametro di formato stringa, in modo che abbia un orario coincidente a
   * quello di inizio giornata.
   * 
   * @param in Oggetto Date contenente la data a cui aggiungere l'ora di inizio
   *          giornata.
   * @return String Stringa SQL che provvede a formattare la data passata come
   *         parametro in modo che abbia orario pari a quello di inizio
   *         giornata.
   * 
   * @throws IllegalArgumentException Genera una Exception se il formato della
   *           data passata come parametro è errato (non è "dd/MM/yyyy").
   * 
   * @throws UnsupportedDatabaseException Nel caso che il dbmsType non sia fra
   *           quelli riconosciuti
   */
	/*protected String dayStart(String in) {
		DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");

		if (dbms == Commons.DB_SQLSERVER) {
			try {
				formatter1.parse(in);
				return ("'" + formatter1.format(formatter1.parse(in)) + " 12:00:00AM'");
			} catch (Exception ex) {
				throw new IllegalArgumentException("Formato della data non valido!");
			}
		} else if (dbms == Commons.DB_ORACLE) {
			try {
				formatter1.parse(in);
				return ("TO_DATE('" + formatter1.format(formatter1.parse(in)) + " 00:00:00','DD/MM/YYYY HH24:MI:SS')");
			} catch (Exception ex1) {
				throw new IllegalArgumentException("Formato della data non valido!");
			}			
		}  else if (dbms == Commons.DB_POSTGRESQL) {
			try {
				formatter1.parse(in);
				return ("TO_TIMESTAMP('" + formatter1.format(formatter1.parse(in)) + " 00:00:00','DD/MM/YYYY HH24:MI:SS')");
			} catch (Exception ex1) {
				throw new IllegalArgumentException("Formato della data non valido!");
			}
		} else {
			throw new UnsupportedDatabaseException();
		}
	}
*/
	/**
   * Costruisce la clausola formattata nel seguente modo: NOME_CAMPO IN
   * (list[0], ... list[n-1])
   * 
   * @param fieldName Nome del campo interessato alla clausola IN
   * @param list Contiene la lista di valori per la clausola IN
   * @return Clausola formattata nel seguente modo: NOME_CAMPO IN (list[0], ...
   *         list[n-1])
   */
	
public String inClause(String fieldName, StringList rawList) {
		
		//Validazione parametri in ingresso
		if (rawList==null  || rawList.size() == 0 || StringUtils.isEmpty(fieldName)) {
			return " 1=0 ";
		}
		
		//Rimuovo dalla lista i null per evitare "in ('X', NULL)" o "in (NULL)" (il quale potrebbe, da solo, innescare full scan della tabella)
		StringList cleanList = new StringList();
		for (String itm : rawList) {
			if (itm!=null) {
				cleanList.add(itm);
			}
		}
		
		// Controllo che siano presenti i dati necessari per costruire lo statement IN(...)
		// se richiesto in(NULL), la query restituirà un insieme vuoto forzando "1=0"
		// se richiesto in con un numero di elementi maggiore del limite viene lanciata eccezione
		if (cleanList.size() == 0) {
			return " 1=0 ";
		} else if (cleanList.size() > IN_CLAUSE_MAX_TOTAL_ELEMENT) {
			throw new RuntimeException(IN_CLAUSE_MAX_TOTAL_ELEMENT_ERROR);
		}
		
		// variabili
		StringBuffer bufIn = new StringBuffer(1024);
		String strApp;
		int bufferCount = 0;
		int element = IN_CLAUSE_MAX_ELEMENT;

		// Inizio costruzione IN CLAUSE
		bufIn.append(" " + fieldName + " IN (");
		for (int i = 0; i < cleanList.size(); i++) {
			// verifico se bisogna spezzettare la clausola IN in + parti
			if (((i / element) > 0) && ((i / element) > bufferCount)) {
				// elimino l'ultima virgola
				strApp = bufIn.toString().substring(0, bufIn.length() - 2) + ") ";
				// reinizializzo il buffer con la clausola corretta
				bufIn = new StringBuffer(strApp);
				// aggiungo una condizione di OR
				bufIn.append(" OR " + fieldName + " IN (");
				// allineo il contatore
				bufferCount = (i / element);
			}
			// aggiungo l'elemento alla clausola IN
			bufIn.append(writeString(cleanList.get(i)) + ", ");
		}
		// Termino la stringa contenente la/e clausola/e IN inserendo le opportune parentesi
		strApp = (bufferCount > 0 ? "(" : "") + bufIn.toString().substring(0, bufIn.length() - 2) + ") " + (bufferCount > 0 ? ")" : "");
		// ritorno valore
		return strApp;
	}
	

	//PRODUCE COMANDO NON COMPILATO.. NON USARE
//	/**
//   * Costruisce la clausola formattata nel seguente modo: NOME_CAMPO IN
//   * (list[0], ... list[n-1])
//   * 
//   * @param fieldName Nome del campo interessato alla clausola IN
//   * @param list Contiene la lista di valori per la clausola IN
//   * @return Clausola formattata nel seguente modo: NOME_CAMPO IN (list[0], ...
//   *         list[n-1])
//   */
//	public String inClauseStringSet(String fieldName, Set<String> list) {
//		StringList stringList = new StringList();
//		stringList.addAll(list);
//		return inClause(fieldName, stringList);
//	}

	//PRODUCE COMANDO NON COMPILATO.. NON USARE
//	/**
//	   * Costruisce la clausola formattata nel seguente modo: NOME_CAMPO NOT IN
//	   * (list[0], ... list[n-1])
//	   * 
//	   * @param fieldName Nome del campo interessato alla clausola NOT IN
//	   * @param list Contiene la lista di valori per la clausola NOT IN
//	   * @return Clausola formattata nel seguente modo: NOME_CAMPO NOT IN (list[0], ...
//	   *         list[n-1])
//	   */
//	public String notInClauseStringSet(String fieldName, Set<String> list) {
//		StringList stringList = new StringList();
//		stringList.addAll(list);
//		return notInClause(fieldName, stringList);
//	}
		
	/**
   * Costruisce la clausola formattata nel seguente modo: NOME_CAMPO IN
   * (list[0], ... list[n-1])
   * 
   * @param fieldName Nome del campo interessato alla clausola IN
   * @param list Contiene la lista di valori per la clausola IN
   * @return Clausola formattata nel seguente modo: NOME_CAMPO IN (list[0], ...
   *         list[n-1])
   */
	public String inClauseInteger(String fieldName, StringList list) {
		// variabili
		StringBuffer bufIn = new StringBuffer(1024);
		String strApp;
		int bufferCount = 0;
		int element = IN_CLAUSE_MAX_ELEMENT;
		
		// Controllo che siano presenti i dati necessari per costruire lo statement IN(...)
		// se richiesto in(NULL), la query restituirà un insieme vuoto forzando "1=0"
		// se richiesto in con un numero di elementi maggiore del limite viene lanciata eccezione
		if (list == null || list.size() == 0 || fieldName == null || fieldName.trim().length() == 0) {
			return " 1=0 ";
		} else if (list.size() > IN_CLAUSE_MAX_TOTAL_ELEMENT) {
			throw new RuntimeException(IN_CLAUSE_MAX_TOTAL_ELEMENT_ERROR);
		}

		// Inizio costruzione IN CLAUSE
		bufIn.append(" " + fieldName + " IN (");
		for (int i = 0; i < list.size(); i++) {
			// verifico se bisogna spezzettare la clausola IN in + parti
			if (((i / element) > 0) && ((i / element) > bufferCount)) {
				// elimino l'ultima virgola
				strApp = bufIn.toString().substring(0, bufIn.length() - 2) + ") ";
				// reinizializzo il buffer con la clausola corretta
				bufIn = new StringBuffer(strApp);
				// aggiungo una condizione di OR
				bufIn.append(" OR " + fieldName + " IN (");
				// allineo il contatore
				bufferCount = (i / element);
			}
			// aggiungo l'elemento alla clausola IN
			bufIn.append(writeInteger(Integer.parseInt(list.get(i))) + ", ");
		}
		// Termino la stringa contenente la/e clausola/e IN inserendo le opportune parentesi
		strApp = (bufferCount > 0 ? "(" : "") + bufIn.toString().substring(0, bufIn.length() - 2) + ") " + (bufferCount > 0 ? ")" : "");
		// ritorno valore
		return strApp;
	}

	public String inClause(String fieldName, IntegerList list) {
		return inClause(fieldName, list, true);
	}
	
	/**PRODUCE COMANDO NON COMPILATO.. NON USARE CON PreparedStatementBuilder 
	 * Usare addBindVariable passando new IntegerList(Set<Integer> list);
	 * @param fieldName
	 * @param list
	 * @return
	 */
	@Deprecated
	public String inClauseIntegerSet(String fieldName, Set<Integer> list) {
		IntegerList integerList = new IntegerList();
		integerList.addAll(list);
		return inClause(fieldName, integerList, true);
	}
	
	/**
   * Costruisce la clausola formattata nel seguente modo: NOME_CAMPO IN
   * (list[0], ... list[n-1])
   * 
   * @param fieldName Nome del campo interessato alla clausola IN
   * @param list Contiene la lista di valori per la clausola IN
   * @return Clausola formattata nel seguente modo: NOME_CAMPO IN (list[0], ...
   *         list[n-1])
   */
	public String inClause(String fieldName, IntegerList rawList, boolean include) {
		//Validazione parametri in ingresso
		if (rawList==null || rawList.size() == 0 || StringUtils.isEmpty(fieldName)) {
			return " 1=0 ";
		}
		
		//Rimuovo dalla lista i null per evitare "in ('X', NULL)" o "in (NULL)" (il quale potrebbe, da solo, innescare full scan della tabella)
		IntegerList cleanList = new IntegerList();
		for (Integer itm : rawList) {
			if (itm!=null) {
				cleanList.add(itm);
			}
		}
		
		// Controllo che siano presenti i dati necessari per costruire lo statement IN(...)
		// se richiesto in(NULL), la query restituirà un insieme vuoto forzando "1=0"
		// se richiesto in con un numero di elementi maggiore del limite viene lanciata eccezione
		if (cleanList.size() == 0) {
			return " 1=0 ";
		} else if (cleanList.size() > IN_CLAUSE_MAX_TOTAL_ELEMENT) {
			throw new RuntimeException(IN_CLAUSE_MAX_TOTAL_ELEMENT_ERROR);
		}
		
		// variabili
		StringBuffer bufIn = new StringBuffer(1024);
		String strApp, op, join;
		int bufferCount = 0;
		int element = IN_CLAUSE_MAX_ELEMENT;
		
		if( include ){
			op = " IN ";
			join = " OR ";
		}else{
			op = " NOT IN ";
			join = " AND ";
		}
		
		// Inizio costruzione IN CLAUSE
		bufIn.append(" " + fieldName + " "+op+" (");
		for (int i = 0; i < cleanList.size(); i++) {
			// verifico se bisogna spezzettare la clausola IN in + parti
			if (((i / element) > 0) && ((i / element) > bufferCount)) {
				// elimino l'ultima virgola
				strApp = bufIn.toString().substring(0, bufIn.length() - 2) + ") ";
				// reinizializzo il buffer con la clausola corretta
				bufIn = new StringBuffer(strApp);
				// aggiungo una condizione di OR
				bufIn.append(" "+join+" " + fieldName + " "+op+" (");
				// allineo il contatore
				bufferCount = (i / element);
			}
			// aggiungo l'elemento alla clausola IN
			bufIn.append(writeInteger(cleanList.get(i)) + ", ");
		}
		// Termino la stringa contenente la/e clausola/e IN inserendo le opportune parentesi
		strApp = (bufferCount > 0 ? "(" : "") + bufIn.toString().substring(0, bufIn.length() - 2) + ") " + (bufferCount > 0 ? ")" : "");
		// ritorno valore
		return strApp;
	}

	/** Costruisce la clausola NOT IN con tanti '?' quanti sono gli elementi nella lista 
	 * 	Se il numero di elementi supera il limite massimo, lo spezza in N clausule di AND
	 **/
	public String notInClauseForBindVariable(String fieldName, Integer listSize) {
		return inClauseForBindVariable(fieldName, listSize, false);
	}
	
	/** Costruisce la clausola IN con tanti '?' quanti sono gli elementi nella lista 
	 * Se il numero di elementi supera il limite massimo, lo spezza in N clausule di OR
	 **/
	public String inClauseForBindVariable(String fieldName, Integer listSize) {
		return inClauseForBindVariable(fieldName, listSize, true);
	}
	
	/** Costruisce la clausola IN con tanti '?' quanti sono gli elementi nella lista 
	 * Se il numero di elementi supera il limite massimo, lo spezza in N clausule di OR
	 * **/
	private String inClauseForBindVariable(String fieldName, Integer listSize, boolean include) {
		// variabili
		StringBuffer bufIn = new StringBuffer(1024);

		int bufferCount = 0;
		int element = IN_CLAUSE_MAX_ELEMENT;
		
		// Controllo che siano presenti i dati necessari per costruire lo statement IN(...)
		// se richiesto in(NULL), la query restituirà un insieme vuoto forzando "1=0"
		// se richiesto in con un numero di elementi maggiore del limite viene lanciata eccezione
		if (listSize == null || listSize == 0 || StringUtils.isEmpty(fieldName)) {
			return " 1=0 ";
		} else if (listSize > IN_CLAUSE_MAX_TOTAL_ELEMENT) {
			throw new RuntimeException(IN_CLAUSE_MAX_TOTAL_ELEMENT_ERROR);
		}				
		
		//Quando un solo elemento, è più performante utilizzare equal
		if (listSize==1) {
			bufIn.append(" ");
			bufIn.append(fieldName);
			if (include) {
				bufIn.append(" = ");			
			} else {
				bufIn.append(" != ");
			}
			bufIn.append(" ? ");
			return bufIn.toString();
		}
		
		//Variabile per definire se si tratta di un include o un exclude
		String strInclude = " IN ";
		if (!include)
			strInclude = " NOT IN ";
		
		//Separatore dei blocchi di elementi in caso di un include o un exclude
		String strElemSep = " OR ";
		if (!include)
			strElemSep = " AND ";
		
		String strApp;
		
		// Inizio costruzione IN CLAUSE
		bufIn.append(" ").append(fieldName).append(strInclude).append(" ( ");
		for (int i = 0; i < listSize; i++) {
			// verifico se bisogna spezzettare la clausola IN in + parti
			if (((i / element) > 0) && ((i / element) > bufferCount)) {
				// elimino l'ultima virgola
				strApp = bufIn.toString().substring(0, bufIn.length() - 2) + ") ";
				// reinizializzo il buffer con la clausola corretta
				bufIn = new StringBuffer(strApp);
				// aggiungo una condizione di OR
				bufIn.append(strElemSep).append(fieldName).append(strInclude).append(" ( ");
				// allineo il contatore
				bufferCount = (i / element);
			}
			// aggiungo '?' alla clausola IN
			bufIn.append("? , ");
		}
		// Termino la stringa contenente la/e clausola/e IN inserendo le opportune parentesi
		strApp = (bufferCount > 0 ? "(" : "") + bufIn.toString().substring(0, bufIn.length() - 2) + ") " + (bufferCount > 0 ? ")" : "");
		// ritorno valore
		return strApp;
	}
	
	/**
   * Restituisce il testo SQL per convertire un oggetto Date in una data e ora,
   * formattata come "dd/MM/yyyy HH:mm:ss"
   * 
   * @param in il valore da preparare
   * @return la stringa da scrivere su database
   */
	public String writeDate(Date in) {
		if (in == null) {
			return "NULL";
		}
		return writeDate(Commons.formatDateTime(in));
	}

	/**
	   * Restituisce il testo SQL per convertire un oggetto Date in una data e ora,
	   * formattata come "dd-MM-yy"
	   * 
	   * @param in il valore da preparare
	   * @return la stringa da scrivere su database
	   */
		public String writeSimpleDate(Date in) {
			if (in == null) {
				return "NULL";
			}
			return writeSimpleDate(Commons.formatSimpleDateTime(in));
		}
	
	/**
   * Restituisce il testo SQL per convertire un oggetto Calendar in una data e
   * ora, formattata come "dd/MM/yyyy HH:mm:ss"
   * 
   * @param in il valore da preparare
   * @return la stringa da scrivere su database
   */
	public String writeDate(Calendar in) {
		if (in == null) {
			return "NULL";
		}
		return writeDate(in.getTime());
	}

	/**Usare PreparedStatementBuilder e queries compilate
   * Restituisce il testo SQL rappresentativo della data e ora di sistema
   * 
   * @param in il valore da preparare
   * @return la stringa da scrivere su database
   */
	@Deprecated
	public String writeSystemDate(Date in) {
		if (in == null) {
			return "NOW()";
		}
		return writeDate(Commons.formatDateTime(in));
	}
	
	/**Usare PreparedStatementBuilder e queries compilate
   * Restituisce il testo SQL per convertire un oggetto Calendar in una data e
   * ora, formattata come "dd/MM/yyyy HH:mm:ss"
   * 
   * @param in il valore da preparare
   * @return la stringa da scrivere su database
   */
	@Deprecated
	public String writeSystemDate(Calendar in) {
		Date dt = null;
		if (in!=null) {
			dt = in.getTime();
		}
		return writeSystemDate(dt);
	
//		if (in == null) {
//			if (dbms == Commons.DB_SQLSERVER) {
//				return "GETDATE()";
//			} else if (dbms == Commons.DB_ORACLE) {
//				return "SYSDATE";
//			}else if(dbms == Commons.DB_POSTGRESQL){
//				return "NOW()";
//			}
//		}
//		return writeDate(in.getTime());
	}
		
	/**
   * Il metodo formatta un campo per gestire il valore di default nel caso dei
   * null
   * 
   * @param fieldName nome campo es:(F28_IMPORTO oppure SUM(F28_IMPORTO))
   * @param defValue valore di default da attribuire al campo in caso di NULL
   * @return formattazione sql per SQL Server e Oracle
   *         (Oracle:NVL(FIELD_NAME,DEF_VALUE); SQL Server
   *         ISNULL(FIELD_NAME,DEF_VALUE)).
   */
	public String isNull(String fieldName, String defValue) {
		return " COALESCE(" + fieldName + "," + defValue + ") ";
	}

	/**
   * Metodo che restituisce la funzione SQL per rimuovere gli spazi (blank)
   * eventualmente presenti nella parte iniziale di una espressione.
   * 
   * @param expression Espressione da cui rimuovere gli spazi bianchi.
   * @return Stringa in formato SQL, dipendente dal dbms, che contiene il
   *         comando per rimuovere i blank presenti all'inizio dell'espressione.
   */
	public String lTrim(String expression) {
		return ("");
	}

	/**
   * Metodo che restituisce il testo SQL per rimuovere gli spazi (blank)
   * eventualmente presenti nella parte finale di una espressione.
   * 
   * @param expression Espressione da cui rimuovere gli spazi bianchi.
   * @return Stringa in formato SQL, dipendente dal dbms, che contiene il
   *         comando per rimuovere i blank presenti alla fine dell'espressione.
   */
	public String rTrim(String expression) {
		return ("");
	}

	/**
   * Restituisce una stringa che contiene la clausola SQL usata per trasformare
   * l'espressione fornita come paramentro tutta in maiuscole.
   * 
   * @param expression l'espressione che deve essere trasfomata.
   * @return una clausola di codice SQL
   */
	public String toUpper(String expression) {
		return ("UPPER(" + expression + ")");
	}

	/**
   * Restituisce una stringa che contiene la clausola SQL usata per trasformare
   * l'espressione fornita come paramentro tutta in minuscole.
   * 
   * @param expression l'espressione che deve essere trasfomata.
   * @return una clausola di codice SQL
   */
	public String toLower(String expression) {
		return ("");
	}

	/**
   * Restituisce la funzione SQL per estrarre una sottostringa dall'espressione
   * passata come parametro, indicando la posione iniziale e la lunghezza della
   * sottostringa da estrarre.
   * 
   * @param expression Espressione da cui estrarre la sottostringa.
   * @param start Posizione iniziale da cui estrarre la sottostringa.
   * @param length Lunghezza della sotostringa da estrarre.
   * @return Stringa contenente la funzione SQL per estrarre una sottostringa.
   */
	public String subString(String expression, int start, int length) {
		return ("");
	}

	/**
   * Costruisce la clausola formattata nel seguente modo: NOME_CAMPO NOT IN
   * (list[0], ... list[n-1])
   * 
   * @param fieldName Nome del campo interessato alla clausola IN
   * @param list Contiene la lista di valori per la clausola IN
   * @return Clausola formattata nel seguente modo: NOME_CAMPO IN (list[0], ...
   *         list[n-1])
   * @author not attributable
   */
	public String notInClause(String fieldName, StringList list) {
		// variabili
		StringBuffer bufIn = new StringBuffer(1024);
		String strApp;
		int bufferCount = 0;
		int element = IN_CLAUSE_MAX_ELEMENT;
		
		// Controllo che siano presenti i dati necessari per costruire lo statement IN(...)
		// se richiesto in(NULL), la query restituirà un insieme vuoto forzando "1=0"
		// se richiesto in con un numero di elementi maggiore del limite viene lanciata eccezione
		if (list == null || list.size() == 0 || fieldName == null || fieldName.trim().length() == 0) {
			return " 1=0 ";
		} else if (list.size() > IN_CLAUSE_MAX_TOTAL_ELEMENT) {
			throw new RuntimeException(IN_CLAUSE_MAX_TOTAL_ELEMENT_ERROR);
		}
		
		// Inizio costruzione IN CLAUSE
		bufIn.append(" " + fieldName + " NOT IN (");
		for (int i = 0; i < list.size(); i++) {
			// verifico se bisogna spezzettare la clausola IN in + parti
			if (((i / element) > 0) && ((i / element) > bufferCount)) {
				// elimino l'ultima virgola
				strApp = bufIn.toString().substring(0, bufIn.length() - 2) + ") ";
				// reinizializzo il buffer con la clausola corretta
				bufIn = new StringBuffer(strApp);
				// aggiungo una condizione di OR
				bufIn.append(" AND " + fieldName + " NOT IN (");
				// allineo il contatore
				bufferCount = (i / element);
			}
			// aggiungo l'elemento alla clausola IN
			bufIn.append(writeString(list.get(i)) + ", ");
		}
		// Termino la stringa contenente la/e clausola/e IN inserendo le opportune
		// parentesi
		strApp = (bufferCount > 0 ? "(" : "") + bufIn.toString().substring(0, bufIn.length() - 2) + ") " + (bufferCount > 0 ? ")" : "");
		// ritorno valore
		return strApp;
	}

	/**
   * Costruisce la clausola formattata nel seguente modo: NOME_CAMPO NOT IN
   * (list[0], ... list[n-1])
   * 
   * @param fieldName Nome del campo interessato alla clausola IN
   * @param list Contiene la lista di valori per la clausola IN
   * @return Clausola formattata nel seguente modo: NOME_CAMPO IN (list[0], ...
   *         list[n-1])
   */
	public String notInClause(String fieldName, IntegerList list) {
		// variabili
		StringBuffer bufIn = new StringBuffer(1024);
		String strApp;
		int bufferCount = 0;
		int element = IN_CLAUSE_MAX_ELEMENT;
		
		// Controllo che siano presenti i dati necessari per costruire lo statement IN(...)
		// se richiesto in(NULL), la query restituirà un insieme vuoto forzando "1=0"
		// se richiesto in con un numero di elementi maggiore del limite viene lanciata eccezione
		if (list == null || list.size() == 0 || fieldName == null || fieldName.trim().length() == 0) {
			return " 1=0 ";
		} else if (list.size() > IN_CLAUSE_MAX_TOTAL_ELEMENT) {
			throw new RuntimeException(IN_CLAUSE_MAX_TOTAL_ELEMENT_ERROR);
		}				

		// Inizio costruzione IN CLAUSE
		bufIn.append(" " + fieldName + " NOT IN (");
		for (int i = 0; i < list.size(); i++) {
			// verifico se bisogna spezzettare la clausola IN in + parti
			if (((i / element) > 0) && ((i / element) > bufferCount)) {
				// elimino l'ultima virgola
				strApp = bufIn.toString().substring(0, bufIn.length() - 2) + ") ";
				// reinizializzo il buffer con la clausola corretta
				bufIn = new StringBuffer(strApp);
				// aggiungo una condizione di OR
				bufIn.append(" AND " + fieldName + " NOT IN (");
				// allineo il contatore
				bufferCount = (i / element);
			}
			// aggiungo l'elemento alla clausola IN
			bufIn.append(writeInteger(list.get(i)) + ", ");
		}
		// Termino la stringa contenente la/e clausola/e IN inserendo le opportune
		// parentesi
		strApp = (bufferCount > 0 ? "(" : "") + bufIn.toString().substring(0, bufIn.length() - 2) + ") " + (bufferCount > 0 ? ")" : "");
		// ritorno valore
		return strApp;
	}

	/**
   * Restituisce la istruzione in SQL che si può utilizzare per ottenere la data
   * e l'ora del DBMS server.
   * 
   * @return Stringa SQL contenente il comando per la lettura della data del
   *         DBMS server.
   */
	public String sysDate() {
		return "NOW()";
	}

	public String sysDateTruncate() {		
		return " CURRENT_DATE ";	
	}

	/**
   * Metodo che specifica che si vuole utilizzare un lock per una query di
   * selezione, nella clausola FROM. La modalità UpdateLock consente di indicare
   * al dbms che si ha l'intenzione di aggiornare una tabella. Anche questa
   * funzione, come quelle per le join, va usata sempre unitamente alla
   * updLockClause(). In particolare il formato della query che ne fa uso deve
   * essere del tipo: <BR>
   * "SELECT <campi> FROM " + updLock() + "WHERE " <condizioni> +
   * updLockClause() + "ORDER BY <campi>"
   * 
   * @return Restituisce una stringa SQL che serve per specificare il lock per
   *         la query di selezione.
   */
	public String updLock() {
		return "";
	}

	/**
   * Metodo che specifica che si vuole utilizzare un lock per una query di
   * selezione, nella clausola FROM. La modalità UpdateLock consente di indicare
   * al dbms che si ha l'intenzione di aggiornare una tabella. Anche questa
   * funzione, come quelle per le join, va usata sempre unitamente alla
   * updLock(). In particolare il formato della query che ne fa uso deve essere
   * del tipo: <BR>
   * "SELECT <campi> FROM " + updLock() + "WHERE " <condizioni> +
   * updLockClause() + "ORDER BY <campi>"
   * 
   * @param tables Nome della tabella o tabelle su cui fare il lock.
   * @return Restituisce una stringa SQL che serve per specificare il lock per
   *         la query di selezione.
   */
	public String updLockClause(String tables) {

		if (tables.indexOf(",") == -1) {
				return (" FOR UPDATE ");
			}
			return (" FOR UPDATE OF " + tables);
		
	}

	/**
   * Metodo che specifica che si vuole utilizzare un lock per una query di
   * selezione, nella clausola FROM. La modalità UpdateLock consente di indicare
   * al dbms che si ha l'intenzione di aggiornare una tabella. Anche questa
   * funzione, come quelle per le join, va usata sempre unitamente alla
   * updLock(). In particolare il formato della query che ne fa uso deve essere
   * del tipo: <BR>
   * "SELECT <campi> FROM " + updLock() + "WHERE " <condizioni> +
   * updLockClause() + "ORDER BY <campi>"
   * 
   * @return Restituisce una stringa SQL che serve per specificare il lock per
   *         la query di selezione.
   */
	public String updLockClause() {
		return updLockClause("");
	}
	
	public String limitRowsPostgres(int maxrows) {
		StringBuffer result = new StringBuffer();
		result.append(" LIMIT ").append(maxrows).append(" ");
		return result.toString();
	}

	public String offsetRowsPostgres(int offset) {
		StringBuffer result = new StringBuffer();
		result.append(" OFFSET ").append(offset).append(" ");
		return result.toString();
	}

	/**
   * Ritorna una stringa che rappresenta il valore in ingresso, pronta per
   * essere scritta su database.
   * 
   * @param in il valore da preparare
   * @return la stringa da scrivere su database
   */
	public String writeInteger(Integer in) {
		if (in == null) 
			return "NULL";
		return String.valueOf(in.intValue());
	}
	public String writeInteger(String in){
		if (StringUtils.isEmpty(in)) 
			return "NULL";
		return writeInteger( Integer.parseInt(in) );
	}

	public String writeSmallint(Integer in) {	
		if (in == null) 
			return "NULL";
		return String.valueOf(in.intValue());
	}

	
	public String writeReal(String in){
			//db wants . for decimal separator
			in = in.replaceAll(",",".");;
		
		if( StringUtils.isNotEmpty(in) )
			return "NULL";
		
		return in;
	}
	/**
   * Ritorna una stringa che rappresenta il valore in ingresso, pronta per
   * essere scritta su database.
   * 
   * @param in il valore da preparare
   * @return la stringa da scrivere su database
   */
	public String writeInteger(int in) {
		return String.valueOf(in);
	}
	
	/**
   * Ritorna una stringa che rappresenta il valore in ingresso, pronta per
   * essere scritta su database.
   * 
   * @param in il valore da preparare
   * @return la stringa da scrivere su database
   */
	public String writeLong(Long in) {
		if (in == null) 
			return "NULL";
		return String.valueOf(in);
	}

	/**
   * Ritorna una stringa che rappresenta il valore in ingresso, pronta per
   * essere scritta su database. Il valore viene anche arrotondato sencondo la
   * legge.
   * 
   * @param in double double da preparare
   * @return String stringa da scrivere su database
   */
	public String writeCurrency(double in) {
		double val = Commons.round(in);
		return writeDouble(val);
	}

	/**
   * Ritorna una stringa che rappresenta il valore in ingresso, pronta per
   * essere scritta su database. Il valore viene anche arrotondato secondo la
   * legge.
   * 
   * @param in oggetto Double da preparare
   * @return la stringa da scrivere su database
   */
	public String writeCurrency(Double in) {
		if (in == null) {
			return "NULL";
		}
		return writeCurrency(in.doubleValue());
	}

	/**
   * Ritorna una stringa che rappresenta il valore in ingresso, pronta per
   * essere scritta su database.
   * 
   * @param in l'oggetto Double da preparare
   * @return la stringa da scrivere su database
   */
	public String writeDouble(double in) {
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		nf.setGroupingUsed(false);
		nf.setMinimumFractionDigits(0);
		nf.setMaximumFractionDigits(1000);
		return nf.format(in);
	}

	/**
   * Ritorna una stringa che rappresenta il valore in ingresso, pronta per
   * essere scritta su database.
   * 
   * @param in il valore da preparare
   * @return la stringa da scrivere su database
   */
	public String writeDouble(Double in) {
		if (in == null) {
			return "NULL";
		}
		return writeDouble(in.doubleValue());
	}

	/**
   * Ritorna una stringa che rappresenta il valore in ingresso, pronta per
   * essere scritta su database.
   * 
   * @param in il valore da preparare
   * @return la stringa da scrivere su database
   */
	public String writeBoolean(boolean in) {
		return writeInteger(in ? 1 : 0);
	}

	/**
   * Ritorna una stringa che rappresenta il valore in ingresso, pronta per
   * essere scritta su database.<br>
   * ATTENZIONE: se l'oggetto in ingresso e' null viene ritornata la stringa
   * "NULL".
   * 
   * @param in il valore da preparare
   * @return la stringa da scrivere su database
   */
	public String writeBoolean(Boolean in) {
		if (in == null)
			return "NULL";

		return writeBoolean(in.booleanValue());
	}

	// -------------------------METODI PRIVATI
	// ------------------------------------

	/**
   * Restituisce il testo SQL per convertire un campo ( o un'espressione) in una
   * data e ora, formattata come "dd/MM/yyyy HH:mm:ss". Viene utilizzata
   * solamente internamente per trasformare Date e Calendar in stringhe pronte
   * per essere inserite nel DataBase
   * 
   * @param dateTime Stringa contenente la data da convertire.
   * @return Stringa contenente la data nel formato desiderato. *
   */
	public String writeDate(String dateTime) {
		// Formatta il parametro a seconda del database utilizzato
		if (StringUtils.isEmpty(dateTime)) {
				return "NULL";
			}
			return ("STR_TO_DATE('" + dateTime + "','%d/%m/%Y %H:%i:%s')");
	}

	
	/**
	   * Restituisce il testo SQL per convertire un campo ( o un'espressione) in una
	   * data e ora, formattata come "dd-MM-yy". Viene utilizzata
	   * solamente internamente per trasformare Date e Calendar in stringhe pronte
	   * per essere inserite nel DataBase
	   * 
	   * @param dateTime Stringa contenente la data da convertire.
	   * @return Stringa contenente la data nel formato desiderato. *
	   */
		private String writeSimpleDate(String dateTime) {
			if (StringUtils.isEmpty(dateTime)) {
				return "NULL";
			}
			// Formatta il parametro a seconda del database utilizzato
			return ("TO_DATE('" + dateTime + "','DD-MM-YY')");
		}


	/**
   * Ritorna una stringa che rappresenta il valore in ingresso, pronta per
   * essere scritta su database.
   * 
   * @param in il valore da preparare
   * @return la stringa da scrivere su database
   */
	public String writeString(String in) {
		if(in != null && in.length() > 0) 
			return"'" + StringUtils.replace(in, "'", "''") + "'";
		else 
			return "NULL";
	}

	/**
   * Ritorna una stringa che rappresenta la concatenazione delle stringhe in ingresso, pronta per
   * essere scritta su database.
   * 
   * @param in il valore da preparare
   * @return la stringa da scrivere su database
   */
	public String writeString(ArrayList <String> in) {
		String result = "";
		if(in != null && in.size() > 0) {
			result = "(";
			for (int i = 0; i < in.size(); i++) {
				String tmp = in.get(i);
				result = result + "'" + StringUtils.replace(tmp, "'", "''") + "'";
				if (i < (in.size() -1)) {
					result = result + ", ";
				}
			}
			result = result + ")";
		} else {
			result = "NULL";
		}

		return result;
	}
	
	public static int getBooleanIntegerValue(Boolean value) {
		int result = 0;
		if (value != null && value)
			result = 1;
		return result;
	}
	

	/** Estrae la lista delle colonne dal resultSet, comprensiva del tipo di dato
	 * @throws SQLException */
	public static HashMap<String, Integer> getColumnsMap(ResultSet rst) throws SQLException
	{
		HashMap<String, Integer> colsList = new HashMap<String, Integer>();
		
		//Estrae la lista dei nomi colonna dal ResultSet
		ResultSetMetaData rsmd = rst.getMetaData();
		int columnCount = rsmd.getColumnCount();
		for (int i = 1; i < columnCount + 1; i++ ) 
		{
			String name = rsmd.getColumnName(i);			  
			Integer type = rsmd.getColumnType(i);
			colsList.put(name.toUpperCase(), type);
		}
		
		return colsList;		
	}
	
	public String getRownumSyntax(String orderBy) {
		return (StringUtils.isEmpty(orderBy))? " row_number() OVER ()" : " row_number() OVER (order by " + orderBy + ") ";
	}
	
	public String getSysDateIntervalSyntax (int days) {
		if (days >=0) {
				return " NOW() + INTERVAL '" + days + "' DAY  ";
			} else return " NOW() - INTERVAL '" + days*(-1) + "' DAY  ";
	}
	
	public String getSysDateIntervalSyntax (double days) {
		if (days >=0) {
				return " NOW() + INTERVAL '" + days + "' DAY  ";
			} else return " NOW() - INTERVAL '" + days*(-1) + "' DAY  ";
	}
	
	/**Restituisce la sintassi TRUNC(SYSDATE) nei vari formati */
	public String getTruncSysDateSyntax() {
		return getTruncDateSyntax() + "(" + getSysDateSyntax() + ")";
	}
	public String getTruncDateSyntax() {
		return " DATE";
	}

	public String getInstrSyntaxFor2Parameters(String str, String substr) {
			return " POSITION(" + substr + " in " + str + ") ";
	}

	public static String castAs(String column, String type) {
			return " CAST ( " + column + " AS " + type + ")";
	}
	
	public String toCharSyntax(String column) {
			return " CAST ( " + column + " AS VARCHAR" + ")";
	}
	
	public String getDateDiffSintax(int datePart, String dataField1, String dataField2) throws Exception {
		/*		Oracle								PostgreSQL
		Years	DATEDIFF(yy, start, end)	DATE_PART('year', end) - DATE_PART('year', start)
		Months	DATEDIFF(mm, start, end)	years_diff * 12 + (DATE_PART('month', end) - DATE_PART('month', start))
		Days	DATEDIFF(dd, start, end)	DATE_PART('day', end - start)
		Weeks	DATEDIFF(wk, start, end)	TRUNC(DATE_PART('day', end - start)/7)
		Hours	DATEDIFF(hh, start, end)	days_diff * 24 + DATE_PART('hour', end - start )
		Minutes	DATEDIFF(mi, start, end)	hours_diff * 60 + DATE_PART('minute', end - start )
		Seconds	DATEDIFF(ss, start, end)	minutes_diff * 60 + DATE_PART('minute', end - start )
		
		aggiornamento: e` stata introdotta la funzione esplicita DATEDIFF in postgres e oracle, quindi e` inutile dividere i due casi.
		*/
		

			switch (datePart) {
			//cast necessari perche` postgres e` molto fiscale sui tipi
			case SQL_DAY:
				return ("DATEDIFF(cast('dd' as "+getVarchar()+"(2)), cast(" + dataField1 + " as "+getTimestamp()+"), cast(" + dataField2 + " as "+getTimestamp()+") )");
			case SQL_MONTH:
				return ("DATEDIFF(cast('mm' as "+getVarchar()+"(2)), cast(" + dataField1 + " as "+getTimestamp()+"), cast(" + dataField2 + " as "+getTimestamp()+"))");
			case SQL_YEAR:
				return ("DATEDIFF(cast('yy' as "+getVarchar()+"(2)), cast(" + dataField1 + " as "+getTimestamp()+"), cast(" + dataField2 + " as "+getTimestamp()+"))");
			case SQL_HOUR:
				return ("DATEDIFF(cast('hh' as "+getVarchar()+"(2)), cast(" + dataField1 + " as "+getTimestamp()+"), cast(" + dataField2 + " as "+getTimestamp()+"))");
			case SQL_MINUTE:
				return ("DATEDIFF(cast('mi' as "+getVarchar()+"(2)), cast(" + dataField1 + " as "+getTimestamp()+"), cast(" + dataField2 + " as "+getTimestamp()+"))");
			case SQL_SECOND:
				return ("DATEDIFF(cast('ss' as "+getVarchar()+"(2)), cast(" + dataField1 + " as "+getTimestamp()+"), cast(" + dataField2 + " as "+getTimestamp()+") )");
			default:
				throw new Exception("unsupported datePart");
			}
	}
	
	public String getAliasSyntax(String select) {
			return " as " + select + " ";
	}
	// di default aggiunge sempre l'opzione per l'estrazione solo del testo
	public String getExtractXmlSintax(String xml, String path){ return getExtractXmlSintax(xml, path, true); }
	
	public String getExtractXmlSintax(String xml, String path, boolean valueOnly) {
			//se valueOnly allora e` un solo valore, quindi converto in valore semplice (mentre xpath ritorna un array!)
			return " CASE XML_IS_WELL_FORMED_DOCUMENT(CAST(" + xml + " AS TEXT)) WHEN true THEN "
					+ ((valueOnly)?" UNNEST(": " ") +"XPATH(CAST(" + path + " AS TEXT), CAST( " + xml + " AS XML)) "+((valueOnly)?") ": " ")
					+ " ELSE NULL END ";
	}
	
//	/**
//	 * Controlla la stringa in input, nel caso non sia formattata in modo corretto,
//	 * restituisce un xml di default ({@literal <XML></XML>})
//	 * 
//	 * @param xml
//	 * @return lo stesso xml in ingresso se formattato in modo corretto, oppure uno di default
//	 */
//	private String getSafeXml(String xml) {
//		String result = null;
//		
//		// Se il campo "xml" è vuoto o formattato male va a generare un errore!
//		result = " CASE xml_is_well_formed_document( CAST(" + xml + " as text)) WHEN true THEN CAST(" + xml + " as text) ELSE '<XML></XML>' END ";
//		
//		return result;
//	}

	public String getXpathSyntax(String path) {
			String modPath = path.toLowerCase();
			//rimuovo quel rowset perche` in postgres non c'e`.. (nella sys_events)
			if( modPath.startsWith("rowset") )
				modPath = modPath.substring("rowset".length());
			if( !modPath.startsWith("/") )
				modPath = "/"+modPath;
			return modPath;
	}

	public String getNumberConversion(String column) {
			return " CAST ( " + column + " AS NUMERIC" + ")";
	}
	
	public String startSubQuery(String subQuery) {
			return subQuery;
	}
	//si comporta esattamente come startSubQuery, solo nel codice e` controintuitivo vedere due volte start
	public String endSubQuery(String subQuery){ return startSubQuery(subQuery); }
	
	public String endSubQueryWithLimit(int max, String select) {
			return " limit " + max + " ) as " + select;
	}
	
	public String subQueryAlias(String alias){
			return " " + alias + " ";
	}
	
	public String getOrderByRownumSintax(String rownum, String sense){
			return " order by " + rownum + " " + sense;
	}
	
	public static String getVarchar(){
			return "VARCHAR";
	}
	
	public static String getTimestamp(){
			return "TIMESTAMP";
	}
	
	public static String getInteger(){
			return "INTEGER";
	}
	
	public static String getDate(){
			return "DATE";
	}
	
	public static String getSmallint(){
			return "SMALLINT";
	}
	
	public static String getNumeric(){
			return "NUMERIC";
	}
	
	public static String getText(){
			return "TEXT";
	}
	
	/*
	 * In PostgreSQL, it is not possible to add a numeric column to a timestamp; this method does the conversion for the numeric column
	 * Parameters:
	 * periodType - period type which is added (day / hour / minute / second)
	 * column - the numeric column; this column can be also the String value of an "int" variable
	 * */
	public String getIntervalSyntax(int periodType, String column) {
			switch (periodType) {
			case SQL_DAY:			
				return "interval '1' day * " + column;
			case SQL_HOUR:
				return "interval '1' hour * " + column;
			case SQL_MINUTE:
				return "interval '1' minute * " + column;
			case SQL_SECOND:
				return "interval '1' second * " + column;
			default:
				return ("");
			}
	}
	
	public String callStoredProcedure(){
			return "{ call ";
	} 
	
	public String endCallStoredProcedure(){
			return " }";
	}
	
	// chiama una procedura all'intero di un package. in postgres non ci sono i package
	// quindi si creano delle procedure che hanno come prefisso il nome del package 
	// questo metodo effettua in modo trasparente e x-db la conversione
	public String getPKGProcedureSyntax(String pkg, String procedure){
			return pkg+"__"+procedure;
	}
	
	public String getRegexp_substrSintaxFor4Parameters(String substr, String split, String position, String occurence) {  
		// the method is tested for PostgreSQL only for parameter "position" = 1 (3'rd parameter for regexp_substr function in Oracle SQL)
			return " split_part (" + substr + " , " + split + " , " + occurence  + ") ";  
	}
	
	public String regexpSplitToTable(String substr, String split){
			return " (select t as "+REGEXP_SPLIT_TO_TABLE_COL+" from regexp_split_to_table("+substr+" , '"+split+"') t) ";
	}
		
	public String getMinusSintax(){
			return " except ";
	} 
	
	public String toDateSyntax(){
			return " to_timestamp ";
	} 
	
	public static StringBuffer getAddHoperaEventSyntax(String procedureName){
		StringBuffer sql = new StringBuffer(4096);
		sql.append("{call "+procedureName+"(");
			sql.append(" "+castAs("?", getVarchar()+"(10)")+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getSmallint())+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getInteger())+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getVarchar()+"(10000)")+",");
			sql.append(" "+castAs("?", getText()));
			sql.append(")}");
		
		return sql;
	}
	
	public String numberToIntervalSyntax(String period, String periodType) {
			return " interval '" + period + "' " + periodType ;
	}

	public String listAggSyntax(String sql, String columnName, String separator){
		return listAggSyntax(sql, columnName, separator, null);
	}

	public String listAggSyntax(String query, String columnName, String separator, String orderByColumn){
			return " ( select STRING_AGG("+castAs(columnName, getVarchar())+", '"+castAs(separator, getVarchar())+"' ORDER BY "+((orderByColumn!=null)?orderByColumn:"1")+") from ("+query+") AS FOOBARBAZ ) ";
	}
	
	// in postgres invece di passare un cursore poi aperto e iterato da una funzione (non possibile)
	// passa un array con i dati ritornati dalla subquery che vengono iterati
	// pensato con l'uso con concatenate_list
	public String writeCursor(String query){
			return " array( "+query+ ") ";
	}
	
	public String multiInsertSyntax(StringList values){
		StringBuffer sb = new StringBuffer();
			sb.append(" values ");
			for( String v : values )
				sb.append("(").append(v).append(")").append(", ");
			return sb.substring(0, sb.length()-1);
	}
	
	
	
	
	
	/**
	 * Data una stringa rimuove tutti i caratteri NON alfanumerici<br><br>
	 * 
	 * Espressione regolare <pre>[^a-zA-Z0-9]+</pre>
	 * <b>NB: La stringa sorgente non viene modificata!</b><br>
	 *  
	 * @param source
	 * @return
	 */
	public static String removeNonAlphanumerics(String source) {
		
		//NON TRASFORMO IN CASO DI STRINGA VUOTA O NULLA E NON MODIFICO L'ORIGINALE 
		if (source==null) 
			return null;
		if ("".equals(source)) 
			return "";
		
		return source.replaceAll("[^a-zA-Z0-9]+","");
	}
	

	/**
	 * @param fields
	 * @return
	 */
	public static List<String> sqlFieldsToList(Enum<?>[] fields) {
		List<String> l = new ArrayList<String>();
		if (fields!=null) {
			for (Enum<?> e:fields){
				l.add(e.name());
			}			
		}
		return l;
	}
	

	/**
	 * Dato un array di enumerati restituisce una stringa che rappresenta i campi della select separati da virgola<br>
	 * 
	 * @param sqlFields

	 * @return
	 * @throws SQLException
	 */
	public static String getSqlFields(Enum<?>[] sqlFields)  {
		return getSqlFields(sqlFields, null, null);
	}
	
	/**
	 * Dato un array di enumerati restituisce una stringa che rappresenta i campi della select separati da virgola<br>
	 * 
	 * @param sqlFields
	 * @param alias
	 * @return
	 * @throws SQLException
	 */
	public static String getSqlFields(Enum<?>[] sqlFields, String alias)  {
		return getSqlFields(sqlFields, alias, null);
	}
	
	/**
	 * Dato un array di enumerati restituisce una stringa che rappresenta i campi della select separati da virgola<br>
	 * 
	 * @param sqlFields
	 * @param alias
	 * @param prefix: se valorizzato, viene premesso ai nomi dei campi; in questo modo è possibile gestire facilmente join e fetch con colonne omonime
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static String getSqlFields(Enum<?>[] sqlFields, String alias, String prefix)  {
		return getSqlFields(sqlFields, alias, null, prefix);
	}
	
	/**
	 * Dato un array di enumerati restituisce una stringa che rappresenta i campi della select separati da virgola<br>
	 * 
	 * @param sqlFields: lista dei campi con cui costruire la lista
	 * @param alias: alias da aggiungere ("ALIAS." + nomeColonna)
	 * @param excludeColumns: lista di colonne da ecludere dal risultato
	 * @param prefix: se valorizzato, viene premesso ai nomi dei campi; in questo modo è possibile gestire facilmente join e fetch con colonne omonime
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static String getSqlFields(Enum<?>[] sqlFields, String alias, Set<Enum<?>> excludeColumns, String prefix)  {

		//controlli sull'input
		if (sqlFields==null || sqlFields.length<1)  {
			return " ";
		}

		StringBuilder sqlHeader=new StringBuilder();
		String sep = " ";
		boolean include = true;
		for (int i=0; i<sqlFields.length; i++) {
			include = true;
			Enum<?> item = sqlFields[i];

			//Check esclusione
			if (excludeColumns!=null) {
				if (excludeColumns.contains(item)) {
					include = false;
				}
			}
			if (include) {
				sqlHeader.append(sep);
				if (StringUtils.isNotEmpty(alias)) {
					sqlHeader.append(alias).append(".");
				}
				sqlHeader.append(item.toString());
				if (StringUtils.isNotEmpty(prefix)) {
					sqlHeader.append(" AS ");
					sqlHeader.append(getFieldName(item, prefix));
				}

				sep = " , ";
			}
		}				
		

		return sqlHeader.toString();
	}
	
	/**Fornisce il nome di un campo dato alias*/
	public static String getFieldName(Enum<?> item, String prefix) {
		prefix = StringUtils.defaultIfEmpty(prefix, "");
		//Limita lunghezza massima campo + alias
		if ((prefix.length()+item.toString().length())<=30) {
			return prefix.concat(item.toString());
		} else {
			return item.toString();
		}
	}
	
	public static String getSqlFieldAlias(Enum<?>[] sqlFields, String alias, String prefix)	{
		//controlli sull'input
		if (sqlFields==null || sqlFields.length<1)  {
			return " ";
		}

		StringBuilder sqlHeader=new StringBuilder();
		String sep = " ";
		boolean include = true;
		for (int i=0; i<sqlFields.length; i++) {
			include = true;
			Enum<?> item = sqlFields[i];

			if (include) {
				sqlHeader.append(sep);
				if (StringUtils.isNotEmpty(alias)) {
					sqlHeader.append(alias).append(".");
				}
				if (StringUtils.isNotEmpty(prefix)) {
					sqlHeader.append(getFieldName(item, prefix));
				}
				sep = " , ";
			}
		}				
		return sqlHeader.toString();
	}
	
}
