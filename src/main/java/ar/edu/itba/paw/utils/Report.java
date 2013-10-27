package ar.edu.itba.paw.utils;

import java.sql.Timestamp;


public class Report {
	
	private String header;
	private String value;
	
	public Report(Object[] result){
		this.header = String.valueOf((Timestamp)result[0]);
		this.value = String.valueOf((Long)result[1]);
	}
	
	public String getHeader(){
		return this.header;
	}
	
	public String getValue(){
		return this.value;
	}
}

