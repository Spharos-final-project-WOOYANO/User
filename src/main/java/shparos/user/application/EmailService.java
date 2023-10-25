package shparos.user.application;

public interface EmailService {

    void sendCheckMail(String name, String email);
    Boolean certifyEmailCode(String email, String code);

}
