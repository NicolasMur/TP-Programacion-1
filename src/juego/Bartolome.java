package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bartolome {
	Image spriteLeft;
	Image spriteRight;
	Image spriteRunningLeft;
	Image spriteRunningRight;
	Image spriteShootLeft;
	Image spriteShootRight;
	double x, y;
	double escala;
	double alto;
	double ancho;
	boolean direccion; // true = left
	boolean estaApoyado; //true = esta apoyado
	boolean estaSaltando; //true = esta saltando
	boolean estaChocandoLeft; //true = esta chocando la izquierda de bart
	boolean estaChocandoRight; //true = esta chocando la derecha de bart
	boolean estaDisparandoLeft; //true = esta disparando
	boolean estaDisparandoRight; //true = esta disparando
	boolean estaCorriendoLeft; //true = esta corriendo left
	boolean estaCorriendoRight; //true = esta corriendo right
	int contadorSalto;

	public Bartolome(double x, double y) {
		this.x = x;
		this.y = y;
		spriteLeft = Herramientas.cargarImagen("restLeft.gif");
		spriteRight = Herramientas.cargarImagen("restRight.gif");
		spriteRunningLeft = Herramientas.cargarImagen("runningLeft.gif");
		spriteRunningRight = Herramientas.cargarImagen("runningRight.gif");
		spriteShootLeft = Herramientas.cargarImagen("shootLeft.gif");
		spriteShootRight = Herramientas.cargarImagen("shootRight.gif");
		direccion = false;
		estaApoyado = false;
		estaSaltando = false;
		estaChocandoLeft = false;
		estaChocandoRight = false;
		estaDisparandoLeft = false;
		estaDisparandoRight = false;
		contadorSalto = 0;
		escala = 0.8;
		alto = spriteRunningLeft.getHeight(null) * escala;
		ancho = spriteRunningRight.getWidth(null) * escala;
		System.out.println("el alto es: " + alto + " el ancho es: " + ancho);
		
	}

	public void mostrar(Entorno e) {
		if(direccion && !estaDisparandoLeft && !estaCorriendoLeft) {
			e.dibujarImagen(spriteLeft, x, y, 0, escala);
		}
		if(!direccion && !estaDisparandoRight && !estaCorriendoRight) {
			e.dibujarImagen(spriteRight, x, y, 0, escala);
		}
		if(direccion && !estaDisparandoLeft && estaCorriendoLeft) {
			e.dibujarImagen(spriteRunningLeft, x, y, 0, escala);
		}
		if(!direccion && !estaDisparandoRight && estaCorriendoRight) {
			e.dibujarImagen(spriteRunningRight, x, y, 0, escala);
		}
		if(estaDisparandoLeft) {
			e.dibujarImagen(spriteShootLeft, x, y, 0, escala);
		}
		if(estaDisparandoRight) {
			e.dibujarImagen(spriteShootRight, x, y, 0, escala);
		}
	}

	public void moverseLeft(boolean avanzo) {
		if (avanzo && (estaApoyado || (!estaApoyado && !estaChocandoLeft))) {
			this.x -= 1;
			this.direccion = true;
		}
	}
	
	public void moverseRight(boolean avanzo) {
		if (avanzo && (estaApoyado || (!estaApoyado && !estaChocandoRight))) {
			this.x += 1;
			this.direccion = false;
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
