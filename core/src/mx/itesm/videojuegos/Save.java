package mx.itesm.videojuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Save {
    public Preferences  prefMusic;
    public Preferences prefLevel;


    public Save() {
        this.prefLevel =  Gdx.app.getPreferences("save-kira");
        this.prefMusic = Gdx.app.getPreferences("mute");



    }


public void saveLevel(String key, int unlock){
        prefLevel.putInteger(key, unlock);
        prefLevel.flush();
}

public void playMusic (boolean unlock){
        prefMusic.putBoolean("mute", unlock);
        prefMusic.flush();
    }

    public int getsavelevel(String key) {
        return prefLevel.getInteger(key);
    }
}
