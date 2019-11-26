package mx.itesm.videojuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


import com.badlogic.gdx.math.Vector2;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static mx.itesm.videojuegos.Personaje.EstadosPersonaje.ATACANDO;
import static mx.itesm.videojuegos.Personaje.EstadosPersonaje.MOV_DERECHA;
import static mx.itesm.videojuegos.Personaje.EstadosPersonaje.MOV_IZQUIERDA;
import static mx.itesm.videojuegos.Personaje.EstadosPersonaje.NEUTRAL;


public class Nivel1  extends Nivel {
    private Juego juego;
    //Variables de nivel
    private int idNivel = 1;
    private int score = 0;
    public int coordenadasDano;
    // PERSONAJE
    private Personaje personaje;
    private int fuerzaPersonaje;
    //ENEMIGO


    private int fuerzaEnemigo;
    private int cantidadEnemigos;
    //TEXTURAS
    private Sprite sprite;
    private Texture texturaFondo;
    private Texture texturaPersonaje;
    private Texture TexturaPersonajeGolpe;
    private Texture texturaEnemigo;
    private Texture textureEnemigoAtacando;
    private Texture texturaEnemigoStuned;

    private Texture barraSaludArriba;
    private Texture barraSaludAbajo;

    private Texture barraEnergiaArriba;
    private Texture barraEnergiaAbajo;

    public Texture textureHearth;


    //palanca
    private Texture texturaPalanca;
    private Sprite spritePalanca;
    private float palancaY;


    //MUSICA
    private Music musica;
    private Sound efecto;
    //Variables de Screen;
    // Escena de menu (botones)
    private Stage escenaHUD;
    private Pausa escenaPausa;
    //Estados
    EstadosNivel estado = EstadosNivel.NORMAL;

    private static final float RADIO = 15f;
    private World mundo; // Mundo paralelo donde se aplica la física.


    private Box2DDebugRenderer debugRenderer;


    /////TEXTOSSSS/////////

    private Texto salud;
    private Texto puntuacion;
    private Texto poderListo;


    private boolean phase1 = false;
    private boolean phase2 = false;
    private boolean phase3 = false;
    private boolean phase4 = false;




    /////////ARRAY DE ENEMIGOS
    private ArrayList<Enemigo> arrayEnemigos = new ArrayList<>();
    private ArrayList<itemDropeado> arrayItems = new ArrayList<>();


    private ImpactManager impactManager;




    Nivel1(Juego juego, Music musica) {
        this.juego = juego;
        this.musica = musica;
    }


    private void generarPersonaje() {
       /* fuerzaEnemigo = enemigo.fuerza;
        Personaje personaje= new Personaje(texturaPersonaje,ANCHO/2,fuerzaEnemigo);
*/
        personaje = new Personaje(texturaPersonaje, TexturaPersonajeGolpe, 10, 10, 30);
        personaje.generateBodyPersonaje(mundo);



    }

    private void generarEnemigos() {


        for (int i = 0; i < 5; i++) {
            System.out.println(i);
            Enemigo enemigo;
            enemigo = new Enemigo(texturaEnemigo, textureEnemigoAtacando, texturaEnemigoStuned,100*i, 20, personaje);
            enemigo.generateBodyEnemigo(mundo, 100*i);
            arrayEnemigos.add(enemigo);


            phase1 = true;
        }



    }

    /////////varia
    public void phaseManager(){

    }


    public void showSalud() {
        ///AGREGAR PROCEDURAL PIXMAP...

        barraSaludArriba = personaje.crearbarraSalud();
        barraSaludAbajo = personaje.crearBarraSaludAtras();

        salud = new Texto();

    }

    public void showScore() {

        puntuacion = new Texto();

    }

    public void showPoderListo() {
        ////AGREGAR PROCEDURAL PIXMAP
        poderListo = new Texto();
    }


    public void renderSalud(SpriteBatch batch) {
        int saludInt = personaje.getSalud();

        //salud.mostrarMensaje(batch, "SALUD... " + String.valueOf(saludInt), 100, 700);
        batch.draw(barraSaludAbajo,50,650);
        batch.draw(barraSaludArriba,50,650);

    }

    public void renderScore(SpriteBatch batch) {
        puntuacion.mostrarMensaje(batch, "PUNTUACION..." + String.valueOf(score), 500, 700);


    }

