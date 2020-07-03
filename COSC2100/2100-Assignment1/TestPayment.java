/**
 * Main test class
 */
public class TestPayment
{
    public static void main(String[] args)
    {
        // Create several test classes and invoke the paymentDetails method
        CashPayment cash1 = new CashPayment(100), cash2 = new CashPayment(100);
        CreditCardPayment credit1 = new CreditCardPayment(100, "Fred",
                "10/5/2010", "123456789");
        CreditCardPayment credit2 = new CreditCardPayment(100, "Barney",
                "11/15/2009", "987654321");

        System.out.println("Cash 1 details:");
        cash1.paymentDetails();
        System.out.println();
        System.out.println("Cash 2 details:");
        cash2.paymentDetails();
        System.out.println();

        System.out.println("Credit 1 details:");
        credit1.paymentDetails();
        System.out.println();
        System.out.println("Credit 2 details:");
        credit2.paymentDetails();
        System.out.println();

        if (cash2.equals(cash1))
            System.out.println("cash2 and cash1 are equal");

        if (cash2.equals(credit1))
            System.out.println("cash2 and credit1 are equal");

        if (credit1.equals(credit2))
            System.out.println("credit2 and credit1 are equal");

    }

} // end of TestPayment