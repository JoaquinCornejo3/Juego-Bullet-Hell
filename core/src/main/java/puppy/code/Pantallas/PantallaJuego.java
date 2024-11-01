package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.*;

public class PantallaJuego implements Screen {
    final GameBase game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private PJprincipal pj;
    private ProyectilesENEMIGOS proyectilesE;
    private float tiempo = 0;
    
    public PantallaJuego(final GameBase game){
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        font = new BitmapFont(Gdx.files.internal("letritas.fnt"));
        	     
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        
        pj = new PJprincipal(new Texture(Gdx.files.internal("nerd 64.png")),
                             hurtSound, 500, 500) {}; //400, 400

        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));

        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        
        Music fondoMusica = Gdx.audio.newMusic(Gdx.files.internal("musicaFondo.wav"));
        proyectilesE = new ProyectilesENEMIGOS(gota, gotaMala, dropSound, fondoMusica);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        pj.crear();

        proyectilesE.crear();
    }
    
    @Override
    public void show() {
        proyectilesE.continuarMusica();
    }
    @Override
    public void render(float delta) {
        tiempo += delta;
        
        int minutos = (int) tiempo / 60;
        int segundos = (int) tiempo % 60;
        
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Gotas totales: " + pj.getPuntos(), 2, 475);//5, 475
        font.draw(batch, "Vidas : " + pj.getVidas(), camera.viewportWidth * 3 /4, 475);
        font.draw(batch, String.format("Tiempo: %02d:%02d", minutos, segundos), camera.viewportWidth /3, 475);///3, 470
        
        if (!pj.estaHerido()) {
            pj.actualizarMovimiento();
            if (!proyectilesE.actualizarMovimiento(pj)) {
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
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        proyectilesE.pausarMusica();
        game.setScreen(new PantallaPausa(game, this));
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        proyectilesE.pausarMusica();
    }

    @Override
    public void dispose() {
    }
    
}
