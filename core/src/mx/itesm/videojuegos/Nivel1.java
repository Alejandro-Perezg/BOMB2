package mx.itesm.videojuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
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
import static mx.itesm.videojuegos.Personaje.EstadosPersonaje.MUERTO;
import static mx.itesm.videojuegos.Personaje.EstadosPersonaje.NEUTRAL;



public class Nivel1  extends Nivel {
    private Juego juego;
    private AssetManager manager;

    //Variables de nivel
    private int idNivel = 1;
    private int score = 0;
    public boolean bloqueado;
    // PERSONAJE
    private Personaje personaje;
    private int fuerzaPersonaje;
    private String personajeS;
    //ENEMIGO
    private int fuerzaEnemigo;
    private int cantidadEnemigos;
    //TEXTURAS
    private Sprite sprite;
    private Texture texturaFondo;
    private Texture texturaPersonaje;
    private Texture TexturaPersonajeGolpe;
    private  Texture texturaPersonajeStuned;
    private Texture texturaEnemigo;
    private Texture textureEnemigoAtacando;
    private Texture texturaEnemigoStuned;
    private Texture texturaBigPaapa;
    private Texture texturaBigPapaStuned;
    private Sound sonidoEnemigo1;
    private Sound sonidoEnemigo2;

    private Texture texturaNemesis;
    private Texture texturaNemesisGolpe;
    private Texture texturaNemesisStuned;


    private Texture barraSaludArriba;
    private Texture barraSaludAbajo;

    private Texture barraEnergiaArriba;
    private Texture barraEnergiaAbajo;

    public Texture textureHearth;


    //palanca
    private Texture texturaPalanca;
    private Sprite spritePalanca;
    private float palancaY;
    // save
    private Save save;

    //MUSICA
    private Music musica;
    private Sound sonidoEnemigoDano;
    private Sound sonidoEnemigoDefault;
    //Variables de Screen;
    // Escena de menu (botones)
    private Stage escenaHUD;
    private Pausa escenaPausa;
    private GameOverStage escenaGameOver ;
    private VictoryStage escenaVictoria;
    //Estados
    EstadosNivel estado = EstadosNivel.NORMAL;

    private static final float RADIO = 15f;
    private World mundo; // Mundo paralelo donde se aplica la física.

    private Box2DDebugRenderer debugRenderer;

    /////TEXTOSSSS/////////

    private Texto salud;
    private Texto puntuacion;
    private Texto poderListo;
    private boolean cambioStageFinal = false;

    private int counter = 0;
    private int ph1;
    private int ph2;
    private int ph3;
    private int ph4;

    private int phIn1;
    private int enemigosMinimosFase1;    private int enemigosMinimosFase3;
    private int enemigosMinimosFase2;    private int enemigosMinimosFase4;

    public enum phase{
        PHASE1,
        PHASE2,
        PHASE3,
        PHASE4,
        fin,
        infinito
    }

    phase phaseJuego = phase.PHASE1;

    public void setphase(phase phase){
        this.phaseJuego = phase;
    }

    private String getPersonajeS(){
        return personajeS;
    }

    /////////ARRAY DE ENEMIGOS
    private ArrayList<Enemigo> arrayEnemigos = new ArrayList<>();
    private ArrayList<itemDropeado> arrayItems = new ArrayList<>();

    //Managers
    private ImpactManager impactManager;





    public Nivel1(Juego juego, Music musica, String personajeSeleccionado, int idNivel) {
        this.juego = juego;
        this.musica = musica;
        this.personajeS = personajeSeleccionado;
        this.idNivel = idNivel;
        manager = juego.getManager();
        this.save = new Save();
    }


    private void generarPersonaje() {
       /* fuerzaEnemigo = enemigo.fuerza;
        Personaje personaje= new Personaje(texturaPersonaje,ANCHO/2,fuerzaEnemigo);
*/
        personaje = new Personaje(texturaPersonaje, TexturaPersonajeGolpe, texturaPersonajeStuned ,10, 10, 30);
        personaje.generateBodyPersonaje(mundo);

    }

    private void generarEnemigos(int cantidad) {
        int spawn = 400;

        for (int i = 0; i < cantidad; i++) {
            //ystem.out.println(i);
            Enemigo enemigo;
            enemigo = new Enemigo(texturaEnemigo, textureEnemigoAtacando, texturaEnemigoStuned,spawn + spawn*i, 20,
                    personaje, sonidoEnemigoDefault, sonidoEnemigoDano, sonidoEnemigo1, sonidoEnemigo2, 1);
            if (i % 2 == 0) {
                enemigo.generateBodyEnemigo(mundo, (int) (Pantalla.ANCHO + 200 * i));
            } else{
                enemigo.generateBodyEnemigo(mundo, (-200 * i));
            }

            arrayEnemigos.add(enemigo);


        }
    }

