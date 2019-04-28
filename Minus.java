import java.util.Map;
/**
 * The type Minus.
 *
 * @author Shir sabo
 */
public class Minus extends BinaryExpression implements Expression {
    /**
     * Minus Constructor - number and expression.
     *
     * @param num1 number
     * @param e2   expression 2
     */
    public Minus(double num1, Expression e2) {
        super(num1, e2);
    }

    /**
     * Minus Constructor - var and expression.
     *
     * @param var1 var name
     * @param e2   expression 2
     */
    public Minus(String var1, Expression e2) {
        super(var1, e2);
    }

    /**
     * Minus Constructor - expression and number.
     *
     * @param e1   expression 1
     * @param num2 number
     */
    public Minus(Expression e1, double num2) {
        super(e1, num2);
    }

    /**
     * Minus Constructor - var & var.
     *
     * @param var1 var name 1
     * @param var2 var name 2
     */
    public Minus(String var1, String var2) {
        super(var1, var2);
    }

    /**
     * Minus Constructor - Expression and var.
     *
     * @param e1   expression 1
     * @param var2 var name
     */
    public Minus(Expression e1, String var2) {
        super(e1, var2);
    }

    /**
     * Minus Constructor - var and num.
     *
     * @param var1 var name
     * @param num2 number
     */
    public Minus(String var1, double num2) {
        super(var1, num2);
    }

    /**
     * Minus Constructor - number and var.
     *
     * @param num1 number
     * @param var2 var name
     */
    public Minus(double num1, String var2) {
        super(num1, var2);
    }

    /**
     * Minus Constructor - num and num.
     *
     * @param num1 number 1
     * @param num2 number 2
     */
    public Minus(double num1, double num2) {
        super(num1, num2);
    }
    /**
     * Instantiates a new Minus.
     *
     * @param e1 the e 1
     * @param e2 the e 2
     */
    public Minus(Expression e1, Expression e2) {
        super(e1, e2);
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
        return  exp1 - exp2;
    }
    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     * @throws Exception If the expression contains a variable which is not in the assignment
     * @return the value.
     */
    public double evaluate() throws Exception {
        double exp1 = this.getExp1().evaluate();
        double exp2 = this.getExp2().evaluate();
        return this.getExp1().evaluate() - this.getExp2().evaluate();
    }
    /**
     * Returns a nice string representation of the expression.
     * @return the string.
     */
    @Override
   public String toString() {
        String s1 = getExp1().toString();
        String s2 = getExp2().toString();
        return "(" + s1 + " - " + s2 + ")";
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
       Expression e1 = getExp1().assign(var, expression);
       Expression e2 = getExp2().assign(var, expression);
       return new Minus(e1, e2);
    }
    /**
     * Differentiate expression.
     * @param var the var
     * @return the expression
     */
    public Expression differentiate(String var) {
        // (f(x)+g(x))' = f'(x)+g'(x)
        Expression a = getExp1().differentiate(var);
        Expression b = getExp2().differentiate(var);
        return new Minus(a, b);
    }
    /**
     * Simplifies the expression.
     * @return the expression
     * @throws Exception if there was a problem  with evaluating.
     */
    public Expression simplify() {
        //recursive call to exp1
        Expression ex1 = getExp1().simplify();
        //recursive call to exp2
        Expression ex2 = getExp2().simplify();
        try {
        if ((ex1.getVariables().size() + ex2.getVariables().size()) != 0) {
            // checks whether the expressions are the same
            //this will work either for formula or numbers
            if (ex1.toString().equals(ex2.toString())) {
                //x - x = 0
                return new Num(0);
            }
            if ((ex1.getVariables().size() == 0) || (ex2.getVariables().size() == 0)) {
                if (ex1.getVariables().size() == 0) {
                    if (ex1.evaluate() == 0) {
                        return new Neg(ex2).simplify();
                    }
                }
                if (ex2.getVariables().size() == 0) {
                    if (ex2.evaluate() == 0) {
                        return ex1;
                    }
                }
            }
            return new Minus(ex1, ex2);
        }
        //if its is just two numbers
        return new Num(new Minus(ex1, ex2).evaluate());
    } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return  null;
    }
}
