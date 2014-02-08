package persistence;

import java.util.List;

import br.com.ppo.persistence.dao.SuperDAO;

public class App {

	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Gerente gerente = new Gerente();
		SuperDAO dao = new SuperDAO();
		gerente.setCodigo("123450");
		gerente.setCpf("123456789");
		gerente.setEndereco("RUA Alsdraberto");
		gerente.setRg("123456789");
		gerente.setTelefone("12345678");
		gerente = (Gerente) dao.save(gerente);
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
