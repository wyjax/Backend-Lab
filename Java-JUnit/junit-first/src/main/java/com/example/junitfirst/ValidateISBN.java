package com.example.junitfirst;

public class ValidateISBN {

    public boolean checkISBN(String ISBN) {
        if (ISBN.length() != 10) {
            throw new NumberFormatException("ISBN의 수가 10자리가 아닙니다.");
        }

        int total = 0;

        for (int i = 0; i < 10; i++) {
            total += ISBN.charAt(i) * (10 - i);
        }

        if (total % 11 == 0) {
            return true;
        }
        return false;
    }
}
