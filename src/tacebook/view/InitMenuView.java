/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.view;

/**
 * Interfaz que define el menú de visualización.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public interface InitMenuView {

    /**
     * Método que mostra o menú de inicio de sesión, coas opcións de iniciar
     * sesión (pedirase o usuario e contrasinal e chamarase ao método "login()"
     * do controlador), crear un novo perfil (chamando o método "register()" do
     * controlador) e saír da aplicación
     *
     * @return True si quieres salir de la aplicación, false si no.
     */
    public boolean showLoginMenu();

    /**
     * Método que mostra unha mensaxe de usuario e contrasinal incorrectos.
     */
    public void showLoginErrorMessage();

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

    /**
     * Método que mostra unha mensaxe indicando que o nome de usuario
     * introducido xa estaba en uso e pedido un novo nome para o usuario.
     *
     * @return Nuevo nombre introducido por el usuario.
     */
    public String showNewNameMenu();

    /**
     * Método que mostra o menú para rexistrarse, no que se pedirá un nome para
     * o perfil, un contrasinal (que se pedirá dúas veces, comprobando que
     * coincidan) e un estado. Con esa información, invocarase o método
     * "createProfile()" do controlador.
     */
    public void showRegisterMenu();
}
