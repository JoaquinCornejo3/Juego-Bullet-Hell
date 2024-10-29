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
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        musicaGameOver = Gdx.audio.newMusic(Gdx.files.internal("gameover.mp3"));
        musicaGameOver.setLooping(true);
        
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
        font.draw(batch, "GAME OVER", 100, 200);
        font.draw(batch, "Toca en cualquier lado para reiniciar.", 100, 100);
        batch.end();
        
        

        if (Gdx.input.isTouched()) {
            game.setScreen(new PantallaJuego(game));
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
    }

    @Override
    public void dispose() {
    }
}
