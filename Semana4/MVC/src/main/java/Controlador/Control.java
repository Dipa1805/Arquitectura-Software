package Controlador;

import Modelo.Modelo;
import Vista.Vista;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author diego
 */
public class Control {

    private Vista vista;
    private ArrayList<Modelo> personas;

    public Control(Vista vista) {
        this.vista = vista;
        this.personas = new ArrayList<>();
        iniciarEventos();
    }

    private void iniciarEventos() {
        vista.getBtnEliminar().addActionListener(evt -> eliminarPersona());
        vista.getBtnActualizar().addActionListener(evt -> actualizarPersona());
        vista.getBtnBorrar().addActionListener(evt -> borrarTodo());
        vista.getDatechooserFecha().addPropertyChangeListener("date", evt -> {
            Date fechaSeleccionada = (Date) evt.getNewValue();
            if (fechaSeleccionada != null) {
                int edad = calcularEdad(fechaSeleccionada);
                vista.getTxtEdad().setText(String.valueOf(edad));
            }
        });
    }

    public void agregarPersona() {
        String nombre = vista.getTxtNombre().getText();
        Date fechaNacimiento = vista.getDatechooserFecha().getDate();  // Asegurarte de que no sea null
        String direccion = vista.getTxtDireccion().getText();
        String telefono = vista.getTxtTelefono().getText();

        // Validar primero si la fecha de nacimiento es nula antes de calcular la edad
        if (fechaNacimiento == null) {
            JOptionPane.showMessageDialog(vista, "Por favor, selecciona una fecha de nacimiento.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calcular la edad
        int edad = calcularEdad(fechaNacimiento);

        // Validar la edad calculada
        if (edad < 0) {
            JOptionPane.showMessageDialog(vista, "La fecha de nacimiento no puede ser mayor que la fecha actual.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que los demás campos no estén vacíos
        if (nombre.isEmpty() || direccion.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, rellena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear el objeto Persona y agregarlo a la lista
        Modelo persona = new Modelo(nombre, edad, fechaNacimiento, direccion, telefono);
        personas.add(persona);

        // Actualizar la visualización (por ejemplo, actualizar la tabla)
        actualizarDisplay();

        // Limpiar los campos después de agregar
        limpiarCampos();
    }

    public void eliminarPersona() {
        int selectedRow = vista.getTablaPersonas().getSelectedRow();
        if (selectedRow != -1) {
            personas.remove(selectedRow);
            actualizarDisplay();
        } else {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione una fila para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarPersona() {
        int selectedRow = vista.getTablaPersonas().getSelectedRow();
        if (selectedRow != -1) {
            String nuevoNombre = vista.getTxtNombre().getText();
            int nuevaEdad = Integer.parseInt(vista.getTxtEdad().getText());
            Date nuevaFechaNacimiento = vista.getDatechooserFecha().getDate();
            String nuevaDireccion = vista.getTxtDireccion().getText();
            String nuevoTelefono = vista.getTxtTelefono().getText();

            Modelo persona = personas.get(selectedRow);
            persona.setNombre(nuevoNombre);
            persona.setEdad(nuevaEdad);
            persona.setFechaNacimiento(nuevaFechaNacimiento);
            persona.setDireccion(nuevaDireccion);
            persona.setTelefono(nuevoTelefono);

            actualizarDisplay();
        } else {
            JOptionPane.showMessageDialog(vista, "Por favor, seleccione una fila para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void borrarTodo() {
        personas.clear();
        actualizarDisplay();
    }

    private int calcularEdad(Date fechaNacimiento) {
        if (fechaNacimiento == null) {
            return -1; // Maneja el caso donde la fecha de nacimiento es nula
        }

        java.util.Calendar fechaNac = java.util.Calendar.getInstance();
        fechaNac.setTime(fechaNacimiento);

        java.util.Calendar fechaAct = java.util.Calendar.getInstance(); // Obtén la fecha actual

        int edad = fechaAct.get(java.util.Calendar.YEAR) - fechaNac.get(java.util.Calendar.YEAR);

        // Ajustar si aún no ha cumplido años este año
        if (fechaAct.get(java.util.Calendar.DAY_OF_YEAR) < fechaNac.get(java.util.Calendar.DAY_OF_YEAR)) {
            edad--;
        }

        return edad;
    }

    private void actualizarDisplay() {
        vista.limpiarTabla(); // Método que deberías crear en la vista para limpiar la tabla
        for (Modelo persona : personas) {
            vista.agregarFilaTabla(persona); // Método que deberías crear en la vista para agregar filas a la tabla
        }
        vista.limpiar(); // Llamar al método que limpia los campos de entrada
    }

    public void guardarDatos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("datos.txt"))) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Formato de fecha
            for (Modelo persona : personas) {
                writer.write(persona.getNombre() + "," + persona.getEdad() + "," + sdf.format(persona.getFechaNacimiento()) + "," + persona.getDireccion() + "," + persona.getTelefono());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(vista, "Datos guardados correctamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vista, "Error al guardar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void leerDatos() {
        personas.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader("datos.txt"))) {
            String linea;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Usar el mismo formato aquí
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 5) {
                    JOptionPane.showMessageDialog(vista, "Formato de datos incorrecto en la línea: " + linea, "Error", JOptionPane.ERROR_MESSAGE);
                    continue; // Salta la línea si no tiene suficientes datos
                }
                String nombre = datos[0].trim();
                int edad = Integer.parseInt(datos[1].trim());
                // Cambiar aquí para usar el formato correcto
                Date fechaNacimiento = sdf.parse(datos[2].trim());
                String direccion = datos[3].trim();
                String telefono = datos[4].trim();
                Modelo persona = new Modelo(nombre, edad, fechaNacimiento, direccion, telefono);
                personas.add(persona);
            }
            JOptionPane.showMessageDialog(vista, "Datos cargados correctamente.");
            actualizarDisplay();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(vista, "Error al parsear la fecha: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Error de formato en los datos numéricos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        vista.getTxtNombre().setText("");
        vista.getDatechooserFecha().setDate(null); // Limpiar el campo de fecha
        vista.getTxtDireccion().setText("");
        vista.getTxtTelefono().setText("");
        vista.getTxtEdad().setText(""); // Si hay un campo para mostrar la edad, también lo limpiamos
    }
}
