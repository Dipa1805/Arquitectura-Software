package Modelo;

/**
 *
 * @author diego
 */
public class Credito extends Venta {

    private int cantidadTotal; // Renombrado de X a cantidadTotal
    private int letras;

    public Credito(String nombreCliente, String numeroRUC, String nombreProducto, int cantidadComprada, int cantidadTotal, int letras) {
        super(nombreCliente, numeroRUC, nombreProducto, cantidadComprada);
        this.cantidadTotal = cantidadTotal;
        this.letras = letras;
    }

    // Getters y Setters
    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public int getLetras() {
        return letras;
    }

    public void setLetras(int letras) {
        this.letras = letras;
    }

    // Método para calcular el interés basado en el número de letras
    public double calculaMontoInteres(double subtotal) {
        double interes = 0.0;
        if (letras <= 6) {
            interes = subtotal * 0.05; // 5% de interés
        } else if (letras <= 12) {
            interes = subtotal * 0.10; // 10% de interés
        } else {
            interes = subtotal * 0.15; // 15% de interés
        }
        return interes;
    }

    // Método para calcular el monto mensual
    public double calculaMontoMensual(double subtotal) {
        double totalConInteres = subtotal + calculaMontoInteres(subtotal);
        return totalConInteres / letras; // Monto mensual
    }

    // Método para calcular el descuento en base al subtotal
    public double calculaDescuento(double subtotal) {
        double descuento = 0.0;
        if (subtotal < 1000) {
            descuento = subtotal * 0.03; // 3% de descuento
        } else if (subtotal >= 1000 && subtotal < 3000) {
            descuento = subtotal * 0.05; // 5% de descuento
        } else {
            descuento = subtotal * 0.08; // 8% de descuento
        }
        return descuento;
    }

    // Método para calcular el total neto a pagar
    public double calcularNeto() {
        double subtotal = calcularSubtotal();
        double descuento = calculaDescuento(subtotal);
        return subtotal - descuento;
    }
}
