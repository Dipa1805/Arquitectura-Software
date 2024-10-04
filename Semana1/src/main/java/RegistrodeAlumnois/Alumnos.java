/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrodeAlumnois;

/**
 *
 * @author diego
 */
public class Alumnos {
    String nombre;
    String fecha;   // Atributo para la fecha (puede ser fecha de nacimiento o inscripción)
    String codigo;
    String telefono;
    String carrera;
    String semestre;

    // Constructor con parámetros
    public Alumnos(String nombre, String fecha, String codigo, String telefono, String carrera, String semestre) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.codigo = codigo;
        this.telefono = telefono;
        this.carrera = carrera;
        this.semestre = semestre;
    }

    // Constructor vacío
    public Alumnos() {
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    // Método toString para mostrar la información del alumno
    @Override
    public String toString() {
        return "Alumnos{" + "nombre=" + nombre + ", fecha=" + fecha + ", codigo=" + codigo + ", telefono=" + telefono + ", carrera=" + carrera + ", semestre=" + semestre + '}';
    }
}


