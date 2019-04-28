import java.util.List;
import java.util.Map;
/**
 * The  Expression interface.
 * @author Shir sabo
 */
public interface Expression {
    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.
     * @param assignment the map.
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
    double evaluate(Map<String, Double> assignment) throws Exception;
    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
    double evaluate() throws Exception;
    /**
     * Returns a list of the variables in the expression.
     * @return the list of Variables.
     */
    List<String> getVariables();
    /**
     * Returns a nice string representation of the expression.
     * @return the string.
     */
    String toString();
    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     * @param var the string.
     * @param  expression string.
     * @return the expression.
     */
    Expression assign(String var, Expression expression);
    /**
     * Differentiate expression.
     * @param var the var
     * @return the expression
     */
    Expression differentiate(String var);
    /**
     * Returned a simplified version of the expression.
     * @throws Exception if there was a problem  with evaluating.
     * @return simplified expression.
     */
    Expression simplify();
}


