/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.persistence;

import tacebook.model.Post;
import tacebook.model.Profile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase que implementa la persistencia de los objetos de la clase Post.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class PostDB {

    /**
     * Almacena unha nova publicación.Neste momento, só habería que engadir a
     * publicación no principio da lista de publicacións do perfil que vai no
     * atributo "profile" da propia publicación. Hai que introducila no comezo
     * da lista para que as publicacións máis recentes saian primeiro.
     *
     * @param post Post a guardar.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     */
    public static void save(Post post) throws PersistenceException {
//        post.getProfile().getPosts().add(0, post);
        Connection c = TacebookDB.getConnection();
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO Post (author, date, profile, text) VALUES (?,?,?,?)");
            pst.setString(1, post.getAuthor().getName());
            pst.setTimestamp(2, new java.sql.Timestamp(post.getDate().getTime()));
            pst.setString(3, post.getProfile().getName());
            pst.setString(4, post.getText());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Garda un Like sobre unha publicación.De momento, só temos que engadir o
     * perfil á lista de "profileLikes" da publicación.
     *
     * @param post Post a guardar.
     * @param profile Like de la publicación.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     */
    public static void saveLike(Post post, Profile profile) throws PersistenceException {
//        post.getProfileLikes().add(profile);
        Connection c = TacebookDB.getConnection();
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO ProfileLikesPost VALUES (?,?)");
            pst.setInt(1, post.getId());
            pst.setString(2, profile.getName());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
