package com.urlShortener.shortener;

/**
 * Created by tbuldina on 16/03/2018.
 */
public class UrlShortener {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();

    /**
     * Convert id to base62 string
     * @param num
     * @return short url
     */
    public static String encode(int num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(ALPHABET.charAt(num % BASE));
            num /= BASE;
        }
        return sb.reverse().toString();
    }
}