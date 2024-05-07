import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PaymentService {
    private Scanner scnr;
    private HashMap<Integer, List<Payment>> paymentRecords; 

    public PaymentService(Scanner scnr) {
        this.scnr = scnr;
        this.paymentRecords = new HashMap<>();
    }

    public void processPayment(int userId, double amount) { // 금액 파라미터 추가
        System.out.println("");
        System.out.println("[PAYMENT PROCESS]");
        System.out.println("Follow the instructions below to complete your payment.\n");

        String creditCardNumber = getValidCreditCardNumber();
        String expiryDate = getValidExpiryDate();
        String cardCVV = getValidCVV();
        String phoneNumber = getValidPhoneNumber();
        String cardholderName = getValidCardholderName();

        try {
            Payment payment = new Payment(userId, creditCardNumber, expiryDate, cardCVV, phoneNumber, cardholderName, amount); // 금액 사용
            
            System.out.println("");
            System.out.println("");
            System.out.println("Payment created with ID: " + payment.getPaymentid());
            System.out.println("Purchase made with card number ending in: " + creditCardNumber.substring(12));
            System.out.println("Payment amount: " + payment.getAmount()); // 금액 출력

            System.out.println("\nPayment successful!");
            // 결제 기록을 HashMap에 저장
            List<Payment> userPayments = paymentRecords.getOrDefault(userId, new ArrayList<>());
            userPayments.add(payment);
            paymentRecords.put(userId, userPayments);
            
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }
    
    
    // 관리자가 사용자 ID로 결제 기록을 검색하는 메서드
    public List<Payment> getPaymentsByUserId(int userId) {
        return paymentRecords.getOrDefault(userId, new ArrayList<>());
    }
    
    private String getValidCreditCardNumber() {
        System.out.println("Enter your credit card number in blocks of four digits separated by spaces for easier readability (e.g., 1234 5678 9012 3456).");
        String number;
        while (true) {
            System.out.print("Enter Credit Card Number (16 digits): ");
            number = scnr.nextLine().trim().replaceAll("\\s+", "");  // Removes all spaces
            if (number.matches("\\d{16}")) {
                break;
            }
            System.out.println("Invalid card number. Credit card number must contain exactly 16 digits \n");
        }
        return number;
    }

    private String getValidExpiryDate() {
        System.out.println("");
    	System.out.println("Enter the Expiry Date of your card as shown in MM/YY format on the card.");
        System.out.println("Type the month and year directly as a continuous four-digit number (e.g., 0123 for January 2023).");
        String date;
        while (true) {
            System.out.print("Enter Expiry Date (MMYY): ");
            date = scnr.nextLine().trim();
            if (date.matches("(0[1-9]|1[0-2])([0-9]{2})")) { // 년도 검증 로직 강화 필요
                return date;
            }
            System.out.println("Invalid expiry date. Please use MMYY format, where MM must be 01-12, and YY should be current year or later.");
        }
    }

    private String getValidCVV() {
        System.out.println("");
        System.out.println("Enter Card CVV (3 digits security code on the back of your card):");
        String cvv;
        while (true) {
            System.out.print("Enter Card CVV (3 digits): ");
            cvv = scnr.nextLine().trim();
            if (cvv.matches("\\d{3}")) {
                break;
            }
            System.out.println("Invalid CVV. It should be exactly three digits long. Please re-enter your CVV without any spaces or letters.\n");
        }
        return cvv;
    }

    private String getValidPhoneNumber() {
        System.out.println("");
        System.out.println("Please enter your phone number as 10 continuous digits.");
        System.out.println("For example, for the number (123) 456-7890, you should type 1234567890.");
        String phone;
        while (true) {
            System.out.print("Enter Phone Number (XXXXXXXXXX): ");
            phone = scnr.nextLine().trim();
            if (phone.matches("\\d{10}")) {
                break;
            }
            System.out.println("Invalid phone number. Enter exactly 10 digits without any formatting.\n");
        }
        return phone;
    }

    private String getValidCardholderName() {
        System.out.println("");
    	System.out.println("Please enter the cardholder's name as 'Firstname Lastname'.");
        System.out.println("For example, type 'Minwoo Park' for a cardholder named Minwoo Park.");
        String name;
        while (true) {
            System.out.print("Enter Cardholder Name (max 20 characters including spaces): ");
            name = scnr.nextLine();
            if (name.length() <= 20) {
                break;
            }
            System.out.println("The name entered is too long. Please ensure the cardholder's name is no longer than 20 characters, including spaces between first name and last name.\n");
        }
        return name;
    }
} // class
