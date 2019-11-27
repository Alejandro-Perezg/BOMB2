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

    private Texture hearthTexture;

    private Animation animacion;    // Caminando
    private float timerAnimacion;   // tiempo para calcular el frame

    private Animation animacionDerecha;

    public Body bodyItem;

    float xBody;
    float yBody;

    public float getxBody(){
        return xBody;
    }

    public int getSaludDeItem(){
        return saludDeItem;
    }

    public itemDropeado(int saludDeItem, Texture texture,World mundo, int x, int y) {
        this.saludDeItem = saludDeItem;
        this.hearthTexture = texture;
        generateBodyItem(mundo,x, y );

    }

    public void generateBodyItem(World mundo, int x, int y) {
        //Body Def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y); //METROS

        bodyItem = mundo.createBody(bodyDef);  //Objeto simulado.

    }

    public void render(SpriteBatch batch){
        batch.draw(hearthTexture, (int)xBody,20);
    }

    public void actualizarItem() {
        xBody = bodyItem.getPosition().x;
        yBody = bodyItem.getPosition().y;



        //bodyItem.setTransform(xBody, yBody, 0);

    }

    private Sprite getSprite(){
        return sprite;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}