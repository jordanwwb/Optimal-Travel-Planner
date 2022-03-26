/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.email;

import javax.mail.*;

/**
 *
 * @author Anais
 */
public class SMTPAuthenticator extends javax.mail.Authenticator {

    private String smtpAuthUser;
    private String smtpAuthPassword;

    /**
     *
     */
    public SMTPAuthenticator() {
    }

    /**
     *
     * @param smtpAuthUser
     * @param smtpAuthPassword
     */
    public SMTPAuthenticator(String smtpAuthUser, String smtpAuthPassword) {
        this.smtpAuthUser = smtpAuthUser;
        this.smtpAuthPassword = smtpAuthPassword;
    }

    /**
     *
     * @return
     */
    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(smtpAuthUser, smtpAuthPassword);
    }
}
