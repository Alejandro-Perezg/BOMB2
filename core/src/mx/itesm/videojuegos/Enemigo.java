package mx.itesm.videojuegos;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemigo {
    private int salud;
    private int daño;   //recibe
    public float velocidad;
    private float rangoDeAtaque;
    private float probabilidadAtaqueCritico;
    private float probabilidadAtaque;
    public int fuerza; //de daño/ ataque.

    //SPRITES copiado de rmroman :)
    private Animation<TextureRegion> spriteAnimado;         // Animación caminando
    private float timerAnimacion;                           // Tiempo para cambiar frames de la animación

    private Sprite sprite;
    EstadosEnemigo estadosEnemigo = EstadosEnemigo.NEUTRAL;

    public Enemigo(Texture textura, float x, float y){
        cargarTexturas(textura);    //Creo que esto ya va en la implentacion tho

    }

    private void cargarTexturas(Texture textura) {
       /* TextureRegion texturaCompleta = new TextureRegion(textura);
        TextureRegion[][] texturaPersonaje = new TextureRegion[0][];    // ejemplo para la vivi del futuro = texturaCompleta.split(32,64);

        spriteAnimado = new Animation(0.15f, texturaPersonaje[0][3], texturaPersonaje[0][1], texturaPersonaje[0][1] );
        // Animación infinita
        spriteAnimado.setPlayMode(Animation.PlayMode.LOOP);
        // Inicia el timer que contará tiempo para saber qué frame se dibuja
        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaPersonaje[0][0]);    // QUIETO
        sprite.setPosition(x,y);    // Posición inicial
*/
    }
    public void dibujar(SpriteBatch batch){
        //Dibujar eal enemigo
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
        MUERTO
    }
}


