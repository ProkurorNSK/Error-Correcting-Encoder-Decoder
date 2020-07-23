package correcter;

public class Main {
    public static void main(String[] args) {

        EncoderDecoder encoderDecoder = new BitEncoderDecoder();

        String text = encoderDecoder.getText();
        System.out.println(text);

        String stringEncode = encoderDecoder.getEncode(text);
        System.out.println(stringEncode);

        String stringErrors = encoderDecoder.getErrors(stringEncode);
        System.out.println(stringErrors);

        String stringDecode = encoderDecoder.getDecode(stringErrors);
        System.out.println(stringDecode);
    }

}
