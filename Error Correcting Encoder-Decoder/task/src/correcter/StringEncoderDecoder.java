package correcter;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class StringEncoderDecoder implements EncoderDecoder {
    @Override
    @NotNull
    public byte[] getEncode(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            stringBuilder.append(aByte).append(aByte).append(aByte);
        }
        return stringBuilder.toString().getBytes();
    }

    @Override
    @NotNull
    public byte[] getErrors(@NotNull byte[] bytes) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(new String(bytes));
        for (int i = 0; i < bytes.length / 3; i++) {
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
        return stringBuilder.toString().getBytes();
    }

    @Override
    @NotNull
    public byte[] getDecode(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i += 3) {
            byte a = bytes[i];
            byte b = bytes[i + 1];
            byte c = bytes[i + 2];
            if (a == b) {
                stringBuilder.append(a);
            } else {
                stringBuilder.append(c);
            }
        }

        return stringBuilder.toString().getBytes();
    }
}
