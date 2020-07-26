package correcter;

import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

public class HammingEncoderDecoder implements EncoderDecoder {
    @NotNull
    @Override
    public byte[] getEncode(@NotNull byte[] bytes) {

        BitSet bitSet = BitSet.valueOf(bytes);
        BitSet bitResult = new BitSet();

        for (int i = 0; i < bytes.length * 2; i++) {
            int index1 = 7 - (i * 4) % 8 + ((i * 4) / 8) * 8;
            int index2 = 7 - (i * 4 + 1) % 8 + ((i * 4 + 1) / 8) * 8;
            int index3 = 7 - (i * 4 + 2) % 8 + ((i * 4 + 2) / 8) * 8;
            int index4 = 7 - (i * 4 + 3) % 8 + ((i * 4 + 3) / 8) * 8;
            bitResult.set(i * 8 + 7, bitSet.get(index1) ^ bitSet.get(index2) ^ bitSet.get(index4));
            bitResult.set(i * 8 + 6, bitSet.get(index1) ^ bitSet.get(index3) ^ bitSet.get(index4));
            bitResult.set(i * 8 + 5, bitSet.get(index1));
            bitResult.set(i * 8 + 4, bitSet.get(index2) ^ bitSet.get(index3) ^ bitSet.get(index4));
            bitResult.set(i * 8 + 3, bitSet.get(index2));
            bitResult.set(i * 8 + 2, bitSet.get(index3));
            bitResult.set(i * 8 + 1, bitSet.get(index4));
        }

        byte[] result = bitResult.toByteArray();
        WriteBytes(result, "encoded.txt");
        return result;
    }

    @NotNull
    @Override
    public byte[] getDecode(byte[] bytes) {
        BitSet bitSet = BitSet.valueOf(bytes);
        BitSet bitResult = new BitSet();

        for (int count = 0, i = 0; i < bytes.length; i++, count += 4) {
            boolean bit1 = bitSet.get(i * 8 + 7);
            boolean bit2 = bitSet.get(i * 8 + 6);
            boolean bit3 = bitSet.get(i * 8 + 5);
            boolean bit4 = bitSet.get(i * 8 + 4);
            boolean bit5 = bitSet.get(i * 8 + 3);
            boolean bit6 = bitSet.get(i * 8 + 2);
            boolean bit7 = bitSet.get(i * 8 + 1);
            int indexError = 0;
            if (bit1 != bit3 ^ bit5 ^ bit7) {
                indexError += 1;
            }
            if (bit2 != bit3 ^ bit6 ^ bit7) {
                indexError += 2;
            }
            if (bit4 != bit5 ^ bit6 ^ bit7) {
                indexError += 4;
            }
            if (indexError == 3) bit3 = !bit3;
            if (indexError == 5) bit5 = !bit5;
            if (indexError == 6) bit6 = !bit6;
            if (indexError == 7) bit7 = !bit7;
            bitResult.set((count / 8 + 1) * 8 - (1 + count % 8), bit3);
            bitResult.set(((count + 1) / 8 + 1) * 8 - (1 + (count + 1) % 8), bit5);
            bitResult.set(((count + 2) / 8 + 1) * 8 - (1 + (count + 2) % 8), bit6);
            bitResult.set(((count + 3) / 8 + 1) * 8 - (1 + (count + 3) % 8), bit7);
        }

        byte[] result = bitResult.toByteArray();
        WriteBytes(result, "decoded.txt");
        return result;
    }
}
