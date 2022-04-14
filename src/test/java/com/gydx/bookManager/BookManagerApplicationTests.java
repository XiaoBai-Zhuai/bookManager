package com.gydx.bookManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;

@SpringBootTest
class BookManagerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sendUsername;

    public String createCode() {
        int code = (int)((Math.random() * 9 + 1) * 100000);
        return String.valueOf(code);
    }

    @Test
    public void sendMail() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //标题
        mailMessage.setSubject("注意");
        //内容
        mailMessage.setText("有内鬼，终止交易");
        //发送人
        mailMessage.setFrom(sendUsername);
        //收件人
        mailMessage.setTo("xxxxxx@qq.com");
        javaMailSender.send(mailMessage);
    }

    @Test
    public void sendMail2() throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("注意");
        helper.setText("<b style='color:blue'>xxxxxxxxx</b>", true);
        helper.setFrom(new InternetAddress(sendUsername, "无情的邮件发送机器", "UTF-8"));
        helper.setTo("xxxxxx@qq.com");
        javaMailSender.send(mimeMessage);
    }

    @Test
    public void sendMail3() throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("重置密码");
        helper.setText("尊敬的<b>" + "xx" + "</b>，您好：<br/><br/>" + "&nbsp;&nbsp;&nbsp;&nbsp;" +
                "您的验证码为<b>" + createCode() + "</b>。<br/>&nbsp;&nbsp;&nbsp;&nbsp;请在<b>10分钟</b>" +
                "之内完成密码重置，否则过期则验证码无效！", true);
        helper.setFrom(new InternetAddress(sendUsername, "管理员", "UTF-8"));
        helper.setTo("xxxxx@163.com");
//        helper.setTo("xxxxxxx@qq.com");

        javaMailSender.send(mimeMessage);
    }

}
