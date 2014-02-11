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
		gerente.setNome("Gran Fina1li");
		gerente.setCpf("000-000-010-10");
		gerente.setRg("000.000.001");
		gerente.setTelefone("(00)00100-0001");
		gerente.setCodigo("21");
		Setor setor = new Setor();
		setor.setNome("GERENC1IA");
		Unidade unidade = new Unidade();
		unidade.setNumero(1314);
		try {
			SuperDAO dao = new SuperDAO();
			/*unidade = (Unidade) dao.save(unidade);
			setor.setUnidade(unidade);
			setor = (Setor) dao.save(setor);
			gerente.setSetor(setor);
			gerente = (Gerente) dao.save(gerente);
			List<Gerente> list = dao.findAll(Gerente.class);
			
			for(Gerente g: list){
				System.out.println("--------------REGISTROS-------------------");
				System.out.println(g.getNome());
				System.out.println(g.getSetor().getNome());
				System.out.println(g.getSetor().getUnidade().getNumero());
			}*/
			System.out.println(((Unidade)dao.findById(Unidade.class, 1)).getNumero());
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
	}
}
