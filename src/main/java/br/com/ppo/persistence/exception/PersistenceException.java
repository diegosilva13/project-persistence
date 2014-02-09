package br.com.ppo.persistence.exception;

public class PersistenceException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersistenceException(String mensagem) {
		super(mensagem);
	}
	
	public PersistenceException(Exception e, String mensagem){
		super(mensagem, e);
	}
	
	public PersistenceException(Exception e){
		super(e);
	}
}
