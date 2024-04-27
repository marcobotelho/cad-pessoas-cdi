package util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JPAUtil {

	private static final String PERSISTENCE_UNIT_NAME = "pu-cad-pessoas-cdi";
	private static EntityManagerFactory entityManagerFactory;

	static {
		entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	@Produces
	public EntityManager createEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	public static void closeEntityManagerFactory() {
		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}

//	public static EntityManager getEntityManager() {
//		if (entityManagerFactory == null) {
//			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//		}
//		return entityManagerFactory.createEntityManager();
//	}
}

//@ApplicationScoped
//public class JPAUtil {
//
//	private static final String PERSISTENCE_UNIT_NAME = "cad-pessoas-cdi";
//	private static EntityManagerFactory entityManagerFactory;
//
//	@Produces
//	public static EntityManager getEntityManager() {
//		if (entityManagerFactory == null) {
//			entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//		}
//		return entityManagerFactory.createEntityManager();
//	}
//
//	public static void closeEntityManagerFactory() {
//		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
//			entityManagerFactory.close();
//		}
//	}

//
//	@Produces
//	public EntityManager createEntityManager() {
//		return entityManagerFactory.createEntityManager();
//	}
//}
