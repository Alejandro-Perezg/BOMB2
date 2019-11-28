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

public class GameOverStage extends Stage { ;
    private TextureRegionDrawable trdgiveupBtn;
    private TextureRegionDrawable trdgiveupBtnPr;
    private TextureRegionDrawable trdgameoverBtn;
    private TextureRegionDrawable trdgameoverBtnPr;
    private String personajeSeleccionado;

    private Music musica;

    public GameOverStage(Viewport vista, SpriteBatch batch, String personajeS){
        super(vista, batch);
        this.personajeSeleccionado = personajeS;
    }


    public void creargameOverStage(final Juego juego, final Music musica, final int Nivel){
        crearFondo();

        TextureRegionDrawable trdgiveupBtn = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Opciones/gameover/giveupbtn.png")));
        TextureRegionDrawable trdgiveupBtnPr = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Opciones/gameover/giveupbtnPressed.png")));
        TextureRegionDrawable trdTryagainBtn = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Opciones/gameover/tryagainbtn.png")));
        TextureRegionDrawable trdTryagainBtnPr = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Opciones/gameover/tryagainbtnPressed.png")));

        ImageButton btnTryagain = new ImageButton(trdTryagainBtn, trdTryagainBtnPr);
        btnTryagain.setPosition(120,ALTO -500);
        ImageButton btnGiveup = new ImageButton(trdgiveupBtn, trdgiveupBtnPr);
        btnGiveup.setPosition(745,ALTO -500);

        btnTryagain.addListener(new ClickListener(){
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        super.clicked(event, x, y);
                                        //INSTRUCCIONE
                                        musica.stop();
                                        juego.setScreen(new Nivel1(juego, musica, personajeSeleccionado,Nivel));
                                    }
                                }
        );

        btnGiveup.addListener(new ClickListener(){
                                  @Override
                                  public void clicked(InputEvent event, float x, float y) {
                                      super.clicked(event, x, y);
                                      //INSTRUCCIONE
                                      musica.dispose();
                                      juego.setScreen(new PantallaMenuPrincipal(juego));
                                  }
                              }
        );

        this.addActor(btnGiveup);
        this.addActor(btnTryagain);

        Gdx.input.setInputProcessor(this);

    }

    private void crearFondo(){
        Pixmap sombra = new Pixmap((int)ANCHO, (int)ALTO, Pixmap.Format.RGBA8888);
        sombra.setColor(0, 0, 0,.3f);
        sombra.fillRectangle(0,0,sombra.getWidth(), sombra.getHeight());
        Texture texturaSombra  =new Texture(sombra);
        Image imgRectSombra = new Image(texturaSombra);
        imgRectSombra.setPosition(0,0);

        Texture texturaLetras = new Texture("menus/Opciones/gameover/gameoverSign.png");
        Image letras = new Image(texturaLetras);
        letras.setPosition(275, ALTO - 260);


        this.addActor(letras);
        this.addActor(imgRectSombra);
    }


}
