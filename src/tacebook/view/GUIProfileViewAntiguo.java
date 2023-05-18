/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.view;

import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import tacebook.model.Profile;
import tacebook.controller.ProfileController;

/**
 * Clase que define la interfaz gráfica del perfil.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class GUIProfileViewAntiguo implements ProfileView {

    private int postsShowed;
    private ProfileController profileController;
    private SimpleDateFormat formatter;
    private GUIProfileView dialogWindow;

    /**
     * Devuelve el valor parael objeto ProfileCOntroller.
     *
     * @return Objeto de la clase ProfileController.
     */
    public ProfileController getProfileController() {
        return profileController;
    }

    /**
     * Cambia el valor para el objeto ProfileController.
     *
     * @param profileController Objeto de la clase ProfileController.
     */
    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }

    /**
     * Devuelve un formato de fecha.
     *
     * @return Formato de fecha.
     */
    public SimpleDateFormat getFormatter() {
        return formatter;
    }

    /**
     * Cambia el formato de fecha.
     *
     * @param formatter Objeto de esta clase.
     */
    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    /**
     * Constructor de la clase ProfileView.
     *
     * @param profileController Objeto de la clase ProfileController.
     */
    public GUIProfileViewAntiguo(ProfileController profileController) {
        this.profileController = profileController;
        this.postsShowed = 10;
        this.formatter = new SimpleDateFormat("dd/MM/yyy 'a las' HH:mm:ss");
//        this.dialogWindow = new GUIProfileView(null, true);
    }

    /**
     * Devuelve el objeto de la clase ProfileController.
     *
     * @return Objeto de la clase ProfileController.
     */
    @Override
    public int getPostsShowed() {
        return postsShowed;
    }

    /**
     * Cambia el objeto de la clase ProfileController.
     *
     * @param postsShowed Objeto de la clase ProfileController.
     */
    public void setPostsShowed(int postsShowed) {
        this.postsShowed = postsShowed;
    }

    /**
     * Método que encárgase de mostrar toda a información do perfil, aínda que
     * de momento só será o nome e estado actual.
     *
     * @param ownProfile True si ve su propio perfil, false si ve el de otro.
     * @param profile Perfil del usuario.
     */
    private void showProfileInfo(boolean ownProfile, Profile profile) {
//        System.out.println("");
//        System.out.println("------VERSIÓN GUI------");
//        System.out.println("Nombre del usuario: " + profile.getName());
//        System.out.println("Estado actual: " + profile.getStatus());
        if (ownProfile) {
            // Nombre y estado del usuario
//            dialogWindow.setProfileSettings(profile);
//            dialogWindow.setBiography(ownProfile, profile, this);
            System.out.println("Tú biografía (" + postsShowed + " últimas publicaciones): ");
            if (!profile.getPosts().isEmpty()) {
                // Si los posts no están vacíos, printeamos los post que haya
                for (int i = 0; (i < profile.getPosts().size()) && (i < postsShowed); i++) {
                    // Si son del sessionProfile sacamos un sout con sus posts
                    if (profile.getPosts().get(i).getAuthor().getName().equals(profileController.getSessionProfile().getName())) {
                        System.out.println("    " + i + ". El " + formatter.format(profile.getPosts().get(i).getDate())
                                + " escribiste (" + profile.getPosts().get(i).getProfileLikes().size() + " likes): ");
                        System.out.println("\t" + profile.getPosts().get(i).getText());
                    } else {
                        // Si son de otro perfil, sacamos un sout con sus posts y el nombre respectivo
                        System.out.println("    " + i + ". El " + formatter.format(profile.getPosts().get(i).getDate())
                                + " " + profile.getPosts().get(i).getAuthor().getName()
                                + " ha escrito (" + profile.getPosts().get(i).getProfileLikes().size() + " likes): ");
                        System.out.println("\t" + profile.getPosts().get(i).getText());
                    }
                    // Si los posts tienen comentarios, los printeamos todos con su respectivo autor
                    if (!profile.getPosts().get(i).getComments().isEmpty()) {
                        for (int j = 0; j < profile.getPosts().get(i).getComments().size(); j++) {
                            System.out.println("\t" + "- " + profile.getPosts().get(i).getComments().get(j).getText() + " - "
                                    + profile.getPosts().get(i).getComments().get(j).getSourceProfile().getName() + " - "
                                    + formatter.format(profile.getPosts().get(i).getComments().get(j).getDate()));
                        }
                    }
                }
            }
            System.out.println("Amigos del usuario: ");
            if (!profile.getFriends().isEmpty()) {
                // Si hay amigos del usuario, los printeamos
                for (int i = 0; i < profile.getFriends().size(); i++) {
                    System.out.println(i + ". " + profile.getFriends().get(i).getName() + " - " + profile.getFriends().get(i).getStatus());
                }
            }
            if (!profile.getMessages().isEmpty()) {
                // Si hay mensajes privaos, los printeamos
                System.out.println("Mensajes privados: ");
                for (int i = 0; i < profile.getMessages().size(); i++) {
                    if (!profile.getMessages().get(i).isRead()) {
                        // Si no está leido el mensaje, le ponemos un * delante (más cool)
                        System.out.println("Tienes " + profile.getMessages().size() + " mensajes sin leer.");
                        System.out.println("*" + i + ". De " + profile.getMessages().get(i).getSourceProfile().getName()
                                + "(" + new SimpleDateFormat("dd/MM/yy").format(profile.getMessages().get(i).getDate())
                                + ") --> " + profile.getMessages().get(i).getText());
                    } else {
                        System.out.println(i + ". De " + profile.getMessages().get(i).getSourceProfile().getName()
                                + "(" + new SimpleDateFormat("dd/MM/yy").format(profile.getMessages().get(i).getDate())
                                + ") --> " + profile.getMessages().get(i).getText());
                    }
                }
            }
            if (!profile.getFriendshipRequests().isEmpty()) {
                // Si hay solicitudes de amistad, las printeamos
                System.out.println("Solicitudes de amistad del usuario: ");
                for (int i = 0; i < profile.getFriendshipRequests().size(); i++) {
                    System.out.println(i + ". " + profile.getFriendshipRequests().get(i).getName() + " quiere ser tu amig@.");
                }
            }
            System.out.println("");
        } else {
            System.out.println("Su biografía (10 últimas publicaciones): ");
            if (!profile.getPosts().isEmpty()) {
                // Si los posts no están vacíos, printeamos los post que haya
                for (int i = 0; (i < profile.getPosts().size()) && (i < postsShowed); i++) {
                    // Si son del sessionProfile sacamos un sout con sus posts
                    if (profile.getName().equals(profileController.getSessionProfile().getName())) {
                        System.out.println("    " + i + ". El " + formatter.format(profile.getPosts().get(i).getDate())
                                + " escribiste (" + profile.getPosts().get(i).getProfileLikes().size() + " likes): ");
                        System.out.println("\t" + profile.getPosts().get(i).getText());
                    } else {
                        // Si son de otro perfil, sacamos un sout con sus posts y el nombre respectivo
                        System.out.println("    " + i + ". El " + formatter.format(profile.getPosts().get(i).getDate())
                                + " " + profile.getPosts().get(i).getAuthor().getName() + " ha escrito ("
                                + profile.getPosts().get(i).getProfileLikes().size() + " likes): ");
                        System.out.println("\t" + profile.getPosts().get(i).getText());
                    }
                    // Si los posts tienen comentarios, los printeamos todos con su respectivo autor
                    if (!profile.getPosts().get(i).getComments().isEmpty()) {
                        for (int j = 0; j < profile.getPosts().get(i).getComments().size(); j++) {
                            System.out.println("\t" + "- " + profile.getPosts().get(i).getComments().get(j).getText() + " - "
                                    + profile.getPosts().get(i).getComments().get(j).getSourceProfile().getName() + " - "
                                    + formatter.format(profile.getPosts().get(i).getComments().get(j).getDate()));
                        }
                    }
                }
            }
            System.out.println("Amigos del usuario: ");
            if (!profile.getFriends().isEmpty()) {
                // Si hay amigos del usuario, los printeamos
                for (int i = 0; i < profile.getFriends().size(); i++) {
                    System.out.println(i + ". " + profile.getFriends().get(i).getName() + " - " + profile.getFriends().get(i).getStatus());
                }
            }
            System.out.println("");
        }
        dialogWindow.setVisible(true);
    }

    /**
     * Método que cambia el estado del usuario.
     *
     * @param ownProfile True si ve su propio perfil, false si ve el de otro.
     * @param profile Perfil del usuario.
     */
    public void changeStatus(boolean ownProfile, Profile profile) {
        if (ownProfile) {
            String status = JOptionPane.showInputDialog("Escribe el nuevo estado:");
            profileController.updateProfileStatus(status);
        } else {
            JOptionPane.showMessageDialog(null, "Sólo puedes cambiar el estado en tu propia biografía", "ERROR", JOptionPane.ERROR_MESSAGE,
                    new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
            showProfileMenu(profile);
        }
    }

    /**
     * Método que chamará ao método "showProfileInfo" para mostrar a información
     * do perfil, e a continuación mostrará as opcións de cambiar o estado do
     * perfil (que provocará a chamada ao método "changeStatus") e pechar a
     * sesión (que non fará nada, simplemente sairá do método)
     *
     * @param profile Perfil del usuario.
     */
    @Override
    public void showProfileMenu(Profile profile) {
        boolean ownProfile = profileController.getShownProfile().getName().equals(profileController.getSessionProfile().getName());
        showProfileInfo(ownProfile, profile);
        if (ownProfile) {
            System.out.println("Escoge una opción:");
            System.out.println("1.- Escribir una nueva publicación.");
            System.out.println("2.- Comentar una publicación.");
            System.out.println("3.- Dar LIKE a una publicación.");
            System.out.println("4.- Ver la biografía de una amigo.");
            System.out.println("5.- Enviar solicitud de amistad.");
            System.out.println("6.- Aceptar solicitud de amistad.");
            System.out.println("7.- Rechazar solicitud de amistad.");
            System.out.println("8.- Enviar MD a un amigo.");
            System.out.println("9.- Leer MD.");
            System.out.println("10.- Eliminar MD.");
            System.out.println("11.- Ver publicaciones anteriores.");
            System.out.println("12.- Cambiar estado.");
            System.out.println("13.- Cerrar sesión.");
            System.out.print("Escribe aquí: ");
        } else {
            System.out.println("Escoge una opción:");
            System.out.println("1.- Escribir una nueva publicación.");
            System.out.println("2.- Comentar una publicación.");
            System.out.println("3.- Dar LIKE a una publicación.");
            System.out.println("4.- Volver a mi biografía.");
            System.out.println("8.- Enviar MD a este amigo.");
            System.out.println("11.- Ver publicaciones anteriores.");
            System.out.println("13.- Cerrar sesión.");
            System.out.print("Escribe aquí: ");
        }
        int choice = 3;
        System.out.println("");
        switch (choice) {
            case 1:
                writeNewPost(profile);
                break;
//            case 2:
//                commentPost(dialogWindow.getNumberIndex(), profile);
//                break;
//            case 3:
//                addLike(dialogWindow.getNumberIndex(), profile);
//                break;
//            case 4:
//                showBiography(ownProfile, dialogWindow.getNumberIndex(), profile);
//                break;
//            case 5:
////                sendFriendshipRequest(ownProfile, scan, profile);
//                break;
//            case 6:
//                proccessFriendshipRequest(ownProfile, scan, profile, true);
//                break;
//            case 7:
//                proccessFriendshipRequest(ownProfile, scan, profile, false);
//                break;
//            case 8:
//                sendPrivateMessage(ownProfile, scan, profile);
//                break;
//            case 9:
//                readPrivateMessage(ownProfile, scan, profile);
//                break;
//            case 10:
//                deletePrivateMessage(ownProfile, scan, profile);
//                break;
//            case 11:
//                showOldPosts(scan, profile);
//                break;
            case 12:
                changeStatus(ownProfile, profile);
                break;
            case 13:
                break;
            default:
                System.out.println("Opción incorrecta.");
                showProfileMenu(profile);
        }
    }

    /**
     * Este método utilizarase cando se pida ao usuario que introduza un número
     * para seleccionar un elemento dunha lista (de publicacións, de mensaxes,
     * de amizades, etc.). Encárgase de pedir un número ao usuario mostrando o
     * texto que recibe en "text", lelo usando o "scanner" e volvelo a pedir
     * repetidamente ata que o valor introducido estea entre 0 e "maxNumber-1".
     * Devolve o número introducido polo usuario.
     *
     * @param text Texto con directrices a enseñar al usuario.
     * @param maxNumber Número máximo entre el que el usuario puede elegir.
     * @param scanner Introducción por teclado.
     * @return Número de algo elegido por el usuario.
     */
    private int selectElement(String text, int maxNumber, Scanner scanner) {
        // Pedimos un número entre 0 y maxNumber (incluido) hasta que lo ponga bien
        int selection;
        do {
            System.out.println(text);
            selection = readNumber(scanner);
            if (selection < 0 || selection >= maxNumber) {
                System.out.println("");
                System.out.println("Debes introducir un número entre " + 0 + " y " + maxNumber + ".");
                System.out.println("");
            }
            System.out.println("");
        } while (selection < 0 || selection >= maxNumber);
        return selection;
    }

    /**
     * Pide o texto para crear unha nova publicación e chama ao controlador para
     * creala.
     *
     * @param profile Perfil del post.
     */
    public void writeNewPost(Profile profile) {
        String post = JOptionPane.showInputDialog("Escribe el texto de la publicación: ");
        profileController.newPost(post, profile);
    }

    /**
     * Pide ao usuario que seleccione unha publicación e que introduza un texto,
     * e chama ao controlador para crear un comentario con ese texto.
     *
     * @param postNumber
     * @param profile Perfil del post.
     */
    public void commentPost(int postNumber, Profile profile) {
        String comment = JOptionPane.showInputDialog("Escribe el comentario: ");
        profileController.newComment(profile.getPosts().get(postNumber), comment);
    }

    /**
     * Pide ao usuario que seleccione unha publicación e chama ao controlador
     * para facer like sobre ela.
     *
     * @param postNumber
     * @param profile Perfil del post.
     */
    public void addLike(int postNumber, Profile profile) {
        profileController.newLike(profile.getPosts().get(postNumber));
    }

    /**
     * Se estamos vendo o propio perfil, pide ao usuario seleccionar unha
     * amizade para establecer ese perfil como perfil a mostrar (chamando ao
     * controlador), e senón volve a poñer o perfil da sesión como perfil a
     * mostrar.O parámetro "ownProfile" é o que indica se estamos vendo o propio
     * perfil da sesión ou o perfil dunha amizade.
     *
     * @param ownProfile True si perfil del usuario, false si no.
     * @param friendToShow
     * @param profile Perfil de la biografía.
     */
    public void showBiography(boolean ownProfile, int friendToShow, Profile profile) {
        if (ownProfile) {
            profileController.setShownProfile(profile.getFriends().get(friendToShow));
        } else {
            profileController.setShownProfile(profileController.getSessionProfile());
        }
    }

    /**
     * Pide o nome dun perfil e chama ao controlador para enviarlle unha
     * solicitude de amizade.
     *
     * @param ownProfile True si perfil del usuario, false si no.
     * @param scanner Introducción por teclado.
     * @param profile Perfil que realiza la acción.
     */
    private void sendFriendshipRequest(boolean ownProfile, Scanner scanner, Profile profile) {
        if (ownProfile) {
            System.out.println("¿A quién quieres mandarle una solicitud de amistad?");
            String friendRequestToSend = scanner.nextLine();
            profileController.newFriendshipRequest(friendRequestToSend);
        } else {
            showProfileMenu(profile);
        }
    }

    /**
     * Pide o número dunha solicitude de amizade e chama ao controlador para
     * aceptala ou rexeitala, en función do que se indique no parámetro
     * "accept".
     *
     * @param ownProfile True si perfil del usuario, false si no.
     * @param scanner Introducción por teclado.
     * @param profile Perfil que realiza la acción.
     * @param accept True si acepta la solicitud, false si no.
     */
    private void proccessFriendshipRequest(boolean ownProfile, Scanner scanner, Profile profile, boolean accept) {
        if (ownProfile) {
            if (accept) {
                // Si quiere aceptar la solicitud d amistad
                int friendRequestToProccess = selectElement("¿Qué solicitud de amistad quieres aceptar?",
                        profile.getFriendshipRequests().size(), scanner);
                System.out.println("");
                System.out.println("¡Solicitud aceptada!");
                System.out.println("");
                profileController.acceptFriendshipRequest(profile.getFriendshipRequests().get(friendRequestToProccess));
            } else {
                // Si quiere rechazar la solicitud de amistad
                int friendRequestToProccess = selectElement("¿Qué solicitud de amistad quieres rechazar?",
                        profile.getFriendshipRequests().size(), scanner);
                System.out.println("");
                System.out.println("¡Solicitud rechazada!");
                System.out.println("");
                profileController.rejectFriendshipRequest(profile.getFriendshipRequests().get(friendRequestToProccess));
            }
        } else {
            System.out.println("");
            System.out.println("ESTA OPCIÓN SOLO ESTÁ PERMITIDA EN TU BIOGRAFÍA");
            System.out.println("");
            showProfileMenu(profileController.getShownProfile());
        }
    }

    /**
     * Se estamos vendo o propio perfil, pide ao usuario selecciona un amigo e o
     * texto da mensaxe e chama ao controlador para enviar unha mensaxe. Se
     * estamos vendo o perfil dunha amizade, pide o texto para enviarlle unha
     * mensaxe a ese perfil.
     *
     * @param ownProfile True si perfil del usuario, false si no.
     * @param scanner Introducción por teclado.
     * @param profile Perfil que realiza la acción.
     */
    private void sendPrivateMessage(boolean ownProfile, Scanner scanner, Profile profile) {
        if (ownProfile) {
            int selectFriendToMessage = selectElement("Elige al amigo al que le quieres enviar el mensaje: (número)", profile.getFriends().size(), scanner);
            System.out.println("¿Qué mensaje le quieres enviar?");
            String message = scanner.nextLine();
            profileController.newMessage(profile.getFriends().get(selectFriendToMessage), message);
        } else {
            System.out.println("¿Qué mensaje le quieres enviar?");
            String message = scanner.nextLine();
            profileController.newMessage(profileController.getShownProfile(), message);
        }
    }

    /**
     * Pide ao usuario que seleccione unha mensaxe e a mostra completa, dando as
     * opcións de respondela, eliminala ou simplemente volver á biografia
     * marcando a mensaxe como lida, chamando ao controlador para executar as
     * distintas accións.
     *
     * @param ownProfile True si perfil del usuario, false si no.
     * @param scanner Introducción por teclado.
     * @param profile Perfil que realiza la acción.
     */
    private void readPrivateMessage(boolean ownProfile, Scanner scanner, Profile profile) {
        if (ownProfile) {
            int selectedMessage = selectElement("Elige el mensaje que quieres leer:", profile.getMessages().size(), scanner);
            System.out.println("");
            System.out.println("Mensaje privado");
            System.out.println("De: " + profile.getMessages().get(selectedMessage).getSourceProfile().getName());
            System.out.println("Data: " + formatter.format(profile.getMessages().get(selectedMessage).getDate()));
            System.out.println("Texto: ");
            System.out.println(profile.getMessages().get(selectedMessage).getText());
            System.out.println("");
            System.out.println("¿Qué quieres hacer con el mensaje?");
            System.out.println("1. Responder.");
            System.out.println("2. Borrar.");
            System.out.println("3. Volver a la biografía.");
            System.out.print("Escribe aquí: ");
            int option = readNumber(scanner);
            switch (option) {
                case 1:
                    System.out.println("");
                    System.out.println("¿Qué le quieres decir?");
                    String answer = scanner.nextLine();
                    // Si lo quiere responder, lo marcamos como leido
                    profile.getMessages().get(selectedMessage).setRead(true);
                    profileController.newMessage(profile.getMessages().get(selectedMessage).getSourceProfile(), answer);
                    break;
                case 2:
                    System.out.println("");
                    System.out.println("Mensaje eliminado.");
                    profileController.deleteMessage(profile.getMessages().get(selectedMessage));
                    break;
                case 3:
                    profileController.markMessageAsRead(profile.getMessages().get(selectedMessage));
                    break;
                default:
                    System.out.println("OPCIÓN INCORRECTA.");
            }
        } else {
            System.out.println("");
            System.out.println("ESTA OPCIÓN SOLO ESTÁ PERMITIDA EN TU BIOGRAFÍA");
            System.out.println("");
            showProfileMenu(profileController.getShownProfile());
        }
    }

    /**
     * Pide ao usuario que seleccione unha mensaxe e chama ao controlador para
     * borrala.
     *
     * @param ownProfile True si perfil del usuario, false si no.
     * @param scanner Introducción por teclado.
     * @param profile Perfil que realiza la acción.
     */
    private void deletePrivateMessage(boolean ownProfile, Scanner scanner, Profile profile) {
        if (ownProfile) {
            int selectedMessage = selectElement("Selecciona el número del mensaje que quieres borrar:",
                    profile.getMessages().size(), scanner);
            profileController.deleteMessage(profile.getMessages().get(selectedMessage));
        } else {
            System.out.println("");
            System.out.println("ESTA OPCIÓN SOLO ESTÁ PERMITIDA EN TU BIOGRAFÍA");
            System.out.println("");
            showProfileMenu(profileController.getShownProfile());
        }
    }

    /**
     * Pide o número de publicacións que se queren visualizar e chamar ao
     * controlador para recargar o perfil.
     *
     * @param profile Perfil que realiza la acción.
     */
    public void showOldPosts(Profile profile) {
        do {
            postsShowed = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántas publicaciones quieres ver?"));
            if (postsShowed <= 10) {
                System.out.println("");
                System.out.println("Ya estás visualizando las 10 últimas publicaciones");
                System.out.println("");
            }
        } while (postsShowed <= 10);
        profileController.reloadProfile();
    }

    /**
     * Os métodos que se inclúen a partir de aquí, simplemente mostran mensaxes
     * por pantalla e chámanse dende o controlador para informar ao usuario de
     * circunstancias que poden provocar que unha acción non se poida realizar.
     * Neste caso, que un perfil non se atopou (Úsase cando se quere enviar unha
     * solicitude de amizade).
     */
    @Override
    public void showProfileNotFoundMessage() {
        System.out.println("");
        System.out.println("No se encuentra ese perfil.");
        System.out.println("");
    }

    /**
     * Informa de que non se pode facer like sobre unha publicación propia.
     */
    @Override
    public void showCannotLikeOwnPostMessage() {
        System.out.println("");
        System.out.println("No se puede dar like sobre una publicación propia.");
        System.out.println("");
    }

    /**
     * Informa de que non se pode facer like sobre unha publicación sobre a que
     * xa se fixo like.
     */
    @Override
    public void showAlreadyLikedPostMessage() {
        System.out.println("");
        System.out.println("No se puede dar like a una publicación a la que ya se le ha dado like.");
        System.out.println("");
    }

    /**
     * Informa de que xa tes amizade con ese perfil.
     *
     * @param profileName Nombre del perfil.
     */
    @Override
    public void showIsAlreadyFriendMessage(String profileName) {
        System.out.println("");
        System.out.println("Ya eres amigo de " + profileName + ".");
        System.out.println("");
    }

    /**
     * Informa de que ese perfil xa ten unha solicitude de amizade contigo.
     *
     * @param profileName Nombre del perfil.
     */
    @Override
    public void showExistsFrienshipRequestMessage(String profileName) {
        System.out.println("");
        System.out.println("Ya le has enviado solicitud de amistad a " + profileName + ".");
        System.out.println("");
    }

    /**
     * Informa de que xa tes unha solicitude de amizade con ese perfil.
     *
     * @param profileName Nombre del perfil.
     */
    @Override
    public void showDuplicateFrienshipRequestMessage(String profileName) {
        System.out.println("");
        System.out.println("Ya tienes una solicitud de amistad pendiente con " + profileName + ".");
        System.out.println("");
    }

    /**
     * Este método lerá un dato por teclado usando o método "nextInt()" do
     * scanner recibido como parámetro, e se se produce a excepción
     * "NoSuchElementException" mostrará unha mensaxe indicando que se debe
     * introducir un número e volverá a chamarse a si mesmo para volver a pedir
     * o dato. Só cando non se produza a excepción, devolverá como resultado o
     * número introducido. Hai que ter en conta que despois da chamada a
     * "nextInt()" hai que chamar tamén a "nextLine()" para ler o salto de liña
     * que o usuario ten que introducir para confirmar a entrada de teclado.
     * Chamaremos a este método en todos os sitios onde chamásemos a "nextInt()"
     * e así xa teremos controlado en todos os puntos do programa a posibilidade
     * de que o usuario introduza caracteres non numéricos.
     *
     * @param scanner Scanner para scannear el número.
     * @return Número escaneado.
     */
    private int readNumber(Scanner scanner) {
        int number = 0;
        boolean goodNumber;
        do {
            try {
                number = scanner.nextInt();
                goodNumber = true;
            } catch (NoSuchElementException e) {
                System.out.println("");
                System.out.print("Debes introducir un número --> ");
                goodNumber = false;
            } finally {
                scanner.nextLine();
            }
        } while (!goodNumber);
        return number;
    }

    /**
     * Mostrará a mensaxe "Erro na conexión co almacén de datos!".
     */
    @Override
    public void showConnectionErrorMessage() {
        System.out.println("Erro na conexión co almacén de datos!");
    }

    /**
     * Mostrará a mensaxe "Erro na lectura de datos!".
     */
    @Override
    public void showReadErrorMessage() {
        System.out.println("Erro na lectura de datos!");
    }

    /**
     * Mostrará a mensaxe "Erro na escritura dos datos!".
     */
    @Override
    public void showWriteErrorMessage() {
        System.out.println("Erro na escritura dos datos!");
    }
}
