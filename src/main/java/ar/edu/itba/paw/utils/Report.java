package ar.edu.itba.paw.utils;


import org.joda.time.DateTime;


public class Report {
	
	private String header;
	private Long value;
	
	public Report(Object[] result,String type){
		if(type.compareTo("day") == 0 ){
			this.header = new DateTime((Integer) result[3], (Integer) result[2], (Integer) result[1], 0, 0, 0, 0).toString();
		}else if(type.compareTo("month") == 0){
			this.header = new DateTime((Integer) result[2], (Integer) result[1], 0, 0, 0, 0, 0).toString();
		}else if(type.compareTo("year") == 0){
			this.header = new DateTime((Integer) result[1], 0, 0, 0, 0, 0, 0).toString();
		}
		this.value = (Long)result[0];
	}
	
	public Report(Object[] result){
		this.header = (String)result[1];
		this.value = (Long)result[0];
	}
	
	public String getHeader(){
		return this.header;
	}
	
	public Long getValue(){
		return this.value;
	}
}

