package ar.edu.itba.paw.model;

public class Url extends PersistableEntity {

	private String base;
	private String resol;

	public Url(String base, String resol){
        super(-1);
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
