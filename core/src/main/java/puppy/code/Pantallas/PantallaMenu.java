package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.*;

public class PantallaMenu implements Screen {
    private final GameBase game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Music fondoMusicaMenu = Gdx.audio.newMusic(Gdx.files.internal("musicaMenu.wav"));

    private String[] menuOptions = {"Iniciar Juego", "Opciones", "Salir"};
    private int hoveredIndex = -1; // Índice para resaltar la opción en la que está el mouse
    private Rectangle[] menuBounds;

    public PantallaMenu(GameBase game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("letritas.fnt"));

        // Inicializar los límites de cada opción de menú
        menuBounds = new Rectangle[menuOptions.length];
        for (int i = 0; i < menuOptions.length; i++) {
            menuBounds[i] = new Rectangle(350, 300 - i * 40 - 20, 200, 30); // Ajusta ancho y alto según el texto
        }
    }

    @Override
    public void show() {
        fondoMusicaMenu.setLooping(true);
        fondoMusicaMenu.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Detectar la posición del mouse
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos); // Convertir a coordenadas de la cámara

        // Revisar si el mouse está sobre alguna opción y actualizar `hoveredIndex`
        hoveredIndex = -1;
        for (int i = 0; i < menuBounds.length; i++) {
            if (menuBounds[i].contains(mousePos.x, mousePos.y)) {
                hoveredIndex = i;
                break;
            }
        }

        // Dibujar las opciones del menú, resaltando la opción donde está el mouse
        batch.begin();
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == hoveredIndex) {
                font.draw(batch, "> " + menuOptions[i], 350, 300 - i * 40); // Resalta con una flecha
            } else {
                font.draw(batch, menuOptions[i], 350, 300 - i * 40);
            }
        }
        batch.end();

        // Detección de clic del mouse
        if (Gdx.input.isTouched() && hoveredIndex != -1) {
            selectOption(hoveredIndex); // Ejecutar la opción seleccionada
        }
    }

    private void selectOption(int optionIndex) {
        switch (optionIndex) {
            case 0: // Iniciar Juego
                game.setScreen(new PantallaJuego(game));
                break;
            case 1: // Opciones
                // game.setScreen(new OptionsScreen(game));
                break;
            case 2: // Salir
                Gdx.app.exit();
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