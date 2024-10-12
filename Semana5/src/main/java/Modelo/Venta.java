package Modelo;

import java.time.LocalDateTime;


/**
 *
 * @author diego
 */
public class Venta {
    private String nombreCliente;
    private String numeroRUC;
    private LocalDateTime fechaHoraRegistro;
    private String nombreProducto;
    private int cantidadComprada;
    private double precioUnitario;

    public Venta(String nombreCliente, String numeroRUC, String nombreProducto, int cantidadComprada) {
        this.nombreCliente = nombreCliente;
        this.numeroRUC = numeroRUC;
        this.fechaHoraRegistro = LocalDateTime.now();
        this.nombreProducto = nombreProducto;
        this.cantidadComprada = cantidadComprada;
        this.precioUnitario = asignaPrecio(nombreProducto);
    }
    
    public double asignaPrecio(String nombreProducto) {
        switch (nombreProducto) {
            case "Lavadora":
                return 1500.00;
            case "Refrigeradora":
                return 3500.00;
            case "Licuadora":
                return 500.00;
            case "Extractor":
                return 150.00;
            case "Radiograbadora":
                return 750.00;
            case "DVD":
                return 100.00;
            case "Blue Ray":
                return 250.00;
            default:
                return 0.00;
        }
    }
    
    // Método para calcular el subtotal
    public double calcularSubtotal() {
        return precioUnitario * cantidadComprada;
    }

    // Getters y Setters (opcional)
    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getNumeroRUC() {
        return numeroRUC;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }
}
