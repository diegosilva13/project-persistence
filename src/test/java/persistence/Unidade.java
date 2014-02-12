package persistence;

import br.com.ppo.persistence.entity.BaseEntity;

public class Unidade extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer numero;
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
}
