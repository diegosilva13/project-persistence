package persistence;

import br.com.ppo.persistence.dao.SuperDAO;
import br.com.ppo.persistence.exception.PersistenceException;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Gerente gerente = new Gerente();
		SuperDAO dao = null;
		gerente.setCodigo("123451");
		gerente.setNome("TESTETSTSSTST");
		gerente.setCpf("123456789");
		gerente.setEndereco("RUA Alsdraberto");
		gerente.setRg("123456789");
		gerente.setTelefone("12345678");
		
		try {
			dao = new SuperDAO();
			gerente = (Gerente) dao.save(gerente);
			System.out.println(gerente.getCodigo());
			gerente = (Gerente) dao.update(gerente);
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		System.out.println(gerente.getId());
//		List<Gerente> listGerente = dao.findAll(Gerente.class);
//		
//		for(Gerente g: listGerente){
//			System.out.println("Código: "+g.getCodigo());
//			System.out.println("CPF: "+g.getCpf());
//		}
//		
//		Gerente result = (Gerente) dao.findById(gerente,8);
//		System.out.println("Buscado: "+result.getCodigo());
//		dao.remove(result);
	}

}
