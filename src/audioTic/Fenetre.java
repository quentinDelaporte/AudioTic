package audioTic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;


 
public class Fenetre extends JFrame implements ActionListener{
  private Panneau pan = new Panneau();
  private Button pause = new Button("||");
  private Button next = new Button(">>");
  private Button previous = new Button("<<");
  private Button close = new Button("x");
  private Panneau container = new Panneau();
  private Panneau container1 = new Panneau();
  private Panneau container2 = new Panneau();
  private JTextField saisiePath = new JTextField("C:/users/...");
  private Button valider = new Button("Valider");
  public String titleTrack="";
  private label titre = new label("audioTic");
  private label titleTracks = new label(titleTrack);
  public static Sound music;
  int maxNbPiste=0;
  public String[] musicPaths= new String[1000];
  public int numPiste=0;
  public int timecode=0;
  private Thread play;
  public int rep = 0;
  int MusicIsStart=0;
  boolean PlayIsPause=false;
  String path="";
  
  
  public static void main(String args[]) {
      new Fenetre();
  }
  
  
  public Fenetre(){    


    this.setTitle("Animation");
    this.setSize(300, 300);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
 
    container.setBackground(Color.white);
    container.setLayout(new BorderLayout());
    container.add(pan, BorderLayout.CENTER);

    pause.addActionListener(new PauseListener()); 
    next.addActionListener(new NextListener()); 
    previous.addActionListener(new PreviousListener()); 
    close.addActionListener(new closeListener()); 
    valider.addActionListener(new validPathListener()); 

    
    Box boutonControles = Box.createHorizontalBox();
    boutonControles.add(previous);
    boutonControles.add(pause);
    boutonControles.add(next);
    boutonControles.add(close);
    
    Box pathSelect = Box.createHorizontalBox();
    pathSelect.add(saisiePath);
    pathSelect.add(valider);
    
    Box Controles = Box.createVerticalBox();
    Controles.add(boutonControles);
    Controles.add(pathSelect);
    
    
    container1.add(Controles);
    container2.add(titleTracks);
    
    Font police = new Font("Tahoma", Font.BOLD, 16);  
    titre.setFont(police);  
    titre.setForeground(Color.blue);  
    titre.setHorizontalAlignment(JLabel.CENTER);

    container.add(titre, BorderLayout.NORTH);
    container.add(container2, BorderLayout.CENTER);
    container.add(container1, BorderLayout.SOUTH);

    this.setContentPane(container);
    this.setVisible(true);
  }

   public void actionPerformed(ActionEvent arg0) {

  }    
  	  
	  public boolean isPlay() {
	      return PlayIsPause;
	  }
	  private void miseAJourAffichage() {
		  String titleTr=musicPaths[numPiste].substring(musicPaths[numPiste].lastIndexOf("/")+1);
		  titleTracks.setText(titleTr.substring(0,titleTr.length()));
	  }
	  
	  public void startOrcloseMusic() {
		  if(MusicIsStart==0) {
			  MusicIsStart++;
			  startMusic();

		  } else {
			  System.err.println("erreur fichier deja lancé");
		  }
	  }
	
	  public void startMusic() {
		   play = new Thread(){
	          public void run(){
	              try {
	              	  System.out.println("musique lance");
	                  music = new Sound(musicPaths[numPiste]);
	                  PlayIsPause=false;
	                  music.play();
	                                   
	              } catch (Exception ex) {
	            	  System.err.println("erreur lors du lancement de la musique");
	              }
	          }
	  	};
		  play.start();
	  }
	  
	  public void closeMusic() {
		  new Thread(new Runnable(){
			  public void run(){
				  PlayIsPause=false;
				  music.close();
				  System.out.println("musique close");          
			  }
	       }).start();
	  }
	  

	
	 public void next() {
		 new Thread(new Runnable(){
	 		public void run(){
		    		if(numPiste+1<=maxNbPiste) {
		    			numPiste=numPiste+1;
			    	}else {
			    		numPiste=0;
			    	}
		    		closeMusic();
		    		startMusic();
		    		miseAJourAffichage();
	 		}
	 	}).start();
	 }
	 
	  class validPathListener implements ActionListener{
		    public void actionPerformed(ActionEvent arg0) {
		    	path=saisiePath.getText();
		    	System.out.println(path);
		    	TraitementPath();
		    }
	  }
	 
	  class NextListener implements ActionListener{
		    public void actionPerformed(ActionEvent arg0) {
		    	next();
		    }
	  }
	  
	  class PreviousListener implements ActionListener{
		    public void actionPerformed(ActionEvent arg0) {
		    	new Thread(new Runnable(){
		    		public void run(){
				    	if(0<=numPiste-1) {
				    		numPiste--;
				    	} else {
				    		numPiste=0;
				    	}	
			    		closeMusic();
			    		startMusic();
			    		miseAJourAffichage();
	
				    }
				}).start();
		    }
	  }
	  class closeListener implements ActionListener{
		    public void actionPerformed(ActionEvent arg0) {
		    	new Thread(new Runnable(){
		    		public void run(){
			    		closeMusic();
			    		MusicIsStart=0;
		    		}
		    	}).start();
		    }
	  }
	  class PauseListener implements ActionListener{
		    public void actionPerformed(ActionEvent arg0) {  
			    miseAJourAffichage();
		    	startOrcloseMusic();	    	
		    }
	  }
	  
   	  public void generatePlaylist() {
   		  System.out.println("generation de la playliste");
   		  System.out.println("chemin:" + path);
   		  File repertoire = new File(path);
   		  File[] files=repertoire.listFiles();
   		  for(int i = 0; i <= 4 ; i++){
            String fileName = files[i].getName();
            String uName = fileName;
            musicPaths[i]=path+uName;
            System.out.println(path+uName);
            maxNbPiste++;
   		  }
   	  }
   	  //C:/Users/Quentin/eclipse-workspace/AudioTic/src/audioTic/audioSrc/
   	  public void TraitementPath() {
   		  for(int i=0; i<path.length();i++) {
   			  if(path.charAt(i)==' ') {
   				  path=path.substring(0,i)+path.substring(i+1,path.length());
   			  } else if(path.charAt(i)=='\\') {
   				  path=path.substring(0,i)+'/'+path.substring(i+1,path.length());

   			  }
   		  }
   		  
   		  Path direct = Paths.get(path);
   		  if(Files.exists(direct)) {
	   		  System.out.println("nouveau chemin="+path);
	   		  generatePlaylist();
   		  } else {
   			  System.err.println("chemin erroné: " + path);
   		  }

   	  }
	  


}
  


