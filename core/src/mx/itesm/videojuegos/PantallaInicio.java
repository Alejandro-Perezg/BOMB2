package mx.itesm.videojuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

class PantallaInicio extends Pantalla {

    private final Juego juego;

    //fondo
    private Texture texturaFondo;
    private Texture title;
    private Texture textoTocar;

    //escena
    private Stage escenaMenu;

    public PantallaInicio(Juego juego) {
        this.juego = juego;   //this de pantalla inicio consrtuctor
    }

    @Override
    public void show() { //mostrar en pantalla fisica. ini items, texturas.
        configurarVista();
        crearMenu();
        //cargar fondo
        texturaFondo = new Texture( "menus/FondoMenu.jpeg");
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);
        title = new Texture("menus/menuInicio/title.png");
        textoTocar = new Texture("menus/menuInicio/TextoTocaContinuar.png");

        Gdx.input.setInputProcessor(new ProcesadorEntrada());
    }

    private void configurarVista() {
        camara = new OrthographicCamera();
        camara.position.set(ANCHO/2,ALTO/2,0);
        camara.update();

        vista = new StretchViewport(ANCHO, ALTO, camara);

        batch = new SpriteBatch(); //administra los trazos.
    }


    @Override
    public void render(float delta) {
        borrarPantalla();
        //batch escalaTodo de acuerdo a la visat y la camara
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(texturaFondo, 0, 0);
        batch.draw(title, ANCHO/2-(title.getWidth()/2) , 2*ALTO/3);
        batch.draw(textoTocar, ANCHO/2-(title.getWidth()/2+(textoTocar.getWidth()/2) -200) , 2*ALTO/3 - 300);
        batch.end();
        escenaMenu.draw();
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

        //  Escucha y atiende eventos de touch
        class ProcesadorEntrada implements InputProcessor {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector3 v = new Vector3(screenX,screenY,0);
                camara.unproject(v);
                juego.setScreen(new PantallaMenuPrincipal(juego));
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        }
    }
