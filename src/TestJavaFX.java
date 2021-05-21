import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//extends application class to which creates a window
public class TestJavaFX extends Application {

    public double x;
    public double y;
    public void start(Stage mainStage){

        //set text in which appears on the window bar
        mainStage.setTitle("My JavaFX Application");

        //Pane is our layout manager
        Pane root = new Pane();

        //Scene contains content within Stage/Window
        //param: Layout manager(root), width, height
        Scene mainScene = new Scene(root, 800, 600);

        //attach the scene to the stage
        mainStage.setScene(mainScene);

        /*
        // add a text label to the window
        //make sure it says my name
        Label message = new Label("Giuseppe D'Ambrosio");
        message.setLayoutX(400);
        message.setLayoutY(300);
        // must add label to scene graph
        root.getChildren().add( message );
        */


        //add a canvas to the window
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        Image ball = new Image("images/basketball.png", 100, 100, true, true);

        x = 10;
        y = 10;

        //add label to scene graph - IMPORTANT
        root.getChildren().add(canvas);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                //clear the canvas
                graphicsContext.setFill(Color.ALICEBLUE);
                graphicsContext.fillRect(0,0,800,600);

                x += 2;
                y += 1;
                graphicsContext.drawImage(ball, x, y);
            }
        };

        timer.start();

        //make the window visible
        mainStage.show();
    }

    //driver
    public static void main(String[] args){
        try{
            launch(args);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
