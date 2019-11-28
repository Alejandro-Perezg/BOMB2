package mx.itesm.videojuegos;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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

import java.util.Random;
///BODY
import com.badlogic.gdx.physics.box2d.World;





public class Enemigo {
    //Stats
    private int salud;
    public float velocidad;
    private float rangoDeAtaque = 20;
    private float probabilidadAtaqueCritico;
    private float probabilidadAtaque;
    public int fuerza; //de daño/ ataque
    private boolean retrasado = true;
    private int framesAturdidos = -1;
    private int framesAtacando = 0; //60;
    private int framesStunned = -1;
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

    private int idTipoEnemigo;

    //SONIDOS
    private Sound sonidoDano;
    private Sound sonidoDefault;

    private Animation<TextureRegion> animacionGolpe;
    private  TextureRegion texturaCompletaGOLPE;
    private TextureRegion texturaStuned;
    private Animation<TextureRegion> animacionStuned;
    private TextureRegion[][] texturasGOLPES;

    private Personaje personaje;


    public int getPuntos(){
        if (idTipoEnemigo == 1){
            return 10;
        }
        if (idTipoEnemigo == 0){
            return 50;
        }
        return 400;
    }




    //Estados
    EstadosEnemigo estadosEnemigo = EstadosEnemigo.NEUTRAL;
    private EstadosEnemigo nextEstadoEnemigo = EstadosEnemigo.NEUTRAL;
    private MirandoA mirandoA = MirandoA.DERECHA;

    private boolean puedoRecibirDano = true;

    //Body enemigoGenerado;
    public Body bodyPersonaje;
    BodyDef bodyDef = new BodyDef();
    private float x;
    private float y;

    private Sound sonido1;
    private Sound sonido2;


    public boolean frameDeAtaque = false;


    public Enemigo(Texture textura, Texture textureAtacando, Texture texturaEnemigoStuned, float x, int fuerzaPersonaje, Personaje personaje,
                   Sound sonidoDefault, Sound sonidoDano, Sound sonido1, Sound sonido2, int idTipoEnemigo) {
        this.idTipoEnemigo = idTipoEnemigo;
        this.personaje = personaje;
        cargarTexturas(textura,textureAtacando,texturaEnemigoStuned, x);
        cargarFisica();
        this.sonidoDefault = sonidoDefault;
        this.sonidoDano = sonidoDano;

        this.sonido1 = sonido1;
        this.sonido2 = sonido2;

        if(idTipoEnemigo == 1){
            this.salud = 30;
        }
        if (idTipoEnemigo == 0){
            this.salud = 60;
        }
        if (idTipoEnemigo == 2){
            this.salud = 100;
        }

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

    void cargarTexturas(Texture textura, Texture texturaAtacando, Texture texturaEnemigoStuned, float x) {
        texturaCompleta = new TextureRegion(textura);
        texturaCompletaGOLPE = new TextureRegion(texturaAtacando);
        texturaStuned = new TextureRegion(texturaEnemigoStuned);
        TextureRegion[][] texturaEnemigo = texturaCompleta.split(texturaCompleta.getRegionWidth()/4,texturaCompleta.getRegionHeight());  // ejemplo para la vivi del futuro = texturaCompleta.split(32,64);

        TextureRegion[][] texturasGOLPES = texturaCompletaGOLPE.split(texturaCompletaGOLPE.getRegionWidth()/4, texturaCompletaGOLPE.getRegionHeight());

        TextureRegion[][] texturaSTUNED = texturaStuned.split(texturaStuned.getRegionWidth()/4,texturaStuned.getRegionHeight());

        animacionMoverse = new Animation<>(0.4f, texturaEnemigo[0][0], texturaEnemigo[0][1], texturaEnemigo[0][1], texturaEnemigo[0][2], texturaEnemigo[0][3]);

        animacionGolpe = new Animation<>(0.32f, texturasGOLPES[0][0], texturasGOLPES[0][1],  texturasGOLPES[0][2],  texturasGOLPES[0][3]);

        animacionStuned  = new Animation<>(0.6f, texturaSTUNED[0][0], texturaSTUNED[0][1], texturaSTUNED[0][2], texturaSTUNED[0][3]);

        // Animación infinita
        animacionMoverse.setPlayMode(Animation.PlayMode.LOOP);
        animacionGolpe.setPlayMode(Animation.PlayMode.LOOP);
        animacionStuned.setPlayMode(Animation.PlayMode.LOOP);

        // Inicia el timer que contará tiempo para saber qué frame se dibuja
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
                sonido1.play(0.5f);

                this.mirandoA = this.mirandoA.DERECHA;
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
                sonido2.play(0.5f);

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
                frameDeAtaque = false;
                timerAnimacion += Gdx.graphics.getDeltaTime();
                region= animacionGolpe.getKeyFrame(timerAnimacion);
                if (mirandoA == mirandoA.IZQUIERDA) {
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }
                } else  if (mirandoA == mirandoA.DERECHA) {
                    if (region.isFlipX()) {
                        region.flip(true,false);
                    }
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                break;

            case STUNNED:
                region = animacionStuned.getKeyFrame(timerAnimacion);
                batch.draw(region, sprite.getX(), sprite.getY());
                break;

            case NEUTRAL:
            case PAUSADO:
                //System.out.println("DIBUJANDO, NEUTERAL");
                region = animacionMoverse.getKeyFrame(timerAnimacion);
                batch.draw(region, sprite.getX(), sprite.getY());
                sprite.draw(batch);
                break;


        }
    }

