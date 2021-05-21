import javafx.scene.paint.Color;

public class SubSurvival extends Game {

    //Properties of SubSurvival Game
    public Sprite water;
    public Sprite core;
    public Sprite player;
    public double enemyTimer;
    public double enemySpeed;
    public Group enemyGroup;
    public Group bulletGroup;
    public Texture enemyTexture;
    public Texture bulletTexture;
    public Animation explosionAnimation;
    public Label coreHealthLabel;
    public int coreHealth;
    public Label scoreLabel;
    public int score;
    public Sprite gameOver;

    public void initialize(){

        //Set Title and Window Size
        setTitle("Sub Survival");
        setWindowSize(640, 480);

        //Init. Water
        water = new Sprite();
        water.setTexture(new Texture("images/water_background.png"));
        water.setPosition(320, 240);
        group.add(water);

        //Init. Core
        core = new Sprite();
        core.setTexture(new Texture("images/core.png"));
        core.setPosition(40, 240);
        coreHealth = 100;
        group.add(core);

        //Init. Player
        player = new Sprite();
        player.setTexture(new Texture("images/player.png"));
        player.setPosition(140, 240);
        player.setPhysics(new Physics(400,200,400));
        player.addAction(Action.boundToScreen(640, 480));
        group.add(player);

        //Init. Explosion Animation
        explosionAnimation = new Animation("images/explosion_water.png", 5, 8, 0.02, false);

        //Init. EnemyGroup
        enemyGroup = new Group();
        enemyTexture = new Texture("images/enemy.png");
        enemyTimer = 2.0;
        enemySpeed = 200;
        group.add(enemyGroup);

        //Init. BulletGroup
        bulletGroup = new Group();
        bulletTexture = new Texture("images/bullet.png");
        group.add(bulletGroup);

        //Init. Core Health Label
        coreHealthLabel = new Label("Arial Bold", 24);
        coreHealthLabel.fontColor = Color.WHITE;
        coreHealthLabel.drawBorder = true;
        coreHealthLabel.setPosition(450, 50);
        coreHealthLabel.setText("Score: " + score);
        group.add(coreHealthLabel);

        //Init. Score Label
        scoreLabel = new Label("Arial Bold", 24);
        scoreLabel.fontColor = Color.WHITE;
        scoreLabel.drawBorder = true;
        scoreLabel.setPosition(470, 470);
        scoreLabel.setText("Core HP: " + coreHealth);
        group.add(scoreLabel);

        //Init. Game Over Label
        gameOver = new Sprite();
        gameOver.setTexture(new Texture("images/GameOver.png"));
        gameOver.setPosition(320, 240);
        gameOver.visible = false;
        group.add(gameOver);
    }

    public void update(){
        //Game over clause
        if (gameOver.visible){
            return;
        }

        //Game over toggle
        if (coreHealth == 0){
            core.removeSelf();
            gameOver.visible = true;
        }

        //Key inputs for movement and firing
        if (input.isKeyPressed("LEFT")){
            player.physics.accelerateAtAngle(180);
        }
        if (input.isKeyPressed("RIGHT")){
            player.physics.accelerateAtAngle(0);
        }
        if (input.isKeyPressed("UP")){
            player.physics.accelerateAtAngle(270);
        }
        if (input.isKeyPressed("DOWN")){
            player.physics.accelerateAtAngle(90);
        }
        if (input.isKeyJustPressed("SPACE") && bulletGroup.size() < 1){
            //fire a missile
            Sprite bullet = new Sprite();
            bullet.setTexture(bulletTexture);
            bullet.alignToSprite(player);
            bullet.setPhysics(new Physics(0, 400, 0));
            bullet.physics.setSpeed(400);
            bulletGroup.add(bullet);
        }

        //Update label code
        scoreLabel.setText("Score: " + score);
        coreHealthLabel.setText("Core HP: " + coreHealth);


        //enemy timer code for spawning at a random y position and initialization
        enemyTimer -= 1.0 / 60.0;
        if (enemyTimer < 0){
            Sprite enemy = new Sprite();
            enemy.setTexture(enemyTexture);
            double enemyY = Math.random() * 300 + 100;
            enemy.setPosition(700, enemyY);
            enemy.setPhysics(new Physics(0, 600, 0));
            enemy.physics.setSpeed(enemySpeed);
            enemy.physics.setMotionAngle(180);
            enemyGroup.add(enemy);
            enemyTimer = 2.0;
            enemySpeed += 10;
        }

        //Bullet actions/properties
        for (Sprite bullet : bulletGroup.getSpriteList()){
            if (bullet.position.x > 640){
                bulletGroup.remove(bullet);
            }

            for (Sprite enemy : enemyGroup.getSpriteList()){
                if (bullet.overlaps(enemy)){
                    enemy.removeSelf();
                    Sprite explosion = new Sprite();
                    explosion.setAnimation(explosionAnimation.clone());
                    explosion.alignToSprite(enemy);
                    explosion.addAction(Action.animateThenRemove());
                    group.add(explosion);
                    score += 100;
                }
            }
        }

        //Enemy action/properties
        for (Sprite enemy : enemyGroup.getSpriteList()){

            if (enemy.overlaps(core)){
                enemy.removeSelf();
                Sprite explosion = new Sprite();
                explosion.setAnimation(explosionAnimation.clone());
                explosion.alignToSprite(enemy);
                explosion.addAction(Action.animateThenRemove());
                group.add(explosion);
                coreHealth -= 25;
            }

            if (enemy.overlaps(player)){
                player.removeSelf();
                enemy.removeSelf();
                Sprite explosion = new Sprite();
                explosion.setAnimation(explosionAnimation.clone());
                explosion.alignToSprite(enemy);
                explosion.addAction(Action.animateThenRemove());
                group.add(explosion);
                gameOver.visible = true;
            }
        }
    }
}
