package entidades;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Bartolome {
	public double x;
	public double y;
	Image spriteIzq;
	Image spriteDer;
	public boolean dir; // false = Izq
	public boolean estaApoyado;
	public boolean estaSaltando; //false = no esta saltando
	public double escala;
	public double alto;
	public double ancho;
	public int contadorSalto;
	public Rectangle rectangulo;

	public Bartolome(double x, double y) {
		this.x = x;
		this.y = y;
		spriteIzq = Herramientas.cargarImagen("entidades/bodyIzq.png");
		spriteDer = Herramientas.cargarImagen("entidades/body.png");
		dir = false;
		contadorSalto = 0;
		estaApoyado = false;
		estaSaltando = false;
		escala = 0.2;
		alto = spriteIzq.getHeight(null) * escala;
		ancho = spriteIzq.getWidth(null) * escala;
//		rectangulo = new Rectangle();     
//		rectangulo.x = 40;
//		rectangulo.y = 0;
//		rectangulo.height = 54;
//		rectangulo.width = 60;
		
		
	}

	public void mostrar(Entorno e) {
		if (dir) {
			e.dibujarImagen(spriteDer, x, y, 0, escala);
		} else {
			e.dibujarImagen(spriteIzq, x, y, 0, escala);
		}
	}

	public void moverse(boolean dirMov) {
		if ( estaApoyado || !estaApoyado || estaSaltando) {
			if (dirMov) {
				this.x += 1;
			} else {
				this.x -= 1;
			}
			this.dir = dirMov;
		}
	}
	
	
	public void movVertical() {

		if (!estaApoyado && !estaSaltando) {
			this.y += 3.2;
		}
		if(estaSaltando) {
			this.y -= 6.5;
			contadorSalto++;
		}
		if(contadorSalto == 20) {
			contadorSalto = 0;
			estaSaltando = false;
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
