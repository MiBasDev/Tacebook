/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Clase que almacena los posts de usuario.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class Post {

    private int id;
    private Date date;
    private String text;
    private Profile author;
    private Profile profile;
    private ArrayList<Profile> profileLikes;
    private ArrayList<Comment> comments;

    /**
     * Constructor de la clase Post.
     *
     * @param id Número único que referencia al post.
     * @param date Fecha del post.
     * @param text Texto del post.
     * @param author Autor del post.
     * @param profile Perfil del post.
     */
    public Post(int id, Date date, String text, Profile author, Profile profile) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.author = author;
        this.profile = profile;
        this.profileLikes = new ArrayList();
        this.comments = new ArrayList();
    }

    /**
     * Devuelve el número único que referencia al post.
     *
     * @return Número único que referencia al post.
     */
    public int getId() {
        return id;
    }

    /**
     * Cambia el número único que referencia al post.
     *
     * @param id Número único que referencia al post.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve la fecha del post.
     *
     * @return Fecha del post.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Cambia la fecha del post.
     *
     * @param date Fecha del post.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Devuelve el texto del post.
     *
     * @return Texto del post.
     */
    public String getText() {
        return text;
    }

    /**
     * Cambia el texto del post.
     *
     * @param text Texto del post.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Devuelve el arraylist de likes del post.
     *
     * @return Arraylist de likes del post.
     */
    public ArrayList<Profile> getProfileLikes() {
        return profileLikes;
    }

    /**
     * Cambia el arraylist de likes del post.
     *
     * @param profileLikes Arraylist de likes del post.
     */
    public void setProfileLikes(ArrayList<Profile> profileLikes) {
        this.profileLikes = profileLikes;
    }

    /**
     * Devuelve el autor del post.
     *
     * @return Autor del post.
     */
    public Profile getAuthor() {
        return author;
    }

    /**
     * Cambia el autor del post.
     *
     * @param author Autor del post.
     */
    public void setAuthor(Profile author) {
        this.author = author;
    }

    /**
     * Devuelve el perfil del post.
     *
     * @return Perfil del post.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Cambia el perfil del post.
     *
     * @param profile Perfil del post.
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * Devuelve el arraylist de comentarios del post.
     *
     * @return Arraylist de comentarios del post.
     */
    public ArrayList<Comment> getComments() {
        return comments;
    }

    /**
     * Cambia el arraylist de comentarios del post.
     *
     * @param comments Arraylist de comentarios del post.
     */
    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
