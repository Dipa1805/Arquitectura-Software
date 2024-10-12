package Controlador;

import Conexion.ConexionBD;
import Modelo.Contado;
import Modelo.Venta;
import Vista.CContado;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diego
 */
public class ControladorCContado {

    private Contado modeloContado;
    private CContado vistaContado;

    public ControladorCContado(Contado modeloContado, CContado vistaContado) {
        this.modeloContado = modeloContado;
        this.vistaContado = vistaContado;
    }

    public void adquirirProducto() {
        // Obtener los datos desde la vista
        String cantidad = vistaContado.getTxtCantidad().getText();
        String cliente = vistaContado.getTxtCliente().getText();
        String ruc = vistaContado.getTxtRuc().getText();
        String producto = vistaContado.getSelectedProducto();

        // Asegurarse de que no haya campos vacíos
        if (cantidad.isEmpty() || cliente.isEmpty() || ruc.isEmpty() || producto == null) {
            JOptionPane.showMessageDialog(vistaContado, "Por favor, complete todos los campos.");
            return;
        }

        int cantidadComprada;
        try {
            cantidadComprada = Integer.parseInt(cantidad);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaContado, "La cantidad debe ser un número entero.");
            return;
        }

        // Crear una nueva venta de contado
        modeloContado = new Contado(cliente, ruc, producto, cantidadComprada, cantidadComprada);

        double precioUnitario = modeloContado.getPrecioUnitario();
        double subtotal = modeloContado.calcularSubtotal();
        DefaultTableModel modeloTabla = (DefaultTableModel) vistaContado.getTablaProductos().getModel();

        int numeroFila = modeloTabla.getRowCount() + 1;

