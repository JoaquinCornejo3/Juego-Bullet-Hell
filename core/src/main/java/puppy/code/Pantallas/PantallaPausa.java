/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    
    //Opciones de pausa
    private String[] opciones = {"Continuar","Reiniciar","Salir"};
    private int selectedIndex = 0;
    
    public PantallaPausa(GameBase game, PantallaJuego juego) {
        this.game = game;
        this.juego = juego;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();
        font = new BitmapFont();
    }
    
    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0.2f,1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        for(int i = 0; i < opciones.length;i++){
            if(i == selectedIndex){
                font.draw(batch, "> " + opciones[i], 350, 300 - i * 40);
            } 
            
            else{
                font.draw(batch, opciones[i], 350, 300 - i * 40);
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectedIndex = (selectedIndex - 1 + opciones.length) % opciones.length;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectedIndex = (selectedIndex + 1) % opciones.length;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            selectOption();
        }
        
        batch.end();
    }
    
    private void selectOption(){
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
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
    
}
