package mx.itesm.videojuegos;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemigo {
    private int salud;
    private int daño;
    private float velocidad;
    private float rangoDeAtaque;
    private float probabilidadAtaqueCritico;
    private float probabilidadAtaque;

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

    public void atacarJugador(Personaje personaje){

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


    public void perseguir(Personaje personaje){
        float xP = personaje.getX();
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


