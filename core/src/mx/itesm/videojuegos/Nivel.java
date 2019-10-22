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
    private int fuerzaEnemigo;
    private int fuerzaPersonaje;



    private void generarPersonaje() {
        fuerzaEnemigo = enemigo.fuerza;
    }
    private void generarEnemigos(){
        fuerzaPersonaje = personaje.fuerza;
    }

    public void generarZonaDeDaño(){
        float rangoDeAtaque = 0;
        if(enemigo.estadosEnemigo == Enemigo.EstadosEnemigo.ATACANDO){
           rangoDeAtaque = enemigo.atacarJugador(fuerzaEnemigo);

        }else if(personaje.estadosPersonaje == Personaje.EstadosPersonaje.ATACANDO){
            rangoDeAtaque = personaje.atacar(fuerzaPersonaje);

        }
        enemigo.identificalAreaDeDaño(rangoDeAtaque);
        personaje.identificalAreaDeDaño(rangoDeAtaque);
    }

    private void reproducirMusica(){

    }




}
