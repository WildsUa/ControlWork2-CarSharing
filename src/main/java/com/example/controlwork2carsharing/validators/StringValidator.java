package com.example.controlwork2carsharing.validators;

import java.util.regex.Pattern;

public class StringValidator {
    final private String[] phonePatterns = {
            "^\\+38 \\([0-9]{3}\\) [0-9]{3}-[0-9]{2}-[0-9]{2}$",
            "^\\([0-9]{3}\\) [0-9]{3}-[0-9]{2}-[0-9]{2}$",
            "^[0-9]{10}$"};
    final private String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public boolean validateEmail(String string){
        Pattern regexPatter = Pattern.compile(emailPattern);
        return  regexPatter.matcher(string).matches();
    }

    public boolean validatePhone(String string){
        boolean result = false;
        for (String pattern:phonePatterns) {
            Pattern regexPatter = Pattern.compile(pattern);
            if (regexPatter.matcher(string).matches())
                result = true;
        }
        return  result;
    }

}
