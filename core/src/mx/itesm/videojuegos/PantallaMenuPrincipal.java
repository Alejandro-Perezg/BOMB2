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

    public PantallaMenuPrincipal (Juego juego) {
        this.juego = juego;

    }

    public PantallaMenuPrincipal(Juego juego, Music music){
        this.juego = juego;
        this.musica = music;
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
        if (juego.playMusic == true){
            musica.play();
        } if (juego.playMusic ==false){
            musica.stop();
        }

    }


    private void crearHUD() {
        escenaHUD = new Stage(vista);
        escenaHUD.addAction(Actions.sequence(Actions.alpha(0),Actions.fadeIn(0.5f)));


        //Boton jugar
        TextureRegionDrawable trdTitle = new TextureRegionDrawable(new TextureRegion(new Texture("menus/menuPantalla/title.png")));
        TextureRegionDrawable trdJugar = new TextureRegionDrawable(new TextureRegion(new Texture("menus/menuPantalla/btn_jugar.png")));
        TextureRegionDrawable trdAcercaDe = new TextureRegionDrawable(new TextureRegion(new Texture("menus/menuPantalla/btn_acerca_de.png")));
        TextureRegionDrawable trdAcercaDePr = new TextureRegionDrawable(new TextureRegion(new Texture("menus/menuPantalla/btn_acerca_de_pr.png")));
        TextureRegionDrawable trdOpciones = new TextureRegionDrawable(new TextureRegion(new Texture("menus/menuPantalla/btn_opciones.png")));
        TextureRegionDrawable trdOpcionesPr = new TextureRegionDrawable(new TextureRegion(new Texture("menus/menuPantalla/btn_opciones_pr.png")));
        TextureRegionDrawable trdJugarPre = new TextureRegionDrawable(new TextureRegion(new Texture("menus/menuPantalla/btn_jugar_pr.png")));
        TextureRegionDrawable trdSalir = new TextureRegionDrawable(new TextureRegion(new Texture("menus/menuPantalla/exit.png")));

        ImageButton btnJugar = new ImageButton(trdJugar,trdJugarPre);
        ImageButton btnAcerecaDe = new ImageButton(trdAcercaDe,trdAcercaDePr);
        ImageButton btnOpciones = new ImageButton(trdOpciones,trdOpcionesPr);
        Image titulo = new Image(trdTitle);
        ImageButton salir = new ImageButton(trdSalir);


        titulo.setPosition(ANCHO/2-(titulo.getWidth()/2), ALTO - 20 - titulo.getHeight());
        //btnJugar.setPosition(ANCHO/2 - (btnJugar.getWidth()/2), ALTO/2);
        btnJugar.setPosition( 100 , 40);

        //btnAcerecaDe.setPosition(ANCHO/2 - (btnAcerecaDe.getWidth()/2), ALTO/2-btnAcerecaDe.getHeight()-25);
        btnAcerecaDe.setPosition((btnJugar.getWidth()) +200,  40);

        //btnOpciones.setPosition(ANCHO/2 - (btnOpciones.getWidth()/2), ALTO/2-btnOpciones.getHeight()*2-50);
        btnOpciones.setPosition(btnJugar.getWidth() + btnAcerecaDe.getWidth() + 300,40);
        salir.setPosition(5,ALTO - salir.getHeight()-10);

        //Evento de boton.
        btnJugar.addListener(new ClickListener(){
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    super.clicked(event, x, y);
                                    //INSTRUCCIONES
                                    musica.pause();
                                    escenaHUD.addAction(Actions.sequence(Actions.fadeOut(0.5f),Actions.run(new Runnable() {
                                        @Override
                                        public void run() {
                                            ((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaSeleccionNivel(juego,musica));
                                        }
                                    })));
                                    //juego.setScreen(new PantallaSeleccionNivel(juego,musica));


                                }
                            }
        );
        btnAcerecaDe.addListener(new ClickListener(){
                                 @Override
                                 public void clicked(InputEvent event, float x, float y) {
             super.clicked(event, x, y);
             //INSTRUCCIONe
             musica.pause();

             escenaHUD.addAction(Actions.sequence(Actions.fadeOut(0.5f),Actions.run(new Runnable() {
                 @Override
                 public void run() {
                     ((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaAcercaDe(juego,musica));
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


        escenaHUD.addActor(titulo);
        escenaHUD.addActor(btnAcerecaDe);
        escenaHUD.addActor(btnJugar);
        escenaHUD.addActor(btnOpciones);
        escenaHUD.addActor(salir);
        Gdx.input.setInputProcessor(escenaHUD);
    }


    private void cargarTexturas() {
        texturaFondo = new Texture( "fondos/estatua.png");

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
        texturaFondo.dispose(); //liberar
    }

}

enum Estado{
    PAUSA,
    NORMAL
}