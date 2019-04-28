import java.util.Map;

/**
 * The type Plus.
 */
public class Plus extends BinaryExpression implements Expression {
    /**
     * Plus Constructor - Expression & var.
     *
     * @param e1   expression 1
     * @param var2 var name
     */
    public Plus(Expression e1, String var2) {
        super(e1, var2);
    }

    /**
     * Plus Constructor - two expressions.
     *
     * @param e1 expression 1
     * @param e2 expression 2
     */
    public Plus(Expression e1, Expression e2) {
        super(e1, e2);
    }

    /**
     * Plus Constructor - number & expression.
     *
     * @param num1 number
     * @param e2   expression 2
     */
    public Plus(double num1, Expression e2) {
        super(num1, e2);
    }

    /**
     * Plus Constructor - expression & number.
     *
     * @param e1   expression 1
     * @param num2 number
     */
    public Plus(Expression e1, double num2) {
        super(e1, num2);
    }

    /**
     * Description for Plus Constructor - number & var.
     *
     * @param num1 number
     * @param var2 var name
     */
    public Plus(double num1, String var2) {
        super(num1, var2);
    }

    /**
     * Plus Constructor - var & expression.
     *
     * @param var1 var name
     * @param e2   expression 2
     */
    public Plus(String var1, Expression e2) {
        super(var1, e2);
    }

    /**
     * Description for Plus Constructor - var & num.
     *
     * @param var1 var name
     * @param num2 number
     */
    public Plus(String var1, double num2) {
        super(var1, num2);
    }

    /**
     * Description for Plus Constructor - var & var.
     *
     * @param var1 var name 1
     * @param var2 var name 2
     */
    public Plus(String var1, String var2) {
        super(var1, var2);
    }

    /**
     * Description for Plus Constructor - num & num.
     *
     * @param num1 number 1
     * @param num2 number 2
     */
    public Plus(double num1, double num2) {
        super(num1, num2);
    }

    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result.
     *
     * @param assignment the map.
     * @return the value.
     * @throws Exception If the expression contains a variable which is not in the assignment
     */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double exp1 = this.getExp1().evaluate(assignment);
        double exp2 = this.getExp2().evaluate(assignment);
        return exp1 + exp2;
    }

    /**
     * A convenience method. Like the `evaluate(assignment)` method above,
     *
     * @return the value.
     * @throws Exception If the expression contains a variable which is not in the assignment
     */
    public double evaluate() throws Exception {
        double exp1 = this.getExp1().evaluate();
        double exp2 = this.getExp2().evaluate();
        return exp1 + exp2;
    }

    /**
     * Returns a nice string representation of the expression.
     *
     * @return the string.
     */
    @Override
    public String toString() {
        String s1 = getExp1().toString();
        String s2 = getExp2().toString();
        return "(" + s1 + " + " + s2 + ")";
    }

    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     *
     * @param var        the string.
     * @param expression string.
     * @return the expression.
     */
    public Expression assign(String var, Expression expression) {
        Expression e1 = getExp1().assign(var, expression);
        Expression e2 = getExp2().assign(var, expression);
        return new Plus(e1, e2);
    }

    /**
     * Differentiate expression.
     *
     * @param var the var
     * @return the expression
     */
    public Expression differentiate(String var) {
        // (f(x)+g(x))'=f'(x)+g'(x)
        Expression a = getExp1().differentiate(var);
        Expression b = getExp2().differentiate(var);
        return new Plus(a, b);
    }

    /**
     * Simplifies the expression.
     *
     * @return the expression
     * @throws Exception if there was a problem  with evaluating.
     */
    public Expression simplify() {
        //recursive call to exp1
        Expression e1 = getExp1().simplify();
        //recursive call to exp2
        Expression e2 = getExp2().simplify();
        // if there are vars in the expression
        if ((e1.getVariables().size() + e2.getVariables().size()) != 0) {
            try {
                if (e1.getVariables().size() == 0) {
                    double val1 = e1.evaluate();
                    //if exp1 = num(0)
                    if (val1 == 0) {
                        return e2;
                    }
                }
                if (e2.getVariables().size() == 0) {
                    double val2 = e2.evaluate();
                    //if exp1 = num(0)
                    if (val2 == 0) {
                        return e1;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            //check if neg
            boolean ifE1Neg = simplifyHelper(e1);
            boolean ifE2Neg = simplifyHelper(e2);
            if (ifE1Neg || ifE2Neg) {
                if (ifE1Neg && ifE2Neg) {
                    return new Neg(new Plus(new Neg(e1), new Neg(e2))).simplify();
                }
                if (ifE1Neg) {
                    return new Minus(e2, new Neg(e1)).simplify();
                }
                if (ifE2Neg) {
                    return new Minus(e1, new Neg(e2)).simplify();
                }
            }
            return new Plus(e1, e2);
        }
        try {
                return new Num(new Plus(e1, e2).evaluate());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        return null;
    }

    /**
     * Simplify helper boolean.
     *
     * @param ex the ex
     * @return the boolean
     */
    public boolean simplifyHelper(Expression ex) {
        boolean ifExNeg = ex instanceof Neg;
        return ifExNeg;
    }
}
