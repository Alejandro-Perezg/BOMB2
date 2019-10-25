package mx.itesm.videojuegos;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;





import java.util.Arrays;



public class Personaje {

    private boolean sexo;
    private int salud = 100;
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

    private Animation animacionDerecha;

    private Animation GOLPE;
    private  TextureRegion texturaCompletaGOLPE;
    private TextureRegion[][] texturasGOLPES;

    EstadosPersonaje estadosPersonaje = EstadosPersonaje.NEUTRAL;

    mirandoA mirandoA;

    public Personaje(Texture texture, Texture textureGolpe,float x, float y,int fuerzaEnemigo) {

        daño = fuerzaEnemigo;
        this.texturaCompleta= new TextureRegion(texture);

        TextureRegion[][] texturas = texturaCompleta.split(250,393);
        animacion = new Animation(0.2f, texturas[0][0], texturas[0][1], texturas[0][2],texturas[0][3]);
        animacionDerecha = new Animation(0.2f, texturas[0][0]);

        this.texturaCompletaGOLPE = new TextureRegion(texture);
        TextureRegion[][] texturasGOLPES = texturaCompletaGOLPE.split(250, 393);
        GOLPE = new Animation(0.2f, texturasGOLPES[0][0], texturasGOLPES[0][1], texturasGOLPES[0][2], texturasGOLPES[0][3]) ;

        timerAnimacion = 0;

        animacion.setPlayMode(Animation.PlayMode.LOOP);
        GOLPE.setPlayMode(Animation.PlayMode.LOOP);
        sprite = new Sprite(texturas[0][0]);
        sprite.setPosition(x,y);





        this.estadosPersonaje = EstadosPersonaje.NEUTRAL;

        setMirandoA(mirandoA.DERECHA);

        System.out.println(toString());

    }




    public void render(SpriteBatch batch){

        // Dibuja el personaje dependiendo del estadoMovimiento

        switch (estadosPersonaje) {
            case MOV_DERECHA:
            case MOV_IZQUIERDA:
                //System.out.println("Dibujando, moviendo" );

                timerAnimacion += Gdx.graphics.getDeltaTime();

                 TextureRegion region = (TextureRegion) animacion.getKeyFrame(timerAnimacion);

                if (estadosPersonaje == EstadosPersonaje.MOV_IZQUIERDA) {
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,false);
                    }
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                break;
            case ATACANDO:

                timerAnimacion += Gdx.graphics.getDeltaTime();
                    region= (TextureRegion) GOLPE.getKeyFrame(timerAnimacion);

                if (mirandoA == mirandoA.IZQUIERDA) {
                    if (!region.isFlipX()) {
                        region.flip(true,false);
                    }
                } else {
                    if (region.isFlipX()) {
                        region.flip(true,false);
                    }
                }
                batch.draw(region,sprite.getX(),sprite.getY());
                break;

            case NEUTRAL:
                //System.out.println("DIBUJANDO, NEUTERAL");
                region = (TextureRegion) animacionDerecha.getKeyFrame(timerAnimacion) ;
                batch.draw(region, sprite.getX(), sprite.getY());
                sprite.draw(batch);
                break;

        }
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


    public void  setEstadosPersonaje(EstadosPersonaje estadosPersonaje){
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

    public Sprite getSprite(){
        return sprite;
    }


    public int  getSalud(){
        return salud;
    }

    private void setSalud(){
        this.salud = salud;
    }

    public int getPoder(){
        return poder;
    }

    public void setPoder(int poder){
        this.poder = poder;
    }

    public void cargarPoder(int agregar){
        this.poder = getPoder() + agregar;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "sexo=" + sexo +
                ", salud=" + salud +
                ", daño=" + daño +
                ", poder=" + poder +
                ", velocidad=" + velocidad +
                ", rangoDeAtaque=" + rangoDeAtaque +
                ", rangoDePatada=" + rangoDePatada +
                ", porcentajeDeStamina=" + porcentajeDeStamina +
                ", porcentajePoder=" + porcentajePoder +
                ", fuerza=" + fuerza +
                ", sprite=" + sprite +
                ", texturaCompleta=" + texturaCompleta +
                ", texturas=" + Arrays.toString(texturas) +
                ", animacion=" + animacion +
                ", timerAnimacion=" + timerAnimacion +
                ", estadosPersonaje=" + estadosPersonaje +
                '}';
    }

    protected enum EstadosPersonaje{
        NEUTRAL,
        ATACANDO,
        STUNNED,
        MUERTO,
        MOV_IZQUIERDA,
        MOV_DERECHA
    }

    public  void setMirandoA(mirandoA mira){
        this.mirandoA = mira;
    }


    public mirandoA getMirandoA(){
        return mirandoA;
    }

    protected  enum mirandoA{
        DERECHA,
        IZQUIERDA
    }

}
