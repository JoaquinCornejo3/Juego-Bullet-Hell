package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import puppy.code.Pantallas.*;

public class GameBase extends Game {
    
    private static GameBase instancia; //Aplicación de singleton
    private SpriteBatch batch;
    private BitmapFont font;
    private int higherScore;

    private GameBase(){ //Aplicación de singleton
    }
    
    public static GameBase getInstance() { //Aplicación de singleton
        if (instancia == null) {
            instancia = new GameBase();
        }
        return instancia;
    }
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new PantallaMenu(this));
    }
    
    @Override
    public void render() {
        super.render();
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    
    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public int getHigherScore() {
        return higherScore;
    }

    public void setHigherScore(int highScore) {
        higherScore = highScore;
    }
}
