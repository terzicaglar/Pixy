<img width="150" alt="pixy_alpha" src="https://user-images.githubusercontent.com/3480398/163027801-21a4724c-6d20-4d6a-bd38-42d3cba5bec3.PNG">


# Create pixel based drawings using PiXY!

PiXY is a drawing tool inspired by [ROBO](http://www.cs.bilkent.edu.tr/~david/robo.htm), a program that helps to teach basic computer engineering concepts to freshmen CS students, developed by [David Davenport](http://www.cs.bilkent.edu.tr/~david/david.html).

What you can do using PiXY is very basic, you can color pixels in the given grid one by one and eventually create some art. You can go to a desired pixel either using its row and column coordinates or you can move one pixel at a time using eight directional movement. After reaching to the desired pixel, you can paint that pixel with a color or clear it. These are all the things you can do using predefined commands for coloring pixels.

# Example 1 - Two-colored Line

If we want to draw a blue and red line from $(4,2)$ to $(4,7)$, we should write commands like this:

![image](https://user-images.githubusercontent.com/3480398/162762595-2a1d2e92-3239-49e3-baec-81e0b6f4bbe3.png)

If we examine the code, we will see some commands: *GOTO*, *COLOR*, *MARCH*, and *INK*. These are predifined codes that we can use to color pixels. `GOTO 4 2` command goes to the pixel at row $4$ and column $2$, i.e., $(4,2)$. `COLOR BLUE` sets the selected color as blue, and `INK` colors the current pixel with the selected color. `MARCH E` command moves pixel to one pixel right (i.e., east) without coloring. If we want to color current pixel, we have to use `INK` again, as done in the example. After coloring three pixels, we switch out color to red with command `COLOR RED` and color three more pixels. Although it seems an easy job, what happens when we want to draw a line length of $20$? We should write dozens of lines of repeating code.

To solve this problem, we will save the repeating code (`INK` and `MARCH E`) as a new method and call it as *INK_AND_MARCH* as seen below. 

![image](https://user-images.githubusercontent.com/3480398/162753398-6b6a111f-b654-4bb5-bc9b-6101bf00a051.png)

Now, we can call *INK_AND_MARCH* method $10$ times for blue color and $10$ times for red color to draw our two-colored line of $20$ pixels.

![image](https://user-images.githubusercontent.com/3480398/162755411-ffdccecd-eafc-43f6-835e-1ca6a81def35.png)

We see a new predefined method named as *METHOD*. We use it to call our previously saved methods. After command *METHOD*, we write our saved method's name, *INK_AND_MARCH*, and then we write the parameters of this method, which is *NULL* for this example, since we do not have any parameters in this method, and finally, number of repetitions, which is $10$. This method call is equivalent to writing `INK` and `MARH E` $10$ times. Since, $20$ pixel line could not fit into a $8$x$8$ grid, we changed our grid size to 22x22 for this example.

What happens if we want to draw our line vertically, instead of horizontally? Should we write another version of *INK_AND_MARCH* method and set the marching direction to south with command `MARCH S`? We can do that, but a better solution will be parameterizing the direction input. Instead of `MARCH E` command in *INK_AND_MARCH* method, we can write `MARCH $1`. *$* denotes that the given input is a string input with letters, and $1$ denotes that it is the first string input. After making this change, we can modify our code as below, and get a vertical line.

![image](https://user-images.githubusercontent.com/3480398/162756777-c2f8bcc2-b621-4978-a473-394755d5d348.png)

Finally, our whole code can be parametrized and user can set the colors, direction, and length of the line and also the starting point as seen below. 

![image](https://user-images.githubusercontent.com/3480398/162761475-3635cc76-ad05-4900-b10a-ddc167e85a9a.png)

Parameters defined with *#* sign denotes that these parameters are integer numbers. *#1*: starting pixel's row value, *#2*: starting pixel's column value, *#3*: half-length of the line, *$1*: first half's color, *$2*: second half's color, *$3* is the direction of the line. If we save this code as *TWO_COLORED_LINE* method, we can call it with parameters below to draw a diagonal (in south-east (SE) direction) $10$ pixels magenta and black line starting from (row: $3$, col: $2$). Note that each parameter is divided with comma. $1$ at the end means that this method is called once.

![image](https://user-images.githubusercontent.com/3480398/162761958-9af2c0fd-8ab8-42de-a687-cb0d9a9da527.png)


# Example 2 - Chess Board

![image](https://user-images.githubusercontent.com/3480398/144652718-de890bac-6cb9-4c65-a123-be09418bbcb5.png)

# Commands and Parameters

If you wish to call your previously saved method, you need to write a command like this:

	method method_name param1,param2,param3 no_of_recurrences

Here, all $4$ fields are mandatory, i.e., you must write method keyword at the beginning, then write the
	method name, e.g., chess_generic, write the parameters, e.g., red,yellow,$4$ (if you do not have any parameters you can write null)
	and at last, number of recurrences should be given ex:3 (if you do not call the method more than once,
	you should set it as 1) An example:

	method chess_generic yellow,red,4,1 1

Integer parameters can be any int value, however, there are restrictions for String values. You should
only use String values given below:

	Color List:

	"RED", "BLACK", "BLUE", "GREEN", "YELLOW", "WHITE", "ORANGE", "MAGENTA", "CYAN", "PINK"

	Directions List:

	"E", "W", "S", "N", "NW", "NE", "SW", "SE"

String parameters are represented inside a method with *$* (Dollar Sign) followed by parameter no, for example:

	$1: Represents the first String parameter
	$10: Represents the tenth String parameter

and Integer parameters are represented inside a method with # followed by parameter no, for example:

	#2: Represents the second Integer parameter

Calling a method with parameters red,yellow,1,2 will be treated as $1: red, $2: yellow, #1: 1, #2: 2 inside that method

Pre-defined special methods are:

	GOTO row_no col_no: Goes to cell (pixel) in (row_no, col_no)
	INK: Paints that cell
	CLEARALL: Clear the board
	CLEAR row_no col_no: Clears the cell (row_no,col_no)
	COLOR color_name: Set the color to given color using a color defined in Color List
	METHOD method_name method_params(seperated with a comma (,)) no_of_recurrences: Calls a method with method_name using given method_params which is
		repeatedly called by no_of_recurrences times
	MARCH direction_name: Sets the direction of the pointer for the next move using a direction_name listed in Direction List
