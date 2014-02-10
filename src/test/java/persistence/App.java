package persistence;

import java.util.List;

import br.com.ppo.persistence.dao.SuperDAO;
import br.com.ppo.persistence.exception.PersistenceException;

public class App {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Gerente gerente = new Gerente();
		gerente.setNome("João Rafael");
		gerente.setCpf("00000000");
		gerente.setRg("000000000");
		gerente.setTelefone("000000000");
		gerente.setCodigo("31");
		gerente.setId(1);
		Setor setor = new Setor();
		setor.setNome("GERENCIA");
		try {
			SuperDAO dao = new SuperDAO();
			setor = (Setor) dao.save(setor);
			gerente = (Gerente) dao.update(gerente);
			gerente.setSetor(setor);
			gerente = (Gerente) dao.update(gerente);
			List<Gerente> list = dao.findAll(Gerente.class);
			
			for(Gerente g: list){
				System.out.println(g.getNome());
			}
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}
}
