package mx.itesm.videojuegos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class PantallaSeleccionNivel extends Pantalla {

    private final Juego juego;

    //FONDO
    private Texture texturaFondo;

    //STAGES
    private Stage escenaMenuNivel;



    //AUDIO
    public Music musica;

    public PantallaSeleccionNivel(Juego juego, Music music){
        this.juego = juego;
        this.musica = music;

    }

    @Override
    public void show() {
        AssetManager manager = new AssetManager();
        cargarTexturas();
        cargarAudios(manager);
        crearHUD();
    }


    private void crearHUD() {
        escenaMenuNivel = new Stage(vista);
        escenaMenuNivel.addAction(Actions.fadeIn(0.5f));
        TextureRegionDrawable Back = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/prev.png")));
        TextureRegionDrawable BackPr = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/prev_pr.png")));
        TextureRegionDrawable Label = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/Select_title.png")));
        TextureRegionDrawable btnN1 = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/N1.png")));
        TextureRegionDrawable btnN2 = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/N2.png")));

        ImageButton btnBack = new ImageButton(Back,BackPr);
        ImageButton btnNiv1= new ImageButton(btnN1);
        ImageButton btnNiv2 = new ImageButton(btnN2);
        ImageButton label = new ImageButton(Label);

        label.setPosition(10,ALTO-label.getHeight()-120);
        btnNiv1.setPosition(ANCHO/3-btnNiv1.getWidth(),ALTO/2);
        btnNiv2.setPosition(ANCHO/3-btnNiv1.getWidth(),ALTO/2-btnNiv2.getHeight()-80);
        btnBack.setPosition(10,ALTO-btnBack.getHeight()-20);

        btnNiv1.addListener(new ClickListener(){
                                  @Override
                                  public void clicked(InputEvent event, float x, float y) {
                                      super.clicked(event, x, y);

                                      //INSTRUCCIONE
                                      juego.setScreen(new Nivel1(juego,musica));
                                  }
                              });
        btnBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //INSTRUCCIONE
                escenaMenuNivel.addAction(Actions.sequence(Actions.fadeOut(0.5f),Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal(juego,musica));
                    }
                })));


               // juego.setScreen(new PantallaMenuPrincipal(juego,musica));
            }
        });

        escenaMenuNivel.addActor(btnNiv1);
        escenaMenuNivel.addActor(label);
        escenaMenuNivel.addActor(btnBack);
        escenaMenuNivel.addActor(btnNiv2);
        escenaMenuNivel.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.5f)));
        Gdx.input.setInputProcessor(escenaMenuNivel);
    }



    private void cargarAudios(AssetManager manager) {
        if (musica == null) {
            manager.load("menus/music/09 Come and Find Me - B mix.mp3", Music.class);
            manager.finishLoading();
            musica = manager.get("menus/music/09 Come and Find Me - B mix.mp3");
            musica.setLooping(true);
        }
        if (juego.playMusic == true){
            musica.play();
        } if (juego.playMusic ==false){
            musica.stop();
        }
    }

    private void cargarTexturas() {
        texturaFondo = new Texture( "menus/fondoMenu.png");

    }



    @Override
    public void render(float delta) {
        borrarPantalla();
        //batch escalaTodo de acuerdo a la visat y la camara
        batch.setProjectionMatrix(camara.combined);


        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        batch.end();

        escenaMenuNivel.act(Gdx.graphics.getDeltaTime());

        escenaMenuNivel.draw();



    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
