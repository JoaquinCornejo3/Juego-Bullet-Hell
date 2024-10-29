
package puppy.code.Controladores;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Vixho
 */
public abstract class EntidadMovible {
    
    protected Sprite spr;
    protected int velX;
    protected int velY;
    

    public EntidadMovible(int xSpeed, int ySpeed) {
        velX = xSpeed;
        velY = ySpeed;
    }

    public abstract boolean checkCollision(EntidadMovible other);
    public abstract void crear();

    public Rectangle getArea() {
        return spr.getBoundingRectangle();
    }

    public int getXSpeed() {
        return velX;
    }

    public void setXSpeed(int xSpeed) {
        velX = xSpeed;
    }

    public int getYSpeed() {
        return velY;
    }

    public void setYSpeed(int ySpeed) {
        velY = ySpeed;
    }
}


