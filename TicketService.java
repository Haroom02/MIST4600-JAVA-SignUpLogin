import java.util.InputMismatchException;
import java.util.Scanner;

public class TicketService {
    private Scanner scnr;

    public TicketService(Scanner scnr) {
        this.scnr = scnr;
    }

    public void processTicketPurchase(int userId, UserManager userManager, PaymentService paymentService) {
        boolean continuePurchasing = true;
        int grandTotal = 0;
        StringBuilder summary = new StringBuilder();

        while (continuePurchasing) {
            try {
                System.out.println("\nAvailable Football Ticket Categories:");
                System.out.println("1. Regular - $100");
                System.out.println("2. Premium - $150");
                System.out.println("3. VIP - $200");
                System.out.print("Select a category (1-3): ");
                int category = scnr.nextInt();
                int price = 0;
                String ticketType = "";

                switch (category) {
                    case 1:
                        price = 100;
                        ticketType = "Regular";
                        break;
                    case 2:
                        price = 150;
                        ticketType = "Premium";
                        break;
                    case 3:
                        price = 200;
                        ticketType = "VIP";
                        break;
                    default:
                        System.out.println("Invalid category. Please select 1, 2, or 3.");
                        continue;
                }

                System.out.print("Enter the number of tickets you want to buy: ");
                int count = scnr.nextInt();
                if (count <= 0) {
                    throw new IllegalArgumentException("Number of tickets must be greater than zero.");
                }
                int total = price * count;
                grandTotal += total;
                summary.append(count).append(" ").append(ticketType).append(" ticket(s) totaling $").append(total).append(".\n");

                System.out.println("");
                System.out.println("You have selected " + count + " " + ticketType + " ticket(s) for a total of $" + total + ".");
                
                System.out.println("");
                System.out.println("Current order summary:\n" + summary);
                System.out.print("Would you like to purchase more tickets? (yes/no): ");
                String moreTickets = scnr.next().trim().toLowerCase();
                if (!"yes".equals(moreTickets)) {
                    System.out.println("Final decision: You are purchasing the following tickets:\n" + summary);
                    System.out.println("Total amount due: $" + grandTotal);
                    System.out.print("Proceed to payment? (yes/no): ");
                    String finalChoice = scnr.next().trim().toLowerCase();
                    if ("yes".equals(finalChoice)) {
                        paymentService.processPayment(userId, grandTotal);
                        continuePurchasing = false; // End purchase loop and go back to main menu
                    } else if ("no".equals(finalChoice)) {
                        continuePurchasing = false; // Exit without proceeding to payment
                        grandTotal = 0;
                        summary = new StringBuilder();
                    }
                }
            
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter numeric values.");
                scnr.nextLine(); // to clear the buffer
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
