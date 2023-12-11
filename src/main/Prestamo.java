package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Prestamo {
    private int id_prestamo, id_libro, id_usuario, fecha_prestamo_dia, fecha_prestamo_mes, fecha_prestamo_anio, fecha_devolucion_dia, fecha_devolucion_mes, fecha_devolucion_anio;
    private Scanner sca = new Scanner(System.in);

    Principal principal = new Principal();

    public Prestamo() {
        System.out.println("Elige una de las opciones");
        System.out.println("1. Hacer un préstamo");
        System.out.println("2. Mostrar lista de préstamos");
        System.out.println("3. Modificar un préstamo");
        System.out.println("4. Eliminar un préstamo");
        System.out.println("5. Salir al menú principal");
        int optionMenuActor = sca.nextInt();

        switch(optionMenuActor){
            case 1:
				añadirPrestamo();
            break;
            case 2:
				mostrarPrestamo();
            break;
            case 3:
				modificarPrestamo();
            break;
            case 4:
				eliminarPrestamo();
            break;
            case 5:
				principal.mostrarMenu();
            break;
            default:
            System.err.println("Elige una opcion del menú");
        }
    }

    public void añadirPrestamo() {
        System.out.println("Introduce el ID del libro: ");
        id_libro = sca.nextInt();

        System.out.println("Introduce el ID del usuario: ");
        id_usuario = sca.nextInt();

        System.out.println("Introduce la fecha del prestamo: (DD:MM:AA)");
        fecha_prestamo_dia = sca.nextInt();
        fecha_prestamo_mes = sca.nextInt();
        fecha_prestamo_anio = sca.nextInt();

        System.out.println("Introduce la fecha de la devolución: (DD:MM:AA)");
        fecha_devolucion_dia = sca.nextInt();
        fecha_devolucion_mes = sca.nextInt();
        fecha_devolucion_anio = sca.nextInt();

        if (id_libro > 0 && id_usuario > 0 &&  fecha_prestamo_dia > 0 &&  fecha_prestamo_mes > 0 &&  fecha_prestamo_anio > 0 &&  fecha_devolucion_dia > 0 &&  fecha_devolucion_mes > 0 &&  fecha_devolucion_anio > 0) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
                String sql = "INSERT INTO prestamo (id_libro, id_usuario, fecha_prestamo_dia, fecha_prestamo_mes, fecha_prestamo_anio) VALUES ('"+id_libro+"','"+id_usuario+"','"+fecha_prestamo_dia+"','"+fecha_prestamo_mes+"','"+fecha_prestamo_anio+"','"+fecha_devolucion_dia+"','"+fecha_devolucion_mes+"','"+fecha_devolucion_anio+"')";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                System.out.println("Datos enviados y guardados en la base de datos.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error al conectar con la base de datos.");
            }
        } else {
            System.err.println("Por favor, completa todos los campos.");
        }
    }

    public void eliminarPrestamo() {
        System.out.println("Introduce el ID del prestamo a eliminar: ");
        int id_prestamo = sca.nextInt();

        if (id_prestamo > 0) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
                String sql = "DELETE FROM prestamo WHERE id_prestamo = '" + id_prestamo + "'";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, id_prestamo);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Datos eliminados de la base de datos.");
                } else {
                    System.out.println("No se encontró ningún préstamo con ese ID.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error al conectar con la base de datos.");
            }
        } else {
            System.err.println("Por favor, ingresa un ID de autor válido.");
        }
    }

    public void modificarPrestamo() {
        System.out.println("Introduce el ID del préstamo a modificar: ");
        int id_prestamo = sca.nextInt();
        sca.nextLine();

        System.out.println("Introduce la fecha de la devolución: (DD:MM:AA)");
        int nueva_fecha_devolucion_dia = sca.nextInt();
        int nueva_fecha_devolucion_mes = sca.nextInt();
        int nueva_fecha_devolucion_anio = sca.nextInt();

        if (id_prestamo > 0 && nueva_fecha_devolucion_dia > 0 && nueva_fecha_devolucion_mes > 0 && nueva_fecha_devolucion_anio > 0) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
                String sql = "UPDATE prestamo SET fecha_devolucion_dia = '"+nueva_fecha_devolucion_dia+"', nacionalidad = '"+nueva_fecha_devolucion_mes+"', anio_nacimiento = '"+nueva_fecha_devolucion_anio+"' WHERE id_prestamo = '"+id_prestamo+"'";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, nueva_fecha_devolucion_dia);
                statement.setInt(2, nueva_fecha_devolucion_mes);
                statement.setInt(3, nueva_fecha_devolucion_anio);
                statement.setInt(4, id_prestamo);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Datos del préstamo modificados en la base de datos.");
                } else {
                    System.out.println("No se encontró ningún préstamo con ese ID.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.err.println("Error al conectar con la base de datos.");
            }
        } else {
            System.err.println("Por favor, ingresa valores válidos para modificar el préstamo.");
        }
    }

    public void mostrarPrestamo() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/biblioteca", "root", "");
            String sql = "SELECT * FROM prestamo";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("Lista de Préstamos Registrados:");
            System.out.printf("%-5s %-20s %-20s %-10s%n", "ID", "LIBRO", "USUARIO", "DIA", "MES", "AÑO", "DEVOLUCION(DIA)", "DEVOLUCION(MES)", "DEVOLUCION(AÑO)");

            while (resultSet.next()) {
                int id_prestamo = resultSet.getInt("id_prestamo");
                int id_libro = resultSet.getInt("id_libro");
                int id_usuario = resultSet.getInt("id_usuario");
                int fecha_prestamo_dia = resultSet.getInt("fecha_prestamo_dia");
                int fecha_prestamo_mes = resultSet.getInt("fecha_prestamo_mes");
                int fecha_prestamo_anio = resultSet.getInt("fecha_prestamo_anio");
                int fecha_devolucion_dia = resultSet.getInt("fecha_devolucion_dia");
                int fecha_devolucion_mes = resultSet.getInt("fecha_devolucion_mes");
                int fecha_devolucion_anio = resultSet.getInt("fecha_devolucion_anio");

                System.out.printf("%-5d %-20s %-20s %-10d%n", id_prestamo, id_libro, id_usuario, fecha_prestamo_dia, fecha_prestamo_mes, fecha_prestamo_anio, fecha_devolucion_dia, fecha_devolucion_mes, fecha_devolucion_anio);
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