    public void rendePoderListo(SpriteBatch batch) {
        int intPoder = personaje.getPoder();

        if (intPoder >= 100) {
            poderListo.mostrarMensaje(batch, "PODER LISTO", 500, 700);
        } else {
            poderListo.mostrarMensaje(batch, "PODER..." + intPoder, 500, 700);
        }
    }




    private void renderEnemigo(SpriteBatch batch) {

     /*
        for (int i = enemyBodies.size() - 1; i > 0; i--) {
            arrayEnemigos.get(i).render(batch);

            System.out.println(enemyBodies.get(i).toString());
            System.out.println(arrayEnemigos.get(i).toString());
            System.out.println(i);
        }
*/
        for (int i = 0; i <arrayEnemigos.size(); i++){
            //System.out.println(arrayEnemigos.get(i-1));
            arrayEnemigos.get(i).render(batch);
        }

    }

    public void renderItems(){
        for (int i = 0; i <arrayItems.size(); i++){
            //System.out.println(arrayEnemigos.get(i-1));
            arrayItems.get(i).render(batch);
        }
    }

    private void reproducirMusica() {

    }


    @Override
    public void show() {
        cargarTexturas();
        crearMundo();
        crearObjetos();
        reproducirMusica();
        generarPersonaje();
        generarEnemigos();
        impactManager = new ImpactManager(personaje, arrayEnemigos);
        showSalud();
        showScore();
        //showPoderListo();
        crearHUD();

        spritePalanca = new Sprite(texturaPalanca);
        spritePalanca.setPosition(260, 0);

      //  impactManager = new ImpactManager(personaje, arrayEnemigos);


    }

