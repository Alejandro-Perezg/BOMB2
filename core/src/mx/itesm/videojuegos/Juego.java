package mx.itesm.videojuegos;
import com.badlogic.gdx.Game;


public class Juego extends Game {
    public static boolean playMusic = true;

    @Override
    public void create() {
        setScreen(new PantallaInicio(this)); //referencia del administracion  para pasar de pantalla a otr

    }
}







