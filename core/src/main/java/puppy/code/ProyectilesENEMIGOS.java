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

        // Validar que las texturas no sean nulas
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

            // Ahora solo se utiliza la estrategia de movimiento vertical
            gota.setEstrategiaMovimiento(new MovimientoVertical(velocidadY));
            gota.mover();

            // Verificar si la gota ha salido de la pantalla
            if (gota.getPosY() <= 0) {
                rainDrops.removeIndex(i);
                i--;
                continue;
            }

            // Verificar colisión con el jugador
            if (gota.checkCollision(PJpri)) {
                gota.efecto(PJpri); // Aplica el efecto de la gota
                dropSound.play();
                rainDrops.removeIndex(i);
                i--;
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
        // No es necesario implementar en esta clase, ya que no tiene área propia
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizarMovimiento() {
        // No es necesario en esta clase, ya que el movimiento se maneja en Gota
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
