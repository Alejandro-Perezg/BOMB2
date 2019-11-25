package mx.itesm.videojuegos;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;


///BODY
import com.badlogic.gdx.physics.box2d.World;





public class Enemigo {
    //Stats
    private int salud = 20;
    private int daño;   //recibe
    public float velocidad;
    private float rangoDeAtaque = 20;
    private float probabilidadAtaqueCritico;
    private float probabilidadAtaque;
    public int fuerza; //de daño/ ataque
    private boolean aturdido = true;
    private int framesAturdidos = -1;
    private int framesAtacando = 0; //60;
    //FISICAS
    private Body body;
    private Box2DDebugRenderer debugRenderer;

    //SPRITES
    private Sprite sprite;
    private TextureRegion texturaCompleta;
    private TextureRegion[][] texturas;
    private Animation<TextureRegion> animacionMoverse;         // Animación caminando
    private float timerAnimacion = 0;
    private Animation animacionDerecha;

    private Animation<TextureRegion> animacionGolpe;
    private  TextureRegion texturaCompletaGOLPE;
    private TextureRegion[][] texturasGOLPES;

    private Personaje personaje;
// Tiempo para cambiar frames de la animación
    EstadosEnemigo estadosEnemigo = EstadosEnemigo.NEUTRAL;
    EstadosEnemigo nextEstadoEnemigo = EstadosEnemigo.NEUTRAL;
    static Enemigo.mirandoA mirandoA;


    ////
    //Body enemigoGenerado;
    public Body bodyPersonaje;
    BodyDef bodyDef = new BodyDef();


    float x;
    float y;


    public Enemigo(Texture textura, Texture textureAtacando, float x, int fuerzaPersonaje, Personaje personaje) {
        this.personaje = personaje;
        daño = fuerzaPersonaje;
        cargarTexturas(textura,textureAtacando,x);
        cargarFisica();

        //setMirandoA(mirandoA.DERECHA);
        setMirandoA(mirandoA.DERECHA);

    }

    public void cargarFisica() {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(Pantalla.ANCHO,0 );

        PolygonShape box = new PolygonShape();
        box.setAsBox(23,32);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;

        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f; // rebota un poco

    }

    private void cargarTexturas(Texture textura, Texture texturaAtacando, float x) {
        texturaCompleta = new TextureRegion(textura);
        texturaCompletaGOLPE = new TextureRegion(texturaAtacando);
        TextureRegion[][] texturaEnemigo = texturaCompleta.split(175, 350);  // ejemplo para la vivi del futuro = texturaCompleta.split(32,64);

        TextureRegion[][] texturasGOLPES = texturaCompletaGOLPE.split(205, 355);

        animacionMoverse = new Animation<>(0.2f, texturaEnemigo[0][0], texturaEnemigo[0][1], texturaEnemigo[0][2], texturaEnemigo[0][3]);

        animacionGolpe = new Animation<>(0.2f, texturasGOLPES[0][0], texturasGOLPES[0][1],  texturasGOLPES[0][2],  texturasGOLPES[0][3]);

        // Animación infinita
        animacionMoverse.setPlayMode(Animation.PlayMode.LOOP);
        animacionGolpe.setPlayMode(Animation.PlayMode.LOOP);

        // Inicia el timer que contará tiempo para saber qué frame se dibuja

//        GOLPE.setPlayMode(Animation.PlayMode.LOOP);

        timerAnimacion = 0;
        // Crea el sprite con el personaje quieto (idle)
        sprite = new Sprite(texturaEnemigo[0][0]);    // QUIETO
        sprite.setPosition(x, 0);    // Posición inicial
        this.estadosEnemigo = EstadosEnemigo.NEUTRAL;

    }

