/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.persistence;

import tacebook.model.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase que implementa la persistencia de los objetos de la clase Message.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class MessageDB {

    /**
     * Almacena unha nova mensaxe.De momento, só haberá que engadir a mensaxe no
     * comezo da lista de mensaxes do perfil de destino.
     *
     * @param message Mensaje a guardar.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     */
    public static void save(Message message) throws PersistenceException {
//        message.getDestProfile().getMessages().add(0, message);
        Connection c = TacebookDB.getConnection();
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO Message (date, destination, isRead, source, text) VALUES (?,?,?,?,?)");
            pst.setTimestamp(1, new java.sql.Timestamp(message.getDate().getTime()));
            pst.setString(2, message.getDestProfile().getName());
            pst.setBoolean(3, message.isRead());
            pst.setString(4, message.getSourceProfile().getName());
            pst.setString(5, message.getText());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Actualiza a información dunha mensaxe.De momento este método non fai
     * nada, porque modificando os atributos do obxecto xa quedan feitos os
     * cambios.
     *
     * @param message Mensaje a actualizar.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     */
    public static void update(Message message) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        try {
            PreparedStatement pst = c.prepareStatement("UPDATE Message SET isRead=? WHERE date=? AND text=?;");
            pst.setBoolean(1, message.isRead());
            pst.setTimestamp(2, new java.sql.Timestamp(message.getDate().getTime()));
            pst.setString(3, message.getText());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Borra unha mensaxe.De momento, só hai que quitar a mensaxe da lista de
     * mensaxes do perfil de destino.
     *
     * @param message Mensaje a borrar.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     */
    public static void remove(Message message) throws PersistenceException {
//        message.getDestProfile().getMessages().remove(message);
        Connection c = TacebookDB.getConnection();
        try {
            PreparedStatement pst = c.prepareStatement("DELETE FROM Message WHERE destination=? AND id=?;");
            pst.setString(1, message.getDestProfile().getName());
            pst.setInt(2, message.getId());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
