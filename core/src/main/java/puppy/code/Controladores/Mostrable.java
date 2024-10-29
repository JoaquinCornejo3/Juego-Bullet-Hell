/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package puppy.code.Controladores;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author Vixho
 */
public interface Mostrable {
    public void dibujar(SpriteBatch batch);
    public void actualizarMovimiento();
    public Rectangle getArea();
    
}
