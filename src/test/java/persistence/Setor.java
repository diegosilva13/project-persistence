package persistence;

import br.com.ppo.persistence.entity.BaseEntity;

public class Setor extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Unidade unidade;
	private Funcionario secretario;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Unidade getUnidade() {
		return unidade;
	}
	
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Funcionario getSecretario() {
		return secretario;
	}

	public void setSecretario(Funcionario secretario) {
		this.secretario = secretario;
	}

}
