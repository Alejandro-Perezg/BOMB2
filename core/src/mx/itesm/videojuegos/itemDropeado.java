package mx.itesm.videojuegos;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;




public class itemDropeado {

    private int saludDeItem;
    private Sprite sprite;
    private TextureRegion texturaCompleta;
    private TextureRegion[][] texturas;

    private Animation animacion;    // Caminando
    private float timerAnimacion;   // tiempo para calcular el frame

    private Animation animacionDerecha;

    public Body bodyItem;


    public itemDropeado(int saludDeItem, World mundo){

        generateBodyItem(mundo);

    }



    public void generateBodyItem(World mundo){
        //Body Def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(200, 200); //METROS

        bodyItem = mundo.createBody(bodyDef);  //Objeto simulado.

    }


    public void actualizarItem(){

    }


}
