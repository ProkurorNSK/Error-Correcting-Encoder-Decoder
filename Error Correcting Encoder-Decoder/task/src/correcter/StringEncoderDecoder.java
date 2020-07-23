package correcter;

import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.Scanner;

public class StringEncoderDecoder implements EncoderDecoder {
    @Override
    @NotNull
    public String getText() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    @NotNull
    public String getDecode(String stringErrors) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringErrors.length(); i += 3) {
            char a = stringErrors.charAt(i);
            char b = stringErrors.charAt(i + 1);
            char c = stringErrors.charAt(i + 2);
            if (a == b) {
                stringBuilder.append(a);
            } else {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString();
    }

    @Override
    @NotNull
    public String getEncode(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            stringBuilder.append(text.charAt(i)).append(text.charAt(i)).append(text.charAt(i));
        }
        return stringBuilder.toString();
    }

    @Override
    @NotNull
    public String getErrors(String text) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(text);
        for (int i = 0; i < text.length() / 3; i++) {
            int index = i * 3 + random.nextInt(3);
            char newCh;
            do {
                int rand = random.nextInt(63);
                if (rand == 0) {
                    newCh = ' ';
                } else if (rand > 0 && rand <= 10) {
                    newCh = (char) (rand + 48 - 1);
                } else if (rand > 10 && rand <= 36) {
                    newCh = (char) (rand + 65 - 11);
                } else {
                    newCh = (char) (rand + 97 - 37);
                }
            } while (stringBuilder.charAt(index) == newCh);
            stringBuilder.setCharAt(index, newCh);
        }
        return stringBuilder.toString();
    }
}
