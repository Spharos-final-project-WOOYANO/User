package shparos.user.application;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import shparos.user.global.config.redis.RedisUtil;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    // 회원가입 이메일 전송
    @Override
    public void sendCheckEmail(String name, String email) {

        // 이미 이메일이 Redis에 등록되어 있는 경우는 삭제
        if (redisUtil.existData(email)) {
            redisUtil.deleteData(email);
        }

        // 랜덤 숫자 4자리 설정
        Random random = new Random();
        String authCode = Integer.toString(random.nextInt(9000)+1000);

        // 메일 작성
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // use multipart (true)

            // 메일제목 설정
            mimeMessageHelper.setSubject("Wooyano 회원가입 이메일 인증을 위한 메일 전송");

            // 메일내용 설정
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
                            "   <b style=\" font-size : 40px ; color: {$point_color};\">메일 인증 번호 : "+authCode+"</b>" +
                            "   <br/><br/><br/></p>\n" +
                            "   <div style=\"border-top: 1px solid #DDD; padding: 5px;\">\n" +
                            "      <p style=\"font-size: 13px; line-height: 21px; color: #555;\">\n" +
                            "         만약 인증번호가 정상적으로 보이지않거나 인증이 지속적으로 실패된다면 고객센터로 연락주시면 감사하겠습니다.<br />\n" +
                            "      </p>\n" +
                            "   </div>\n" +
                            "</div>",
                    true);

            // 메일 발송인 설정 - application.yml의 email과 동일해야함
            mimeMessageHelper.setFrom("so6918@naver.com");

            // 메일 수신인 설정
            mimeMessageHelper.setTo(email);

            // 레디스에 저장
            redisUtil.setDataExpire(email, authCode, 180); // 유효시간 3분

            // 메일 전송
            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 비밀번호 재설정시 인증 이메일 전송
    @Override
    public void sendPasswordChangeAuthMail(String name, String email) {

        // 이미 이메일이 Redis에 등록되어 있는 경우는 삭제
        if (redisUtil.existData(email)) {
            redisUtil.deleteData(email);
        }

        // 랜덤 숫자 4자리 설정
        Random random = new Random();
        String authCode = Integer.toString(random.nextInt(9000)+1000);

        // 메일 작성
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // use multipart (true)

            // 메일제목 설정
            mimeMessageHelper.setSubject("Wooyano 비밀번호 변경을 위한 이메일 인증 메일 전송");

            // 메일내용 설정
            mimeMessageHelper.setText("<div style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid {$point_color}; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">\n" +
                            "   <h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">\n" +
                            "      <span style=\"font-size: 15px; margin: 0 0 10px 3px;\">Wooyano</span><br />\n" +
                            "      <span style=\"color: {$point_color};\">메일인증</span> 안내입니다.\n" +
                            "   </h1>\n" +
                            "   <p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">\n" +
                            "      안녕하세요."+name+"님<br />\n" +
                            "      아래 <b style=\"color: {$point_color};\">'메일 인증 번호'</b>를 입력하여 메일인증을 완료해 주세요.<br />\n" +
                            "      감사합니다.<br/><br/>" +
                            "   </p>\n" +
                            "   <b style=\" font-size : 40px ; color: {$point_color};\">메일 인증 번호 : "+authCode+"</b>" +
                            "   <br/><br/><br/></p>\n" +
                            "   <div style=\"border-top: 1px solid #DDD; padding: 5px;\">\n" +
                            "      <p style=\"font-size: 13px; line-height: 21px; color: #555;\">\n" +
                            "         만약 인증번호가 정상적으로 보이지않거나 인증이 지속적으로 실패된다면 고객센터로 연락주시면 감사하겠습니다.<br />\n" +
                            "      </p>\n" +
                            "   </div>\n" +
                            "</div>",
                    true);

            // 메일 발송인 설정 - application.yml의 email과 동일해야함
            mimeMessageHelper.setFrom("so6918@naver.com");

            // 메일 수신인 설정
            mimeMessageHelper.setTo(email);

            // 레디스에 저장
            redisUtil.setDataExpire(email, authCode, 180); // 유효시간 3분

            // 메일 전송
            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 이메일 코드 확인
    @Override
    public Boolean certifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);
        if (codeFoundByEmail == null) {
            return false;
        }
        return codeFoundByEmail.equals(code);
    }
}
