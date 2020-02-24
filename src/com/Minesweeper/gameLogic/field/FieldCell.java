package com.Minesweeper.gameLogic.field;

/**
 *  Данный класс реализует ячейку поля
 * {@link #opened} - открытая ячейка
 * {@link #flagged} - ячейка, помеченная флагом
 * {@link #minesAround} - число, которое показывает, сколько мин находится вокруг этой ячейки
 */
public abstract class FieldCell {
    private boolean opened = false;
    private boolean flagged = false;
    private int minesAround = 0;

    public FieldCell() {
    }

    public boolean isOpened() {
        return opened;
    }

    public void openCell(){
        opened = true;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void setMinesAround(int minesAround) {
        this.minesAround = minesAround;
    }
}
