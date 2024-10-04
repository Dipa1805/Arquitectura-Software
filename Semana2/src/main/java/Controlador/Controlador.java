/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Estudiante;
import Modelo.EstudianteArray;
import Vista.IRegistro;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author USER 17
 */
public class Controlador {
    private EstudianteArray gestor;
    private IRegistro vista;

    public Controlador(IRegistro vista, EstudianteArray gestor) {
        this.vista = vista;
        this.gestor = gestor;

        // Asignar eventos usando los getters de los botones
        this.vista.getBtnGuardar().addActionListener(e -> guardarEstudiante());
        this.vista.getBtnEliminar().addActionListener(e -> eliminarEstudiante());
        this.vista.getBtnEliminarTodos().addActionListener(e -> eliminarTodosEstudiantes());
        this.vista.getBtnVerificar().addActionListener(e -> verificarLista());

        // Añadir DocumentListener al campo de búsqueda
        this.vista.getTxtBuscador().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarPorNombre();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarPorNombre();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buscarPorNombre();
            }
        });
    }
    
    public void guardarEstudiante() {
        // Usar los getters para acceder a los campos
        if (vista.getTxtCodigo().getText().isEmpty() || vista.getTxtNombre().getText().isEmpty() || 
            vista.getTxtApellido().getText().isEmpty() || vista.getCboFacultad().getSelectedItem() == null || 
            vista.getCboProvincia().getSelectedItem() == null || vista.getCalendario().getDate() == null) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
            return;
        }

        Estudiante estudiante = new Estudiante();
        estudiante.setCodigo(vista.getTxtCodigo().getText());
        estudiante.setNombre(vista.getTxtNombre().getText());
        estudiante.setApellidos(vista.getTxtApellido().getText());
        estudiante.setFacultad(vista.getCboFacultad().getSelectedItem().toString());
        estudiante.setProvincia(vista.getCboProvincia().getSelectedItem().toString());

        Date fechaNacimiento = vista.getCalendario().getDate();
        gestor.agregarEstudiante(estudiante, fechaNacimiento);

        gestor.limpiar(vista.getTxtCodigo(), vista.getTxtNombre(), vista.getTxtApellido(), vista.getCboFacultad(), vista.getCboProvincia(), vista.getCalendario());
        gestor.actualizarTabla();
    }

    public void eliminarEstudiante() {
        String codigo = vista.getTxtCodigo().getText();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese el código del estudiante a eliminar.");
            return;
        }

        gestor.eliminarEstudiante(codigo);
        gestor.actualizarTabla();
    }

    public void buscarPorNombre() {
        String nombreBuscado = vista.getTxtBuscador().getText();
        gestor.buscarPorNombre(nombreBuscado); // Actualiza la tabla según el texto buscado
    }

    public void eliminarTodosEstudiantes() {
        gestor.eliminarTodosEstudiantes();
    }

    public void verificarLista() {
        gestor.verificarListaVacia();
    }
}