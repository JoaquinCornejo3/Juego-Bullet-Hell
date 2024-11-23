
package puppy.code.Controladores;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.GotaBuena;


public class GotaBuenaFactory implements GotaFactory {
    private Texture textura;

    public GotaBuenaFactory(Texture textura) {
        this.textura = textura;
    }

    @Override
    public Gota crearGota(float x, float y) {
        return new GotaBuena(textura, x, y);
    }
}