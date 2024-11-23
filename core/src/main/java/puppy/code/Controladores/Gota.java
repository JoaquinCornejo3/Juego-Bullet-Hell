package puppy.code.Controladores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import puppy.code.PJprincipal;

public abstract class Gota {
    protected Texture textura;
    protected Rectangle area;
    private float posX;
    private float posY;

    public Gota(Texture textura, float x, float y) {
        this.textura = textura;
        this.area = new Rectangle(x, y, 48, 48); // Tamaño de las gotas
        posX = x;
        posY = y;
    }

    public Rectangle getArea() {
        return area;
    }

    public abstract void efecto(PJprincipal jugador); // Efecto al tocar al jugador

    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }

    public void mover(float velY) {
        area.y -= velY * Gdx.graphics.getDeltaTime();
    }
    
    public void moverX(float velX){
        area.x += velX * Gdx.graphics.getDeltaTime();
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }
    
    public boolean checkCollision(EntidadMovible other) {
        return this.getArea().overlaps(other.getArea());
    }

}

