package com.Minesweeper.gameplay;

/**
 * Интерфейс для слушателя, который определяет нажатие на поле
 */
public interface OnFieldClickListener {

    /**
     * Открытие ячейки (нажатие ЛКМ)
     * @param posX - Позицця по оси X ячейки, по которой был произведен клик
     * @param posY - Позицця по оси Y ячейки, по которой был произведен клик
     */
    void onCellClick(int posX, int posY);

    /**
     * Установка флага (нажатие ПКМ)
     * @param posX - Позицця по оси X ячейки, по которой был произведен клик
     * @param posY - Позицця по оси Y ячейки, по которой был произведен клик
     */
    void onCellSetFlag(int posX, int posY);
}
