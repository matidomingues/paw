package ar.edu.itba.paw.model;

/**
 * Created with IntelliJ IDEA.
 * User: facundo
 * Date: 05/09/13
 * Time: 21:13
 * To change this template use File | Settings | File Templates.
 */
public abstract class PersistableEntity {

    private Integer id;

    public PersistableEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

	public void setId(Integer id) {
		this.id = id;
	}
}
