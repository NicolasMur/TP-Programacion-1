package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Fondo {
    // Variables de instancia
	double x,y,escala;
    Image img;

    public Fondo(double x, double y) {
        this.x = x;
        this.y = y;

        img = Herramientas.cargarImagen("entidades/paisaje.gif");
        escala = 3; // Cambiado el valor de escala a 1
    }

    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(img, this.x, this.y, 0, escala);
    }
}
