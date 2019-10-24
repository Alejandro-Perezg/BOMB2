package mx.itesm.videojuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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


    Nivel1(Juego juego){
        this.juego = juego;
    }


    private void generarPersonaje() {
       /* fuerzaEnemigo = enemigo.fuerza;
        Personaje personaje= new Personaje(texturaPersonaje,ANCHO/2,fuerzaEnemigo);
*/
       //Personaje JUGADOR = new Personaje(texturaPersonaje, 100, 30);


    }
    private void generarEnemigos(){
        /*
        switch(idNivel){
            case 1:
                cantidadEnemigos = 10;
                        break;
            case 2:
                cantidadEnemigos = 15;
                break;
        }
        fuerzaPersonaje = personaje.fuerza;

         */
    }

    public void generarZonaDeDaño(){
        /*
        float rangoDeAtaque = 0;
        if(enemigo.estadosEnemigo == Enemigo.EstadosEnemigo.ATACANDO){
            rangoDeAtaque = enemigo.atacarJugador(fuerzaEnemigo);

        }else if(personaje.estadosPersonaje == Personaje.EstadosPersonaje.ATACANDO){
        rangoDeAtaque = personaje.atacar(fuerzaPersonaje);

    }
        enemigo.identificalAreaDeDaño(rangoDeAtaque);
        personaje.identificalAreaDeDaño(rangoDeAtaque);

         */
    }

    private void reproducirMusica(){

    }



    @Override
    public void show() {
        cargarTexturas();
        reproducirMusica();
        configurarVista();
        crearHUD();
        generarEnemigos();
        generarPersonaje();
    }

    private void crearHUD() {
        escenaHUD = new Stage(vista);

        TextureRegionDrawable trdPausa = new TextureRegionDrawable(new TextureRegion(new Texture("btnpausa.png")));
        TextureRegionDrawable trdPausaPressed = new TextureRegionDrawable(new TextureRegion(new Texture("btnpausaPressed.png")));


        final ImageButton btnPausa = new ImageButton(trdPausa,trdPausaPressed);
        btnPausa.setPosition(ANCHO-btnPausa.getWidth(), ALTO - btnPausa.getHeight());

        //Evento de boton.
        btnPausa.addListener(new ClickListener(){
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //INSTRUCCIONE
                juego.setScreen(new PantallaMenuPrincipal(juego));
            }
        }
        );
        escenaHUD.addActor(btnPausa);
        Gdx.input.setInputProcessor(escenaHUD);

    }

    private void cargarTexturas() {
        texturaFondo = new Texture( "fondos/fondo2.jpg");

        texturaPersonaje = new Texture("EnemigoPrototipo/Enemigo_Proto_200x300.png");
        ///CARGAR TEXTURAS JUGADOR???

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
        //ACTUALIZAR NAVE
        actualizarPersonaje();

        borrarPantalla();

        //batch escalaTodo de acuerdo a la visat y la camara
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
       // personaje.render(batch);
        batch.end();
        escenaHUD.draw();
    }

    private void actualizarPersonaje() {

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
    protected void borrarPantalla() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }
}
