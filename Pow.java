import java.util.Map;
/**
 * The type Pow class.
 * @author Shir sabo
 */
public class Pow extends BinaryExpression implements Expression {
    /**
     * Pow Constructor - var and expression.
     * @param var1 var name
     * @param e2   expression 2
     */
    public Pow(String var1, Expression e2) {
        super(var1, e2);
    }
    /**
     * Pow Constructor - two expressions.
     *
     * @param e1 expression 1
     * @param e2 expression 2
     */
    public Pow(Expression e1, Expression e2) {
        super(e1, e2);
    }
    /**
     * Pow Constructor - Expression and var.
     *
     * @param e1   expression 1
     * @param var2 var name
     */
    public Pow(Expression e1, String var2) {
        super(e1, var2);
    }
    /**
     * Pow Constructor - expression & number.
     *
     * @param e1   expression 1
     * @param num2 number
     */
    public Pow(Expression e1, double num2) {
        super(e1, num2);
    }
    /**
     * Description for Pow Constructor - number and var.
     *
     * @param num1 number
     * @param var2 var name
     */
    public Pow(double num1, String var2) {
        super(num1, var2);
    }
    /**
     * Description for Pow Constructor - var and var.
     *
     * @param var1 var name 1
     * @param var2 var name 2
     */
    public Pow(String var1, String var2) {
        super(var1, var2);
    }

    /**
     * Pow Constructor - number and expression.
     *
     * @param num1 number
     * @param e2   expression 2
     */
    public Pow(double num1, Expression e2) {
        super(num1, e2);
    }
    /**
     * Description for Pow Constructor - var and num.
     *
     * @param var1 var name
     * @param num2 number
     */
    public Pow(String var1, double num2) {
        super(var1, num2);
    }
    /**
     * Pow Constructor - num and num.
     * @param num1 number 1
     * @param num2 number 2
     */
    public Pow(double num1, double num2) {
        super(num1, num2);
    }
    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.
     * @param assignment the map.
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double exp1 = this.getExp1().evaluate(assignment);
        double exp2 = this.getExp2().evaluate(assignment);
        return Math.pow(exp1, exp2);
    }
    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
    public double evaluate() throws Exception {
        double exp1 = this.getExp1().evaluate();
        double exp2 = this.getExp2().evaluate();
        return Math.pow(exp1, exp2);
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
        Expression exp2 = getExp2().assign(var, expression);
        return new Pow(exp1, exp2);
    }
    @Override
    /**
     * Returns a nice string representation of the expression.
     * @return the string.
     */
    public String toString() {
        String s1 = getExp1().toString();
        String s2 = getExp2().toString();
        return "(" + s1 + "^" + s2 + ")";
    }
    /**
     * Differentiate expression.
     * @param var the var
     * @return the expression
     */
    public Expression differentiate(String var) {
        Expression fx = getExp1();
        Expression gx = getExp2();
      //  if (fx.toString().equals("e")) {
       //     return  new Mult(new Pow(fx, gx), gx.differentiate(var));
      //  }
        // (Fx ^ Gx)=
        // ((Fx ^ Gx) * (((Fx' * (Gx / Fx)) + (Gx' * log(e, Fx))))
        Expression part1 = new Mult(fx.differentiate(var), new Div(gx, fx));
        Expression part2 = new Mult(gx.differentiate(var), new Log("e", fx));
        return new Mult(new Pow(fx, gx), new Plus(part1, part2));
    }
    /**
     * Returned a simplified version of the expression.
     * @throws Exception if there was a problem
     * @return simplified  expression
     */
    public Expression simplify() {
        //recursive call to exp1
        Expression e1 = getExp1().simplify();
        //recursive call to exp2
        Expression e2 = getExp2().simplify();
        //if expression does not contain any vars.
        try {
            if (e1.getVariables().size() == 0 && e2.getVariables().size() == 0) {
                return new Num(new Pow(e1, e2).evaluate());
            }
            // x^1 = x, x^0 = 1
            if (e1.getVariables().size() == 0) {
                double value1 = e1.evaluate();
                if (value1 == 0) {
                    return new Num(0);
                }
            }
            if (e2.getVariables().size() != 0) {
                //continue the recursion
                return new Pow(e1, e2);
            }
            double value2 = e2.evaluate();
            if (value2 == 0) {
                return new Num(1);
            } else if (value2 == 1) {
                return e1;
            }
            return new Pow(e1, e2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}