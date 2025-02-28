package jade.com.ejer1;

import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import clases.Empleados;

public class OperacionesEmpleados {

	private final SessionFactory factorysesion;

	public OperacionesEmpleados() {

		factorysesion = new Configuration().configure().buildSessionFactory();
	}

	public void ReadEmpleadoPorId(int empNo) {

		Session session = factorysesion.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Empleados empleado = session.get(Empleados.class, empNo); 
			if (empleado != null) {
				System.out.println(empleado); 
			} else {
				System.out.println("Empleado no encontrado.");
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public void UpdateDatosEmpleado() {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el número del empleado: ");
		int empNo = scanner.nextInt();
		scanner.nextLine();

		System.out.print("Ingrese el nuevo apellido: ");
		String apellidoN = scanner.next();

		System.out.print("Ingrese el nuevo oficio: ");
		String oficioN = scanner.next();

		System.out.print("Ingrese el nuevo dir: ");
		Short dirN = scanner.nextShort();

		System.out.print("Ingrese el nuevo salario: ");
		Float salarioN = scanner.nextFloat();

		System.out.print("Ingrese la nueva comisión: ");
		Float comisionN = scanner.nextFloat();

		/*System.out.print("Ingrese el nuevo departamento: ");
		String departamentoN = scanner.next();*/

		Session session = factorysesion.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			Empleados nuevoEmpleado = session.get(Empleados.class, empNo); 

			if (nuevoEmpleado != null) {

				nuevoEmpleado.setApellido(apellidoN);
				nuevoEmpleado.setComision(comisionN);
				nuevoEmpleado.setSalario(salarioN);
				nuevoEmpleado.setDir(dirN);
				nuevoEmpleado.setOficio(oficioN);

				session.merge(nuevoEmpleado);

				tx.commit(); 

				System.out.println("Datos del empleado actualizados correctamente.");
			} else {
				System.out.println("Empleado no encontrado.");
			}
		} catch (HibernateException e) {
			if (tx != null) tx.rollback(); // Revierte la transacción en caso de error
			e.printStackTrace();
		} finally {
			session.close(); 
		}
	}

	public void ReadEmpleados() {
		Session session = factorysesion.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			List<Empleados> empleados = session.createQuery("FROM Empleados", Empleados.class).list(); 
			for (Empleados empleado : empleados) {
				System.out.println(empleado); 
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