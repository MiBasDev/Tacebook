/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tacebook.persistence;

/**
 * CLase que estenderá a clase "Exception" e representa unha excepción que se
 * pode producir nunha operación de persistencia. Terá un atributo "code" de
 * tipo numérico e tres constantes para indicar posibles valores para este
 * atributo: CONECTION_ERROR (que vale cero), CANNOT_READ (que vale un) e
 * CANNOT_WRITE (que vale dous), que definirá tres posibles tipos de erros que
 * imos contemplar. A clase terá un construtor con dous parámetros: un para
 * recibir un valor para o atributo "code" e outro para recibir a mensaxe da
 * excepción, que se utilizará para invocar o construtor da superclase.
 *
 * @author Miguel Bastos Gándara & Ainhoa Barros Queimadelos
 */
public class PersistenceException extends Exception {

    /**
     * Referencia al código de la excepción.
     */
    private int code;

    /**
     * Referencia al error de conexión.
     */
    public static final int CONECTION_ERROR = 0;

    /**
     * Referencia al error de lectura.
     */
    public static final int CANNOT_READ = 1;

    /**
     * Referencia al error de escritura.
     */
    public static final int CANNOT_WRITE = 2;

    /**
     * Devuelve el valor del código.
     *
     * @return Valor del código.
     */
    public int getCode() {
        return code;
    }

    /**
     * Cambia el valor del código.
     *
     * @param code Valor del código.
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Cnstructor de la clase PersistenceException.
     *
     * @param code Valor del código.
     * @param exceptionText Texto de la excepción.
     */
    public PersistenceException(int code, String exceptionText) {
        this.code = code;
    }
}
