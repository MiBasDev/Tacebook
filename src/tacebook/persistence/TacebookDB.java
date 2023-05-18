/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import tacebook.model.Profile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que almacena la información de los perfiles creados.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class TacebookDB {

    /**
     * Devuelve el arraylist de perfiles.
     *
     * @return Arraylist de perfiles.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     */
    public static ArrayList<Profile> getProfiles() throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        ArrayList<Profile> profilesDB = new ArrayList();
        try {
            String sql = "Select * FROM Profile";
            Statement st = c.createStatement();
            ResultSet rst = st.executeQuery(sql);
            while (rst.next()) {
                Profile p = new Profile(rst.getString("name"), rst.getString("password"), rst.getString("status"));
                profilesDB.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return profilesDB;
    }

    /**
     * Método para cerrar la conexión con la base de datos.
     */
    public static void close() {
        try {
            try {
                TacebookDB.getConnection().close();
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } catch (PersistenceException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Referencia á conexión coa BD
    private static Connection connection = null;

    /**
     * Obtén unha única conexión coa base de datos, abríndoa se é necesario.
     *
     * @return Conexión coa base de datos aberta.
     * @throws PersistenceException Se se produce un erro ao conectar coa BD.
     */
    public static Connection getConnection() throws PersistenceException {
        // Obtemos unha conexión coa base de datos
        try {
            if (connection == null) {
                // Conexión SQLITO
                //connection = DriverManager.getConnection("jdbc:sqlite://home/mbasgan/Documentos/1º DAW/Programación/tacebook.db/tacebook.db", "", "");

                // Conexión MYSQL
                InputStream input = TacebookDB.class.getClassLoader().getResourceAsStream("resources/db.properties");
                if (input == null) {
                    System.out.println("No se puede leer el fichero de propiedades");
                } else {
                    // Cargamos las propiedades del fichero
                    Properties prop = new Properties();
                    prop.load(input);
                    // Obtenemos el valor de sus propiedades y cerramos el flujo
                    String url = prop.getProperty("url");
                    String user = prop.getProperty("user");
                    String password = prop.getProperty("password");
                    input.close();
                    // Usamos los valores para conectarnos a la base de datos
                    connection = DriverManager.getConnection(url, user, password);
                    System.out.println("********************************");
                    System.out.println("* Conexion realizada con éxito *");
                    System.out.println("********************************");
                }
            }
        } catch (SQLException | IOException e) {
            throw new PersistenceException(PersistenceException.CONECTION_ERROR, e.getMessage());
        }
        return connection;
    }
}
