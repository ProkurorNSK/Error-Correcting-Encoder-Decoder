package correcter;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class BitEncoderDecoder implements EncoderDecoder {
    @NotNull
    @Override
    public String getText() {

        try (Scanner scanner = new Scanner(new FileInputStream("send.txt"))) {
            return scanner.nextLine();
        } catch (FileNotFoundException ignored) {
            return "";
        }
    }

    @NotNull
    @Override
    public String getDecode(String stringErrors) {
        return stringErrors;
    }

    @NotNull
    @Override
    public String getEncode(String text) {
        return text;
    }

    @NotNull
    @Override
    public String getErrors(String text) {
        Random random = new Random();
        byte[] bytes = text.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            int position = random.nextInt(7);
            bytes[i] ^= 1 << position;
        }
        String result = new String(bytes);
        try (PrintWriter printWriter = new PrintWriter("received.txt");) {
            printWriter.print(result);
        } catch (FileNotFoundException ignored) {}
        return result;
    }
}
