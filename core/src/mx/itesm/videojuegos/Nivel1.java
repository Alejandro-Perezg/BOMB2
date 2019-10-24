package mx.itesm.videojuegos;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
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
    private Texture texturaPersonaje;
    private Texture texturaEnemigo;
//MUSICA
    private Music musica;
    private Sound efecto;
//Variables de Screen
    private OrthographicCamera camara;
    private Viewport vista;
    private SpriteBatch batch;
    // Escena de menu (botones)
    private Stage escenaHUD;


    private void Nivel1(Juego juego){
        this.juego = juego;
    }


    private void generarPersonaje() {
        fuerzaEnemigo = enemigo.fuerza;
        Personaje personaje= new Personaje(texturaPersonaje,ANCHO/2,fuerzaEnemigo);

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



    @Override
    public void show() {
        generarEnemigos();
        generarPersonaje();
        reproducirMusica();
        configurarVista();
        cargarTexturas();
        crearHUD();
    }

    private void crearHUD() {
        escenaHUD = new Stage(vista);

    }

    private void cargarTexturas() {
    }

    private void configurarVista() {
        camara = new OrthographicCamera();
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();

        vista = new StretchViewport(ANCHO, ALTO, camara);

        batch = new SpriteBatch(); //administra los trazos.

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
