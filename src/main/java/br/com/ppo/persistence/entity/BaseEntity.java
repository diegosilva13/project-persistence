package br.com.ppo.persistence.entity;


public class BaseEntity implements IBaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Integer id;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public boolean isPersistent(){
		return this.id != null;
	}
}
