package persistence;

import br.com.ppo.persistence.dao.BaseDAO;
import br.com.ppo.persistence.exception.PersistenceException;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gerente gerente = new Gerente();
		gerente.setNome("Gran Finali 2");
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
			s.setNome("TESTE");
			d.setNome("FUNCIONÁRIO");
			
			s = dao.save(s);
			
			d.setSetor(s);
			
			d = dao.save(d);
			
			s.setSecretario(d);
			
			s = dao.update(s);
			
			System.out.println(s.getId() + " = "+ d.getId());
			
			Funcionario f = s.getSecretario();
			Setor s2 = f.getSetor();
			Funcionario f2 = s2.getSecretario();
			
			System.out.println("Nome f: "+f.getNome());
			System.out.println("Nome do setor: "+s2.getNome());
			System.out.println("Nome f2: "+f2.getNome());
			
			System.out.println(f2.getSetor().getId() + " = "+ f2.getSetor().getSecretario().getId());
			
		} catch (PersistenceException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
