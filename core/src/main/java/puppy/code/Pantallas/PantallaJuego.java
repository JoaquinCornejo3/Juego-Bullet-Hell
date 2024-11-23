package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.*;

public class PantallaJuego implements Screen {
    final GameBase game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private PJprincipal pj;
    private ProyectilesENEMIGOS proyectilesE;
    private boolean cambio = false;

    private Animation<TextureRegion> fondoAnimado;
    private float stateTime; 

    private float tiempo = 0;

    public PantallaJuego(final GameBase game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        font = new BitmapFont(Gdx.files.internal("letritas.fnt"));

        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.mp3"));
        pj = new PJprincipal(new Texture(Gdx.files.internal("nerd 64.png")), hurtSound, 500, 500) {};

        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        Texture gotaGod = new Texture(Gdx.files.internal("dropLife.png"));
        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        Music fondoMusica = Gdx.audio.newMusic(Gdx.files.internal("musicaJuego.mp3"));
        proyectilesE = new ProyectilesENEMIGOS(gotaMala, gota, gotaGod, dropSound, fondoMusica, 30000);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i <= 6; i++) {
            String fileName = String.format("GIF JUEGO/frame%d.png", i);
            frames.add(new TextureRegion(new Texture(Gdx.files.internal(fileName))));
        }
        fondoAnimado = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP); 
        stateTime = 0f;

        pj.crear();
        proyectilesE.crear();
    }

    @Override
    public void show() {
        proyectilesE.continuarMusica();
    }

    @Override
    public void render(float delta) {
        stateTime += delta; 
        tiempo += delta;

        int minutos = (int) tiempo / 60;
        int segundos = (int) tiempo % 60;
        int velocidadY = proyectilesE.getVelY();
        int velocidadX = proyectilesE.getVelX();

        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        TextureRegion currentFrame = fondoAnimado.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, 0, 0, 800, 480);

        if (segundos % 30 == 0 && segundos != 0) {
            proyectilesE.setVelY(velocidadY + 500);
            font.draw(batch, "LEVEL UP!", camera.viewportWidth / 2, camera.viewportHeight / 2);
        }
        font.draw(batch, "Gotas totales: " + pj.getPuntos(), 2, 475);
        font.draw(batch, "Vidas : " + pj.getVidas(), camera.viewportWidth * 3 / 4, 475);
        font.draw(batch, String.format("Tiempo: %02d:%02d", minutos, segundos), camera.viewportWidth / 3, 475);
        
        
        if (!pj.estaHerido()) {
            pj.actualizarMovimiento();
            if (minutos == 1){
                cambio = true;
            }
            
            if (!proyectilesE.actualizarMovimiento(pj, cambio)) {
                if (game.getHigherScore() < pj.getPuntos()) {
                    game.setHigherScore(pj.getPuntos());
                }
                game.setScreen(new PantallaGameOver(game));
                proyectilesE.pausarMusica();
                dispose();
            }
        }
        pj.dibujar(batch);
        proyectilesE.actualizarDibujoLluvia(batch);

        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            pause();
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {
        proyectilesE.pausarMusica();
        game.setScreen(new PantallaPausa(game, this));
    }

    @Override
    public void resume() {}

    @Override
    public void hide() {
        proyectilesE.pausarMusica();
    }

    @Override
    public void dispose() {
        try {
            pj.destruir();
            proyectilesE.destruir();

            Gdx.app.log("Dispose", "Llamado a dispose");
          
            if (fondoAnimado != null) {            
                for (TextureRegion frame : fondoAnimado.getKeyFrames()) {
                    Texture texture = frame.getTexture();  
                    if (texture != null) {
                        Gdx.app.log("Dispose", "Liberando textura: " + texture);
                        texture.dispose();  
                    }
                }
            }
            Gdx.app.log("Dispose", "Recursos liberados correctamente");

        } catch (Exception e) {
            Gdx.app.log("Error", "Error en dispose: " + e.getMessage());
        }
    }
}
