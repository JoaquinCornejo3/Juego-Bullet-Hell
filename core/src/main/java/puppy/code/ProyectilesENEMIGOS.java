package puppy.code;

import puppy.code.Controladores.Mostrable;
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
             
        rainMusic.setLooping(true);
        rainMusic.play();
    }
    private void crearGotaDeLluvia() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64); //0, 800 - 64
        raindrop.y = 480;
        raindrop.width = 48; //64
        raindrop.height = 48; //64
        rainDropsPos.add(raindrop);
        
        // ver el tipo de gota
        if (MathUtils.random(1, 10) <= 3) {
            rainDropsType.add(2);
        } else {
            rainDropsType.add(1);
        }
        lastDropTime = TimeUtils.nanoTime();
    }
    public boolean actualizarMovimiento(PJprincipal PJpri) {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) {
            crearGotaDeLluvia();
        }
        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            raindrop.y -= 300 * Gdx.graphics.getDeltaTime();
                      
            if (raindrop.y + 64 < 0) {
                rainDropsPos.removeIndex(i);
                rainDropsType.removeIndex(i);
            }
            if (raindrop.overlaps(PJpri.getArea())) { 
                if (rainDropsType.get(i) == 1) { 
                    PJpri.dañar();
                    
                    if(PJpri.getVidas() <= 0){
                        PJpri.morir();
                        return false;
                    }                      
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                } else { 
                    PJpri.sumarPuntos(10);
                    dropSound.play();
                    rainDropsPos.removeIndex(i);
                    rainDropsType.removeIndex(i);
                }
            }           
        }
        return true;
    }
    public void actualizarDibujoLluvia(SpriteBatch batch) {

        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            if (rainDropsType.get(i) == 1) 
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


}
