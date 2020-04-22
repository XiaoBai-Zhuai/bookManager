package com.gydx.bookManager.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
public class SendMailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String postName;

    @Async("taskExecutor")
    public void sendMail(String nickname, String receiver, String code) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("重置密码");
        helper.setText("尊敬的<b>" + nickname + "</b>，您好：<br/><br/>" + "&nbsp;&nbsp;&nbsp;&nbsp;" +
                "验证码为<b>" + code + "</b>。<br/>&nbsp;&nbsp;&nbsp;&nbsp;请在<b>10分钟</b>之内完成密码重置，" +
                "否则过期则验证码无效！（请勿泄露此验证码）如非本人操作，请忽略该邮件。", true);
        helper.setFrom(new InternetAddress(postName, "管理员", "UTF-8"));
        helper.setTo(receiver);

        javaMailSender.send(mimeMessage);
    }

}
