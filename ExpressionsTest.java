import java.util.Map;
import java.util.TreeMap;
/**
 * The ExpressionsTest class.
 * @author Shir sabo
 */
public class ExpressionsTest {
    /**
     * The entry point of application.
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args)  throws Exception {
        //1. Create the expression (2x) + (sin(4y)) + (e^x).
        Expression e = new Plus(new Plus(new Mult(2, "x"), new Sin(new Mult(4, "y"))), new Pow("e", "x"));
        ((Plus) e).setexp(e.toString());
        // 2.Print the expression
        System.out.println(((Plus) e).getexp());
        Map<String, Double> assignments = new TreeMap<String, Double>();
        assignments.put("x", 2.0);
        assignments.put("y", 0.25);
        assignments.put("e", Math.E);
        // 3.Print the value of the expression with (x=2,y=0.25,e=2.71).
        System.out.println(e.evaluate(assignments));
        // 4.Print the differentiated expression according to X
        System.out.println(e.differentiate("x").toString());
        // 5.Print the value of the differentiated expression according to x with the assignment above.
        try {
            System.out.println(e.differentiate("x").evaluate(assignments));
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
        // 6.Print the simplified differentiated expression.
        try {
            System.out.println(e.differentiate("x").simplify().toString());
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }
}