package br.com.ppo.persistence.util.exception;

public class PropertyNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropertyNotFoundException(String mensagem, Exception e){
		super(mensagem, e);
	}
}
