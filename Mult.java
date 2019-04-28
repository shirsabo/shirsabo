import java.util.Map;
/**
 * The type Mult.
 * @author Shir sabo
 */
public class Mult extends BinaryExpression implements Expression {
    /**
     * Mult Constructor - two expressions.
     *
     * @param e1 expression 1
     * @param e2 expression 2
     */
    public Mult(Expression e1, Expression e2) {
        super(e1, e2);
    }
    /**
     * Mult Constructor - Expression and var.
     *
     * @param e1   expression 1
     * @param var2 var name
     */
    public Mult(Expression e1, String var2) {
        super(e1, var2);
    }
    /**
     * Mult Constructor - var and expression.
     *
     * @param var1 var name
     * @param e2   expression 2
     */
    public Mult(String var1, Expression e2) {
        super(var1, e2);
    }
    /**
     * Mult Constructor - number and expression.
     *
     * @param num1 number
     * @param e2   expression 2
     */
    public Mult(double num1, Expression e2) {
        super(num1, e2);
    }
    /**
     * Description for Mult Constructor - number and var.
     *
     * @param num1 number
     * @param var2 var name
     */
    public Mult(double num1, String var2) {
        super(num1, var2);
    }

    /**
     * Mult Constructor - expression and number.
     *
     * @param e1   expression 1
     * @param num2 number
     */
    public Mult(Expression e1, double num2) {
        super(e1, num2);
    }
    /**
     * Description for Mult Constructor - var and num.
     *
     * @param var1 var name
     * @param num2 number
     */
    public Mult(String var1, double num2) {
        super(var1, num2);
    }

    /**
     * Description for Mult Constructor - var and var.
     * @param var1 var name 1
     * @param var2 var name 2
     */
    public Mult(String var1, String var2) {
        super(var1, var2);
    }
    /**
     * Description for Mult Constructor - num and num.
     *
     * @param num1 number 1
     * @param num2 number 2
     */
    public Mult(double num1, double num2) {
        super(num1, num2);
    }
    /**
     * Evaluate the expression.
     * @param assignment map of key and values.
     * @return values of evaluation
     * @throws Exception if there us no value.
     */
   public double evaluate(Map<String, Double> assignment) throws Exception {
        return this.getExp1().evaluate(assignment) * this.getExp2().evaluate(assignment);
    }
    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * but uses an empty assignment.
     * @return values of evaluation
     * @throws Exception if there us no value.
     */
    public double evaluate() throws Exception {
        return this.getExp1().evaluate() * this.getExp2().evaluate();
    }
    /**
     * Returns a string representation of the expression.
     * @return a string of the expression.
     */
    public String toString() {
        String s1 = getExp1().toString();
        String s2 = getExp2().toString();
        return "(" + s1 + " * " + s2 + ")";
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
        return new Mult(getExp1().assign(var, expression), getExp2().assign(var, expression));
    }
    /**
     * Returns the expression tree resulting from differentiating the current expression relative to variable `var`.
     * @param var variable to differentiate
     * @return Differentiated tree
     */
    public Expression differentiate(String var) {
        //f(x)*g(x) = f(x)*g'(x)+f'(x)*g(x)
        //f(x)*g'(x) = a
        Expression a = new Mult(getExp1(), getExp2().differentiate(var));
        //f'(x)*g(x) = b
        Expression b = new Mult(getExp1().differentiate(var), getExp2());
        return new Plus(a, b);
    }
    /**
     * Simplifies the expression.
     * @return simplified tree
     */
    public Expression simplify() {
        // recursive call to exp1
        Expression e1 = getExp1().simplify();
        // recursive call to exp2
        Expression e2 = getExp2().simplify();
        try {
            if ((e1.getVariables().size() + e2.getVariables().size()) != 0) {
                // If either one is equal to 0/1, that's easy
                if ((e1.getVariables().size() != 0) && (e2.getVariables().size() != 0)) {
                    return new Mult(e1, e2);
                }
                if (e1.getVariables().size() == 0) {
                    double value1 = e1.evaluate();
                    Expression ex1 = checkMult(value1, e2);
                    if (ex1 != null) {
                        if ((ex1.toString().equals("0.0")) || (ex1.toString().equals("0"))) {
                            return new Num(0);
                        }
                        return e2;
                    }
                    return new Mult(e1, e2);
                }
                if (e2.getVariables().size() == 0) {
                    double value2 = e2.evaluate();
                    Expression ex2 = checkMult(value2, e1);
                    if (ex2 != null) {
                        if ((ex2.toString().equals("0")) || (ex2.toString().equals("0.0"))) {
                            return new Num(0);
                        }
                        return e1;
                    }
                    //xa => ax
                    return new Mult(e2, e1);
                }
            }
            return new Num(new Mult(e1, e2).evaluate());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return  null;
    }
    /**
     * Check mult expression.
     * @param val the val
     * @param ex  the ex
     * @return the expression
     */
    public Expression checkMult(double val, Expression ex) {
        if (val == 0) {
            return new Num(0);
        }
        if (val == 1) {
            return ex;
        }
        return null;
    }
    }