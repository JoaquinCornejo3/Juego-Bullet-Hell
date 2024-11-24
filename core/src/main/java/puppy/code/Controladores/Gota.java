package puppy.code.Controladores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import puppy.code.PJprincipal;

public abstract class Gota {
    protected Texture textura;
    protected Rectangle area;  // Usamos Rectangle para almacenar posición y tamaño
    private EstrategiaMovimiento estrategiaMovimiento;  // Estrategia para el movimiento

    // Constructor de la clase Gota
    public Gota(Texture textura, float x, float y) {
        this.textura = textura;
        this.area = new Rectangle(x, y, 48, 48); // Tamaño de las gotas
    }

    // Método abstracto que cada tipo de gota debe implementar
    public abstract void efecto(PJprincipal jugador); // Efecto al tocar al jugador

    // Método para dibujar la gota en la pantalla
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }

    // Método para mover la gota usando la estrategia de movimiento
    public void mover() {
        if (estrategiaMovimiento != null) {
            estrategiaMovimiento.mover(area, Gdx.graphics.getDeltaTime()); // Mover con deltaTime
        }
    }

    // Establecer la estrategia de movimiento
    public void setEstrategiaMovimiento(EstrategiaMovimiento estrategiaMovimiento) {
        this.estrategiaMovimiento = estrategiaMovimiento;
    }

    // Obtener el área de la gota (Rectangle)
    public Rectangle getArea() {
        return area;
    }

    // Verificar colisión con otro objeto que implemente EntidadMovible
    public boolean checkCollision(EntidadMovible other) {
        return this.getArea().overlaps(other.getArea());
    }

    // Métodos para obtener la posición X y Y desde el Rectangle
    public float getPosX() {
        return area.x;
    }

    public float getPosY() {
        return area.y;
    }
}
