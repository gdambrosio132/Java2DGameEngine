import javafx.scene.paint.Color;

public class Asteroids extends Game {
    public Sprite spaceship;
    public Group rockGroup;
    public Texture rockTex;
    public Group laserGroup;
    public Texture laserTex;
    public Animation explosionAnim;
    public Sprite shields;
    public Sprite winMessage;
    public Sprite loseMessage;
    public int time = 60;
    public int timeRef = 60;
    public Label timer;
    public Label scoreLabel;
    public int score = 0;
    public void initialize(){
        setTitle("Asteroids");
        setWindowSize(800,600);

        Sprite background = new Sprite();
        Texture bgTex = new Texture("images/space.png");
        background.setTexture(bgTex);
        background.setPosition(400, 300);
        group.add(background);

        spaceship = new Sprite();
        Texture ssTex = new Texture("images/spaceship.png");
        spaceship.setTexture(ssTex);
        spaceship.setPosition(400, 300);
        spaceship.setPhysics(new Physics(200, 200, 20));
        spaceship.addAction(Action.wrapToScreen(800, 600));
        group.add(spaceship);

        rockGroup = new Group();
        group.add(rockGroup);
        int rockCount = 8;
        rockTex = new Texture("images/asteroid.png");
        for (int i = 0; i < rockCount; i++){
            Sprite rock = new Sprite();
            rock.setTexture(rockTex);
            rock.setSize(100, 100);

            double angle = 2 * Math.PI * Math.random();
            double x = spaceship.position.x + 250 * Math.cos(angle);
            double y = spaceship.position.y + 250 * Math.sin(angle);
            rock.setPosition(x,y);
            rock.setPhysics(new Physics(0, 100, 0));
            double moveSpeed = 30 * Math.random() + 90;
            rock.physics.setSpeed(moveSpeed);
            rock.physics.setMotionAngle(Math.toDegrees(angle));
            double rotateSpeed = 2 * Math.random() + 1;
            rock.addAction(Action.forever(Action.rotateBy(360, rotateSpeed)));
            rock.addAction(Action.wrapToScreen(800, 600));
            rockGroup.add(rock);
        }

        laserGroup = new Group();
        group.add(laserGroup);
        laserTex = new Texture("images/laser.png");

        explosionAnim = new Animation("images/explosion.png", 6, 6, 0.02, false);

        shields = new Sprite();
        Texture shieldTex = new Texture("images/shields.png");
        shields.setTexture(shieldTex);
        shields.setSize(120, 120);
        group.add(shields);

        //Win Message Sprite
        winMessage = new Sprite();
        winMessage.setPosition(400, 300);
        winMessage.setTexture(new Texture("images/message-win.png"));
        winMessage.visible = false;
        group.add(winMessage);

        //Lose Message Sprite
        loseMessage = new Sprite();
        loseMessage.setPosition(400, 300);
        loseMessage.setTexture(new Texture("images/message-lose.png"));
        loseMessage.visible = false;
        group.add(loseMessage);

        //Timer Label
        timer = new Label("Comic Sans MS Bold", 48);
        timer.setText("Time: " + time);
        timer.fontColor = Color.RED;
        timer.setBorder(2, Color.DARKRED);
        timer.setPosition(780, 570);
        timer.alignment = "RIGHT";
        group.add(timer);

        //Score Label
        scoreLabel = new Label("Comic Sans MS Bold", 48);
        scoreLabel.setText("Score: " + score);
        scoreLabel.fontColor = Color.BLUE;
        scoreLabel.setBorder(2, Color.DARKBLUE);
        scoreLabel.setPosition(780, 70);
        scoreLabel.alignment = "RIGHT";
        group.add(scoreLabel);

    }


    public void update(){

        if (winMessage.visible || loseMessage.visible){
            return;
        }

        //time logic
        timeRef -= 1.0 / 60.0;
        if (timeRef == 0) {
            timer.setText("Time: " + time--);
            timeRef = 60;
        }
        if (time == -1){
            loseMessage.visible = true;
            spaceship.physics.setSpeed(0);
        }


        shields.alignToSprite(spaceship);

        if (input.isKeyPressed("LEFT")){
            spaceship.rotateBy(-3);
        }
        if (input.isKeyPressed("RIGHT")){
            spaceship.rotateBy(3);
        }
        if (input.isKeyPressed("UP")){
            spaceship.physics.accelerateAtAngle(spaceship.angle);
        }

        if (input.isKeyJustPressed("SPACE") && laserGroup.size() < 4){
            Sprite laser = new Sprite();
            laser.setTexture(laserTex);
            laser.alignToSprite(spaceship);
            laser.setPhysics(new Physics(0, 400, 0));
            laser.physics.setSpeed(400);
            laser.physics.setMotionAngle(spaceship.angle);
            laser.addAction(Action.wrapToScreen(800, 600));
            laserGroup.add(laser);
            laser.addAction(Action.delayFadeRemove(1, 0.5));
        }


        //This deals with destroying a space rock with a laser
        for (Entity rockE : rockGroup.getList()){
            Sprite rock = (Sprite) rockE;
            if (shields.overlaps(rock) && shields.opacity > 0){
                rock.removeSelf();
                shields.opacity -= 0.25;
                Sprite explosion = new Sprite();
                explosion.setAnimation(explosionAnim.clone());
                explosion.alignToSprite(rock);
                explosion.addAction(Action.animateThenRemove());
                group.add(explosion);
                scoreLabel.setText("Score: " + (score += 15));
            }

            //game over condition
            if (rock.overlaps(spaceship)){
                spaceship.removeSelf();
                loseMessage.visible = true;
            }

            for (Entity laserE : laserGroup.getList()){
                Sprite laser = (Sprite) laserE;
                if (rock.overlaps(laser)){
                    rock.removeSelf();
                    laser.removeSelf();
                    Sprite explosion = new Sprite();
                    explosion.setAnimation(explosionAnim.clone());
                    explosion.alignToSprite(rock);
                    explosion.addAction(Action.animateThenRemove());
                    group.add(explosion);
                    //if the rock is large (100x100),
                    // we split into two smaller rocks
                    if (rock.width == 100){
                        scoreLabel.setText("Score: " + (score += 25));
                        for (int i = 0; i < 2; i++){
                            Sprite rockSmall = new Sprite();
                            rockSmall.setTexture(rockTex);
                            rockSmall.setSize(50,50);
                            rockSmall.alignToSprite(rock);
                            rockSmall.addAction(Action.wrapToScreen(800, 600));
                            rockSmall.setPhysics(new Physics(0, 300, 0));
                            rockSmall.physics.setSpeed(2 * rock.physics.getSpeed());
                            rockSmall.physics.setMotionAngle(rock.physics.getMotionAngle() + 90 * Math.random() - 45);
                            rockGroup.add(rockSmall);
                        }
                    } else {
                        scoreLabel.setText("Score: " + (score += 10));
                    }
                }
            }
        }

        if (rockGroup.size() == 0){
            winMessage.visible = true;
            scoreLabel.setText("Score: " + (score += time));
        }
    }
}
