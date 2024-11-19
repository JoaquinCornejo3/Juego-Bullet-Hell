package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.*;

public class PantallaGameOver implements Screen {

    private final GameBase game;
    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private Music musicaGameOver;
    private Array<TextureRegion> frames;
    private float stateTime; // Tiempo para controlar la animación del fondo

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

        // Inicializamos el Array para los frames
        frames = new Array<>();

        // Intentamos cargar las imágenes del fondo animado
        cargarFondoAnimado();
        
        stateTime = 0f;
    }

    private void cargarFondoAnimado() {
        for (int i = 1; i <= 26; i++) { // Tenemos 26 imágenes para el fondo
            String fileName = String.format("assets/GIF GAMEOVER/frame%d.png", i);

            // Verificamos si el archivo existe antes de intentar cargarlo
            if (Gdx.files.internal(fileName).exists()) {
                try {
                    frames.add(new TextureRegion(new Texture(Gdx.files.internal(fileName))));
                    System.out.println("Archivo cargado correctamente: " + fileName); // Mensaje en consola para confirmar carga
                } catch (Exception e) {
                    System.out.println("Error al cargar la imagen: " + fileName + " - " + e.getMessage());
                }
            } else {
                System.out.println("Archivo no encontrado: " + fileName);
            }
        }

        // Si no se cargaron los frames, mostramos un mensaje de advertencia
        if (frames.size == 0) {
            System.out.println("No se cargaron frames para el fondo animado.");
        }
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

        // Dibujar fondo animado solo si se han cargado los frames
        if (frames.size > 0) {
            stateTime += delta; // Actualizar el tiempo para la animación
            TextureRegion currentFrame = frames.get((int)(stateTime / 0.1f) % frames.size); // Usamos el índice de la animación
            batch.draw(currentFrame, 0, 0, 800, 480);
        } else {
            System.out.println("No se puede dibujar el fondo, ya que los frames no fueron cargados.");
        }

        // Mensajes de "Game Over"
        String gameOverText = "GAME OVER";
        float gameOverWidth = font.getRegion().getRegionWidth();
        float gameOverX = (1100 - gameOverWidth) / 2;
        font.draw(batch, gameOverText, gameOverX, 300);

        String retryMessage = "Toca en cualquier lado con tu mouse para reiniciar.";
        float retryMessageWidth = font.getRegion().getRegionWidth();
        float retryMessageX = (600 - retryMessageWidth) / 2;
        font.draw(batch, retryMessage, retryMessageX, 160);

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
        musicaGameOver.stop();
    }

    @Override
    public void dispose() {
    }
}
