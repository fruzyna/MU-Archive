/**
 * Base class to store a payment amount and get a description
 * @author Liam Fruzyna
 */
public class Payment {
    protected double paymentAmount;

    /**
     * Default constructor; Initialize amount to zero.
     */
    public Payment() {
        paymentAmount = 0;
    }

    /**
     * Constructor; Initialize amount to input value.
     * @param paymentAmount Input value to initialize amount to
     */
    public Payment(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * Checks if two objects are equal based on their payment amount.
     * @param otherObject Other object to compare current object to
     * @return Whether or not the payments are equivalent
     */
    @Override
    public boolean equals(Object otherObject) {
        if(getClass() == otherObject.getClass()) {
            if(((Payment) otherObject).getPaymentAmount() == paymentAmount) {
                return true;
            }
        }
        return false;
    }

    /**
     * Inializes the amount to input value.
     * @param amount Value to initialize payment amount to
     */
    public void setPaymentAmount(double amount) {
        paymentAmount = amount;
    }

    /**
     * Returns the payment amount.
     * @return Amount of the payment
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Outputs the details of the payment.
     */
    public void paymentDetails() {
        System.out.println("The payment amount is $" + paymentAmount);
    }
}
