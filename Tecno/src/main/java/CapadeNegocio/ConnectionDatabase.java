package CapadeNegocio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDatabase {

    // Datos de conexión
    private static final String URL = "jdbc:postgresql://www.tecnoweb.org.bo/db_agenda";
    private static final String USUARIO = "agenda";
    private static final String CONTRASEÑA = "agendaagenda";
    private Connection conexion;

    // Constructor
    public ConnectionDatabase() {
        this.conexion = null;
    }

    // Método para conectar a la base de datos
    public void conectar() {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para desconectar de la base de datos
    public void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar personas por nombre, sensible a mayúsculas y minúsculas
    public void buscarPersonasPorNombre(String nombre) {
        if (conexion == null) {
            System.out.println("Conexión no establecida.");
            return;
        }

        // Sensible a mayúsculas y minúsculas con "ILIKE"
        // String consulta = "SELECT per_nom FROM persona WHERE per_nom ILIKE '%" + nombre + "%'";
        
        // NO Sensible a mayúsculas y minúsculas con "LIKE"
        String consulta = "SELECT per_nom FROM persona WHERE per_nom LIKE '%" + nombre + "%'";

        try {
            Statement declaracion = conexion.createStatement();
            ResultSet resultado = declaracion.executeQuery(consulta);

            // Procesar los resultados
            while (resultado.next()) {
                System.out.println("per_nom: " + resultado.getString("per_nom"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener todas las personas de la tabla "persona"
    public void obtenerPersonas() {
        if (conexion == null) {
            System.out.println("Conexión no establecida.");
            return;
        }

        String consulta = "SELECT * FROM persona";

        try {
            Statement declaracion = conexion.createStatement();
            ResultSet resultado = declaracion.executeQuery(consulta);
            ResultSetMetaData metaDatos = resultado.getMetaData();

            // Obtener el número de columnas
            int numeroColumnas = metaDatos.getColumnCount();

            // Mostrar nombres de columnas
            for (int i = 1; i <= numeroColumnas; i++) {
                System.out.print(metaDatos.getColumnName(i) + "\t");
            }
            System.out.println();

            // Procesar los resultados
            while (resultado.next()) {
                for (int i = 1; i <= numeroColumnas; i++) {
                    System.out.print(resultado.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConnectionDatabase db = new ConnectionDatabase();
        db.conectar();
        db.buscarPersonasPorNombre("Er");
        db.desconectar();
    }
}
