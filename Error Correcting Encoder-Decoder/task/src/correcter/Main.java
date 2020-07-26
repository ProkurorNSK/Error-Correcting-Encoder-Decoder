package correcter;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EncoderDecoder encoderDecoder = new BitEncoderDecoder();
        byte[] bytes;

        System.out.print("Write a mode: ");
        String variant = scanner.nextLine();
        switch (variant) {
            case "encode":
                bytes = encoderDecoder.getBytes("send.txt");
//                print(bytes);
                byte[] bytesEncode = encoderDecoder.getEncode(bytes);
//                print(bytesEncode);
                break;
            case "send":
                bytes = encoderDecoder.getBytes("encoded.txt");
//                print(bytes);
                byte[] bytesError = encoderDecoder.getErrors(bytes);
//                print(bytesError);
                break;
            case "decode":
                bytes = encoderDecoder.getBytes("received.txt");
//                print(bytes);
                byte[] bytesDecode = encoderDecoder.getDecode(bytes);
//                print(bytesDecode);
                break;
        }
    }

    private static void print(byte[] bytes) {
        for (byte a: bytes) {
            System.out.print(Integer.toHexString(a) + " ");
        }
        System.out.println();
    }
}
