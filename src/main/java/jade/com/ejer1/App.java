package jade.com.ejer1;

import java.util.Scanner;

public class App {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		OperacionesDepartamentos operacionDepartamentos = new OperacionesDepartamentos();

		boolean salir = false; 

		while (!salir) {

			System.out.println("\n\nBIENVENIDO AL CRUD PARA LA BASE DE DATOS SQLITE:\n\n");
			System.out.println("Seleccione una opción:\n");
			System.out.println("1. Mostrar datos de todos los departamentos");
			System.out.println("0. Salir");

			int opcion = scanner.nextInt();
			scanner.nextLine(); 

			switch (opcion) {
			case 1:
				operacionDepartamentos.ReadDepartamentos();
				break;

			case 0:
				salir = true;
				break;

			default:
				System.out.println("Opción no válida.");
			}
		}

		scanner.close();
	}
}

