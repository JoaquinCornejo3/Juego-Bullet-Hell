package puppy.code.Controladores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import puppy.code.PJprincipal;

public abstract class Gota {
    protected Texture textura;
    protected Rectangle area;  
    private EstrategiaMovimiento estrategiaMovimiento;  

    public Gota(Texture textura, float x, float y) {
        this.textura = textura;
        this.area = new Rectangle(x, y, 48, 48); 
    }

    public abstract void efecto(PJprincipal jugador); 

    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }

    public void mover() {
        if (estrategiaMovimiento != null) {
            estrategiaMovimiento.mover(area, Gdx.graphics.getDeltaTime()); 
        }
    }

    public void setEstrategiaMovimiento(EstrategiaMovimiento estrategiaMovimiento) {
        this.estrategiaMovimiento = estrategiaMovimiento;
    }

    public Rectangle getArea() {
        return area;
    }

    public boolean checkCollision(EntidadMovible other) {
        return this.getArea().overlaps(other.getArea());
    }

    public float getPosX() {
        return area.x;
    }

    public float getPosY() {
        return area.y;
    }
}
