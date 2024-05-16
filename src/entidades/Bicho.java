package entidades;
import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;


public class Bicho {
	double x, y;
	double escala;
	double alto;
	double ancho;
	Image bichoIzq;
	Image bichoDer;
	boolean direccion; // false = Izq // DIRECCION 
	boolean estaApoyado;


	public Bicho (double x, double y) {
		this.x = x;
		this.y = y;
		bichoIzq = Herramientas.cargarImagen("entidades/bicho.gif");
		bichoDer = Herramientas.cargarImagen("entidades/bicho.gif");
		direccion = false;
		estaApoyado = false;
		escala = 0.6;
		alto = bichoIzq.getHeight(null) * escala;
		ancho = bichoDer.getWidth(null) * escala;
		

	}
	
	
	public void mostrarBicho(Entorno e) {
		if (direccion) {
			e.dibujarImagen(bichoDer, x, y, 0, escala);
			
		} else {
			e.dibujarImagen(bichoIzq, x, y, 0, escala);
		}
		
	}
	
		
	public double getTecho() {
		return y - alto / 2;
	}

	public double getPiso() {
		return y + alto / 2;
	}

	public double getDerecho() {
		return x + ancho / 2;
	}

	public double getIzquierdo() {
		return x - ancho / 2;
	}

	
}
	
