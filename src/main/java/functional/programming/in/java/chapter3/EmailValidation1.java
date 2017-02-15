package functional.programming.in.java.chapter3;

import java.util.function.Function;
import java.util.regex.Pattern;

public class EmailValidation1 {
    static Pattern emailPattern = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

    static Function<String, Result> emailChecker = s -> {
        if (s == null) {
            return new Result.Failure("email must not be null");
        } else if (s.length() == 0) {
            return new Result.Failure("email must not be empty");
        } else if (emailPattern.matcher(s).matches()) {
            return new Result.Success();
        } else {
            return new Result.Failure("email " + s + " is invalid.");
        }
    };

    public static void main(String... args) {
        validate("this.is@my.email").run();
        validate(null).run();
        validate("").run();
        validate("john.doe@acme.com").run();
    }

    private static void logError(String s) {
        System.err.println("Error message logged: " + s);
    }

    private static void sendVerificationMail(String s) {
        System.out.println("Mail sent to " + s);
    }

    static Runnable validate(String s) {
        Result result = emailChecker.apply(s);
        if (result instanceof Result.Success) {
            return () -> sendVerificationMail(s);
        } else {
            return () -> logError(((Result.Failure) result).getErrorMessage());
        }
    }
}
