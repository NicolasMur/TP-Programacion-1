package juego;

import entorno.Entorno;

public class Piso {
	Bloque[] bloques;
	double y;
	
	public Piso(double y) {
		Bloque testigo = new Bloque(0, 0);
		bloques =  new Bloque [(int) (800 / testigo.ancho) + 1];  //esto modifica los bloques vistos en la pantalla
		this.y = y;
		
		for(int i = 0; i < bloques.length; i++) {
			bloques[i] = new Bloque((i+0.5)* testigo.ancho, y);  //LA POSICION DE LOS BLOQUES, SI SE AUMENTA O DECRECE SE MUEVE A LOS COSTADOS DE LA PANTALLA
		}
		
	}
	
	public void mostrar(Entorno e) {
		for(int i = 0; i < bloques.length; i++) {
			if(bloques[i] != null) {
				bloques[i].mostrar(e);
			}
		}
	}

}
