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

    public Personaje(Texture texture, float x, float y) {
        this.texturaCompleta= new TextureRegion(texture);
        TextureRegion[][] texturas = texturaCompleta.split(32,54);

    }

    public float atacar(int daño){

        return rangoDeAtaque; //Se llama en nivel y con este valor se calcula en personaje si esta denro del area de ataque.
    }
    private void empujarEnemigo(){

    }

    public void identificalAreaDeDaño (float rangoDeAtaque){


    }
    private void recogerItem (){

    }

    public void alentarTiempo(){

    }

    public void recivirDaño (int daño){
        if(salud >0){
            salud -= daño;
        }else{
            estadosPersonaje = EstadosPersonaje.MUERTO;
        }

    }


    protected enum EstadosPersonaje{
        NEUTRAL,
        ATACANDO,
        STUNNED,
        MUERTO
    }

}
