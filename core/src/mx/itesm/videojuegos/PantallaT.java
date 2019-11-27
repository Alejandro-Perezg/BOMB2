package mx.itesm.videojuegos;

import com.badlogic.gdx.graphics.Texture;

public class PantallaT extends Pantalla {
    private final Juego juego;
    private Texture fondo;


    public PantallaT(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
    manager.load("fondos/T/fondoT.png", Texture.class);
    manager.finishLoading();
    fondo = manager.get("fondos/T/fondoT.png");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(fondo, 0,0);
        batch.end();

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
