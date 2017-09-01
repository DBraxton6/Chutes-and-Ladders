import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JFileChooser;

/* Daisha Braxton
 * CSCI 1302 - Final Project (updated)
 * 5/4/2016
 */

public class GameBoard {
	private Stage primaryStage = new Stage();
	
	//Variable to hold the players turn.
	private int turn = 0;
	
	// 3d array of random ladders (I had major help with this)
	static int[][][] randomLadders;
	static int reset = 0;
	int numLadders; //ladders are red
	int numChutes; //chutes are black
	
	//private variables "spinner" and "numOfPlayers"
	private int spinner;
	private int numOfPlayers;
	
	//creation of spinner button
	private Button spinButton = new Button("Spin");

	
	Label pTurn = new Label(); //player turn label
	Label spinnerSpin = new Label(); // spinner label
	private Label CandL = new Label(); // chutes and ladders label
	private Label pLocation = new Label(); // player location label
	private Label gameEnd = new Label(); // end of game label
	private VBox recBox = new VBox();
	
	//Player array.
	private Player[] players;

	static boolean startedGame = false;
	
	//creation of my main pane and stack pane (to hold the 3 different windows)
	Pane mainPane = new HBox(1);
	StackPane startPane = new StackPane();
	
	public void start(int numPlayers, Rectangle[] r2, boolean loadGame, String[] names, double ladders, double chutes) throws Exception {
			numLadders = (int)ladders;
			numChutes = (int)chutes;
			numOfPlayers = numPlayers;
			players = new Player[numOfPlayers];
			
			//for each player  they have a player icon (rectangle) and their name
			for(int i=0; i<numOfPlayers; i++){
				players[i] = new Player(r2[i], names[i]); 
			}
		
		spinButton.setTranslateX(95);
		
		//shows who's turn it is
		pTurn.setText(players[turn].getName() + " it's your turn!\nSpin the spinner");		
		pTurn.setPadding(new Insets(0,0,0,15));
		pTurn.setAlignment(Pos.BASELINE_CENTER);
		
		spinnerSpin.setPadding(new Insets(5,10,0,20));
		
				
		for(int i=0; i<numOfPlayers; i++){
			recBox.getChildren().add(players[i].getPRec());
			if(i == 2){
				recBox.getChildren().get(i).setTranslateX(15);
				recBox.getChildren().get(i).setTranslateY(-26);
				players[i].setPXSpace(players[i].getPXSpace() + 15);
				players[i].setPYSpace(players[i].getPYSpace() - 26);
				
			}
			if(i == 3){
				recBox.getChildren().get(i).setTranslateX(15);
				recBox.getChildren().get(i).setTranslateY(-26);
				players[i].setPXSpace(players[i].getPXSpace() + 15);
				players[i].setPYSpace(players[i].getPYSpace() - 26);
			}
		}
		
		recBox.setPadding(new Insets(343,0,0,-832));
		recBox.setSpacing(2);
		
		if(loadGame == false){
		CreateLadders();
		}
		for(int i=0; i< 20; i++){
			
			VBox gameBoard = new VBox(1);
			for( int j=0; j<9; j++){
				Rectangle r = new Rectangle(40,40);
				r.setStroke(Color.LIGHTYELLOW);
				if(j%2==0){
					r.setFill(Color.SKYBLUE);
					if(j==0 && i ==19){
						r.setFill(Color.PURPLE);	
						}
				}
				else{
					if(i==19 && (j==7 || j==3)){
						r.setFill(Color.SKYBLUE);
					}
					else if(i==0 && (j==1 || j==5)){
						r.setFill(Color.SKYBLUE);
					}
					else{
					r.setFill(Color.LIGHTYELLOW);
					}
				}
				gameBoard.getChildren().add(r);
			}
			mainPane.getChildren().add(gameBoard);
		}
		
		VBox leftSide = new VBox();
		
		leftSide.getChildren().add(pTurn);
		leftSide.getChildren().add(spinButton);
		leftSide.getChildren().add(spinnerSpin);
		leftSide.getChildren().add(pLocation);
		leftSide.getChildren().add(CandL);
		leftSide.getChildren().add(gameEnd);
		
		leftSide.setPadding(new Insets(5, 5, 0, 0));
		leftSide.setSpacing(5);

		//the spinner button calls getSpinnerSum() method; generates a number 1-6
		spinButton.setOnAction(e -> {
			CandL.setText("");
			setSpinnerSum((int)((Math.random()*6)+1));
			
			//checking if a player has won the game
			//player must roll exactly 103 spaces to win
			if(players[turn].getPSquare()+getSpinnerSum() >= 103){
				if(players[turn].getPSquare() +getSpinnerSum() == 103){
					gameEnd.setText("\n\t   Congratulations Player " +(turn+1) + "!\n" +"\t\t\tYou Win!");
					spinButton.setDisable(true); //disables spinner button if game has been won
					
				}
				else{
					gameEnd.setText("\nSorry!\nYou must land exactly on the end space!\nYou rolled a "+ getSpinnerSum());
					setSpinnerSum(0);
				}
			}
			//keeps track of player location
			spinnerSpin.setText("You spun a " + getSpinnerSum() + ". Move " + getSpinnerSum() + " spaces");
					if(players[turn].getFwd() == true){
						while(players[turn].getXPlace() < 19 && players[turn].getYPlace() % 4 == 0 && getSpinnerSum() > 0){
							moveRight();
						}
						while(players[turn].getXPlace() == 19 && ((players[turn].getYPlace() < 2 && players[turn].getYPlace() >= 0) || (players[turn].getYPlace() >= 4 && players[turn].getYPlace() < 6)) && getSpinnerSum() > 0){
							moveUp();
						}
						while(getSpinnerSum() > 0 && players[turn].getPSquare() <= players[turn].getMaxSpaces() && (players[turn].getYPlace() % 2== 0 && players[turn].getYPlace() % 4 != 0)){
							moveLeft();
							players[turn].setFwd(false);
						}
					}
					else if(players[turn].getFwd() == false){
						while(players[turn].getXPlace() > 0 && (players[turn].getYPlace() == 2 || players[turn].getYPlace() == 6) && getSpinnerSum() > 0){
							moveLeft();
						}
						while(players[turn].getXPlace() == 0 && ((players[turn].getYPlace() < 4 && players[turn].getYPlace() >= 2) || (players[turn].getYPlace() >= 6 && players[turn].getYPlace() < 8))  && getSpinnerSum() > 0){
							moveUp();
						}
						while(getSpinnerSum() > 0 && players[turn].getPSquare() <= players[turn].getMaxSpaces() && players[turn].getYPlace() % 4 == 0){
						moveRight();
						players[turn].setFwd(true);
						}
					}
					
					
					//checks to see if a player has landed on a chute or a ladder then moves them backward or forward
					for(int i=0; i<randomLadders.length-numChutes; i++){
						if(players[turn].getXPlace() == randomLadders[i][0][0] && (8-players[turn].getYPlace()) == randomLadders[i][0][1]){
							while(players[turn].getXPlace() != randomLadders[i][1][0]){
								if(players[turn].getXPlace() > randomLadders[i][1][0]){
									players[turn].setPSquare(players[turn].getPSquare()- 1);
									moveLeft();
								}
								else{
									players[turn].setPSquare(players[turn].getPSquare()- 1);
									moveRight();
								}
								
							}
							while((8-players[turn].getYPlace()) != randomLadders[i][1][1]){
								players[turn].setPSquare(players[turn].getPSquare()- 1);
								moveUp();
							}
							for(int j=randomLadders[i][1][1]; j< randomLadders[i][0][1]+1; j++){
								int spacesMoved = 0;
								if(j == randomLadders[i][1][1]){
									if(randomLadders[i][1][1] % 2 != 0){
										spacesMoved += 1;
									}
									else if(randomLadders[i][1][1] % 4 == 0){
									spacesMoved += randomLadders[i][1][0]+1;
									}
									else{
										spacesMoved += 20-randomLadders[i][1][0];
									}
								}
								else if(j == randomLadders[i][0][1]){
									if(randomLadders[i][0][1] % 4 == 0){
										spacesMoved += 19-randomLadders[i][0][0];
									}
									else if(randomLadders[i][0][1] % 4 != 0  && randomLadders[i][0][1] % 2 == 0){
										spacesMoved += randomLadders[i][0][0];
									}
								}
								else if(j%2 == 0){
									spacesMoved += 20;
								}
								else {
									spacesMoved += 1;
								}
								players[turn].setPSquare(players[turn].getPSquare() + spacesMoved);
								
							}
							if(randomLadders[i][1][1] % 4 == 0 || (randomLadders[i][1][1]+1) %  4 == 0){
								players[turn].setFwd(true);
							}
							else{
								players[turn].setFwd(false);
							}
							setSpinnerSum(0);
						}
					}
					for(int i=0; i<randomLadders.length-numLadders; i++){
						if(players[turn].getXPlace() == randomLadders[i+numLadders][1][0] && 8-players[turn].getYPlace() == randomLadders[i+numLadders][1][1]){
							
							while(players[turn].getXPlace() != randomLadders[i+numLadders][0][0]){
								if(players[turn].getXPlace() > randomLadders[i+numLadders][0][0]){
									players[turn].setPSquare(players[turn].getPSquare()- 1);
									moveLeft();
								}
								else{
									players[turn].setPSquare(players[turn].getPSquare()- 1);
									moveRight();
								}
								
							}
							while(8-players[turn].getYPlace() != randomLadders[i+numLadders][0][1]){
								moveDown();
							}
							for(int j=randomLadders[i+numLadders][1][1]; j< randomLadders[i+numLadders][0][1]+1; j++){
								
								int spacesMoved = 0;
								if(j == randomLadders[i+numLadders][1][1]){
									if(randomLadders[i+numLadders][1][1] % 2 != 0){
										spacesMoved += 1;
									}
									else if(randomLadders[i+numLadders][1][1] % 4 == 0){
									spacesMoved += randomLadders[i+numLadders][1][0]+1;
									}
									else{
										spacesMoved += 20-randomLadders[i+numLadders][1][0];
									}
								}
								else if(j == randomLadders[i+numLadders][0][1]){
									if(randomLadders[i+numLadders][0][1] % 4 == 0){
										spacesMoved += 19-randomLadders[i+numLadders][0][0];
									}
									else if(randomLadders[i+numLadders][0][1] % 4 != 0 && randomLadders[i+numLadders][0][1] % 2 == 0){
										spacesMoved += randomLadders[i+numLadders][0][0];
									}
								}
								else if(j%2 == 0){
									spacesMoved += 20;
								}
								else {
									spacesMoved += 1;
								}
								players[turn].setPSquare(players[turn].getPSquare() - spacesMoved);
							}
							
						if(randomLadders[i+numLadders][0][1] % 4 == 0 || (randomLadders[i+numLadders][0][1]+1) % 4 == 0){
							players[turn].setFwd(true);
						}
						else{
							players[turn].setFwd(false);
						}
						setSpinnerSum(0);
						}
		}
					
			
			if(turn < numOfPlayers-1){
			turn++;
			pTurn.setText(players[turn].getName() + " it's your turn!\nSpin the spinner");
			
			}
			else{
				turn = 0;
				pTurn.setText(players[turn].getName() + " it's your turn!\nSpin the spinner");
			}
			Label location = new Label();
			for(int i=0; i<numOfPlayers; i++){
				location.setText(location.getText() +"\n" +players[i].getName() +" is on space " +players[i].getPSquare());
			}
			pLocation.setText(location.getText());		
		});

		
		//adding to the main pane
		mainPane.getChildren().add(recBox);
		mainPane.getChildren().add(leftSide);
		
		
			
		//adding the main pane to the start pane and setting the scene, stage, and title
		startPane.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, null, null)));
		startPane.getChildren().add(mainPane);
		Scene scene = new Scene(startPane, 1070, 380);
		primaryStage.setTitle("Chutes and Ladders");
		primaryStage.setScene(scene);
		primaryStage.show();

		startPane.getChildren().add(createCLImages());
		startedGame = true;
		}
	
	//methods to move the players left, right, up, or down on the board	
	public void moveRight(){;
		players[turn].setPXSpace(players[turn].getPXSpace() + 42);
		players[turn].getPRec().setTranslateX(players[turn].getPXSpace());
		players[turn].setPSquare(players[turn].getPSquare()+ 1);
		players[turn].setXPlace(players[turn].getXPlace()+ 1);
		setSpinnerSum(getSpinnerSum()-1);
		
	}
	public void moveLeft(){
		players[turn].setPXSpace(players[turn].getPXSpace() - 42);
		players[turn].getPRec().setTranslateX(players[turn].getPXSpace());
		players[turn].setPSquare(players[turn].getPSquare()+ 1);
		setSpinnerSum(getSpinnerSum()-1);
		players[turn].setXPlace(players[turn].getXPlace()- 1);
	}
	public void moveUp(){
		players[turn].setPYSpace(players[turn].getPYSpace() - 42);
		players[turn].getPRec().setTranslateY(players[turn].getPYSpace());
		players[turn].setPSquare(players[turn].getPSquare()+ 1);
		players[turn].setMaxSpaces(spinner + players[turn].getPSquare());
		players[turn].setYPlace(players[turn].getYPlace()+ 1);
		setSpinnerSum(getSpinnerSum()-1);
	}
	public void moveDown(){
		players[turn].setPYSpace(players[turn].getPYSpace() + 42);
		players[turn].getPRec().setTranslateY(players[turn].getPYSpace());
		players[turn].setYPlace(players[turn].getYPlace()- 1);
	}
	
	//get and set spinnerSum methods
	public void setSpinnerSum(int spinChange){
		this.spinner = spinChange;
	}
	public int getSpinnerSum(){
		return spinner;
	}

	//creating random chutes and ladders
	//help from peers and online sources
	public void CreateLadders(){
		randomLadders = new int[numLadders+numChutes][2][2];
		for(int i=0; i<numLadders+numChutes; i++){
				
			randomLadders[i][0][0] = (int)(Math.random()*18+1);
			randomLadders[i][0][1] = (int)(Math.random()*9);

			if(randomLadders[i][0][1] == 3 || randomLadders[i][0][1] == 7){
				randomLadders[i][0][0] = 19;
			}
			else if(randomLadders[i][0][1] == 5 || randomLadders[i][0][1] == 1){
				randomLadders[i][0][0] = 0;
			}
			randomLadders[i][1][0] = (int)(Math.random()*18+1);
			randomLadders[i][1][1] = (int)(Math.random()*9);
			while(randomLadders[i][0][1] == randomLadders[i][1][1]){
				randomLadders[i][1][1] = (int)(Math.random()*9);
		}
			if(randomLadders[i][1][1] == 3 || randomLadders[i][1][1] == 7){
				randomLadders[i][1][0] = 19;
			}
			else if(randomLadders[i][1][1] == 5 || randomLadders[i][1][1] == 1){
				randomLadders[i][1][0] = 0;
			}

			// more help from sources (very long code)
			if(i > 0){
				for(int j=0; j<i; j++){
			while(randomLadders[i][0][1] == randomLadders[j][0][1] && randomLadders[i][0][0] == randomLadders[j][0][0] 
					|| randomLadders[i][1][1] == randomLadders[j][1][1] && randomLadders[i][1][0] == randomLadders[j][1][0] 
							|| randomLadders[j][0][0] == randomLadders[i][1][0] && randomLadders[j][0][1] == randomLadders[i][1][1] 
									||randomLadders[i][0][1] == randomLadders[j][1][0] && randomLadders[i][0][1] == randomLadders[j][1][1]
											||randomLadders[i][0][0] == randomLadders[j][1][0] && randomLadders[i][0][1] == randomLadders[j][1][1]
													||randomLadders[i][1][0] == randomLadders[j][1][0] && randomLadders[i][1][1] == randomLadders[j][1][1]){
					randomLadders[i][0][0] = (int)(Math.random()*18+1);
					randomLadders[i][0][1] = (int)(Math.random()*9);
					if(randomLadders[i][0][1] == 3 || randomLadders[i][0][1] == 7){
						randomLadders[i][0][0] = 19;
					}
					else if(randomLadders[i][0][1] == 5 || randomLadders[i][0][1] == 1){
						randomLadders[i][0][0] = 0;
					}
					randomLadders[i][1][0] = (int)(Math.random()*18+1);
					randomLadders[i][1][1] = (int)(Math.random()*9);
					while(randomLadders[i][0][1] == randomLadders[i][1][1]){
						randomLadders[i][1][1] = (int)(Math.random()*9);
				}
					if(randomLadders[i][1][1] == 3 || randomLadders[i][1][1] == 7){
						randomLadders[i][1][0] = 19;
					}
					else if(randomLadders[i][1][1] == 5 || randomLadders[i][1][1] == 1){
						randomLadders[i][1][0] = 0;
					}
				while(randomLadders[i][0][0] == randomLadders[i][1][0] && randomLadders[i][0][1] == randomLadders[i][1][1]){
					randomLadders[i][1][0] = (int)(Math.random()*18+1);
					randomLadders[i][1][1] = (int)(Math.random()*9);
					while(randomLadders[i][0][1] == randomLadders[i][1][1]){
						randomLadders[i][1][1] = (int)(Math.random()*9);
				}
					if(randomLadders[i][1][1] == 3 || randomLadders[i][1][1] == 7){
						randomLadders[i][1][0] = 19;
					}
					else if(randomLadders[i][1][1] == 5 || randomLadders[i][1][1] == 1){
						randomLadders[i][1][0] = 0;
					}
				}
			}

			// more long code; ensures a chute and a ladder aren't on the same space
			for(int k=0; k<j; k++){
				if(randomLadders[k][0][0] == randomLadders[i][0][0] && randomLadders[k][0][1] == randomLadders[i][0][1]
						||randomLadders[k][0][0] == randomLadders[i][1][0] && randomLadders[k][0][1] == randomLadders[i][1][1]
								||randomLadders[k][1][0] == randomLadders[i][0][0] && randomLadders[k][1][1] == randomLadders[i][0][1]
										||randomLadders[k][1][0] == randomLadders[i][1][0] && randomLadders[k][1][1] == randomLadders[i][1][1]){
					j=0;
					reset += 1;
					if(reset == 100){
						for(int l=1; l< randomLadders.length; l++){
						Arrays.fill(randomLadders[l][0], 0);
						Arrays.fill(randomLadders[l][1], 0);
						}
						i=0;
					}
					
					break;
				}
			}
			if(reset == 100){
				System.out.println("Too complex, retrying. \n\n\n\n");
				reset = 0;
				break;
			}
			
			}
			}
		}
		for(int i=0; i<randomLadders.length; i++){
			if(randomLadders[i][0][1] < randomLadders[i][1][1]){
				int place[] = {0, 0};
				place[0] = randomLadders[i][0][1];
				place[1] = randomLadders[i][0][0];
				randomLadders[i][0][1] = randomLadders[i][1][1];
				randomLadders[i][0][0] = randomLadders[i][1][0];
				randomLadders[i][1][1] = place[0];
				randomLadders[i][1][0] = place[1];
			}
			
			// coordinates of chutes and ladders
			System.out.println(randomLadders[i][0][0]+" "+randomLadders[i][0][1] +"  "+randomLadders[i][1][0]+" "+randomLadders[i][1][1]);
		}
	}
	
	
	//connecting the chutes and ladders to the squares on the game board
	public Node createCLImages(){
		Pane pane = new Pane();
		Line[] lines = new Line[randomLadders.length];
		Rectangle[][] r = new Rectangle[randomLadders.length][2];
		for(int i =0; i<randomLadders.length; i++){
			r[i][0] = new Rectangle(40,40);
			r[i][1] = new Rectangle(40,40);
			if(numOfPlayers == 4){
				r[i][0].setTranslateX(42*randomLadders[i][0][0]+.25);
				r[i][0].setTranslateY(42*randomLadders[i][0][1]-5);
				r[i][1].setTranslateX(42*randomLadders[i][1][0]+.25);
				r[i][1].setTranslateY(42*randomLadders[i][1][1]-5);
			}
			else{
			r[i][0].setTranslateX(42*randomLadders[i][0][0]+.25);
			r[i][0].setTranslateY(42*randomLadders[i][0][1]+.25);
			r[i][1].setTranslateX(42*randomLadders[i][1][0]+.25);
			r[i][1].setTranslateY(42*randomLadders[i][1][1]+.25);
			}
			r[i][0].setOpacity(.6);
			r[i][1].setOpacity(.6);
			pane.getChildren().addAll(r[i][0],r[i][1]);
		}
		for(int i=0; i< randomLadders.length; i++){
			lines[i] = new Line();
			if(numOfPlayers == 4){
				lines[i].setStartX(42*randomLadders[i][0][0]+20);
				lines[i].setStartY(42*randomLadders[i][0][1]+15);
				lines[i].setEndX(42*randomLadders[i][1][0]+20);
				lines[i].setEndY(42*randomLadders[i][1][1]+15);
			}
			else{
			lines[i].setStartX(42*randomLadders[i][0][0]+20);
			lines[i].setStartY(42*randomLadders[i][0][1]+20);
			lines[i].setEndX(42*randomLadders[i][1][0]+20);
			lines[i].setEndY(42*randomLadders[i][1][1]+20);
			}
			if(i<numLadders){
			lines[i].setStroke(Color.RED);
			r[i][0].setFill(Color.SKYBLUE);
			r[i][1].setFill(Color.SKYBLUE);
			}
			else{
				lines[i].setStroke(Color.BLACK);
				r[i][0].setFill(Color.SKYBLUE);
				r[i][1].setFill(Color.SKYBLUE);
			}
			pane.getChildren().add(lines[i]);
		}
		pane.setMouseTransparent(true);
		return pane;
	}
	
	
	}
