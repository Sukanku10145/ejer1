package com.jade;

import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		OperacionesDepartamentos operacionDepartamentos = new OperacionesDepartamentos();
		OperacionesEmpleados operacionEmpleados = new OperacionesEmpleados();
		boolean salir = false;

		while (!salir) {
			System.out.println("\n\n	┌─── GESTIÓN DE EMPLEADOS Y DEPARTAMENTOS ───┐");
			System.out.println("	│   0. Salir                                 │");
			System.out.println("	│   1. Buscar empleado                       │");
			System.out.println("	│   2. Buscar departamento                   │");
			System.out.println("	│   3. Listar todos los empleados            │");
			System.out.println("	│   4. Listar todos los departamentos        │");
			System.out.println("	│   5. Modificar empleado                    │");
			System.out.println("	│   6. Modificar departamento                │");
			System.out.println("	│   7. Crear empleado                        │");
			System.out.println("	│   8. Crear departamento                    │");
			System.out.println("	│   9. Eliminar empleado                     │");
			System.out.println("	│   10. Eliminar departamento                │");
			System.out.println("	└────────────────────────────────────────────┘");
			System.out.print("\nSeleccione una opción: ");

			try {
				int opcion = Integer.parseInt(scanner.nextLine());

				switch (opcion) {
				case 0:
					salir = true;
					break;
				case 1:
					System.out.print("Inserte número de empleado: ");
					operacionEmpleados.ReadEmpleadoPorId(Short.parseShort(scanner.nextLine()));
					break;
				case 2:
					System.out.print("Inserte número de departamento: ");
					operacionDepartamentos.ReadDepartamentoPorId(Byte.parseByte(scanner.nextLine()));
					break;
				case 3:
					operacionEmpleados.ReadEmpleados();
					break;
				case 4:
					operacionDepartamentos.ReadDepartamentos();
					break;
				case 5:
					operacionEmpleados.UpdateDatosEmpleado(scanner);
					break;
				case 6:
					operacionDepartamentos.UpdateDepartamento(scanner);
					break;
				case 7:
					operacionEmpleados.CreateEmpleado(scanner);
					break;
				case 8:
					operacionDepartamentos.CreateDepartamento(scanner);
					break;
				case 9:
					operacionEmpleados.DeleteEmpleado(scanner);
					break;
				case 10:
					operacionDepartamentos.DeleteDepartamento(scanner);
					break;
				default:
					System.out.println("❌ Opción no válida");
				}

				// Esperar entrada solo si no es la opción de salir
				if (!salir) {
					System.out.print("\n--------------- Presione enter para continuar ---------------");
					scanner.nextLine();
				}

			} catch (NumberFormatException e) {
				System.out.println("❌ Error: Debe ingresar un número válido");
			} catch (Exception e) {
				System.out.println("❌ Error inesperado: " + e.getMessage());
			}
		}
		scanner.close();
	}
}