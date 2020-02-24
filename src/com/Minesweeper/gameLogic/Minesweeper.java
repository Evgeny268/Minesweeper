package com.Minesweeper.gameLogic;

import com.Minesweeper.gameLogic.field.Bomb;
import com.Minesweeper.gameLogic.field.BombCell;
import com.Minesweeper.gameLogic.field.EmptyCell;
import com.Minesweeper.gameLogic.field.FieldCell;

import java.util.ArrayList;
import java.util.Random;

/**
 * Данный класс реализует функциональность игрового минного поля
 * {@link #field} - массив, хранящий ячейки поля
 *  @see FieldCell - ячейка поля
 * {@link #fieldSizeX} - размер поля по оси X
 * {@link #fieldSizeY} - размер поля по оси Y
 * {@link #minesCount} - количество мин на поле
 * {@link #flagsCount} - количество установленных флагов
 * {@link #gameOver} - статус окончания игры
 */
public class Minesweeper {
    protected FieldCell [][]field;
    protected int fieldSizeX;
    protected int fieldSizeY;
    protected int minesCount;
    protected int flagsCount = 0;
    protected boolean firstClick = true;
    protected boolean gameOver = false;

    /**
     *
     * @param fieldSizeX - размер поля по оси X
     * @param fieldSizeY - размер поля по оси Y
     * @param minesCount - количество мин, которое необходимо сгенерировать на случайных ячейках поля
     */
    public Minesweeper(int fieldSizeX, int fieldSizeY, int minesCount) {
        this.fieldSizeX = (fieldSizeX>1)? fieldSizeX: 9;
        this.fieldSizeY = (fieldSizeY>1)? fieldSizeY: 9;
        if (minesCount>0 && minesCount<fieldSizeX*fieldSizeY) {
            this.minesCount = minesCount;
        }else {
            this.minesCount = (fieldSizeX*fieldSizeY)/4;
        }
        field = new FieldCell[fieldSizeX][fieldSizeY];
        generateEmptyField();
    }

    /**
     * Клик по ячейке поля
     * @param posX - позиция ячейки по оси X
     * @param posY - позиция ячейки по оси Y
     */
    public void clickOnField(int posX, int posY){
        if ((posX<0 || posX>=fieldSizeX) || (posY<0 || posY>=fieldSizeY)) return;
        if (gameOver || field[posX][posY].isFlagged()|| field[posX][posY].isOpened()) return;
        if (firstClick){
            firstClickOnField(posX, posY);
            firstClick = false;
        }else {
            FieldCell fieldCell = field[posX][posY];
            fieldCell.openCell();
            clickOnCell(fieldCell);
        }
        openCellsAround(posX,posY);
        checkGamePassed();
    }

    /**
     * Установка или снятие флага на ячейку поля
     * @param posX - позиция ячейки по оси X
     * @param posY - позиция ячейки по оси Y
     */
    public void setFlag(int posX, int posY){
        if ((posX<0 || posX>=fieldSizeX) || (posY<0 || posY>=fieldSizeY)) return;
        if (gameOver || firstClick || field[posX][posY].isOpened()) return;
        if (field[posX][posY].isFlagged()){
            field[posX][posY].setFlagged(false);
            flagsCount--;
        }else {
            field[posX][posY].setFlagged(true);
            flagsCount++;
        }
        checkGamePassed();
    }

    public FieldCell[][] getField() {
        return field;
    }

    public int getFieldSizeX() {
        return fieldSizeX;
    }

    public int getFieldSizeY() {
        return fieldSizeY;
    }

    public int getMinesCount() {
        return minesCount;
    }

