import javafx.scene.paint.Color;

import java.util.Random;

public class StarfishCollector extends Game {
    Sprite water;
    Group starfishGroup;
    Sprite turtle;
    //Sprite shark;
    //Sprite winMessage;
    Sprite loseMessage;
    Group rockGroup;
    //Sprite fish;
    //Sprite fish2;
    Group school; //because a group of fish is called a school :)
    Label starfishLabel;
    Label winLabel;
    Label loseLabel;
    Group sharkGroup;
    public void initialize(){
        setTitle("Starfish Collector");
        setWindowSize(800, 600);

        water = new Sprite();
        water.setTexture(new Texture("images/water.png"));
        water.setPosition(400, 300);
        group.add(water);

        /*
        //Add shark sprite
        shark = new Sprite();
        shark.setPosition(400, 300);
        shark.setTexture(new Texture("images/shark.png"));
        group.add(shark);
        */

        //create shark group
        sharkGroup = new Group();
        for (int i = 0; i < 3; i++){ //create 3 sharks and give them half the speed of the turtle along with wrap around and new angle
            Sprite shark = new Sprite();
            shark.setPosition(400, 300);
            shark.setTexture(new Texture("images/shark.png"));
            shark.setSize(80, 70);
            shark.setPhysics(new Physics(0,200 / 2,0));
            shark.physics.setSpeed(200/2);
            double randomAngle = Math.random() * 240 + 120;
            if (randomAngle > 90 && randomAngle < 270)
                shark.flipped = true;
            shark.physics.setMotionAngle(randomAngle);
            shark.setAngle(randomAngle);
            shark.addAction(Action.wrapToScreen(800, 600));
            sharkGroup.add(shark);
        }
        group.add(sharkGroup); //add the sharks to the main group

        //Add rock sprite
        rockGroup = new Group();
        Texture rockTexture = new Texture("images/rock.png");
        int rockCount = 3;
        for (int i = 0; i < rockCount; i++){
            Sprite rock = new Sprite();
            double x = Math.random() * 600 + 100;
            double y = Math.random() * 400 + 100;
            rock.setPosition(x, y);
            rock.setTexture(rockTexture);
            rockGroup.add(rock);
        }
        group.add(rockGroup);

        //Generate Starfish
        starfishGroup = new Group();
        Texture starfishTexture = new Texture("images/starfish.png");
        int starfishCount = 20;
        for (int i = 0; i < starfishCount; i++){
            Sprite starfish = new Sprite();
            double x = Math.random() * 600 + 100;
            double y = Math.random() * 400 + 100;
            starfish.setPosition(x, y);
            starfish.setTexture(starfishTexture);
            starfish.setSize( (Math.random() * 60) + 40,  (Math.random() * 60) + 40);
            starfish.setAngle(Math.random() * 360);
            boolean rockOverlap = false;
            do {
                rockOverlap = false;
                x = Math.random() * 600 + 100;
                y = Math.random() * 400 + 100;
                starfish.setPosition(x, y);
                for (Entity entity : rockGroup.getList()){
                    Sprite rock = (Sprite) entity;
                    if (rock.overlaps(starfish)){
                        rockOverlap = true;
                    }
                }
            } while ( rockOverlap );
            //for (int z = 0; z < 20; z++)
                //starfish.addAction(Action.rotateBy(360, 10));
            //starfish.addAction(Action.moveBy(50, 5, 0));
            //starfish.addAction(Action.forever(Action.rotateBy(360, 1)));
            //starfish.addAction(Action.sequence(Action.moveBy(50, 0, 2), Action.moveBy(-50, 0, 2)));
            /*starfish.addAction(
                    Action.repeat(
                            Action.sequence(
                                    Action.moveBy(50, 0, 1),
                                    Action.moveBy(-50, 0, 1)
                            ), 3
                    )
            );*/

            starfish.addAction(
                    Action.forever(
                            Action.rotateBy(360, Math.random() + 2)
                    )
            );
            starfish.data = false;
            starfishGroup.add(starfish);
            //loop until it doesn't overlap
            /*while (starfish.overlaps(shark)){
                x = Math.random() * 600 + 100;
                starfish.setPosition(x, y);
            }*/
        }
        group.add(starfishGroup);

        //turtle sprite
        turtle = new Sprite();
        turtle.setPosition(90, 90);
        turtle.setTexture(new Texture("images/turtle.png"));

        //new code for physics of turtle
        turtle.setPhysics(new Physics(400, 200, 400));
        turtle.addAction(Action.boundToScreen(800, 600));
        group.add(turtle);

        /*
        Sprite water2 = new Sprite();
        water2.setTexture(new Texture("images/water.png"));
        water2.setPosition(400, 300);
        water2.opacity = 0.1;
        group.add(water2);
        */

        /*
        //Fish Sprite
        fish = new Sprite();
        fish.setPosition(100, 400);
        Animation fishAnim = new Animation("images/fish.png", 8, 1, 0.1, true);
        fish.setAnimation(fishAnim);
        group.add(fish);

        //fish #2
        fish2 = new Sprite();
        fish2.setPosition(700, 400);
        fish2.setAngle(180);
        Animation fish2Anim = new Animation("images/fish.png", 8, 1, 0.1, true);
        fish2.setAnimation(fish2Anim);
        group.add(fish2);*/
        school = new Group();
        for (int i = 1; i <= 6; i++){
            Sprite fish = new Sprite(); //new fish local sprite
            double x = Math.random() * 600 + 100; //get random x & y values
            double y = Math.random() * 400 + 100;
            fish.setPosition(x, y); //position set for 1 local fish
            Animation fishAnimation = new Animation("images/fish.png", 8, 1, 0.1, true);
            fish.setAnimation(fishAnimation); //animation set to the .png
            double randomSpeed = Math.random() * (100 - 50 + 1) + 50;
            fish.setPhysics(new Physics(5, randomSpeed, 0)); //set physics
            Random random = new Random();
            double z = random.nextInt(360);
            fish.setAngle(z);
            fish.physics.setMotionAngle(z); //set both angle for appearance and physics
            school.add(fish); //add local singular fish to the school
        }
        group.add(school);

        int newFishCount = 6;
        for (int s = 0; s < newFishCount; s++){
            Sprite newFish = new Sprite();
            //newFish.setPosition(100, 400);
            newFish.setPosition(Math.random() * 800, Math.random() * 600);
            Animation newFishAnim = new Animation("images/fish.png", 8, 1, 0.1, true);
            //newFish.setAnimation(newFishAnim);
            newFish.setAnimation(newFishAnim.clone());
            newFish.setPhysics(new Physics(0, 200, 0));
            double newFishSpeed = Math.random() * 100 + 100;
            //newFish.physics.setSpeed(200);
            newFish.physics.setSpeed(newFishSpeed);
            double newFishAngle = Math.random() * 360;
            //newFish.physics.setMotionAngle(45);
            newFish.physics.setMotionAngle(newFishAngle);
            //newFish.setAngle(45);
            newFish.setAngle(newFishAngle);
            newFish.addAction(Action.wrapToScreen(800, 600));
            group.add(newFish);
        }

        /*
        //win-message sprite
        winMessage = new Sprite();
        winMessage.setPosition(400, 300);
        winMessage.setTexture(new Texture("images/YouWin.png"));
        winMessage.visible = false;
        group.add(winMessage);*/


        //old you win message sprite
        winLabel = new Label("Comic Sans MS Bold", 80);
        winLabel.setText("You Win!");
        winLabel.fontColor = Color.LIGHTGREEN;
        winLabel.setBorder(2, Color.DARKGREEN);
        winLabel.setPosition(400,300);
        winLabel.alignment = "CENTER";
        winLabel.visible = false;
        group.add(winLabel);
        /*
        //old lose message sprite
        loseMessage = new Sprite();
        loseMessage.setPosition(400, 300);
        loseMessage.setTexture(new Texture("images/GameOver.png"));
        loseMessage.visible = false;
        group.add(loseMessage);
        */

        //loselabel utilizing the label class
        loseLabel = new Label("Comic Sans MS Bold", 80);
        loseLabel.setText("Game Over");
        loseLabel.fontColor = Color.RED;
        loseLabel.setBorder(2, Color.DARKRED);
        loseLabel.setPosition(400, 300);
        loseLabel.alignment = "CENTER";
        loseLabel.visible = false;
        group.add(loseLabel);

        starfishLabel = new Label("Comic Sans MS Bold", 48);
        String text = "Starfish Left: " + starfishGroup.size();
        starfishLabel.setText(text);
        starfishLabel.setPosition(780, 570);
        starfishLabel.alignment = "RIGHT";
        starfishLabel.fontColor = Color.RED;
        starfishLabel.setBorder(2, Color.BLACK);
        group.add(starfishLabel);
    }

