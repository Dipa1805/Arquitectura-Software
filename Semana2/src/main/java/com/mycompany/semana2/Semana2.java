/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.semana2;

import Vista.IRegistro;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import javax.swing.UIManager;

/**
 *
 * @author USER 17
 */
public class Semana2 {

    public static void main(String[] args) {
        
        try {
            // Establecer el Look and Feel antes de crear el JFrame
            UIManager.setLookAndFeel(new  HiFiLookAndFeel()); // Cambia el Look and Feel según prefieras
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IRegistro().setVisible(true);
            }
        });
    }
}