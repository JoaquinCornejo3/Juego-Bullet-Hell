package puppy.code.Controladores;

//import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public abstract class Jefe extends EntidadMovible{
    
    protected int vidas;
    protected Texture aparienciaJefe;
    //protected Sound sonidoJefe; //NO es la música
    
    

    public Jefe(int xSpeed, int ySpeed, int vidas, Texture apariencia) {
        super(xSpeed, ySpeed);
        aparienciaJefe = apariencia;
    }
    
    public abstract void disparar();

}
