package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bala {
	
	double x, y, escala, alto, ancho, velocidad;
	boolean direccion; // true = left
	Image spriteIzq;
	Image spriteDer;
	int contBala;

	public Bala(double x, double y, boolean direccion) {
		this.x = x;
		this.y = y;
		spriteIzq = Herramientas.cargarImagen("balaIzq.png");
		spriteDer = Herramientas.cargarImagen("Bala.png");
		this.direccion = direccion;
		escala = 0.1;
		alto = spriteIzq.getHeight(null) * escala;
		ancho = spriteIzq.getWidth(null) * escala;
		this.velocidad = 8;
		contBala = 0;
		System.out.println("el alto es: " + alto + " el ancho es: " + ancho);
	}

	public void mostrar(Entorno e) {
		if (direccion) {
			e.dibujarImagen(spriteIzq, x, y, 0, escala);
		} else {
			e.dibujarImagen(spriteDer, x, y, 0, escala);
		}
	}

	public void moverse() {
		if (this.direccion) {
			this.x -= velocidad;
		} else {
			this.x += velocidad;
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
