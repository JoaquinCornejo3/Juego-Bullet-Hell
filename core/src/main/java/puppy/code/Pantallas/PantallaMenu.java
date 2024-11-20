package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.GameBase;

public class PantallaMenu implements Screen {
    private final GameBase game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Music fondoMusicaMenu;

    private Animation<TextureRegion> fondoAnimado;
    private float stateTime; // Tiempo para controlar la animación

    private String[] menuOptions = {"Iniciar Juego", "Opciones", "Salir"};
    private int hoveredIndex = -1;
    private Rectangle[] menuBounds;

    public PantallaMenu(GameBase game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("letritas.fnt"));

        fondoMusicaMenu = Gdx.audio.newMusic(Gdx.files.internal("musicaMenu.wav"));

        menuBounds = new Rectangle[menuOptions.length];
        for (int i = 0; i < menuOptions.length; i++) {
            menuBounds[i] = new Rectangle(350, 300 - i * 40 - 20, 200, 30);
        }

        // Cargar frames de la animación desde "assets/GIF MENU"
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i <= 22; i++) { 
            String fileName = String.format("GIF MENU/frame%d.png", i);
            frames.add(new TextureRegion(new Texture(Gdx.files.internal(fileName))));
        }
        fondoAnimado = new Animation<>(0.02f, frames, Animation.PlayMode.LOOP); // Duración de cada frame: 0.02s
        stateTime = 0f; // Inicializar el tiempo de animación
    }

    @Override
    public void show() {
        fondoMusicaMenu.setLooping(true);
        fondoMusicaMenu.play();
    }

    @Override
    public void render(float delta) {
        stateTime += delta; // Actualizar el tiempo de la animación

        // Limpiar pantalla
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Obtener posición del ratón
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        // Determinar si el ratón está sobre una opción del menú
        hoveredIndex = -1;
        for (int i = 0; i < menuBounds.length; i++) {
            if (menuBounds[i].contains(mousePos.x, mousePos.y)) {
                hoveredIndex = i;
                break;
            }
        }

        batch.begin();

        // Dibujar el fondo animado
        TextureRegion currentFrame = fondoAnimado.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, 0, 0, 800, 480);

        // Dibujar las opciones del menú
        for (int i = 0; i < menuOptions.length; i++) {
            if (i == hoveredIndex) {
                font.draw(batch, "> " + menuOptions[i], 350, 300 - i * 40);
            } else {
                font.draw(batch, menuOptions[i], 350, 300 - i * 40);
            }
        }

        batch.end();

        // Manejar la selección de una opción al hacer clic
        if (Gdx.input.isTouched() && hoveredIndex != -1) {
            selectOption(hoveredIndex);
        }
    }

    private void selectOption(int optionIndex) {
    switch (optionIndex) {
        case 0: // Iniciar juego
            game.setScreen(new PantallaJuego(game));
            break;
        case 1: // Opciones
            game.setScreen(new PantallaOpciones(game)); // Cambiar a la pantalla de opciones
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
        fondoMusicaMenu.dispose();
        for (TextureRegion frame : fondoAnimado.getKeyFrames()) {
            frame.getTexture().dispose();
        }
    }
}
