package com.Harsh.SocialMedia.user_service.utils;

import lombok.Getter;
import org.mindrot.jbcrypt.BCrypt;


public class PasswordUtils {

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String storedHash) {
        return BCrypt.checkpw(plainPassword, storedHash);
    }
}
