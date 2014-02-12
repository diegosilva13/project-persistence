package br.com.ppo.persistence.entity;

import java.io.Serializable;

public interface IBaseEntity extends Serializable{
	public Integer getId();
	public void setId(Integer id);
	public boolean isPersistent();
}
