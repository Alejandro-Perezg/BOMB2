package mx.itesm.videojuegos;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PantallaCargando extends Pantalla{

    private final float TIEMPO_ENTRE_FRAME = 0.05f; 
    private Sprite spriteCargando; 
    private float timerAnimacion = TIEMPO_ENTRE_FRAME; 
    
    private AssetManager manager; //unico en el juego. 
    
    private Juego juego; 
    private TipoPantalla siguientePantalla; 
    private int avance; //0 - 100% de la carga de recursos. 
    
    private Texture texturaCargando; 
    
    public PantallaCargando(Juego juego, TipoPantalla siguiente){
        this.juego = juego; 
        this.siguientePantalla = siguiente; 
    }
    
    @Override
    public void show() {
        texturaCargando = new Texture("loading.png");
        spriteCargando = new Sprite(texturaCargando); 
        spriteCargando.setPosition(ANCHO/2-spriteCargando.getWidth(), ALTO/2 - spriteCargando.getHeight());
        
        manager = juego.getManager(); 
        cargarRecursosPantalla(); //cargar imagener, mp3, ...
        
        
    }

    private void cargarRecursosPantalla() {
        switch (siguientePantalla){
            case PANTALLAINICIO:
                cargarRecursosPantallaInicio();
                break;
            case MENU:
                //cargarRecursosMenu();
                break;
            case  PANTALLAACERCADE:
                //cargarRecursosPantallaAcercaDe();
                break;
            case  PANTALLASELECCIONPERSONAJE:
                //cargarRecursosPantallaSeleccionPersonaje();
                break; 
            case NIVEL1: 
                //cargarRecursosNievl1();
                break; 
                  
        }
    }

    private void cargarRecursosPantallaInicio() {
        manager.load("menus/menuInicio/TextoTocaContinuar.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0.5f, 0.4f,0.8f);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        spriteCargando.draw(batch);
        batch.end();

        //ACTUALIZAR
        timerAnimacion -=delta;
        /*if(timerAnimacion<= 0){
            spriteCargando.rotate(-20);
            timerAnimacion = TIEMPO_ENTRE_FRAME;
        }
        */
        actializarCargaRecursos();


    }

    private void actializarCargaRecursos() {
        if(manager.update()){
            switch (siguientePantalla) {
                case PANTALLAINICIO:
                    juego.setScreen(new PantallaInicio(juego));
                    break;
                case MENU:
                    juego.setScreen(new PantallaMenuPrincipal(juego));
                    break;
                case  PANTALLAACERCADE:
                    juego.setScreen(new PantallaAcercaDe(juego));
                    break;
                case  PANTALLASELECCIONPERSONAJE:
                    juego.setScreen(new PantallaSeleccionPersonaje(juego));
                    break;
                /*case NIVEL1:
                    juego.setScreen(new Nivel1());
                    break;*/
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
}
