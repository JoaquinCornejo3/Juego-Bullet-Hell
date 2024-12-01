package puppy.code;

import puppy.code.Controladores.Mostrable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import puppy.code.Controladores.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;

public class ProyectilesENEMIGOS implements Mostrable {
    
    private Array<Gota> rainDrops; 
    private Array<GotaFactory> factories; 
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

        if (gotaMala == null || gotaBuena == null || gotaVida == null) {
            throw new IllegalArgumentException("Las texturas de las gotas no pueden ser nulas.");
        }

        // Inicializar las fábricas concretas y agregarlas a la lista
        factories.add(new GotaMalaFactory(gotaMala));
        factories.add(new GotaBuenaFactory(gotaBuena));
        factories.add(new GotaVidaFactory(gotaVida));
    }
    
    public void inicializarFábricas(GotaFactory... gotaFactories) {
        for (GotaFactory factory : gotaFactories) {
            factories.add(factory);
        }
        
    private Texture gotaBuena;
    private Texture gotaMala;
    private Texture gotaGod;
    private Sound dropSound;
    private Music rainMusic;

    public ProyectilesENEMIGOS(Texture gotaBuena, Texture gotaMala, Texture gotaGod, Sound ss, Music mm, int velocidadY) {
        rainMusic = mm;
        dropSound = ss;
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
        this.gotaGod = gotaGod;
        this.velocidadY = velocidadY;
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
        Gota nuevaGota = factories.get(index).crearGota(MathUtils.random(0, Gdx.graphics.getWidth() - 64), Gdx.graphics.getHeight());
        rainDrops.add(nuevaGota);
        lastDropTime = TimeUtils.nanoTime();
    }
    
    public boolean actualizarMovimiento(PJprincipal PJpri, boolean cambio) {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearGotaDeLluvia();
        }

        for (int i = 0; i < rainDrops.size; i++) {
            Gota gota = rainDrops.get(i);
            gota.setEstrategiaMovimiento(new MovimientoVertical(velocidadY));
            gota.mover();

            if (gota.getPosY() <= 0) {
                rainDrops.removeIndex(i);
                i--;
                continue;
            }

            // Verificar colisión con el jugador
            if (gota.checkCollision(PJpri)) {
                gota.efecto(PJpri); 
                dropSound.play();
                rainDrops.removeIndex(i);
                i--;
                
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64); //0, 800 - 64
        raindrop.y = 480;
        raindrop.width = 48; //64
        raindrop.height = 48; //64
        rainDropsPos.add(raindrop);
        
        // ver el tipo de gota    
        int tipo = MathUtils.random(1,10);
        if(tipo <= 3){
            rainDropsType.add(2);
        }
        if (tipo >= 3 && tipo <= 9){
            rainDropsType.add(1);
        }
        if(tipo == 10){
            rainDropsType.add(3);
        }
        
        lastDropTime = TimeUtils.nanoTime();
    }
    
    public boolean actualizarMovimiento(PJprincipal PJpri, int velY) {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearGotaDeLluvia();
        }
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            raindrop.y -= velY * Gdx.graphics.getDeltaTime();
            
            int vidas = PJpri.getVidas();
                      
            if (raindrop.y + 64 < 0) {
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
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
        actualizarDibujoLluvia(batch);
    }

    @Override
    public Rectangle getArea() {
        throw new UnsupportedOperationException("Not supported yet.");
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

    public int getVelY() {
        return velocidadY;
    }

    public void setVelY(int velocidad) {
        velocidadY = velocidad;
    }

    public int getVelX() {
        return velocidadX;
    }

    public void setVelX(int velocidadX) {
        this.velocidadX = velocidadX;
    }
}

