package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import model.Pessoa;

public class DaoPessoa extends DaoGenerico<Pessoa> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public List<Pessoa> pesquisarNome(String nome) {
		nome = nome.toUpperCase();
		String sql = "from Pessoa p where upper(p.nome) like '%" + nome + "%'";
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		try {
			pessoas = entityManager.createQuery(sql, Pessoa.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pessoas;
	}

}
