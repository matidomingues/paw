package ar.edu.itba.paw.utils;


import org.joda.time.DateTime;


public class Report {
	
	private DateTime header;
	private Long value;
	
	public Report(Object[] result,String type){
		if(type.compareTo("day") == 0 ){
			this.header = new DateTime((Integer) result[3], (Integer) result[2], (Integer) result[1], 0, 0, 0, 0);
		}else if(type.compareTo("month") == 0){
			this.header = new DateTime((Integer) result[2], (Integer) result[1], 0, 0, 0, 0, 0);
		}else if(type.compareTo("year") == 0){
			this.header = new DateTime((Integer) result[1], 0, 0, 0, 0, 0, 0);
		}
		this.value = (Long)result[0];
	}
	
	public DateTime getHeader(){
		return this.header;
	}
	
	public Long getValue(){
		return this.value;
	}
}

