package mx.itesm.videojuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.bcel.internal.generic.FLOAD;

import javax.swing.text.View;

import static mx.itesm.videojuegos.Pantalla.ALTO;
import static mx.itesm.videojuegos.Pantalla.ANCHO;

public class Pausa extends Stage { ;

        public Pausa(Viewport vista, SpriteBatch batch){
            super(vista, batch);
        }

        public void crearOpcionesMenuPrincipal(final Juego juego){
            Pixmap sombra = new Pixmap((int)ANCHO, (int)ALTO, Pixmap.Format.RGBA8888);
            sombra.setColor(0, 0, 0,.3f);
            sombra.fillRectangle(0,0,sombra.getWidth(), sombra.getHeight());
            Texture texturaSombra  =new Texture(sombra);
            Image imgRectSombra = new Image(texturaSombra);
            imgRectSombra.setPosition(0,0);
            this.addActor(imgRectSombra);


            Pixmap plantilla = new Pixmap(543, 677, Pixmap.Format.RGBA8888);
            plantilla.setColor(.172f, .537f, .627f,1);
            plantilla.fillRectangle(0,0,plantilla.getWidth(), plantilla.getHeight());
            Texture texturaPlantilla  =new Texture(plantilla);
            Image imgRectPlantilla = new Image(texturaPlantilla);
            imgRectPlantilla.setPosition(368,21);
            this.addActor(imgRectPlantilla);

            TextureRegionDrawable trdExit = new TextureRegionDrawable(new TextureRegion(new Texture("menus/btnExit.png")));

            ImageButton btnExit = new ImageButton(trdExit);
            btnExit.setPosition(455, 134);

            btnExit.addListener(new ClickListener(){
                                         @Override
                                         public void clicked(InputEvent event, float x, float y) {
                                             super.clicked(event, x, y);
                                             //INSTRUCCIONE
                                             juego.setScreen(new PantallaMenuPrincipal(juego));

                                         }
                                     }
            );


            this.addActor(btnExit);

            Gdx.input.setInputProcessor(this);
        }
}
