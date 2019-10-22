package mx.itesm.videojuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;


public class NivelDojo extends Pantalla {
    private Juego juego;

    private Texture texturaFondo;

    //escena de  (botones)
    private Stage escenaHUD;

    //personaje
    private Personaje personaje;
    private NivelDojo.Movimiento estadoPersonaje = NivelDojo.Movimiento.QUIETO;
    private Texture texturaPersonaje;

    //Enemigo
    private Enemigo enemigo;
    private Enemigo.EstadosEnemigo estadosEnemigo = Enemigo.EstadosEnemigo.NEUTRAL;

    // Herramientas
    private Texture texturaBarraVida;
    public NivelDojo(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() { //mostrar en pantalla fisica. ini items, texturas.
        configurarVista();
        cargarTexturas();
        crearHUD();
        crearPersonaje();
        crearEnemigo();

    }

    private void crearEnemigo() {
        Texture texturaEnemgio = new Texture("EnemigoPrototipo/Enemigo_Proto_200x300.png");
        enemigo = new Enemigo(texturaEnemgio, 800,50);
    }

    private void crearPersonaje() {
            Texture texturaPersonaje = new Texture("Personajes/TexturaHombre.png");
            personaje = new Personaje(texturaPersonaje, 300 , 50);
    }

    private void crearHUD() {
        escenaHUD = new Stage(vista);

        //botones para mover
        TextureRegionDrawable trdDerecha = new TextureRegionDrawable(new TextureRegion(new Texture("flechaDerecha.png")));
        TextureRegionDrawable trdIzquierda  = new TextureRegionDrawable(new TextureRegion(new Texture("flechaIzquierda.png")));
        ImageButton  btnDerecha = new ImageButton(trdDerecha);
        ImageButton  btnIzquierda = new ImageButton(trdIzquierda);
        btnDerecha.setPosition((btnDerecha.getWidth()*2) + 20 ,0);
        btnIzquierda.setPosition( 10 + btnDerecha.getWidth(),0);

        //Listeners
        btnDerecha.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                estadoPersonaje = NivelDojo.Movimiento.DERECHA;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                estadoPersonaje = NivelDojo.Movimiento.QUIETO;
            }


        });

        btnIzquierda.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                estadoPersonaje = NivelDojo.Movimiento.IZQUIERDA;
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                estadoPersonaje = NivelDojo.Movimiento.QUIETO;
            }


        });

        escenaHUD.addActor(btnDerecha);
        escenaHUD.addActor(btnIzquierda);

        Gdx.input.setInputProcessor(escenaHUD);

    }

    private void cargarTexturas() {
        texturaFondo = new Texture( "Fondos/fondoN1.jpg");
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
        //ACTUALIZAR PERSONAJE
        actualizarPersonaje();
        actualizarEnemigo();



        borrarPantalla();
        //batch escalaTodo de acuerdo a la visat y la camara
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        batch.draw(texturaBarraVida, 800, 800);
        personaje.render(batch);
        enemigo.render(batch);
        batch.end();
        escenaHUD.draw();
    }

    private void actualizarEnemigo() {
        switch(estadosEnemigo){
            case NEUTRAL:
                enemigo.perseguir(personaje);
            case STUNNED:



        }
    }

    private void actualizarPersonaje() {
        switch(estadoPersonaje){
            case DERECHA:
                personaje.mover(10);
                break;
            case IZQUIERDA:
                personaje.mover(-10);
                break;
        }

       // System.out.println(personaje.health);
    }


    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

        texturaFondo.dispose(); //liberar
        texturaBarraVida.dispose();
    }

    private enum Movimiento {
        QUIETO,
        DERECHA,
        IZQUIERDA
    }
}
