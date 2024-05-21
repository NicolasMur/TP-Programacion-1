package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Bartolome bart;
	Piso[] p;
	Bala bala;
	Fondo fondo;
	Enemigos enemigos;
	int contBala = 0;
	
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "El maestro fuego", 800, 600);
		
		fondo = new Fondo(400,300);
		bart  = new Bartolome(400, 540);
		enemigos = new Enemigos(5, entorno.alto() - 45);
		
		p = new Piso[5]; // LINEAS DE BLOQUES 
		for(int i = 0; i < p.length; i++) {
			p[i] = new Piso(120 + i * (entorno.alto() / p.length));
		}
		
		this.entorno.iniciar(); // Inicia el juego!
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	
	public void tick() { // Procesamiento de un instante de tiempo
		
//FONDO-----------------------------------------------------------------------------------------------------------------------------------
		fondo.dibujarse(this.entorno);
		
//PISO-----------------------------------------------------------------------------------------------------------------------------------		
		for(int i = 0; i < p.length; i++) {
			p[i].mostrar(entorno);
		}

//BART-----------------------------------------------------------------------------------------------------------------------------------
		bart.mostrar(entorno);
		bart.movVertical();
		
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			bart.moverseLeft(true);
			bart.estaCorriendoLeft = true;
		}
		else {
			bart.estaCorriendoLeft = false;
		}
		
		if(entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			bart.moverseRight(true);
			bart.estaCorriendoRight = true;
		}
		else {
			bart.estaCorriendoRight = false;
		}
		
		if (entorno.estaPresionada('x') && bart.estaApoyado){
			bart.estaSaltando = true;	
		}
		
		if(detectarColisionTopBart(bart, p)) {
			//System.out.println("Colision top encontrada");
			bart.estaSaltando = false;
			bart.contadorSalto = 0;
		}
		
		if(detectarColisionBottomBart(bart, p)) {
			//System.out.println("Colision bottom encontrada");
			bart.estaApoyado = true;
		}
		else {
			bart.estaApoyado = false;
		}
		
		if(detectarColisionLeftBart(bart, p)) {
			//System.out.println("Colision left encontrada");
			bart.estaChocandoLeft = true;
		}
		else {
			bart.estaChocandoLeft = false;
		}
		
		if(detectarColisionRightBart(bart, p)) {
			//System.out.println("Colision right encontrada");
			bart.estaChocandoRight = true;
		}
		else {
			bart.estaChocandoRight = false;
		}

//BALA-----------------------------------------------------------------------------------------------------------------------------------
		if (contBala > -5) {
			contBala --;
		}
		
		if(bala == null && entorno.estaPresionada(entorno.TECLA_ESPACIO) && contBala <= 0) {
			bala = new Bala(bart.x, bart.y, bart.direccion);
			contBala = 15;
//			System.out.println("bala");
		}
		else {
			bart.estaDisparandoLeft = false;
			bart.estaDisparandoRight = false;
		}
		
		if(bala != null && (((bala.x < -0.1 * entorno.ancho() 
		|| bala.x > entorno.ancho() * 1.1)) || detectarColisionBala(bala, enemigos))) {
			bala = null;	
		}
			
		if(bala != null) {
			if(bart.direccion) {
				bart.estaDisparandoLeft = true;
			}
			
			if(!bart.direccion) {
			bart.estaDisparandoRight = true;
			}
			
			bala.mostrar(entorno);
			bala.moverse();
		}
		
//ENEMIGOS---------------------------------------------------------------------------------------------------------------
		enemigos.mostrar(entorno); //MUESTRA LOS ENEMIGOS
	
//BICHO------------------------------------------------------------------------------------------------------------------------------
		for(int i = 0; i < enemigos.enemigos.length; i++) { //ITERA BICHO POR BICHO DE LA LISTA DE ENEMIGOS
			Bicho bicho = enemigos.enemigos[i]; //RENOMBRO PARA HACERLO MAS CORTO Y FACIL
			
			if(bicho != null) {
				if(detectarColisionBottomBicho(p, bicho)) { //DETECTA APOYO DE CADA TORTUGA
					bicho.estaApoyado = true;
				}
				else {
					bicho.estaApoyado = false;
				}
				
				if(detectarColisionLeftBicho(bicho, entorno) || //HACE QUE BICHO DE BICHO CAMBIE DE DIRECCION
					detectarColisionLeftBichoConBicho(bicho, enemigos, entorno)) {
					bicho.estaChocandoLeft = true; //DETECTA COLISION A LA DERECHA IZQUIERDA
					bicho.ultimoChoque = true; //SWITCH DIRECCION
				}
				else {
					bicho.estaChocandoLeft = false;
				}
				
				if(detectarColisionRightBicho(bicho, entorno) || //HACE QUE BICHO DE BICHO CAMBIE DE DIRECCION
					detectarColisionRightBichoConBicho(bicho, enemigos, entorno)) {
					bicho.estaChocandoRight = true; //DETECTA COLISION A LA DERECHA
					bicho.ultimoChoque = false; //SWITCH DIRECCION
				}
				else {
					bicho.estaChocandoRight = false;
				}
				
				bicho.caer();
				bicho.movimiento();
			}
		}
	}
	
