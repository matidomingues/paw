package ar.edu.itba.paw.domain.url;

import ar.edu.itba.paw.domain.entity.PersistentEntity;

import javax.persistence.Entity;

@Entity
public class Url extends PersistentEntity {
	
	private String base;
	private String resol;

	Url(){
	}
	
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
