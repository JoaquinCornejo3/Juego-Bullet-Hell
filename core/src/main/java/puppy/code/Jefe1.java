
package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import puppy.code.Controladores.*;

public class Jefe1 extends Jefe implements Mostrable{
    private boolean disparando;
    

    public Jefe1(int xSpeed, int ySpeed, int vidas, Texture apariencia) {
        super(xSpeed, ySpeed, vidas, apariencia);
    }

    @Override
    public boolean checkCollision(EntidadMovible other) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void crear() {
        spr.setPosition(spr.getWidth()/2, spr.getHeight() - 30);
        spr.setSize(32, 32); //64, 64 Ajusta el tamaño si es necesario 
    }
    
    @Override
    public void disparar(){
        disparando = true;
        
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        if(!disparando){
            spr.draw(batch);
        }
        else{
            spr.setY(spr.getY() + MathUtils.random(-3, 3)); //se sacude al disparar
            spr.draw(batch);
            
        }
    }

    @Override
    public void actualizarMovimiento() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
