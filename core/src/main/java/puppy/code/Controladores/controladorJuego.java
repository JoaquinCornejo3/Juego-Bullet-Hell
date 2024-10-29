/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code.Controladores;

import puppy.code.Pantallas.PantallaGameOver;
import puppy.code.Pantallas.PantallaJuego;
import puppy.code.Pantallas.PantallaMenu;

/**
 *
 * @author joaqu
 */
public class ControladorJuego {
    private PantallaMenu menu;
    private PantallaJuego pantallaJuego;
    private PantallaGameOver gameOver;

    public ControladorJuego() {
        menu = new PantallaMenu();
        pantallaJuego = new PantallaJuego();
        gameOver = new PantallaGameOver();
    }

    public void iniciarMenu() {
        menu.mostrarMenu();
    }

    public void iniciarJuego() {
        pantallaJuego.mostrarJuego();
    }

    public void mostrarGameOver() {
        gameOver.mostrarGameOver();
    }
}
