import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class promptgen {
	
	Container cont;																					//Container
	JFrame mainwindow;																				//Frame Utama
	JPanel title, startPanel, promptPanel, buttonPanel, savedPanel, savedPrompt;					//Panel
	JLabel titleName, titleName2, promptNoun, promptAdj, prompt1, prompt2, prompt3 , promptFull;	//Label
	JButton startButton, generateButton, saveButton, eraseButton;									//Button
	Font titleFont = new Font   ("Courier New", Font.ITALIC, 40);									//Font
	Font titleFont2 = new Font  ("Courier New", Font.PLAIN,  30);
	Font startFont = new Font   ("Courier New", Font.PLAIN,  35);
	Font normalFont = new Font  ("Courier New", Font.PLAIN,  24);
	Font promptFont = new Font  ("Courier New", Font.PLAIN,  40);
	Color boneWhite, coal_black, softWhite, softShade, softGray, softBlack;							//Color
	TitleScreenHandler tsHandler = new TitleScreenHandler();										//Event Handler buat tombol
	GeneratorHandler genHandler = new GeneratorHandler();
	SavePromptHandler saveHandler = new SavePromptHandler();
	DeletePromptHandler deleteHandler = new DeletePromptHandler();
	
	Stack<String> savedPrompts = new Stack<String>();												//Stack Prompt
	
	String adjective[] = {"Funky", "Scary", "Edgy", "Sad", "Furious", "Cool", "Hungry", "Gorgeous", "Ugly", "Sleepy"};	//Adjective
	String noun[] = {"Woman", "Man", "Monkey", "Girrafe", "Rat", "Wolf", "Snake", "House", "Furry", "Car"};				//Noun
	int randMin, randMax, random, maxSavedPrompt;																		//Integer
	String selectedAdj, selectedNoun, currentPrompt;																	//String
	
	
	public static void main(String[] args) {
		new promptgen();	//Buat instansi GUI baru
	}
	
	public promptgen() {
		
		maxSavedPrompt = 0;  //Penumpukan prompt
		
		softWhite  = new Color(240, 245, 249) ;    //Soft White
		softShade  = new Color(201, 214, 223) ;    //Soft Shade
		softGray   = new Color(82, 97, 107)   ;    //Soft Gray
		softBlack  = new Color(30, 32, 34)    ;    //Soft Black
		boneWhite  = new Color(247, 245, 238) ;    //Bone White
		coal_black = new Color(26, 26, 26)    ;    //Coal Black
		
		mainwindow = new JFrame();		//Main Window
		mainwindow.setSize(800, 600);	//Ukuran window
		mainwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Close
		mainwindow.getContentPane().setBackground(softWhite);		//Warna BG
		mainwindow.setLayout(null);									//Layout kosong
		mainwindow.setVisible(true);								//Transparansi
		cont = mainwindow.getContentPane();							//Membuat window sebagai container yang dapat menampung komponen
		
		title = new JPanel();		//Title
		title.setBounds(50, 100, 665, 110);	//Ukuran Panel
		title.setBackground(softBlack);		//Warna
		titleName = new JLabel("Drawing Prompt Generator");	//Judul dan nama
		titleName2 = new JLabel("[REDACTED NAME]");
		titleName.setForeground(softShade);
		titleName2.setForeground(softWhite);
		titleName.setFont(titleFont);						//menentukan font yang digunakan
		titleName2.setFont(titleFont2);
		title.add(titleName);								//Label judul nama dimasukkan dalam panel
		title.add(titleName2);
		
		startPanel = new JPanel();		//Start Button
		startPanel.setBounds(180, 400, 400, 100);
		startPanel.setBackground(softWhite);
		startButton = new JButton("GENERATE");		//buat button
		startButton.setBackground(Color.black);
		startButton.setForeground(softWhite);
		startButton.setFont(startFont);
		startButton.setFocusPainted(false);			//Supaya menghilangkan garis focused button
		startButton.addActionListener(tsHandler);	//Menyambungkan button ke eventListener
		startPanel.add(startButton);				//Menambahkan button ke panel tombol start
		

		cont.add(title);		//Memasukkan judul dan tombol kedalam container
		cont.add(startPanel);
	}
	
	
	public void promptScreen() {
		title.setVisible(false);		//Menghilangkan tampilan main menu
		startPanel.setVisible(false);
		
		
		promptPanel = new JPanel(); 					//Panel buat Prompt
		promptPanel.setBounds(65, 20, 650, 80);
		promptPanel.setBackground(softGray);
		promptPanel.setLayout(new GridLayout(1, 2));
		cont.add(promptPanel);
		
		promptAdj = new JLabel("  Adjective");			//VerbText
		promptAdj.setFont(promptFont);
		promptAdj.setForeground(softWhite);
		promptPanel.add(promptAdj);
		
		promptNoun = new JLabel("  Noun");			//NounText
		promptNoun.setFont(promptFont);
		promptNoun.setForeground(softWhite);
		promptPanel.add(promptNoun);
		
		buttonPanel = new JPanel();						//Panel buat button
		buttonPanel.setBounds(65, 150, 650, 140);
		buttonPanel.setBackground(softWhite);
		buttonPanel.setLayout(new GridLayout (4, 1));
		cont.add(buttonPanel);
		
		generateButton = new JButton("Generate Prompt");	//Tombol Generate
		generateButton.setBackground(coal_black);
		generateButton.setForeground(boneWhite);
		generateButton.setFont(normalFont);
		generateButton.setFocusPainted(false);
		generateButton.addActionListener(genHandler);
		buttonPanel.add(generateButton);
		
		saveButton = new JButton("Save Current Prompt");	//Tombol simpan
		saveButton.setBackground(softBlack);
		saveButton.setForeground(softWhite);
		saveButton.setFont(normalFont);
		saveButton.setFocusPainted(false);
		saveButton.addActionListener(saveHandler);
		buttonPanel.add(saveButton);
		
		eraseButton = new JButton("Delete Last Prompt");	//Tombol Hapus
		eraseButton.setBackground(softBlack);
		eraseButton.setForeground(softWhite);
		eraseButton.setFont(normalFont);
		eraseButton.setFocusPainted(false);
		eraseButton.addActionListener(deleteHandler);
		buttonPanel.add(eraseButton);
		
		savedPrompt = new JPanel();						//Saved Prompt Panel
		savedPrompt.setBounds(65, 300, 650, 200);
		savedPrompt.setBackground(softShade);
		savedPrompt.setLayout(new GridLayout(4, 1));
		cont.add(savedPrompt);
		
		prompt1 = new JLabel("  ");						//Label prompt 1
		prompt1.setFont(normalFont);
		prompt1.setForeground(softBlack);
		savedPrompt.add(prompt1);
		
		prompt2 = new JLabel("  ");						//Label prompt 2
		prompt2.setFont(normalFont);
		prompt2.setForeground(softBlack);
		savedPrompt.add(prompt2);

		prompt3 = new JLabel("  ");						//Label prompt 3
		prompt3.setFont(normalFont);
		prompt3.setForeground(softBlack);
		savedPrompt.add(prompt3);

		promptFull = new JLabel("  Your saved prompts goes here");	//Label prompt note
		promptFull.setFont(normalFont);
		promptFull.setForeground(softBlack);
		savedPrompt.add(promptFull);

		
		
	}
	
	
	public void generatePrompt() {
		randMin = 0;		//Batas min max dalam mengambil angka random
		randMax = 9;
		
		random = (int)Math.floor(Math.random()*(randMax-randMin+1)+randMin); //Angka random antara 1-10
		promptAdj.setText("  " + adjective[random]);						//menulis Adj/Noun dengan yang terpilih
		selectedAdj = adjective[random];
		
		random = (int)Math.floor(Math.random()*(randMax-randMin+1)+randMin);
		promptNoun.setText("  " + noun[random]);
		selectedNoun = noun[random];
		
		currentPrompt = selectedAdj +" "+ selectedNoun;						//Mengkombinasikan Adj/Noun
	}
	
	
	public void savePrompt() {
		if (maxSavedPrompt < 3) {									//Jika Prompt dibawah 3
			promptFull.setText("  ");								//Menghapus Note
			savedPrompts.push(currentPrompt);						//Menambahkan prompt ke stack
			maxSavedPrompt += 1;									//Menambah count total prompt
			switch(maxSavedPrompt) {
			case (1):
				prompt1.setText("  " + savedPrompts.peek());		//Mengisi di posisi prompt 1 dst
			break;
			case (2):
				prompt2.setText("  " + savedPrompts.peek());
			break;
			case (3):
				prompt3.setText("  " + savedPrompts.peek());
			}
		}
		else {
			promptFull.setText("  Delete some prompts first!");		//Menampilkan text bahwa prompt sudah penuh
		}
	}
	
	
	public void deletePrompt() {
		promptFull.setText("  ");
		if (maxSavedPrompt > 0) {				//Jika masih ada sisa prompt
			savedPrompts.pop();					//Menghilangkan prompt dari stack
			maxSavedPrompt -= 1;				//Mengurangi count prompt
		}

		switch(maxSavedPrompt) {
		case (0):
			prompt1.setText("  ");				//Menghapus text prompt
		break;
		case (1):
			prompt2.setText("  ");
		break;
		case (2):
			prompt3.setText("  ");
		break;
		}
		
		if (maxSavedPrompt == 0) {
			promptFull.setText(" Save some prompts first!");	//Menampilkan Text bahwa prompt kosong
		}
	}
	
	
	public class TitleScreenHandler implements ActionListener {  //Start Button
		public void actionPerformed(ActionEvent event) {
			promptScreen();
		}
	}
	
	public class GeneratorHandler implements ActionListener{	//Generate Button
		public void actionPerformed(ActionEvent event) {
			generatePrompt();
		}
	}
	
	public class SavePromptHandler implements ActionListener{	//Save Button
		public void actionPerformed(ActionEvent event) {
			savePrompt();
		}
	}
	
	public class DeletePromptHandler implements ActionListener{	//Delete Button
		public void actionPerformed(ActionEvent event) {
			deletePrompt();
		}
	}
}
