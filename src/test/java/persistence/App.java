package persistence;

import br.com.ppo.persistence.dao.BaseDAO;
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
	
		BaseDAO dao = new BaseDAO();
//			unidade = (Unidade) dao.save(unidade);
//			setor.setUnidade(unidade);
//			setor = (Setor) dao.save(setor);
//			gerente.setSetor(setor);
//			gerente = (Gerente) dao.save(gerente);
//			List<Gerente> list = dao.findAll(Gerente.class);
		
		try {
			Funcionario f = new Funcionario();
			Setor s1 = new Setor();
			
			s1.setSecretario(f);
			f.setSetor(setor);
			
			System.out.println(dao.findAll(Gerente.class).size());
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}
}
