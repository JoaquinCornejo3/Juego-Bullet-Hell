package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.*;

public class PantallaMenu implements Screen {
    private final GameBase game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    Music fondoMusicaMenu = Gdx.audio.newMusic(Gdx.files.internal("musicaMenu.wav"));

    // Opciones del menú
    private String[] menuOptions = {"Iniciar Juego", "Opciones", "Salir"};
    private int selectedIndex = 0; // Opción seleccionada
    
    
    public PantallaMenu(GameBase game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void show() {
        fondoMusicaMenu.setLooping(true); // Sonará en bucle
        fondoMusicaMenu.play(); // Empieza a sonar la música de fondo
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        batch.begin();
        // Dibujar las opciones del menú
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == selectedIndex) {
                font.draw(batch, "> " + menuOptions[i], 350, 300 - i * 40);
            } else {
                font.draw(batch, menuOptions[i], 350, 300 - i * 40);
            }
        }

        batch.end();

        // Control de teclas
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectedIndex = (selectedIndex - 1 + menuOptions.length) % menuOptions.length;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectedIndex = (selectedIndex + 1) % menuOptions.length;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            selectOption();
        }
    }

    private void selectOption() {
        switch (selectedIndex) {
            case 0: // Iniciar Juego
                game.setScreen(new PantallaJuego(game)); // Cambia a la pantalla del juego
                break;
            case 1: // Opciones
                //game.setScreen(new OptionsScreen(game)); // Cambia a la pantalla de opciones
                break;
            case 2: // Salir
                Gdx.app.exit(); // Sale del juego
                break;
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
    fondoMusicaMenu.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
