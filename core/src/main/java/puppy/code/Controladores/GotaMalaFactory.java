
package puppy.code.Controladores;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.GotaMala;

public class GotaMalaFactory implements GotaFactory {
    private Texture textura;

    public GotaMalaFactory(Texture textura) {
        this.textura = textura;
    }

    @Override
    public Gota crearGota(float x, float y) {
        return new GotaMala(textura, x, y);
    }
}
