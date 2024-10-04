package Controlador;

import Modelo.Contado;
import Vista.CContado;
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
        limpiarCampos();
        actualizarResumen();
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
