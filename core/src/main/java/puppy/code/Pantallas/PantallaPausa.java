package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.GameBase;

public class PantallaPausa implements Screen {
    private final GameBase game;
    private PantallaJuego juego;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Music musicaPausa;
    
    private Animation<TextureRegion> fondoAnimado;
    private float stateTime;

    private String[] pausaOpciones = {"Continuar", "Reiniciar", "Salir"};
    private int selectedIndex = 0;
    private int hoveredIndex;
    private Rectangle[] PausaBounds;

    private boolean isPaused; 

    public PantallaPausa(GameBase game, PantallaJuego juego) {
        this.game = game;
        this.juego = juego;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("letritas.fnt"));

        PausaBounds = new Rectangle[pausaOpciones.length];
        for (int i = 0; i < pausaOpciones.length; i++) {
            PausaBounds[i] = new Rectangle(150 + i * 200, 70, 150, 30); 
        }

        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i <= 39; i++) {
            String fileName = String.format("GIF PAUSA/frame%d.png", i);
            frames.add(new TextureRegion(new Texture(Gdx.files.internal(fileName))));
        }
        fondoAnimado = new Animation<>(0.05f, frames); 
        stateTime = 0f;

        musicaPausa = Gdx.audio.newMusic(Gdx.files.internal("musicaPausa.mp3"));
        isPaused = true; 
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        stateTime += delta; 
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos); 

        hoveredIndex = -1;
        for (int i = 0; i < PausaBounds.length; i++) {
            if (PausaBounds[i].contains(mousePos.x, mousePos.y)) {
                hoveredIndex = i;
                selectedIndex = i;
                break;
            }
        }

        batch.begin();

        TextureRegion currentFrame = fondoAnimado.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, 0, 0, 800, 480);

        for (int i = 0; i < pausaOpciones.length; i++) {
            if (i == hoveredIndex) {
                font.draw(batch, "> " + pausaOpciones[i], PausaBounds[i].x, PausaBounds[i].y + PausaBounds[i].height);
            } else {
                font.draw(batch, pausaOpciones[i], PausaBounds[i].x, PausaBounds[i].y + PausaBounds[i].height);
            }
        }
        batch.end();

        if (Gdx.input.isTouched() && hoveredIndex != -1) {
            selectOption(hoveredIndex);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || Gdx.input.isKeyPressed(Input.Keys.P)) {
            if (isPaused) {
                game.setScreen(juego); 
                isPaused = false; 
            }
        }
    }

    private void selectOption(int hoveredIndex) {
        switch (selectedIndex) {
            case 0:
                game.setScreen(juego);
                break;
            case 1:
                game.setScreen(new PantallaJuego(game)); 
                break;
            case 2:
                game.setScreen(new PantallaMenu(game)); 
                break;
        }
    }

    @Override
    public void show() {
        musicaPausa.setLooping(true);
        musicaPausa.play();
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
        musicaPausa.stop();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        musicaPausa.dispose();
    }
}
