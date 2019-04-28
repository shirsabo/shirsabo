import java.util.Map;
/**
 * The type Sin class.
 * @author Shir sabo
 */
public class Sin extends UnaryExpression implements Expression {
    /**
     * Sin Constructor - from expression.
     * @param e1 expression
     */
    public Sin(Expression e1) {
        super(e1);
    }
    /**
     * Sin Constructor - from var name.
     * @param var1 var name
     */
    public Sin(String var1) {
        super(var1);
    }
    /**
     * Sin Constructor - from number.
     * @param num1 number
     */
    public Sin(double num1) {
        super(num1);
    }
    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.
     * @param assignment the map.
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double exp1 = getExp1().evaluate(assignment);
        return Math.sin(exp1);
    }
    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
    public double evaluate() throws Exception {
        double exp1 = getExp1().evaluate();
        return Math.sin(exp1);
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
        return new Sin(getExp1().assign(var, expression));
    }
    @Override
    public String toString() {
        String s1 = getExp1().toString();

        return "sin(" + s1 + ")";
    }
    /**
     * Differentiate expression.
     * @param var the var
     * @return the expression
     */
    public Expression differentiate(String var) {
        //f'(g(x))
        Expression fTag = new Cos(getExp1());
        Expression gTag = getExp1().differentiate(var);
        return new Mult(fTag, gTag);
    }
    /**
     * Simplifies the expression.
     * @return the expression
     * @throws Exception if there was a problem  with evaluating.
     */
    public Expression simplify() {
        Expression exp1 = this.getExp1().simplify();
        if (exp1.getVariables().size() == 0) {
            try {
                return new Num(new Sin(exp1).evaluate());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return new Sin(exp1);
    }
}
