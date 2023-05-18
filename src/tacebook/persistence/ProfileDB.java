/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.persistence;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import tacebook.model.Profile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tacebook.model.Comment;
import tacebook.model.Message;
import tacebook.model.Post;

/**
 * Clase que implementa la persistencia de los objetos de la clase Profile.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class ProfileDB {

    /**
     * Método que recibe como parámetro o nome dun usuario e o número de
     * publicacións dese perfil que queremos recuperar, e devolve o obxecto
     * "Profile" asociado a ese usuario, ou "null" se o usuario non existe.
     *
     * @param name Nombre de usuario.
     * @param numberOfPosts Número de publicaciones del usuario.
     * @return Perfil del usuario si existe, sino null.
     * @throws tacebook.persistence.PersistenceException Escepción de
     * persistencia.
     */
    public static Profile findByName(String name, int numberOfPosts) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        Profile p = null;
        try {
            String sql = "Select * FROM Profile WHERE name=?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rst = st.executeQuery();
            if (rst.next()) {
                p = new Profile(rst.getString("name"), rst.getString("password"), rst.getString("status"));
                fillProfile(p, numberOfPosts, c);
            }
            rst.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return p;
    }

    /**
     * Método que recibe como parámetros o nome dun usuario, o contrasinal e o
     * número de publicacións dese perfil que queremos recuperar, e devolve o
     * obxecto "Profile" asociado a ese usuario, ou "null" se ese usuario non
     * existe.
     *
     * @param name Nombre de usuario.
     * @param password Contraseña del usuario.
     * @param numberOfPosts Número de publicaciones del usuario.
     * @return Perfil del usuario si existe, sino null.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     * @throws java.security.NoSuchAlgorithmException
     */
    public static Profile findByNameAndPassword(String name, String password, int numberOfPosts) throws PersistenceException, NoSuchAlgorithmException {
        Connection c = TacebookDB.getConnection();
        Profile p = null;
        try {
            String sql = "Select * FROM Profile WHERE name=?";
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rst = st.executeQuery();
            if ((rst.next()) && (rst.getString("password").equals(getPasswordHash(password)))) {
                p = new Profile(rst.getString("name"), "", rst.getString("status"));
                fillProfile(p, numberOfPosts, c);
            }
            rst.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return p;
    }

    /**
     * Método que llena un perfil con todos sus datos.
     *
     * @param profile Perfil a llenar.
     * @param numberOfPosts Número de posts a cargar.
     * @param c Conexión a la base de datos.
     */
    private static void fillProfile(Profile profile, int numberOfPosts, Connection c) throws PersistenceException {
        String sql;
        PreparedStatement pst;
        ResultSet rst;
        try {
            // Sacamos -Posts- del perfil
            sql = "SELECT * FROM Post WHERE profile=?";
            pst = c.prepareStatement(sql);
            pst.setString(1, profile.getName());
            rst = pst.executeQuery();
            int postCap = 0;
            while (rst.next() && postCap < numberOfPosts) {
                // Sacamos -Comments- del post
                Post post = new Post(rst.getInt("id"), rst.getTimestamp("date"), rst.getString("text"), new Profile(rst.getString("author"), "", ""), profile);
                String sqlComment = "SELECT * FROM Comment WHERE idPost=?";
                PreparedStatement pstComment = c.prepareStatement(sqlComment);
                pstComment.setInt(1, post.getId());
                ResultSet rstComment = pstComment.executeQuery();
                while (rstComment.next()) {
                    Comment commment = new Comment(rstComment.getInt("id"), rstComment.getTimestamp("date"), rstComment.getString("text"), post, new Profile(rstComment.getString("author"), "", ""));
                    post.getComments().add(commment);
                }
                rstComment.close();
                pstComment.close();

                // Sacamos -Likes- del post
                String sqlLike = "SELECT * FROM ProfileLikesPost WHERE idPost=?";
                PreparedStatement pstLike = c.prepareStatement(sqlLike);
                pstLike.setInt(1, post.getId());
                ResultSet rstLike = pstLike.executeQuery();
                while (rstLike.next()) {
                    post.getProfileLikes().add(new Profile(rstLike.getString("profile"), "", ""));
                }
                pstLike.close();
                rstLike.close();
                profile.getPosts().add(post);
                postCap++;
            }
            rst.close();
            pst.close();

            // Sacamos -Friends- del perfil
            sql = "SELECT profile1, profile2, profile1.status as status1, profile2.status as status2 FROM Friend "
                    + "LEFT JOIN Profile as profile1 on Friend.profile1=profile1.name "
                    + "LEFT JOIN Profile as profile2 on Friend.profile2=profile2.name "
                    + "WHERE profile1=? OR profile2=?";
            pst = c.prepareStatement(sql);
            pst.setString(1, profile.getName());
            pst.setString(2, profile.getName());
            rst = pst.executeQuery();
            while (rst.next()) {
                if (rst.getString("profile1").equals(profile.getName())) {
                    profile.getFriends().add(new Profile(rst.getString("profile2"), "", rst.getString("status2")));
                } else {
                    profile.getFriends().add(new Profile(rst.getString("profile1"), "", rst.getString("status1")));
                }
            }
            rst.close();
            pst.close();

            // Sacamos -FriendshipRequests- de perfil
            sql = "SELECT * FROM FriendRequest WHERE destinationProfile=?";
            pst = c.prepareStatement(sql);
            pst.setString(1, profile.getName());
            rst = pst.executeQuery();
            while (rst.next()) {
                profile.getFriendshipRequests().add(new Profile(rst.getString("sourceProfile"), "", ""));
            }
            rst.close();
            pst.close();

            // Sacamos -Messages- del perfil
            sql = "SELECT * FROM Message WHERE source=? OR destination=?";
            pst = c.prepareStatement(sql);
            pst.setString(1, profile.getName());
            pst.setString(2, profile.getName());
            rst = pst.executeQuery();
            while (rst.next()) {
                if (rst.getString("destination").equals(profile.getName())) {
                    Message message = new Message(rst.getInt("id"), rst.getString("text"), rst.getTimestamp("date"),
                            rst.getBoolean("isRead"), new Profile(rst.getString("destination"), "", ""), new Profile(rst.getString("source"), "", ""));
                    profile.getMessages().add(message);
                }
            }
            rst.close();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Método que almacena o perfil no almacenamento.
     *
     * @param profile Perfil del usuario.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     */
    public static void save(Profile profile) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO Profile(name, password, status) VALUES (?,?,?);");
            pst.setString(1, profile.getName());
            pst.setString(2, getPasswordHash(profile.getPassword()));
            pst.setString(3, profile.getStatus());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Método que actualiza o perfil no almacemento (neste caso, non ten que
     * facer nada).
     *
     * @param profile Perfil del usuario.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     */
    public static void update(Profile profile) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        try {
            PreparedStatement pst = c.prepareStatement("UPDATE Profile SET status=? WHERE name=?;");
            pst.setString(1, profile.getStatus());
            pst.setString(2, profile.getName());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Almacena unha nova solicitude de amizade.De momento, simplemente terá que
     * engadir o perfil de orixe na lista de solicitudes do perfil de destino.
     *
     * @param destProfile Perfil de destino.
     * @param sourceProfile Perfil fuente.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     */
    public static void saveFrienshipRequest(Profile destProfile, Profile sourceProfile) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO FriendRequest(destinationProfile, sourceProfile) VALUES (?,?);");
            pst.setString(1, destProfile.getName());
            pst.setString(2, sourceProfile.getName());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Borra unha solicitude de amizade.De momento, só haberá que borrar o
     * perfil de orixe da lista de solicitudes do perfil de destino.
     *
     * @param destProfile Perfil de destino.
     * @param sourceProfile Perfil fuente.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     */
    public static void removeFrienshipRequest(Profile destProfile, Profile sourceProfile) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        try {
            PreparedStatement pst = c.prepareStatement("DELETE FROM FriendRequest WHERE destinationProfile=? AND sourceProfile=?;");
            pst.setString(1, destProfile.getName());
            pst.setString(2, sourceProfile.getName());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Almacena unha amizade entre dous perfís.De momento, só haberá que engadir
     * o perfil 1 á lista de amigos do perfil 2 e ao perfil 2 á lista de amigos
     * do perfil 1.
     *
     * @param profile1 Primer perfil.
     * @param profile2 Segundo perfil.
     * @throws tacebook.persistence.PersistenceException Excepción de
     * persistencia.
     */
    public static void saveFriendship(Profile profile1, Profile profile2) throws PersistenceException {
        Connection c = TacebookDB.getConnection();
        try {
            PreparedStatement pst = c.prepareStatement("INSERT INTO Friend(profile1, profile2) VALUES (?,?);");
            pst.setString(1, profile1.getName());
            pst.setString(2, profile2.getName());
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Método que crea un hash en base a una contraseña,
     *
     * @param password Contraseña a encriptar.
     * @return Contraseña encriptada.
     * @throws NoSuchAlgorithmException Excepción que lanza.
     */
    private static String getPasswordHash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        return new String(messageDigest.digest());
    }
}