    private void crearHUD() {



        escenaHUD = new Stage(vista);
        //BOTON pausa.
        TextureRegionDrawable trdPausa = new TextureRegionDrawable(new TextureRegion(new Texture("Nivel/btnpausa.png")));
        TextureRegionDrawable trdPausaPressed = new TextureRegionDrawable(new TextureRegion(new Texture("Nivel/btnpausaPressed.png")));
        final ImageButton btnPausa = new ImageButton(trdPausa, trdPausaPressed);
        btnPausa.setPosition(ANCHO - btnPausa.getWidth(), ALTO - btnPausa.getHeight());


        //BOTONES
        TextureRegionDrawable trdAtacar = new TextureRegionDrawable(new TextureRegion(new Texture("Nivel/fist2.png")));
        final TextureRegionDrawable trdAtacarPressed = new TextureRegionDrawable(new TextureRegion(new Texture("Nivel/fist2Pressed.png")));
        final ImageButton btnAtacar = new ImageButton(trdAtacar, trdAtacarPressed);
        btnAtacar.setPosition(ANCHO - btnAtacar.getWidth() - 50, btnAtacar.getHeight() - 200);

        //Palanca
        TextureRegionDrawable trdPalanca = new TextureRegionDrawable(new TextureRegion(new Texture("Nivel/palancaBarra.png")));
        final Image barraPalanca = new Image(trdPalanca);
        barraPalanca.setPosition(110, 0);
        final float centroPalanca = barraPalanca.getWidth() / 2;
        palancaY = (barraPalanca.getY() + (barraPalanca.getHeight() / 2)) - 50;

        //Listeners Palanca
        barraPalanca.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                spritePalanca.setPosition((x + barraPalanca.getX()) - 50, palancaY);
                if (x < centroPalanca) {
                    personaje.setEstadosPersonaje(MOV_IZQUIERDA);
                } else {
                    personaje.setEstadosPersonaje(MOV_DERECHA);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                spritePalanca.setPosition((barraPalanca.getX() + centroPalanca) - 50, palancaY);
                switch (personaje.estadosPersonaje) {
                    case ATACANDO:
                        personaje.setEstadosPersonaje(ATACANDO);
                        break;
                    default:
                        personaje.setEstadosPersonaje(NEUTRAL);
                        break;

                }
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (y < barraPalanca.getHeight() && y > 0 && x > 0 && x < barraPalanca.getWidth()) {
                    if (x < 50) {
                        spritePalanca.setPosition(barraPalanca.getX(), palancaY);
                    } else if (x > barraPalanca.getWidth() - 50) {
                        spritePalanca.setPosition(barraPalanca.getX() + barraPalanca.getWidth() - 100, palancaY);
                    } else {
                        spritePalanca.setPosition((x + barraPalanca.getX()) - 50, palancaY);
                    }

                    if (x < centroPalanca && x > 0) {
                        personaje.setEstadosPersonaje(MOV_IZQUIERDA);
                    } else if (x < barraPalanca.getWidth() && x > centroPalanca) {
                        personaje.setEstadosPersonaje(MOV_DERECHA);
                    }
                } else {
                    spritePalanca.setPosition((barraPalanca.getX() + centroPalanca) - 50, palancaY);
                    switch (personaje.estadosPersonaje) {
                        case ATACANDO:
                            personaje.setEstadosPersonaje(ATACANDO);
                            break;
                        default:
                            personaje.setEstadosPersonaje(NEUTRAL);
                            break;

                    }
                }
            }


        });

        btnAtacar.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                personaje.setEstadosPersonaje(ATACANDO);
                ////////////////////////////////////////////////////////////////

                for (int i = 0; i <arrayEnemigos.size(); i++){
                    //System.out.println(i);
                    //System.out.println(arrayEnemigos.get(i));
                    //System.out.println(arrayEnemigos.get(i).getEstadoEnemigo());

                    ////////

                    //AQUI SE CALCULAN LAS COLISIONES
                    //System.out.println(i);
                    //arrayEnemigos.get(i).setEstadosEnemigo(Enemigo.EstadosEnemigo.MUERTO);


                    ////////

                }




                score = 10;
                personaje.cargarPoder(20);

                /////////////////////////////////////////////////////////////////
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                switch (personaje.getEstadosPersonaje()) {
                    case MOV_DERECHA:
                        personaje.setEstadosPersonaje(MOV_DERECHA);
                        break;
                    case MOV_IZQUIERDA:
                        personaje.setEstadosPersonaje(MOV_IZQUIERDA);
                        break;
                    case ATACANDO:
                        personaje.setEstadosPersonaje(NEUTRAL);
                        break;
                    default:
                        personaje.setEstadosPersonaje(NEUTRAL);
                        break;
                }

            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {

                //System.out.println("Pos x: " + x);
                //System.out.println("Pos y: " + y);

                if (x > btnAtacar.getWidth() || x < 0 || y > btnAtacar.getHeight() || y < 0) {
                    switch (personaje.getEstadosPersonaje()) {
                        case MOV_DERECHA:
                            personaje.setEstadosPersonaje(MOV_DERECHA);
                            break;
                        case MOV_IZQUIERDA:
                            personaje.setEstadosPersonaje(MOV_IZQUIERDA);
                            break;
                        default:
                            personaje.setEstadosPersonaje(NEUTRAL);
                            break;
                    }
                } else {
                    personaje.setEstadosPersonaje(ATACANDO);
                }
            }


        });


        btnPausa.addListener(new ClickListener() {
                                 @Override
                                 public void clicked(InputEvent event, float x, float y) {
                                     super.clicked(event, x, y);
                                     //INSTRUCCIONE
                                     if (escenaPausa == null) {
                                         escenaPausa = new Pausa(vista, batch);
                                     }
                                     escenaHUD.dispose();
                                     estado = EstadosNivel.PAUSA;
                                     escenaPausa.crearPausa(juego, musica);

                                 }
                             }
        );


        escenaHUD.addActor(btnPausa);
        escenaHUD.addActor(barraPalanca);
        escenaHUD.addActor(btnAtacar);

        Gdx.input.setInputProcessor(escenaHUD);

    }

    private void cargarTexturas() {
        texturaFondo = new Texture("fondos/fond1.jpg");

        texturaPersonaje = new Texture("sprites_personaje/caminaKiraDer.png");
        TexturaPersonajeGolpe = new Texture("sprites_personaje/golpeKiraDer.png");

        texturaEnemigo = new Texture("sprites_enemigo1/enemigo.png");

        textureEnemigoAtacando = new Texture("sprites_enemigo1/enemigoGolpear.png");
        texturaEnemigoStuned = new Texture("sprites_enemigo1/enemigoStoned.png");

        texturaPalanca = new Texture("Nivel/palanca.png");

        textureHearth = new Texture("Nivel/heart_80x80.png");
    }


    @Override
    public void render(float delta) {
        DecimalFormat df = new DecimalFormat("#.#############");

        //ACTUALIZAR NAVE
        personaje.actualizarPersonaje();

        for (int i = 0; i <arrayEnemigos.size(); i++) {
            //arrayEnemigos.get(i).setEstadosEnemigo(Enemigo.EstadosEnemigo.ATACANDO);
//            System.out.println(i);
              arrayEnemigos.get(i).comportamiento(df.format(delta));
              arrayEnemigos.get(i).actualizarEnemigo(mundo);
        }

        for (int i = 0; i < arrayItems.size(); i++){
            System.out.println(arrayItems.get(i).toString());
            arrayItems.get(i).actualizarItem();
        }

        eliminarEnemigosMuertos();

        //TODO arreglar problema de caida de framrete
        impactManager.revisarAtaques();

        borrarPantalla();


        //batch escalaTodo de acuerdo a la visat y la camara
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        rendePersonaje(batch);
        renderSalud(batch);
        renderScore(batch);
        //rendePoderListo(batch);
        renderEnemigo(batch); //Cambiar por array


        renderItems();
        batch.end();


        if (estado == EstadosNivel.PAUSA) {
            escenaPausa.draw();

            if (!escenaPausa.isActive()) {
                estado = EstadosNivel.NORMAL;
                escenaPausa.setActive(true);
                crearHUD();
                escenaPausa.dispose();
                Gdx.input.setInputProcessor(escenaHUD);
            }
        } else {
            escenaHUD.draw();
            batch.begin();
            spritePalanca.draw(batch);

            batch.end();
        }


        mundo.step(1 / 60f, 6, 2);
    }
