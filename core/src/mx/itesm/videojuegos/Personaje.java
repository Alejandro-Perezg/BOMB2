package mx.itesm.videojuegos;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Personaje {
    private boolean sexo;
    private int salud;
    private int daño;   //recibe
    private int poder;
    private float velocidad;
    private float rangoDeAtaque;
    private int porcentajeDeStamina;
    private float porcentajePoder;
    public int fuerza; //de daño/ ataque.

    //SPRITES
    private Sprite sprite;
    private TextureRegion texturaCompleta;
    private TextureRegion[][] texturas;

    EstadosPersonaje estadosPersonaje = EstadosPersonaje.NEUTRAL;


    public float atacar(){

        return rangoDeAtaque; //Se llama en nivel y con este valor se calcula en personaje si esta denro del area de ataque.
    }

    public void identificalAreaDeDaño (float rangoDeAtaque){

    }


    protected enum EstadosPersonaje{
        NEUTRAL,
        ATACANDO,
        STUNNED,
        MUERTO
    }

}
