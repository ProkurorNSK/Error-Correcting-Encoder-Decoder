package correcter;

import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Random;

public class BitEncoderDecoder implements EncoderDecoder {
    private void WriteBytes(byte[] bytes, String path) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            fileOutputStream.write(bytes);
        } catch (IOException ignored) {
        }
    }

    @NotNull
    @Override
    public byte[] getEncode(@NotNull byte[] bytes) {

        BitSet bitSet = BitSet.valueOf(bytes);
        BitSet bitResult = new BitSet();

        int indexFirst = 0;
        int indexSecond = 0;
        int indexThird = 0;
        for (int i = 0; i <= (bytes.length * 8) / 3; i++) {
            indexFirst = 7 - (i * 3) % 8 + ((i * 3) / 8) * 8;
            indexSecond = 7 - (i * 3 + 1) % 8 + ((i * 3 + 1) / 8) * 8;
            indexThird = 7 - (i * 3 + 2) % 8 + ((i * 3 + 2) / 8) * 8;
            bitResult.set(i * 8 + 7, bitSet.get(indexFirst));
            bitResult.set(i * 8 + 6, bitSet.get(indexFirst));
            bitResult.set(i * 8 + 5, bitSet.get(indexSecond));
            bitResult.set(i * 8 + 4, bitSet.get(indexSecond));
            bitResult.set(i * 8 + 3, bitSet.get(indexThird));
            bitResult.set(i * 8 + 2, bitSet.get(indexThird));
            bitResult.set(i * 8 + 1, bitSet.get(indexFirst) ^ bitSet.get(indexSecond) ^ bitSet.get(indexThird));
            bitResult.set(i * 8, bitSet.get(indexFirst) ^ bitSet.get(indexSecond) ^ bitSet.get(indexThird));
        }

        byte[] result = bitResult.toByteArray();
  /*      if (!(bitSet.get(indexFirst) | bitSet.get(indexSecond) | bitSet.get(indexThird))) {
            result = Arrays.copyOf(result, result.length + 1);
        }*/
        WriteBytes(result, "encoded.txt");
        return result;
    }

    @NotNull
    @Override
    public byte[] getErrors(@NotNull byte[] bytes) {
        Random random = new Random();
        for (int i = 0; i < bytes.length; i++) {
            int position = random.nextInt(7);
            bytes[i] ^= 1 << position;
        }
        WriteBytes(bytes, "received.txt");
        return bytes;
    }

    @NotNull
    @Override
    public byte[] getDecode(byte[] bytes) {
        BitSet bitSet = BitSet.valueOf(bytes);
        BitSet bitResult = new BitSet();

        for (int count = 0, i = 0; i < bytes.length; i++, count += 3) {
            boolean bit1 = bitSet.get(i * 8 + 7);
            boolean bit2 = bitSet.get(i * 8 + 6);
            boolean bit3 = bitSet.get(i * 8 + 5);
            boolean bit4 = bitSet.get(i * 8 + 4);
            boolean bit5 = bitSet.get(i * 8 + 3);
            boolean bit6 = bitSet.get(i * 8 + 2);
            boolean bit7 = bitSet.get(i * 8 + 1);
            if (bit1 != bit2) {
                bit1 = bit7 ^ bit3 ^ bit5;
            } else if (bit3 != bit4) {
                bit3 = bit7 ^ bit1 ^ bit5;
            } else if (bit5 != bit6) {
                bit5 = bit7 ^ bit1 ^ bit3;
            }
            bitResult.set((count / 8 + 1) * 8 - (1 + count % 8), bit1);
            bitResult.set(((count + 1) / 8 + 1) * 8 - (1 + (count + 1) % 8), bit3);
            bitResult.set(((count + 2) / 8 + 1) * 8 - (1 + (count + 2) % 8), bit5);
        }

        byte[] result = bitResult.toByteArray();
        WriteBytes(result, "decoded.txt");
        return result;
    }
}
