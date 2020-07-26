package correcter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EncoderDecoder encoderDecoder = new HammingEncoderDecoder();

        System.out.print("Write a mode: ");
        String variant = scanner.nextLine();
        switch (variant) {
            case "encode":
                encoderDecoder.getEncode(encoderDecoder.getBytes("send.txt"));
                break;
            case "send":
                encoderDecoder.getErrors(encoderDecoder.getBytes("encoded.txt"));
                break;
            case "decode":
                encoderDecoder.getDecode(encoderDecoder.getBytes("received.txt"));
                break;
        }
    }
}
