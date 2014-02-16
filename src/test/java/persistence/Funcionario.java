package persistence;

import br.com.ppo.persistence.entity.BaseEntity;

public class Funcionario extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	private Setor setor;
	
	public Setor getSetor() {
		return setor;
	}
	
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
}
