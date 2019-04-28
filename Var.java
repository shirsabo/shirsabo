import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * The type Var.
 * @author Shir sabo
 */
public class Var implements Expression {
    private String exp;
    /**
     * Var Constructor.
     * @param expIn some parameter
     */
    public Var(String expIn) {
        this.exp = expIn;
    }
    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.
     * @param assignment the map.
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
   public double evaluate(Map<String, Double> assignment) throws Exception {
        String str = this.exp;
        for (String key: assignment.keySet()) {
            if ((str.equals(key))) {
                return assignment.get(str);
            }
        }
        // if not found
        throw new Exception("Variable not found");
    }
    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
    public double evaluate() throws Exception {
        throw new Exception("no value");
    }
    /**
     * Returns a list of the variables in the expression.
     * @return the list of Variables.
     */
    @Override
    public List<String> getVariables() {
        List<String> l = new ArrayList<String>();
        l.add(this.exp);
        return l;
    }
    /**
     * Returns a nice string representation of the expression.
     * @return the string.
     */
    @Override
    public String toString() {
        return this.exp;
     }
    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     * @param var the string.
     * @param  expression string.
     * @return the expression.
     */
    public Expression assign(String var, Expression expression) {
        if (var.equals(this.exp)) {
            return expression;
        }
        return new Var(this.exp);
    }
    /**
     * Differentiate expression.
     * @param var the var
     * @return the expression
     */
    public Expression differentiate(String var) {
        if (var.equals(this.exp)) {
            Expression number = new Num(1);
            return number;
        } else {
            Expression number = new Num(0);
            return number;
        }
    }
    /**
     * Returned a simplified version of the expression.
     * @return simplified expression.
     */
    public Expression simplify() {
        // Variable simplify - do nothing
        return new Var(this.exp);
    }
}
