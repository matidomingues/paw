package ar.edu.itba.paw.domain.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PersistentEntity {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	
	public int getId() {
		return id;
	}
	
	public boolean isNew() {
		return id == 0;
	}
}
