package gc.related;

import java.util.ArrayList;
import java.util.List;

public class SubstringMemoryLeak {
    // Copied from book: http://item.jd.com/11099999.html and page 69
    public static void main(String args[]) {
        List<String> handler = new ArrayList<>();

        // Neither blows up because substring code changed to make a copy of the char array now
        for (int i = 0; i < 1000; i++) {
            HugeString h = new HugeString();
            //ImprovedHugeStr h = new ImprovedHugeStr();
            handler.add(h.getSubString(1, 5));
        }
    }

    static class HugeString {
        private String str = new String(new char[100000]);

        public String getSubString(int begin, int end) {
            return str.substring(begin, end);
        }
    }

    static class ImprovedHugeStr {
        private String str = new String(new char[100000]);

        public String getSubString(int begin, int end) {
            return new String(str.substring(begin, end));
        }
    }
}
