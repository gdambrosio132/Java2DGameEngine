import javafx.scene.paint.Color;

public class PlaneDodger extends Game {

    public Sprite sky;
    public Sprite ground;
    public Sprite player;
    public Group enemyGroup;
    public Texture enemyTex;
    public double enemyTimer;
    public double enemySpeed;
    public Group starGroup;
    public Texture starTex;
    public double starTimer;
    public int score;
    public Label scoreLabel;
    public Sprite gameOver;
    public Animation explosionAnimation;
    public Animation sparkleAnimation;


    public void initialize(){
        setTitle("Plane Dodger");
        setWindowSize(600, 800);

        sky = new Sprite();
        Texture skyTex = new Texture("images/sky.png");
        sky.setTexture(skyTex);
        sky.setPosition(300, 400);
        sky.setPhysics(new Physics(0, 150, 0));
        sky.physics.setSpeed(50);
        sky.physics.setMotionAngle(180);
        group.add(sky);

        ground = new Sprite();
        Texture groundTex = new Texture("images/ground.png");
        ground.setTexture(groundTex);
        ground.setPosition(300, 760);
        ground.setPhysics(new Physics(0, 150, 0));
        ground.physics.setSpeed(150);
        ground.physics.setMotionAngle(180);
        group.add(ground);

        player = new Sprite();
        Texture playerTex = new Texture("images/plane-green.png");
        player.setTexture(playerTex);
        player.setPosition(80, 80);
        player.setPhysics(new Physics(200, 600, 0));
        player.addAction(Action.boundToScreen(600, 730));
        group.add(player);

        enemyGroup = new Group();
        enemyTex = new Texture("images/plane-red.png");
        enemyTimer = 2.0;
        enemySpeed = 200;
        group.add(enemyGroup);

        starGroup = new Group();
        starTex = new Texture("images/star.png");
        starTimer = 2.0;
        group.add(starGroup);

        scoreLabel = new Label("Arial Bold", 64);
        scoreLabel.fontColor = Color.WHITE;
        scoreLabel.drawBorder = true;
        scoreLabel.setPosition(300, 80);
        scoreLabel.setText("Score: " + score);
        group.add(scoreLabel);

        gameOver = new Sprite();
        gameOver.setTexture(new Texture("images/GameOver.png"));
        gameOver.setPosition(300, 400);
        gameOver.visible = false;
        group.add(gameOver);

        explosionAnimation = new Animation("images/explosion.png", 6, 6, 0.02, false);

        sparkleAnimation = new Animation("images/sparkle.png", 8, 8, 0.02, false);

    }

    public void update(){
        if (gameOver.visible){
            sky.physics.setSpeed(0);
            ground.physics.setSpeed(0);
            return;
        }

        if (sky.position.x < 0){
            sky.moveBy(600, 0);
        }

        if (ground.position.x < 0){
            ground.moveBy(600, 0);
        }

        player.physics.accelerateAtAngle(90);

        if (input.isKeyJustPressed("SPACE")){
            player.physics.setSpeed(200);
            player.physics.setMotionAngle(270);
        }

        enemyTimer -= 1.0 / 60.0;
        if (enemyTimer < 0){
            Sprite enemy = new Sprite();
            enemy.setTexture(enemyTex);
            double enemyY = Math.random() * 600 + 100;
            enemy.setPosition(700, enemyY);
            enemy.setPhysics(new Physics(0, 600, 0));
            enemy.physics.setSpeed(enemySpeed);
            enemy.physics.setMotionAngle(180);
            enemyGroup.add(enemy);
            enemyTimer = 2.0;
            enemySpeed += 10;

        }

        if (player.overlaps(ground)){
            gameOver.visible = true;
        }

        for (Sprite enemy : enemyGroup.getSpriteList()){
            if (player.overlaps(enemy)){
                //play an explosion and remove the planes from group
                player.removeSelf();
                enemy.removeSelf();
                Sprite explosion = new Sprite();
                explosion.setAnimation(explosionAnimation.clone());
                explosion.alignToSprite(player);
                explosion.addAction(Action.animateThenRemove());
                group.add(explosion);
                gameOver.visible = true;
            }

            if (enemy.position.x < -40){
                enemyGroup.remove(enemy);
                score += 1;
                scoreLabel.setText("Score: " + score);
            }
        }


        starTimer -= 1.0 / 60.0;
        if (starTimer < 0){
            Sprite star = new Sprite();
            star.setTexture(starTex);
            double starY = Math.random() * 600 + 100;
            star.setPosition(700, starY);
            star.setPhysics(new Physics(0, 200, 0));
            star.physics.setSpeed(150);
            star.physics.setMotionAngle(180);
            star.setAngle(-15);
            star.addAction(
                    Action.forever(
                            Action.sequence(
                                    Action.rotateBy(30, 0.5),
                                    Action.rotateBy(-30, 0.5)
                            )
                    )
            );
            starGroup.add(star);
            starTimer = 2.0;
        }

        for (Sprite star : starGroup.getSpriteList()){
            if (player.overlaps(star)){
                Sprite sparkle = new Sprite();
                sparkle.setAnimation(sparkleAnimation.clone());
                sparkle.alignToSprite(star);
                sparkle.addAction(Action.animateThenRemove());
                group.add(sparkle);
                score += 5;
                scoreLabel.setText("Score: " + score);
                starGroup.remove(star);
            }

            if (star.position.x < -40){
                starGroup.remove(star);
            }
        }
    }
}
