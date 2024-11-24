package puppy.code.Controladores;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Vixho
 * @author joaqunin
 */
public abstract class EntidadMovible {

    protected Sprite spr;
    protected int velX;
    protected int velY;

    public EntidadMovible(int xSpeed, int ySpeed) {
        velX = xSpeed;
        velY = ySpeed;
    }

    // Método Template
    public final void actualizar(SpriteBatch batch) {
        actualizarMovimiento(); // Movimiento definido en la subclase
        checkCollision();  // Lógica de colisiones (puede ser abstracta o implementada)
        accionesEspeciales();   // Opcional: estados especiales (como "herido")
        dibujar(batch);         // Renderizado
    }

    // Métodos abstractos o con implementación por defecto
    protected abstract void actualizarMovimiento();
    protected void accionesEspeciales() {
        // Por defecto, sin acción especial
    }
    protected abstract void dibujar(SpriteBatch batch);

    public abstract boolean checkCollision(EntidadMovible other);
    public abstract void crear();

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    public int getXSpeed() { return velX; }
    public void setXSpeed(int xSpeed) { velX = xSpeed; }
    public int getYSpeed() { return velY; }
    public void setYSpeed(int ySpeed) { velY = ySpeed; }

    private void checkCollision() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}