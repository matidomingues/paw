package ar.edu.itba.paw.entity;

import javax.persistence.Entity;

@Entity
public class Url extends PersistentEntity {
	
	private String base;
	private String resol;

	public Url(String base, String resol){
		this.base = base;
		this.resol = resol;
	}

	public String getBase() {
		return base;
	}

	public String getResol() {
		return resol;
	}
	
}
