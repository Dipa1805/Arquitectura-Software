package Clases;

/**
 *
 * @author JHULINHO
 */
public class Registrar_alumnos {
    private int id_alumno;
    private String nombre, apellido, grado, seccion,id_curso_asignado;

    public Registrar_alumnos(int id_alumno, String nombre, String apellido, String grado, String seccion, String id_curso_asignado) {
        this.id_alumno = id_alumno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.grado = grado;
        this.seccion = seccion;
        this.id_curso_asignado = id_curso_asignado;
    }

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getId_curso_asignado() {
        return id_curso_asignado;
    }

    public void setId_curso_asignado(String id_curso_asignado) {
        this.id_curso_asignado = id_curso_asignado;
    }
    
    
}
