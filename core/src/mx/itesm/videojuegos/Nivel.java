package mx.itesm.videojuegos;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Nivel {

    private int idNivel;
    private int score;
    private Personaje personaje;
    private Enemigo enemigo;
    private Sprite sprite;
    private String fondos;
    private Music musica;
    private Sound efecto;
    public int coordenadasDano;



    private void generarPersonaje() {

    }
    private void generarEnemigos(){

    }

    public void generarZonaDeDaño(){
        float rangoDeAtaque = 0;
        if(enemigo.estadosEnemigo == Enemigo.EstadosEnemigo.ATACANDO){
           rangoDeAtaque = enemigo.atacarJugador();
        }else if(personaje.estadosPersonaje == Personaje.EstadosPersonaje.ATACANDO){
            rangoDeAtaque = personaje.atacar();
        }
        enemigo.identificalAreaDeDaño(rangoDeAtaque);
        personaje.identificalAreaDeDaño(rangoDeAtaque);
    }

    private void reproducirMusica(){

    }




}
