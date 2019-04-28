import java.util.Map;
/**
 * The type Div.
 * @author Shir sabo
 */
public class Div extends BinaryExpression implements Expression {
    /**
     * Div Constructor - number & expression.
     *
     * @param num1 number
     * @param e2   expression 2
     */
    public Div(double num1, Expression e2) {
        super(num1, e2);
    }
    /**
     * Div Constructor - var & expression.
     *
     * @param var1 var name
     * @param e2   expression 2
     */
    public Div(String var1, Expression e2) {
        super(var1, e2);
    }
    /**
     * Div Constructor - two expressions.
     *
     * @param e1 expression 1
     * @param e2 expression 2
     */
    public Div(Expression e1, Expression e2) {
        super(e1, e2);
    }
    /**
     * Div Constructor - expression & number.
     *
     * @param e1   expression 1
     * @param num2 number
     */
    public Div(Expression e1, double num2) {
        super(e1, num2);
    }
    /**
     * Div Constructor - Expression & var.
     *
     * @param e1   expression 1
     * @param var2 var name
     */
    public Div(Expression e1, String var2) {
        super(e1, var2);
    }

    /**
     * Description for Div Constructor - number & var.
     *
     * @param num1 number
     * @param var2 var name
     */
    public Div(double num1, String var2) {
        super(num1, var2);
    }

    /**
     * Description for Div Constructor - var & num.
     *
     * @param var1 var name
     * @param num2 number
     */
    public Div(String var1, double num2) {
        super(var1, num2);
    }
    /**
     * Description for Div Constructor - var & var.
     *
     * @param var1 var name 1
     * @param var2 var name 2
     */
    public Div(String var1, String var2) {
        super(var1, var2);
    }
    /**
     * Description for Div Constructor - num & num.
     *
     * @param num1 number 1
     * @param num2 number 2
     */
    public Div(double num1, double num2) {
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
        double exp2 =  this.getExp2().evaluate(assignment);
        return exp1 / exp2;
    }
    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
    public double evaluate() throws Exception {
        double exp1 = this.getExp1().evaluate();
        double exp2 = this.getExp2().evaluate();
        if (exp2 == 0) {
            throw new Exception("dividing by 0");
        }
        return exp1 / exp2;
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
        return new Div(exp1, exp2);
    }
    /**
     * Returns a nice string representation of the expression.
     * @return the string.
     */
    @Override
    public String toString() {
        String s1 = getExp1().toString();
        String s2 = getExp2().toString();
        return "(" + s1 + " / " + s2 + ")";
    }
    /**
     * Differentiate expression.
     * @param var the var
     * @return the expression
     */
    public Expression differentiate(String var) {
        // (f*g' - g*f') / (f^2)
        Expression p2 = new Mult(getExp1(), getExp2().differentiate(var));
        Expression p1 = new Mult(getExp1().differentiate(var), getExp2());
        Expression topOfdiv = new Minus(p1, p2);
        return new Div(topOfdiv, new Pow(getExp2(), 2));
    }
    /**
     * Simplifies the expression.
     * @return the expression
     * @throws Exception if there was a problem  with evaluating.
     */
    public Expression simplify() {
        //recursive call to exp1
        Expression e1 = getExp1().simplify();
        //recursive call to exp2
        Expression e2 = getExp2().simplify();
        try {
        if ((e1.getVariables().size() + e2.getVariables().size()) != 0) {
            // Simplify, see if they are equal to each other
            // If part2 is equal to 1
            if (e2.getVariables().size() == 0) {
                double val2 = e2.evaluate();
                if (val2 == 1) {
                    return e1.simplify();
                }
                if (val2 == 0) {
                    throw new Exception("dividing by 0");
                }
            }
            if (e1.getVariables().size() == 0) {
                double val1 = e1.evaluate();
                if (val1 == 0) {
                    return new Num(0);
                }
            }
            if (e1.toString().equals(e2.toString())) {
                return new Num(1);
            }
            return new Div(e1, e2);
        }
        return new Num(new Div(e1, e2).evaluate());
    } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
