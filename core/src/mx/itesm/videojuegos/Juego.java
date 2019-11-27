package mx.itesm.videojuegos;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;


public class Juego extends Game {
    public static boolean playMusic = true;


    private static final AssetManager manager = new AssetManager();;
    @Override
    public void create() {
        manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        setScreen(new PantallaCargando(this,TipoPantalla.PANTALLAINICIO)); //referencia del administracion  para pasar de pantalla a otr

    }

    public AssetManager getManager() {
        return manager;
    }


}







