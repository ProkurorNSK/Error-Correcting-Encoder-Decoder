package correcter;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;

public interface EncoderDecoder {

    @NotNull
    default byte[] getBytes(String path) {
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            return fileInputStream.readAllBytes();
        } catch (IOException ignored) {
            return new byte[0];
        }
    }

    @NotNull
    byte[] getEncode(byte[] bytes);

    @NotNull
    byte[] getErrors(byte[] bytes);

    @NotNull
    byte[] getDecode(byte[] bytes);
}
