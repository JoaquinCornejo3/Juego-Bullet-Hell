package puppy.code;

import puppy.code.Controladores.Mostrable;
import puppy.code.Controladores.EntidadMovible;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public abstract class PJprincipal extends EntidadMovible implements Mostrable {

    private Sound sonidoHerido;
    private int vidas = 3;
    private int puntos = 0;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;

    public PJprincipal(Texture tex, Sound ss, int xSpeed, int ySpeed) {
        super(xSpeed, ySpeed);  
        this.spr = new Sprite(tex); 
        this.sonidoHerido = ss;
    }
    
    @Override
    public void crear() {
        spr.setPosition(800 / 2 - spr.getWidth() / 2, 20);
        spr.setSize(32, 32); 
    }
    
    @Override
    protected void accionesEspeciales() {
        if (herido) {
            spr.setY(spr.getY() + MathUtils.random(-5, 5));
            tiempoHerido--;
            if (tiempoHerido <= 0) {
                herido = false;
            }
        }
    }
    
    public int getVidas() {
        return vidas;
    }
    
    public void setVidas(int vidas){
        this.vidas = vidas;
    }

    public int getPuntos() {
        return puntos;
    }

    public void sumarPuntos(int pp) {
        puntos += pp;
    }

    public void dañar() {
        vidas--;
        herido = true;
        tiempoHerido = tiempoHeridoMax;
        sonidoHerido.play();
    }

    public void morir() {
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        if (!herido) {
            spr.draw(batch);  
        } else {
            spr.setY(spr.getY() + MathUtils.random(-5, 5));
            spr.draw(batch);
            tiempoHerido--;
            if (tiempoHerido <= 0) {
                herido = false;
            }
        }
    }

    @Override
    public void actualizarMovimiento() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            spr.translateX(-velX * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            spr.translateX(velX * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            spr.translateY(velY * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            spr.translateY(-velY * Gdx.graphics.getDeltaTime());
        }

        if (spr.getX() < 0) spr.setX(0);
        if (spr.getX() > 800 - spr.getWidth()) spr.setX(800 - spr.getWidth());
        if (spr.getY() < 0) spr.setY(0);
        if (spr.getY() > 480 - spr.getHeight()) spr.setY(480 - spr.getHeight());
    }

    public void destruir() {
        spr.getTexture().dispose();
        sonidoHerido.dispose();
    }

    public boolean estaHerido() {
        return herido;
    }

    @Override
    public boolean checkCollision(EntidadMovible other) {
        return this.getArea().overlaps(other.getArea());
    }
}