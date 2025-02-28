package com.jade;

import clases.Departamentos;
import clases.Empleados;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class OperacionesEmpleados {
	private final SessionFactory factorysesion = new Configuration().configure().buildSessionFactory();

	public void ReadEmpleadoPorId(short empNo) {
		try (Session session = factorysesion.openSession()) {
			Empleados empleado = session.get(Empleados.class, empNo);
			if (empleado != null) {
				System.out.println("\n=== EMPLEADO ENCONTRADO ===");
				System.out.println(empleado);
			} else {
				System.out.println("❌ Empleado no encontrado");
			}
		}
	}

	public void ReadEmpleados() {
		try (Session session = factorysesion.openSession()) {
			List<Empleados> empleados = session.createQuery("FROM Empleados", Empleados.class).list();
			if (empleados.isEmpty()) {
				System.out.println("No hay empleados registrados");
				return;
			}
			System.out.println("\n=== LISTA DE EMPLEADOS ===");
			empleados.forEach(emp -> {
				System.out.println(emp);
			});
		}
	}

	public void CreateEmpleado(Scanner scanner) {
		try (Session session = factorysesion.openSession()) {
			Transaction tx = session.beginTransaction();
			Empleados nuevo = new Empleados();

			// Validación ID único
			boolean idValido = false;
			while (!idValido) {
				System.out.print("Ingrese número de empleado: ");
				short id = scanner.nextShort();
				scanner.nextLine();

				if (session.get(Empleados.class, id) == null) {
					nuevo.setEmpNo(id);
					idValido = true;
				} else {
					System.out.println("❌ Este ID ya está en uso");
				}
			}

			// Validación apellido
			String apellido;
			do {
				System.out.print("Apellido (requerido): ");
				apellido = scanner.nextLine().trim();
			} while (apellido.isEmpty());
			nuevo.setApellido(apellido);

			// Validación oficio
			String[] oficiosValidos = {"ANALISTA", "VENDEDOR", "DIRECTOR", "ADMINISTRATIVO"};
			System.out.println("Oficios válidos: " + Arrays.toString(oficiosValidos));
			String oficio;
			do {
				System.out.print("Oficio: ");
				oficio = scanner.nextLine().toUpperCase();
			} while (!Arrays.asList(oficiosValidos).contains(oficio));
			nuevo.setOficio(oficio);

			// Validación director
			System.out.print("Número de director (0 si no tiene): ");
			short dir = scanner.nextShort();
			scanner.nextLine();
			if (dir != 0) {
				Empleados director = session.get(Empleados.class, dir);
				if (director == null) {
					System.out.println("❌ Director no encontrado");
					tx.rollback();
					return;
				}
				nuevo.setDir(dir);
			}

			// Validación salario
			float salario;
			do {
				System.out.print("Salario (mayor a 0): ");
				salario = scanner.nextFloat();
			} while (salario <= 0);
			nuevo.setSalario(salario);

			// Comisión
			System.out.print("Comisión (0 si no aplica): ");
			float comision = scanner.nextFloat();
			nuevo.setComision(comision > 0 ? comision : null);

			// Departamento
			System.out.println("\nDepartamentos disponibles:");
			new OperacionesDepartamentos().ReadDepartamentos();
			boolean deptValido = false;
			while (!deptValido) {
				System.out.print("Número de departamento: ");
				byte deptNo = scanner.nextByte();
				Departamentos dept = session.get(Departamentos.class, deptNo);
				if (dept != null) {
					nuevo.setDepartamentos(dept);
					deptValido = true;
				} else {
					System.out.println("❌ Departamento no válido");
				}
			}

			nuevo.setFechaAlt(new Date(System.currentTimeMillis()));
			session.persist(nuevo);
			tx.commit();
			System.out.println("✔ Empleado creado exitosamente");
		} catch (Exception e) {
			System.out.println("❌ Error: " + e.getMessage());
		}

	}

	public void UpdateDatosEmpleado(Scanner scanner) {
		try (Session session = factorysesion.openSession()) {

			Transaction tx = session.beginTransaction();
			try {
				System.out.print("Ingrese número de empleado a modificar: ");
				short empNo = scanner.nextShort();
				scanner.nextLine();

				Empleados empleado = session.get(Empleados.class, empNo);
				if (empleado == null) {
					System.out.println("❌ Empleado no encontrado");
					return;
				}

				System.out.println("\nDeje vacío para mantener valor actual");

				// Apellido
				System.out.printf("Apellido [Actual: %s]: ", empleado.getApellido());
				String nuevoApellido = scanner.nextLine();
				if (!nuevoApellido.isEmpty()) empleado.setApellido(nuevoApellido);

				// Oficio
				String[] oficiosValidos = {"ANALISTA", "VENDEDOR", "DIRECTOR", "ADMINISTRATIVO"};
				System.out.printf("Oficio [Actual: %s]: ", empleado.getOficio());
				String nuevoOficio = scanner.nextLine().toUpperCase();
				if (!nuevoOficio.isEmpty() && Arrays.asList(oficiosValidos).contains(nuevoOficio)) {
					empleado.setOficio(nuevoOficio);
				}

				// Director
				System.out.printf("Director actual: %d | Nuevo director (0 para ninguno): ", empleado.getDir());
				String nuevoDir = scanner.nextLine();
				if (!nuevoDir.isEmpty()) {
					short dir = Short.parseShort(nuevoDir);
					if (dir != 0) {
						Empleados director = session.get(Empleados.class, dir);
						if (director != null) empleado.setDir(dir);
					} else {
						empleado.setDir(null);
					}
				}

				// Salario
				System.out.printf("Salario [Actual: %.2f]: ", empleado.getSalario());
				String nuevoSalario = scanner.nextLine();
				if (!nuevoSalario.isEmpty()) {
					empleado.setSalario(Float.parseFloat(nuevoSalario));
				}

				// Departamento
				System.out.println("Departamentos disponibles:");
				new OperacionesDepartamentos().ReadDepartamentos();
				System.out.printf("Nuevo departamento [Actual: %d]: ", empleado.getDepartamentos().getDeptNo());
				String nuevoDept = scanner.nextLine();
				if (!nuevoDept.isEmpty()) {
					Departamentos dept = session.get(Departamentos.class, Byte.parseByte(nuevoDept));
					if (dept != null) empleado.setDepartamentos(dept);
				}

				session.merge(empleado);
				tx.commit();
				System.out.println("✔ Empleado actualizado");
			} catch (Exception e) {
				tx.rollback();
				System.out.println("❌ Error: " + e.getMessage());
			}
		}
	}

	public void DeleteEmpleado(Scanner scanner) {
		try (Session session = factorysesion.openSession()) {

			Transaction tx = session.beginTransaction();
			try {
				System.out.print("Número de empleado a eliminar: ");
				short empNo = scanner.nextShort();
				scanner.nextLine();

				Empleados empleado = session.get(Empleados.class, empNo);
				if (empleado == null) {
					System.out.println("❌ Empleado no encontrado");
					return;
				}

				System.out.printf("¿Eliminar al empleado %s? (S/N): ", empleado.getApellido());
				String confirmacion = scanner.nextLine().toUpperCase();
				if (!confirmacion.equals("S")) {
					System.out.println("Operación cancelada");
					return;
				}

				// Verificar si es jefe
				Query<Long> query = session.createQuery(
						"SELECT COUNT(*) FROM Empleados WHERE dir = :empNo", Long.class);
				query.setParameter("empNo", empNo);
				if (query.getSingleResult() > 0) {
					System.out.println("❌ No se puede eliminar: Tiene empleados a cargo");
					return;
				}

				session.remove(empleado);
				tx.commit();
				System.out.println("✔ Empleado eliminado");
			} catch (Exception e) {
				tx.rollback();
				System.out.println("❌ Error: " + e.getMessage());
			}
		}
	}
}