/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.view;

import tacebook.model.Profile;

/**
 * Interfaz que define el menú de visualización del perfil.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public interface ProfileView {

    /**
     * Devuelve el objeto de la clase ProfileController.
     *
     * @return Objeto de la clase ProfileController.
     */
    public int getPostsShowed();

    /**
     * étodo que chamará ao método "showProfileInfo" para mostrar a información
     * do perfil, e a continuación mostrará as opcións de cambiar o estado do
     * perfil (que provocará a chamada ao método "changeStatus") e pechar a
     * sesión (que non fará nada, simplemente sairá do método)
     *
     * @param profile Perfil del usuario.
     */
    public void showProfileMenu(Profile profile);

    /**
     * Informa de que non se encontra a mensaxe.
     */
    public void showProfileNotFoundMessage();

    /**
     * Informa de que non se pode facer like sobre unha publicación propia.
     */
    public void showCannotLikeOwnPostMessage();

    /**
     * Informa de que non se pode facer like sobre unha publicación sobre a que
     * xa se fixo like.
     */
    public void showAlreadyLikedPostMessage();

    /**
     * Informa de que xa tes amizade con ese perfil.
     *
     * @param profileName Nombre de perfil
     */
    public void showIsAlreadyFriendMessage(String profileName);

    /**
     *
     * @param profileName Nombre de perfil
     */
    public void showExistsFrienshipRequestMessage(String profileName);

    /**
     * Informa de que ese perfil xa ten unha solicitude de amizade contigo.
     *
     * @param profileName Nombre de perfil
     */
    public void showDuplicateFrienshipRequestMessage(String profileName);

    /**
     * Mostrará a mensaxe "Erro na conexión co almacén de datos!".
     */
    public void showConnectionErrorMessage();

    /**
     * Mostrará a mensaxe "Erro na lectura de datos!".
     */
    public void showReadErrorMessage();

    /**
     * Mostrará a mensaxe "Erro na escritura dos datos!".
     */
    public void showWriteErrorMessage();
}
