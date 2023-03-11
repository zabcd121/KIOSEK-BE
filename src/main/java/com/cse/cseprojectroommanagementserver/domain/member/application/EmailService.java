package com.cse.cseprojectroommanagementserver.domain.member.application;

import com.cse.cseprojectroommanagementserver.domain.member.exception.AuthCodeNotEqException;
import com.cse.cseprojectroommanagementserver.domain.member.exception.AuthCodeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.cse.cseprojectroommanagementserver.global.config.RedisConfig.*;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisTemplate redisTemplate;

//    @Value("${spring.mail.username}")
//    private String senderEmail;

    public void sendEmail(String toEmail) throws MessagingException {
        if(redisTemplate.opsForValue().get(EM + toEmail) != null) {
            redisTemplate.delete(EM + toEmail);
        }

        MimeMessage emailForm = createEmailForm(toEmail);
        emailSender.send(emailForm);
    }

    public boolean verifyEmailAuthCode(String toEmail, String code) {
        String authCode = (String) redisTemplate.opsForValue().get(EM + toEmail);
        if(authCode == null) throw new AuthCodeNotFoundException();
        if(!authCode.equals(code)) throw new AuthCodeNotEqException();

        redisTemplate.opsForValue()
                .set(EV + toEmail, "completed", 180000L, TimeUnit.MILLISECONDS); // 인증완료 되었다는것을 저장해둠.

        return true;
    }

    private String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0;i<8;i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 :
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        return key.toString();
    }

    //메일 양식 작성
    private MimeMessage createEmailForm(String email) throws MessagingException {

        String authCode = createCode();//인증 코드 생성
        String setFrom = "kiosek@naver.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toEmail = email; //받는 사람
        String title = "KIOSEK 회원가입 인증 번호"; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(setContext(authCode), "utf-8", "html");

        redisTemplate.opsForValue()
                .set(EM + toEmail, authCode, 180000L, TimeUnit.MILLISECONDS);

        return message;
    }

    private String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context); //mail.html
    }

}