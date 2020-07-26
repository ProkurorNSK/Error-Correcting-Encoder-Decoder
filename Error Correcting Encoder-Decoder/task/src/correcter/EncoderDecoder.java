package correcter;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public interface EncoderDecoder {

    @NotNull
    default byte[] getBytes(String path) {
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            return fileInputStream.readAllBytes();
        } catch (IOException ignored) {
            return new byte[0];
        }
    }

    default void WriteBytes(byte[] bytes, String path) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path)) {
            fileOutputStream.write(bytes);
        } catch (IOException ignored) {
        }
    }

    @NotNull
    default byte[] getErrors(@NotNull byte[] bytes) {
        Random random = new Random();
        for (int i = 0; i < bytes.length; i++) {
            int position = random.nextInt(7);
            bytes[i] ^= 1 << position;
        }
        WriteBytes(bytes, "received.txt");
        return bytes;
    }
    @NotNull
    byte[] getEncode(byte[] bytes);

    @NotNull
    byte[] getDecode(byte[] bytes);
}
