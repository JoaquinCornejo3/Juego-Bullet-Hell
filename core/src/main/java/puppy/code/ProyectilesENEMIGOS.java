package puppy.code;

import puppy.code.Controladores.Mostrable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.utils.TimeUtils;
import puppy.code.PJprincipal;
import puppy.code.Controladores.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;
import puppy.code.Controladores.Gota;
import puppy.code.Controladores.GotaFactory;

public class ProyectilesENEMIGOS implements Mostrable{
    
    private Array<Gota> rainDrops; // Lista de gotas (abstracción con polimorfismo)
    private Array<GotaFactory> factories; // Lista de fábricas de gotas
    private long lastDropTime;
    private int velocidadY;
    private int velocidadX;
    private Sound dropSound;
    private Music rainMusic;
    
    
    public ProyectilesENEMIGOS(Texture gotaMala, Texture gotaBuena, 
                               Texture gotaVida, Sound dropSound, 
                               Music rainMusic, int velocidadY) {
        
        this.dropSound = dropSound;
        this.rainMusic = rainMusic;
        this.velocidadY = velocidadY;
        this.rainDrops = new Array<>();
        this.factories = new Array<>();
        
        // Inicializar las fábricas concretas y agregarlas a la lista
        factories.add(new GotaMalaFactory(gotaMala));
        factories.add(new GotaBuenaFactory(gotaBuena));
        factories.add(new GotaVidaFactory(gotaVida));

    }
    
    public void inicializarFábricas(GotaFactory... gotaFactories) {
        for (GotaFactory factory : gotaFactories) {
            factories.add(factory);
        }
    }


    public void crear() {
        rainDrops.clear();
        crearGotaDeLluvia();
        rainMusic.setLooping(true);
        rainMusic.play();
    }
    
    
    private void crearGotaDeLluvia() {
        // Selecciona una fábrica al azar
        int index = MathUtils.random(0, factories.size - 1);
        Gota nuevaGota = factories.get(index).crearGota(MathUtils.random(0, 800 - 64), 480);
        rainDrops.add(nuevaGota);
        lastDropTime = TimeUtils.nanoTime();
    }
    
   
    public boolean actualizarMovimiento(PJprincipal PJpri, boolean cambio) {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearGotaDeLluvia();
        }

        for (int i = 0; i < rainDrops.size; i++) {
            Gota gota = rainDrops.get(i);
            gota.mover(velocidadY * Gdx.graphics.getDeltaTime());
            float posY = gota.getPosY();

            if (posY <= 0) {
                rainDrops.removeIndex(i);
                i--;
                continue;
            }

            if (gota.checkCollision(PJpri)) {
                gota.efecto(PJpri); // Aplica el efecto de la gota
                dropSound.play();
                rainDrops.removeIndex(i);
                i--;
            }
            
            if(cambio){
                gota.moverX(100);
            }
        }
        return true;
    }
    
    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (Gota gota : rainDrops) {
            gota.dibujar(batch);
        }
    }
    
    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }
    
    @Override
    public void dibujar(SpriteBatch batch) {
    }

    @Override
    public Rectangle getArea() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizarMovimiento() {
    }
    
    public void pausarMusica() {
        rainMusic.stop();
    }

    public void continuarMusica() {
        rainMusic.play();
    }
    
    public int getVelY(){
        return velocidadY;
    }
    
    public void setVelY(int velocidad){
        velocidadY = velocidad;
    }
    
    public int getVelX(){
        return velocidadX;
    }
    
    public void setVelX(int velocidadX){
        this.velocidadX = velocidadX;
    }
}