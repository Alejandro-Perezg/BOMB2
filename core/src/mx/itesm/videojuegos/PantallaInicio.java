package mx.itesm.videojuegos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import java.util.ArrayList;

class PantallaInicio extends Pantalla {

    private final Juego juego;

    //Stage
    private Texture labelTexture;
    private Stage intro;

    public PantallaInicio(Juego juego){
        this.juego = juego;

    }
    @Override
    public void show() {
        manager = juego.getManager();
        cargarTitulo(manager);
        cargarFondo();

    }

    private void cargarFondo() {


    }

    private void cargarTitulo(AssetManager manager) {
        manager.load("Splash/splashtitle.png", Texture.class);
        manager.finishLoading();
        labelTexture = manager.get("Splash/splashtitle.png");
        intro = new Stage(vista);
        TextureRegionDrawable labelIntro = new TextureRegionDrawable(new TextureRegion(labelTexture));
        ImageButton tituloInicial = new ImageButton(labelIntro);

        tituloInicial.setPosition(ANCHO/2-tituloInicial.getWidth()/2,ALTO/2-tituloInicial.getHeight()/2);

        intro.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(4),Actions.fadeOut(4), Actions.run(new Runnable() {
            @Override
            public void run() {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal(juego));
            }
        })));

        intro.addActor(tituloInicial);
        Gdx.input.setInputProcessor(intro);


    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        Gdx.gl.glClearColor(0,0,0,1);
        batch.setProjectionMatrix(camara.combined);

        intro.act(Gdx.graphics.getDeltaTime());
        intro.draw();

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
