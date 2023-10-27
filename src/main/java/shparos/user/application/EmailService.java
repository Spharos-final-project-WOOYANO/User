package shparos.user.application;

public interface EmailService {

    void sendCheckEmail(String name, String email);
    void sendPasswordChangeAuthMail(String name, String email);
    Boolean certifyEmailCode(String email, String code);


}
