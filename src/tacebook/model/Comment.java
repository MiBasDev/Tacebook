/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.model;

import java.util.Date;

/**
 * Clase que almacena los comentarios de usuario.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class Comment {

    private int id;
    private Date date;
    private String text;
    private Post post;
    private Profile sourceProfile;

    /**
     * Constructor de la clase Comment.
     *
     * @param id Número único que referencia al comentario.
     * @param date Fecha del comentario
     * @param text Texto del comentario.
     * @param post Post del comentario.
     * @param sourceProfile Perfil del comentario.
     */
    public Comment(int id, Date date, String text, Post post, Profile sourceProfile) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.post = post;
        this.sourceProfile = sourceProfile;
    }

    /**
     * Devuelve el número único que referencia al comentario.
     *
     * @return Número único que referencia al comentario.
     */
    public int getId() {
        return id;
    }

    /**
     * Cambia el número único que referencia al comentario.
     *
     * @param id Número único que referencia al comentario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve la fecha del comentario.
     *
     * @return Fecha del comentario.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Cambia la fecha del comentario.
     *
     * @param date Decha del comentario.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Devuelve el texto del comentario.
     *
     * @return Texto del comentario.
     */
    public String getText() {
        return text;
    }

    /**
     * Cambia el texto del comentario.
     *
     * @param text Texto del comentario.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Devuelve el post del comentario.
     *
     * @return Post del comentario.
     */
    public Post getPost() {
        return post;
    }

    /**
     * Cambia el post del comentario.
     *
     * @param post Post del comentario.
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Devuelve el perfil del comentario.
     *
     * @return Perfil del comentario.
     */
    public Profile getSourceProfile() {
        return sourceProfile;
    }

    /**
     * Cambia el perfil del comentario.
     *
     * @param sourceProfile Perfil del comentario.
     */
    public void setSourceProfile(Profile sourceProfile) {
        this.sourceProfile = sourceProfile;
    }
}
