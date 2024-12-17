import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeEncoder {

    public static void main(String[] args) {
        // 基准时间
        LocalDateTime baseTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0);
        // 目标时间
        LocalDateTime targetTime = LocalDateTime.of(2024, 10, 5, 6, 26, 17, 440 * 1_000_000);

        // 计算时间差（以毫秒为单位）
        long millis = targetTime.toInstant(ZoneOffset.UTC).toEpochMilli() - 
                      baseTime.toInstant(ZoneOffset.UTC).toEpochMilli();

        // 将时间差转换为6字节
        byte[] timeBytes = encodeTimeTo6Bytes(millis);

        // 打印结果
        for (byte b : timeBytes) {
            System.out.printf("0x%02X ", b);
        }
    }

    /**
     * 将毫秒时间差编码为6字节数组
     * @param millis 毫秒时间差
     * @return 6字节数组
     */
    public static byte[] encodeTimeTo6Bytes(long millis) {
        if (millis < 0 || millis > 0xFFFFFFFFFFFFL) {
            throw new IllegalArgumentException("时间差超出6字节表示范围");
        }
        
        ByteBuffer buffer = ByteBuffer.allocate(8); // 分配8字节缓冲区
        buffer.putLong(millis); // 将时间差写入缓冲区
        byte[] fullBytes = buffer.array(); // 获取8字节数组

        // 截取后6字节
        byte[] timeBytes = new byte[6];
        System.arraycopy(fullBytes, 2, timeBytes, 0, 6);

        return timeBytes;
    }
}
