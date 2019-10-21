package mx.itesm.videojuegos;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemigo {
    private int salud;
    private int daño;   //recibe
    private float velocidad;
    private float rangoDeAtaque;
    private float probabilidadAtaqueCritico;
    private float probabilidadAtaque;
    public int fuerza; //de daño/ ataque.

    //SPRITES
    private Sprite sprite;
    private TextureRegion texturaCompleta;
    private TextureRegion[][] texturas;

    EstadosEnemigo estadosEnemigo = EstadosEnemigo.NEUTRAL;

    public Enemigo(){
        cargarTexturas();

    }

    private void cargarTexturas() {

    }

    public float atacarJugador(){

        return rangoDeAtaque; //Se llama en nivel y con este valor se calcula en personaje si esta denro del area de ataque.
    }

    public void identificalAreaDeDaño (float rangoDeAtaque){

    }

    private void seguirJugador(){

    }
    private void stun (){

    }

    private void recivirDaño (int daño){
        salud -= daño;
    }


    public void perseguir(float posicionDeJugador){     //Se llama en nivel con el personaje.getX
        float xP = posicionDeJugador;
        if ((sprite.getX() > xP)) {
            sprite.setX(sprite.getX() - 6);
        } if(sprite.getX() < xP) {
            sprite.setX(sprite.getX() + 6);
        }
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);

    }

    protected enum EstadosEnemigo{
        NEUTRAL,
        ATACANDO,
        STUNNED,
        MUERTO
    }
}


