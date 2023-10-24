package shparos.user.application;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    @Override
    public void sendCheckMail(String name, String email) {

        // 랜덤 숫자 4자리 설정
        Random random = new Random();
        int keyCode = random.nextInt(10000)+1000;

        // 메일 작성
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // use multipart (true)

            mimeMessageHelper.setSubject("Wooyano 회원가입 이메일 인증을 위한 메일 전송");
//            mimeMessageHelper.setText(name+"님</br><p>"+
//                    " 아래에 표시되는 인증코드 4자리를 입력해주세요</p><p>감사합니다.</p><p><p>"+
//                    "인증 코드:"+keyCode,
//                    true);
            mimeMessageHelper.setText("<div style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid {$point_color}; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">\n" +
                            "   <h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">\n" +
                            "      <span style=\"font-size: 15px; margin: 0 0 10px 3px;\">Wooyano</span><br />\n" +
                            "      <span style=\"color: {$point_color};\">메일인증</span> 안내입니다.\n" +
                            "   </h1>\n" +
                            "   <p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">\n" +
                            "      안녕하세요."+name+"님<br />\n" +
                            "      Wooyano에 가입해 주셔서 진심으로 감사드립니다.<br />\n" +
                            "      아래 <b style=\"color: {$point_color};\">'메일 인증 번호'</b>를 입력하여 메일인증을 완료해 주세요.<br />\n" +
                            "      감사합니다.<br/><br/>" +
                            "   </p>\n" +
                            "   <b style=\" font-size : 40px ; color: {$point_color};\">메일 인증 번호 : "+keyCode+"</b>" +
                            "   <br/><br/><br/></p>\n" +
                            "   <div style=\"border-top: 1px solid #DDD; padding: 5px;\">\n" +
                            "      <p style=\"font-size: 13px; line-height: 21px; color: #555;\">\n" +
                            "         만약 인증번호가 정상적으로 보이지않거나 인증이 지속적으로 실패된다면 고객센터로 연락주시면 감사하겠습니다.<br />\n" +
                            "      </p>\n" +
                            "   </div>\n" +
                            "</div>",
                    true);
            mimeMessageHelper.setFrom("so6918@naver.com");
            mimeMessageHelper.setTo(email);

            javaMailSender.send(mimeMessage);

//            logger.info("MailServiceImpl.sendMail() :: SUCCESS");
        } catch (Exception e) {
//            logger.info("MailServiceImpl.sendMail() :: FAILED");
            e.printStackTrace();
        }

    }
}
