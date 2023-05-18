/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package tacebook.view;

import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import tacebook.controller.ProfileController;
import tacebook.model.Profile;

/**
 * Clase que implementa la ventana del perfil de la aplicación.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos.
 */
public class GUIProfileView extends javax.swing.JDialog implements ProfileView {

    private int postsShowed;
    private ProfileController profileController;
    private SimpleDateFormat formatter;
    private Profile profile;
    private boolean ownProfile;

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
     * Creates new form JDialogProfileView
     *
     * @param parent Componente padre del dialogo.
     * @param modal True si modal, false si no.
     * @param profileController Controlador de la sesión del perfil.
     */
    public GUIProfileView(java.awt.Frame parent, boolean modal, ProfileController profileController) {
        this.profileController = profileController;
        this.postsShowed = 10;
        this.formatter = new SimpleDateFormat("dd/MM/yyy 'a las' HH:mm:ss");
        initComponents();
        this.setLocationRelativeTo(null);
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
        // Cosas vista
        jTabPanePanel.setEnabledAt(2, true);
        jBtnShowBiography.setText("Ver biografía");
        jBtnAcceptFriend.setEnabled(true);
        jBtnRejectFriend.setEnabled(true);
        jBtnNewFriendship.setEnabled(true);
        jListFriendshipRequests.setEnabled(true);
        jTableFriendsList.setEnabled(true);
        // Panel superior
        jLblSessionProfileName.setText("@" + profile.getName());
        jLblSessionProfileName.setForeground(jLblSessionProfileUser.getForeground());
        jLblSessionProfileStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/status-profile.png")));
        jLblSessionProfileStatus.setText("Estado: " + profile.getStatus());
        jLblNumPosts.setText(postsShowed + " últimas publicaciones:");
        // Model de los posts
        DefaultTableModel modeloPost = new DefaultTableModelImpl();
        modeloPost.addColumn("Fecha");
        modeloPost.addColumn("Autor");
        modeloPost.addColumn("Texto");
        modeloPost.addColumn("Likes");
        jTablePosts.setModel(modeloPost);
        if (!profile.getPosts().isEmpty()) {
            // Si los posts no están vacíos, printeamos los post que haya
            for (int i = 0; (i < profile.getPosts().size()) && (i < postsShowed); i++) {
                // Si son del sessionProfile sacamos un sout con sus posts
                if (profile.getPosts().get(i).getAuthor().getName().equals(profileController.getSessionProfile().getName())) {
                    modeloPost.addRow(new Object[]{formatter.format(profile.getPosts().get(i).getDate()), "escribiste",
                        profile.getPosts().get(i).getText(), profile.getPosts().get(i).getProfileLikes().size()});
                } else {
                    // Si son de otro perfil, sacamos un sout con sus posts y el nombre respectivo
                    modeloPost.addRow(new Object[]{formatter.format(profile.getPosts().get(i).getDate()),
                        profile.getPosts().get(i).getAuthor().getName(), profile.getPosts().get(i).getText(), profile.getPosts().get(i).getProfileLikes().size()});
                }
            }
        }
        DefaultTableModel modeloFriends = new DefaultTableModelImpl();
        modeloFriends.addColumn("Nombre");
        modeloFriends.addColumn("Estado");
        jTableFriendsList.setModel(modeloFriends);
        if (!profile.getFriends().isEmpty()) {
            // Si hay amigos del usuario, los printeamos
            for (int i = 0; i < profile.getFriends().size(); i++) {
                modeloFriends.addRow(new Object[]{profile.getFriends().get(i).getName(),
                    profile.getFriends().get(i).getStatus()});
            }
        }
        if (ownProfile) {
            DefaultTableModel modeloPrivateMessages = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 0) {
                        return Boolean.class;
                    } else {
                        return super.getColumnClass(columnIndex);
                    }
                }
            };
            modeloPrivateMessages.addColumn("Leído");
            modeloPrivateMessages.addColumn("Fecha");
            modeloPrivateMessages.addColumn("De");
            modeloPrivateMessages.addColumn("Texto");
            jTablePrivateMessages.setModel(modeloPrivateMessages);
            if (!profile.getMessages().isEmpty()) {
                // Si hay mensajes privados, los printeamos
                boolean isMessageNotRead = false;
                for (int i = 0; i < profile.getMessages().size(); i++) {
                    modeloPrivateMessages.addRow(new Object[]{profile.getMessages().get(i).isRead(),
                        new SimpleDateFormat("dd/MM/yy").format(profile.getMessages().get(i).getDate()),
                        profile.getMessages().get(i).getSourceProfile().getName(), profile.getMessages().get(i).getText()});
                    if (!profile.getMessages().get(i).isRead()) {
                        isMessageNotRead = true;
                    }
                }
                if (isMessageNotRead) {
                    jTabPanePanel.setTitleAt(2, "<html><b>Mensajes privados</b></html>");
                } else {
                    jTabPanePanel.setTitleAt(2, "Mensajes privados");
                }
            }
            DefaultListModel<String> modelo = new DefaultListModel<>();
            jListFriendshipRequests.setModel(modelo);
            if (!profile.getFriendshipRequests().isEmpty()) {
                // Si hay solicitudes de amistad, las printeamos
                for (int i = 0; i < profile.getFriendshipRequests().size(); i++) {
                    modelo.addElement("-- @" + profile.getFriendshipRequests().get(i).getName() + " -- quiere ser tu amig@");
                }
            }
        } else {
            jLblSessionProfileName.setText("<html><b>@" + profile.getName() + "</b></html>");
            Color ourColor = new Color(112, 144, 164);
            jLblSessionProfileName.setForeground(ourColor);
            jTabPanePanel.setEnabledAt(2, false);
            jBtnShowBiography.setText("Volver a tu perfil");
            jBtnAcceptFriend.setEnabled(false);
            jBtnRejectFriend.setEnabled(false);
            jBtnNewFriendship.setEnabled(false);
            jListFriendshipRequests.setEnabled(false);
            jTableFriendsList.setEnabled(false);
            jTabPanePanel.setTitleAt(2, "Mensajes privados");
            DefaultListModel<String> modelo = new DefaultListModel<>();
            jListFriendshipRequests.setModel(modelo);
            if (!profile.getFriendshipRequests().isEmpty()) {
                // Si hay solicitudes de amistad, las printeamos
                for (int i = 0; i < profile.getFriendshipRequests().size(); i++) {
                    modelo.addElement("-- @" + profile.getFriendshipRequests().get(i).getName() + " -- quiere ser tu amig@");
                }
            }
        }
        this.setVisible(true);
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
        this.profile = profile;
        ownProfile = profileController.getShownProfile().getName().equals(profileController.getSessionProfile().getName());
        showProfileInfo(ownProfile, profile);
    }

    /**
     * Pide o texto para crear unha nova publicación e chama ao controlador para
     * creala.
     *
     * @param profile Perfil del post.
     */
    private void writeNewPost(Profile profile) {
        String post = JOptionPane.showInputDialog("Escribe el texto de la publicación: ");
        if (post != null) {
            if (!post.isBlank()) {
                profileController.newPost(post, profile);
            }
        }
    }

    /**
     * Pide ao usuario que seleccione unha publicación e que introduza un texto,
     * e chama ao controlador para crear un comentario con ese texto.
     *
     * @param postNumber Número del post al que añadirle un comentario.
     * @param profile Perfil del post.
     */
    private void commentPost(int postNumber, Profile profile) {
        if (jTablePosts.getSelectedRow() != -1) {
            String comment = JOptionPane.showInputDialog("Escribe el comentario: ");
            if (comment != null) {
                if (!comment.isBlank()) {
                    profileController.newComment(profile.getPosts().get(postNumber), comment);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes elegir una publicación", "ERROR", JOptionPane.ERROR_MESSAGE,
                    new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
        }
    }

    /**
     * Pide ao usuario que seleccione unha publicación e chama ao controlador
     * para facer like sobre ela.
     *
     * @param postNumber Número del post al que añadirle like.
     * @param profile Perfil del post.
     */
    private void addLike(int postNumber, Profile profile) {
        if (jTablePosts.getSelectedRow() != -1) {
            profileController.newLike(profile.getPosts().get(postNumber));
        } else {
            JOptionPane.showMessageDialog(null, "Debes elegir una publicación", "ERROR", JOptionPane.ERROR_MESSAGE,
                    new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
        }
    }

    /**
     * Se estamos vendo o propio perfil, pide ao usuario seleccionar unha
     * amizade para establecer ese perfil como perfil a mostrar (chamando ao
     * controlador), e senón volve a poñer o perfil da sesión como perfil a
     * mostrar.O parámetro "ownProfile" é o que indica se estamos vendo o propio
     * perfil da sesión ou o perfil dunha amizade.
     *
     * @param ownProfile True si perfil del usuario, false si no.
     * @param friendToShow Amigo elegido para ver el perfil.
     * @param profile Perfil de la biografía.
     */
    private void showBiography(boolean ownProfile, int friendToShow, Profile profile) {
        if (ownProfile) {
            if (jTableFriendsList.getSelectedRow() != -1) {
                profileController.setShownProfile(profile.getFriends().get(friendToShow));
            } else {
                JOptionPane.showMessageDialog(null, "Debes elegir a un amig@", "ERROR", JOptionPane.ERROR_MESSAGE,
                        new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
            }
        } else {
            //JOptionPane.showMessageDialog(null, "Elige un amigo.");
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
    private void sendFriendshipRequest(boolean ownProfile, String friendRequestToSend, Profile profile) {
        if (ownProfile) {
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
    private void proccessFriendshipRequest(int friendRequestToProccess, Profile profile, boolean accept) {
        if (jListFriendshipRequests.getSelectedIndex() != -1) {
            if (accept) {
                // Si quiere aceptar la solicitud d amistad
                profileController.acceptFriendshipRequest(profile.getFriendshipRequests().get(friendRequestToProccess));
            } else {
                // Si quiere rechazar la solicitud de amistad
                profileController.rejectFriendshipRequest(profile.getFriendshipRequests().get(friendRequestToProccess));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes elegir una solicitud de amistad", "ERROR", JOptionPane.ERROR_MESSAGE,
                    new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
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
    private void sendPrivateMessage(boolean ownProfile, int selectFriendToMessage, Profile profile) {
        String message;
        if (ownProfile) {
            if (jTableFriendsList.getSelectedRow() != -1) {
                message = JOptionPane.showInputDialog("Escribe el mensaje privado:");
                if (message != null) {
                    if (!message.isBlank()) {
                        profileController.newMessage(profile.getFriends().get(selectFriendToMessage), message);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debes elegir a un amigo", "ERROR", JOptionPane.ERROR_MESSAGE,
                        new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
            }
        } else {
            message = JOptionPane.showInputDialog("Escribe el mensaje privado:");
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
    private void readPrivateMessage(int selectedMessage, Profile profile) {
        if (jTablePrivateMessages.getSelectedRow() != -1) {
            String[] message = {
                "Mensaje privado de: ", profile.getMessages().get(selectedMessage).getSourceProfile().getName(),
                "Fecha: ", formatter.format(profile.getMessages().get(selectedMessage).getDate()),
                "Texto: ", profile.getMessages().get(selectedMessage).getText()
            };
            int option = JOptionPane.showInternalOptionDialog(null, message, "Mensaje privado",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    new javax.swing.ImageIcon(getClass().getResource("/Imagenes/reading-message.png")),
                    new String[]{"Salir", "Borrar", "Responder"}, 0);
            //showConfirmDialog(null, message, "Mensaje privado", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            switch (option) {
                case 2:
                    String answer = JOptionPane.showInputDialog("Escribir mensaje");
                    // Si lo quiere responder, lo marcamos como leido
                    profileController.markMessageAsRead(profile.getMessages().get(selectedMessage));
                    if (answer != null) {
                        if (!answer.isBlank()) {
                            profileController.newMessage(profile.getMessages().get(selectedMessage).getSourceProfile(), answer);
                        }
                    }
                    break;
                case 1:
                    profileController.deleteMessage(profile.getMessages().get(selectedMessage));
                    break;
                case 0:
                case JOptionPane.CLOSED_OPTION:
                    profileController.markMessageAsRead(profile.getMessages().get(selectedMessage));
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debes elegir un mensaje", "ERROR", JOptionPane.ERROR_MESSAGE,
                    new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
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
    private void deletePrivateMessage(int selectedMessage, Profile profile) {
        if (jTablePrivateMessages.getSelectedRow() != -1) {
            profileController.deleteMessage(profile.getMessages().get(selectedMessage));
        } else {
            JOptionPane.showMessageDialog(null, "Debes elegir un mensaje", "ERROR", JOptionPane.ERROR_MESSAGE,
                    new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
            showProfileMenu(profileController.getShownProfile());
        }
    }

    /**
     * Pide o número de publicacións que se queren visualizar e chamar ao
     * controlador para recargar o perfil.
     *
     * @param profile Perfil que realiza la acción.
     */
    private void showOldPosts(Profile profile) {
        int numberOfPostsToShow = 10;
        do {
            try {
                numberOfPostsToShow = Integer.parseInt(JOptionPane.showInputDialog("¿Cuántas publicaciones quieres ver?"));
            } catch (NumberFormatException e) {
            }
            if (numberOfPostsToShow < 10) {
                JOptionPane.showMessageDialog(null, "Por defecto se ven las 10 últimas publicaciones", "ERROR", JOptionPane.ERROR_MESSAGE,
                        new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
                numberOfPostsToShow = 10;
            }
        } while (numberOfPostsToShow < 10);
        postsShowed = numberOfPostsToShow;
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
        JOptionPane.showMessageDialog(null, "No se encuentra ese perfil", "ERROR", JOptionPane.ERROR_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
    }

    /**
     * Informa de que non se pode facer like sobre unha publicación propia.
     */
    @Override
    public void showCannotLikeOwnPostMessage() {
        JOptionPane.showMessageDialog(null, "No se puede dar like sobre una publicación propia", "ERROR", JOptionPane.ERROR_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
    }

    /**
     * Informa de que non se pode facer like sobre unha publicación sobre a que
     * xa se fixo like.
     */
    @Override
    public void showAlreadyLikedPostMessage() {
        JOptionPane.showMessageDialog(null, "No se puede dar like a una publicación a la que ya le has dado like",
                "ERROR", JOptionPane.ERROR_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
    }

    /**
     * Informa de que xa tes amizade con ese perfil.
     *
     * @param profileName Nombre del perfil.
     */
    @Override
    public void showIsAlreadyFriendMessage(String profileName) {
        JOptionPane.showMessageDialog(null, "Ya eres amigo de " + profileName + ".", "ERROR", JOptionPane.ERROR_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
    }

    /**
     * Informa de que ese perfil xa ten unha solicitude de amizade contigo.
     *
     * @param profileName Nombre del perfil.
     */
    @Override
    public void showExistsFrienshipRequestMessage(String profileName) {
        JOptionPane.showMessageDialog(null, "Ya le has enviado solicitud de amistad a " + profileName + ".", "ERROR", JOptionPane.ERROR_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
    }

    /**
     * Informa de que xa tes unha solicitude de amizade con ese perfil.
     *
     * @param profileName Nombre del perfil.
     */
    @Override
    public void showDuplicateFrienshipRequestMessage(String profileName) {
        JOptionPane.showMessageDialog(null, "Ya tienes una solicitud de amistad pendiente con " + profileName + ".", "ERROR", JOptionPane.ERROR_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
    }

    /**
     * Mostrará a mensaxe "Erro na conexión co almacén de datos!".
     */
    @Override
    public void showConnectionErrorMessage() {
        JOptionPane.showMessageDialog(null, "Error en la conexión con el almacén de datos!", "ERROR", JOptionPane.ERROR_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
    }

    /**
     * Mostrará a mensaxe "Erro na lectura de datos!".
     */
    @Override
    public void showReadErrorMessage() {
        JOptionPane.showMessageDialog(null, "Error en la lectura de datos!", "ERROR", JOptionPane.ERROR_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
    }

    /**
     * Mostrará a mensaxe "Erro na escritura dos datos!".
     */
    @Override
    public void showWriteErrorMessage() {
        JOptionPane.showMessageDialog(null, "Error en la escritura dos datos!", "ERROR", JOptionPane.ERROR_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
    }

    /**
     * Método que saca los comentarios de la publiación seleccionada.
     */
    private void showComments() {
        DefaultTableModel modeloComments = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloComments.addColumn("Texto");
        modeloComments.addColumn("De");
        modeloComments.addColumn("Fecha");
        jTableComments.setModel(modeloComments);
        if (!profile.getPosts().get(jTablePosts.getSelectedRow()).getComments().isEmpty()) {
            // Si los posts tienen comentarios, los printeamos todos con su respectivo autor
            for (int j = 0; j < profile.getPosts().get(jTablePosts.getSelectedRow()).getComments().size(); j++) {
                modeloComments.addRow(new Object[]{profile.getPosts().get(jTablePosts.getSelectedRow()).getComments().get(j).getText(),
                    profile.getPosts().get(jTablePosts.getSelectedRow()).getComments().get(j).getSourceProfile().getName(),
                    formatter.format(profile.getPosts().get(jTablePosts.getSelectedRow()).getComments().get(j).getDate())});
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLblSessionProfileUser = new javax.swing.JLabel();
        jLblSessionProfileStatus = new javax.swing.JLabel();
        jLblLogo = new javax.swing.JLabel();
        jTabPanePanel = new javax.swing.JTabbedPane();
        jPanelBiography = new javax.swing.JPanel();
        jLblNumPosts = new javax.swing.JLabel();
        jPanelPosts = new javax.swing.JPanel();
        jScrollPanePosts = new javax.swing.JScrollPane();
        jTablePosts = new javax.swing.JTable();
        jBtnNewPost = new javax.swing.JButton();
        jBtnNewComment = new javax.swing.JButton();
        jBtnNewLike = new javax.swing.JButton();
        jBtnSeeOldPosts = new javax.swing.JButton();
        jPanelComments = new javax.swing.JPanel();
        jLblComentarios = new javax.swing.JLabel();
        jPanelPostComments = new javax.swing.JPanel();
        jScrollPaneComments = new javax.swing.JScrollPane();
        jTableComments = new javax.swing.JTable();
        jPanelFriends = new javax.swing.JPanel();
        jBtnAcceptFriend = new javax.swing.JButton();
        jBtnRejectFriend = new javax.swing.JButton();
        jBtnNewFriendship = new javax.swing.JButton();
        jLblFirendList = new javax.swing.JLabel();
        jPanelFriendsList = new javax.swing.JPanel();
        jScrollPanePosts1 = new javax.swing.JScrollPane();
        jTableFriendsList = new javax.swing.JTable();
        jBtnShowBiography = new javax.swing.JButton();
        jBtnSendPrivateMessage = new javax.swing.JButton();
        jPanelFirenshipRequestList = new javax.swing.JPanel();
        jLblFriendshipRequests = new javax.swing.JLabel();
        jScrollPaneFriendshipRequestList = new javax.swing.JScrollPane();
        jListFriendshipRequests = new javax.swing.JList<>();
        jPanelPrivateMessages = new javax.swing.JPanel();
        jBtnReadMessage = new javax.swing.JButton();
        jBtnRemoveMessage = new javax.swing.JButton();
        jLabelPrivateMessages = new javax.swing.JLabel();
        jScrollPanePrivateMessages = new javax.swing.JScrollPane();
        jTablePrivateMessages = new javax.swing.JTable();
        jBtnChangeStatus = new javax.swing.JButton();
        jBtnCloseSession = new javax.swing.JButton();
        jBtnUpdate = new javax.swing.JButton();
        jLblSessionProfileName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MAINHBOOK");
        setMinimumSize(new java.awt.Dimension(1000, 730));
        setModal(true);
        setResizable(false);

        jLblSessionProfileUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/user-profile.png"))); // NOI18N
        jLblSessionProfileUser.setText("Usuario:");

        jLblSessionProfileStatus.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLblSessionProfileStatus.setText("estado");

        jLblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo-mainhbook.png"))); // NOI18N

        jTabPanePanel.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N

        jTablePosts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Data", "Autor", "Texto", "Likes"
            }
        ));
        jTablePosts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePostsMouseClicked(evt);
            }
        });
        jTablePosts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTablePostsKeyReleased(evt);
            }
        });
        jScrollPanePosts.setViewportView(jTablePosts);
        if (jTablePosts.getColumnModel().getColumnCount() > 0) {
            jTablePosts.getColumnModel().getColumn(2).setHeaderValue("Texto");
            jTablePosts.getColumnModel().getColumn(3).setHeaderValue("Likes");
        }

        javax.swing.GroupLayout jPanelPostsLayout = new javax.swing.GroupLayout(jPanelPosts);
        jPanelPosts.setLayout(jPanelPostsLayout);
        jPanelPostsLayout.setHorizontalGroup(
            jPanelPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPostsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPanePosts)
                .addContainerGap())
        );
        jPanelPostsLayout.setVerticalGroup(
            jPanelPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPanePosts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
        );

        jBtnNewPost.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/biografia.png"))); // NOI18N
        jBtnNewPost.setText("Nueva publicación");
        jBtnNewPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNewPostActionPerformed(evt);
            }
        });

        jBtnNewComment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/comment.png"))); // NOI18N
        jBtnNewComment.setText("Comentar");
        jBtnNewComment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNewCommentActionPerformed(evt);
            }
        });

        jBtnNewLike.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/like.png"))); // NOI18N
        jBtnNewLike.setText("Like");
        jBtnNewLike.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNewLikeActionPerformed(evt);
            }
        });

        jBtnSeeOldPosts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/show-old-posts.png"))); // NOI18N
        jBtnSeeOldPosts.setText("Ver publicaciones anteriores");
        jBtnSeeOldPosts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSeeOldPostsActionPerformed(evt);
            }
        });

        jLblComentarios.setText("Comentarios:");

        jTableComments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Texto", "De", "Data"
            }
        ));
        jScrollPaneComments.setViewportView(jTableComments);

        javax.swing.GroupLayout jPanelPostCommentsLayout = new javax.swing.GroupLayout(jPanelPostComments);
        jPanelPostComments.setLayout(jPanelPostCommentsLayout);
        jPanelPostCommentsLayout.setHorizontalGroup(
            jPanelPostCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneComments, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanelPostCommentsLayout.setVerticalGroup(
            jPanelPostCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPostCommentsLayout.createSequentialGroup()
                .addComponent(jScrollPaneComments, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 314, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelCommentsLayout = new javax.swing.GroupLayout(jPanelComments);
        jPanelComments.setLayout(jPanelCommentsLayout);
        jPanelCommentsLayout.setHorizontalGroup(
            jPanelCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCommentsLayout.createSequentialGroup()
                .addComponent(jLblComentarios, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanelPostComments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelCommentsLayout.setVerticalGroup(
            jPanelCommentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCommentsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLblComentarios, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelPostComments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanelBiographyLayout = new javax.swing.GroupLayout(jPanelBiography);
        jPanelBiography.setLayout(jPanelBiographyLayout);
        jPanelBiographyLayout.setHorizontalGroup(
            jPanelBiographyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPosts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelBiographyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelComments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanelBiographyLayout.createSequentialGroup()
                .addComponent(jLblNumPosts, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBiographyLayout.createSequentialGroup()
                .addContainerGap(187, Short.MAX_VALUE)
                .addComponent(jBtnNewPost)
                .addGap(18, 18, 18)
                .addComponent(jBtnNewComment)
                .addGap(18, 18, 18)
                .addComponent(jBtnNewLike, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnSeeOldPosts)
                .addGap(136, 136, 136))
        );
        jPanelBiographyLayout.setVerticalGroup(
            jPanelBiographyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBiographyLayout.createSequentialGroup()
                .addComponent(jLblNumPosts, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelPosts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanelBiographyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBtnNewPost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnNewComment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnSeeOldPosts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnNewLike, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelComments, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jTabPanePanel.addTab("Biografía", new javax.swing.ImageIcon(getClass().getResource("/Imagenes/biografia.png")), jPanelBiography); // NOI18N

        jBtnAcceptFriend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/accept-friend.png"))); // NOI18N
        jBtnAcceptFriend.setText("Aceptar solicitud");
        jBtnAcceptFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAcceptFriendActionPerformed(evt);
            }
        });

        jBtnRejectFriend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/reject-friend.png"))); // NOI18N
        jBtnRejectFriend.setText("Rechazar solictiud");
        jBtnRejectFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnRejectFriendActionPerformed(evt);
            }
        });

        jBtnNewFriendship.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/new-frienship.png"))); // NOI18N
        jBtnNewFriendship.setText("Nueva solicitud");
        jBtnNewFriendship.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNewFriendshipActionPerformed(evt);
            }
        });

        jLblFirendList.setText("Lista de amig@s:");

        jTableFriendsList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nombre", "Estado"
            }
        ));
        jTableFriendsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableFriendsListMouseClicked(evt);
            }
        });
        jScrollPanePosts1.setViewportView(jTableFriendsList);

        javax.swing.GroupLayout jPanelFriendsListLayout = new javax.swing.GroupLayout(jPanelFriendsList);
        jPanelFriendsList.setLayout(jPanelFriendsListLayout);
        jPanelFriendsListLayout.setHorizontalGroup(
            jPanelFriendsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPanePosts1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanelFriendsListLayout.setVerticalGroup(
            jPanelFriendsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFriendsListLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPanePosts1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jBtnShowBiography.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/biografia.png"))); // NOI18N
        jBtnShowBiography.setText("Ver biografía");
        jBtnShowBiography.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnShowBiographyActionPerformed(evt);
            }
        });

        jBtnSendPrivateMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/send-message.png"))); // NOI18N
        jBtnSendPrivateMessage.setText("Enviar mensaje privado");
        jBtnSendPrivateMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSendPrivateMessageActionPerformed(evt);
            }
        });

        jLblFriendshipRequests.setText("Solicitudes de amistad:");

        jScrollPaneFriendshipRequestList.setViewportView(jListFriendshipRequests);

        javax.swing.GroupLayout jPanelFirenshipRequestListLayout = new javax.swing.GroupLayout(jPanelFirenshipRequestList);
        jPanelFirenshipRequestList.setLayout(jPanelFirenshipRequestListLayout);
        jPanelFirenshipRequestListLayout.setHorizontalGroup(
            jPanelFirenshipRequestListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFirenshipRequestListLayout.createSequentialGroup()
                .addComponent(jLblFriendshipRequests)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPaneFriendshipRequestList)
        );
        jPanelFirenshipRequestListLayout.setVerticalGroup(
            jPanelFirenshipRequestListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFirenshipRequestListLayout.createSequentialGroup()
                .addComponent(jLblFriendshipRequests)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneFriendshipRequestList, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 5, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelFriendsLayout = new javax.swing.GroupLayout(jPanelFriends);
        jPanelFriends.setLayout(jPanelFriendsLayout);
        jPanelFriendsLayout.setHorizontalGroup(
            jPanelFriendsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFriendsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFriendsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelFriendsLayout.createSequentialGroup()
                        .addComponent(jLblFirendList)
                        .addGap(0, 849, Short.MAX_VALUE))
                    .addComponent(jPanelFriendsList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelFirenshipRequestList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelFriendsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jBtnShowBiography)
                .addGap(18, 18, 18)
                .addComponent(jBtnSendPrivateMessage)
                .addGap(300, 300, 300))
            .addGroup(jPanelFriendsLayout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(jBtnAcceptFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnRejectFriend)
                .addGap(18, 18, 18)
                .addComponent(jBtnNewFriendship, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelFriendsLayout.setVerticalGroup(
            jPanelFriendsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelFriendsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblFirendList, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelFriendsList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelFriendsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnShowBiography, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnSendPrivateMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelFirenshipRequestList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelFriendsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(jBtnRejectFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnNewFriendship, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAcceptFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabPanePanel.addTab("Amig@s", new javax.swing.ImageIcon(getClass().getResource("/Imagenes/friends.png")), jPanelFriends); // NOI18N

        jBtnReadMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/read-message.png"))); // NOI18N
        jBtnReadMessage.setText("Leer mensaje");
        jBtnReadMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnReadMessageActionPerformed(evt);
            }
        });

        jBtnRemoveMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/reject-friend.png"))); // NOI18N
        jBtnRemoveMessage.setText("Eliminar mensaje");
        jBtnRemoveMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnRemoveMessageActionPerformed(evt);
            }
        });

        jLabelPrivateMessages.setText("Mensajes privados:");

        jTablePrivateMessages.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Leido", "Fecha", "De", "Texto"
            }
        ));
        jTablePrivateMessages.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePrivateMessagesMouseClicked(evt);
            }
        });
        jScrollPanePrivateMessages.setViewportView(jTablePrivateMessages);

        javax.swing.GroupLayout jPanelPrivateMessagesLayout = new javax.swing.GroupLayout(jPanelPrivateMessages);
        jPanelPrivateMessages.setLayout(jPanelPrivateMessagesLayout);
        jPanelPrivateMessagesLayout.setHorizontalGroup(
            jPanelPrivateMessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPrivateMessagesLayout.createSequentialGroup()
                .addGroup(jPanelPrivateMessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPrivateMessages)
                    .addGroup(jPanelPrivateMessagesLayout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(jBtnReadMessage)
                        .addGap(20, 20, 20)
                        .addComponent(jBtnRemoveMessage)))
                .addContainerGap(360, Short.MAX_VALUE))
            .addComponent(jScrollPanePrivateMessages)
        );
        jPanelPrivateMessagesLayout.setVerticalGroup(
            jPanelPrivateMessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelPrivateMessagesLayout.createSequentialGroup()
                .addComponent(jLabelPrivateMessages)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPanePrivateMessages, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelPrivateMessagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jBtnRemoveMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnReadMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        jTabPanePanel.addTab("Mensajes privados", new javax.swing.ImageIcon(getClass().getResource("/Imagenes/private-message.png")), jPanelPrivateMessages); // NOI18N

        jBtnChangeStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/status-profile.png"))); // NOI18N
        jBtnChangeStatus.setText("Cambiar estado");
        jBtnChangeStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnChangeStatusActionPerformed(evt);
            }
        });

        jBtnCloseSession.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/close-session.png"))); // NOI18N
        jBtnCloseSession.setText("Cerrar sesión");
        jBtnCloseSession.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCloseSessionActionPerformed(evt);
            }
        });

        jBtnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/upload.png"))); // NOI18N
        jBtnUpdate.setBorderPainted(false);
        jBtnUpdate.setFocusable(false);
        jBtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(340, 340, 340)
                .addComponent(jBtnChangeStatus)
                .addGap(26, 26, 26)
                .addComponent(jBtnCloseSession)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLblSessionProfileUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLblSessionProfileName, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLblSessionProfileStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(33, 33, 33))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabPanePanel)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblLogo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLblSessionProfileStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLblSessionProfileUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblSessionProfileName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabPanePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBtnCloseSession, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnChangeStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnNewPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNewPostActionPerformed
        writeNewPost(profileController.getShownProfile());
    }//GEN-LAST:event_jBtnNewPostActionPerformed

    private void jBtnChangeStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnChangeStatusActionPerformed
        changeStatus(ownProfile, profile);
    }//GEN-LAST:event_jBtnChangeStatusActionPerformed

    private void jBtnCloseSessionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCloseSessionActionPerformed
        dispose();
    }//GEN-LAST:event_jBtnCloseSessionActionPerformed

    private void jBtnNewCommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNewCommentActionPerformed
        commentPost(jTablePosts.getSelectedRow(), profile);
    }//GEN-LAST:event_jBtnNewCommentActionPerformed

    private void jBtnNewLikeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNewLikeActionPerformed
        addLike(jTablePosts.getSelectedRow(), profile);
    }//GEN-LAST:event_jBtnNewLikeActionPerformed

    private void jBtnSeeOldPostsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSeeOldPostsActionPerformed
        showOldPosts(profile);
    }//GEN-LAST:event_jBtnSeeOldPostsActionPerformed

    private void jBtnShowBiographyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnShowBiographyActionPerformed
        showBiography(ownProfile, jTableFriendsList.getSelectedRow(), profile);
    }//GEN-LAST:event_jBtnShowBiographyActionPerformed

    private void jBtnRemoveMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnRemoveMessageActionPerformed
        deletePrivateMessage(jTablePrivateMessages.getSelectedRow(), profile);
    }//GEN-LAST:event_jBtnRemoveMessageActionPerformed

    private void jBtnSendPrivateMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSendPrivateMessageActionPerformed
        sendPrivateMessage(ownProfile, jTableFriendsList.getSelectedRow(), profile);
    }//GEN-LAST:event_jBtnSendPrivateMessageActionPerformed

    private void jBtnAcceptFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAcceptFriendActionPerformed
        proccessFriendshipRequest(jListFriendshipRequests.getSelectedIndex(), profile, true);
    }//GEN-LAST:event_jBtnAcceptFriendActionPerformed

    private void jBtnRejectFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnRejectFriendActionPerformed
        proccessFriendshipRequest(jListFriendshipRequests.getSelectedIndex(), profile, false);
    }//GEN-LAST:event_jBtnRejectFriendActionPerformed

    private void jBtnNewFriendshipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNewFriendshipActionPerformed
        String friendRequestToSend = JOptionPane.showInputDialog("¿Quién quieres que sea tu nuevo amigo?");
        sendFriendshipRequest(ownProfile, friendRequestToSend, profile);
    }//GEN-LAST:event_jBtnNewFriendshipActionPerformed

    private void jBtnReadMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnReadMessageActionPerformed
        readPrivateMessage(jTablePrivateMessages.getSelectedRow(), profile);
    }//GEN-LAST:event_jBtnReadMessageActionPerformed

    private void jTablePostsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePostsMouseClicked
        showComments();
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            commentPost(jTablePosts.getSelectedRow(), profile);
        }
    }//GEN-LAST:event_jTablePostsMouseClicked

    private void jTablePostsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTablePostsKeyReleased
        showComments();
    }//GEN-LAST:event_jTablePostsKeyReleased

    private void jTableFriendsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableFriendsListMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            showBiography(ownProfile, jTableFriendsList.getSelectedRow(), profile);
        }
    }//GEN-LAST:event_jTableFriendsListMouseClicked

    private void jTablePrivateMessagesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePrivateMessagesMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            readPrivateMessage(jTablePrivateMessages.getSelectedRow(), profile);
        }
    }//GEN-LAST:event_jTablePrivateMessagesMouseClicked

    private void jBtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnUpdateActionPerformed
        profileController.reloadProfile();
    }//GEN-LAST:event_jBtnUpdateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("GTK+".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIProfileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIProfileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIProfileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIProfileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAcceptFriend;
    private javax.swing.JButton jBtnChangeStatus;
    private javax.swing.JButton jBtnCloseSession;
    private javax.swing.JButton jBtnNewComment;
    private javax.swing.JButton jBtnNewFriendship;
    private javax.swing.JButton jBtnNewLike;
    private javax.swing.JButton jBtnNewPost;
    private javax.swing.JButton jBtnReadMessage;
    private javax.swing.JButton jBtnRejectFriend;
    private javax.swing.JButton jBtnRemoveMessage;
    private javax.swing.JButton jBtnSeeOldPosts;
    private javax.swing.JButton jBtnSendPrivateMessage;
    private javax.swing.JButton jBtnShowBiography;
    private javax.swing.JButton jBtnUpdate;
    private javax.swing.JLabel jLabelPrivateMessages;
    private javax.swing.JLabel jLblComentarios;
    private javax.swing.JLabel jLblFirendList;
    private javax.swing.JLabel jLblFriendshipRequests;
    private javax.swing.JLabel jLblLogo;
    private javax.swing.JLabel jLblNumPosts;
    private javax.swing.JLabel jLblSessionProfileName;
    private javax.swing.JLabel jLblSessionProfileStatus;
    private javax.swing.JLabel jLblSessionProfileUser;
    private javax.swing.JList<String> jListFriendshipRequests;
    private javax.swing.JPanel jPanelBiography;
    private javax.swing.JPanel jPanelComments;
    private javax.swing.JPanel jPanelFirenshipRequestList;
    private javax.swing.JPanel jPanelFriends;
    private javax.swing.JPanel jPanelFriendsList;
    private javax.swing.JPanel jPanelPostComments;
    private javax.swing.JPanel jPanelPosts;
    private javax.swing.JPanel jPanelPrivateMessages;
    private javax.swing.JScrollPane jScrollPaneComments;
    private javax.swing.JScrollPane jScrollPaneFriendshipRequestList;
    private javax.swing.JScrollPane jScrollPanePosts;
    private javax.swing.JScrollPane jScrollPanePosts1;
    private javax.swing.JScrollPane jScrollPanePrivateMessages;
    private javax.swing.JTabbedPane jTabPanePanel;
    private javax.swing.JTable jTableComments;
    private javax.swing.JTable jTableFriendsList;
    private javax.swing.JTable jTablePosts;
    private javax.swing.JTable jTablePrivateMessages;
    // End of variables declaration//GEN-END:variables

    private static class DefaultTableModelImpl extends DefaultTableModel {

        public DefaultTableModelImpl() {
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
