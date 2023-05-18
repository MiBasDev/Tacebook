/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.model;

import java.util.ArrayList;

/**
 * Clase que alamacena los perfiles de usuario.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class Profile {

    private String name;
    private String password;
    private String status;
    private ArrayList<Message> messages;
    private ArrayList<Post> posts;
    private ArrayList<Profile> friends;
    private ArrayList<Profile> friendshipRequests;

    /**
     * Constructor de la clase Profile.
     *
     * @param name Nombre del usuario.
     * @param password del usuario.
     * @param status Estado del usuario.
     */
    public Profile(String name, String password, String status) {
        this.name = name;
        this.password = password;
        this.status = status;
        this.messages = new ArrayList();
        this.posts = new ArrayList();
        this.friends = new ArrayList();
        this.friendshipRequests = new ArrayList();
    }

    /**
     * Devuelve el nombre del usuario.
     *
     * @return Nombre del usuario.
     */
    public String getName() {
        return name;
    }

    /**
     * Cambia el nombre del usuario.
     *
     * @param name Nombre del usuario.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Devuelve la contraseña del usuario.
     *
     * @return Contraseña deñ usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Cambia la contraseña del usuario.
     *
     * @param password Contraseña del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Devuelve el estado del usuario.
     *
     * @return Estado del usuario.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Cambia el estado del usuario.
     *
     * @param status Estado del usuario.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Devuelve el arraylist de mensajes del perfil.
     *
     * @return Arraylist de mensajes del perfil.
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * Cambia el arraylist de mensajes del perfil.
     *
     * @param messages Arraylist de mensajes del perfil.
     */
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    /**
     * Devuelve el arraylist de posts del perfil.
     *
     * @return Arraylist de posts del perfil.
     */
    public ArrayList<Post> getPosts() {
        return posts;
    }

    /**
     * Cambia el arraylist de posts del perfil.
     *
     * @param posts Arraylist de posts del perfil.
     */
    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    /**
     * Devuelve el arraylist de amigos del perfil.
     *
     * @return Arraylist de amigos del perfil.
     */
    public ArrayList<Profile> getFriends() {
        return friends;
    }

    /**
     * Cambia el arraylist de amigos del perfil.
     *
     * @param friends Arraylist de amigos del perfil.
     */
    public void setFriends(ArrayList<Profile> friends) {
        this.friends = friends;
    }

    /**
     * Devuelve el arraylist de peticiones de amistad del perfil.
     *
     * @return Arraylist de peticiones de amistad del perfil.
     */
    public ArrayList<Profile> getFriendshipRequests() {
        return friendshipRequests;
    }

    /**
     * Cambia el arraylist de peticiones de amistad del perfil.
     *
     * @param friendshipRequests Arraylist de peticiones de amistad del perfil.
     */
    public void setFriendshipRequests(ArrayList<Profile> friendshipRequests) {
        this.friendshipRequests = friendshipRequests;
    }
}
