package ar.edu.itba.paw.domain.userlist;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import ar.edu.itba.paw.domain.entity.PersistentEntity;
import ar.edu.itba.paw.domain.twattuser.TwattUser;

@Entity
public class UserList extends PersistentEntity{
	
	@Column(nullable=false,unique=true)
	private String name;
	
	@ManyToOne
	private TwattUser creator;
	
	@ManyToMany
	private List<TwattUser> followed = new LinkedList<TwattUser>();
	
	public UserList(String name, TwattUser creator){
		this.name = name;
		this.creator = creator;
	}
	
	UserList(){
	}
	
	public String getName(){
		return this.name;
	}
	
	public List<TwattUser> getFollowed(){
		return this.followed;
	}
	
	public void removefollowers(List<TwattUser> users){
		this.followed.removeAll(users);
	}
	
	public void addFollowers(List<TwattUser> users){
		this.followed.addAll(users);
	}

	public void setFollowers(List<TwattUser> users){
		this.followed = users;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result
				+ ((followed == null) ? 0 : followed.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserList other = (UserList) obj;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (followed == null) {
			if (other.followed != null)
				return false;
		} else if (!followed.equals(other.followed))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
