/**
 * 
 */
package fr.guronzan.mediatheque.utils;


/**
 * @author rodriguesgu
 * 
 */
public abstract class DigestUtils {

    /**
     * @param password
     * @return
     */
    public static String hashPassword(final String inPassword) {
        return String.valueOf(org.springframework.util.DigestUtils
                .md5DigestAsHex(inPassword.getBytes()));
    }

    /**
     * @param password
     * @return
     */
    public static String hashPassword(final char[] inPassword) {
        return hashPassword(String.valueOf(inPassword));
    }
}
