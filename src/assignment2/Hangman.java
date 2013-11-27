package assignment2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Hangman extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int gusleft, difficulty;
	boolean done;
	private String dashFill, word, missletters;
	
	private JLabel lblGuessesLeft;
	private JTextField txtGuessleftnumber, txtGuessfield, txtUserguess, textField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hangman frame = new Hangman();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String get_word() throws IOException {
		int rand = new Random().nextInt(63)+1;
		BufferedReader br = new BufferedReader(new FileReader("Words"));
		String line = null;
		for (int i =0; i<=rand;i++){
			line = br.readLine();
		}
		br.close();		
		return line;
	}
	
	public void doubleletterentrydisplay(char c){
		JOptionPane.showMessageDialog(null, "You have already entered letter "+c, "Already Entered", JOptionPane.ERROR_MESSAGE);	   
	}

	public void youwondisplay(){
		JOptionPane.showMessageDialog(null, "You won!","Message", JOptionPane.ERROR_MESSAGE);	   
	}

	public void nochancesdisplay(){
		JOptionPane.showMessageDialog(null, "No chances left", "Message", JOptionPane.ERROR_MESSAGE);	   
	}

	public void entervalid(){
		JOptionPane.showMessageDialog(null, "Enter Valid Characters", "Message", JOptionPane.ERROR_MESSAGE);	   
	}

	public void changedToHard() {
		reset(2,5);
	}


	public void changedToMediumDef() {
		reset(0,7);
	}

	public void changedToEasy() {
		reset(1,10);
	}
	
	public void reset(int ldifficulty,int moves){
		gusleft = moves;
		difficulty = ldifficulty;
	}
	
	
	/**
	 * Create the frame.
	 */
	public Hangman() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		JMenuItem mntmStart = new JMenuItem("Start");
		mnGame.add(mntmStart);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnGame.add(mntmQuit);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JRadioButtonMenuItem rdbtnmntmEasy = new JRadioButtonMenuItem("Easy");
		rdbtnmntmEasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changedToEasy();
			}
		});
		
		mnOptions.add(rdbtnmntmEasy);
		
		JRadioButtonMenuItem rdbtnmntmMedium = new JRadioButtonMenuItem("Medium");
		// To make medium level as default
		rdbtnmntmMedium.setSelected(true);
		rdbtnmntmMedium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changedToMediumDef();
				
			}

		});
		mnOptions.add(rdbtnmntmMedium);
		
		JRadioButtonMenuItem rdbtnmntmHard = new JRadioButtonMenuItem("Hard");
		rdbtnmntmHard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changedToHard();
			}
		});
		mnOptions.add(rdbtnmntmHard);
		
		ButtonGroup radioBG = new ButtonGroup();
		radioBG.add(rdbtnmntmEasy);
		radioBG.add(rdbtnmntmMedium);
		radioBG.add(rdbtnmntmHard);
		getContentPane().setLayout(null);
		
		lblGuessesLeft = new JLabel("Guesses Left :");
		lblGuessesLeft.setBounds(59, 31, 100, 14);
		getContentPane().add(lblGuessesLeft);
		
		txtGuessleftnumber = new JTextField();
		txtGuessleftnumber.setBounds(179, 28, 24, 20);
		getContentPane().add(txtGuessleftnumber);
		txtGuessleftnumber.setColumns(10);
		txtGuessleftnumber.setEditable(false);
		txtGuessleftnumber.setText(Integer.toString(gusleft));
		
		JLabel lblCurrentStatus = new JLabel("Current Status :");
		lblCurrentStatus.setBounds(59, 68, 110, 14);
		getContentPane().add(lblCurrentStatus);
		
		txtGuessfield = new JTextField();
		txtGuessfield.setText("_ _ _ _ _ _ _ _ _ _ _ _");
		txtGuessfield.setEditable(false);
		txtGuessfield.setBounds(179, 65, 127, 20);
		getContentPane().add(txtGuessfield);
		txtGuessfield.setColumns(10);
		
		JLabel lblGuess = new JLabel("Guess :");
		lblGuess.setBounds(59, 109, 46, 14);
		getContentPane().add(lblGuess);
		
		txtUserguess = new JTextField();
		txtUserguess.setBounds(179, 106, 24, 20);
		getContentPane().add(txtUserguess);
		txtUserguess.setColumns(10);
		txtUserguess.setEditable(false);
		
		
		JLabel lblMisses = new JLabel("Misses :");
		lblMisses.setBounds(59, 145, 69, 14);
		getContentPane().add(lblMisses);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(179, 145, 232, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		class quitaction implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		
		class startaction implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					startact();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		}
		
		mntmQuit.addActionListener(new quitaction());
		mntmStart.addActionListener(new startaction());
		
	}

	
	private void startact() throws IOException{
		word = get_word();
//		System.out.println(word);
		dashFill = "-";
		for(int i = 1;i<word.length();i++){
			dashFill = dashFill.concat("-");
		}
		if(difficulty== 0){
			changedToMediumDef();
		}
		
		txtGuessfield.setText(dashFill);
		txtUserguess.setEditable(true);
		gameloop(dashFill);
	}

	private void gameloop(String dashFill){
		missletters = "";
		done = false;
		txtGuessleftnumber.setText(Integer.toString(gusleft));
		txtUserguess.setEditable(false);
		txtUserguess.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
            	char ch = e.getKeyChar();
               if(isenteredagain(ch)){
            	  doubleletterentrydisplay(ch);
               }
               else{
            	if(Character.isLetter(ch)){
                    txtUserguess.setText(Character.toString(ch).toUpperCase());
                    
                }
                else{
                	entervalid();
                }
                if(gusleft==0 && done == false){
        			txtUserguess.removeKeyListener(txtUserguess.getKeyListeners()[0]);
        			txtUserguess.setEditable(false);
        			nochancesdisplay();
                }
                if(isexist(ch)){
            		txtGuessfield.setText(replacedash(ch));
            		if(won()){
            			txtUserguess.removeKeyListener(txtUserguess.getKeyListeners()[0]);
            			txtUserguess.setEditable(false);
            			youwondisplay();
            		}
                }
                else if(Character.isLetter(ch)){
                	gusleft--;
                	txtGuessleftnumber.setText(Integer.toString(gusleft));
                	missletters = missletters.concat(Character.toString(ch)).toUpperCase();
                	textField.setText(missletters);
                }
            }
        }});
	}

	
	protected boolean won() {
		if(dashFill.equalsIgnoreCase(word)){
		return true;	
		}
		else{
		return false;
		}
	}

	private boolean isexist(char ch){
		boolean existed = false;
		for(int i = 0;i<word.length();i++){
			if(word.charAt(i) == ch||word.charAt(i) == Character.toUpperCase(ch)){
				existed = true;
			}
		}
		return existed;
	}
	
	private boolean isenteredagain(char ch){
		return dashcheck(ch)||misslettercheck(ch);
		
	}
	
	private boolean dashcheck(char ch){
		boolean existed = false;
		for(int i = 0;i<missletters.length();i++){
			if(missletters.charAt(i) == ch||missletters.charAt(i) == Character.toUpperCase(ch)){
				existed = true;
			}
		}
		return existed;
	}
	
	private boolean misslettercheck(char ch){
		boolean existed = false;
		for(int i = 0;i<dashFill.length();i++){
			if(dashFill.charAt(i) == ch||dashFill.charAt(i) == Character.toUpperCase(ch)){
				existed = true;
			}
		}
		return existed;
		
	}
	
	private String replacedash(char ch){
		char[] myNameChars = dashFill.toCharArray();
		for(int i = 0;i<word.length();i++){
			if(word.charAt(i) == ch||word.charAt(i) == Character.toUpperCase(ch)){
				myNameChars[i] = Character.toUpperCase(ch);
				dashFill = String.valueOf(myNameChars);
			}
		}
		return dashFill;
	}
}