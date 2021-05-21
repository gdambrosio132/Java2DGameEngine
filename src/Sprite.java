import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Sprite extends Entity {

    //Sprite location in the game world
    public Vector position;

    //angle is measured in degrees
    public double angle;

    // reflect along x direction
    public boolean mirrored;

    // reflect along y direction
    public boolean flipped;

    // amount of opacity used when rendering this sprite
    public double opacity;

    //Shape used for collision
    public Rectangle boundary;

    //Image displayed when rendering this sprite
    public Texture texture;

    //Width of sprite
    public double width;

    //Height of Sprite
    public double height;

    //Determines if the sprite will be visible
    public boolean visible;

    //Add Physics
    public Physics physics;

    public Animation animation;

    public ArrayList<Action> actionList;

    public Object data;

    public Sprite(){
        position = new Vector();
        angle = 0;
        mirrored = false;
        flipped = false;
        opacity = 1;
        texture = new Texture();
        boundary = new Rectangle();
        visible = true;
        physics = null;
        animation = null;
        actionList = new ArrayList<Action>();
    }

    public void setPosition(double x, double y){
        position.setValues(x, y);
        boundary.setPosition(x, y);
    }

    public void moveBy(double dx, double dy){
        position.addValues(dx, dy);
    }

    public void setAngle(double a){
        angle = a;
    }

    public void rotateBy(double da){
        angle += da;
    }

    public void alignToSprite(Sprite other){
        this.setPosition(other.position.x, other.position.y);
        this.setAngle(other.angle);
    }

    //angle a is in degrees
    public void moveAtAngle(double dist, double a){
        double A = Math.toRadians(a);
        double dx = dist * Math.cos(A);
        double dy = dist * Math.sin(A);
        moveBy(dx, dy);
    }

    public void moveForward(double dist){
        moveAtAngle(dist, angle);
    }



    public void setTexture(Texture tex){
        texture = tex;
        width = texture.region.getWidth();
        height = texture.region.getHeight();
        boundary.setSize(width, height);
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
        boundary.setSize(width, height);
    }

    public void setSize(double width, double height){
        this.width = width;
        this.height = height;
        boundary.setSize(width, height);
    }

    public void setPhysics(Physics p){
        physics = p;
    }

    public void setAnimation(Animation anim) {
        animation = anim;
        width = anim.getCurrentTexture().region.getWidth();
        height = anim.getCurrentTexture().region.getHeight();
        boundary.setSize(width, height);
    }

    public void addAction(Action action){
        actionList.add(action);
    }

    public Rectangle getBoundary(){
        //boundary.setPosition(position.x, position.y);
        boundary.setValues(position.x - width/2, position.y - height/2, width, height);
        return boundary;
    }

    public boolean overlaps(Sprite other){
        return this.getBoundary().overlaps(other.getBoundary());
    }

    public void preventOverlap(Sprite other){
        if (this.overlaps(other)){
            Vector mtv = this.getBoundary().getMinimumTranslationVector(other.getBoundary());
            this.position.addVector(mtv);
        }
    }

    public void boundToScreen(int screenWidth, int screenHeight){
        if (position.x - width/2 < 0){
            position.x = width/2;
        }
        if (position.y - height/2 < 0){
            position.y = height/2;
        }
        if (position.x + width/2 > screenWidth){
            position.x = screenWidth - width/2;
        }
        if (position.y + height/2 > screenHeight){
            position.y = screenHeight - height/2;
        }
    }

    public void wrapToScreen(int screenWidth, int screenHeight){
        if (position.x + width/2 < 0){
            position.x = screenWidth + width / 2;
        }
        if (position.x - width/2 > screenWidth){
            position.x = -width/2;
        }
        if (position.y + height/2 < 0){
            position.y = screenHeight + height/2;
        }
        if (position.y - height/2 > screenHeight){
            position.y = -height/2;
        }
    }

    public void update(double dt){
        if (physics != null){
            physics.position.setValues(this.position.x, this.position.y);
            physics.update(dt);
            this.position.setValues(physics.position.x, physics.position.y);
        }

        if (animation != null){
            animation.update(dt);
            texture = animation.getCurrentTexture();
        }

        ArrayList<Action> actionListCopy = new ArrayList<Action>(actionList);
        for (Action a : actionListCopy){
            boolean finished = a.apply(this, dt);
            if (finished){
                actionList.remove(a);
            }
        }
    }

    public void draw(GraphicsContext graphicsContext){
        // TODO: implement

        //if sprite is not visible, exit out of method
        if (!this.visible)
            return;

        double A = Math.toRadians(angle);
        double cosA = Math.cos(A);
        double sinA = Math.sin(A);

        double scaleX = 1;
        if (mirrored){
            scaleX = -1;
        }

        double scaleY = 1;
        if (flipped){
            scaleY = -1;
        }



        //graphicsContext.setTransform(1, 0, 0, 1, position.x, position.y); old code, new one rotates sprite
        graphicsContext.setTransform(scaleX * cosA, scaleX * sinA, scaleY * -sinA, scaleY * cosA, position.x, position.y);

        // set opacity level
        graphicsContext.setGlobalAlpha(opacity);
        //define the source rectangle region of image & destination of region of canvas
        //graphicsContext.drawImage(texture.image, texture.region.getLeft(),
                //texture.region.getTop(), texture.region.getWidth(),
                //texture.region.getHeight(), 0, 0, this.width, this.height);

        graphicsContext.drawImage(texture.image, texture.region.getLeft(),
                texture.region.getTop(), texture.region.getWidth(),
                texture.region.getHeight(), -this.width/2, -this.height/2, this.width, this.height);
    }
}
