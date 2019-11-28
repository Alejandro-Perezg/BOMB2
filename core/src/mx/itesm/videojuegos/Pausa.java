package mx.itesm.videojuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;


import static mx.itesm.videojuegos.Pantalla.ALTO;
import static mx.itesm.videojuegos.Pantalla.ANCHO;

public class Pausa extends Stage { ;
    private boolean active = true;
    private PantallaMenuPrincipal menu;
    private Texture texturaFondoPausa;
    private boolean sonidoOn, musicaOn;
    private Save save;


    public Pausa(Viewport vista, SpriteBatch batch){
        super(vista, batch);
        this.save = new Save();
    }

    private void crearPlantillaPausa(){
        texturaFondoPausa = new Texture("menus/fondoPausa.png");

        Pixmap sombra = new Pixmap((int)ANCHO, (int)ALTO, Pixmap.Format.RGBA8888);
        sombra.setColor(0, 0, 0,.3f);
        sombra.fillRectangle(0,0,sombra.getWidth(), sombra.getHeight());
        Texture texturaSombra  =new Texture(sombra);
        Image imgRectSombra = new Image(texturaSombra);
        imgRectSombra.setPosition(0,0);


        Image imagenFondo = new Image(texturaFondoPausa);
        imagenFondo.setPosition(360,0);

        this.addActor(imagenFondo);
        this.addActor(imgRectSombra);
    }

    public void crearOpcionesMenuPrincipal(final Juego juego, final Music musica){

        crearPlantillaPausa();
        //boton Salir
        TextureRegionDrawable trdExit = new TextureRegionDrawable(new TextureRegion(new Texture("menus/btnExit.png")));
        TextureRegionDrawable trdExitPr = new TextureRegionDrawable(new TextureRegion(new Texture("menus/btnExit_pr.png")));
        ImageButton btnExit = new ImageButton(trdExit,trdExitPr);
        btnExit.setPosition(480, 190);

        btnExit.addListener(new ClickListener(){
                                     @Override
                                     public void clicked(InputEvent event, float x, float y) {
             super.clicked(event, x, y);
             //INSTRUCCIONE
             active = false;
             }
         }
        );

        TextureRegionDrawable trdMusic = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Opciones/soundOn.png")));
        final ImageButton btnMute;
        btnMute = new ImageButton(trdMusic);
        btnMute.setPosition(450, 315);

        btnMute.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                super.clicked(event, x, y);
                //INSTRUCCIONES
                save.playMusic(false);
                musica.stop();
            }
        }
        );

        TextureRegionDrawable trdMusicOn = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Opciones/musicOn.png")));
        ImageButton btnSonido = new ImageButton(trdMusicOn);
        btnSonido.setPosition(670, 315);

        btnSonido.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    //INSTRUCCIONE
                   //menu.ManejarMusica(true);
                    save.playMusic(true);
                    musica.play();

                }
            }
        );

        this.addActor(btnExit);
        this.addActor(btnMute);
        this.addActor(btnSonido);
        Gdx.input.setInputProcessor(this);


    }

    public void crearPausa(final Juego juego, final Music musica) {
        crearPlantillaPausa();

        TextureRegionDrawable trdExit = new TextureRegionDrawable(new TextureRegion(new Texture("menus/btnExit.png")));
        TextureRegionDrawable trdExitPr = new TextureRegionDrawable(new TextureRegion(new Texture("menus/btnExit_pr.png")));
        ImageButton btnExit = new ImageButton(trdExit,trdExitPr);
        btnExit.setPosition(480, 200);

        btnExit.addListener(new ClickListener(){
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    super.clicked(event, x, y);
                                    //INSTRUCCIONE
                                    juego.setScreen(new PantallaMenuPrincipal(juego));
                                    musica.dispose();
                                }
                            }
        );

        this.addActor(btnExit);

        TextureRegionDrawable trdContinue = new TextureRegionDrawable(new TextureRegion(new Texture("menus/continueBtn.png")));
        TextureRegionDrawable trdContinuePr = new TextureRegionDrawable(new TextureRegion(new Texture("menus/continueBtn_pr.png")));
        ImageButton btnContinue = new ImageButton(trdContinue,trdContinuePr);
        btnContinue.setPosition(480, 330);

        btnContinue.addListener(new ClickListener(){
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    super.clicked(event, x, y);
                                    //INSTRUCCIONE
                                    active = false;
                                }
                            }
        );

        this.addActor(btnContinue);

        Gdx.input.setInputProcessor(this);
    }

    public boolean isActive(){
        return this.active;
    }

    public void setActive(Boolean act){
        this.active = act;
    }


}
