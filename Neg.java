import java.util.Map;
/**
 * The type Neg.
 * @author Shir sabo
 */
public class Neg extends UnaryExpression implements Expression {
    /**
     * Description for Neg Constructor - from expression.
     *
     * @param exp1 expression
     */
    public Neg(Expression exp1) {
        super(exp1);
    }

    /**
     * Description for Neg Constructor - from var name.
     *
     * @param varIn var name
     */
    public Neg(String varIn) {
        super(varIn);
    }

    /**
     * Description for Neg Constructor - from number.
     *
     * @param numIn number
     */
    public Neg(double numIn) {
        super(numIn);
    }
    /**
     * Evaluate the expression using the expression tree, and an assignment map for variables.
     * @param assignment value map for variables
     * @return values of evaluation
     * @throws Exception if any variables don't have a value
     */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double exp1 = getExp1().evaluate(assignment);
        return -exp1;
    }
    /**
     * Evaluate the expression using the expression tree, and an assignment map for variables.
     * @return values of evaluation
     * @throws Exception if any variables don't have a value
     */
    public double evaluate() throws Exception {
        double exp1 = getExp1().evaluate();
        return -getExp1().evaluate();
    }
    /**
     * Returns a new expression in which all occurrences of the variable `var` are replaced with an expression.
     * @param var the name of the variable
     * @param expression new value
     * @return new expression with the replaced values
     */
    public Expression assign(String var, Expression expression) {
        Expression exp1 = getExp1().assign(var, expression);
        return new Neg(exp1);
    }
    @Override
    /**
     * Returns a nice string representation of the expression.
     * @return the string.
     */
    public String toString() {
        String s1 = getExp1().toString();
        return "(-" + s1 + ")";
    }
    /**
     * Differentiate expression.
     * @param var the var
     * @return the expression
     */
    public Expression differentiate(String var) {
        Expression exp1 = getExp1().differentiate(var);
        return new Neg(exp1);
    }
    /**
     * Simplifies the expression.
     * @return the expression
     * @throws Exception if there was a problem  with evaluating.
     */
    public Expression simplify() {
        Expression e1 = getExp1().simplify();
        if (e1.getVariables().size() == 0) {
            //only a number
            try {
                return new Num(this.evaluate());
            } catch (Exception ex) {
                // this will never happen, so empty catch
            }
        }
        if (!(getExp1() instanceof Neg)) {
            return new Neg(getExp1().simplify());
        } else {
            Neg currentNumber = (Neg) getExp1();
            //now it is positive
            return currentNumber.getExp1().simplify();
        }
    }
}