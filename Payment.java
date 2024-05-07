
public class Payment {
    private static int nextPaymentId = 1; // 정적 변수로 Payment ID 자동 할당을 위한 카운터 시작
    private int paymentid;
    private int userid;
    private String creditcardnumber;
    private String expiryDate;
    private String cardCVV;
    private String phoneNumber;
    private String cardholderName;
    private double amount; // 결제 금액을 저장할 변수 추가


    // Default constructor
    public Payment() {
        // 기본 생성자에서 특별히 초기화할 필드가 없습니다.
    }

    // 매개변수가 있는 생성자
    public Payment(int userid, String creditcardnumber, String expiryDate, String cardCVV, String phoneNumber, String cardholderName, double amount) {
        this.userid = userid;
        this.creditcardnumber = validateCreditCardNumber(creditcardnumber);
        this.expiryDate = expiryDate;
        this.cardCVV = cardCVV;
        this.phoneNumber = phoneNumber;
        this.cardholderName = cardholderName;
        this.amount = amount; // 생성자를 통해 금액 초기화
        this.paymentid = nextPaymentId++;  // 결제가 성공적으로 생성되었을 때 ID 할당
    }

    // 신용카드 번호 검증 메서드
    private String validateCreditCardNumber(String number) {
        if (!number.matches("\\d{16}")) {  // 입력값이 숫자 16자리인지 검사
            throw new IllegalArgumentException("Credit card number must be exactly 16 digits.");
        }
        return number;
    }

    // Getter and Setter Methods
    public void setPaymentid(int paymentid) {
        this.paymentid = paymentid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setCreditcardnumber(String creditcardnumber) {
        this.creditcardnumber = creditcardnumber;
    }

    public int getPaymentid() {
        return paymentid;
    }

    public int getUserid() {
        return userid;
    }

    public String getCreditcardnumber() {
        return creditcardnumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCardholderName() {
        return cardholderName;
    }
    
    public double getAmount() {
        return amount;
    }
}
