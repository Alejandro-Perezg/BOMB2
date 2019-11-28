package mx.itesm.videojuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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

public class VictoryStage extends Stage {
    private String personajeS;

    public VictoryStage(Viewport vista, SpriteBatch batch, String personageS){
        super(vista,batch);
        this.personajeS = personageS;
    }

    public void crearVictoryStage(final Juego juego, final Music musica){
        TextureRegionDrawable trdContinueBtn = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Opciones/Victoria/continueBtn.png")));
        TextureRegionDrawable trdContinueBtnPr = new TextureRegionDrawable(new TextureRegion(new Texture("menus/Opciones/Victoria/continueBtnPressed.png")));
        Texture texturaLetras = new Texture("menus/Opciones/Victoria/victorySign.png");

        ImageButton btnContinue = new ImageButton(trdContinueBtn, trdContinueBtnPr);
        btnContinue.setPosition(430, ALTO - 550);

        Image letreroVictoria = new Image(texturaLetras);
        letreroVictoria.setPosition(195, ALTO - 261);

        btnContinue.addListener(new ClickListener(){
                                  @Override
                                  public void clicked(InputEvent event, float x, float y) {
                                      super.clicked(event, x, y);
                                      //INSTRUCCIONE
                                      musica.stop();
                                      juego.setScreen(new PantallaSeleccionNivel(juego, musica));
                                  }
                              }
        );

        this.addActor(btnContinue);
        this.addActor(letreroVictoria);

        Gdx.input.setInputProcessor(this);
    }

}
