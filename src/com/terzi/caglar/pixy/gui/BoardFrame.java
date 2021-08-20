package com.terzi.caglar.pixy.gui;

import com.terzi.caglar.pixy.gui.PlaceHolderTextField;
import com.terzi.caglar.pixy.logic.Board;
import com.terzi.caglar.pixy.logic.Method;
import com.terzi.caglar.pixy.logic.Command;
import com.terzi.caglar.pixy.logic.CommandPerformer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class BoardFrame extends JFrame implements ActionListener
{
	private JComboBox methodBox;
	private Board board;
	private BoardPanel boardPanel; //board panel at left, which shows the drawn pixels
	private JPanel rightPanel, //right panel which contains commandPanel, buttonPanel, and bottomPanel
			commandPanel, //panel where user enters commans (code)
			buttonPanel, //panel where Run and Save buttons are placed
			bottomPanel; //panel where Show Grids checkbox, and row/column text fields are placed.
	private JTextArea textArea, methodArea;
	private JTextField rowsField,colsField;
	private PlaceHolderTextField tf;
	private JCheckBox showGrids;
	private JButton run, save, save2, delete;
	private Command[] commandList;
	private CommandPerformer commandPerformer;
	private Timer delay;
	private String placeHolderText = "Enter a method name to save", methodLoc = "methods/";
	private ArrayList<Method> methodList;
	private JMenuBar menuBar;
	private JMenu helpMenu;
	private JMenuItem help;
	public BoardFrame(Board board, Command[] commandList, ArrayList<Method> methodList)
	{
		super("PiXY");
		this.methodList = methodList;
		this.commandList = commandList;
		commandPerformer = new CommandPerformer(board, commandList);

		menuBar = new JMenuBar();
		helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		this.setJMenuBar(menuBar);


		help = new JMenuItem("Help Document");
		helpMenu.add(help);
		help.addActionListener(this);

		boardPanel = new BoardPanel();
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,1));
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,3));
		
		//setLayout(new BorderLayout());
		setLayout(new GridLayout(1, 2));
		add(boardPanel);
		add(rightPanel);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tf = new PlaceHolderTextField();
		tf.setPlaceholder(placeHolderText);


		rowsField = new JTextField("" + board.getRows());
		colsField = new JTextField("" + board.getCols());
		
		commandPanel = new JPanel();
		commandPanel.setLayout( new BorderLayout());
		textArea = new JTextArea();
		textArea.setText("method chess null 1");
		run = new JButton("Run!");
		run.setMnemonic(KeyEvent.VK_ENTER); //hotkey is alt+enter
		save = new JButton("Save");
		save.setMnemonic(KeyEvent.VK_S); //hotkey is alt+s
		save2 = new JButton("Save");
		delete = new JButton("Delete");
		showGrids = new JCheckBox("Show Grids");
		showGrids.setSelected(true);
		run.addActionListener(this);
		save.addActionListener(this);
		save2.addActionListener(this);
		delete.addActionListener(this);
		showGrids.addActionListener(this);
		
		
		buttonPanel.add(run);
		buttonPanel.add(save);
		
		bottomPanel.add(showGrids);
		bottomPanel.add(rowsField);
		bottomPanel.add(colsField);
		
		commandPanel.add(textArea, BorderLayout.CENTER);
		commandPanel.add(buttonPanel, BorderLayout.EAST);
		commandPanel.add(tf, BorderLayout.NORTH);
		commandPanel.add(bottomPanel, BorderLayout.SOUTH);
		tabbedPane.addTab("Command List", null, commandPanel,
		                  "");
		//tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JPanel methodPanel = new JPanel();
		methodPanel.setLayout(new BorderLayout());
		tabbedPane.addTab("Methods", null, methodPanel,
		                  "");
		methodBox = new JComboBox();
		methodBox.addActionListener(this);
		
		
		methodArea = new JTextArea();
		addItemstoMethodArea();
		fillMethodArea();
		methodPanel.add(methodArea, BorderLayout.CENTER);
		methodPanel.add(methodBox, BorderLayout.NORTH);
		methodPanel.add(delete, BorderLayout.SOUTH);
		methodPanel.add(save2, BorderLayout.EAST);
		//tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		rightPanel.add( tabbedPane, BorderLayout.CENTER );

		this.board = board;
		//Set default close operation for JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Set JFrame size
		setSize(800,400);
		
		//Make JFrame visible
		setVisible(true);
		
		boardPanel.repaint();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boardPanel.repaint();
	}

	private void addItemstoMethodArea()
	{
		File dir = new File(methodLoc);
		File [] files = dir.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".txt");
		    }
		});
		for(int i =0; i < files.length; i++)
		{
			methodBox.addItem(files[i].getName().substring(0,files[i].getName().length()-4));
		}
	}
	
	private void fillMethodArea() {
		

		methodArea.setText("");
		BufferedReader br = null;
		if(methodBox.getSelectedItem() != null)
		{
			try {
				br = new BufferedReader(new FileReader(methodLoc + methodBox.getSelectedItem() +".txt"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String line = "";
			try {
				while((line = br.readLine())!= null)
				{
					if(methodArea != null)
						methodArea.setText(methodArea.getText() + line +"\n");
				}
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}

	private class BoardPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int rows,cols;

            rowsField.setBackground(Color.WHITE);
            colsField.setBackground(Color.WHITE);
            
            rows = board.getRows();
            cols = board.getCols();
            try
            {
            	rows = Integer.parseInt(rowsField.getText());
            }
            catch(NumberFormatException e){
            	System.out.println("Rows should be Integer" + e.getMessage());

            	rowsField.setBackground(Color.YELLOW);
            }
            try
            {
            	cols = Integer.parseInt(colsField.getText());
            }
            catch(Exception e){
            	System.out.println("Cols should be Integer");
            	colsField.setBackground(Color.YELLOW);
            }
            
            board.updateMatrix(rows,cols);

    		//draw rectangle outline
            int minDimension = Math.min( this.getHeight(), this.getWidth());
            int minLength = Math.min(minDimension / rows, minDimension / cols);
            
    		int rowLength = minLength;
    		int collength = minLength;
    		
    		for(int i = 0; i < rows; i++)
    			for(int j = 0; j < cols; j++)
    			{
    				g.setColor(board.getMatrix()[i][j]);
    				g.fillRect(j * collength, i * rowLength, collength, rowLength);
    				if(showGrids.isSelected())
    				{
	    				g.setColor(Color.GRAY);
	    				g.drawRect(j * collength, i * rowLength, collength, rowLength);
    				}
    			}
    		
        }
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//if(e.getSource() == run || e.getSource() == save)
		if(e.getSource() == showGrids)
		{
			repaintBoard();
				
		}
		else if(e.getSource() == run)
		{
			board.init();
			
			String str = textArea.getText();
			StringTokenizer st = new StringTokenizer(str,"\n");
			Highlighter h = textArea.getHighlighter();
		    h.removeAllHighlights();
			int lineNo = 0;
			boolean error = true;
			while(st.hasMoreTokens())
			{
				String line = st.nextToken();
				line = line.toUpperCase();
				line = line.replace("#COLS", ""+board.getCols());
				line = line.replace("#ROWS", ""+board.getRows());
				
				error = !commandPerformer.checkLine(line);
				if(error)
				{
					
				    int start = -1, end = -1;
				    try {
						start = textArea.getLineStartOffset(lineNo);
						end = textArea.getLineEndOffset(lineNo);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    if(start > -1 && end > -1){
						try {
							h.addHighlight(start, end, DefaultHighlighter.DefaultPainter);
						} catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    }
				    break;
				}
				else
				{
					commandPerformer.performCommand(line);
				}
				lineNo++;
			}
			
			repaintBoard();
			
			/*if(e.getSource() == save && !error)
			{
				if(tf.getText().equals(placeHolderText) || tf.getText().length() <= 0)
				{
					tf.setBackground(Color.YELLOW);
				}
				else
				{
					tf.setBackground(Color.WHITE);
					String name = tf.getText();
					String textAreaContent = textArea.getText();
					
					PrintWriter writer;
					try {
						writer = new PrintWriter(methodLoc + name.toUpperCase() + ".txt", "UTF-8");
						writer.print(textAreaContent);
						writer.close();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}*/
		}
		else if(e.getSource() == save)
		{
			if(tf.getText().length() <= 0)
			{
				tf.setBackground(Color.YELLOW);
			}
			else
			{
				tf.setBackground(Color.WHITE);
				String name = tf.getText();
				String textAreaContent = textArea.getText();
				
				PrintWriter writer;
				try {
					writer = new PrintWriter(methodLoc + name.toUpperCase() + ".txt", "UTF-8");
					writer.print(textAreaContent);
					writer.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				methodBox.addItem(name.toUpperCase());
				
			}
		}
		else if(e.getSource() == save2)
		{
			
			String name = (String) methodBox.getSelectedItem();
			String textAreaContent = methodArea.getText();
			
			if(name != null && name.length() > 0)
			{
				PrintWriter writer;
				try {
					writer = new PrintWriter(methodLoc + name.toUpperCase() + ".txt", "UTF-8");
					writer.print(textAreaContent);
					writer.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				

		}
		else if(e.getSource() == delete)
		{
			String name = (String) methodBox.getSelectedItem();
			System.out.println(name);
			int index = methodBox.getSelectedIndex();
			System.out.println(index);
			boolean isdeleted = false;
			
			try{
				 
	    		File file = new File(methodLoc + name + ".txt");
	    		System.out.println(file.getPath());
	    		if(file.exists())
	    			isdeleted = file.delete();
	    		else
	    			System.out.println("File not found");
	    		
	 
	    	}catch(Exception e2){
	 
	    		e2.printStackTrace();
	 
	    	}
			if(isdeleted)
			{
				methodArea.setText("");
				methodBox.removeItemAt(index);
				if(methodBox.getItemCount() > 0)
					methodBox.setSelectedIndex(0);
				fillMethodArea();
			}
			else
			{
				System.out.println("File cannot be deleted");
			}
			
		}
		else if(e.getSource() == methodBox)
		{
			int index = methodBox.getSelectedIndex();
			System.out.println(index);
			fillMethodArea();
		}
		else if(e.getSource() == help)
		{
			JTextArea textArea = new JTextArea("PiXY Game\n" +
					"Create pixel based drawings using PiXY Language!\n" +
					"You can create custom methods for any drawing (like a chess board) and you can repeat these drawings by calling these methods\n" +
					"In order to draw a pixel based drawing, you should enter valid commands to the command screen on the left-side text area and then you should click Run button on your right.\n" +
					"You can save your commands by writing the method name on the text field on the right-top text field.\n" +
					"\n" +
					"If you wish to call your pre saved method, you need to write a command like this:\n" +
					"\n" +
					"method method_name param1,param2,param3 no_of_recurrences\n" +
					"\n" +
					"Here, all 4 fields are mandatory, i.e., you must write method keyword at the beginning, then write the method name ex:chess_generic, write the paramaters ex:red,yellow,4 (if you do not have any parameters you can write null) and at last number of recurrences should be given ex:3 (if you do not call the method more than once, you should set it as 1) An example:\n" +
					"\n" +
					"method chess_generic yellow,red,4,1 1\n" +
					"\n" +
					"Integer parameters can be any int value, however, there are restrictions for String values. You should only use String values given below:\n" +
					"\n" +
					"Color List:\n" +
					"\n" +
					"\"RED\", \"BLACK\", \"BLUE\", \"GREEN\", \"YELLOW\", \"WHITE\", \"ORANGE\", \"MAGENTA\", \"CYAN\", \"PINK\"\n" +
					"\n" +
					"Directions List:\n" +
					"\n" +
					"\"E\", \"W\", \"S\", \"N\", \"NW\", \"NE\", \"SW\", \"SE\"\n" +
					"\n" +
					"String parameters are represented inside a method with $ (Dollar Sign) followed by parameter no, for example:\n" +
					"\n" +
					"$1: Represents the first String parameter\n" +
					"$10: Represents the tenth String parameter\n" +
					"\n" +
					"and Integer parameters are represented inside a method with # followed by parameter no, for example:\n" +
					"\n" +
					"#2: Represents the second Integer parameter\n" +
					"\n" +
					"Calling a method with parameters red,yellow,1,2 will be treated as $1: red, $2: yellow, #1: 1, #2: 2 inside that method\n" +
					"\n" +
					"Pre-defıned specıal methods are:\n" +
					"\n" +
					"GOTO row_no col_no: Goes to cell (pixel) in (row_no,col_no)\n" +
					"INK: Paints that cell\n" +
					"CLEARALL: Clear the board\n" +
					"CLEAR row_no col_no: Clears the cell (row_no,col_no)\n" +
					"COLOR color_name: Set the color to given color using a color defined in Color List\n" +
					"METHOD method_name method_params(seperated with a comma (,)) no_of_recurrence: Calls a method with method_name using given method_params which is repeatedly called by no_of_recurrence times\n" +
					"MARCH direction_name: Sets the direction of the pointer for the next move using a direction_name listed in Direction List");
			JScrollPane scrollPane = new JScrollPane(textArea);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
			JOptionPane.showMessageDialog(null, scrollPane);

		}

	}

	public void repaintBoard()
	{
		boardPanel.revalidate();
		boardPanel.repaint();
	}
	

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}