package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Autor {
    private String nombre_autor, nacionalidad;
    private int anio_nacimiento, id_autor;
    private Scanner sca = new Scanner(System.in);

    Principal principal = new Principal();

    public Autor() {
        System.out.println("Elige una de las opciones");
        System.out.println("1. Añadir un/a autor/a");
        System.out.println("2. Mostrar lista de Autores registrados");
        System.out.println("3. Modificar un/a autor/a");
        System.out.println("4. Eliminar un/a autor/a");
        System.out.println("5. Salir al menú principal");
        int optionMenuActor = sca.nextInt();

        switch(optionMenuActor){
            case 1:
				añadirAutor();
            break;
            case 2:
				mostrarAutor();
            break;
            case 3:
				modificarAutor();
            break;
            case 4:
				eliminarAutor();
            break;
            case 5:
				principal.mostrarMenu();
            break;
            default:
            System.err.println("Elige una opcion del menú");
        }
    }

    public void añadirAutor() {
        System.out.println("Introduce el nombre: ");
        sca.nextLine();
        nombre_autor = sca.nextLine();
        System.out.println("Introduce la nacionalidad: ");
        nacionalidad = sca.nextLine();
        System.out.println("Introduce el año de nacimiento: ");
        anio_nacimiento = sca.nextInt();

        if (!nombre_autor.isEmpty() && !nacionalidad.isEmpty() && anio_nacimiento > 0) {
            try {
            	Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
                String sql = "INSERT INTO autor (nombre_autor, nacionalidad, anio_nacimiento) VALUES ('" + nombre_autor
                        + "','" + nacionalidad + "','" + anio_nacimiento + "')";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                System.out.println("Datos enviados y guardados en la base de datos.");
            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
                System.err.println("Error al conectar con la base de datos.");
            }
        } else {
            System.err.println("Por favor, completa todos los campos.");
        }
    }

    public void eliminarAutor() {
        System.out.println("Introduce el ID del autor a eliminar: ");
        int id_autor = sca.nextInt();

        if (id_autor > 0) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
                String sql = "DELETE FROM autor WHERE id_autor = '" + id_autor + "'";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id_autor);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Datos eliminados de la base de datos.");
                } else {
                    System.out.println("No se encontró ningún autor con ese ID.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error al conectar con la base de datos.");
            }
        } else {
            System.err.println("Por favor, ingresa un ID de autor válido.");
        }
    }

    public void modificarAutor() {
        System.out.println("Introduce el ID del autor a modificar: ");
        int id_autor = sca.nextInt();
        sca.nextLine();

        System.out.println("Introduce el nuevo nombre: ");
        String nuevo_nombre_autor = sca.nextLine();
        System.out.println("Introduce la nueva nacionalidad: ");
        String nueva_nacionalidad = sca.nextLine();
        System.out.println("Introduce el nuevo año de nacimiento: ");
        int nuevo_anio_nacimiento = sca.nextInt();

        if (id_autor > 0 && !nuevo_nombre_autor.isEmpty() && !nueva_nacionalidad.isEmpty()
                && nuevo_anio_nacimiento > 0) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
                String sql = "UPDATE autor SET nombre_autor = '" + nuevo_nombre_autor + "', nacionalidad = '"
                        + nueva_nacionalidad + "', anio_nacimiento = '" + nuevo_anio_nacimiento
                        + "' WHERE id_autor = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, nuevo_nombre_autor);
                statement.setString(2, nueva_nacionalidad);
                statement.setInt(3, nuevo_anio_nacimiento);
                statement.setInt(4, id_autor);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Datos del autor modificados en la base de datos.");
                } else {
                    System.out.println("No se encontró ningún autor con ese ID.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error al conectar con la base de datos.");
            }
        } else {
            System.err.println("Por favor, ingresa valores válidos para modificar el autor.");
        }
    }

    public void mostrarAutor() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
            String sql = "SELECT * FROM autor";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Lista de Autores Registrados:");
            System.out.printf("%-5s %-20s %-20s %-10s%n", "ID", "Nombre", "Nacionalidad", "Año Nacimiento");

            while (resultSet.next()) {
                int id_autor = resultSet.getInt("id_autor");
                String nombre_autor = resultSet.getString("nombre_autor");
                String nacionalidad = resultSet.getString("nacionalidad");
                int anio_nacimiento = resultSet.getInt("anio_nacimiento");

                System.out.printf("%-5d %-20s %-20s %-10d%n", id_autor, nombre_autor, nacionalidad, anio_nacimiento);
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Error al conectar con la base de datos.");
        }
    }
}