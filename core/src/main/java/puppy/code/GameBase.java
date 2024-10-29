package puppy.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameBase extends ApplicationAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private PJprincipal tarro;
    private ProyectilesENEMIGOS proyectilesE;

    @Override
    public void create() {
        font = new BitmapFont(); // use libGDX's default Arial font

        // load the images for the droplet and the bucket, 64x64 pixels each 	     
        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        tarro = new PJprincipal(new Texture(Gdx.files.internal("bucket.png")), hurtSound);

        // load the drop sound effect and the rain background "music" 
        Texture gota = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));

        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        proyectilesE = new ProyectilesENEMIGOS(gota, gotaMala, dropSound, rainMusic);

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        // creacion del tarro
        tarro.crear();

        // creacion de la lluvia
        proyectilesE.crear();
    }

    @Override
    public void render() {
        //limpia la pantalla con color azul obscuro.
        ScreenUtils.clear(0, 0, 0.2f, 1);
        //actualizar matrices de la cámara
        camera.update();
        //actualizar 
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //dibujar textos
        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 720, 475);

        if (!tarro.estaHerido()) {
            // movimiento del tarro desde teclado
            tarro.actualizarMovimiento();
            // caida de la lluvia 
            proyectilesE.actualizarMovimiento(tarro);
        }

        if (tarro.getVidas() < 1) {
            // Dibuja el estado congelado del juego
            tarro.dibujar(batch);
            proyectilesE.actualizarDibujoLluvia(batch);

            // Dibuja una capa oscura semitransparente en toda la pantalla
            batch.setColor(0, 0, 0, 0.5f);  // Color negro con 50% de transparencia
            batch.draw(new Texture("rectangulo.png"), 0, 0, 800, 480); // Dibuja un rectángulo completo
            batch.setColor(1, 1, 1, 1);  // Restablece el color de dibujo a opaco 

            // Dibuja el texto de "Game Over" en grande
            font.getData().setScale(3);  // Aumenta el tamaño de la fuente
            font.draw(batch, "GAME OVER", 250, 300);
            font.getData().setScale(1);  // Restablece el tamaño de la fuente
            font.draw(batch, "Presiona R para reiniciar", 270, 200);

        }

        tarro.dibujar(batch);
        proyectilesE.actualizarDibujoLluvia(batch);

        batch.end();

    }

    @Override
    public void dispose() {
        tarro.destruir();
        proyectilesE.destruir();
        batch.dispose();
        font.dispose();
    }
}
