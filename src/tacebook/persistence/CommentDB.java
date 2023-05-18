/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.persistence;

import tacebook.model.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase que implementa la persistencia de los objetos de la clase Comment.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class CommentDB {

    /**
     * Que almacena un novo comentario.De momento, só engade o comentario no
     * principio da lista de comentarios da publicación que está no atributo
     * "post" do propio comentario. Hai que introducilo no comezo da lista para
     * que os comentarios máis recentes saian primeiro.
     *
     * @param comment Comentario a almacenar.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     */
    public static void save(Comment comment) throws PersistenceException {
//        comment.getPost().getComments().add(0, comment);
        Connection c = TacebookDB.getConnection();
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO Comment(author, date, idPost, text) VALUES (?,?,?,?)");
            pst.setString(1, comment.getSourceProfile().getName());
            pst.setTimestamp(2, new java.sql.Timestamp(comment.getDate().getTime()));
            pst.setInt(3, comment.getPost().getId());
            pst.setString(4, comment.getText());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
