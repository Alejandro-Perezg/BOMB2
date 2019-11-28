package mx.itesm.videojuegos;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;




import com.badlogic.gdx.assets.AssetManager;

public class PantallaCargando extends Pantalla{

    private final float TIEMPO_ENTRE_FRAME = 0.05f; 
    private Sprite spriteCargando; 
    private float timerAnimacion = TIEMPO_ENTRE_FRAME;
    private Juego juego; 
    private TipoPantalla siguientePantalla; 
    private int avance; //0 - 100% de la carga de recursos. 

    private AssetManager manager; //unico en el juego.


    private Texture texturaCargando;


    public PantallaCargando(Juego juego, TipoPantalla siguiente){
        manager = juego.getManager();
        this.juego = juego; 
        this.siguientePantalla = siguiente; 
    }
    
    @Override
    public void show() {
        texturaCargando = new Texture("loading.png");
        spriteCargando = new Sprite(texturaCargando); 
        spriteCargando.setPosition(ANCHO/2-spriteCargando.getWidth(), ALTO/2 - spriteCargando.getHeight());
        
        cargarRecursosPantalla(); //cargar imagener, mp3, ...
        
        cargarTexturas();
    }

    private void cargarRecursosPantalla() {
        switch (siguientePantalla){
            case PANTALLAINICIO:
                cargarRecursosPantallaInicio();
                break;
            case MENU:
                //cargarRecursosMenu();
                break;
            case  PANTALLAACERCADE:
                //cargarRecursosPantallaAcercaDe();
                break;
            case  PANTALLASELECCIONPERSONAJE:
                //cargarRecursosPantallaSeleccionPersonaje();
                break; 
            case NIVEL1:
                cargarTexturas();
                //cargarRecursosNievl1();
                break; 
                  
        }
    }

    private void cargarRecursosPantallaInicio() {
        manager.load("menus/menuInicio/TextoTocaContinuar.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0.5f, 0.4f,0.8f);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteCargando.draw(batch);
        batch.end();

        //ACTUALIZAR
        timerAnimacion -=delta;
        /*if(timerAnimacion<= 0){
            spriteCargando.rotate(-20);
            timerAnimacion = TIEMPO_ENTRE_FRAME;
        }
        */
        actializarCargaRecursos();


    }

    private void actializarCargaRecursos() {
        if(manager.update()){
            switch (siguientePantalla) {
                case PANTALLAINICIO:
                    juego.setScreen(new PantallaInicio(juego));
                    break;
                case MENU:
                    juego.setScreen(new PantallaMenuPrincipal(juego));
                    break;
                case  PANTALLAACERCADE:
                    juego.setScreen(new PantallaAcercaDe(juego));
                    break;
                case  PANTALLASELECCIONPERSONAJE:
                    juego.setScreen(new PantallaSeleccionPersonaje(juego));
                    break;
                case NIVEL1:
                    cargarTexturas();
                    break;
                /*case NIVEL1:
                    juego.setScreen(new Nivel1());
                    break;*/
            }
        }
    }


    private void cargarTexturas(){
        /////fondos
        manager.load("fondos/cabezaArena.png", Texture.class);

        //personaje, kira
        manager.load("sprites_personaje/caminaKiraDer.png", Texture.class);
        manager.load("sprites_personaje/golpeKiraDer.png", Texture.class);
        manager.load("sprites_personaje/kiraStuned.png", Texture.class);

        //personake. rah
        manager.load("sprites_personaje/caminaRaohDer.png", Texture.class);
        manager.load("sprites_personaje/golpeRaohDer.png", Texture.class);
        manager.load("sprites_personaje/rahStuned.png", Texture.class);

        ///personaje enemigos
        manager.load("sprites_enemigo1/enemigo.png", Texture.class);
        manager.load("sprites_enemigo1/enemigoGolpear.png", Texture.class);
        manager.load("sprites_enemigo1/enemigoStoned.png", Texture.class);

        ///PALANCA
        manager.load("Nivel/palanca.png", Texture.class);
        manager.load("Nivel/heart_80x80.png", Texture.class);

    }

    private void cargarSFX(){

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
