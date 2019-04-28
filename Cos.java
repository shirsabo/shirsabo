import java.util.Map;
/**
 * The type Cos.
 * @author Shir sabo
 */
public class Cos extends UnaryExpression implements Expression {
    /**
     * Cos Constructor - from expression.
     *
     * @param e1 expression
     */
    public Cos(Expression e1) {
            super(e1);
        }
    /**
     * Cos Constructor - from number.
     *
     * @param num1 number
     */
    public Cos(double num1) {
        super(num1);
    }
    /**
     *Cos Constructor - from var name.
     *
     * @param var1 var name
     */
    public Cos(String var1) {
            super(var1);
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
            return Math.cos(exp1);
        }
    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
        public double evaluate() throws Exception {
            double exp1 = getExp1().evaluate();
            return Math.cos(exp1);
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
            Expression exp1 = getExp1().assign(var, expression);
            return new Cos(exp1);
        }
    /**
     * Returns a nice string representation of the expression.
     * @return the string.
     */
        @Override
        public String toString() {
            String s1 = getExp1().toString();
            return "cos(" + s1 + ")";
        }
    /**
     * Differentiate expression.
     * @param var the var
     * @return the expression
     */
        public Expression differentiate(String var) {
            //f'(g(x))
            Expression fTag = new Neg((new Sin(getExp1())));
            Expression gTag = getExp1().differentiate(var);
            return new Mult(fTag, gTag);
        }
    /**
     * Returned a simplified version of the expression.
     * @throws Exception if there was a problem
     * @return simplified  expression
     */
        public Expression simplify() {
            Expression exp1 = this.getExp1().simplify();
            try {
                if (getExp1().getVariables().size() != 0) {
                    return new Cos(exp1);
                } else {
                    return new Num(new Cos(exp1).evaluate());
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            return null;
        }
}
