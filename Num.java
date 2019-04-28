import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * The type Num.
 * @author Shir sabo
 */
public class Num implements Expression {
    private double num;
    /**
     * Instantiates a new Num.
     *
     * @param numIn the num in
     */
    public Num(double numIn) {
       this.num = numIn;
    }
    /**
     // Evaluate the expression using the variable values provided
     // in the assignment, and return the result.
     * @param assignment the map.
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return this.num;
    }
    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
      but uses an empty assignment.
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
    public double evaluate() throws Exception {
        return this.num;
    }
    /**
     * Returns a list of the variables in the expression.
     * @return the value.
     */
    public List<String> getVariables() {
        return new ArrayList<String>();
    }
    /**
     *Returns a nice string representation of the expression.
     * @return the string.
     */
    @Override
    public String toString() {
        return this.num + "";
    }
    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     * @param var the var.
     * @param  expression the  expression.
     * @return the string.
     */
    public Expression assign(String var, Expression expression) {
        return new Num(this.num);

    }
    /**
     * Returns the differentiate relative to variable `var`.
     * @param var variable to differentiate
     * @return Differentiated expression.
     */
    public Expression differentiate(String var) {
        return new Num(0);
    }
    /**
     * simplifies the expression- in this case do nothing.
     * @return num expression.
     */
    public Expression simplify() {
        return new Num(this.num);
    }
}
