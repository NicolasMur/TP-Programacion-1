package juego;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;


public class Bicho {
	Image bichoLeft;
	Image bichoRight;
	double x, y;
	double escala;
	double alto;
	double ancho;
	boolean direccion; //true = left
	boolean estaApoyado; //true = esta apoyado
	boolean estaChocandoLeft; //true = esta chocando la izquierda de bart
	boolean estaChocandoRight; //true = esta chocando la derecha de bart
	boolean ultimoChoque; //true = izquierda;

	public Bicho (double x, double y) {
		this.x = x;
		this.y = y;
		bichoLeft = Herramientas.cargarImagen("bichoLeft.gif");
		bichoRight = Herramientas.cargarImagen("bichoRight.gif");
		direccion = true;
		estaApoyado = false;
		estaChocandoLeft = false;
		estaChocandoRight = false;
		ultimoChoque = false;
		escala = 0.5;
		alto = bichoLeft.getHeight(null) * escala;
		ancho = bichoRight.getWidth(null) * escala;
		System.out.println("el alto es: " + alto + " el ancho es: " + ancho);
		

	}
	
	public void caer() {
		if(!estaApoyado) {
			this.y += 3;
		}
	}
	
	public void mostrar(Entorno e) {
		if (direccion) {
			e.dibujarImagen(bichoLeft, x, y, 0, escala);
		}
		else {
			e.dibujarImagen(bichoRight, x, y, 0, escala);
		}
	}
	
	public void movimiento() {
		if(estaApoyado && !estaChocandoLeft && !ultimoChoque) {
			x -= 1;
			direccion = true;
		}
		if(estaApoyado && !estaChocandoRight && ultimoChoque) {
			x += 1;
			direccion = false;
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
	
