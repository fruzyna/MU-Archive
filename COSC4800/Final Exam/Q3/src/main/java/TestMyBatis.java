import java.util.List;
import java.util.Scanner;

import com.domain.Apartment;
import com.domain.RentedBy;
import com.service.AptService;

public class TestMyBatis {

	public static void main(String[] args)
	{
		System.out.print("Apt Number: ");
		Scanner kb = new Scanner(System.in);
		String apt = kb.nextLine();
		
		boolean found = false;
		
		AptService service = new AptService();
		List<Apartment> aptList = service.getAptList();
		for (Apartment a : aptList)
		{
			String anum = a.getAnum();
			if(apt.equals(anum))
			{
				found = true;
				System.out.print("Apartment Number #" + anum + " is ");
				if(a.getAvailable().equals("no"))
				{
					System.out.print("not ");
				}
				System.out.print("availabe for renting. ");
				List<RentedBy> rbList = service.getRBListByAnum(anum);
				if(rbList.size() > 0)
				{
					for (RentedBy rb : rbList)
					{
						System.out.print("The apartment last rented for $" + rb.getRent() + " on " +  rb.getStartrent());
					}
				}
				else
				{
					System.out.println("Has not been previously rented.");
				}
			}
		}
		
		if(!found)
		{
			System.out.println("Apartment not found!");
		}
	}
}