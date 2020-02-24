package com.Minesweeper.gui;

import com.Minesweeper.gameLogic.field.ExtendBomdCell;
import com.Minesweeper.gameLogic.field.FieldCell;
import com.Minesweeper.gameplay.OnFieldClickListener;

import java.awt.*;

/**
 * ДАННЫЙ КЛАСС СОЗДАН ДЛЯ ДЕМОНСТРАЦИИ РАСШИРЕНИЯ ФУНКЦИОНАЛЬНОСТИ ПРИ НАСЛЕДОВАНИИ, ПОЭТОМУ ОН НЕ РЕАЛИЗОВАН ПОЛНОСТЬЮ И НЕ ИСПОЛЬЗУЕТСЯ В ПРОЕКТЕ
 *
 * Данный класс позволяет добавить новые картинки к новым типам ячеек, а так же изменить логигу отрисовки поля
 */
public class ExtendSwingGui extends SwingGUI {

    public ExtendSwingGui(OnFieldClickListener onFieldClickListener) {
        super(onFieldClickListener);
    }

    @Override
    protected Image getImageCell(FieldCell fieldCell) {
        Image cellImage = super.getImageCell(fieldCell);
        if (fieldCell instanceof ExtendBomdCell){
            //НАЗНАЧАЕМ НУЖНУЮ КАРТИНКУ ДЛЯ НОВОГО ТИПА БОМБЫ
        }
        return cellImage;
    }
}
