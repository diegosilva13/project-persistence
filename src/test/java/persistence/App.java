package persistence;

import java.util.List;

import br.com.ppo.persistence.dao.SuperDAO;
import br.com.ppo.persistence.exception.PersistenceException;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gerente gerente = new Gerente();
		gerente.setNome("João Rafael");
		gerente.setCpf("00000000");
		gerente.setRg("000000000");
		gerente.setTelefone("000000000");
		gerente.setCodigo("3");
		
		try {
			SuperDAO dao = new SuperDAO();
			gerente = (Gerente) dao.save(gerente);
			
			List<Gerente> list = dao.findAll(Gerente.class);
			
			for(Gerente g: list){
				System.out.println(g.getNome());
			}
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}
}
