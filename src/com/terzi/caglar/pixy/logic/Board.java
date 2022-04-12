package com.terzi.caglar.pixy.logic;

import java.awt.Color;


public class Board {
    private int cols, rows, xPointer = 0, yPointer = 0, pointer;
    private Color color, clearColor;
    private Color[][] matrix;

    public Board(int rows, int cols) {
        this.color = Color.RED;
        this.rows = rows;
        this.cols = cols;

        matrix = new Color[rows][cols];
        clearColor = Color.WHITE;
        init();
    }

    public Board(int rows, int cols, Color c) {
        this.color = Color.RED;
        this.rows = rows;
        this.cols = cols;

        matrix = new Color[rows][cols];
        clearColor = c;
        init();
    }

    public void init() {
        // TODO Auto-generated method stub
        this.color = Color.RED;
        clearBoard();
    }

    public void clearBoard() {
        xPointer = 0;
        yPointer = 0;
        calculatePointer();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = clearColor;
            }
        }
    }

    public void setBackground(Color c) {
        xPointer = 0;
        yPointer = 0;
        calculatePointer();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == clearColor) {
                    matrix[i][j] = c;
                }
            }
        }
    }

    private void calculatePointer() {
        // TODO Auto-generated method stub
        pointer = yPointer * cols + xPointer;
    }

    private void calculateXandYPointers() {
        yPointer = pointer / cols;
        xPointer = pointer % cols;
    }

    public void drawCell(int row, int col, Color c) {
        matrix[row][col] = c;
    }

    public void drawPointer() {
        matrix[yPointer][xPointer] = color;
    }

    public Color[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(Color[][] matrix) {
        this.matrix = matrix;
    }

    public void updateMatrix(int rows, int cols) {
        Color[][] newMatrix = new Color[rows][cols];
        int minrows = Math.min(matrix.length, newMatrix.length);
        int mincols = Math.min(matrix[0].length, newMatrix[0].length);
        for (int i = 0; i < minrows; i++) {
            for (int j = 0; j < mincols; j++) {
                newMatrix[i][j] = this.matrix[i][j];
            }
        }
        this.matrix = new Color[rows][cols];
        this.rows = rows;
        this.cols = cols;
        this.matrix = newMatrix;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getxPointer() {
        return xPointer;
    }

    public void setxPointer(int xPointer) {
        this.xPointer = xPointer;
        calculatePointer();
    }

    public int getyPointer() {
        return yPointer;
    }

    public void setyPointer(int yPointer) {
        this.yPointer = yPointer;
        calculatePointer();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getClearColor() {
        return clearColor;
    }

    public void setClearColor(Color clearColor) {
        this.clearColor = clearColor;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointerVal) {
        pointer = pointerVal % (rows * cols); // - olan ve board boyunu gecenler icin
        if (pointer < 0) {
            pointer += (rows * cols);
        }
        calculateXandYPointers();
    }
}
