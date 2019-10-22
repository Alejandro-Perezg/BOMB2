package mx.itesm.videojuegos;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Nivel1  extends Nivel{
    private Juego juego;
//Variables de nivel
    private int idNivel = 1;
    private int score = 0;
    public int coordenadasDano;
// PERSONAJE
    private Personaje personaje;
    private int fuerzaPersonaje;
//ENEMIGO
    private Enemigo enemigo;
    private int fuerzaEnemigo;
    private int cantidadEnemigos;
    //TEXTURAS
    private Sprite sprite;
    private Texture texturaFondo;
//MUSICA
    private Music musica;
    private Sound efecto;
//Variables de Screen
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;
    // Escena de menu (botones)
    private Stage escenaHUD;

    public Nivel1() {
        this.juego = juego;
    }


    private void generarPersonaje() {
        fuerzaEnemigo = enemigo.fuerza;
    }
    private void generarEnemigos(){
        switch(idNivel){
            case 1:
                cantidadEnemigos = 10;
                        break;
            case 2:
                cantidadEnemigos = 15;
                break;

        }



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

    private void Nivel1(){

    }

    @Override
    public void show() {
        generarEnemigos();
        generarPersonaje();
        reproducirMusica();


    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
