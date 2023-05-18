/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.view;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import tacebook.controller.InitMenuController;

/**
 * Clase que define la interfaz gráfica del menú.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class GUIInitMenuView implements InitMenuView {

    private InitMenuController controller;

    /**
     * Constructor de la clase InitMenuView.
     *
     * @param controller Objeto de la clase InitMenuController.
     */
    public GUIInitMenuView(InitMenuController controller) {
        this.controller = controller;
    }

    /**
     * Devuelve el objeto de la clase InitMenuController.
     *
     * @return Objeto de la clase InitMenuController.
     */
    public InitMenuController getController() {
        return controller;
    }

    /**
     * Cambia el objeto de la clase InitMenuController.
     *
     * @param controller Objeto de la clase InitMenuController.
     */
    public void setController(InitMenuController controller) {
        this.controller = controller;
    }

    /**
     * Método que mostra o menú de inicio de sesión, coas opcións de iniciar
     * sesión (pedirase o usuario e contrasinal e chamarase ao método "login()"
     * do controlador), crear un novo perfil (chamando o método "register()" do
     * controlador) e saír da aplicación
     *
     * @return True si quieres salir de la aplicación, false si no.
     */
    @Override
    public boolean showLoginMenu() {
        boolean menu = false;
        JTextField username = new JTextField();
        JTextField password = new JPasswordField();
        Object[] message = {
            "Usuario:", username,
            "Contraseña:", password
        };
        int option = JOptionPane.showOptionDialog(null, message, "BIENVENID@ A MAINH-BOOK", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo-mainhbook-login.png")),
                new String[]{"LogOut", "Registro", "Inicio de sesión"}, null);
        switch (option) {
            case 2: {
                try {
                    controller.login(username.getText(), password.getText());
                } catch (NoSuchAlgorithmException ex) {
                    ex.getMessage();
                }
            }
            break;

            case 1:
                controller.register();
                break;
            default:
                menu = true;
                break;
        }
        return menu;
    }

    /**
     * Método que mostra unha mensaxe de usuario e contrasinal incorrectos.
     */
    @Override
    public void showLoginErrorMessage() {
        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "ERROR", JOptionPane.ERROR_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
    }

    /**
     * Método que mostra o menú para rexistrarse, no que se pedirá un nome para
     * o perfil, un contrasinal (que se pedirá dúas veces, comprobando que
     * coincidan) e un estado. Con esa información, invocarase o método
     * "createProfile()" do controlador.
     */
    @Override
    public void showRegisterMenu() {
        JTextField username = new JTextField();
        JTextField password1 = new JPasswordField();
        JTextField password2 = new JPasswordField();
        JTextField status = new JTextField();
        Object[] message = {
            "Usuario:", username,
            "Contraseña:", password1,
            "Repita la contraseña:", password2,
            "Estado actual:", status
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Resgistro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Imagenes/register.png")));
        if (option == JOptionPane.OK_OPTION) {
            if (!password1.getText().equals(password2.getText())) {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "ERROR", JOptionPane.ERROR_MESSAGE,
                        new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
                showRegisterMenu();
            } else {
                controller.createProfile(username.getText(), password1.getText(), status.getText());
            }
        }
    }

    /**
     * Método que mostra unha mensaxe indicando que o nome de usuario
     * introducido xa estaba en uso e pedido un novo nome para o usuario.
     *
     * @return Nuevo nombre introducido por el usuario.
     */
    @Override
    public String showNewNameMenu() {
        JOptionPane.showMessageDialog(null, "Ya existe un usuario con es nombre", "ERROR", JOptionPane.ERROR_MESSAGE,
                new javax.swing.ImageIcon(getClass().getResource("/Imagenes/error.png")));
        JTextField username = new JTextField();
        Object[] message = {
            "Nuevo nombre de usuario:", username
        };
        JOptionPane.showConfirmDialog(null, message, "Elección de nombre", JOptionPane.OK_CANCEL_OPTION);
        String newName = username.getText();
        return newName;
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
                System.out.println("Debes introducir un número");
                System.out.println("");
                goodNumber = false;
                showLoginMenu();
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
