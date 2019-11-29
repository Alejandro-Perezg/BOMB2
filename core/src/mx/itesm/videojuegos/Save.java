package mx.itesm.videojuegos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Save {
    public Preferences prefMusic;
    public Preferences prefLevel;


    public Save() {
        this.prefLevel = Gdx.app.getPreferences("save");
        this.prefMusic = Gdx.app.getPreferences("mute");


    }

    public void saveLevel(int nivelesDisponibles) {
        prefLevel.putInteger("save", nivelesDisponibles);
        prefLevel.flush();
    }

    public int getsavelevel() {
        return prefLevel.getInteger("save");
    }


    public void playMusic(boolean unlock) {
        prefMusic.putBoolean("mute", unlock);
        prefMusic.flush();
    }
}

