import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class BoardFrame extends JFrame implements ActionListener
{
	private JComboBox methodBox;
	private Board board;
	private BoardPanel bp;
	private JPanel commandPanel, rightPanel, buttonPanel, bottomPanel;
	private JTextArea textArea, methodArea;
	private JTextField tf,rowsField,colsField;
	private JCheckBox showGrids;
	private JButton run, save, save2, delete;
	private Command[] commandList;
	private CommandPerformer commandPerformer;
	private Timer delay;
	private String tfString = "Enter a method name", methodLoc = "methods/";
	private ArrayList<Method> methodList;
	public BoardFrame(Board board,Command[] commandList, ArrayList<Method> methodList)
	{
	//to  Set JFrame title
		super("PiXY");
		this.methodList = methodList;
		this.commandList = commandList;
		commandPerformer = new CommandPerformer(board, commandList);
		bp = new BoardPanel();
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2,1));
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,3));
		
		//setLayout(new BorderLayout());
		setLayout(new GridLayout(1, 2));
		add(bp);
		add(rightPanel);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		tf = new JTextField(tfString);
		
		rowsField = new JTextField("" + board.getRows());
		colsField = new JTextField("" + board.getCols());
		
		commandPanel = new JPanel();
		commandPanel.setLayout( new BorderLayout());
		textArea = new JTextArea();
		run = new JButton("Run!");
		run.setMnemonic(KeyEvent.VK_ENTER); //hotkey is alt+enter
		save = new JButton("Save");
		save.setMnemonic(KeyEvent.VK_S); //hotkey is alt+s
		save2 = new JButton("Save");
		delete = new JButton("Delete");
		showGrids = new JCheckBox("Show Grids");
		
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
		
		bp.repaint();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bp.repaint();
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
            catch(Exception e){
            	System.out.println("Rows should be Integer");

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
				if(tf.getText().equals(tfString) || tf.getText().length() <= 0)
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
			if(tf.getText().equals(tfString) || tf.getText().length() <= 0)
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
	}

	public void repaintBoard()
	{
		bp.revalidate();
		bp.repaint();
	}
	

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}