    private void generarAlto(){
        int spawn = 1000;
        Enemigo enemigo;
        enemigo = new Enemigo(texturaBigPaapa, textureEnemigoAtacando, texturaBigPapaStuned,spawn, 20,
                personaje, sonidoEnemigoDefault, sonidoEnemigoDano, sonidoEnemigo1, sonidoEnemigo2, 0);
        enemigo.generateBodyEnemigo(mundo, (int)spawn);

        arrayEnemigos.add(enemigo);
    }

    private void showEnemigos(){
        switch (idNivel){
            case 1:
                System.out.println("NIVEL 1");
                phIn1 = 4;
                ph1 = 1;     ph2 = 1;     ph3 = 1;     ph4 = 1;
                enemigosMinimosFase1 = 2;   enemigosMinimosFase2 = 3;
                enemigosMinimosFase3 = 4;   enemigosMinimosFase4 = 5;
                generarEnemigos(phIn1);
                break;
            case 2:
                System.out.println("NIVEL 2");
                phIn1 = 5;
                ph1 = 2;     ph2 = 4;     ph3 = 8;     ph4 = 8;
                enemigosMinimosFase1 = 2;   enemigosMinimosFase2 = 3;
                enemigosMinimosFase3 = 4;   enemigosMinimosFase4 = 5;
                generarEnemigos(phIn1);
                break;
            case 3:
                System.out.println("NIVEL 3");
                phIn1 = 0;
                ph1 = 0;     ph2 = 0;     ph3 = 0;     ph4 = 0;
                enemigosMinimosFase1 = 2;   enemigosMinimosFase2 = 3;
                enemigosMinimosFase3 = 4;   enemigosMinimosFase4 = 5;

                if (getPersonajeS().equals("kira")){
                    texturaNemesis = this.manager.get("sprites_personaje/caminaRaohDer.png");
                    texturaNemesisGolpe = this.manager.get("sprites_personaje/golpeRaohDer.png");
                    texturaNemesisStuned = this.manager.get("sprites_personaje/rahStuned.png");

                }else {
                    texturaNemesis = this.manager.get("sprites_personaje/caminaKiraDer.png");
                    texturaNemesisGolpe = this.manager.get("sprites_personaje/golpeKiraDer.png");
                    texturaNemesisStuned = this.manager.get("sprites_personaje/kiraStuned.png");

                }
                Enemigo enemigo;
                enemigo = new Enemigo(texturaNemesis,texturaNemesisGolpe,texturaNemesisStuned,1000, 20,
                        personaje, sonidoEnemigoDefault, sonidoEnemigoDano, sonidoEnemigo1, sonidoEnemigo2, 2);
                enemigo.generateBodyEnemigo(mundo, 1000);

                arrayEnemigos.add(enemigo);
                generarEnemigos(phIn1);

                break;
            case 999:
                generarEnemigos(3);
                phIn1 = 10;
                System.out.println("NIVEL INFINITO");
                break;
        }
    }

    private phase getPhase(){
        return phaseJuego;
    }

    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////
                                                                    ///DIFERENCIA DE NIVELES
                                                                    ///CAMBIAR COMPARACION Y  ENEMIGOS GENERADOS...

    public void phaseManager(){
        switch (phaseJuego) {
            case PHASE1:
                if(arrayEnemigos.size()<enemigosMinimosFase1) {
                    System.out.println("PHASE1");
                    generarEnemigos(ph1);
                    setphase(getPhase().PHASE2);
                }
                break;

            case PHASE2:
                if(arrayEnemigos.size()<enemigosMinimosFase2) {


                    System.out.println("PHASE2");
                    generarEnemigos(ph2);
                    setphase(getPhase().PHASE3);

                    if (idNivel == 2){


                        generarAlto();
                    }

                }
                break;

            case PHASE3:
                if(arrayEnemigos.size()<enemigosMinimosFase3) {

                    System.out.println("PHASE3");
                    generarEnemigos(ph3);
                    setphase(getPhase().PHASE4);
                }
                break;

            case PHASE4:
                if(arrayEnemigos.size()<enemigosMinimosFase4) {

                    System.out.println("PHASE4");
                    generarEnemigos(ph4);
                    setphase(getPhase().fin);
                }
                break;
            case infinito:

                if(arrayEnemigos.size()<2){
                    counter = counter+1;
                    System.out.println("INFINITO");
                    generarEnemigos(counter);
                }

                break;

        }
    }

    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////    ////////



