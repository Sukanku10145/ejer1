package jade.com.ejer1;

import java.util.Scanner;

public class App {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		OperacionesDepartamentos operacionDepartamentos = new OperacionesDepartamentos();
		OperacionesEmpleados operacionEmpleados = new OperacionesEmpleados();

		boolean salir = false; 

		while (!salir) {

			System.out.println("\n\n-------------BIENVENIDO AL CRUD PARA LA BASE DE DATOS SQLITE-------------");
			System.out.println("|   0. Salir                                                            |");
			System.out.println("|   1. Buscar empleado                                                  |");
			System.out.println("|   2. Buscar departamento                                              |");
			System.out.println("|   3. Mostrar datos de todos los empleados                             |");
			System.out.println("|   4. Mostrar datos de todos los departamentos                         |");
			System.out.println("|   5. Modificar un empleado                                            |");
			System.out.println("-------------------------------------------------------------------------");
			System.out.print("\nSeleccione una opción: ");

			int opcion = scanner.nextInt();
			scanner.nextLine();

			System.out.println("\n");

			switch (opcion) {
			case 0:
				salir = true;
				break;

			case 1:
				System.out.print("Inserte un número de empleado: ");
				int empNo = scanner.nextInt();
				System.out.println("\n");
				operacionEmpleados.ReadEmpleadoPorId(empNo);
				break;

			case 2:
				System.out.print("Inserte un número de departamento: ");
				int depNo = scanner.nextInt();
				System.out.println("\n");
				operacionDepartamentos.ReadDepartamentoPorId(depNo);
				break;

			case 3:
				operacionEmpleados.ReadEmpleados();
				break;

			case 4:
				operacionDepartamentos.ReadDepartamentos();
				break;

			case 5:
				operacionEmpleados.UpdateDatosEmpleado();
				break;

			default:
				System.out.println("Opción no válida.");
			}
		}

		scanner.close();
	}
}

