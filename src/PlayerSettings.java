import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/* Daisha Braxton
 * CSCI 1302 - Final Project (updated)
 * 5/4/2016
 */

public class PlayerSettings {
	Stage primaryStage = new Stage();
	
	public void start(int numOfPlayers, Rectangle[] r){

		BorderPane bPane = new BorderPane();
		
		
		Label text = new Label("Enter player names:");
		Label[] pTags = new Label[4]; // player tags
		String[] names = new String[4]; //players' names
		
		text.setPadding(new Insets(10,0,0,60));
		
		TextField[] nameTxt = new TextField[numOfPlayers];
		Button start = new Button("Start");
				
		for(int i=0; i<numOfPlayers; i++){
			
			nameTxt[i] = new TextField("Enter Your Name:");
			
		}
		
		
		//setting colors of the rectangle player icons
			r[0].setFill(Color.RED);
		nameTxt[0].setOnMouseClicked(e -> {
			nameTxt[0].setText("");
		});
		
		r[1].setFill(Color.YELLOW);
		nameTxt[1].setOnMouseClicked(e -> {
			nameTxt[1].setText("");
		});
		
		if(numOfPlayers >= 3){
				r[2].setFill(Color.GREEN);
			nameTxt[2].setOnMouseClicked(e -> {
				nameTxt[2].setText("");
			});
				if(numOfPlayers > 3){
					r[3].setFill(Color.DARKBLUE);
					nameTxt[3].setOnMouseClicked(e -> {
						nameTxt[3].setText("");
					});
					}
				}
		
		VBox[] vbox1 = new VBox[6];
		HBox hbox = new HBox();
		
		for(int i=0; i<4; i++){
			pTags[i] = new Label("Player: " +(i+1));
			vbox1[i] = new VBox();
			vbox1[i].setSpacing(10);
			hbox.getChildren().add(vbox1[i]);
		}
//*implemented late in the project. Sliders to select how many chutes and ladders you want.
		HBox lSliderBox = new HBox();
		HBox cSliderBox = new HBox();
		Slider ladderSlider = new Slider();
		Slider chuteSlider = new Slider();
		ladderSlider.setValue(5);
		chuteSlider.setValue(5);
		lSliderBox.getChildren().add(ladderSlider);
		cSliderBox.getChildren().add(chuteSlider);
		vbox1[5] = new VBox();
		vbox1[4] = new VBox();
		hbox.getChildren().add(vbox1[4]);

		//Adding children		
		for(int j=0; j<numOfPlayers; j++){
			vbox1[0].getChildren().add(pTags[j]);
			vbox1[2].getChildren().add(r[j]);
			vbox1[3].getChildren().add(nameTxt[j]);
		}
		vbox1[4].getChildren().add(vbox1[5]);
		
		hbox.setSpacing(10);
		hbox.setPadding(new Insets(20,0,0,-10));
		hbox.setAlignment(Pos.BASELINE_CENTER);
		vbox1[0].setSpacing(20);
		vbox1[2].setSpacing(26);
		vbox1[2].setPadding(new Insets(5,0,0,-15));
		
		//starts the Game		
		start.setOnAction(e -> {
			GameBoard board = new GameBoard();
			try {
				for(int i=0; i<numOfPlayers; i++){
					if((nameTxt[i].getText().equals("Input Name:")||nameTxt[i].getText().equals("")) == true){
						names[i] = "Player "+(i+1);
					}
					else{
						names[i]= nameTxt[i].getText();
					}
				}
				board.start(numOfPlayers, r, false, names, ladderSlider.getValue(), chuteSlider.getValue());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			primaryStage.hide();
		});

		
		bPane.setCenter(hbox);
		bPane.setTop(text);
		bPane.setBottom(start);

		
		bPane.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, null, null)));
		Scene scene = new Scene(bPane, 500, 320);
		primaryStage.setTitle("Player Settings");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.show();
						
	}
}