    public void render(SpriteBatch batch) {
        //Dibujar eal enemigo
        timerAnimacion += Gdx.graphics.getDeltaTime();
        //System.out.println("ESTADO ENEMIGO" + estadosEnemigo + mirandoA);


        switch (estadosEnemigo) {
            case MOV_DERECHA:
                this.mirandoA = Enemigo.mirandoA.DERECHA;
                timerAnimacion += Gdx.graphics.getDeltaTime();
                TextureRegion region = animacionMoverse.getKeyFrame(timerAnimacion);

                if (region.isFlipX()) {
                    region.flip(true, false);
                }

//                this.sprite.setPosition(sprite.getX() + 3 , sprite.getY());
                this.sprite.setPosition(sprite.getX(), sprite.getY());


                batch.draw(region, sprite.getX(), sprite.getY());

                break;

            case MOV_IZQUIERDA:
                this.mirandoA = Enemigo.mirandoA.IZQUIERDA;
                //System.out.println("Dibujando, moviendo" );

                timerAnimacion += Gdx.graphics.getDeltaTime();
                region = animacionMoverse.getKeyFrame(timerAnimacion);

                if (!region.isFlipX()) {
                    region.flip(true, false);
                }

//                this.sprite.setPosition(sprite.getX() - 3 , sprite.getY());
                this.sprite.setPosition(sprite.getX() - 3, sprite.getY());


                batch.draw(region, sprite.getX(), sprite.getY());
                break;
            case ATACANDO:

                timerAnimacion += Gdx.graphics.getDeltaTime();
                region= animacionGolpe.getKeyFrame(timerAnimacion);

                if (mirandoA == mirandoA.IZQUIERDA) {
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,false);
                    }
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                break;

            case NEUTRAL:
                //System.out.println("DIBUJANDO, NEUTERAL");
                region = animacionMoverse.getKeyFrame(timerAnimacion);
                batch.draw(region, sprite.getX(), sprite.getY());
                sprite.draw(batch);
                break;


        }
    }

    public void comportamiento(String random){
        //System.out.println(estadosEnemigo);
        char rngChar = (random.toCharArray())[7];
        int rng = Integer.parseInt(String.valueOf(rngChar));
        //System.out.println(rng);
        if (aturdido){
            //System.out.println(framesAturdidos);
            aturdir(rng * 5);
        } else if (framesAtacando > 0) {
            estadosEnemigo = EstadosEnemigo.ATACANDO;
            framesAtacando -= 1;
        } else {

            float leftX = personaje.getX();
            float rightX = leftX + personaje.getSprite().getWidth();

            if ((sprite.getX() > rightX)) {
                this.estadosEnemigo = EstadosEnemigo.MOV_IZQUIERDA;
            } else if (sprite.getX() + sprite.getWidth() < leftX) {
                this.estadosEnemigo = EstadosEnemigo.MOV_DERECHA;
            } else {
                nextEstadoEnemigo = EstadosEnemigo.ATACANDO;
                aturdido = true;
                framesAtacando = 60;

            }
        }
    }

    private void aturdir(int rng) {
        estadosEnemigo = EstadosEnemigo.NEUTRAL;
        if (framesAturdidos <= -1) {
            this.framesAturdidos = rng;
        } else if (framesAturdidos == 0) {
            this.aturdido = false;
            estadosEnemigo = nextEstadoEnemigo;
        }
        framesAturdidos -= 1;
        // System.out.println(framesAturdidos);
    }

    public float atacarJugador(int daño) {

        return rangoDeAtaque; //Se llama en nivel y con este valor se calcula en personaje si esta denro del area de ataque.
    }


    public void recibirDano (int dano){
        salud -= dano;
    }



    public void setMirandoA(mirandoA mira) {
        this.mirandoA = mira;
    }


    public mirandoA getMirandoA() {
        return mirandoA;
    }

    public void setEstadosEnemigo(EstadosEnemigo estado) {
        this.estadosEnemigo = estado;
    }

    public EstadosEnemigo getEstadoEnemigo() {
        return estadosEnemigo;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getX() {
        return this.sprite.getX();
    }

    protected enum EstadosEnemigo {
        NEUTRAL,
        ATACANDO,
        STUNNED,
        MUERTO,
        MOV_IZQUIERDA,
        MOV_DERECHA
    }

    protected enum mirandoA {
        DERECHA,
        IZQUIERDA
    }

    public void generateBodyEnemigo(World mundo, int x) {

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, 200); //METROS

        bodyPersonaje = mundo.createBody(bodyDef);


    }



    public void actualizarEnemigo() {

        x = bodyPersonaje.getPosition().x;
        y = bodyPersonaje.getPosition().y;

        getSprite().setPosition(x - 5, y - 200f);

        x = bodyPersonaje.getPosition().x;
        y = bodyPersonaje.getPosition().y;

        switch (getEstadoEnemigo()) {
            case MOV_DERECHA:
                bodyPersonaje.setTransform(x + 5, y, 0);
                break;

            case MOV_IZQUIERDA:
                bodyPersonaje.setTransform(x - 5, y, 0);
                break;
            case NEUTRAL:
                break;
        }
    }

}