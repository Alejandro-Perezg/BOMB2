package mx.itesm.videojuegos;

import java.util.ArrayList;

public class ImpactManager {

    //Personaje
    private Personaje personaje;
    private boolean personajeAtacando;

    //Enemigos
    private ArrayList<Enemigo> arrayEnemigos;
    private ArrayList<Enemigo> arrayEnemigosAtacando = new ArrayList<>();

    //rangos de ataque
    private float rangoAtaquePersonaje;
    private float areaDeAtaquePersonajeIzquierda;
    private float areaDeAtaquePersonajeDerecha;
    private float rangoAtaqueEnemigo = rangoAtaquePersonaje;
    private ArrayList<Float> areasDeAtaqueEnemigoIzquierda = new ArrayList<>();
    private ArrayList<Float> areasDeAtaqueEnemigoDerecha = new ArrayList<>();


    public ImpactManager(Personaje personaje, ArrayList<Enemigo> arrayEnemigos){
        this.arrayEnemigos = arrayEnemigos;
        this.personaje = personaje;
        this.rangoAtaquePersonaje = this.personaje.getSprite().getWidth() /2;
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

        //Revisa ataques personaje
        if (personajeAtacando) {
            crearAreaDeAtaquePersonaje();
            for(int i = 0; i < arrayEnemigos.size(); i++){
                Enemigo enem = arrayEnemigos.get(i);
                float left = enem.getX();
                float right =left + 175;//enem.getSprite().getWidth();
                if (right < areaDeAtaquePersonajeDerecha && right > areaDeAtaquePersonajeIzquierda ||
                left > areaDeAtaquePersonajeIzquierda && left < areaDeAtaquePersonajeDerecha) {
                    enem.setEstadosEnemigo(Enemigo.EstadosEnemigo.STUNNED);
                    enem.recibirDano(10);
                    /*
                    System.out.println( "\n"+ "PERSONAJE Left: " + areaDeAtaquePersonajeIzquierda);
                    System.out.println("PERSONAJE Right: " + areaDeAtaquePersonajeDerecha);
                    System.out.println("ENEMIGO: " + i + " Left: " + left);
                    System.out.println("ENEMIGO: " + i + " Right: " + right + "\n");

                     */
                }

            }
        }

        //Revisa ataques enemigos
        if (arrayEnemigosAtacando.size() > 0) {
            for(int i = 0; i < arrayEnemigosAtacando.size(); i++){
                if(arrayEnemigosAtacando.get(i).getEstadoEnemigo() == Enemigo.EstadosEnemigo.ATACANDO) {
                    float left = personaje.getX();
                    float right = left + personaje.getSprite().getWidth();
                    if (right < areasDeAtaqueEnemigoDerecha.get(i) && right > areasDeAtaqueEnemigoIzquierda.get(i) ||
                    left > areasDeAtaqueEnemigoIzquierda.get(i) && left < areasDeAtaqueEnemigoDerecha.get(i)) {
                        if (personaje.getEstadosPersonaje() != Personaje.EstadosPersonaje.STUNNED && personaje.getFramesRecovery() < 0) {
                            personaje.recibirDano(5);
                            personaje.setFramesStunned(30);
                            personaje.setEstadosPersonaje(Personaje.EstadosPersonaje.STUNNED);
                        }
                    }
                }
            }
        }

    }

    private void crearAreaDeAtaqueEnemigo(Enemigo enem){
        float x = enem.getX();
        if (enem.getMirandoA() == Enemigo.mirandoA.DERECHA) {
            areasDeAtaqueEnemigoIzquierda.add(x + 175/2);//enem.getSprite().getWidth()/2);
            areasDeAtaqueEnemigoDerecha.add(x + rangoAtaqueEnemigo + 175);//enem.getSprite().getWidth());
        } else{
            areasDeAtaqueEnemigoIzquierda.add(x  - rangoAtaqueEnemigo);
            areasDeAtaqueEnemigoDerecha.add( x + 175/5);//enem.getSprite().getWidth()/2);
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
