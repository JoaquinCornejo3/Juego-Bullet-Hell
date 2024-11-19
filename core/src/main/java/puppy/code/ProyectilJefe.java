package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import puppy.code.Controladores.EntidadMovible;

public class ProyectilJefe extends EntidadMovible {
    private Texture textura;

    public ProyectilJefe(int x, int y, int xSpeed, int ySpeed, Texture textura) {
        super(xSpeed, ySpeed);
        this.textura = textura;
        spr.setBounds(x, y, textura.getWidth(), textura.getHeight());
    }

    public void actualizarMovimiento() {
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, spr.getX(), spr.getY());
    }

    @Override
    public boolean checkCollision(EntidadMovible other) {
        return this.getArea().overlaps(other.getArea());
    }

    @Override
    public void crear() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
