package juego;

import java.awt.Color;

import bloques.Bloque;
import entidades.Bala;
import entidades.Bartolome;
import entidades.Bicho;
import entidades.Piso;
import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Bartolome bart;
	Piso[] p;
	Bala bala;
	Bicho bicho;
	Fondo fondo;
	
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "El maestro fuego", 800, 600);
		
		
		fondo= new Fondo(400,300);
		bart = new Bartolome(400, 540);
		bicho= new Bicho (550,190);
		
		// LINEAS DE BLOQUES 
		p = new Piso[5];
		
		for(int i = 0; i < p.length; i++) {
			p[i] = new Piso(120 + i * (entorno.alto() / p.length));
		}
		
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo
		if(entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			bart.moverse(true);
			
		}
		

		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			bart.moverse(false);
		}
		
		
		if (entorno.estaPresionada('x') && bart.estaApoyado){
			bart.estaSaltando = true;	
		}
		
		if(bala == null && entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
			bala = new Bala(bart.x, bart.y, bart.dir);
			 
		}
		
		fondo.dibujarse(this.entorno);
		bart.mostrar(entorno);
		bicho.mostrarBicho(entorno);
		bart.movVertical();

		
		if(bala != null) {
			bala.mostrar(entorno);
			bala.moverse();
		}
		
		for(int i = 0; i < p.length; i++) {
			p[i].mostrar(entorno);
		}
		
		//CANTIDAD DE BALAS EN PANTALLA
		if( bala != null && (bala.x < -0.1 * entorno.ancho() 
		|| bala.x > entorno.ancho() * 1.1)) {
			bala = null;
			
		}
		
		if(detectarApoyo(bart, p)) {
			bart.estaApoyado = true;
		}
		else {
			bart.estaApoyado = false;
		}
		
		if(detectarColision (bart, p)) {
			System.out.println("Colision encontrada");
			bart.estaSaltando = false;
			bart.contadorSalto = 0;
		}
		
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
	
	public boolean detectarApoyo(Bartolome ba, Bloque bl) {
		return Math.abs((ba.getPiso() - bl.getTecho())) < 2 && 
				(ba.getIzquierdo() < (bl.getDerecho())) &&
				(ba.getDerecho() > (bl.getIzquierdo()));		
	}
	
	

	
	public boolean detectarApoyo(Bartolome ba, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i] != null && detectarApoyo(ba, pi.bloques[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean detectarApoyo(Bartolome ba, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarApoyo(ba, pisos[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean detectarColision(Bartolome ba, Bloque bl) {
		return Math.abs((ba.getTecho() - bl.getPiso())) < 2 &&  
				(ba.getIzquierdo() < (bl.getDerecho())) &&
				(ba.getDerecho() > (bl.getIzquierdo()));		
	}
	// BLOQUE ORO. 
	public boolean detectarColision(Bartolome ba, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i] != null && detectarColision(ba, pi.bloques[i])) {
				if(pi.bloques[i].rompible) {
					pi.bloques[i] = null; 
				}
				return true; //ESTO ROMPE EL BLOQUE
			}
		}
		
		return false; //ACA COLISIONA CON UN BLOQUE DE ORO
	}
	
	public boolean detectarColision(Bartolome ba, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarColision(ba, pisos[i])) {
				return true;
			}
		}
		
		return false;
	}

}
