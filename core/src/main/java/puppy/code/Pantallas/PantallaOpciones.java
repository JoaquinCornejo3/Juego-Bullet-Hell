package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.GameBase;

public class PantallaOpciones implements Screen {

    private final GameBase game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    public PantallaOpciones(GameBase game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("letritas.fnt"));
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Establecer el fondo negro (RGBA: 0, 0, 0, 1)
        ScreenUtils.clear(0, 0, 0, 1); // Este es el color negro

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Calcular las posiciones para centrar el texto
        String titulo = "Opciones";
        String mensaje = "Presiona ESC para volver al menu principal";

        GlyphLayout layout = new GlyphLayout();

        // Centrar el título
        layout.setText(font, titulo);
        float tituloWidth = layout.width;
        float tituloX = (800 - tituloWidth) / 2; // 800 es el ancho de la pantalla
        float tituloY = 400; // Altura donde se muestra el título

        // Centrar el mensaje
        layout.setText(font, mensaje);
        float mensajeWidth = layout.width;
        float mensajeX = (800 - mensajeWidth) / 2;
        float mensajeY = 200; // Altura donde se muestra el mensaje

        // Dibujar los textos centrados
        font.draw(batch, titulo, tituloX, tituloY);
        font.draw(batch, mensaje, mensajeX, mensajeY);

        batch.end();

        // Volver al menú principal al presionar ESC
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.setScreen(new PantallaMenu(game));
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
