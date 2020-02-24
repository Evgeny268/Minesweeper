package com.Minesweeper.gui;

import com.Minesweeper.gameLogic.Minesweeper;
import com.Minesweeper.gameLogic.field.Bomb;
import com.Minesweeper.gameLogic.field.FieldCell;
import com.Minesweeper.gameplay.Gameplay;
import com.Minesweeper.gameplay.OnFieldClickListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * GUI интерфейс для игры, используется Swing
 */
public class SwingGUI extends JFrame implements Gameplay.GameGUI {
    protected OnFieldClickListener onFieldClickListener;
    protected JPanel panel;
    protected JLabel statusLabel;
    protected int IMAGE_SIZE = 30;//Размер ячейки поля
    protected int COLS;
    protected int ROWS;
    protected boolean firstDraw = true;
    protected Minesweeper minesweeper;

    public SwingGUI(OnFieldClickListener onFieldClickListener){
        this.onFieldClickListener = onFieldClickListener;
    }

    @Override
    public void drawGame(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
        if (firstDraw){
            ROWS = minesweeper.getFieldSizeX();
            COLS = minesweeper.getFieldSizeY();
            initPanel();
            initStatusLabel();
            initFrame();
            firstDraw = false;
        }
        panel.repaint();
        if (minesweeper.isGameOver()){
            statusLabel.setText("Игра завершена");
        }else {
            statusLabel.setText("Осталось отметить бомб: "+(minesweeper.getMinesCount()-minesweeper.getFlagsCount()));
        }
    }

    protected void initPanel(){
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                FieldCell[][]field = minesweeper.getField();
                for (int i = 0; i < ROWS; i++){
                    for (int j = 0; j < COLS; j++){
                        FieldCell currentCell = field[i][j];
                        Image cellImage = getImageCell(currentCell);
                        g.drawImage(cellImage,j*IMAGE_SIZE,i*IMAGE_SIZE,this);
                    }
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int posX = e.getY() / IMAGE_SIZE;
                int posY = e.getX() / IMAGE_SIZE;
                if ((posX<0 || posX >= minesweeper.getFieldSizeX()) || (posY<0 || posY>=minesweeper.getFieldSizeY())) return;
                if (e.getButton() == MouseEvent.BUTTON1) //Левая клавига мыши
                    onFieldClickListener.onCellClick(posX,posY);
                if (e.getButton() == MouseEvent.BUTTON3){ //Правая клавиша мыши
                    onFieldClickListener.onCellSetFlag(posX,posY);
                }
            }
        });
        panel.setPreferredSize(new Dimension(COLS*IMAGE_SIZE, ROWS*IMAGE_SIZE));
        add(panel);
    }

    protected void initStatusLabel(){
        statusLabel = new JLabel("Осталось отметить бомб: "+(minesweeper.getMinesCount()-minesweeper.getFlagsCount()));
        add(statusLabel,BorderLayout.NORTH);
    }

    protected void initFrame(){
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    protected Image getImageCell(FieldCell fieldCell){
        Image cellImage = null;
        if (fieldCell.isOpened()){
            if (fieldCell instanceof Bomb){
                cellImage = loadByName("bombed.png");
            }else {
                cellImage = getImageNumCell(fieldCell.getMinesAround());
            }
        }else {
            if (!minesweeper.isGameOver()){
                if (fieldCell.isFlagged()){
                    cellImage = loadByName("flaged.png");
                }else {
                    cellImage = loadByName("closed.png");
                }
            }else {
                if (fieldCell instanceof Bomb){
                    if (fieldCell.isFlagged()){
                        cellImage = loadByName("flaged.png");
                    }else {
                        cellImage = loadByName("bomb.png");
                    }
                }else {
                    if (fieldCell.isFlagged()){
                        cellImage = loadByName("nobomb.png");
                    }else {
                        cellImage = loadByName("closed.png");
                    }
                }
            }
        }
        return cellImage;
    }


    protected Image getImageNumCell(int number){
        Image image = null;
        switch (number){
            case 0:
                image = loadByName("zero.png");
                break;
            case 1:
                image = loadByName("num1.png");
                break;
            case 2:
                image = loadByName("num2.png");
                break;
            case 3:
                image = loadByName("num3.png");
                break;
            case 4:
                image = loadByName("num4.png");
                break;
            case 5:
                image = loadByName("num5.png");
                break;
            case 6:
                image = loadByName("num6.png");
                break;
            case 7:
                image = loadByName("num7.png");
                break;
            case 8:
                image = loadByName("num8.png");
                break;
        }
        return image;
    }

    protected Image loadByName(String name){
        String fName = "/img/";
        fName+=name;
        ImageIcon icon = new ImageIcon(getClass().getResource(fName));
        return createResizedCopy(icon.getImage(),IMAGE_SIZE,IMAGE_SIZE,false);
    }

    protected BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }
}
