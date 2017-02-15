package functional.programming.in.java.chapter3;

import java.util.function.Function;
import java.util.regex.Pattern;

public class EmailValidation2 {
    static Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    static Function<String, Result2<String>> emailChecker = s -> {
        if (s == null) {
            return Result2.failure("email must not be null");
        } else if (s.length() == 0) {
            return Result2.failure("email must not be empty");
        } else if (emailPattern.matcher(s).matches()) {
            return Result2.success(s);
        } else {
            return Result2.failure("email " + s + " is invalid.");
        }
    };

    public static void main(String... args) {
        Effect<String> success = EmailValidation2::sendVerificationMail;
        Effect<String> failure = EmailValidation2::logError;
        emailChecker.apply("this.is@my.email").bind(success, failure);
        emailChecker.apply(null).bind(success, failure);
        emailChecker.apply("").bind(success, failure);
        emailChecker.apply("john.doe@cme.com").bind(success, failure);
    }

    private static void logError(String s) {
        System.err.println("Error message logged: " + s);
    }

    private static void sendVerificationMail(String s) {
        System.out.println("Mail sent to " + s);
    }
}
