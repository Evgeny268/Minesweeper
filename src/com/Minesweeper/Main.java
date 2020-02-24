package com.Minesweeper;

import com.Minesweeper.gameLogic.Minesweeper;
import com.Minesweeper.gameplay.Gameplay;
import com.Minesweeper.gui.SwingGUI;

public class Main {

    public static void main(String[] args) {
        Gameplay gamePlay;
        Minesweeper minesweeper;
        if (args.length==1){
            if (args[0].equals("easy")){
                minesweeper = new Minesweeper(9,9,10);
            }else if (args[0].equals("hard")){
                minesweeper = new Minesweeper(16,30,99);
            }else {
                minesweeper = new Minesweeper(16, 16, 40);
            }
            gamePlay = new Gameplay(minesweeper);
        }else {
            gamePlay = new Gameplay(new Minesweeper(16,16,40));
        }
        SwingGUI swingGUI = new SwingGUI(gamePlay);
        gamePlay.setGameGUI(swingGUI);
    }
}
