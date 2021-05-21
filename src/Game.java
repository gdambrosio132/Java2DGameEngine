import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/*
 * This main class is to be extended for our game projects.
 * It creates the window {@link Group} objects,
 * and manages the life cycle of the game (initialization and game loop)
 */

public abstract class Game extends Application implements Screen {
    public Canvas canvas;
    public GraphicsContext graphicsContext;
    public Group group;
    public Stage stage;
    public Input input;

    public void start(Stage mainStage){
        mainStage.setTitle("Game");
        mainStage.setResizable(false);

        Pane root = new Pane();
        Scene mainScene = new Scene(root);
        mainStage.setScene(mainScene);
        mainStage.sizeToScene();

        canvas = new Canvas(512, 512);
        graphicsContext = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        group = new Group();

        input = new Input(mainScene);

        //set animation timer
        //to clarify, class containing update method
        Game self = this;
        AnimationTimer gameloop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //used to update the game state
                //we want to update user input

                //update user input
                input.update();

                //update game state
                self.update();
                self.group.update(1/60.0);

                //clear the canvas
                self.graphicsContext.setFill(Color.GRAY);
                self.graphicsContext.fillRect(0,0, self.canvas.getWidth(), self.canvas.getHeight());

                //render game objects
                self.group.draw(self.graphicsContext);
            }
        };

        mainStage.show();

        //references are required for the set/mutator methods
        stage = mainStage;
        initialize();
        gameloop.start();
    }

    public void setTitle(String title){
        stage.setTitle(title);
    }

    public void setWindowSize(int width, int height){
        //stage.setWidth(width);
        //stage.setHeight(height);
        canvas.setWidth(width);
        canvas.setHeight(height);
        stage.sizeToScene();
    }
}
