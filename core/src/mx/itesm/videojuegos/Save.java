package mx.itesm.videojuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Save {
public Preferences prefkira;
public Preferences prefraoh;

    public Save() {
        this.prefkira =  Gdx.app.getPreferences("save-kira");
        this.prefraoh =  Gdx.app.getPreferences("save-raoh");

    }


public void saveSlotKira(String key, boolean unlock){
        prefkira.putBoolean(key, unlock);
        prefkira.flush();
}
public void saveSlotRaoh(String key,boolean unlock){
        prefraoh.putBoolean(key, unlock);
        prefraoh.flush();
}

}
