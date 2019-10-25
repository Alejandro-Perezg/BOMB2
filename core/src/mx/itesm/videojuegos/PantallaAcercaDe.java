package mx.itesm.videojuegos;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaAcercaDe extends Pantalla {

    private final Juego juego;

    //fondosss
    private Texture texturaFondo;
    private Texture informacion;


    //escena de menu (botones)
    private Stage escenaHUD;

    //AUDIO
    private Music musica;


    public PantallaAcercaDe (Juego juego) {
        this.juego = juego;
    }

    public PantallaAcercaDe (Juego juego, Music musica) {
        this.juego = juego;
        this.musica = musica;
        //this.musicPosition = musicPosition;
    }

    @Override
    public void show() {
        cargarTexturas();
        ajustarMusica();
        crearHUD();

    }

    private void ajustarMusica() {
        if (juego.playMusic == true) {
            musica.play();
        }
    }


    private void cargarTexturas() {
        texturaFondo = new Texture( "menus/menus.jpg");
        informacion = new Texture("menus/acercaDe/nombres.png");
    }

    private void crearHUD() {
        escenaHUD = new Stage(vista);
        TextureRegionDrawable trdBack = new TextureRegionDrawable(new TextureRegion(new Texture("menus/back.png")));
        TextureRegionDrawable trdBackPressed = new TextureRegionDrawable(new TextureRegion(new Texture("menus/backPressed.png")));


        final ImageButton btnBack = new ImageButton(trdBack,trdBackPressed);
        btnBack.setPosition(0, ALTO - btnBack.getHeight());

        //Evento de boton.
        btnBack.addListener(new ClickListener(){
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    super.clicked(event, x, y);
                                    //INSTRUCCIONE
                                    juego.setScreen(new PantallaMenuPrincipal(juego, musica));
                                }
                            }
        );

        escenaHUD.addActor(btnBack);
        Gdx.input.setInputProcessor(escenaHUD);

    }
    @Override
    public void render(float delta) {
        borrarPantalla();
        //batch escalaTodo de acuerdo a la visat y la camara
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        batch.draw(informacion, ANCHO/2-(informacion.getWidth()/2) , 2*ALTO/3 - 300);
        batch.end();
        escenaHUD.draw();
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
    }
}
