package correcter;

import org.jetbrains.annotations.NotNull;

public interface EncoderDecoder {
    @NotNull
    String getText();

    @NotNull
    String getDecode(String stringErrors);

    @NotNull
    String getEncode(String text);

    @NotNull
    String getErrors(String text);
}
