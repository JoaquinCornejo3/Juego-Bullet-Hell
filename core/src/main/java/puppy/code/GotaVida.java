package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.Controladores.Gota;

public class GotaVida extends Gota {
    public GotaVida(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public void efecto(PJprincipal jugador) {
        jugador.setVidas(jugador.getVidas() + 1);
    }
}