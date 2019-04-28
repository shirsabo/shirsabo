import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
 * The UnaryExpression class.
 * @author Shir sabo
 */
public abstract class UnaryExpression extends BaseExpression {
    private Expression e;
    /**
     * Setter for the child expression object.
     * @param newE the child expression
     */
    protected void setExp1(Expression newE) {
        this.e = newE;
    }
    /**
     * Getter for the child expression object.
     * @return the child expression
     */
    protected Expression getExp1() {
        return this.e;
    }
    /**
     * Description for UnaryExpression Constructor - from expression.
     * @param e1 expression
     */
    public UnaryExpression(Expression e1) {
        this.e = e1;
    }
    /**
     * Description for UnaryExpression Constructor - from var name.
     * @param var1 var name
     */
    public UnaryExpression(String var1) {
        this.e = new Var(var1);
    }
    /**
     * Description for UnaryExpression Constructor - from number.
     * @param num1 number
     */
    public UnaryExpression(double num1) {
        this.e = new Num(num1);
    }
    /**
     * Function to retrieve all the variable names from all children expressions.
     * @return list of variable names
     */
    public List<String> getVariables() {
        return new ArrayList<>(new HashSet<>(e.getVariables()));
    }

}