        // Añadir la venta a la tabla
        modeloTabla.addRow(new Object[]{
            numeroFila,
            modeloContado.getNombreProducto(),
            modeloContado.getCantidadComprada(),
            precioUnitario,
            subtotal
        });
        actualizarResumen();
    }

    public void guardarPersona() {
        // Obtener los datos desde la vista
        String cantidadTexto = vistaContado.getTxtCantidad().getText();
        String cliente = vistaContado.getTxtCliente().getText();
        String ruc = vistaContado.getTxtRuc().getText();
        String producto = vistaContado.getSelectedProducto();
        String fechaTexto = vistaContado.getLblFechaActual().getText();

        // Validaciones
        if (cantidadTexto.isEmpty() || cliente.isEmpty() || ruc.isEmpty() || producto.isEmpty() || fechaTexto.isEmpty()) {
            JOptionPane.showMessageDialog(vistaContado, "Por favor, complete todos los campos.");
            return;
        }

        int cantidad;

        try {
            cantidad = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaContado, "La cantidad debe ser un número entero.");
            return;
        }

        // Formatear fecha
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaLocalDate = LocalDate.parse(fechaTexto, formato);
        Date fechaSQL = Date.valueOf(fechaLocalDate);

        // Abrir conexión a la base de datos
        Venta nuevaVenta = new Venta(cliente, ruc, producto, cantidad);
        double precioUnitario = nuevaVenta.getPrecioUnitario();

        double subtotal = cantidad * precioUnitario;

        // Abrir conexión a la base de datos
        ConexionBD db = new ConexionBD();
        Connection conexion = db.conectar();

        try {
            // 1. Verificar si el cliente existe. Si no existe, insertarlo
            String queryCliente = "IF NOT EXISTS (SELECT 1 FROM Cliente WHERE ruc = ?) "
                    + "BEGIN INSERT INTO Cliente (nombre, ruc) VALUES (?, ?); END";
            PreparedStatement psCliente = conexion.prepareStatement(queryCliente);
            psCliente.setString(1, ruc);
            psCliente.setString(2, cliente);
            psCliente.setString(3, ruc);
            psCliente.executeUpdate();

            // Obtener el ID del cliente recién insertado o existente
            String obtenerIdCliente = "SELECT id FROM Cliente WHERE ruc = ?";
            PreparedStatement psIdCliente = conexion.prepareStatement(obtenerIdCliente);
            psIdCliente.setString(1, ruc);
            ResultSet rsCliente = psIdCliente.executeQuery();
            rsCliente.next();
            int clienteId = rsCliente.getInt("id");

            // 2. Verificar si el producto existe. Si no existe, insertarlo
            String queryProducto = "IF NOT EXISTS (SELECT 1 FROM Producto WHERE nombre = ?) "
                    + "BEGIN INSERT INTO Producto (nombre, precioUnitario) VALUES (?, ?); END";
            PreparedStatement psProducto = conexion.prepareStatement(queryProducto);
            psProducto.setString(1, producto);
            psProducto.setString(2, producto);
            psProducto.setDouble(3, precioUnitario);
            psProducto.executeUpdate();

            // Obtener el ID del producto recién insertado o existente
            String obtenerIdProducto = "SELECT id FROM Producto WHERE nombre = ?";
            PreparedStatement psIdProducto = conexion.prepareStatement(obtenerIdProducto);
            psIdProducto.setString(1, producto);
            ResultSet rsProducto = psIdProducto.executeQuery();
            rsProducto.next();
            int productoId = rsProducto.getInt("id");

            // 3. Insertar la venta con el subtotal
            String queryVenta = "INSERT INTO Venta (cliente_id, producto_id, cantidad, subtotal, fecha) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psVenta = conexion.prepareStatement(queryVenta);
            psVenta.setInt(1, clienteId);
            psVenta.setInt(2, productoId);
            psVenta.setInt(3, cantidad);
            psVenta.setDouble(4, subtotal); // Guardar subtotal
            psVenta.setDate(5, fechaSQL);
            psVenta.executeUpdate();

            JOptionPane.showMessageDialog(vistaContado, "Venta registrada con éxito.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vistaContado, "Error al registrar la venta: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close(); // Cerramos la conexión al final
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Limpiar campos
        limpiarCampos();
        actualizarResumen();
    }

    public void listarVentas() {
        String query = "SELECT v.id AS idVenta, c.nombre AS Cliente, p.nombre AS Producto, v.cantidad, p.precioUnitario, "
                + "(v.cantidad * p.precioUnitario) AS Subtotal "
                + "FROM Venta v "
                + "JOIN Cliente c ON v.cliente_id = c.id "
                + "JOIN Producto p ON v.producto_id = p.id";

        ConexionBD db = new ConexionBD();
        try (Connection conexion = db.conectar(); PreparedStatement statement = conexion.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

            DefaultTableModel modeloTabla = (DefaultTableModel) vistaContado.getTablaProductos().getModel();
            modeloTabla.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos

            int numeroFila = 1;
            while (resultSet.next()) {
                String cliente = resultSet.getString("Cliente");
                String producto = resultSet.getString("Producto");
                int cantidad = resultSet.getInt("cantidad");
                double precioUnitario = resultSet.getDouble("precioUnitario");
                double subtotal = resultSet.getDouble("Subtotal");

                modeloTabla.addRow(new Object[]{
                    numeroFila,
                    cliente,
                    producto,
                    cantidad,
                    precioUnitario,
                    subtotal
                });
                numeroFila++;
            }
        } catch (SQLException e) {
            System.out.println("Error al listar ventas: " + e.getMessage());
        }
    }

    public void actualizarVenta() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vistaContado.getTablaProductos().getModel();
        int filaSeleccionada = vistaContado.getTablaProductos().getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vistaContado, "Por favor, seleccione una fila para actualizar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos actuales
        int idVenta = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        String clienteActual = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String productoActual = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
        int cantidadActual = (int) modeloTabla.getValueAt(filaSeleccionada, 3);

        // Solicitar nuevos datos
        String nuevoCliente = JOptionPane.showInputDialog(vistaContado, "Actualizar Cliente:", clienteActual);
        String nuevoProducto = JOptionPane.showInputDialog(vistaContado, "Actualizar Producto:", productoActual);
        String nuevaCantidadStr = JOptionPane.showInputDialog(vistaContado, "Actualizar Cantidad:", cantidadActual);

        if (nuevoCliente == null || nuevoProducto == null || nuevaCantidadStr == null) {
            JOptionPane.showMessageDialog(vistaContado, "Los campos no pueden ser vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int nuevaCantidad;
        try {
            nuevaCantidad = Integer.parseInt(nuevaCantidadStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaContado, "La cantidad debe ser un número entero.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener cliente_id y producto_id
        int clienteId = 0;
        int productoId = 0;
        double precioUnitario = 0;

        ConexionBD db = new ConexionBD();
        String queryIdCliente = "SELECT id FROM Cliente WHERE nombre = ?";
        String queryIdProducto = "SELECT id, precioUnitario FROM Producto WHERE nombre = ?";

        try (Connection conexion = db.conectar(); PreparedStatement stmtCliente = conexion.prepareStatement(queryIdCliente); PreparedStatement stmtProducto = conexion.prepareStatement(queryIdProducto)) {

            stmtCliente.setString(1, nuevoCliente);
            ResultSet rsCliente = stmtCliente.executeQuery();
            if (rsCliente.next()) {
                clienteId = rsCliente.getInt("id");
            } else {
                JOptionPane.showMessageDialog(vistaContado, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            stmtProducto.setString(1, nuevoProducto);
            ResultSet rsProducto = stmtProducto.executeQuery();
            if (rsProducto.next()) {
                productoId = rsProducto.getInt("id");
                precioUnitario = rsProducto.getDouble("precioUnitario");
            } else {
                JOptionPane.showMessageDialog(vistaContado, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vistaContado, "Error al obtener IDs: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Calcular el nuevo subtotal
        double nuevoSubtotal = nuevaCantidad * precioUnitario;

        // Actualizar los datos en la base de datos
        String query = "UPDATE Venta SET cliente_id = ?, producto_id = ?, cantidad = ?, subtotal = ? WHERE id = ?";

        try (Connection conexion = db.conectar(); PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, clienteId);
            statement.setInt(2, productoId);
            statement.setInt(3, nuevaCantidad);
            statement.setDouble(4, nuevoSubtotal);
            statement.setInt(5, idVenta); // ID de la venta a actualizar

            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(vistaContado, "Venta actualizada correctamente.");
            } else {
                JOptionPane.showMessageDialog(vistaContado, "No se pudo actualizar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vistaContado, "Error al actualizar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        listarVentas(); // Actualiza la lista de ventas
    }

    public void eliminarVenta() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vistaContado.getTablaProductos().getModel();
        int filaSeleccionada = vistaContado.getTablaProductos().getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vistaContado, "Por favor, seleccione una fila para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener los datos de la fila seleccionada
        Object clienteObj = modeloTabla.getValueAt(filaSeleccionada, 1); // Columna del cliente
        Object productoObj = modeloTabla.getValueAt(filaSeleccionada, 2); // Columna del producto

        // Convertir los objetos a String (si son del tipo correcto)
        String cliente = clienteObj instanceof String ? (String) clienteObj : clienteObj.toString();
        String producto = productoObj instanceof String ? (String) productoObj : productoObj.toString();

        // Confirmación de eliminación
        int confirmacion = JOptionPane.showConfirmDialog(vistaContado, "¿Está seguro de que desea eliminar esta venta?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.NO_OPTION) {
            return;
        }

        // Eliminar de la base de datos
        ConexionBD db = new ConexionBD();
        String query = "DELETE FROM Venta WHERE cliente_id = (SELECT id FROM Cliente WHERE nombre = ?) "
                + "AND producto_id = (SELECT id FROM Producto WHERE nombre = ?)";

        try (Connection conexion = db.conectar(); PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, cliente);
            statement.setString(2, producto);

            int filasEliminadas = statement.executeUpdate();
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(vistaContado, "Venta eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(vistaContado, "No se pudo eliminar la venta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vistaContado, "Error al eliminar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Actualizar la tabla después de eliminar
        listarVentas();
    }

    public void actualizarResumen() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vistaContado.getTablaProductos().getModel();

        double sumaSubtotal = 0;
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            sumaSubtotal += (double) modeloTabla.getValueAt(i, 4); // Asumiendo que el subtotal está en la columna 3
        }

        // Obtener los datos del cliente y calcular el descuento
        String cliente = modeloContado.getNombreCliente();
        String ruc = modeloContado.getNumeroRUC();
        String fechaActual = java.time.LocalDate.now().toString(); // Puedes formatear la fecha como prefieras
        String horaActual = java.time.LocalTime.now().toString(); // Puedes formatear la hora como prefieras

        double descuento = modeloContado.calculaDescuento(sumaSubtotal);
        double neto = sumaSubtotal - descuento;

        // Crear el resumen
        String resumen = "RESUMEN DE VENTA\n"
                + "---------------------------\n"
                + "CLIENTE : " + cliente + "\n"
                + "RUC : " + ruc + "\n"
                + "FECHA : " + fechaActual + "\n"
                + "HORA : " + horaActual + "\n"
                + "---------------------------\n"
                + "SUBTOTAL : " + sumaSubtotal + "\n"
                + "DESCUENTO : " + descuento + "\n"
                + "NETO : " + neto;

        vistaContado.setTxareaResumen(resumen);
        vistaContado.getLblNetoPago().setText(String.valueOf(neto));
    }

    private void limpiarCampos() {
        vistaContado.setTxtCantidad("");
        vistaContado.getCboProductos().setSelectedIndex(0);
    }
}