    public void showSalud() {
        ///AGREGAR PROCEDURAL PIXMAP...

        barraSaludArriba = personaje.crearbarraSalud();
        barraSaludAbajo = personaje.crearBarraSaludAtras();

        salud = new Texto();

    }
    public void updateBarraSalud(){
        barraSaludArriba = personaje.crearbarraSalud();
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
        puntuacion.mostrarMensaje(batch, String.valueOf(score), 400, 700);
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
        if (save.prefMusic.getBoolean("mute")){
            musica.play();
        } else{
            musica.stop();
        }
        //re
    }


    @Override
    public void show() {
        manager = juego.getManager();
        cargarTexturas();
        crearMundo();
        crearObjetos();
        reproducirMusica();
        cargarSFX();
        generarPersonaje();
        showEnemigos();
        impactManager = new ImpactManager(personaje, arrayEnemigos);
        showSalud();
        showScore();
        //showPoderListo();
        crearHUD();

        spritePalanca = new Sprite(texturaPalanca);
        spritePalanca.setPosition(260, 0);

        //escenaGameOver = new GameOverStage(vista, batch);
      //  impactManager = new ImpactManager(personaje, arrayEnemigos);

    }

    private void cargarSFX() {
        sonidoEnemigoDano = manager.get("Audio/enemigoDaño.mp3");
        sonidoEnemigoDefault = manager.get("Audio/enemigoSonido1.mp3");
        sonidoEnemigo1 = manager.get("Audio/enemigoSonido1.mp3");
        sonidoEnemigo2 = manager.get("Audio/enemigoSonido2.mp3");

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
                switch (personaje.getEstadosPersonaje()) {
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

        this.manager.load("fondos/cabezaArena.png", Texture.class);
        this.manager.load("sprites_personaje/caminaKiraDer.png", Texture.class);
        this.manager.load("sprites_personaje/golpeKiraDer.png", Texture.class);
        this.manager.load("sprites_personaje/kiraStuned.png", Texture.class);
        this.manager.load("sprites_personaje/caminaRaohDer.png", Texture.class);
        this.manager.load("sprites_personaje/golpeRaohDer.png", Texture.class);
        this.manager.load("sprites_personaje/rahStuned.png", Texture.class);
        this.manager.load("sprites_enemigo1/enemigo.png", Texture.class);
        this.manager.load("sprites_enemigo1/enemigoGolpear.png", Texture.class);
        this.manager.load("sprites_enemigo1/enemigoStoned.png", Texture.class);
        this.manager.load("sprites_enemigo1/bigPapa.png", Texture.class);
        this.manager.load("sprites_enemigo1/bigPapaStuned.png", Texture.class);
        this.manager.load("Nivel/palanca.png", Texture.class);
        this.manager.load("Nivel/heart_80x80.png", Texture.class);
        this.manager.load("fondos/rocas.png",Texture.class);
        this.manager.load("fondos/estatua.png",Texture.class);
        //Sonidos
        this.manager.load("Audio/enemigoDaño.mp3", Sound.class);
        this.manager.load("Audio/enemigoSonido1.mp3", Sound.class);
        this.manager.load("Audio/enemigoSonido2.mp3", Sound.class);
        this.manager.finishLoading();


        if (idNivel ==1){
            texturaFondo = this.manager.get("fondos/cabezaArena.png");
        }

        if (idNivel ==2){
            texturaFondo = this.manager.get("fondos/rocas.png");
        }

        if (idNivel ==3){
            texturaFondo = this.manager.get("fondos/estatua.png");
        }


        if(personajeS == "kira") {
            texturaPersonaje = this.manager.get("sprites_personaje/caminaKiraDer.png");
            TexturaPersonajeGolpe = this.manager.get("sprites_personaje/golpeKiraDer.png");
            texturaPersonajeStuned = this.manager.get("sprites_personaje/kiraStuned.png");
        }else {
            texturaPersonaje = this.manager.get("sprites_personaje/caminaRaohDer.png");
            TexturaPersonajeGolpe = this.manager.get("sprites_personaje/golpeRaohDer.png");
            texturaPersonajeStuned = this.manager.get("sprites_personaje/rahStuned.png");
        }

        texturaEnemigo = this.manager.get("sprites_enemigo1/enemigo.png");
        textureEnemigoAtacando = this.manager.get("sprites_enemigo1/enemigoGolpear.png");
        texturaEnemigoStuned = this.manager.get("sprites_enemigo1/enemigoStoned.png");
        texturaBigPaapa = this.manager.get("sprites_enemigo1/bigPapa.png");
        texturaBigPapaStuned = this.manager.get("sprites_enemigo1/bigPapaStuned.png");

        texturaPalanca = this.manager.get("Nivel/palanca.png");
        textureHearth = this.manager.get("Nivel/heart_80x80.png");
    }



    @Override
    public void render(float delta) {
        System.out.println(estado);
        DecimalFormat df = new DecimalFormat("#.#############");
        personaje.actualizarPersonaje();

        //Cambia el estado del juego. esto no se puede queda asi...
        revisarEstadoNivel();

        for (int i = 0; i <arrayEnemigos.size(); i++) {
              arrayEnemigos.get(i).comportamiento(df.format(delta));
              arrayEnemigos.get(i).actualizarEnemigo(mundo);
           // System.out.println("Enemigo: " + i + "Mirando a: " + arrayEnemigos.get(i).getMirandoA());
        }

        for (int i = 0; i < arrayItems.size(); i++){
            //System.out.println(arrayItems.get(i).toString());
            arrayItems.get(i).actualizarItem();
        }

        updateBarraSalud();
        eliminarEnemigosMuertos();
        eliminarItemsRecogidos();
        phaseManager();
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

        switch (estado){
            case PAUSA:
                for (int i = 0; i <arrayEnemigos.size(); i++) {

                    arrayEnemigos.get(i).setEstadosEnemigo(Enemigo.EstadosEnemigo.PAUSADO);
                }
                escenaPausa.draw();

                if (!escenaPausa.isActive()) {
                    estado = EstadosNivel.NORMAL;
                    escenaPausa.setActive(true);
                    crearHUD();
                    escenaPausa.dispose();
                    Gdx.input.setInputProcessor(escenaHUD);
                    for (int i = 0; i <arrayEnemigos.size(); i++) {
                        arrayEnemigos.get(i).setEstadosEnemigo(Enemigo.EstadosEnemigo.NEUTRAL);
                    }
                }
                break;

            case NORMAL:
                escenaHUD.draw();
                batch.begin();
                spritePalanca.draw(batch);
                batch.end();
                break;

            case PIERDE:
                escenaGameOver.draw();
                break;

            case GANA:
                escenaVictoria.draw();
                break;

        }


        mundo.step(1 / 60f, 6, 2);
    }

    private void revisarEstadoNivel() {
        if (this.arrayEnemigos.size() == 0) {


            this.estado = EstadosNivel.GANA;
            if (personajeS.equals("kira")) {
                save.saveSlotKira("unlock2",true);

            }
            if (personajeS.equals("raoh")){
               save.saveSlotRaoh("unlock2",true);
            }

            if (!cambioStageFinal) {
                escenaVictoria = new VictoryStage(vista, batch, personajeS);
                escenaHUD.dispose();
                escenaVictoria.crearVictoryStage(juego, musica);
            }
            cambioStageFinal = true;

        }



        if (personaje.getEstadosPersonaje() == MUERTO) {
            this.estado = EstadosNivel.PIERDE;

            if (!cambioStageFinal) {
                escenaGameOver = new GameOverStage(vista, batch, personajeS);
                escenaHUD.dispose();
                escenaGameOver.creargameOverStage(juego, musica, idNivel);
            }
            cambioStageFinal = true;

        }
    }





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

    public void eliminarEnemigosMuertos(){

        for (int i = 0; i <  arrayEnemigos.size(); i++) {

            if (arrayEnemigos.get(i).estadosEnemigo == Enemigo.EstadosEnemigo.MUERTO){
                arrayItems.add(new itemDropeado(100, textureHearth, mundo, (int)arrayEnemigos.get(i).getX(), 100));
                //System.out.println("aaaaaa");
                score = score + arrayEnemigos.get(i).getPuntos();
                arrayEnemigos.remove(i);

            }

        }
    }

    public void eliminarItemsRecogidos(){
        for (int i = 0; i< arrayItems.size(); i++){
            //System.out.println(arrayItems.get(i));
            if (arrayItems.get(i).getxBody() < personaje.getX()-20 && personaje.getX() < arrayItems.get(i).getxBody()+100){
                personaje.curarPersonaje(arrayItems.get(i).getSaludDeItem());
                arrayItems.remove(i);
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
        PAUSA,
        PIERDE,
        GANA
    }
}
