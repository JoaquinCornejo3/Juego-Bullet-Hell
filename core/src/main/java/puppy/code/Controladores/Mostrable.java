package puppy.code.Controladores;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface Mostrable {
    public void dibujar(SpriteBatch batch);
    public void actualizarMovimiento();
    public Rectangle getArea();
    
}
