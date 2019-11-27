package mx.itesm.videojuegos;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;


import java.util.Arrays;



public class Personaje {

    private boolean sexo;
    private int salud = 1000;
    private int saludInicial = salud;
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

    private TextureRegion texturaStuned;
    private Animation stuned;

    public Body bodyPersonaje;

    public boolean puedoRecibirDano;

    EstadosPersonaje estadosPersonaje = EstadosPersonaje.NEUTRAL;

    static mirandoA mirandoA;
    private int framesStunned = 0;
    private int framesRecovery = 0;
    public Personaje(Texture texture , Texture textureGolpe,Texture textureStuned,float x, float y,int fuerzaEnemigo) {
        daño = fuerzaEnemigo;
        this.texturaCompleta= new TextureRegion(texture);
        this.texturaCompletaGOLPE = new TextureRegion(textureGolpe);
        this.texturaStuned = new TextureRegion(textureStuned);

        TextureRegion[][] texturas = texturaCompleta.split(220,389);
        animacion = new Animation(0.2f, texturas[0][0], texturas[0][1], texturas[0][2],texturas[0][3]);
        animacionDerecha = new Animation(0.3f, texturas[0][0]);

        TextureRegion[][] texturasGOLPES = texturaCompletaGOLPE.split(220, 389);
        GOLPE = new Animation(0.1f, texturasGOLPES[0][0], texturasGOLPES[0][1], texturasGOLPES[0][2],texturasGOLPES[0][3]);

        TextureRegion[][] texturaStunedRegion  = texturaStuned.split(210,390);
        stuned = new Animation(0.1f,texturaStunedRegion[0][0],texturaStunedRegion[0][1],texturaStunedRegion[0][2],texturaStunedRegion[0][3]);

        timerAnimacion = 0;
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        GOLPE.setPlayMode(Animation.PlayMode.LOOP);
        stuned.setPlayMode(Animation.PlayMode.LOOP);

        sprite = new Sprite(texturas[0][0]);
        sprite.setPosition(x,y);

        this.estadosPersonaje = EstadosPersonaje.NEUTRAL;

        setMirandoA(mirandoA.DERECHA);
    }


    public void render(SpriteBatch batch){

        // Dibuja el personaje dependiendo del estadoMovimiento

        switch (estadosPersonaje) {
            case MOV_DERECHA:
                setMirandoA(mirandoA.DERECHA);
                timerAnimacion += Gdx.graphics.getDeltaTime();

                TextureRegion region = (TextureRegion) animacion.getKeyFrame(timerAnimacion);
                batch.draw(region,sprite.getX(),sprite.getY());

                this.setMirandoA(mirandoA.DERECHA);
                if (region.isFlipX()) {
                    region.flip(true, false);
                }
                break;


            case MOV_IZQUIERDA:
                setMirandoA(mirandoA.IZQUIERDA);
                //System.out.println("Dibujando, moviendo" );

                timerAnimacion += Gdx.graphics.getDeltaTime();

                region = (TextureRegion) animacion.getKeyFrame(timerAnimacion);

                this.setMirandoA(mirandoA.IZQUIERDA);
                if (!region.isFlipX()) {
                    region.flip(true,false);
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

                timerAnimacion += Gdx.graphics.getDeltaTime();

                region = (TextureRegion) animacionDerecha.getKeyFrame(timerAnimacion);

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
            case STUNNED:
                timerAnimacion += Gdx.graphics.getDeltaTime();

                region = (TextureRegion) stuned.getKeyFrame(timerAnimacion);

                batch.draw(region,sprite.getX(),sprite.getY());
                break;

        }
    }




    private void empujarEnemigo(){

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
        if (this.getEstadosPersonaje() != EstadosPersonaje.STUNNED) {
            this.estadosPersonaje = estadosPersonaje;
        }
    }

    public EstadosPersonaje getEstadosPersonaje(){

        return estadosPersonaje;
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

    public void curarPersonaje(int saludRecibida){
        if (salud + saludRecibida >= saludInicial)  {
            salud = saludInicial;
        }else {
            salud = salud + saludRecibida;
        }
    }
    public void generateBodyPersonaje(World mundo){
        //Body Def
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(200, 200); //METROS
        bodyPersonaje = mundo.createBody(bodyDef);  //Objeto simulado.

    }


    public void setFramesStunned(int frames){
        this.framesStunned = frames;
    }

    public int getFramesRecovery(){
        return framesRecovery;
    }

    public void actualizarPersonaje() {
        //System.out.println(getEstadosPersonaje());
        //System.out.println("Recovery Frames: " + framesRecovery);
        System.out.println("Salud Personaje: " + salud);



        float x = bodyPersonaje.getPosition().x;
        float y = bodyPersonaje.getPosition().y;

        getSprite().setPosition(x - 5, y - 200f);

        if (framesStunned > 0) {
            setEstadosPersonaje(EstadosPersonaje.STUNNED);
        }  else if(framesStunned == 0){
            framesRecovery = 10;
            framesStunned -= 1;
            estadosPersonaje = EstadosPersonaje.NEUTRAL;
        }

        if (framesRecovery < 0) {
            puedoRecibirDano = true;
        } else{
            framesRecovery -= 1;
        }

        if(salud <= 0){
            estadosPersonaje = EstadosPersonaje.MUERTO;
        }


        switch (getEstadosPersonaje()) {
            case STUNNED:
                puedoRecibirDano = false;
                framesStunned -= 1;
                break;

            case MOV_DERECHA:
                if (sprite.getX() + sprite.getWidth() + 5 > Pantalla.ANCHO) {
                    bodyPersonaje.setTransform(x, y, 0);
                } else{
                    bodyPersonaje.setTransform(x + 5, y, 0);
                }

                break;

            case MOV_IZQUIERDA:
                if (sprite.getX() < -5) {
                    bodyPersonaje.setTransform(x, y, 0);
                } else{
                    bodyPersonaje.setTransform(x - 5, y, 0);
                }
                break;
            case NEUTRAL:
                break;
            case MUERTO:
                bodyPersonaje.setTransform(-1000, y, 0);
                break;
        }
    }

    public Texture crearBarraSaludAtras(){

        Pixmap pixmap = new Pixmap(300, 50, Pixmap.Format.RGBA8888);

        pixmap.setColor(1,0,0,1);

        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        Texture textura = new Texture(pixmap);
        pixmap.dispose();
        return textura;
    }


    public Texture crearbarraSalud() {

        float aImprimir = (float)salud/saludInicial*300;

        Pixmap pixmap = new Pixmap(300, 50, Pixmap.Format.RGBA8888);

        pixmap.setColor(0,1,0,1);
        pixmap.fillRectangle(0 , 0, (int) aImprimir, pixmap.getHeight());
        Texture textura = new Texture(pixmap);
        pixmap.dispose();
        return textura;
    }

    public void recibirDano(int dano){
        if (puedoRecibirDano) {
            salud -= dano;
        }
    }

    public Texture crearBarraEnergiaAtras(){
        Pixmap pixmap = new Pixmap(200, 50, Pixmap.Format.RGBA8888);

        pixmap.setColor(1,0,0,1);

        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        Texture textura = new Texture(pixmap);
        pixmap.dispose();
        return textura;
    }

    public Texture crearBarraEnergia(){
        Pixmap pixmap = new Pixmap(200, 50, Pixmap.Format.RGBA8888);

        pixmap.setColor(1,0,0,1);

        pixmap.fillRectangle(0, 0, pixmap.getWidth(), pixmap.getHeight());
        Texture textura = new Texture(pixmap);
        pixmap.dispose();
        return textura;
    }

}
