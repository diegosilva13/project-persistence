package persistence;

import br.com.ppo.persistence.dao.SuperDAO;
import br.com.ppo.persistence.exception.PersistenceException;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gerente gerente = new Gerente();
		gerente.setNome("Gran Finali 2");
		gerente.setCpf("000-000-11-10");
		gerente.setRg("000.000.000");
		gerente.setTelefone("(11)0000-0001");
		gerente.setCodigo("2111");
		Setor setor = new Setor();
		setor.setNome("RH");
		Unidade unidade = new Unidade();
		unidade.setNumero(7);
	
		try {
			SuperDAO dao = new SuperDAO();
//			unidade = (Unidade) dao.save(unidade);
//			setor.setUnidade(unidade);
//			setor = (Setor) dao.save(setor);
//			gerente.setSetor(setor);
//			gerente = (Gerente) dao.save(gerente);
//			List<Gerente> list = dao.findAll(Gerente.class);
			
			unidade = (Unidade) dao.save(unidade);
			Setor setor2 = new Setor();
			setor2.setUnidade(unidade);
			unidade = (Unidade) dao.save(unidade);
			setor2 = (Setor) dao.save(setor2);
			gerente.setSetor(setor2);
			gerente = (Gerente) dao.save(gerente);
			System.out.println(gerente.getSetor());
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}
}
