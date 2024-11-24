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
    private float stateTime; 

    private String[] menuOptions = {"Iniciar Juego", "Salir"};
    private int hoveredIndex = -1;
    private Rectangle[] menuBounds;

    public PantallaMenu(GameBase game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("letritas.fnt"));

        fondoMusicaMenu = Gdx.audio.newMusic(Gdx.files.internal("menuMusica.mp3"));
        menuBounds = new Rectangle[menuOptions.length];
        for (int i = 0; i < menuOptions.length; i++) {
            menuBounds[i] = new Rectangle(350, 300 - i * 40 - 20, 200, 30);
        }
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i <= 22; i++) { 
            String fileName = String.format("GIF MENU/frame%d.png", i);
            frames.add(new TextureRegion(new Texture(Gdx.files.internal(fileName))));
        }
        fondoAnimado = new Animation<>(0.02f, frames, Animation.PlayMode.LOOP); 
        stateTime = 0f; 
    }

    @Override
    public void show() {
        fondoMusicaMenu.setLooping(true);
        fondoMusicaMenu.play();
    }

    @Override
    public void render(float delta) {
        stateTime += delta; 

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        hoveredIndex = -1;
        for (int i = 0; i < menuBounds.length; i++) {
            if (menuBounds[i].contains(mousePos.x, mousePos.y)) {
                hoveredIndex = i;
                break;
            }
        }

        batch.begin();

        TextureRegion currentFrame = fondoAnimado.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, 0, 0, 800, 480);

        for (int i = 0; i < menuOptions.length; i++) {
            if (i == hoveredIndex) {
                font.draw(batch, "> " + menuOptions[i], 350, 300 - i * 40);
            } else {
                font.draw(batch, menuOptions[i], 350, 300 - i * 40);
            }
        }
        batch.end();
        if (Gdx.input.isTouched() && hoveredIndex != -1) {
            selectOption(hoveredIndex);
        }
    }

    private void selectOption(int optionIndex) {
    switch (optionIndex) {
        case 0: 
            game.setScreen(new PantallaJuego(game));
            break;
        case 1:
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
