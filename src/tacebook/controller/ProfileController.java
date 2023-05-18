/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.controller;

import tacebook.view.ProfileView;
import java.util.GregorianCalendar;
import tacebook.model.Comment;
import tacebook.persistence.CommentDB;
import tacebook.model.Message;
import tacebook.persistence.MessageDB;
import tacebook.model.Post;
import tacebook.persistence.PostDB;
import tacebook.model.Profile;
import tacebook.persistence.PersistenceException;
import tacebook.persistence.ProfileDB;
import tacebook.view.GUIProfileView;
import tacebook.view.TextProfileView;

/**
 * Clase que controlará as accións do menú principal. Terá os atributos
 * "profileView" que manterá a referencia ao obxecto vista que se inicializará
 * na declaración cun novo obxecto da clase "ProfileView", e "sessionProfile"
 * que manterá a referencia ao perfil que abre a sesión (atributo para o que
 * haberá un método "get" para que dende a vista se poida obter o perfil do
 * usuario), e que recibirá como parámetro no método "openSession()"
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class ProfileController {

    private ProfileView profileView;
    private Profile sessionProfile;
    private Profile shownProfile;
    private boolean textMode;

    /**
     * Devuelve el perfil de la visión.
     *
     * @return Perfil de la visión.
     */
    public ProfileView getProfileView() {
        return profileView;
    }

    /**
     * Cambai el perfil de la visión.
     *
     * @param profileView Perfil de la visión.
     */
    public void setProfileView(ProfileView profileView) {
        this.profileView = profileView;
    }

    /**
     * Cambia el úlitmo perfil visto.
     *
     * @return Úlitmo perfil visto.
     */
    public Profile getShownProfile() {
        return shownProfile;
    }

    /**
     * Devuelve el úlitmo perfil visto.
     *
     * @param shownProfile Úlitmo perfil visto.
     */
    public void setShownProfile(Profile shownProfile) {
        try {
            if (ProfileDB.findByName(shownProfile.getName(), shownProfile.getPosts().size()) != null) {
                // Si existe el perfil, devolvemos el shownProfile
                this.shownProfile = shownProfile;
                reloadProfile();
            } else {
                profileView.showProfileNotFoundMessage();
            }
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Devuelve el objeto de la clase Profile.
     *
     * @return Objeto de la clase Profile.
     */
    public Profile getSessionProfile() {
        return sessionProfile;
    }

    /**
     * Constructor de la clase ProfileController.
     *
     * @param textMode True si interfaz de texto, false si interfaz gráfica.
     */
    public ProfileController(boolean textMode) {
        this.textMode = textMode;
        if (this.textMode) {
            profileView = new TextProfileView(this);
        } else {
            profileView = new GUIProfileView(null, true, this);
        }
    }

    /**
     * Método que obtén o número de publicacións a mostrar, para o que chamará
     * ao método co mesmo nome da vista do perfil.
     *
     * @return Número de publicaciones.
     */
    public int getPostsShowed() {
        return profileView.getPostsShowed();
    }

    /**
     * Método que obtén o obxecto do perfil da sesión (NOS MINTIÓ ANTONIO)
     * usando a clase "ProfileDB" e mostra o menú do perfil para el.
     */
    public void reloadProfile() {
        try {
            // Actualizamos los datos del sessionProfile en el propio sessionProfile
            shownProfile = ProfileDB.findByName(shownProfile.getName(), getPostsShowed());
            profileView.showProfileMenu(shownProfile);
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Método que abre unha sesión con un perfil, almacenando o obxecto
     * "sessionProfile" no seu atributo e chamando ao método "showProfileMenu()"
     * do obxecto vista.
     *
     * @param sessionProfile Perfil de la sesion.
     */
    public void openSession(Profile sessionProfile) {
        this.sessionProfile = sessionProfile;
        shownProfile = sessionProfile;
        profileView.showProfileMenu(shownProfile);
    }

    /**
     * Método que actualiza o estado do perfil, modificando o atributo do
     * obxecto "profile" e chamando á clase "ProfileDB" para gardar o cambio.
     * Feito isto, invocará o método "reloadProfile()" para recargar o perfil e
     * mostrar de novo o menú.
     *
     * @param newStatus Estado del usuario.
     */
    public void updateProfileStatus(String newStatus) {
        try {
            sessionProfile.setStatus(newStatus);
            ProfileDB.save(sessionProfile);
            ProfileDB.update(sessionProfile);
            reloadProfile();
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Crea un novo obxecto "Post", chama á clase "PostDB" para gardalo e chama
     * ao método "reloadProfile" para refrescar a información do perfil.
     *
     * @param text Texto del post.
     * @param destProfile Perfil de destino del post.
     */
    public void newPost(String text, Profile destProfile) {
        try {
            PostDB.save(new Post(0, new GregorianCalendar().getTime(), text, sessionProfile, destProfile));
            reloadProfile();
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Crea un novo obxecto "Comment", chama á clase "CommentDB" para gardalo e
     * chama ao método "reloadProfile" para refrescar a información do perfil.
     *
     * @param post Post a guardar.
     * @param commentText Comentario del post.
     */
    public void newComment(Post post, String commentText) {
        try {
            CommentDB.save(new Comment(0, new GregorianCalendar().getTime(), commentText, post, sessionProfile));
            reloadProfile();
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Comproba que o perfil da sesión non sexa o autor da publicación e non
     * fixera xa Like sobre a publicación recibida. Se non é así, chama á clase
     * "PostDB" para gardar o Like. En todo caso, refresca a información do
     * perfil chamando a "reloadProfile".
     *
     * @param post Post a checkear.
     */
    public void newLike(Post post) {
        boolean saveLike = true;
        if (!sessionProfile.getName().equals(post.getAuthor().getName())) {
            // Miramos que no es el sessionProfile
            for (Profile profLike : post.getProfileLikes()) {
                if (sessionProfile.getName().equals(profLike.getName())) {
                    // Si ese post ya tiene like del sessionProfile, no le puede
                    // dar otro, por lo tanto ponemos el saveLike a false
                    saveLike = false;
                }
            }
            if (saveLike) {
                try {
                    // Guardamos el like en el post
                    PostDB.saveLike(post, sessionProfile);
                } catch (PersistenceException e) {
                    proccessPersistenceException(e);
                }
            } else {
                profileView.showAlreadyLikedPostMessage();
            }
        } else {
            profileView.showCannotLikeOwnPostMessage();
        }
        reloadProfile();
    }

    /**
     * Comproba que exista un perfil co nome recibido como parámetro, que ese
     * perfil non teña xa amizade co perfil da sesión, e que non haxa xa unha
     * solicitude dese perfil ao perfil da sesión e viceversa. Se non ocorre
     * nada diso, chama á clase "ProfileDB" para gardar a solicitude de amizade.
     * En todo caso, refresca a información do perfil chamando a
     * "reloadProfile".
     *
     * @param profileName Nombre del perfil a comprobar.
     */
    public void newFriendshipRequest(String profileName) {
        try {
            boolean nameFriend = false;
            boolean nameFrienshipRequest = false;
            boolean nameFrienshipRequestExternalProfile = false;

            // Buscamos si el sessionProfile ya es amigo de este usuario
            for (int i = 0; i < sessionProfile.getFriends().size(); i++) {
                if (sessionProfile.getFriends().get(i).getName().equals(profileName)) {
                    nameFriend = true;
                    profileView.showIsAlreadyFriendMessage(profileName);
                }
            }

            // Buscamos si el sessionProfile ya tiene una solicitud de amistad de este usuario
            for (int i = 0; i < sessionProfile.getFriendshipRequests().size(); i++) {
                if (sessionProfile.getFriendshipRequests().get(i).getName().equals(profileName)) {
                    nameFrienshipRequest = true;
                    profileView.showDuplicateFrienshipRequestMessage(profileName);
                }
            }

            // Creamos un perfil en donde almacenamos el perfil con ese nombre
            Profile profileToCheck = ProfileDB.findByName(profileName, 0);

            if (profileToCheck == null) {
                profileView.showProfileNotFoundMessage();
            } else {
                // Si existe un perfil con ese nombre, miramos que ese perfil no tenga
                // ya una solicitud de amistad pendiente del sessionProfile
                for (int i = 0; i < profileToCheck.getFriendshipRequests().size(); i++) {
                    if (profileToCheck.getFriendshipRequests().get(i).getName().equals(sessionProfile.getName())) {
                        nameFrienshipRequestExternalProfile = true;
                        profileView.showExistsFrienshipRequestMessage(profileName);
                    }
                }
            }

            // Si no se cumple nada de lo antes comprobado, creamos la solicitud
            if (profileToCheck != null && !nameFriend && !nameFrienshipRequest && !nameFrienshipRequestExternalProfile) {
                ProfileDB.saveFrienshipRequest(ProfileDB.findByName(profileName, 0), sessionProfile);
            }
            reloadProfile();
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Chama á clase "ProfileDB" para borrar a solicitude de amizade e gravar a
     * amizade entre o perfil de orixe e o perfil da sesión. Despois chama ao
     * método "reloadProfile" para refrescar a información do perfil.
     *
     * @param sourceProfile Perfil de origen.
     */
    public void acceptFriendshipRequest(Profile sourceProfile) {
        try {
            ProfileDB.saveFriendship(sessionProfile, sourceProfile);
            ProfileDB.removeFrienshipRequest(sessionProfile, sourceProfile);
            reloadProfile();
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Chama á clase "ProfileDB" para borrar a solicitude de amizade. Despois
     * chama ao método "reloadProfile" para refrescar a información do perfil.
     *
     * @param sourceProfile Perfil de origen.
     */
    public void rejectFriendshipRequest(Profile sourceProfile) {
        try {
            ProfileDB.removeFrienshipRequest(sessionProfile, sourceProfile);
            reloadProfile();
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Crea un novo obxecto "Message", chama á clase "MessageDB" para gardalo e
     * chama ao método "reloadProfile" para refrescar a información do perfil.
     *
     * @param destProfile Perfil de destino
     * @param text Mensaje para el perfil.
     */
    public void newMessage(Profile destProfile, String text) {
        try {
            MessageDB.save(new Message(0, text, new GregorianCalendar().getTime(), false, destProfile, sessionProfile));
            reloadProfile();
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Chama á clase "MessageDB" para borrar a mensaxe e chama ao método
     * "reloadProfile" para refrescar a información do perfil.
     *
     * @param message Mensaje a borrar.
     */
    public void deleteMessage(Message message) {
        try {
            MessageDB.remove(message);
            reloadProfile();
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Cambia o valor do atributo "read" da mensaxe e true, chama á clase
     * "MessageDB" para actualizar a mensaxe e chama ao método "reloadProfile"
     * para refrescar a información do perfil.
     *
     * @param message Mensaje a actualizar.
     */
    public void markMessageAsRead(Message message) {
        try {
            message.setRead(true);
            MessageDB.update(message);
            reloadProfile();
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Cambia o valor do atributo "read" da mensaxe e true, chama á clase
     * "MessageDB" para actualizar a mensaxe e chama ao método "newMessage" para
     * enviar a mensaxe de resposta e recargar o perfil.
     *
     * @param message Mensaje a actualizar.
     * @param text Respuesta del mensae.
     */
    public void replyMessage(Message message, String text) {
        try {
            message.setRead(true);
            MessageDB.update(message);
            newMessage(sessionProfile, text);
            reloadProfile();
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Este método encárgase de procesar unha excepción de persistencia, e en
     * función do código da excepción chamará a un dos tres métodos engadidos
     * nas vistas no punto anterior.
     *
     * @param ex Excepción de persistencia a procesar.
     */
    private void proccessPersistenceException(PersistenceException ex) {
        switch (ex.getCode()) {
            case PersistenceException.CANNOT_READ:
                profileView.showReadErrorMessage();
                break;
            case PersistenceException.CANNOT_WRITE:
                profileView.showWriteErrorMessage();
                break;
            case PersistenceException.CONECTION_ERROR:
                profileView.showConnectionErrorMessage();
        }
    }
}
