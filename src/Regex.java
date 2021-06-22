import java.util.regex.Pattern;

public class Regex {

    public static boolean check(String text, String regex) {
        return Pattern.compile(regex).matcher(text).find();
    }

}

