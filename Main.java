import java.util.Scanner;


public class Main {
    private static Scanner scnr = new Scanner(System.in);
    private static TicketService ticketService = new TicketService(scnr);
    private static PaymentService paymentService = new PaymentService(scnr);
    private static UserManager userManager = new UserManager(paymentService);


    public static void main(String[] args) {
    	
    	char userOption;
        do {
            printOption();
            userOption = getUserOption();  // 올바른 옵션을 가져올 때까지 getUserOption을 호출합니다.

            if (userOption != 'q') {
                executeOption(userOption);
            }
        } while (userOption != 'q');

        scnr.close();
        System.out.println("Program terminated.");
    }


    
    public static void printOption() {
        System.out.println("\nDo you have an account?");
        System.out.println("Type: y - Yes");
        System.out.println("Type: n - No");
        System.out.println("Type: q - Quit");
    }
    

    
    public static void executeOption(char userOption) {
        switch (userOption) {
            case 'y':
                int userId = userManager.login(scnr);
                if (userId != -1) {
                    if (userManager.isAdmin(userId)) {
                        userManager.manageAdminSession(scnr, userId, paymentService);
                    } else {
                        ticketService.processTicketPurchase(userId, userManager, paymentService); // Use ticket service for normal users
                    }
                }
                break;
            case 'n':
                userManager.signUp(scnr);
                break;
            case 'q':
                break;
        }
    }

    
    
    private static char getUserOption() {
        System.out.println("Please enter 'y', 'n', or 'q':");
        String input = scnr.nextLine().trim().toLowerCase();
        while (!input.matches("[ynq]")) {
            System.out.println("Invalid option. Try again.");
            input = scnr.nextLine().trim().toLowerCase();
        }
        return input.charAt(0);
    }

    public static void handleLoginFailure() {
        while (true) {
            System.out.println("Type 'r' to retry logging in, or 'n' to Sign Up.");
            String nextOption = scnr.nextLine().trim().toLowerCase();
            if ("r".equals(nextOption)) {
                int userId = userManager.login(scnr);
                if (userId != -1) {
                    // 로그인 성공 후에는 사용자가 서비스를 선택하도록 유도합니다.
                    break;
                }
            } else if ("n".equals(nextOption)) {
                userManager.signUp(scnr);
                break;
            }
        }
    }





}

