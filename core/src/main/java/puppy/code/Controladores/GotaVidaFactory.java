/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code.Controladores;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.GotaVida;

/**
 *
 * @author Vixho
 */
public class GotaVidaFactory implements GotaFactory {
    private Texture textura;

    public GotaVidaFactory(Texture textura) {
        this.textura = textura;
    }

    @Override
    public Gota crearGota(float x, float y) {
        return new GotaVida(textura,x,y);
    }
}