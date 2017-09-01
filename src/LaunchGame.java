import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/* Daisha Braxton
 * CSCI 1302 - Final Project (updated)
 * 5/4/2016
 */

public class LaunchGame extends Application{
	static int numOfPlayers = 2;
	public static void main(String[] args){
		launch();
	}
	public void start(Stage primaryStage){
		
		HBox icon = new HBox();
		Image image = new Image("image/CAL.jpg"); //adding an image icon to the start pane
		ImageView CandL = new ImageView();
		CandL.setImage(image);
		CandL.setFitWidth(250); //setting the width of the image
		CandL.setPreserveRatio(true); //keeping the same picture ratio
		CandL.setSmooth(true);
		CandL.setCache(true);
		icon.getChildren().add(CandL);
		
		VBox vBottom = new VBox();
		Rectangle[] r = new Rectangle[4];
		
		for(int i=0; i<4; i++){
			r[i] = new Rectangle();
			r[i].setStroke(Color.BLACK);
			r[i].setFill(Color.BLANCHEDALMOND);
			r[i].setWidth(10);
			r[i].setHeight(10);
			
		}
		BorderPane bPane = new BorderPane();
		
		VBox settings = new VBox(20);
		settings.setPadding(new Insets(0, 20, 20, 42));
	    RadioButton p2 = new RadioButton("2 Players");
	    RadioButton p3 = new RadioButton("3 Players");
	    RadioButton p4 = new RadioButton("4 Players");
	    
	    settings.getChildren().addAll(icon, p2, p3, p4);
	   
	    
	    ToggleGroup group = new ToggleGroup();
	    p2.setToggleGroup(group);
	    p3.setToggleGroup(group);
	    p4.setToggleGroup(group);
	    
	    p2.setOnAction(e -> {
	      if (p2.isSelected()) { numOfPlayers = 2; }});
	    
	    p3.setOnAction(e -> {
	      if (p3.isSelected()) { numOfPlayers = 3; }});

	    p4.setOnAction(e -> {
	      if (p4.isSelected()) { numOfPlayers = 4; }});
		
	    Button nxtScrn = new Button("Continue...");
		
		HBox buttonBox = new HBox(nxtScrn);
		buttonBox.setAlignment(Pos.BASELINE_CENTER);
		buttonBox.setSpacing(5);
		
		
		vBottom.getChildren().add(buttonBox);
		vBottom.setAlignment(Pos.BASELINE_CENTER);
		vBottom.setSpacing(5);
		vBottom.setPadding(new Insets(20, 0, 40, 0));
		
		Label playerSelectLabel = new Label("Welcome to JavaFx Chutes and Ladders!\n\tSelect the number of Players: \n\t\t\t");
		playerSelectLabel.setPadding(new Insets(15, 50, 20, 70));
		
		//The continue button takes you to player settings window
		nxtScrn.setOnAction(e1 ->{
			PlayerSettings pinfostarter = new PlayerSettings();
			try {
				pinfostarter.start(numOfPlayers, r);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			primaryStage.hide();
		});
		
		bPane.setCenter(settings);
		bPane.setTop(playerSelectLabel);
		bPane.setBottom(vBottom);
		
		//setting background color
		bPane.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, null, null)));
		
		Scene scene = new Scene(bPane, 350, 450);
		primaryStage.setTitle("Chutes and Ladders - Welcome!");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}

