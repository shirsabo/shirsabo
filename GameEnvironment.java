import java.util.ArrayList;
import java.util.List;
/**
 * The type Game environment.
 *
 * @author Shir sabo
 */
public class GameEnvironment {
    private List<Collidable> collidables;
    /**
     * Instantiates a new Game environment.
     */
    public  GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }
    /**
     * Add collidable.
     *
     * @param c the c
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }
    /**
     // Assume an object moving from line.start() to line.end().
     // If this object will not collide with any of the collidables
     // in this collection, return null. Else, return the information
     // about the closest collision that is going to occur.
     * @param trajectory the trajectory
     * @return the closest collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point starting = trajectory.start();
        Point ending = trajectory.end();
        List<Point> collidables1 = new ArrayList<Point>();
        List<Double> dis = new ArrayList<Double>();
        List<Collidable> collidablesrec = new ArrayList<Collidable>();
        Rectangle r;
        Point p;
        int j = 0;
        //goes through all the collidables possible
        for (int i = 0; i < collidables.size(); i++) {
            r = collidables.get(i).getCollisionRectangle();
            p = trajectory.closestIntersectionToStartOfLine(r);
            if (p != null) {
                collidables1.add(p);
                dis.add((collidables1.get(j)).distance(trajectory.start()));
                collidablesrec.add(collidables.get(i));
                j = j + 1;
            }
        }
        //if there are not any collidables
        if (collidablesrec.size() == 0) {
            return  null;
        }
        //finds the closest rectangle
        int iMin = 0;
        for (int i = 1; i < collidables1.size(); i++) {
         if (dis.get(iMin) > dis.get(i)) {
             iMin = iMin + 1;
         }
        }
        CollisionInfo info = new CollisionInfo(collidables1.get(iMin), collidablesrec.get(iMin));
        return info;
    }
}
