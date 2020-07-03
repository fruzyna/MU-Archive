/**
 * A cash payment class derived from payment
 * @author Liam Fruzyna
 */
public class CashPayment extends Payment {

    /**
     * Default constructor; Initialize amount to zero.
     */
    public CashPayment() {
        super();
    }

    /**
     * Constructor; Initialize amount to input value.
     * @param paymentAmount Amount to initialize to
     */
    public CashPayment(double paymentAmount) {
        super(paymentAmount);
    }

    /**
     * Outputs the amount of the payment and indicates that it is a cash payment.
     */
    @Override
    public void paymentDetails() {
        System.out.println("The cash payment amount is $" + paymentAmount);
    }
}