    private void comportamientoMinionS(String random){
 /* Orden Prioridades:
          PAUSADO
          MUERTO
          STUNNED
          retrasado
          ATACANDO
          MOVIENDO
        */
        char rngChar = (random.toCharArray())[7];
        int rng = Integer.parseInt(String.valueOf(rngChar));
        if (estadosEnemigo == EstadosEnemigo.PAUSADO) {
            estadosEnemigo = EstadosEnemigo.PAUSADO;
        } else if (salud <= 0) {
            estadosEnemigo = EstadosEnemigo.MUERTO;
        } else if (estadosEnemigo == EstadosEnemigo.STUNNED) {
            if (framesStunned <= -1) {
                sonidoDano.play(0.5f);
                puedoRecibirDano = false;
                framesStunned = 60;
                retrasado = false;
                framesAturdidos = -1;
                framesAtacando = 0;
            } else if (framesStunned == 0) {
                estadosEnemigo = EstadosEnemigo.NEUTRAL;
                puedoRecibirDano = true;
            }

            framesStunned -= 1;
        } else if (retrasado) {
            retrasar(rng);
        } else if (framesAtacando > 0) {
            estadosEnemigo = EstadosEnemigo.ATACANDO;
            framesAtacando -= 1;
        } else {

            float leftX = personaje.getX();
            float rightX = leftX + personaje.getSprite().getWidth();

            if ((sprite.getX() > rightX)) {
                mirandoA = MirandoA.IZQUIERDA;
                this.estadosEnemigo = EstadosEnemigo.MOV_IZQUIERDA;

            } else if (sprite.getX() + sprite.getWidth() < leftX) {
                mirandoA = MirandoA.IZQUIERDA;
                this.estadosEnemigo = EstadosEnemigo.MOV_DERECHA;
            } else {
                nextEstadoEnemigo = EstadosEnemigo.ATACANDO;
                retrasado = true;
                framesAtacando = 60;
            }
        }
    }

    private void comportamientoDelAlto(String random){
    /*
    este vato se mueve de derecha  izquierda, i
    zquierda a derecha
    stuneado
     */
    /* Orden Prioridades:
          PAUSADO
          MUERTO
          STUNNED
          retrasado
          ATACANDO
          MOVIENDO
        */
        char rngChar = (random.toCharArray())[7];
        int rng = Integer.parseInt(String.valueOf(rngChar));
        if (estadosEnemigo == EstadosEnemigo.PAUSADO) {
            estadosEnemigo = EstadosEnemigo.PAUSADO;
        } else if (salud <= 0) {
            estadosEnemigo = EstadosEnemigo.MUERTO;
        } else if (estadosEnemigo == EstadosEnemigo.STUNNED) {
            if (framesStunned <= -1) {
                sonidoDano.play(0.5f);
                puedoRecibirDano = false;
                framesStunned = 60;
                retrasado = false;
                framesAturdidos = -1;
                framesAtacando = 0;
            } else if (framesStunned == 0) {
                estadosEnemigo = EstadosEnemigo.NEUTRAL;
                puedoRecibirDano = true;
            }

            framesStunned -= 1;
        } else if (retrasado) {
            retrasar(rng);
        }


        tacleada();


    }

    private void tacleada(){
        System.out.println(getEstadoEnemigo());
        if (getEstadoEnemigo() == EstadosEnemigo.MOV_IZQUIERDA){
            if (getSprite().getX() < 0){
                setEstadosEnemigo(EstadosEnemigo.MOV_DERECHA);
            }
        }
        if (getEstadoEnemigo() == EstadosEnemigo.MOV_DERECHA){
            if (getSprite().getX() > 1100){
                setEstadosEnemigo(EstadosEnemigo.MOV_IZQUIERDA);
            }
        }
        if (getEstadoEnemigo() == EstadosEnemigo.NEUTRAL){
            setEstadosEnemigo(EstadosEnemigo.MOV_DERECHA);
        }
    }


    public void comportamiento(String random) {
      if (idTipoEnemigo == 1 || idTipoEnemigo == 2){
          comportamientoMinionS(random);
      }else
          comportamientoDelAlto(random);
    }

    private void retrasar(int rng) {
        estadosEnemigo = EstadosEnemigo.NEUTRAL;
        if (framesAturdidos <= -1) {
            this.framesAturdidos = rng;
        } else if (framesAturdidos == 0) {
            this.retrasado = false;
            estadosEnemigo = nextEstadoEnemigo;
        }
        framesAturdidos -= 1;
        // System.out.println(framesAturdidos);
    }



    public void recibirDano (int dano){
        if (puedoRecibirDano) {
            salud -= dano;
        }

    }



    public void setMirandoA(MirandoA mira) {
        this.mirandoA = mira;
    }


    public MirandoA getMirandoA() {
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
    public float getY(){ return this.sprite.getY(); }

    protected enum EstadosEnemigo {
        NEUTRAL,
        ATACANDO,
        STUNNED,
        MUERTO,
        MOV_IZQUIERDA,
        MOV_DERECHA,
        PAUSADO
    }

    protected enum MirandoA {
        DERECHA,
        IZQUIERDA
    }

    public boolean getFraneDeAtaque(){
        return frameDeAtaque;
    }

    public void generateBodyEnemigo(World mundo, int x) {

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, 200); //METROS

        bodyPersonaje = mundo.createBody(bodyDef);


    }



    public void actualizarEnemigo(World mundo) {

        //x = bodyPersonaje.getPosition().x;
        //y = bodyPersonaje.getPosition().y;

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
            case MUERTO:
                break;
        }
    }

}