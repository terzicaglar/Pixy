package com.terzi.caglar.pixy.main;

import com.terzi.caglar.pixy.logic.Command;
import com.terzi.caglar.pixy.gui.BoardFrame;
import com.terzi.caglar.pixy.logic.Board;
import com.terzi.caglar.pixy.logic.Command;
import com.terzi.caglar.pixy.logic.Input;
import com.terzi.caglar.pixy.logic.Method;

import java.awt.Color;
import java.util.ArrayList;

/*
	Author: Caglar Terzi
	Date: 2014
	Edited: 2019
*
	PiXY Game
		Create pixel based drawings using PiXY Language!
		You can create custom methods for any drawing (like a chess board) and you can repeat these drawings by calling these methods
		In order to draw a pixel based drawing, you should enter valid commands to the command screen on the
			left-side text area and then you should click Run button on your right.
		You can save your commands by writing the method name on the text field on the right-top text field.

		If you wish to call your pre saved method, you need to write a command like this:

			method method_name param1,param2,param3 no_of_recurrences

		Here, all 4 fields are mandatory, i.e., you must write method keyword at the beginning, then write the
			method name ex:chess, write the paramaters ex:red,yellow,4 (if you do not have any parameters you can write null)
			and at last number of recurrences should be given ex:3 (if you do not call the method more than once,
			you should set it as 1) An example:

			method chess yellow,red,4,1 1

		Integer parameters can be any int value, however, there are restrictions for String values. You should
		only use String values given below:

			Color List:

			"RED", "BLACK", "BLUE", "GREEN", "YELLOW", "WHITE", "ORANGE", "MAGENTA", "CYAN", "PINK"

			Directions List:

			"E", "W", "S", "N", "NW", "NE", "SW", "SE"

		String parameters are represented inside a method with $ (Dollar Sign) followed by parameter no, for example:

			$1: Represents the first String parameter
			$10: Represents the tenth String parameter

		and Integer methods are represented inside a method with # followed by parameter no, for example:

			#1: Represents the second String parameter

		Calling a method with parameters red,yellow,1,2 will be treated as $1: red, $2: yellow, #1: 1, #2: 2 inside that method

		Pre-defıned specıal methods are:

			GOTO row_no col_no: Goes to cell (pixel) in (row_no,col_no)
			INK: Paints that cell
			CLEARALL: Clear the board
			CLEAR row_no col_no: Clears the cell (row_no,col_no)
			COLOR color_name: Set the color to given color using a color defined in Color List
			METHOD method_name method_params(seperated with a comma (,)) no_of_recurrence: Calls a method with method_name using given method_params which is
				repeatedly called by no_of_recurrence times
			MARCH direction_name: Sets the direction of the pointer for the next move using a direction_name listed in Direction List
*
*
	Classes
		gui package
			BoardFrame:

	Board: Board to be drawn, has a color matrix to represent the board.

 */

public class Game {
	private static int rows = 20, cols = 20;
	private static BoardFrame frame;
	private static Board board = new Board(rows,cols,new Color(0xf0ffff));

	//creating pre-defined specıal methods
	private static Command commandList[] = {
		new Command("GOTO",2, new Input[]{new Input(0, 1, board.getRows()), new Input(0, 1, board.getCols())}),
		new Command("INK"),
		new Command("CLEARALL"),
		new Command("CLEAR",2, new Input[]{new Input(0, 1, board.getRows()), new Input(0, 1, board.getCols())}),
		new Command("COLOR",1, new Input[]{new Input(1,new String[]{"RED", "BLACK", "BLUE", "GREEN", "YELLOW", "WHITE", "ORANGE", "MAGENTA", "CYAN", "PINK"})}),
		new Command("METHOD",3, new Input[]{new Input(1),new Input(1), new Input(0, 0, 5000)}),
		//new Command("WAIT",1, new Input[]{new Input(0, 1, 5000)}),
		//new Command("draw",2, new Input[]{new Input(1,new String[]{"E", "W", "S", "N", "NW", "NE", "SW", "SE"}), new Input(0)})
		new Command("MARCH",1, new Input[]{new Input(1,new String[]{"E", "W", "S", "N", "NW", "NE", "SW", "SE"})})
	};
	private static ArrayList<Method> methodList;
	public static void main(String[] args) {
		//panel = new JPanel();
		methodList = new ArrayList<Method>();
		frame = new BoardFrame( board, commandList, methodList);
		
	}
	
}
