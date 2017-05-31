package es.artiaga;

import java.sql.*;

public class EjemploMain {

   static Connection conexion = null;

    public static void main (String args[]) throws SQLException {

        try {
            conectar();
            consulta();
            consultaFiltrada(1,"Alejandro", "Artiaga");
            transaccion();
        }finally {
            cerrar();
        }



    }

private static void transaccion() throws SQLException {

        final String CLIENTE = "INSERT INTO clientes (Nombre, Apellido, Apellido2, Calle, Numero, Piso, Metros, CodigoPoblacion, CodigoProvincia) VALUES (?, ?, ?, ?, ?, ?, ? ,?, ?)";
        PreparedStatement cliente = null;
        try {
            cliente = conexion.prepareStatement(CLIENTE);
            cliente.setString(1, "Alejandro");
            cliente.setString(2, "Artiaga");
            cliente.setString(3, "Cantero");
            cliente.setInt(4, 4);
            cliente.setInt(5,4);
            cliente.setInt(6,2);
            cliente.setInt(7,100);
            cliente.setInt(8,213);
            cliente.setInt(9, 15);

            cliente.executeUpdate();

            conexion.commit();
            System.out.println("Ejecutado");
        }catch (SQLException e){
            conexion.rollback();
            e.printStackTrace();
        }finally {
            if (cliente != null){
                cliente.close();
            }
        }

}

    private static void consulta() throws SQLException{

        Statement statement = conexion.createStatement();
        ResultSet set = statement.executeQuery("SELECT Codigo, Nombre, Apellido FROM clientes");
        while (set.next()){
            int codigo = set.getInt("codigo");
            String nombre = set.getString("Nombre");
            String apellido = set.getString("Apellido");
            System.out.println("Cliente " + codigo + ". Nombre: " + nombre + " " + apellido);
        }
        set.close();
        statement.close();
    }

    private static void consultaFiltrada(int codigo, String nombre, String apellido) throws SQLException{
        String query = "SELECT Codigo, Nombre, apellido FROM clientes WHERE Codigo = ? and Nombre = ? and Apellido = ?";
        PreparedStatement statement = conexion.prepareStatement(query);
        statement.setInt(1, codigo);
        statement.setString(2,nombre);
        statement.setString(3,apellido);
        ResultSet set = statement.executeQuery();

        while (set.next()){
            System.out.println("Cliente "+ codigo + ". Nombre: " + nombre +" "+ apellido);
        }
        set.close();
        statement.close();

    }

    private static void conectar() throws SQLException {
        String jdbc = "jdbc:mysql://localhost:3306/consumos";
        conexion = DriverManager.getConnection(jdbc, "root", "cambridge2");
        conexion.setAutoCommit(false);
        System.out.println("Estoy Dentro");


    }
        private static void cerrar() throws SQLException {
            if (conexion != null) {
                 conexion.close();
                 System.out.println("Estoy fuera");
            }
    }
}

