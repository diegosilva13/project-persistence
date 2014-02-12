package br.com.ppo.persistence.entity;

public interface IBaseEntity {
	public Integer getId();
	public void setId(Integer id);
	public boolean isPersistent();
}
