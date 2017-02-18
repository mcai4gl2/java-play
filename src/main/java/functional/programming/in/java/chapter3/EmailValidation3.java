package functional.programming.in.java.chapter3;

import java.util.function.Function;
import java.util.regex.Pattern;
import static functional.programming.in.java.chapter3.Case.*;
import static functional.programming.in.java.chapter3.Result2.*;

public class EmailValidation3 {
    static Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    static Function<String, Result2<String>> emailChecker = s -> {
        if (s == null) {
            return failure("email must not be null");
        } else if (s.length() == 0) {
            return failure("email must not be empty");
        } else if (emailPattern.matcher(s).matches()) {
            return success(s);
        } else {
            return failure("email " + s + " is invalid.");
        }
    };

    static Function<String, Result2<String>> emailChecker2 = s -> match(
            case_(() -> Result2.<String>success(s)),
            case_(() -> s == null, () -> failure("email must not be null")),
            case_(() -> s.length() == 0, () -> failure("email must not be empty")),
            case_(() -> !emailPattern.matcher(s).matches(), () -> failure("email " + s + " must not be empty"))
    );

    public static void main(String... args) {
        Effect<String> success = EmailValidation3::sendVerificationMail;
        Effect<String> failure = EmailValidation3::logError;
        emailChecker2.apply("this.is@my.email").bind(success, failure);
        emailChecker2.apply(null).bind(success, failure);
        emailChecker2.apply("").bind(success, failure);
        emailChecker2.apply("john.doe@cme.com").bind(success, failure);
    }

    private static void logError(String s) {
        System.err.println("Error message logged: " + s);
    }

    private static void sendVerificationMail(String s) {
        System.out.println("Mail sent to " + s);
    }
}
