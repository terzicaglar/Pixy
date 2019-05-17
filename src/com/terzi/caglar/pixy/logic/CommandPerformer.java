package com.terzi.caglar.pixy.logic;

import com.terzi.caglar.pixy.logic.Board;
import com.terzi.caglar.pixy.logic.Method;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class CommandPerformer {
	private Board board;
	private Command[] commandList;
	
	public CommandPerformer(Board board, Command[] commandList) {
		// TODO Auto-generated constructor stub
		this.board = board;
		this.commandList = commandList;
	}
	
	public boolean checkLine(String line)
	{
		StringTokenizer st = new StringTokenizer(line);
		int noOfParams = 0;
		ArrayList<String> params = new ArrayList<String>();
		String command = "";
		while(st.hasMoreTokens())
		{
			if(noOfParams == 0)
			{
				command = st.nextToken();
			}
			else
			{
				params.add(st.nextToken());
			}
			noOfParams++;
		}
		if(noOfParams < 1)
			return false;
		else
			return checkCommand(command, params, noOfParams-1);
	}

	private boolean checkCommand(String name, ArrayList<String> params,
			int noOfLines) {
		// TODO Auto-generated method stub
		int index = getCommentIndex(name);
		if(index >= 0)
		{
			Command c = commandList[index];
			if(c.getParamLength() != noOfLines)
				return false;

			
			Input[] inputs = c.getInputs();
			for(int i = 0; i < noOfLines; i++)
			{
				if(!inputs[i].checkInput(params.get(i)))//checks input type
					return false;
			}			
			return true;
		}
		return false;
	}
	
	private int getCommentIndex(String name)
	{
		for(int i = 0; i < commandList.length; i++)
		{
			if(commandList[i].getName().equalsIgnoreCase(name))
				return i;
		}
		return -1;
	}

	public void performCommand(String line) {
		// TODO Auto-generated method stub
		
		StringTokenizer st = new StringTokenizer(line);
		int noOfLines = 0;
		String command = "";

		if(noOfLines == 0)
		{
			command = st.nextToken();
		}

		if(command.equalsIgnoreCase("GOTO"))
		{
			int row = Integer.parseInt(st.nextToken())-1; //starts from 1 (not 0 as usual)
			int col = Integer.parseInt(st.nextToken())-1; //starts from 1 (not 0 as usual)
			
			board.setxPointer(col);
			board.setyPointer(row);
		}
		else if(command.equalsIgnoreCase("INK"))
		{
			board.drawPointer();
		}
		else if(command.equalsIgnoreCase("COLOR"))
		{
			String colorString = st.nextToken();
			board.setColor(getColorFromString(colorString));

			
		}
		else if(command.equalsIgnoreCase("CLEARALL"))
		{
			board.clearBoard();
		}
		else if(command.equalsIgnoreCase("CLEAR"))
		{
			int row = Integer.parseInt(st.nextToken())-1; //starts from 1 (not 0 as usual)
			int col = Integer.parseInt(st.nextToken())-1; //starts from 1 (not 0 as usual)
			
			board.drawCell(row, col, board.getClearColor());
		}
		else if(command.equalsIgnoreCase("MARCH"))
		{
			String direction = st.nextToken();
			
			if(direction.equalsIgnoreCase("E"))
				board.setPointer( board.getPointer() + 1);
			else if(direction.equalsIgnoreCase("W"))
				board.setPointer( board.getPointer() - 1);
			else if(direction.equalsIgnoreCase("N"))
				board.setPointer( board.getPointer() - board.getCols());
			else if(direction.equalsIgnoreCase("S"))
				board.setPointer( board.getPointer() + board.getCols());
			else if(direction.equalsIgnoreCase("NE"))
				board.setPointer( board.getPointer() - board.getCols() + 1);
			else if(direction.equalsIgnoreCase("NW"))
				board.setPointer( board.getPointer() - board.getCols() - 1);
			else if(direction.equalsIgnoreCase("SE"))
				board.setPointer( board.getPointer() + board.getCols() + 1);
			else if(direction.equalsIgnoreCase("SW"))
				board.setPointer( board.getPointer() + board.getCols() - 1);
		}
		else if(command.equalsIgnoreCase("METHOD"))
		{
			String methodName = st.nextToken().toUpperCase();
			String parameters = st.nextToken().toUpperCase();
			int recurrence = Integer.parseInt(st.nextToken());
			File f = new File("methods/" + methodName + ".txt");
			if(!f.exists())
			{
				System.out.println("no file");
			}
			else
			{
				Method newMethod = new Method(methodName);
				StringTokenizer paramTokenizer = new StringTokenizer(parameters, ",");
				//method satranc E,3,1,E,ad,asd,31,qwe 12
				while(paramTokenizer.hasMoreTokens())
				{
					String param = paramTokenizer.nextToken();
					if(isInteger(param)) //integer parameter
					{
						newMethod.addInteger(Integer.parseInt(param));
					}
					else //string parameter
					{
						newMethod.addString(param);
					}
				}
				
				BufferedReader br = null;
				ArrayList<String> methodLines = new ArrayList<String>();
				try {
					br = new BufferedReader(new FileReader(f));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(br != null)
				{
					String methodLine = "";
					try {
						
						while((methodLine = br.readLine())!=null)
						{
							methodLine = methodLine.toUpperCase();
							methodLine = methodLine.replace("#COLS", ""+board.getCols());
							methodLine = methodLine.replace("#ROWS", ""+board.getRows());
							
							methodLine = searchAndReplaceParameters(methodLine, newMethod);
							//System.out.println(methodLine);
							if(methodLine.length() >= 5 && methodLine.substring(0, 5).equalsIgnoreCase("ERROR"))
								System.out.println(methodLine);
							else
								methodLines.add(methodLine);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				for(int i = 0; i < recurrence; i++)
					for(int j = 0; j < methodLines.size(); j++)
						performCommand(methodLines.get(j));
				
			}
			
		}
		noOfLines++;

		
	}
	
	private Color getColorFromString(String colorString)
	{
		if( colorString.equalsIgnoreCase("RED"))
			return Color.RED;
		else if( colorString.equalsIgnoreCase("BLACK"))
			return Color.BLACK;
		else if( colorString.equalsIgnoreCase("BLUE"))
			return Color.BLUE;
		else if( colorString.equalsIgnoreCase("GREEN"))
			return Color.GREEN;
		else if( colorString.equalsIgnoreCase("YELLOW"))
			return Color.YELLOW;
		else if( colorString.equalsIgnoreCase("WHITE"))
			return Color.WHITE;
		else if( colorString.equalsIgnoreCase("ORANGE"))
			return Color.ORANGE;
		else if( colorString.equalsIgnoreCase("MAGENTA"))
			return Color.MAGENTA;
		else if( colorString.equalsIgnoreCase("CYAN"))
			return Color.CYAN;
		else if( colorString.equalsIgnoreCase("PINK"))
			return Color.PINK;
		return Color.RED;
	}
	
	private String searchAndReplaceParameters(String methodLine, Method method) {
		// TODO Auto-generated method stub
		
		//for string params
		int index = methodLine.indexOf("$");
		while(index >= 0)
		{
			int noOfDigits  = 0;
			while(index+2+noOfDigits <= methodLine.length() && isInteger(methodLine.substring(index+1, index+2+noOfDigits)))//counts the number of digits after $
			{
				noOfDigits ++;
			}
			if(noOfDigits == 0)
				return "ERROR - NEED A NUMBER AFTER $";

			//System.out.println("index= " + index + " noofdig: " + noOfDigits);
			int paramNo = Integer.parseInt(methodLine.substring(index+1,index+1+noOfDigits));
			if(paramNo > method.getStrings().size())
				return "ERROR - STRING PARAMETER $" + paramNo + " IS NOT ENTERED FOR METHOD " + method.getName();
			if(paramNo <= 0)
				return "ERROR - STRING PARAMETER NUMBER SHOULD BE POSITIVE";
			methodLine = methodLine.replace(methodLine.substring(index, index + 1 + noOfDigits), "" + method.getStrings().get(paramNo-1));
			
			//System.out.println("newline-" + methodLine + "-");
			index = methodLine.indexOf("$", index+1);
		}
		
		//for int params
		index = methodLine.indexOf("#");
		while(index >= 0)
		{
			int noOfDigits  = 0;
			while(index+2+noOfDigits <= methodLine.length() && isInteger(methodLine.substring(index+1, index+2+noOfDigits)))//counts the number of digits after #
			{
				noOfDigits ++;
			}
			if(noOfDigits == 0)
				return "ERROR - NEED A NUMBER AFTER #";

			//System.out.println("index= " + index + " noofdig: " + noOfDigits);
			int paramNo = Integer.parseInt(methodLine.substring(index+1,index+1+noOfDigits));
			if(paramNo > method.getIntegers().size())
				return "ERROR - INTEGER PARAMETER #" + paramNo + " IS NOT ENTERED FOR METHOD " + method.getName();
			//method altsatiragec 3,3 1
			if(paramNo <= 0)
				return "ERROR - INTEGER PARAMETER NUMBER SHOULD BE POSITIVE";
			methodLine = methodLine.replace(methodLine.substring(index, index + 1 + noOfDigits), "" + method.getIntegers().get(paramNo-1));
			
			//System.out.println("newline-" + methodLine + "-");
			index = methodLine.indexOf("#", index+1);
		}
		
		
		boolean isValidLine = checkLine(methodLine);
		if(!isValidLine)
			return "ERROR - LINE IS INVALID: " + methodLine;
		else
			return methodLine;
	}

	private boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	

}
