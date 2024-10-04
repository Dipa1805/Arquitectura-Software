package Modelo;

/**
 *
 * @author diego
 */
public class Contado extends Venta {

    private int N;

    public Contado(String nombreCliente, String numeroRUC, String nombreProducto, int cantidadComprada, int N) {
        super(nombreCliente, numeroRUC, nombreProducto, cantidadComprada); 
        this.N = N;
    }

    public int getN() {
        return N;
    }

    public void setN(int N) {
        this.N = N;
    }

    public double calculaDescuento(double subtotal) {
        double descuento = 0.0;
        if (subtotal < 1000) {
            descuento = subtotal * 0.05; 
        } else if (subtotal >= 1000 && subtotal <= 3000) {
            descuento = subtotal * 0.08;
        } else if (subtotal > 3000) {
            descuento = subtotal * 0.12;
        }
        return descuento;
    }
    
    public double calcularNeto() {
        double subtotal = calcularSubtotal();
        double descuento = calculaDescuento(subtotal);
        return subtotal - descuento;
    }

}
