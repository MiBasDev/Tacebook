/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.model;

import java.util.Date;

/**
 * Clase que almacena los mensajes de usuario.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class Message {

    private int id;
    private String text;
    private Date date;
    private boolean read;
    private Profile destProfile;
    private Profile sourceProfile;

    /**
     * Constructor de la clase Message.
     *
     * @param id Número único que referencia al mensaje.
     * @param text Texto del mensaje.
     * @param date Fecha del mensaje.
     * @param read Si el mensaje está leido o no.
     * @param destProfile Perfil de destino.
     * @param sourceProfile Perfil de origen.
     */
    public Message(int id, String text, Date date, boolean read, Profile destProfile, Profile sourceProfile) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.read = read;
        this.destProfile = destProfile;
        this.sourceProfile = sourceProfile;
    }

    /**
     * Devuelve el número único que referencia al mensaje.
     *
     * @return Número único que referencia al mensaje.
     */
    public int getId() {
        return id;
    }

    /**
     * Cambia el número único que referencia al mensaje.
     *
     * @param id Número único que referencia al mensaje.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el texto del mensaje.
     *
     * @return Texto del mensaje.
     */
    public String getText() {
        return text;
    }

    /**
     * Cambia el texto del mensaje.
     *
     * @param text Texto del mensaje.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Devuelve la fecha del mensaje.
     *
     * @return Fecha del mensaje.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Cambia la fecha del mensaje.
     *
     * @param date Fecha del mensaje.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Devuelve si está leido o no el mensaje.
     *
     * @return True si el mensaje está leido, false si no lo está.
     */
    public boolean isRead() {
        return read;
    }

    /**
     * Cambia si está leido o no el mensaje.
     *
     * @param read True si el mensaje está leido, false si no lo está.
     */
    public void setRead(boolean read) {
        this.read = read;
    }

    /**
     * Devuelve el perfil destinatario del mensaje.
     *
     * @return Perfil destinatario del mensaje.
     */
    public Profile getDestProfile() {
        return destProfile;
    }

    /**
     * Cambia el perfil destinatario del mensaje.
     *
     * @param destProfile Perfil destinatario del mensaje.
     */
    public void setDestProfile(Profile destProfile) {
        this.destProfile = destProfile;
    }

    /**
     * Devuelve el perfil fuente del mensaje.
     *
     * @return Perfil fuente del mensaje.
     */
    public Profile getSourceProfile() {
        return sourceProfile;
    }

    /**
     * Cambia el perfil fuente del mensaje.
     *
     * @param sourceProfile Perfil fuente del mensaje.
     */
    public void setSourceProfile(Profile sourceProfile) {
        this.sourceProfile = sourceProfile;
    }
}
