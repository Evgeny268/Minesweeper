package com.Minesweeper.gameLogic.field;

/**
 *  ДАННЫЙ КЛАСС СОЗДАН ДЛЯ ДЕМОНСТРАЦИИ РАСШИРЕНИЯ ФУНКЦИОНАЛЬНОСТИ ПРИ НАСЛЕДОВАНИИ, ПОЭТОМУ ОН НЕ ИСПОЛЬЗУЕТСЯ В ПРОЕКТЕ
 *
 *  Бомба, которая взрывается с некоторой вероятностью
 */
public class ExtendBomdCell extends FieldCell implements Bomb{
    public double blastChance = 1.0/3.0; //ВЕРОЯТНОСТЬ ВЗРЫВА БОМБЫ
}
