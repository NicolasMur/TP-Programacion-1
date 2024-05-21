package juego;

import entorno.Entorno;
import java.util.Random;

public class Enemigos {
	Bicho[] enemigos;
    Random random = new Random();
    int min = 100;
    int max = 400;
    int numRandom = 0;
	
	public Enemigos (int cantPisos, double yPiso) {
		enemigos = new Bicho[cantPisos * 2];
		for(int i = 0; i < enemigos.length/2; i++) {
			int numRandom = random.nextInt(max - min);
			if (i > 0) {
				enemigos[i] = new Bicho(50 + numRandom, yPiso - (120*i)); //POSICION X, Y)
				enemigos[i+5] = new Bicho(750 - numRandom, yPiso - (120*i)); //POSICION X, Y)
			}
			else {
				enemigos[i] = new Bicho(50, yPiso - (120*i)); //POSICION X, Y)
				enemigos[i+5] = new Bicho(750, yPiso - (120*i)); //POSICION X, Y)	
			}
		}
	}
	
	public void mostrar(Entorno e) {
		for(int i = 0; i < enemigos.length; i++) {
			if(enemigos[i] != null) {
				enemigos[i].mostrar(e);
			}
		}
	}
	
}