package Modelo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author diego
 */
public class Modelo implements Serializable {

    private String nombre;
    private int edad;
    private Date fechaNacimiento;
    private String direccion;
    private String telefono;

    public Modelo(String nombre, int edad, Date fechaNacimiento, String direccion, String telefono) {
        this.nombre = nombre;
        this.edad = edad;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "PersonaMVC{" + "nombre=" + nombre + ", edad=" + edad + ", fechaNacimiento=" + fechaNacimiento + ", direccion=" + direccion + ", telefono=" + telefono + '}';
    }

    private static final long serialVersionUID = 1L;
}
