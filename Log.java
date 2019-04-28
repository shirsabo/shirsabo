import java.util.Map;
/**
 * The Log class.
 * @author Shir sabo
 */
public class Log extends BinaryExpression implements Expression {

    /**
     * Log Constructor - var & expression.
     *
     * @param var1 var name
     * @param e2   expression 2
     */
    public Log(String var1, Expression e2) {
        super(var1, e2);
    }
    /**
     * Log Constructor - two expressions.
     *
     * @param e1 expression 1
     * @param e2 expression 2
     */
    public Log(Expression e1, Expression e2) {
        super(e1, e2);
    }
    /**
     * Log Constructor - number & expression.
     *
     * @param num1 number
     * @param e2   expression 2
     */
    public Log(double num1, Expression e2) {
        super(num1, e2);
    }
    /**
     * Log Constructor - Expression & var.
     *
     * @param e1   expression 1
     * @param var2 var name
     */
    public Log(Expression e1, String var2) {
        super(e1, var2);
    }
    /**
     * Log Constructor - expression & number.
     *
     * @param e1   expression 1
     * @param num2 number
     */
    public Log(Expression e1, double num2) {
        super(e1, num2);
    }

    /**
     * Description for Log Constructor - num & num.
     *
     * @param num1 number 1
     * @param num2 number 2
     */
    public Log(double num1, double num2) {
        super(num1, num2);
    }

    /**
     * Description for Log Constructor - number & var.
     *
     * @param num1 number
     * @param var2 var name
     */
    public Log(double num1, String var2) {
        super(num1, var2);
    }
    /**
     * Description for Log Constructor - var & var.
     *
     * @param var1 var name 1
     * @param var2 var name 2
     */
    public Log(String var1, String var2) {
        super(var1, var2);
    }
    /**
     * Description for Log Constructor - var & num.
     *
     * @param var1 var name
     * @param num2 number
     */
    public Log(String var1, double num2) {
        super(var1, num2);
    }
    /**
     * Evaluate the expression.
     * @param assignment map of key and values.
     * @return values of evaluation
     * @throws Exception if there us no value.
     */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        //recursion call to first expression.
        double value1 = this.getExp1().evaluate(assignment);
        //recursion call to first expression.
        double value2 = this.getExp2().evaluate(assignment);
        //evaluates
        return Math.log(value1) / Math.log(value2);
    }
    /**
     * Evaluate the expression, uses an empty assignment.
     * @return values of evaluation
     * @throws Exception if there is no value.
     */
   public double evaluate() throws Exception {
        double exp1 = this.getExp1().evaluate();
        double exp2 = this.getExp2().evaluate();
        return Math.log(exp1) / Math.log(exp2);
    }
    /**
     * Returns a string representation of the expression.
     * @return a string of the expression.
     */
     public String toString() {
         String s1 = getExp1().toString();
         String s2 = getExp2().toString();
         return "log(" + s1 + "," + s2 + ")";
     }
    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     * @param var the string.
      @param expression the expression.
     * @return the new expression.
     */
    public Expression assign(String var, Expression expression) {
        Expression exp1 = getExp1().assign(var, expression);
        Expression exp2 = getExp2().assign(var, expression);
        return new Log(exp1, exp2);
    }
    /**
     * Returns the differentiate expression.
     * @param var variable to differentiate
     * @return Differentiated tree
     */
    public Expression differentiate(String var) {
        // (ln(e2) / ln(e1))
        // Creates ln1
        Expression ln1 = new Log("e", getExp1());
        // Creates ln2
        Expression ln2 = new Log("e", getExp2());
        Expression ex1 = differentiateHelper(getExp1(), var);
        Expression ex2 = differentiateHelper(getExp2(), var);
        // (e2*e1' - e1*e2')
        Expression part1 = new Minus(new Mult(ln1, ex2), new Mult(ln2, ex1));
        //(e1^2)
        Expression  part2 = new Pow(ln1, 2);
        return new Div(part1, part2);
    }
    /**
     * Differentiate helper expression.
     * @param exp the exp
     * @param var the var
     * @return the expression
     */
    public Expression differentiateHelper(Expression exp, String var) {
         return new Mult(new Div(1, exp), exp.differentiate(var));
    }
    /**
     * Simplifies the expression.
     * @return the expression
     * @throws Exception if there was a problem  with evaluating.
     */
    public Expression simplify() {
        // recursion call to first expression.
        Expression e1 = getExp1().simplify();
        // recursion call to second expression.
        Expression e2 = getExp2().simplify();
        //if the expression contains vars.
        try {
        if ((e1.getVariables().size() + e2.getVariables().size()) != 0) {
            // if both expression are the same
            if (checkSimplify(e1.toString(), e2.toString())) {
                //return the num 1.
                return new Num(1);
            }
            //return the simplified log.
            return new Log(e1, e2);
        }
        //if the expression does not contain vars, only numbers.
        Expression ex1 = new Num(e1.evaluate());
        Expression ex2 = new Num(e2.evaluate());
        // returns the number
        Expression a = new Num(new Log(ex1, ex2).evaluate());
        return a;
    } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    /**
     * Check simplify boolean.
     * @param e1 the e 1
     * @param e2 the e 2
     * @return the boolean
     */
    public boolean checkSimplify(String e1, String e2) {
        if (e1.equals(e2)) {
            return true;
        }
        return false;
    }
}
