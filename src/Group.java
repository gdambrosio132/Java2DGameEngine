import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Group extends Entity {

    //The collection underlying this list
    private ArrayList<Entity> list;

    //Initialize this object
    public Group(){
        list = new ArrayList<Entity>();
    }

    /*
     * Add an {@Link Entitiy} to this collection
     * @param e The Entity being added to this collection
     */
    public void add(Entity e){
        this.list.add(e);
        e.parent = this;
    }

    /*
     * Remove an {@Link Entitiy} to this collection
     * @param e The Entity being removed to this collection
     */
    public void remove(Entity e){
        this.list.remove(e);
        e.parent = null;
    }


    //if we iterate in another class, we don't modify our list, it is a copy
    public ArrayList<Entity> getList(){
        return new ArrayList<Entity>(list);
    }

    public ArrayList<Sprite> getSpriteList(){
        ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
        for (Entity e : list){
            spriteList.add((Sprite) e);
        }
        return spriteList;
    }

    public int size(){
        return list.size();
    }

    //Render all entity objects in this collection to a canvas
    public void draw(GraphicsContext graphicsContext){
        for(Entity e : this.list)
            e.draw(graphicsContext);
    }

    //Update all entity objects within this collection
    public void update(double dt){
        for(Entity e : this.list)
            e.update(dt);
    }


}
