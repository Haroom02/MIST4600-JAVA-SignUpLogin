import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

public class UserManager {
    private List<User> users = new ArrayList<>();
    private List<Integer> loggedInUserIds = new ArrayList<>();
    private ConcurrentHashMap<Integer, String> passwordHashes = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, List<Integer>> userPayments = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(UserManager.class.getName());
    private FileHandler fileHandler;
    private PaymentService paymentService; // PaymentService 참조 추가

    
    public UserManager(PaymentService paymentService) {
        this.paymentService = paymentService; // PaymentService 초기화
    	try {
            fileHandler = new FileHandler("UserManager.log", true);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error setting up logging.", e);
        }
        
        // Pre-populate users
        initializeUsers();
    }
    
   

    // 생성자: 초기 사용자를 추가합니다.
    private void initializeUsers() {
        try {
            // 기존 사용자 추가
            addUser(new User(811054000, "Minwoo Park", "passwordAdmin", "02/19/2002", "M", "mp74484@uga.edu", 555555555, true));
            addUser(new User(811054001, "Lilly Cargile", "ritzcracker", "05/05/1990", "W", "lillyc@example.com", 555555556, true));
            addUser(new User(810000001, "userOne", "password1", "01/01/1990", "M", "userone@example.com", 1234567890, false));
            addUser(new User(810000002, "userTwo", "password2", "02/02/1990", "F", "usertwo@example.com", 987654321, false));
            addUser(new User(810000003, "userThree", "password3", "03/03/1990", "M", "userthree@example.com", 1231231234, false));
            addUser(new User(810000004, "userFour", "password4", "04/04/1990", "F", "userfour@example.com", 432143214, false));
            addUser(new User(810000005, "userFive", "password5", "05/05/1990", "M", "userfive@example.com", 555555555, false));
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error initializing users: " + e.getMessage());
        }
    } // user manager()
    

