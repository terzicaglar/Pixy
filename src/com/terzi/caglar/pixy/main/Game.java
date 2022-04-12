package com.terzi.caglar.pixy.main;

import com.terzi.caglar.pixy.logic.Command;
import com.terzi.caglar.pixy.gui.BoardFrame;
import com.terzi.caglar.pixy.logic.Board;
import com.terzi.caglar.pixy.logic.Command;
import com.terzi.caglar.pixy.logic.Input;
import com.terzi.caglar.pixy.logic.Method;

import java.awt.Color;
import java.util.ArrayList;

public class Game {
    private static int rows = 8, cols = 8;
    private static BoardFrame frame;
    private static Board board = new Board(rows, cols, new Color(0xf0ffff));

    //creating pre-defined special methods
    private static Command commandList[] = {
            new Command("GOTO", 2, new Input[]{new Input(0, 1, board.getRows()), new Input(0, 1, board.getCols())}),
            new Command("INK"),
            new Command("CLEARALL"),
            new Command("CLEAR", 2, new Input[]{new Input(0, 1, board.getRows()), new Input(0, 1, board.getCols())}),
            new Command("COLOR", 1, new Input[]{new Input(1, new String[]{"RED", "BLACK", "BLUE", "GREEN", "YELLOW", "WHITE", "ORANGE", "MAGENTA", "CYAN", "PINK"})}),
            new Command("METHOD", 3, new Input[]{new Input(1), new Input(1), new Input(0, 0, 5000)}),
            //new Command("WAIT",1, new Input[]{new Input(0, 1, 5000)}),
            //new Command("draw",2, new Input[]{new Input(1,new String[]{"E", "W", "S", "N", "NW", "NE", "SW", "SE"}), new Input(0)})
            new Command("MARCH", 1, new Input[]{new Input(1, new String[]{"E", "W", "S", "N", "NW", "NE", "SW", "SE"})})
    };
    private static ArrayList<Method> methodList;

    public static void main(String[] args) {
        //panel = new JPanel();
        methodList = new ArrayList<Method>();
        frame = new BoardFrame(board, commandList, methodList);
    }
}