    public int getFlagsCount() {
        return flagsCount;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Действия при открытии определенной ячейки поля
     * @param fieldCell - ячейка поля, по которой был произведен щелчек
     */
    protected void clickOnCell(FieldCell fieldCell){
        if (fieldCell instanceof BombCell){
            gameOver = true;
        }
    }

    /**
     *  Действия при самом первом щелче мышкой по полю
     * @param posX - Позицця по оси X ячейки, по которой был произведен клик
     * @param posY - Позицця по оси Y ячейки, по которой был произведен клик
     */
    protected void firstClickOnField(int posX, int posY){
        generateMinedField(posX, posY);
        makeCountMinesAround();
    }

    /**
     *  Генерация пустого поля (заполнение объектами EmptyCell)
     */
    protected void generateEmptyField(){
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                field[i][j] = new EmptyCell();
            }
        }
    }

    /**
     *  Генерация минного поля
     * @param startPosX - Позицця по оси X ячейки, по которой был произведен клик
     * @param startPosY - Позицця по оси Y ячейки, по которой был произведен клик
     */
    protected void generateMinedField(int startPosX, int startPosY){
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                field[i][j] = new EmptyCell();
            }
        }
        ArrayList<Integer> minePosition = new ArrayList<>();
        for (int i = 0; i < fieldSizeX * fieldSizeY; i++) {
            minePosition.add(i);
        }
        int tapCellPos = (startPosX*fieldSizeY)+startPosY;
        minePosition.remove(Integer.valueOf(tapCellPos));
        for (int i = 0; i < minesCount; i++) {
            Random rnd = new Random(System.currentTimeMillis());
            int deleteNumber = rnd.nextInt(minePosition.size());
            int posX = minePosition.get(deleteNumber)/fieldSizeY;
            int posY = minePosition.get(deleteNumber)-(posX*fieldSizeY);
            field[posX][posY] = new BombCell();
            minePosition.remove(Integer.valueOf(minePosition.get(deleteNumber)));
        }
    }

    /**
     * Расчет числа, которое показывает, сколбько бомб окружает текущую ячейку (расчет для всех ячеек поля)
     */
    protected void makeCountMinesAround(){
        for (int i = 0; i < fieldSizeX; i++) { //два цикла по всем ячейкам поля
            for (int j = 0; j < fieldSizeY; j++) {
                int minesAround = 0;
                for (int k = i-1; k <= i+1; k++) { //ячейки вокруг текущей ячейки
                    for (int l = j-1; l <= j+1; l++) {
                        if ((k<0 || k>=fieldSizeX) || (l<0 || l>=fieldSizeY)) continue;
                        if (i==k && j==l) continue;
                        if (field[k][l] instanceof Bomb)
                            minesAround++;
                    }
                }
                field[i][j].setMinesAround(minesAround);
            }
        }
    }

    /**
     * Открытие ячеек вокруг текущей
     * @param posX - Позицця по оси X ячейки, по которой был произведен клик
     * @param posY - Позицця по оси Y ячейки, по которой был произведен клик
     */
    protected void openCellsAround(int posX, int posY){
        ArrayList<Pair> needOpen = new ArrayList<>();
        field[posX][posY].openCell();
        for (int i = posX-1; i <= posX+1; i++) {
            for (int j = posY-1; j <= posY+1; j++) {
                if ((i<0 || i>=fieldSizeX) || (j<0 || j>=fieldSizeY)) continue;
                if (!field[i][j].isOpened()){
                    if (!(field[i][j] instanceof Bomb) && !field[i][j].isFlagged()){
                        if (field[i][j].getMinesAround()==0){
                            needOpen.add(new Pair(i,j));
                        }else {
                            field[i][j].openCell();
                        }
                    }
                }
            }
        }
        for (int i = 0; i < needOpen.size(); i++) {
            openCellsAround(needOpen.get(i).first,needOpen.get(i).second);
        }
    }

    /**
     *  Проверка окончания игры. Игра считается пройденной, если кол-во флажков равно кол-ву мин на поле,
     *  все мины отмечены флажками и все пустые ячейки открыты. В случае окончания игры меняется состояние переменной gameOver на true
     */
    protected void checkGamePassed(){
        if (flagsCount == minesCount){
            for (int i = 0; i < fieldSizeX; i++) {
                for (int j = 0; j < fieldSizeY; j++) {
                    if (!(field[i][j] instanceof Bomb) && field[i][j].isFlagged()) return;
                    if (!field[i][j].isFlagged() && !field[i][j].isOpened()) return;
                }
            }
        }else return;
        gameOver = true;
    }

    protected final class Pair{
        public int first;
        public int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
}
