package components;
/**
 * The type counter.
 *
 * @author Shir sabo
 */
public class Counter {
    private int points;
    /**
     * Constructor.
     * @param val int
     */
    public Counter(int val) {
        this.points = val;
    }
    /**
     * add number to current count.
     * @param number int
     */
    public void increase(int number) {
        this.points += number;
    }
    /**
     * substract number to current count.
     * @param number int
     */
   public void decrease(int number) {
       this.points = this.points - number;
   }
   /**
    * A getter to value.
    * @return points
    */
    public int getValue() {
    return this.points;
   }
}