    public void update(){
        /*if (winMessage.visible || loseMessage.visible) {
            return;
        }*/

        //Display the win and lose label
        if (winLabel.visible || loseLabel.visible){
            return;
        }

        //keyInput Entries
        if (input.isKeyPressed("RIGHT")){
            //turtle.position.addValues(2, 0);
            //turtle.moveBy(2,0);
            turtle.physics.accelerateAtAngle(0);
            //turtle.setAngle(0);
        }
        if (input.isKeyPressed("LEFT")){
            //turtle.position.addValues(-2, 0);
            //turtle.moveBy(-2,0);
            turtle.physics.accelerateAtAngle(180);
            //turtle.setAngle(180);
        }
        if (input.isKeyPressed("UP")){
            //turtle.position.addValues(0, -2);
            //turtle.moveBy(0,-2);
            turtle.physics.accelerateAtAngle(270);
            //turtle.setAngle(270);
        }
        if (input.isKeyPressed("DOWN")){
            //turtle.position.addValues(0, 2);
            //turtle.moveBy(0,2);
            turtle.physics.accelerateAtAngle(90);
            //turtle.setAngle(90);
        }

        //This updates the visual UI angle of the turtle every tick out of 60
        if (turtle.physics.getSpeed() > 0)
            turtle.setAngle(turtle.physics.getMotionAngle());


        for (Entity entity : school.getList()){
            Sprite fish = (Sprite) entity;
            fish.physics.accelerateAtAngle(fish.angle);

            //Take in account for fish moving past left edge of screen
            if (fish.position.x < -fish.width / 2){
                fish.position.x = 800 + fish.width/2;
            }

            //Take in account for fish moving past right edge of screen
            if (fish.position.x > 800 + fish.width / 2){
                fish.position.x = 0;
            }

            //Take in account for fish moving past top edge of screen
            if (fish.position.y < -fish.height / 2){
                fish.position.y = 600 + fish.height/2;
            }

            //Take in account for fish moving past bottom edge of screen
            if (fish.position.y > 600 + fish.height / 2){
                fish.position.y = 0;
            }
        }

        /*old rotate button
        if (input.isKeyPressed("R")){
            turtle.rotateBy(2);
        }*/

        //Starfish removal
        for (Entity entity : starfishGroup.getList()){
            Sprite starfish = (Sprite) entity;
            if (turtle.overlaps(starfish) && (boolean) starfish.data == false){
                //starfishGroup.remove(starfish);
                starfish.actionList.clear();
                starfish.addAction(Action.fadeOut(1));
                //indicate that this starfish has been collected
                starfish.data = true;
            }

            if (starfish.actionList.size() == 0){
                starfishGroup.remove(starfish);
            }

            String text = "Starfish Left: " + starfishGroup.size();
            starfishLabel.setText(text);
        }

        for (Entity entity : rockGroup.getList()){
            Sprite rock = (Sprite) entity;
            turtle.preventOverlap(rock);
        }

        //rotation for each starfish as they wiggle
        /*for (Entity entity : starfishGroup.getList()){
            Sprite starfish = (Sprite) entity;
            starfish.rotateBy((Math.random() * -4) + 2);
        }*/

        /* If you want the turtle to move the rocks
        for (Entity entity : rockGroup.getList()){
            Sprite rock = (Sprite) entity;
            rock.preventOverlap(turtle);
        }
        */

        //create shark moving looking at turtle
        /*Vector sharkVector = new Vector(turtle.position.x - shark.position.x, turtle.position.y - shark.position.y);
        shark.setAngle(sharkVector.getAngle());

        //Flip shark position in relations with turtle
        if (turtle.position.x < shark.position.x){
            shark.flipped = true;
        }

        if (turtle.position.x > shark.position.x){
            shark.flipped = false;
        }*/


        if (starfishGroup.size() == 0 && turtle.visible){
            //winMessage.visible = true;
            winLabel.visible = true;
        }

        for (Entity entity : sharkGroup.getList()){
            Sprite shark = (Sprite) entity;
            if (turtle.overlaps(shark)){
                loseLabel.visible = true;
                group.remove(turtle);
            }
        }
        /*
        if (turtle.overlaps(shark)){
            loseMessage.visible = true;
            group.remove(turtle);
        }*/

        //turtle.boundToScreen(800,600);
    }

}
