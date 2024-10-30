/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import puppy.code.GameBase;

/**
 *
 * @author Vixho
 */
public class PantallaPausa implements Screen{
    private final GameBase game;
    private PantallaJuego juego;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Music musicaPausa = Gdx.audio.newMusic(Gdx.files.internal("MusicaPausa.mp3"));

    
    //Opciones de pausa
    private String[] pausaOpciones = {"Continuar","Reiniciar","Salir"};
    private int selectedIndex = 0;
    private int hoveredIndex;
    private Rectangle[] PausaBounds;
    
    public PantallaPausa(GameBase game, PantallaJuego juego) {
        this.game = game;
        this.juego = juego;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("letritas.fnt"));

        // Inicializar los límites de cada opción de menú
        PausaBounds = new Rectangle[pausaOpciones.length];
        for (int i = 0; i < pausaOpciones.length; i++) {
            PausaBounds[i] = new Rectangle(350, 300 - i * 40 - 20, 200, 30); // Ajusta ancho y alto según el texto
        }
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
        for (int i = 0; i < PausaBounds.length; i++) {
            if (PausaBounds[i].contains(mousePos.x, mousePos.y)) {
                hoveredIndex = i;
                selectedIndex = i;
                break;
            }
        }

        // Dibujar las opciones del menú, resaltando la opción donde está el mouse
        batch.begin();
        for (int i = 0; i < pausaOpciones.length; i++) {
            if (i == hoveredIndex) {
                font.draw(batch, "> " + pausaOpciones[i], 350, 300 - i * 40); // Resalta con una flecha
            } else {
                font.draw(batch, pausaOpciones[i], 350, 300 - i * 40);
            }
        }
        batch.end();

        // Detección de clic del mouse
        if (Gdx.input.isTouched() && hoveredIndex != -1) {
            selectOption(hoveredIndex); // Ejecutar la opción seleccionada
        }
    }
    
    private void selectOption(int hoveredIndex){
        switch(selectedIndex){
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
    }
    
}
