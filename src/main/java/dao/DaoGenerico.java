package dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class DaoGenerico<E> {

	@Inject
	private EntityManager entityManager;

	public void incluir(E entidade) throws Exception {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(entidade);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception(e.getMessage());
		} finally {
			entityManager.clear();
		}
	}

	public void salvar(E entidade) throws Exception {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(entidade);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception(e.getMessage());
		} finally {
			entityManager.clear();
		}
	}

	public void atualizar(E entidade) throws Exception {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(entidade);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception(e.getMessage());
		} finally {
			entityManager.clear();
		}
	}

	public void excluir(E entidade) throws Exception {
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(entidade);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
			throw new Exception(e.getMessage());
		} finally {
			entityManager.clear();
		}
	}

	/*
	 * public E buscarObj(Class<E> entidade) { Object id =
	 * EMF.getPrimaryKeyValue(entidade); return entityManager.find(entidade, id); }
	 */

	public E buscarId(Class<E> entidadeClass, Long id) {
		return entityManager.find(entidadeClass, id);
	}

	public List<E> listarTodos(Class<E> entidadeClass) {
		String sql = "from " + entidadeClass.getName();
		@SuppressWarnings("unchecked")
		List<E> resultados = entityManager.createQuery(sql).getResultList();
		return resultados;
	}

}
