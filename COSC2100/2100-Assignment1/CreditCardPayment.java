/**
 * A class for CreditCardPayment derived from Payment
 * @author Liam Fruzyna
 */
public class CreditCardPayment extends Payment {
    String name;
    String expirationDate;
    String cardNumber;

    /**
     * Default constructor; Initializes amount to zero and strings to blank.
     */
    public CreditCardPayment() {
        super();
        name = "";
        expirationDate = "";
        cardNumber = "";
    }

    /**
     * Constructor; Initializes variables to input values.
     * @param paymentAmount Amount to set the payment to
     * @param name Name on the credit card
     * @param expirationDate Expiration date of the credit card
     * @param cardNumber Number on the credit card
     */
    public CreditCardPayment(double paymentAmount, String name, String expirationDate, String cardNumber) {
        super(paymentAmount);
        this.name = name;
        this.expirationDate = expirationDate;
        this.cardNumber = cardNumber;
    }

    /**
     * Outputs the amount of the payment and credit card information.
     */
    @Override
    public void paymentDetails() {
        System.out.println("The credit card payment amount is $" + paymentAmount);
        System.out.println("The name on the card is: " + name);
        System.out.println("The expiration date is: " + expirationDate);
        System.out.println("The credit card number is: " + cardNumber);
    }
}