//-----------------------------------------------------------------------------------------------------------------------------------	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
	
	public boolean detectarColisionTopBart(Bartolome ba, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) { //ITERA POR CADA UNO DE LOS PISOS
			Piso pi = pisos [i]; //RENOMBRO PARA HACERLO MAS CORTO Y FACIL
			for(int o = 0; o < pi.bloques.length; o++) { //ITERA POR CADA UNO DE LOS BLOQUES
				Bloque bl = pi.bloques[o]; //RENOMBRO PARA HACERLO MAS CORTO Y FACIL
				if(bl != null && //COMPRUEBA SI NO SON NULL
				ba.getTecho() < bl.getPiso() && //COMPRUEBA HITBOX
				ba.getTecho() > bl.getTecho() && //...
				ba.getIzquierdo() < bl.getDerecho() - 5 && //...
				ba.getDerecho() > bl.getIzquierdo() + 5) { //...
					if(bl.rompible && ba.estaSaltando) { //COMPROBACION DE ROMPIBILIDAD DE BLOQUE Y QUE ESTE SALTANDO
						pi.bloques[o] = null; //ESTO ROMPE EL BLOQUE
					}
					return true; //HACE QUE BART DEJE DE SALTAR CUANDO ROMPE EL BLOQUE
				}
			}
		}
		return false; //NO ESTA CHOCANDO
	}
	
	public boolean detectarColisionBottomBart(Bartolome ba, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) { //ITERA POR CADA UNO DE LOS PISOS
			Piso pi = pisos [i]; //RENOMBRO PARA HACERLO MAS CORTO Y FACIL
			for(int o = 0; o < pi.bloques.length; o++) { //ITERA POR CADA UNO DE LOS BLOQUES
				Bloque bl = pi.bloques[o]; //RENOMBRO PARA HACERLO MAS CORTO Y FACIL
				if(bl != null && //COMPRUEBA SI NO SON NULL
				Math.abs(ba.getPiso() - bl.getTecho()) < 2 && //COMPRUEBA HITBOX
				ba.getIzquierdo() < bl.getDerecho()  - 5 && //...
				ba.getDerecho() > bl.getIzquierdo() + 5) { //...
					return true; //ESTA APOYADO, ENTONCES NO CAE
				}
			}
		}
		return false; //NO ESTA APOYADO, ENTONCES CAE
	}
	
	public boolean detectarColisionLeftBart(Bartolome ba, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) { //FROM PISO...
			Piso pi = pisos [i]; //TO PISO
			for(int o = 0; o < pi.bloques.length; o++) { //FROM BLOQUES DEL PISO...
				Bloque bl = pi.bloques[o]; //TO BLOQUE DEL PISO
				if (bl != null && ba.getIzquierdo() < bl.getDerecho() +0 && //COMPROBACION DE HITBOX ENTRE BLOQUE Y BART...
				ba.getIzquierdo() > bl.getIzquierdo() -0 && //...
				ba.getTecho() < bl.getPiso() -5 && //...
				ba.getPiso() > bl.getTecho() +5) { //...
					return true; //ESTA CHOCANDO
				}
			}
		}
		return false; //NO ESTA CHOCANDO
	}
	
	public boolean detectarColisionRightBart(Bartolome ba, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) { //FROM PISO...
			Piso pi = pisos [i]; //TO PISO
			for(int o = 0; o < pi.bloques.length; o++) { //FROM BLOQUES...
				Bloque bl = pi.bloques[o]; //TO BLOQUE
				if (bl != null && ba.getDerecho() > bl.getIzquierdo() -0 && //COMPROBACION DE HITBOX ENTRE BLOQUE Y BART...
				ba.getDerecho() < bl.getDerecho() +0 && //...
				ba.getTecho() < bl.getPiso() -5 && //...
				ba.getPiso() > bl.getTecho() +5) { //...
					return true; //ESTA CHOCANDO
				}
			}
		}
		return false; //NO ESTA CHOCANDO
	}
	
	public boolean detectarColisionBottomBicho(Piso[] pisos, Bicho bi) {
			for(int o = 0; o < pisos.length; o++) { //FROM PISO...
				Piso pi = pisos [o]; //TO PISO
				for(int u = 0; u < pi.bloques.length; u++) { //FROM BLOQUES DEL PISO...
					Bloque bl = pi.bloques[u]; //TO BLOQUE DEL PISO
					if(pi.bloques[u] != null && bi != null && //COMPROBACION DE HITBOX ENTRE BLOQUE Y BART...
					Math.abs(bi.getPiso() - bl.getTecho()) < 2 && //...
					bi.getIzquierdo() < bl.getDerecho() && //...
					bi.getDerecho() > bl.getIzquierdo()) { //...
						return true; //ESTA APOYADO, ENTONCES NO CAE
					}
				}
			}
		return false; //NO ESTA APOYADO, ENTONCES CAE
	}
	
	public boolean detectarColisionLeftBichoConBicho(Bicho bi, Enemigos ene, Entorno e) {
		for(int i = 0; i < ene.enemigos.length; i++) { //ITERA BICHO POR BICHO DE ENEMIGO
			Bicho bicho = ene.enemigos[i]; //RENOMBRO PARA HACERLO MAS CORTO Y FACIL
			if((bi != null && bicho != null) && //CHEQUEA QUE EXISTE bi y bicho (bi = BICHO DE BICHO Y bicho = BICHO DE ENEMEMIGOS)
			(Math.abs(bi.getIzquierdo() - bicho.getDerecho()) < 2 && //CHEQUEA SI bi LEFT COLISIONA CON bicho RIGHT
			Math.abs(bi.y - bicho.y) < 2)) { //CHEQUEA QUE AMBOS ESTEN EN UN MISMO PISO (Y)
				bicho.estaChocandoRight = true; //DETECTA LA COLISION A LA DERECHA DE bicho
				bicho.ultimoChoque = false; //SWITCH DIRECCION DE bicho
				return true;  //HAY COLISION
			}
		}
		return false; //NO HAY COLISION
	}
	
	public boolean detectarColisionRightBichoConBicho(Bicho bi, Enemigos ene, Entorno e) {
		for(int i = 0; i < ene.enemigos.length; i++) { //ITERA BICHO POR BICHO DE ENEMIGO
			Bicho bicho = ene.enemigos[i]; //RENOMBRO PARA HACERLO MAS CORTO Y FACIL
			if(bi != null && bicho != null && //CHEQUEA QUE EXISTE bi y bicho (bi = BICHO DE BICHO Y bicho = BICHO DE ENEMEMIGOS)
			(Math.abs(bi.getDerecho() - bicho.getIzquierdo()) < 2 && //CHEQUEA SI bi RIGHT COLISIONA CON bicho LEFT
			Math.abs(bi.y - bicho.y) < 2)) { //CHEQUEA QUE AMBOS ESTEN EN UN MISMO PISO (Y)
				bicho.estaChocandoLeft = true; //DETECTA LA COLISION A LA IZQUIERDA DE bicho
				bicho.ultimoChoque = false; //SWITCH DIRECCION DE bicho
				return true; //HAY COLISION
			}
		}
		return false; //NO HAY COLISION
	}
	
	public boolean detectarColisionLeftBicho(Bicho bi, Entorno e) {;
		if(bi != null && bi.getIzquierdo() <= e.ancho() * 0) { //CHEQUEA QUE BICHO EXISTA Y QUE HAYA COLISION CON EL BORDE iZQUIERDO DEL JUEGO (x = 0)
			return true; //HAY COLISION
		}
		return false; //NO HAY COLISION
	}
	
	public boolean detectarColisionRightBicho(Bicho bi, Entorno e) {;
		if(bi != null && bi.getDerecho() >= e.ancho() * 1) { //CHEQUEA QUE BICHO EXISTA Y QUE HAYA COLISION CON EL BORDE DERECHO DEL JUEGO (x = 800)
			return true; //HAY COLISION
		}
		return false; //NO HAY COLISION
	}
	
	public boolean detectarColisionBala(Bala bala, Enemigos ene) {
		for(int i = 0; i < ene.enemigos.length; i++) { //ITERA BICHO POR BICHO DE ENEMIGO
			Bicho bi = ene.enemigos[i]; //RENOMBRO PARA HACERLO MAS CORTO Y FACIL
			if(bala != null && bi != null && //CHEQUEA QUE EXISTE BICHO DE BICHO Y BICHO DE ENEMIGOS
			bala.getIzquierdo() < bi.getDerecho() && //CHEQUEA COLISION HITBOX ENTRE BALA Y BICHO...
			bala.getDerecho() > bi.getIzquierdo() && //...
			bala.getTecho() > bi.getTecho() && //...
			bala.getPiso() < bi.getPiso()) { //...
				this.bala = null;
				ene.enemigos[i] = null; //MATA BICHO
				return true; //ENCONTRO COLISION
			}
		}
		return false; //NO ENCONTRO COLISION
	}
	
//	public boolean pisoMax(Bartolome ba, Enemigos ene, Piso[] pisos) {
//		for(int i = 0; i<pisos.length;i++) {
//			if()
//		}
//	}
	
}
 