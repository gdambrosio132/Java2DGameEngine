public class TestGame extends Game {

    Sprite basketball, baseball, soccerball;

    public void initialize(){
        setTitle("Test Game");
        setWindowSize(800, 600);
        System.out.println("Hello, World!");

        //basketball sprite
        basketball = new Sprite();
        basketball.setPosition(10, 10);
        basketball.setTexture(Texture.load("images/basketball.png"));
        basketball.setSize(100, 100);

        /*
        //baseball sprite
        baseball = new Sprite();
        baseball.setPosition(350, 450);
        baseball.setTexture(Texture.load("images/baseball.png"));
        baseball.setSize(100, 100);

        //soccerball sprite
        soccerball = new Sprite();
        soccerball.setPosition(700, 10);
        soccerball.setTexture(Texture.load("images/soccerball.png"));
        soccerball.setSize(80, 80);

        */
        group.add(basketball);
        //group.add(baseball);
        //group.add(soccerball);
    }

    public void update(){
        if(input.isKeyPressed("RIGHT"))
            basketball.position.addValues(2,0);

        if(input.isKeyPressed("LEFT"))
            basketball.position.addValues(-2,0);

        if(input.isKeyPressed("UP"))
            basketball.position.addValues(0,-2);

        if(input.isKeyPressed("DOWN"))
            basketball.position.addValues(0,2);

        //if(input.isKeyJustPressed("Z"))
          //  basketball.position.setValues(0,0);

        if(input.isKeyPressed("SHIFT") && input.isKeyPressed("DIGIT4"))
            basketball.position.setValues(0,0);
    }
}
