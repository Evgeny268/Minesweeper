package com.Minesweeper.gameLogic;

import com.Minesweeper.gameLogic.field.Bomb;
import com.Minesweeper.gameLogic.field.ExtendBomdCell;
import com.Minesweeper.gameLogic.field.FieldCell;

/**
 *  ДАННЫЙ КЛАСС СОЗДАН ДЛЯ ДЕМОНСТРАЦИИ РАСШИРЕНИЯ ФУНКЦИОНАЛЬНОСТИ ПРИ НАСЛЕДОВАНИИ, ПОЭТОМУ ОН НЕ РЕАЛИЗОВАН ПОЛНОСТЬЮ И НЕ ИСПОЛЬЗУЕТСЯ В ПРОЕКТЕ
 *
 *  Модифицированная версия класса Minesweeper.java, которая включает в себя работу с новыми ячейками
 */
public class ExtendMinesweeper extends Minesweeper {

    /**
     * @param fieldSizeX - размер поля по оси X
     * @param fieldSizeY - размер поля по оси Y
     * @param minesCount - количество мин, которое необходимо сгенерировать на случайных ячейках поля
     */
    public ExtendMinesweeper(int fieldSizeX, int fieldSizeY, int minesCount) {
        super(fieldSizeX, fieldSizeY, minesCount);
    }

    @Override
    protected void clickOnCell(FieldCell fieldCell) {
        super.clickOnCell(fieldCell); //Обработка нажатия на стандартные ячейки поля
        if (fieldCell instanceof ExtendBomdCell){
            //ОБРАБОТКА ПОВЕДЕНИЯ НАЖАТИЯ НА НОВУЮ МИНУ
        }
    }

    @Override
    protected void generateMinedField(int startPosX, int startPosY) {
        super.generateMinedField(startPosX, startPosY);
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (field[i][j] instanceof Bomb){ // Заменяем некоторые бобы но новый тип бомб
                    double val = Math.random();
                    if (val>=0.5){
                        field[i][j] = new ExtendBomdCell();
                    }
                }
            }
        }
    }
}
