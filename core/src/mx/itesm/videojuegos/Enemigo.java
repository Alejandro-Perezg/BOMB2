package mx.itesm.videojuegos;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class Enemigo {

    //Stats
    private int salud;
    private int daño;   //recibe
    public float velocidad;
    private float rangoDeAtaque;
    private float probabilidadAtaqueCritico;
    private float probabilidadAtaque;
    public int fuerza; //de daño/ ataque

    //FISICAS
    private Body body;
    private Box2DDebugRenderer debugRenderer;


    //SPRITES
    private Sprite sprite;
                         // Tiempo para cambiar frames de la animación

    //Estado inical
    EstadosEnemigo estadosEnemigo = EstadosEnemigo.NEUTRAL;
    mirandoA mirandoA;

    public Enemigo(Texture textura, float x, float y, int fuerzaPersonaje){
        daño = fuerzaPersonaje;
        TextureRegion texturaCompleta = new TextureRegion(textura);
        TextureRegion[][] texturas = texturaCompleta.split(250, 393);


        sprite = new Sprite(texturas[0][0]);
        sprite.setPosition(x,y);

        this.estadosEnemigo = EstadosEnemigo.NEUTRAL;


    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);

    }

    public Sprite getSprite(){
        return sprite;
    }

    public float atacarJugador(int daño){

        return rangoDeAtaque; //Se llama en nivel y con este valor se calcula en personaje si esta denro del area de ataque.
    }

    public void identificalAreaDeDaño (float rangoDeAtaque){
        //if (enemigo.getX )

    }

    private void seguirJugador(){

    }
    private void stun (){

    }

    public void recivirDaño (int daño){
        if(salud >0){
            salud -= daño;
        }else{
            estadosEnemigo = EstadosEnemigo.ATACANDO.MUERTO;
        }
    }


    public void perseguir(float posicionDeJugador){     //Se llama en nivel con el personaje.getX
        float xP = posicionDeJugador;
        if ((sprite.getX() > xP)) {
            sprite.setX(sprite.getX() - 6);
        } if(sprite.getX() < xP) {
            sprite.setX(sprite.getX() + 6);
        }
    }
    protected enum EstadosEnemigo{
        NEUTRAL,
        ATACANDO,
        STUNNED,
        MUERTO,
        MOV_IZQUIERDA,
        MOV_DERECHA
    }

    protected  enum mirandoA{
        DERECHA,
        IZQUIERDA
    }
}


