package jade.com.ejer1;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import clases.Departamentos;
import clases.Empleados;

public class OperacionesDepartamentos {

	private final SessionFactory factorysesion;

	public OperacionesDepartamentos() {

		factorysesion = new Configuration().configure().buildSessionFactory();
	}

	public void ReadDepartamentoPorId(int empNo) {

		Session session = factorysesion.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Departamentos departamento = session.get(Departamentos.class, empNo); 
			if (departamento != null) {
				System.out.println(departamento); 
			} else {
				System.out.println("Departamento no encontrado.");
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void ReadDepartamentos() {
		Session session = factorysesion.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			List<Departamentos> dep = session.createQuery("FROM Departamentos", Departamentos.class).list(); 
			for (Departamentos d : dep) {
				System.out.println(d); 
				System.out.println();
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
