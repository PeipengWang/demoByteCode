public class ByteFunc {
    public static byte[] IntToByteArrayBigEnd(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value
        };
    }

    public static byte[] ShortToByteArrayBigEnd(short value) {
        return new byte[]{
                (byte) (value >> 8),
                (byte) value
        };
    }

    public static int ByteArray2IntBigEnd(byte[] bytes) {
        return (bytes[0] & 0xFF) << 24 |
               (bytes[1] & 0xFF) << 16 |
               (bytes[2] & 0xFF) << 8 |
               (bytes[3] & 0xFF);
    }

    public static short ByteArray2ShortBigEnd(byte[] bytes) {
        return (short) ((bytes[0] & 0xFF) << 8 | (bytes[1] & 0xFF));
    }

    public static byte[] ExtractByteArray(byte[] src, int start, int length) {
        byte[] result = new byte[length];
        System.arraycopy(src, start, result, 0, length);
        return result;
    }

    public static String ByteArray2HexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("0x%02X ", b));
        }
        return sb.toString().trim();
    }
}
