/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.*;

/**
 *
 * @author joaqu
 */
public class PantallaGameOver implements Screen {

    private final GameBase game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Music musicaGameOver;
    
    public PantallaGameOver(final GameBase game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        font = new BitmapFont(Gdx.files.internal("letritas.fnt"));
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        musicaGameOver = Gdx.audio.newMusic(Gdx.files.internal("MusicaGameOver.mp3"));
        musicaGameOver.setLooping(true);
        musicaGameOver.play();
        
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        
        // Centramos el texto "GAME OVER"
        String gameOverText = "GAME OVER";
        float gameOverWidth = font.getRegion().getRegionWidth();
        float gameOverX = (1100 - gameOverWidth) / 2;
        font.draw(batch, gameOverText, gameOverX, 300);

        // Centramos el mensaje de reinicio
        String retryMessage = "Toca en cualquier lado con tu mouse para reiniciar.";
        float retryMessageWidth = font.getRegion().getRegionWidth();
        float retryMessageX = (600 - retryMessageWidth) / 2;
        font.draw(batch, retryMessage, retryMessageX, 160);
        
        //font.draw(batch, "GAME OVER", 100, 200);
        //font.draw(batch, "Toca en cualquier lado con tu mouse para reiniciar.", 100, 100);
        batch.end();
        
        

        if (Gdx.input.isTouched()) {
            game.setScreen(new PantallaMenu(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        musicaGameOver.stop();
    }

    @Override
    public void dispose() {
    }
}
