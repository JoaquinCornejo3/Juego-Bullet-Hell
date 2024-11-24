package puppy.code.Controladores;
import com.badlogic.gdx.math.Rectangle;

public class MovimientoVertical implements EstrategiaMovimiento {
    private float velocidad;

    public MovimientoVertical(float velocidad) {
        this.velocidad = velocidad;
    }

    @Override
    public void mover(Rectangle area, float deltaTime) {
        area.y -= velocidad * deltaTime;
    }
}
