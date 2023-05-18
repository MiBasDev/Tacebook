/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tacebook.controller;

import java.security.NoSuchAlgorithmException;
import tacebook.view.InitMenuView;
import java.util.Date;
import tacebook.model.Post;
import tacebook.model.Profile;
import tacebook.persistence.PersistenceException;
import tacebook.persistence.ProfileDB;
import tacebook.persistence.TacebookDB;
import tacebook.view.GUIInitMenuView;
import tacebook.view.GUIProfileView;
import tacebook.view.TextInitMenuView;

/**
 * Clase que será a que conteña o método "main()" da aplicación, no que
 * simplemente se creará un obxecto desta clase e invocarase o método "init()".
 * Terá un atributo "initMenuView" que será un obxecto da clase "InitMenuView"
 * que creará na declaración da variable.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class InitMenuController {

    private InitMenuView initMenuView;
    private boolean textMode;

    /**
     * Devuelve el menú de vista.
     *
     * @return Menú de vista.
     */
    public InitMenuView getInitMenuView() {
        return initMenuView;
    }

    /**
     * Cambia el menú de vista.
     *
     * @param initMenuView Menú de vista.
     */
    public void setInitMenuView(InitMenuView initMenuView) {
        this.initMenuView = initMenuView;
    }

    /**
     * Constructor de la clase InitMenuController.
     *
     * @param textMode True si interfaz de texto, false si interfaz gráfica.
     */
    public InitMenuController(boolean textMode) {
        this.textMode = textMode;
        if (this.textMode) {
            initMenuView = new TextInitMenuView(this);
        } else {
            initMenuView = new GUIInitMenuView(this);
        }
    }

    /**
     * Método que inicia a aplicación, chamando repetidamente o método
     * "showLoginMenu()" do obxecto vista ata que devolva true.
     */
    private void init() {
        boolean exit;
        do {
            exit = initMenuView.showLoginMenu();
        } while (!exit);
    }

    /**
     * Método que intenta iniciar sesión na aplicación cun usuario e
     * contrasinal. Para iso creará un obxecto da clase "ProfileController" e
     * buscará se hai un perfil co nome e contrasinal recibidos (usando a clase
     * "ProfileDB"). Se non existe chamará o método "showLoginErrorMessage()"
     * para que mostre o erro ao usuario, e se o atopa abrirá unha sesión con
     * ese perfil usando o método "openSession()" do controlador do perfil.
     *
     * @param name Nombre del usuario.
     * @param password Contraseña del usuario.
     */
    public void login(String name, String password) throws NoSuchAlgorithmException {
        ProfileController controller = new ProfileController(textMode);
        try {
            // Comprobamos que exista el perfil con el que intentan logear
            Profile p = ProfileDB.findByNameAndPassword(name, password, controller.getPostsShowed());
            if (p == null) {
                initMenuView.showLoginErrorMessage();
            } else {
                controller.openSession(p);
            }
        } catch (PersistenceException e) {
            proccessPersistenceException(e);
        }
    }

    /**
     * Método que rexistra un novo usuario, simplemente chamando ao obxecto
     * vista para que mostre o menú de rexistro.
     */
    public void register() {
        initMenuView.showRegisterMenu();
    }

    /**
     * Método que crea un novo perfil (comprobando que o nome non estea xa en
     * uso por outro perfil, chamando á vista para pedir un novo nome nese caso)
     * e abre unha sesión con el. Creará o obxecto "Profile" cos datos
     * recibidos, almacenarao usando a clase "ProfileDB" e creará un obxecto da
     * clase "ProfileController" para abrir unha sesión con ese perfil usando o
     * método "openSession()".
     *
     * @param name Nombre del usuario.
     * @param password Contraseña del usuario.
     * @param status Estado del usuario.
     */
    public void createProfile(String name, String password, String status) {
        try {
            // Comprobamos que el nombre "name" no se está usando por otro usario
            while (ProfileDB.findByName(name, 0) != null) {
                name = initMenuView.showNewNameMenu();
            }
            Profile profile = new Profile(name, password, status);
            ProfileDB.save(profile);
            ProfileController controller = new ProfileController(textMode);
            controller.openSession(profile);
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
                initMenuView.showReadErrorMessage();
                break;
            case PersistenceException.CANNOT_WRITE:
                initMenuView.showWriteErrorMessage();
                break;
            case PersistenceException.CONECTION_ERROR:
                initMenuView.showConnectionErrorMessage();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Look and Feel ÉPICO
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("GTK+".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIProfileView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        Profile miguel = new Profile("miguel", "123", "Programando, no molestar.");
        Profile ainhoa = new Profile("ainhoa", "123", "Ben perrona");
        Profile sandra = new Profile("sandra", "123", "Empezó la guerra");
        Profile bilo = new Profile("bilo", "123", "Soy brasileiro");
        Profile jorge = new Profile("jorge", "123", "Soy el mago de la programación");

        try {
            TacebookDB.getProfiles().add(miguel);
            TacebookDB.getProfiles().add(ainhoa);
            TacebookDB.getProfiles().add(sandra);
            TacebookDB.getProfiles().add(bilo);
            TacebookDB.getProfiles().add(jorge);
        } catch (PersistenceException e) {
        }

        // Amigos
        miguel.getFriends().add(ainhoa);
        ainhoa.getFriends().add(miguel);
        sandra.getFriends().add(ainhoa);
        sandra.getFriends().add(miguel);
        miguel.getFriends().add(sandra);
        ainhoa.getFriends().add(sandra);
        bilo.getFriends().add(sandra);
        bilo.getFriends().add(ainhoa);
        bilo.getFriends().add(miguel);
        miguel.getFriends().add(bilo);
        ainhoa.getFriends().add(bilo);
        sandra.getFriends().add(bilo);

        // Solicitudes amistad
        miguel.getFriendshipRequests().add(jorge);

        // Mensaje privados
//        Message message = new Message(0, "hola guapo", new Date(), false, miguel, ainhoa);
//        miguel.getMessages().add(message);
        // Posts
        miguel.getPosts().add(new Post(0, new Date(), "Hola hijos míos", miguel, miguel));
        miguel.getPosts().add(new Post(0, new Date(), "Que tal miguel", ainhoa, miguel));
        ainhoa.getPosts().add(new Post(0, new Date(), "Hola, me llamo jainoja", ainhoa, ainhoa));
        sandra.getPosts().add(new Post(0, new Date(), "La guerneve", sandra, sandra));
        bilo.getPosts().add(new Post(0, new Date(), "¿Sabes como me llamaban en Brasil? BILO", bilo, bilo));

        // Inicilización en consola con "text" o interfaz gráfica
        InitMenuController controller;
        if ((args.length == 1) && (args[0].equals("text"))) {
            controller = new InitMenuController(true);
        } else {
            controller = new InitMenuController(false);
        }

        // Inicio del programa
        controller.init();
        TacebookDB.close();
    }
}
