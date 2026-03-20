package dam.code.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase Persona
 *
 * @author Alumno- Marco Martin
 * @version 1.0
 */
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;

    private String dni;
    private String nombre;
    private String apellido;
    private String email;

    /**
     * Constructor principal de la clase Persona
     *
     * @param dni
     * @param nombre
     * @param apellido
     * @param email
     */
    public Persona(String dni, String nombre, String apellido, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
    }

    /**
     * Getter del DNI de la persona
     *
     * @return el DNI de la persona
     */
    public String getDni() {
        return dni;
    }

    /**
     * Setter del DNI de la persona
     *
     * @param dni el nuevo DNI
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Getter del nombre de la persona
     *
     * @return el nombre de la persona
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter del nombre de la persona
     *
     * @param nombre el nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter del apellido de la persona
     *
     * @return el apellido de la persona
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Setter del apellido de la persona
     *
     * @param apellido el nuevo apellido
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Getter del email de la persona
     *
     * @return el email de la persona
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter del email de la persona
     *
     * @param email el nuevo email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * HashCode basado en el DNI de la persona
     *
     * @return el código hash, que en este caso será el dni
     */
    @Override
    public int hashCode() {
        return dni != null ? dni.hashCode() : 0;
    }

    /**
     * Compara dos personas basándose exclusivamente en su DNI
     *
     * @param obj el objeto a comparar
     * @return true si los DNI coinciden
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Persona p = (Persona) obj;
        return this.dni != null ? this.dni.equals(p.dni) : p.dni == null;
    }
}