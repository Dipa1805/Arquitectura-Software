package Frames;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import Clases.Conectar;
import Clases.*;
import Clases.TextPrompt;
import com.mysql.jdbc.Driver;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import java.sql.PreparedStatement;
import java.net.ConnectException;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.DriverManager;






/**
 *
 * @author JHULINHO
 */
public class Registrar_alumnos extends javax.swing.JFrame {

    /**
     * Creates new form Registrar_alumnos
     */
    public Registrar_alumnos() {
        initComponents();
        
        TextPrompt nombre=new TextPrompt("Escribe tu nombre", txtNombre);
        TextPrompt apellidos=new TextPrompt("Escribe tu apellido", txtapellidos);
        TextPrompt seccion=new TextPrompt("seccion", txtseccion);
        TextPrompt buscar=new TextPrompt("Buscar", txtbuscar);
        
        this.setLocationRelativeTo(null);
         limpiar();
         txtid_alumno.setEnabled(false);
        
         
         mostrartabla("");
         cargarcombocurso(cbomateria);
         
         for(int i=0; i <= tabla_registros_alumno.getRowCount(); i++ ){
                
                    txtcantidad.setText(" " +i);
            }
   
    }
    void limpiar(){
        txtid_alumno.setText("");
        txtNombre.setText("");
        txtapellidos.setText("");
        txtseccion.setText("");
        cbogrado.setSelectedIndex(0);
        cbomateria.setSelectedIndex(0);
    }
    void mostrartabla(String valor){
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Grado");
        modelo.addColumn("Seccion");
        modelo.addColumn("Materia");
        
        tabla_registros_alumno.setModel(modelo);
        String sql="SELECT * FROM alumnos WHERE CONCAT(nombre,' ',apellido,' ',grado,' ',id_curso_asignado) LIKE '%"+valor+"%'";
        
        String datos[]= new String[6];
        Statement st;
        
        try {
            
            st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            
            while(rs.next()){
            datos[0]=rs.getString(1);
            datos[1]=rs.getString(2);
            datos[2]=rs.getString(3);
            datos[3]=rs.getString(4);
            datos[4]=rs.getString(5);
            datos[5]=rs.getString(6);
            
            modelo.addRow(datos);
            }
            
            tabla_registros_alumno.setModel(modelo);
        } catch (SQLException e) {
            System.err.println(e);
        }
        
    }
    public void  cargarcombocurso(JComboBox combodelcurso){
        try {
            String sql="SELECT nombre_curso FROM curso";
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sql);
            
            while(rs.next()){
                combodelcurso.addItem(rs.getString("nombre_curso"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
     void eliminar(){
       try {
            PreparedStatement ps=cn.prepareStatement("DELETE FROM alumnos WHERE id_alumno='"+txtid_alumno.getText()+"'");
            
            int respuesta=ps.executeUpdate();
            if(respuesta > 0){
                JOptionPane.showMessageDialog(null, "Eliminado Correctamente");
                mostrartabla("");
                limpiar();
            }
            else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado una fila");
                
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Error al actualizar");
            
        }
    }
     void exportar(){
         Document documento =new Document();
        try {
            String ruta =System.getProperty("user.home");
             PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/Registro_Alumnos.pdf"));
             documento.open();
             
             PdfPTable tabla = new PdfPTable(6);
             
             tabla.addCell("Id");
             tabla.addCell("Nombre");
             tabla.addCell("Apellido");
             tabla.addCell("Grado");
             tabla.addCell("Seccion");
             tabla.addCell("Materia");
             
             try {
                 Connection co = DriverManager.getConnection("jdbc:mysql://localhost/sistema","root","");
                 PreparedStatement pst=co.prepareStatement("SELECT * FROM alumnos");
                 ResultSet rs =pst.executeQuery();
                 if(rs.next()){
                     do {
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                        tabla.addCell(rs.getString(6));
                     }while(rs.next());
                     documento.add(tabla);
                 }
                
            } catch (DocumentException | SQLException e) {
            }
             documento.close();
             JOptionPane.showMessageDialog(null, "Reporte Creado");
        } catch (DocumentException | HeadlessException | FileNotFoundException e) {
        }
     }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popborrar = new javax.swing.JPopupMenu();
        popeliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        txtseccion = new javax.swing.JTextField();
        txtapellidos = new javax.swing.JTextField();
        cbomateria = new javax.swing.JComboBox<>();
        cbogrado = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtid_alumno = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_registros_alumno = new javax.swing.JTable();
        btmactualizar = new javax.swing.JButton();
        btmvolver = new javax.swing.JButton();
        btmagregar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtbuscar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtcantidad = new javax.swing.JTextField();
        btmpdf = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        popeliminar.setText("Borrar");
        popeliminar.setComponentPopupMenu(popborrar);
        popeliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popeliminarActionPerformed(evt);
            }
        });
        popborrar.add(popeliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(196, 235, 255), 4, true));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("REGISTRO DE ALUMNOS ");

