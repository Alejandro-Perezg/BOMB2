package mx.itesm.videojuegos;
import com.badlogic.gdx.Game;


public class Juego extends Game {
    public static final float ANCHO= 1280;
    public static final float ALTO= 720;


    @Override
    public void create() {
        setScreen(new PantallaInicio(this)); //referencia del administracion  para pasar de pantalla a otr

    }
}
