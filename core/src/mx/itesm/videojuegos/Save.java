package mx.itesm.videojuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Save {
    public Preferences  prefMusic;
    public Preferences prefkira;
    public Preferences prefraoh;

    public Save() {
        this.prefkira =  Gdx.app.getPreferences("save-kira");
        this.prefraoh =  Gdx.app.getPreferences("save-raoh");
        this.prefMusic = Gdx.app.getPreferences("mute");
    }


public void saveSlotKira(String key, boolean unlock){
        prefkira.putBoolean(key, unlock);
        prefkira.flush();
}
public void saveSlotRaoh(String key,boolean unlock){
        prefraoh.putBoolean(key, unlock);
        prefraoh.flush();
}

public void playMusic (boolean unlock){
        prefMusic.putBoolean("mute", unlock);
        prefMusic.flush();
    }
    public boolean getPrefkira(String key) {
        return prefkira.getBoolean(key);
    }

    public boolean getPrefraoh(String key){
        return  prefraoh.getBoolean(key);
    }
}
