import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
 * The BinaryExpression class.
 * @author Shir sabo
 */
public abstract class BinaryExpression extends BaseExpression {
    private Expression e1;
    private Expression e2;
    /**
     * BinaryExpression Constructor - Expression and var.
     * @param e1 expression 1
     * @param var2 var name, the content
     */
    public BinaryExpression(Expression e1, String var2) {
        this.e1 = e1;
        // now e2 is a var
        this.e2 = new Var(var2);
    }
    /**
     * BinaryExpression Constructor - number and expression.
     * @param num1 number
     * @param e2 expression 2
     */
    public BinaryExpression(double num1, Expression e2) {
        // now e2 is a number
        this.e1 = new Num(num1);
        this.e2 = e2;
    }
    /**
     * BinaryExpression Constructor - var and expression.
     * @param var1 var name, the content
     * @param e2 expression 2
     */
    public BinaryExpression(String var1, Expression e2) {
        // now e1 is a var
        this.e1 = new Var(var1);
        this.e2 = e2;
    }
    /**
     * BinaryExpression Constructor - expression and number.
     * @param e1 expression 1
     * @param num2 number
     */
    public BinaryExpression(Expression e1, double num2) {
        this.e1 = e1;
        // now e2 is a number
        this.e2 = new Num(num2);
    }
    /**
     * BinaryExpression Constructor - number and var.
     * @param num1 number
     * @param var2 var name
     */
    public BinaryExpression(double num1, String var2) {
        // now e1 is a number
        this.e1 = new Num(num1);
        // now e2 is a var.
        this.e2 = new Var(var2);
    }
    /**
     * BinaryExpression Constructor - var and num.
     * @param var1 var name
     * @param num2 number
     */
    public BinaryExpression(String var1, double num2) {
        // now e1 is a var.
        this.e1 = new Var(var1);
        // now e2 is a number
        this.e2 = new Num(num2);
    }
    /**
     * BinaryExpression Constructor - num and num.
     * @param num1 number 1
     * @param num2 number 2
     */
    public BinaryExpression(double num1, double num2) {
        this.e1 = new Num(num1);
        this.e2 = new Num(num2);
    }
    /**
     * BinaryExpression Constructor - two expressions.
     * @param e1 expression 1
     * @param e2 expression 2
     */
    public BinaryExpression(Expression e1, Expression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }
    /**
     *BinaryExpression Constructor - var and var.
     * @param var1 var name 1
     * @param var2 var name 2
     */
    public BinaryExpression(String var1, String var2) {
        //both expressions are now vars.
        this.e1 = new Var(var1);
        this.e2 = new Var(var2);
    }
    /**
     * Getter for the first expression object.
     * @return the child expression
     */
    protected Expression getExp1() {
        return this.e1;
    }
    /**
     * Getter for the second expression object.
     * @return the child expression
     */
    protected Expression getExp2() {
        return this.e2;
    }
    /**
     * Returns a list of the variables in the expression.
     * @return list of variable names
     */
    public List<String> getVariables() {
        // creates an aray of strings.
        List<String> vars = new ArrayList<String>();
        //recursion call to exp1
        vars.addAll(getExp1().getVariables());
        //recursion call to exp2
        vars.addAll(getExp2().getVariables());
        //returns array list containing all the vars.
        return new ArrayList<>(new HashSet<>(vars));
    }
}
