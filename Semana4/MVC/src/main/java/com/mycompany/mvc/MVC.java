package com.mycompany.mvc;

import Controlador.Control;
import Vista.Vista;
import com.formdev.flatlaf.FlatDarculaLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author diego
 */
public class MVC {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Error al establecer el Look and Feel: " + ex.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> {
            Vista vista = new Vista();
            Control controlador = new Control(vista);
            vista.setVisible(true);
        });
    }
}