/*
    private void actualizarPersonaje() {
        float x = personaje.bodyPersonaje.getPosition().x;
        float y = personaje.bodyPersonaje.getPosition().y;

        personaje.getSprite().setPosition(x - 5, y - 200f);
        x = personaje.bodyPersonaje.getPosition().x;
        y = personaje.bodyPersonaje.getPosition().y;


/*
        System.out.println(personaje.getX());
        System.out.println(personaje.getEstadosPersonaje());
        System.out.println(personaje.mirandoA);

        switch (personaje.getEstadosPersonaje()) {
            case MOV_DERECHA:
                personaje.bodyPersonaje.setTransform(x + 5, y, 0);
                break;

            case MOV_IZQUIERDA:
                personaje.bodyPersonaje.setTransform(x - 5, y, 0);
                break;
            case NEUTRAL:
                break;
        }
    }
*/
    private void rendePersonaje(SpriteBatch batch) {
        personaje.render(batch);
    }


    private void crearMundo() {
        Box2D.init();  //Se crea el mundo virtual.
        Vector2 gravedad = new Vector2(0, 0);
        mundo = new World(gravedad, false);
        debugRenderer = new Box2DDebugRenderer();
    }

    private void crearObjetos() {

        //PLATAFORMA
        BodyDef bodyPisodef = new BodyDef();
        bodyPisodef.type = BodyDef.BodyType.StaticBody;
        bodyPisodef.position.set(ANCHO / 4, 10);

        Body bodyPiso = mundo.createBody(bodyPisodef);
        PolygonShape pisoShape = new PolygonShape();
        pisoShape.setAsBox(ANCHO / 4, 10);  //LA MITAD DEL TOTAL

        bodyPiso.createFixture(pisoShape, 0);

        pisoShape.dispose();


    }

    ////////se va a eliminar, implementar en enemigo

    /*
    private void generateBodyEnemigo() {
        Body enemigoGenerado;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(200, 200); //METROS
        enemigoGenerado = mundo.createBody(bodyDef);  //Objeto simulado.

        enemyBodies.add(enemigoGenerado);
        //System.out.println(enemyBodies.size()-1);

    }
*/
////SE VA A ELIMINAR
    /*
    private void generateBodyPersonaje(){
        //Body Def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(200, 200); //METROS
        bodyPersonaje = mundo.createBody(bodyDef);  //Objeto simulado.
    }
    */

    public void eliminarEnemigosMuertos(){

        for (int i = 0; i <  arrayEnemigos.size(); i++) {
            //System.out.println(arrayEnemigos.get(i-1));
            //System.out.println(i-1);
            //System.out.println(arrayEnemigos.size());
            //System.out.println(i);

            if (arrayEnemigos.get(i).estadosEnemigo == Enemigo.EstadosEnemigo.MUERTO){
                arrayItems.add(new itemDropeado(10, textureHearth, mundo, (int)arrayEnemigos.get(i).getX(), 100));
                System.out.println("aaaaaa");
                arrayEnemigos.remove(i);

            }

        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
    protected void borrarPantalla() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    enum EstadosNivel{
        NORMAL,
        PAUSA
    }
}
