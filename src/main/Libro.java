package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Libro {
    private String titulo, genero;
    private int anio_publicacion, id_libro, id_autor;
    private Scanner sca = new Scanner(System.in);

    public Libro() {
        System.out.println("Elige una de las opciones");
        System.out.println("1. Añadir un Libro");
        System.out.println("2. Mostrar lista de Libros registrados");
        System.out.println("3. Modificar un Libro");
        System.out.println("4. Eliminar un Libro");
        System.out.println("5. Salir al menú principal");
        int opcion = sca.nextInt();

        switch (opcion) {
            case 1:
                añadirLibro();
                break;
            case 2:
                mostrarLibro();
                break;
            case 3:
                modificarLibro();
                break;
            case 4:
                eliminarLibro();
                break;
            case 5:
                break;
            default:
                System.err.println("Elige una opcion del menú");
        }
    };

    public void añadirLibro() {
        System.out.println("Introduce el título: ");
        sca.nextLine();
        titulo = sca.nextLine();
        System.out.println("Introduce el ID del autor");
        id_autor = sca.nextInt();
        System.out.println("Introduce el año de publicación: ");
        anio_publicacion = sca.nextInt();
        System.out.println("Introduce el género: ");
        sca.nextLine();
        genero = sca.nextLine();

        if (!titulo.isEmpty() && id_autor > 0 && anio_publicacion > 0 && !genero.isEmpty()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
                String sql = "INSERT INTO libro (titulo, id_autor, anio_publicacion, genero) VALUES ('" + titulo + "','"
                        + id_autor + "','" + anio_publicacion + "','" + genero + "')";
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

    public void eliminarLibro() {
        System.out.println("Introduce el id del libro que quieres eliminar");
        id_libro = sca.nextInt();

        if (id_libro > 0) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
                String sql = "DELETE FROM libro WHERE id_libro = '" + id_libro + "'";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id_libro);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Datos eliminados de la base de datos.");
                } else {
                    System.out.println("No se encontró ningún Libro con ese nombre.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error al conectar con la base de datos.");
            }
        } else {
            System.err.println("Por favor, ingresa el nombre del Libro a eliminar.");
        }
    }

    public void modificarLibro() {
        System.out.println("Introduce el id del libro que desea modificar: ");
        int id_libro = sca.nextInt();

        // Verificar si se ingresó el ID a modificar
        if (id_libro > 0) {
            try {
                // Conectar a la base de datos
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");

                // Consulta SQL para actualizar los datos
                String sql = "UPDATE libro SET titulo='" + titulo + "', id_autor='" + id_autor + "', anio_publicacion='"
                        + anio_publicacion + "', genero='" + genero + "' WHERE id_libro='" + id_libro + "'";
                PreparedStatement statement = connection.prepareStatement(sql);

                // Establecer los valores en la consulta SQL
                statement.setString(1, titulo);
                statement.setInt(2, id_autor);
                statement.setInt(3, anio_publicacion);
                statement.setString(4, genero);
                statement.setInt(4, id_libro);

                // Ejecutar la actualización
                int filasActualizadas = statement.executeUpdate();

                // Verificar si se actualizó correctamente
                if (filasActualizadas > 0) {
                    System.out.println("Datos actualizados en la base de datos.");
                } else {
                    System.err.println("No se encontró el ID especificado.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error al conectar con la base de datos.");
            }
        } else {
            System.err.println("Por favor, ingresa el ID a modificar.");
        }
    }

    public void mostrarLibro() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
            String sql = "SELECT * FROM libro";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Lista de Libros Registrados:");
            System.out.printf("%-5s %-20s %-20s %-10s%n", "ID", "Titulo", "ID_Autor", "Año Nacimiento", "Genero");

            while (resultSet.next()) {
                int id_libro = resultSet.getInt("id_libro");
                String titulo = resultSet.getString("titulo");
                int id_autor = resultSet.getInt("id_autor");
                int anio_nacimiento = resultSet.getInt("anio_nacimiento");
                String genero = resultSet.getString("genero");

                System.out.printf("%-5d %-20s %-20s %-10d%n", id_libro, titulo, id_autor, anio_nacimiento, genero);
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