    public void finalize() {
        try {
            if (fileHandler != null) {
                fileHandler.close();
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error closing file handler", e);
        }
    }
    
    
    public List<Integer> getPaymentRecordsByUserId(int userId) {
        return userPayments.getOrDefault(userId, new ArrayList<>());
    }

    private void addUser(User user) {
        synchronized (this.users) {
            users.add(user);
            passwordHashes.put(user.getUserid(), hashPassword(user.getPassword()));
        }
    }

    private String hashPassword(String password) {
        return Integer.toString(password.hashCode()); // 비밀번호를 해시로 변환
    }
    
    public void addPaymentRecord(int userId, int paymentId) {
        List<Integer> payments = userPayments.getOrDefault(userId, new ArrayList<>());
        payments.add(paymentId);
        userPayments.put(userId, payments);
    }
    
    public boolean isAdmin(int userId) {
        User user = findUserById(userId);
        return user != null && user.getIsAdmin();
    }
    
    private User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserid() == userId) {
                return user;
            }
        }
        return null;
    }
    

    // 회원가입 메소드: 새로운 사용자의 정보를 입력받아 등록합니다.
    public void signUp(Scanner scnr) {
        try {
            System.out.println("[SIGN UP ON SYSTEM]");
            int userid = validateStudentID(scnr);
            String username = validateUsername(scnr);
            String password = validatePassword(scnr);
            String birthdate = validateBirthdate(scnr);
            String gender = validateGender(scnr);
            String email = validateEmail(scnr);
            int contact = validateContact(scnr);

            User newUser = new User(userid, username, password, birthdate, gender, email, contact, false);
            addUser(newUser);
            System.out.println("User registered successfully!");
        } catch (Exception e) {
            System.out.println("Failed to sign up: " + e.getMessage());
        }
    }

    private int validateStudentID(Scanner scnr) {
        while (true) {
            System.out.print("Enter UGA Student ID (must be 9 digits and start with '81'): ");
            String input = scnr.nextLine().trim();
            if (input.matches("81\\d{7}")) {
                return Integer.parseInt(input);
            }
            System.out.println("Invalid User ID. It must be 9 digits and start with '81'.");
        }
    }

    private String validateUsername(Scanner scnr) {
        while (true) {
            System.out.print("Enter Username (20 characters max): ");
            String username = scnr.nextLine().trim();
            if (username.length() <= 20) {
                return username;
            }
            System.out.println("Username must be 20 characters or fewer.");
        }
    }

    private String validatePassword(Scanner scnr) {
        while (true) {
            System.out.print("Enter Password (must include numbers and letters): ");
            String password = scnr.nextLine();
            if (password.matches(".*[a-zA-Z].*") && password.matches(".*[0-9].*")) {
                return password;
            }
            System.out.println("Password must include both letters and numbers.");
        }
    }

    private String validateBirthdate(Scanner scnr) {
        while (true) {
            System.out.print("Enter Birthdate (MM/DD/YYYY): ");
            String birthdate = scnr.nextLine();
            if (birthdate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                return birthdate;
            }
            System.out.println("Birthdate must be in the format MM/DD/YYYY.");
        }
    }

    private String validateGender(Scanner scnr) {
        while (true) {
            System.out.print("Enter Gender (1 for Male, 2 for Female, 3 for Other): ");
            String input = scnr.nextLine();
            switch (input) {
                case "1":
                    return "Male";
                case "2":
                    return "Female";
                case "3":
                    return "Other";
                default:
                    System.out.println("Invalid gender option. Choose 1, 2, or 3.");
                    break;
            }
        }
    }

    private String validateEmail(Scanner scnr) {
        while (true) {
            System.out.print("Enter your UGA email local-part (max 10 chars before @uga.edu): ");
            String email = scnr.nextLine().trim();
            if (email.matches("\\w{1,10}")) {
                return email + "@uga.edu";
            }
            System.out.println("Email local-part must be 1 to 10 alphanumeric characters.");
        }
    }

    private int validateContact(Scanner scnr) {
        while (true) {
            System.out.print("Enter Contact Number (format: (###) ###-####): ");
            String contactInput = scnr.nextLine();
            if (contactInput.matches("\\(\\d{3}\\) \\d{3}-\\d{4}")) {
                return Integer.parseInt(contactInput.replaceAll("[^0-9]", ""));
            }
            System.out.println("Please enter the contact number in the format (###) ###-####.");
        }
    }

    

 // 로그인 메소드: 사용자 ID와 비밀번호를 검증하여 로그인 처리를 합니다.
    public int login(Scanner scnr) {
        System.out.println("\n[LOGIN TO SYSTEM]");
        System.out.print("Please enter your User ID (9 digits starting with '81'): ");
        int userid = -1;
        int attempts = 0;

        try {
            userid = Integer.parseInt(scnr.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: User ID must be numeric. Please enter 9 digits starting with '81'.");
            return -1; // 오류 발생 시 -1 반환
        }

        if (!passwordHashes.containsKey(userid)) {
            System.out.println("User ID not found. Please check your ID and try again.");
            return -1; // 유저 ID가 존재하지 않으면 -1 반환
        }

        while (attempts < 3) {
            System.out.print("Enter your Password: ");
            String inputPassword = scnr.nextLine();
            String hashedInputPassword = hashPassword(inputPassword);

            if (hashedInputPassword.equals(passwordHashes.get(userid))) {
                if (!loggedInUserIds.contains(userid)) {
                    loggedInUserIds.add(userid); // 로그인된 유저 ID 목록에 추가
                }
                logger.info("User " + userid + " logged in successfully.");
                System.out.println("Login successful.");
                return userid; // 로그인 성공시 userid 반환
            } else {
                attempts++;
                logger.info("Invalid password attempt " + attempts + " for User ID " + userid);
                System.out.println("Invalid password entered. Attempts left: " + (3 - attempts));
            }
        }

        // 최대 시도 횟수를 초과한 경우
        logger.warning("User " + userid + " exceeded password attempts and failed to login.");
        System.out.println("You have entered an incorrect password three times.");
        System.out.println("Forgot your password? You can reset it or try logging in again. Type 'reset' to reset your password, 'retry' to try again, or anything else to exit.");
        String choice = scnr.nextLine().trim().toLowerCase();

        if ("reset".equals(choice)) {
            resetPassword(scnr, userid);
        } else if ("retry".equals(choice)) {
            return login(scnr); // 재귀적으로 로그인 메소드 다시 호출
        }

        return -1; // 최종적으로 실패하거나 프로그램 종료를 선택하면 -1 반환
    }



    public boolean manageAdminSession(Scanner scnr, int userId, PaymentService paymentService) {
        User user = findUserById(userId);
        if (user != null && user.getIsAdmin()) {
            String option;
            do {
                System.out.println("\nWelcome, Administrator.");
                System.out.println("1. Display all users");
                System.out.println("2. View user log records");
                System.out.println("3. View user payment records");
                System.out.println("4. Exit and return to main menu");
                System.out.print("Choose an option: ");
                option = scnr.nextLine().trim();

                switch (option) {
                    case "1":
                        displayAllUsers();
                        break;
                    case "2":
                        System.out.print("Enter User ID to view logs: ");
                        int logUserId = Integer.parseInt(scnr.nextLine().trim());
                        viewUserLogs(logUserId);
                        break;
                    case "3":
                        System.out.print("Enter User ID to view payment records: ");
                        int paymentUserId = Integer.parseInt(scnr.nextLine().trim());
                        List<Payment> payments = paymentService.getPaymentsByUserId(paymentUserId);
                        if (payments.isEmpty()) {
                            System.out.println("No payment records found for User ID: " + paymentUserId);
                        } else {
                            for (Payment payment : payments) {
                                System.out.println("Payment ID: " + payment.getPaymentid() + ", Amount: " + payment.getAmount());
                            }
                        }
                        break;
                    case "4":
                        System.out.println("Exiting admin session. Returning to main menu...");
                        return true;
                    default:
                        System.out.println("Invalid option. Please choose again.");
                        break;
                }
            } while (!option.equals("4"));
        } else {
            System.out.println("Welcome, " + user.getUsername());
        }
        return true;
    }
    
    private void viewUserLogs(int userId) {
        // This method assumes logs are available and associated with user IDs.
        System.out.println("Displaying logs for User ID: " + userId);
        // Here, you would fetch and display the logs from wherever they are stored.
        // Since this is a simulation, we'll just note the action:
        logger.info("Displayed logs for User ID: " + userId);
        // Implement actual log retrieval and display logic here.
    }


    private void resetPassword(Scanner scnr, int userid) {
        System.out.println("Password reset process initiated.");
        System.out.println("Please verify your identity.");
        System.out.print("Enter your Username: ");
        String username = scnr.nextLine().trim();
        System.out.print("Enter your Birthdate (MM/DD/YYYY): ");
        String birthdate = scnr.nextLine().trim();
        System.out.print("Enter your registered Email: ");
        String email = scnr.nextLine().trim();

        User user = findUserById(userid);
        if (user != null && user.getUsername().equals(username) && user.getBirthdate().equals(birthdate) && user.getEmail().equals(email)) {
            System.out.print("Identity verified. Enter your new password: ");
            String newPassword = scnr.nextLine().trim();
            System.out.print("Re-enter your new password: ");
            String confirmNewPassword = scnr.nextLine().trim();

            if (newPassword.equals(confirmNewPassword)) {
                passwordHashes.put(userid, hashPassword(newPassword));
                logger.info("Password for User " + userid + " has been reset successfully.");
                System.out.println("Password has been reset successfully.");
            } else {
                System.out.println("Passwords do not match. Please try again.");
            }
        } else {
            System.out.println("Identity verification failed. Please contact support.");
        }
    }

    


    // 관리자 기능: 등록된 모든 사용자의 정보를 출력합니다.
    private void displayAllUsers() {
        System.out.println("\nAll registered users:");
        for (User user : users) {
            System.out.println("User ID: " + user.getUserid() + ", Username: " + user.getUsername() + ", Email: " + user.getEmail());
        }
    }

    private void displayUserPayments(int userId) {
        List<Payment> payments = paymentService.getPaymentsByUserId(userId);
        if (payments.isEmpty()) {
            System.out.println("No payment records found for User ID: " + userId);
        } else {
            for (Payment payment : payments) {
                System.out.println("Payment ID: " + payment.getPaymentid() + ", Amount: " + payment.getAmount());
            }
        }
    }   
}
