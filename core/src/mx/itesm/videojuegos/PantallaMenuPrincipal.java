package mx.itesm.videojuegos;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

class PantallaMenuPrincipal extends Pantalla{
    private final Juego juego;
    private Save save;

    //FONDO
    private Texture texturaFondo;
    //STAGES
    private Stage escenaHUD;
    private Pausa escenaOpciones;

    //ESTADOS
    private Estado estado = Estado.NORMAL;

    //AUDIO
    public Music musica;

    //Manager
    private AssetManager manager;

    //TEXTURAS
    private Texture texturaTitulo;
    private Texture texturaJugar;
    private Texture texturaJugarPr;
    private Texture texturaAcercaDe;
    private Texture texturaAcercaDePr;
    private Texture texturaOpciones;
    private Texture texturaOpcionesPr;
    private Texture texturaSalir;


    public PantallaMenuPrincipal (Juego juego) {
        this.juego = juego;
        this.save = new Save();


    }

    public PantallaMenuPrincipal(Juego juego, Music music){
        this.juego = juego;
        this.musica = music;
        this.save = new Save();
    }

    @Override
    public void show() {
        manager = juego.getManager();
        cargarTexturas();
        cargarAudios(manager);
        crearHUD();

    }

    private void cargarAudios(AssetManager manager) {
        if (musica == null) {
            manager.load("menus/music/09 Come and Find Me - B mix.mp3", Music.class);
            manager.finishLoading();
            musica = manager.get("menus/music/09 Come and Find Me - B mix.mp3");
            musica.setLooping(true);
        }
        if (save.prefMusic.getBoolean("mute")){
            musica.play();
        } else{
            musica.stop();
        }
    }

