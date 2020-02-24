package com.Minesweeper.gameplay;

import com.Minesweeper.gameLogic.Minesweeper;

public class Gameplay implements OnFieldClickListener {

    protected GameGUI gameGUI = null;
    protected Minesweeper minesweeper;


    /**
     * Любой класс, который будет заниматься отрисовкой игры, должен имплементировать данный интерфейс
     */
    public interface GameGUI {
        void drawGame(Minesweeper minesweeper);
    }

    public Gameplay(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
    }


    public void setGameGUI(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        gameGUI.drawGame(minesweeper);
    }

    @Override
    public void onCellClick(int posX, int posY) {
        minesweeper.clickOnField(posX, posY);
        gameGUI.drawGame(minesweeper);
    }

    @Override
    public void onCellSetFlag(int posX, int posY) {
        minesweeper.setFlag(posX, posY);
        gameGUI.drawGame(minesweeper);
    }
}
