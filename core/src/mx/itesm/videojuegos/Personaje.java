package mx.itesm.videojuegos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Personaje extends  Objeto{

    private boolean sexo;
    private int salud;
    private int daño;   //recibe
    private int poder;
    private float velocidad;
    private float rangoDeAtaque = 100;
    private float rangoDePatada = 120;
    private int porcentajeDeStamina;
    private float porcentajePoder;
    public int fuerza; //de daño/ ataque.


    //SPRITES
    private Sprite sprite;
    private TextureRegion texturaCompleta;
    private TextureRegion[][] texturas;


    private Animation animacion;    // Caminando
    private float timerAnimacion;   // tiempo para calcular el frame


    EstadosPersonaje estadosPersonaje = EstadosPersonaje.NEUTRAL;

    public Personaje(Texture texture, float x, int fuerzaEnemigo) {
        daño = fuerzaEnemigo;
        this.texturaCompleta= new TextureRegion(texture);
        TextureRegion[][] texturas = texturaCompleta.split(32,54);

        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion = 0;

    }


    public float atacar(int daño){

        return rangoDeAtaque; //Se llama en nivel y con este valor se calcula en personaje si esta denro del area de ataque.

    }

    private void empujarEnemigo(){

    }

    public void identificalAreaDeDaño (float rangoDeAtaque){
        if(rangoDeAtaque == getX()){
            recibirDano();
        }

    }
    private void recogerItem (){

    }

    public void alentarTiempo(){

    }


    //////SET/GET ESTADOS PERSONAJE

    public void setVelocidad(int velocidad){
        this.velocidad = velocidad;
    }

    public float getVelocidad(){
        return velocidad;
    }

    public float getX(){
        return sprite.getX();
    }

    public float getY(){
        return sprite.getY();
    }

    public void setEstadosPersonaje(EstadosPersonaje estadosPersonaje){
        this.estadosPersonaje = estadosPersonaje;
    }

    public EstadosPersonaje getEstadosPersonaje(){

        return estadosPersonaje;
    }


    private void recibirDano(){ //ESTO esta mal por que si llega a 0 con un ataque va a segiur vivo.
        if(salud - daño >0){
            salud -= daño;
        }else{
            estadosPersonaje = EstadosPersonaje.MUERTO;
        }
    }


    protected enum EstadosPersonaje{
        NEUTRAL,
        ATACANDO,
        STUNNED,
        MUERTO,
        MOV_IZQUIERDA,
        MOV_DERECHA
    }

}
