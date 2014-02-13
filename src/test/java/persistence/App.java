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
			Funcionario d = new Funcionario();
			Setor s = new Setor();
			s.setNome("UM NOME");
			d.setNome("ASDRUBAL");
			
			s = dao.save(s);
			
			d.setSetor(s);
			
			d = dao.save(d);
			
			s.setSecretario(d);
			
			s = dao.update(s);
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}
}
