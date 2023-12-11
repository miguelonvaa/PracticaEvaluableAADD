package main;
import java.util.Scanner;

// Importar otras clases necesarias
public class Principal {
	private static Scanner sca = new Scanner(System.in);
	public static void main(String[] args) {
		boolean salir = false;
		while (!salir) {
			mostrarMenu();
			int opcion = sca.nextInt();
			switch (opcion) {
			case 1:
				// Gestionar libros
				gestionarLibros();
				break;
			case 2:// Gestionar autores
				gestionarAutores();
				break;
			case 3:
				// Gestionar préstamos
				gestionarPrestamos();
				break;
			case 4:
				salir = true;
				break;
			default:
				System.out.println("Opción no válida. Por favor,intente de nuevo.");
			}
		}
	}

	public static void mostrarMenu() {
		System.out.println("Bienvenido al Sistema de Gestión de Biblioteca");
		System.out.println("1. Gestionar Libros");
		System.out.println("2. Gestionar Autores");
		System.out.println("3. Gestionar Préstamos");
		System.out.println("4. Salir");
		System.out.print("Seleccione una opción: ");
	}

	private static void gestionarLibros() {
		Libro libro = new Libro();
	}

	private static void gestionarAutores() {
		Autor autor = new Autor();
	}

	private static void gestionarPrestamos() {
		Prestamo prestamo = new Prestamo();
	}

}
