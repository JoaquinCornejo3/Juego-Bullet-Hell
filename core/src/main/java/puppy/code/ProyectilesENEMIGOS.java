package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import puppy.code.PJprincipal;

public class ProyectilesENEMIGOS implements Mostrable{

    private Array<Rectangle> rainDropsPos;
    private Array<Integer> rainDropsType;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Sound dropSound;
    private Music rainMusic;

    public ProyectilesENEMIGOS(Texture gotaBuena, Texture gotaMala, Sound ss, Music mm) {
        rainMusic = mm;
        dropSound = ss;
        this.gotaBuena = gotaBuena;
        this.gotaMala = gotaMala;
    }

    public void crear() {
        rainDropsPos = new Array<>();
        rainDropsType = new Array<>();
        crearGotaDeLluvia();
        
        // start the playback of the background music immediately
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    private void crearGotaDeLluvia() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        rainDropsPos.add(raindrop);
        
        // ver el tipo de gota
        if (MathUtils.random(1, 10) < 3) {
            rainDropsType.add(1);
        } else {
            rainDropsType.add(2);
        }
        lastDropTime = TimeUtils.nanoTime();
    }

    public void actualizarMovimiento(PJprincipal PJpri) {
        // generar gotas de lluvia 
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearGotaDeLluvia();
        }

        
        // revisar si las gotas cayeron al suelo o chocaron con el tarro
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            raindrop.y -= 300 * Gdx.graphics.getDeltaTime();
            
            //cae al suelo y se elimina
            if (raindrop.y + 64 < 0) {
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
            }
            if (raindrop.overlaps(PJpri.getArea())) { //la gota choca con el tarro
                if (rainDropsType.get(i) == 1) { // gota dañina
                    PJpri.dañar();
                    
                    if(PJpri.getVidas() == 0){
                        PJpri.morir();
                    }
                        
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                } else { // gota a recolectar
                    PJpri.sumarPuntos(10);
                    dropSound.play();
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                }
            }
        }
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {

        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            if (rainDropsType.get(i) == 1) // gota dañina
            {
                batch.draw(gotaMala, raindrop.x, raindrop.y);
            } else {
                batch.draw(gotaBuena, raindrop.x, raindrop.y);
            }
        }
    }

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Rectangle getArea() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizarMovimiento() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
