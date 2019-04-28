/**
 * The BaseExpression class.
 * @author Shir sabo
 */
public abstract class BaseExpression {
        private String finalString;
        /**
         * Setter for the final string.
         * @param s the format string
         */
        public void setexp(String s) {
            this.finalString = s;
        }
        /**
         * Getter for the final string.
         * @return the format string
         */
        public  String getexp() {
            return this.finalString;
        }
    }

