package Controlador;

import Modelo.Contado;
import Modelo.Credito;
import Vista.CCredito;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diego
 */
public class ControladorCCredito {

    private Credito modeloCredito;
    private CCredito vistaCredito;

    public ControladorCCredito(Credito modeloCredito, CCredito vistaCredito) {
        this.modeloCredito = modeloCredito;
        this.vistaCredito = vistaCredito;
    }

    public void adquirirProducto() {
        // Obtener los datos desde la vista
        String cantidad = vistaCredito.getTxtCantidad().getText();
        String cliente = vistaCredito.getTxtCliente().getText();
        String ruc = vistaCredito.getTxtRuc().getText();
        String producto = vistaCredito.getSelectedProducto();

        // Asegurarse de que no haya campos vacíos
        if (cantidad.isEmpty() || cliente.isEmpty() || ruc.isEmpty() || producto == null) {
            JOptionPane.showMessageDialog(vistaCredito, "Por favor, complete todos los campos.");
            return;
        }

        int cantidadComprada;
        try {
            cantidadComprada = Integer.parseInt(cantidad);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaCredito, "La cantidad debe ser un número entero.");
            return;
        }

        modeloCredito = new Credito(cliente, ruc, producto, cantidadComprada, 0, 0);

        double precioUnitario = modeloCredito.getPrecioUnitario();
        double subtotal = modeloCredito.calcularSubtotal();
        DefaultTableModel modeloTabla = (DefaultTableModel) vistaCredito.getTablaProductos().getModel();

        int numeroFila = modeloTabla.getRowCount() + 1;

        // Añadir la venta a la tabla
        modeloTabla.addRow(new Object[]{
            numeroFila,
            modeloCredito.getNombreProducto(),
            modeloCredito.getCantidadComprada(),
            precioUnitario,
            subtotal
        });

        limpiarCampos();
    }

    public void mostrarResumen() {
        try {
            // 1. Obtener el número de letras seleccionadas
            int partes = Integer.parseInt(vistaCredito.getCboLetras().getSelectedItem().toString());

            // 2. Calcular el subtotal (suma de todos los productos agregados)
            double subtotal = 0.0;

            DefaultTableModel modeloTabla = (DefaultTableModel) vistaCredito.getTablaProductos().getModel();
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                subtotal += Double.parseDouble(modeloTabla.getValueAt(i, 4).toString());
            }

            // 3. Crear un objeto Credito para realizar los cálculos
            Credito credito = new Credito("", "", "", 0, 0, partes); // Solo necesitamos las letras

            // 4. Calcular el monto mensual a partir del subtotal
            double montoMensual = credito.calculaMontoMensual(subtotal);

            // 5. Actualizar la tablaResumen con el subtotal, letras y monto mensual
            DefaultTableModel modeloResumen = (DefaultTableModel) vistaCredito.getTablaResumen().getModel();
            modeloResumen.setRowCount(0); // Limpiar la tabla antes de agregar nuevos datos
            modeloResumen.addRow(new Object[]{"Subtotal", String.format("%.2f", subtotal)});
            modeloResumen.addRow(new Object[]{"Letras", partes});
            modeloResumen.addRow(new Object[]{"Monto mensual", String.format("%.2f", montoMensual)});

            // 6. Calcular el monto neto a pagar (incluyendo descuentos e intereses)
            double descuento = modeloCredito.calculaDescuento(subtotal);
            double netoAPagar = subtotal - descuento; 

            // 7. Actualizar el lblNetoPago con el monto total a pagar
            vistaCredito.getLblNetoPago().setText(String.valueOf(netoAPagar));

        } catch (Exception e) {
            // Si ocurre un error, muestra un mensaje más informativo
            JOptionPane.showMessageDialog(vistaCredito, "Ocurrió un error al calcular el resumen: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        vistaCredito.setTxtCantidad("");
        vistaCredito.getCboProductos().setSelectedIndex(0);
    }
}
