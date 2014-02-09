package br.com.ppo.persistence.exception;

public class UtilException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UtilException(String mensagem) {
		super(mensagem);
	}
	
	public UtilException(Exception e, String mensagem){
		super(mensagem, e);
	}
	
	public UtilException(Exception e){
		super(e);
	}
}