        jPanel2.setBackground(new java.awt.Color(196, 235, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0), 3));

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtseccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtseccionKeyTyped(evt);
            }
        });

        txtapellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtapellidosKeyTyped(evt);
            }
        });

        cbomateria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Materia" }));

        cbogrado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione Grado", "1er Grado", "2do Grado", "3er Grado", "4to Grado", "5to Grado" }));
        cbogrado.setToolTipText("\n");

        jLabel2.setText("ID Alumno");

        jLabel3.setText("Nombre");

        jLabel4.setText("Apellidos");

        jLabel5.setText("Seccion");

        txtid_alumno.setEditable(false);
        txtid_alumno.setBackground(new java.awt.Color(255, 255, 255));
        txtid_alumno.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtid_alumno.setAutoscrolls(false);
        txtid_alumno.setBorder(null);

        jLabel6.setText("Grado");

        jLabel7.setText("Curso");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addGap(156, 156, 156)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(232, 232, 232)
                .addComponent(jLabel5)
                .addGap(91, 91, 91))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(cbogrado, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(cbomateria, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtid_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(76, 76, 76)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtapellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(txtseccion, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtapellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtseccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtid_alumno, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbomateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbogrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Alumnos Registrados"));

        tabla_registros_alumno.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabla_registros_alumno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_registros_alumnoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_registros_alumno);

        btmactualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/actualizar-base-de-datos.png"))); // NOI18N
        btmactualizar.setBorder(null);
        btmactualizar.setContentAreaFilled(false);
        btmactualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmactualizarActionPerformed(evt);
            }
        });

        btmvolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/volver (3).png"))); // NOI18N
        btmvolver.setBorder(null);
        btmvolver.setBorderPainted(false);
        btmvolver.setContentAreaFilled(false);
        btmvolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmvolverActionPerformed(evt);
            }
        });

        btmagregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/guardar-datos (1).png"))); // NOI18N
        btmagregar.setBorder(null);
        btmagregar.setContentAreaFilled(false);
        btmagregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmagregarActionPerformed(evt);
            }
        });

        jLabel8.setText("Buscar: ");

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbuscarKeyReleased(evt);
            }
        });

        jLabel9.setText("Cantidad: ");

        txtcantidad.setEditable(false);

        btmpdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pdf (1).png"))); // NOI18N
        btmpdf.setContentAreaFilled(false);
        btmpdf.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pdf (1).png"))); // NOI18N
        btmpdf.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/pdf (2).png"))); // NOI18N
        btmpdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btmpdfActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/eliminar (2).png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btmagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btmactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btmvolver, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                                .addComponent(btmpdf, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 89, 89)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(350, 350, 350))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btmagregar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btmactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 33, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtcantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(btmpdf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btmvolver, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btmagregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmagregarActionPerformed
        String id_curso_asignado=cbomateria.getSelectedItem().toString();
        String materia =(String) cbomateria.getSelectedItem();
        String grado=(String) cbogrado.getSelectedItem();
        
        try {
            if(txtNombre.getText().isEmpty()){
                
                JOptionPane.showMessageDialog(null, "No puedes dejar el campo nombre vacio");
                
            }
            
            else if(txtapellidos.getText().isEmpty()) {
                
                 JOptionPane.showMessageDialog(null, "No puedes dejar el campo apellido vacio");   
                 
            }
            
            else if(txtseccion.getText().isEmpty()) {
                
                 JOptionPane.showMessageDialog(null, "No puedes dejar el campo seccio vacio"); 
                 
            } 
           
            else if(grado.equals("Seleccione grado")) {
                
                 JOptionPane.showMessageDialog(null, "Deves seleccionar un grado");  
                 
            } 
           
            else if(materia.equals("Seleccione Materia")) {
                
                 JOptionPane.showMessageDialog(null, "Deves seleccionar un curso");  
                 
            }
            
            else{
                
                PreparedStatement ps=cn.prepareStatement("INSERT INTO alumnos (nombre,apellido,grado,seccion,id_curso_asignado) VALUES (?,?,?,?,?)");
                ps.setString(1, txtNombre.getText());
                ps.setString(2, txtapellidos.getText());
                ps.setString(3, cbogrado.getSelectedItem().toString());
                ps.setString(4, txtseccion.getText());
                ps.setString(5, id_curso_asignado);
                
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Registrado Exitosamente");
                mostrartabla("");
                limpiar();
                
                for(int i=0; i <= tabla_registros_alumno.getRowCount(); i++ ){
                
                    txtcantidad.setText(" " +i);
            }
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Error al reguistrar");
        }
        
    }//GEN-LAST:event_btmagregarActionPerformed

    private void btmvolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmvolverActionPerformed
        Principal pl=new Principal();
        pl.setVisible(true);
        dispose();
    }//GEN-LAST:event_btmvolverActionPerformed

    private void tabla_registros_alumnoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_registros_alumnoMouseClicked
        int fila=this.tabla_registros_alumno.getSelectedRow();
        this.txtid_alumno.setText(this.tabla_registros_alumno.getValueAt(fila, 0).toString());
        this.txtNombre.setText(this.tabla_registros_alumno.getValueAt(fila, 1).toString());
        this.txtapellidos.setText(this.tabla_registros_alumno.getValueAt(fila, 2).toString());
        this.cbogrado.setSelectedItem(this.tabla_registros_alumno.getValueAt(fila, 3).toString());
        this.txtseccion.setText(this.tabla_registros_alumno.getValueAt(fila, 4).toString());
        this.cbomateria.setSelectedItem(this.tabla_registros_alumno.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tabla_registros_alumnoMouseClicked

    private void btmactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmactualizarActionPerformed
        String materia=(String)cbomateria.getSelectedItem();
        String grado=(String)cbogrado.getSelectedItem();
        
        try {
            if(txtNombre.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "No puedes dejar el campo vacio");
            }
             if(txtapellidos.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "No puedes dejar el campo vacio");
            }
              if(txtseccion.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "No puedes dejar el campo vacio");
            }
               else if(grado.equals("Seleccione grado")) {
                
                 JOptionPane.showMessageDialog(null, "Deves seleccionar un grado");  
                 
            } 
               else if(materia.equals("Seleccione materia")) {
                
                 JOptionPane.showMessageDialog(null, "Deves seleccionar un grado");  
                 
            } 
            PreparedStatement ps=
                    cn.prepareStatement("UPDATE alumnos SET nombre='"+txtNombre.getText()+"',apellido='"+txtapellidos.getText()+"',grado='"+cbogrado.getSelectedItem().toString()+"',seccion='"+txtseccion.getText()+"',id_curso_asignado='"+cbomateria.getSelectedItem().toString()+"' WHERE id_alumno='"+txtid_alumno.getText()+"'");
            
            int respuesta = ps .executeUpdate();
            if(respuesta>0)
            {
                JOptionPane.showMessageDialog(null, "Actualizado con exito");
                limpiar();
                mostrartabla("");       
            }
            else{
                JOptionPane.showMessageDialog(null, "No ha seleccionado una fila");
            }
               
        } catch (SQLException e) {
            System.err.println(e); 
             JOptionPane.showMessageDialog(null,"Error al actualizar");
           
        }
    }//GEN-LAST:event_btmactualizarActionPerformed

    private void popeliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popeliminarActionPerformed
       
        try {
            PreparedStatement ps=cn.prepareStatement("DELETE FROM alumnos WHERE id_alumno='"+txtid_alumno.getText()+"'");
            
            int respuesta=ps.executeUpdate();
            if(respuesta > 0){
                JOptionPane.showMessageDialog(null, "Eliminado Correctamente");
                mostrartabla("");
                limpiar();
            }
            else {
                JOptionPane.showMessageDialog(null, "No ha seleccionado una fila");
                
            }
        } catch (SQLException e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(null, "Error al actualizar");
            
        }
    }//GEN-LAST:event_popeliminarActionPerformed

    private void txtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyReleased
        mostrartabla(txtbuscar.getText());
        for(int i=0; i <= tabla_registros_alumno.getRowCount(); i++ ){
                
                    txtcantidad.setText(" " +i);
            }
    }//GEN-LAST:event_txtbuscarKeyReleased

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
       char c=evt.getKeyChar();
       if ((c<'a'  || c>'z') && (c<'A')  | c>'Z' && c!=KeyEvent.VK_SPACE) evt.consume();
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtapellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtapellidosKeyTyped
         char c=evt.getKeyChar();
       if ((c<'a'  || c>'z') && (c<'A')  | c>'Z' && c!=KeyEvent.VK_SPACE) evt.consume();
    }//GEN-LAST:event_txtapellidosKeyTyped

    private void txtseccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtseccionKeyTyped
         char c=evt.getKeyChar();
       if ((c<'a'  || c>'z') && (c<'A')  | c>'Z' && c!=KeyEvent.VK_SPACE) evt.consume();
    }//GEN-LAST:event_txtseccionKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        eliminar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btmpdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btmpdfActionPerformed
        exportar();
    }//GEN-LAST:event_btmpdfActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Registrar_alumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Registrar_alumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Registrar_alumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Registrar_alumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Registrar_alumnos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btmactualizar;
    private javax.swing.JButton btmagregar;
    private javax.swing.JButton btmpdf;
    private javax.swing.JButton btmvolver;
    private javax.swing.JComboBox<String> cbogrado;
    private javax.swing.JComboBox<String> cbomateria;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu popborrar;
    private javax.swing.JMenuItem popeliminar;
    private javax.swing.JTable tabla_registros_alumno;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtapellidos;
    private javax.swing.JTextField txtbuscar;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtid_alumno;
    private javax.swing.JTextField txtseccion;
    // End of variables declaration//GEN-END:variables

    Conectar con= new Conectar();
    Connection cn=con.conexion();
}
