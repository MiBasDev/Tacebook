/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.view;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import tacebook.controller.InitMenuController;

/**
 * Clase que encárgase de mostrar as opcións do menú inicial e recoller os datos
 * de entrada, invocando ao obxecto controlador para que execute as accións
 * correspondentes. Terá un atributo "initMenuController" que mantén a
 * referencia ao obxecto controlador (que se recibirá como parámetro no
 * construtor).
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class TextInitMenuView implements InitMenuView {

    private InitMenuController controller;

    /**
     * Constructor de la clase InitMenuView.
     *
     * @param controller Objeto de la clase InitMenuController.
     */
    public TextInitMenuView(InitMenuController controller) {
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
        Scanner scan = new Scanner(System.in);
        boolean menu = false;
        System.out.println("BIENVENID@ a MAINH-BOOK");
        System.out.println("");
        System.out.println("¿Qué desea?");
        System.out.println("1.- Inicio de sesión.");
        System.out.println("2.- Registro.");
        System.out.println("3.- LogOut.");
        System.out.println("Elige una opción:");
        int menuNumber = readNumber(scan);
        System.out.println("");
        switch (menuNumber) {
            case 1:
                System.out.println("");
                System.out.println("Has elegido iniciar sesión:");
                System.out.println("Usuario: ");
                String user = scan.nextLine();
                System.out.println("Contraseña: ");
                String pass;
                if (System.console() == null) {
                    pass = scan.nextLine();
                } else {
                    pass = new String(System.console().readPassword());
                }
                 {
                    try {
                        controller.login(user, pass);
                    } catch (NoSuchAlgorithmException ex) {
                        ex.getMessage();
                    }
                }
                break;

            case 2:
                controller.register();
                break;
            case 3:
                menu = true;
                break;
            default:
                System.out.println("");
                System.out.println("Opción incorrecta.");
                System.out.println("");
        }
        return menu;
    }

    /**
     * Método que mostra unha mensaxe de usuario e contrasinal incorrectos.
     */
    @Override
    public void showLoginErrorMessage() {
        System.out.println("");
        System.out.println("USUARIO O CONTRASEÑA INCORRECTAS. INTENTE LOGEAR DE NUEVO O REGISTRESE SI NO LO ESTÁ.");
        System.out.println("");
    }

    /**
     * Método que mostra o menú para rexistrarse, no que se pedirá un nome para
     * o perfil, un contrasinal (que se pedirá dúas veces, comprobando que
     * coincidan) e un estado. Con esa información, invocarase o método
     * "createProfile()" do controlador.
     */
    @Override
    public void showRegisterMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        System.out.println("Has elegido registro:");
        System.out.println("Usuario: ");
        String user = scan.nextLine();
        String pass;
        String passRepeat;
        do {
            System.out.println("Contraseña: ");
            pass = scan.nextLine();
            System.out.println("Repite la contraseña: ");
            passRepeat = scan.nextLine();
            if (!pass.equals(passRepeat)) {
                System.out.println("");
                System.out.println("Las contraseñas no coinciden.");
                System.out.println("");
            }
        } while (!pass.equals(passRepeat));

        System.out.println("Estado actual: ");
        String status = scan.nextLine();

        controller.createProfile(user, pass, status);
    }

    /**
     * Método que mostra unha mensaxe indicando que o nome de usuario
     * introducido xa estaba en uso e pedido un novo nome para o usuario.
     *
     * @return Nuevo nombre introducido por el usuario.
     */
    @Override
    public String showNewNameMenu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        System.out.println("El nombre introducido ya está en uso...");
        System.out.println("Escriba un nuevo nombre: ");
        String newName = scan.nextLine();

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
