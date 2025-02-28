package com.jade;

import clases.Departamentos;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Scanner;

public class OperacionesDepartamentos {
	private final SessionFactory factorysesion = new Configuration().configure().buildSessionFactory();

	public void ReadDepartamentoPorId(byte deptNo) {
		try (Session session = factorysesion.openSession()) {
			Departamentos dept = session.get(Departamentos.class, deptNo);
			if (dept != null) {
				System.out.println("\n=== DEPARTAMENTO ENCONTRADO ===");
				System.out.println(dept);
			} else {
				System.out.println("❌ Departamento no encontrado");
			}
		}
	}

	public void ReadDepartamentos() {
		try (Session session = factorysesion.openSession()) {
			List<Departamentos> departamentos = session.createQuery("FROM Departamentos", Departamentos.class).list();
			if (departamentos.isEmpty()) {
				System.out.println("No hay departamentos registrados");
				return;
			}
			System.out.println("\n=== LISTA DE DEPARTAMENTOS ===");
			departamentos.forEach(dept -> {
				System.out.println(dept);
			});
		}
	}

	public void CreateDepartamento(Scanner scanner) {
		try (Session session = factorysesion.openSession()) {

			Transaction tx = session.beginTransaction();
			try {
				Departamentos nuevo = new Departamentos();

				// Validación ID
				boolean idValido = false;
				while (!idValido) {
					System.out.print("Número de departamento (1-127): ");
					byte deptNo = scanner.nextByte();
					scanner.nextLine();

					if (deptNo < 1 || deptNo > 127) {
						System.out.println("❌ Número inválido");
						continue;
					}

					if (session.get(Departamentos.class, deptNo) == null) {
						nuevo.setDeptNo(deptNo);
						idValido = true;
					} else {
						System.out.println("❌ Este departamento ya existe");
					}
				}

				// Validación nombre
				String nombre;
				do {
					System.out.print("Nombre (requerido): ");
					nombre = scanner.nextLine().trim();
				} while (nombre.isEmpty());
				nuevo.setDnombre(nombre);

				System.out.print("Ubicación: ");
				String loc = scanner.nextLine();
				nuevo.setLoc(loc);

				session.persist(nuevo);
				tx.commit();
				System.out.println("✔ Departamento creado");
			} catch (Exception e) {
				tx.rollback();
				System.out.println("❌ Error: " + e.getMessage());
			}
		}
	}

	public void UpdateDepartamento(Scanner scanner) {
		try (Session session = factorysesion.openSession()) {

			Transaction tx = session.beginTransaction();
			try {
				System.out.print("Número de departamento a modificar: ");
				byte deptNo = scanner.nextByte();
				scanner.nextLine();

				Departamentos dept = session.get(Departamentos.class, deptNo);
				if (dept == null) {
					System.out.println("❌ Departamento no encontrado");
					return;
				}

				System.out.println("\nDeje vacío para mantener valor actual");

				// Nombre
				System.out.printf("Nombre [Actual: %s]: ", dept.getDnombre());
				String nuevoNombre = scanner.nextLine();
				if (!nuevoNombre.isEmpty()) dept.setDnombre(nuevoNombre);

				// Ubicación
				System.out.printf("Ubicación [Actual: %s]: ", dept.getLoc());
				String nuevaLoc = scanner.nextLine();
				if (!nuevaLoc.isEmpty()) dept.setLoc(nuevaLoc);

				session.merge(dept);
				tx.commit();
				System.out.println("✔ Departamento actualizado");
			} catch (Exception e) {
				tx.rollback();
				System.out.println("❌ Error: " + e.getMessage());
			}
		}
	}

	public void DeleteDepartamento(Scanner scanner) {
		try (Session session = factorysesion.openSession()) {

			Transaction tx = session.beginTransaction();
			try {
				System.out.print("Número de departamento a eliminar: ");
				byte deptNo = scanner.nextByte();
				scanner.nextLine();

				Departamentos dept = session.get(Departamentos.class, deptNo);
				if (dept == null) {
					System.out.println("❌ Departamento no encontrado");
					return;
				}

				System.out.printf("¿Eliminar departamento %s y todos sus empleados? (S/N): ", dept.getDnombre());
				String confirmacion = scanner.nextLine().toUpperCase();
				if (!confirmacion.equals("S")) {
					System.out.println("Operación cancelada");
					return;
				}

				// Eliminar empleados asociados
				Query<?> query = session.createQuery("DELETE FROM Empleados WHERE departamentos.deptNo = :deptNo");
				query.setParameter("deptNo", deptNo);
				int empleadosEliminados = query.executeUpdate();

				session.remove(dept);
				tx.commit();
				System.out.printf("✔ Departamento eliminado | Empleados afectados: %d\n", empleadosEliminados);
			} catch (Exception e) {
				tx.rollback();
				System.out.println("❌ Error: " + e.getMessage());
			}
		}
	}
}