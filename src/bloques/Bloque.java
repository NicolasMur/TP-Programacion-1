package bloques;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {
	double x, y, alto;
	public double ancho;
	double escala;
	Image sprite;
	public boolean rompible; //true = rompible
	public Rectangle bloque;
	
	public Bloque(double x, double y) {
		this.x = x;
		this.y = y;
		rompible = true;
		if(Math.random() > 0.5) {
			rompible = false;
		}
		if(rompible) {
			sprite = Herramientas.cargarImagen("entidades/bloque.png");
		}
		else {
			sprite = Herramientas.cargarImagen("entidades/bloqueOro.png");
		}
		
		escala = 0.1;
		alto = sprite.getHeight(null)*escala;
		ancho = sprite.getWidth(null)*escala;
		System.out.println("el alto es: " + alto + " el ancho es: " + ancho);
//		bloque = new Rectangle();
//		bloque.x = 38;
//		bloque.y = 38;
//		bloque.height = 38;
//		bloque.width = 38;
		
	}
	
	public void mostrar(Entorno e) {
		e.dibujarImagen(sprite, x, y, 0, escala);
	}
	
	public double getTecho(){
		return y - alto/2;
	}
	
	public double getPiso(){
		return y + alto/2;
	}
	
	public double getDerecho(){
		return x + ancho/2;
	}
	
	public double getIzquierdo(){
		return x - ancho/2;
	}

}
