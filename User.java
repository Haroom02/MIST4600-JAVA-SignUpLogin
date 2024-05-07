

public class User {
    int userid;  // 사용자 ID
    String username;  // 사용자 이름
    String password;  // 사용자 비밀번호
    String birthdate;  // 사용자 생년월일 (MM/DD/YY 형식)
    String gender;  // 사용자 성별
    String email;  // 사용자 이메일
    int contact;  // 사용자 연락처
    boolean isAdmin;  // 관리자 여부를 나타내는 플래그

    // Default constructor
    public User() {
        userid = 810000000;
        username = "none";
        password = "none";
        birthdate = "00/00";
        gender = "none";
        email = "none";
        contact = 0000000;
        isAdmin = false;  // 기본적으로 사용자는 관리자가 아님
    }

    // Overloaded constructor with admin status
    public User(int userid, String username, String password, String birthdate, 
                String gender, String email, int contact, boolean isAdmin) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
        this.email = email;
        this.contact = contact;
        this.isAdmin = isAdmin;  // 관리자 여부 설정
    }

    // Getters and Setters
    public int getUserid() { return userid; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getBirthdate() { return birthdate; }
    public String getGender() { return gender; }
    public String getEmail() { return email; }
    public int getContact() { return contact; }
    public boolean getIsAdmin() { return isAdmin; }  // 관리자 여부를 반환하는 메소드

    // Setters
    public void setUserid(int userid) {
        String idStr = String.valueOf(userid);
        if (idStr.length() == 9 && idStr.startsWith("81")) {
            this.userid = userid;
        } else {
            throw new IllegalArgumentException("User ID must be 9 digits and start with '81'.");
        }
    }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setBirthday(String birthdate) { this.birthdate = birthdate; }
    public void setGender(String gender) { this.gender = gender; }
    public void setEmail(String email) { this.email = email; }
    public void setContact(int contact) { this.contact = contact; }
    public void setIsAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }  // 관리자 상태 설정

	
    
	

	
	
	
} // class