    private void crearHUD() {
        escenaHUD = new Stage(vista);
        escenaHUD.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.5f)));

        //Boton jugar
        TextureRegionDrawable trdTitle = new TextureRegionDrawable(new TextureRegion(texturaTitulo));
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(texturaJugar));
        TextureRegionDrawable trdAcercaDe = new TextureRegionDrawable(new TextureRegion(texturaAcercaDe));
        TextureRegionDrawable trdAcercaDePr = new TextureRegionDrawable(new TextureRegion(texturaAcercaDePr));
        TextureRegionDrawable trdOpciones = new TextureRegionDrawable(new TextureRegion(texturaOpciones));
        TextureRegionDrawable trdOpcionesPr = new TextureRegionDrawable(new TextureRegion(texturaOpcionesPr));
        TextureRegionDrawable trdJugarPre = new TextureRegionDrawable(new TextureRegion(texturaJugarPr));
        TextureRegionDrawable trdSalir = new TextureRegionDrawable(new TextureRegion(texturaSalir));

        ImageButton btnJugar = new ImageButton(trdJugar,trdJugarPre);
        ImageButton btnAcerecaDe = new ImageButton(trdAcercaDe,trdAcercaDePr);
        ImageButton btnOpciones = new ImageButton(trdOpciones,trdOpcionesPr);
        Image titulo = new Image(trdTitle);
        ImageButton salir = new ImageButton(trdSalir);


        titulo.setPosition(ANCHO/2-(titulo.getWidth()/2), ALTO - 20 - titulo.getHeight());
        //btnJugar.setPosition(ANCHO/2 - (btnJugar.getWidth()/2), ALTO/2);
        btnAcerecaDe.setPosition( 100 , 40);

        //btnAcerecaDe.setPosition(ANCHO/2 - (btnAcerecaDe.getWidth()/2), ALTO/2-btnAcerecaDe.getHeight()-25);
        btnJugar.setPosition((btnJugar.getWidth()) +200,  40);

        //btnOpciones.setPosition(ANCHO/2 - (btnOpciones.getWidth()/2), ALTO/2-btnOpciones.getHeight()*2-50);
        btnOpciones.setPosition(btnJugar.getWidth() + btnAcerecaDe.getWidth() + 300,40);
        salir.setPosition(5,ALTO - salir.getHeight()*3);

        //Evento de boton.
        btnJugar.addListener(new ClickListener(){
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    super.clicked(event, x, y);
                                    //INSTRUCCIONES
                                    escenaHUD.addAction(Actions.sequence(Actions.fadeOut(0.5f),Actions.run(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaCargando(juego, TipoPantalla.PANTALLASELECCIONNIVEL, musica));
                                        }
                                    })));
                                }
                            }
        );

        btnAcerecaDe.addListener(new ClickListener(){
                                 @Override
                                 public void clicked(InputEvent event, float x, float y) {
             super.clicked(event, x, y);
             //INSTRUCCIONe
             escenaHUD.addAction(Actions.sequence(Actions.moveBy(texturaFondo.getWidth(),0,1),Actions.run(new Runnable() {
                 @Override
                 public void run() {
                     ((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaCargando(juego, TipoPantalla.PANTALLAACERCADE, musica));
                 }
             })));             }
         }
        );
        btnOpciones.addListener(new ClickListener(){
                                 @Override
                                 public void clicked(InputEvent event, float x, float y) {
             super.clicked(event, x, y);
             //INSTRUCCIONE
             estado = Estado.PAUSA;
             if (escenaOpciones == null){
                 escenaOpciones = new Pausa(vista, batch);
             }
             escenaOpciones.crearOpcionesMenuPrincipal(juego,musica);
             }
         }
        );
        //rxdctfvygbuhnijmok,l
        salir.addListener(new ClickListener(){
                                     @Override
                                     public void clicked(InputEvent event, float x, float y) {
             super.clicked(event, x, y);
             //INSTRUCCIONe
             musica.pause();

             escenaHUD.addAction(Actions.sequence(Actions.fadeOut(0.5f),Actions.run(new Runnable() {
                 @Override
                 public void run() {
                     Gdx.app.exit();
                 }
             })));             }
                                 }
        );
//ctdyfyghujk

        escenaHUD.addActor(titulo);
        escenaHUD.addActor(btnAcerecaDe);
        escenaHUD.addActor(btnJugar);
        escenaHUD.addActor(btnOpciones);
        escenaHUD.addActor(salir);
        Gdx.input.setInputProcessor(escenaHUD);
    }


    private void cargarTexturas() {
        manager.load("fondos/estatua.png", Texture.class);

        manager.load("menus/menuPantalla/title.png",Texture.class);
        manager.load("menus/menuPantalla/btn_jugar.png",Texture.class);
        manager.load("menus/menuPantalla/btn_acerca_de.png",Texture.class);
        manager.load("menus/menuPantalla/btn_acerca_de_pr.png",Texture.class);
        manager.load("menus/menuPantalla/btn_opciones.png",Texture.class);
        manager.load("menus/menuPantalla/btn_opciones_pr.png",Texture.class);
        manager.load("menus/menuPantalla/btn_jugar_pr.png",Texture.class);
        manager.load("menus/menuPantalla/exit.png",Texture.class);


        manager.finishLoading();
        texturaFondo = manager.get("fondos/estatua.png");
        texturaJugar = manager.get("menus/menuPantalla/btn_jugar.png");
        texturaJugarPr = manager.get("menus/menuPantalla/btn_jugar_pr.png");
        texturaAcercaDe = manager.get("menus/menuPantalla/btn_acerca_de.png");
        texturaAcercaDePr = manager.get("menus/menuPantalla/btn_acerca_de_pr.png");
        texturaOpciones = manager.get("menus/menuPantalla/btn_opciones.png");
        texturaOpcionesPr = manager.get("menus/menuPantalla/btn_opciones_pr.png");
        texturaSalir = manager.get("menus/menuPantalla/exit.png");
        texturaTitulo = manager.get("menus/menuPantalla/title.png");
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        //batch escalaTodo de acuerdo a la visat y la camara
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        batch.end();

        if (estado == Estado.PAUSA) {
            escenaOpciones.draw();
            if (!escenaOpciones.isActive()){
                estado = Estado.NORMAL;
                escenaOpciones.setActive(true);
                Gdx.input.setInputProcessor(escenaHUD);
            }

        } else {
            escenaHUD.act(Gdx.graphics.getDeltaTime());
            escenaHUD.draw();

        }


    }


    @Override
    public void resize(int width, int height) {
        vista.update(width,height);
    }

    @Override
    public void pause() {

    }
    @Override
    public void resume() {

    }
    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
     //   texturaFondo.dispose(); //liberar
    }

}

enum Estado{
    PAUSA,
    NORMAL
}