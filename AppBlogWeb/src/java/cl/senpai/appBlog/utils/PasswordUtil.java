package cl.senpai.appBlog.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    private static final Random random = new SecureRandom();
    private static final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int iterations = 10000;
    private static final int keyLength = 256;
    
    public static String getSalt(int length){
        StringBuilder returnValue = new StringBuilder(length);
        
        for(int i = 0; i < length; i++){
            returnValue.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        
        return new String(returnValue);
    }
    
    public static byte[] hash(char[] password, byte salt[]){
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
        Arrays.fill(password, Character.MIN_VALUE);
        
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error al al hashear contraseña" + e.getMessage()); 
        }finally{
            spec.clearPassword();
        }
    }
    
    public static String generateSecurePassword(String password, String salt){
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        returnValue = Base64.getEncoder().encodeToString(securePassword);
        return returnValue;
    }
    
    public static boolean verifyUserPassword(String providedPasswpord, String securedPassword, String salt){
        boolean returnValue = false;
        //Generar nueva contraseña segura con el mismo salt
        String newSecurePassword = generateSecurePassword(providedPasswpord, salt);
        //Verificar si las 2 contraseñas son iguales
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);
        return returnValue;
    }
}