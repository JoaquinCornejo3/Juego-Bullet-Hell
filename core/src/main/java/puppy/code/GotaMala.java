/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.Controladores.Gota;

/**
 *
 * @author Vixho
 */
public class GotaMala extends Gota{

    public GotaMala(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public void efecto(PJprincipal jugador) {
        jugador.dañar(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
