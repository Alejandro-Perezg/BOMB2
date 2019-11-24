package mx.itesm.videojuegos;

import java.util.ArrayList;

public class ImpactManager {

    //Personaje
    private Personaje personaje;
    private boolean personajeAtacando;

    //Enemigos
    private ArrayList<Enemigo> arrayEnemigos;
    private ArrayList<Enemigo> arrayEnemigosAtacando;

    //rangos de ataque
    private float rangoAtaquePersonaje = personaje.getSprite().getWidth() /2;
    private float areaDeAtaquePersonajeIzquierda;
    private float areaDeAtaquePersonajeDerecha;
    private float rangoAtaqueEnemigo = rangoAtaquePersonaje;
    private ArrayList<Float> areasDeAtaqueEnemigoIzquierda;
    private ArrayList<Float> areasDeAtaqueEnemigoDerecha;


    public ImpactManager(Personaje personaje, ArrayList<Enemigo> arrayEnemigos){
        this.arrayEnemigos = arrayEnemigos;
        this.personaje = personaje;
    }

    private void revisarEnemigosAtacando(){
        Enemigo enemigoIterado;
        for(int i = 0; i < arrayEnemigos.size(); i++){
            enemigoIterado = arrayEnemigos.get(i);
            if (enemigoIterado.getEstadoEnemigo() == Enemigo.EstadosEnemigo.ATACANDO) {
                arrayEnemigosAtacando.add(enemigoIterado);
                crearAreaDeAtaqueEnemigo(enemigoIterado);
            }
        }
    }

    private void revisarPersonajeAtacando(){
        if (personaje.getEstadosPersonaje() == Personaje.EstadosPersonaje.ATACANDO) this.personajeAtacando = true;
        else this.personajeAtacando = false;
    }



    public void revisarAtaques(){
        revisarPersonajeAtacando();
        revisarEnemigosAtacando();
        if (personajeAtacando) {
            crearAreaDeAtaquePersonaje();
            for(int i = 0; i < arrayEnemigos.size(); i++){
                Enemigo enem = arrayEnemigos.get(i);
                float left = enem.getX();
                float right =left + enem.getSprite().getWidth();

            }
        }

    }

    private void crearAreaDeAtaqueEnemigo(Enemigo enem){
        float x = enem.getX();
        if (enem.getMirandoA() == Enemigo.mirandoA.DERECHA) {
            areasDeAtaqueEnemigoIzquierda.add(x + enem.getSprite().getWidth()/2);
            areasDeAtaqueEnemigoDerecha.add(x + rangoAtaqueEnemigo + enem.getSprite().getWidth());
        } else{
            areasDeAtaqueEnemigoIzquierda.add(x  - rangoAtaqueEnemigo);
            areasDeAtaqueEnemigoDerecha.add( x + enem.getSprite().getWidth()/2);
        }

    }

    private void crearAreaDeAtaquePersonaje() {
        float x = personaje.getX();
        if (personaje.mirandoA == Personaje.mirandoA.DERECHA) {
            areaDeAtaquePersonajeIzquierda = x + personaje.getSprite().getWidth()/2;
            areaDeAtaquePersonajeDerecha = x + personaje.getSprite().getWidth() + rangoAtaquePersonaje;
        } else{
            areaDeAtaquePersonajeIzquierda = x - rangoAtaquePersonaje;
            areaDeAtaquePersonajeDerecha = x + personaje.getSprite().getWidth()/2;
        }
    }


}