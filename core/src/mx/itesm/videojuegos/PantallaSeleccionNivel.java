package mx.itesm.videojuegos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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


    //PREVIO DE PERSONAJE
    private String personajeSeleccionado = "kira";
    private Image imageKira;
    private Image imageRaoh;

    //AUDIO
    public Music musica;
    public Save save;



    public PantallaSeleccionNivel(Juego juego, Music music){
        this.juego = juego;
        this.musica = music;
        this.save = save;
    }

    @Override
    public void show() {
        AssetManager manager = new AssetManager();
        cargarTexturas();
        ajustarMusica();
        cargarAudios(manager);
        crearHUDMenu();


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



    private void crearHUDMenu( ) {
        escenaMenuNivel = new Stage(vista);

        escenaMenuNivel.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.5f)));
        TextureRegionDrawable Back = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/prev.png")));
        TextureRegionDrawable BackPr = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/prev_pr.png")));
        TextureRegionDrawable Label = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/Select_title.png")));
        TextureRegionDrawable btnN1 = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/N1.png")));
        TextureRegionDrawable btnN2 = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/N2.png")));
        TextureRegionDrawable btnN3 = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/N3.png")));
        TextureRegionDrawable charlabel = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/select.png")));
        TextureRegionDrawable btnFlechaDer = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/flechaDer.png")));
        TextureRegionDrawable btnFlechaIz = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/flechaIz.png")));

        TextureRegionDrawable kira = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/kira.png")));
        TextureRegionDrawable raoh = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Nivel/raoh.png")));



        ImageButton btnBack = new ImageButton(Back,BackPr);
        ImageButton btnNiv1= new ImageButton(btnN1);
        ImageButton btnNiv2 = new ImageButton(btnN2);
        ImageButton btnNiv3 = new ImageButton(btnN3);
        ImageButton label = new ImageButton(Label);
        ImageButton btnFD = new ImageButton(btnFlechaDer);
        ImageButton btnFI = new ImageButton(btnFlechaIz);

        Image charLabel = new Image(charlabel);
        imageKira = new Image(kira);
        imageRaoh = new Image(raoh);

        label.setPosition(10,ALTO-label.getHeight()-120);
        charLabel.setPosition(ANCHO-charLabel.getWidth()-10,ALTO-label.getHeight()-100);
        btnNiv1.setPosition(ANCHO/3-btnNiv1.getWidth(),ALTO/2);
        btnNiv2.setPosition(ANCHO/3-btnNiv1.getWidth(),ALTO/2-btnNiv2.getHeight()-40);
        btnNiv3.setPosition(ANCHO/3-btnNiv1.getWidth(),ALTO/2-btnNiv2.getHeight() - btnN2.getBottomHeight() - 180);
        btnNiv3.setColor(0,0,0,0.3f);
        btnNiv2.setColor(0,0,0,0.3f);
        btnBack.setPosition(0,ALTO-btnBack.getHeight()-5);
        btnFD.setPosition(ANCHO - btnFD.getWidth() - 100, 60);
        btnFI.setPosition(ANCHO- btnFD.getWidth() - 400,60 );
        imageKira.setPosition(ANCHO - btnFD.getWidth()  - 300, 200);
        imageRaoh.setPosition(ANCHO - btnFD.getWidth()  - 300, 200);
        imageRaoh.setColor(1,1,1,0f);
        imageKira.setColor(1,1,1,1);

        btnFD.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                personajeSeleccionado = "raoh";
                imageRaoh.setColor(1,1,1,1f);
                imageKira.setColor(0,0,0,0f);
            }
        });
        btnFI.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                personajeSeleccionado = "kira";
                imageKira.setColor(1,1,1,1f);
                imageRaoh.setColor(0,0,0,0f);

            }
        });
        btnNiv1.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, TipoPantalla.NIVEL1, musica, personajeSeleccionado, 1));
            }
        });
        btnNiv2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, TipoPantalla.NIVEL2, musica, personajeSeleccionado, 2));
            }
        });
        btnNiv3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, TipoPantalla.NIVEL3, musica, personajeSeleccionado, 3));
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
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaCargando(juego, TipoPantalla.MENU, musica));
                    }
                })
                ));
            }
        });

        escenaMenuNivel.addActor(charLabel);
        escenaMenuNivel.addActor(btnNiv1);
        escenaMenuNivel.addActor(label);
        escenaMenuNivel.addActor(btnBack);
        escenaMenuNivel.addActor(btnNiv2);
        escenaMenuNivel.addActor(btnNiv3);
        escenaMenuNivel.addActor(btnFD);
        escenaMenuNivel.addActor(btnFI);
        escenaMenuNivel.addActor(imageKira);
        escenaMenuNivel.addActor(imageRaoh);
        Gdx.input.setInputProcessor(escenaMenuNivel);
    }



    private void ajustarMusica() {
        System.out.println(this.juego.playMusic);
        if (juego.playMusic == true) {

            musica.play();
        }
    }

    private void cargarTexturas() {
        texturaFondo = new Texture( "menus/fondoMenu.png");

    }



    @Override
    public void render(float delta) {
        borrarPantalla();
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
    enum PersonajeActual{
        KIRA,
        RAOH
    }



}
