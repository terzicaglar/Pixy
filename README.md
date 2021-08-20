# PiXY

Create pixel based drawings using PiXY Language!
You can create custom methods for any drawing (like a chess board) and you can repeat these drawings by calling these methods.
In order to draw a pixel based drawing, you should enter valid commands to the text area and then you should click Run button on your right.
You can save your commands by writing the method name on the top text field.

If you wish to call your pre saved method, you need to write a command like this:

	method method_name param1,param2,param3 no_of_recurrences

Here, all 4 fields are mandatory, i.e., you must write method keyword at the beginning, then write the
	method name, e.g., chess, write the paramaters, e.g., red,yellow,4 (if you do not have any parameters you can write null)
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

	#2: Represents the second String parameter

Calling a method with parameters red,yellow,1,2 will be treated as $1: red, $2: yellow, #1: 1, #2: 2 inside that method

Pre-defined special methods are:

	GOTO row_no col_no: Goes to cell (pixel) in (row_no, col_no)
	INK: Paints that cell
	CLEARALL: Clear the board
	CLEAR row_no col_no: Clears the cell (row_no,col_no)
	COLOR color_name: Set the color to given color using a color defined in Color List
	METHOD method_name method_params(seperated with a comma (,)) no_of_recurrence: Calls a method with method_name using given method_params which is
		repeatedly called by no_of_recurrence times
	MARCH direction_name: Sets the direction of the pointer for the next move using a direction_name listed in Direction List
