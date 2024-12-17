import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeConversion {
    static public Calendar BASE_CALENDAR = Calendar.getInstance();

    static {
        BASE_CALENDAR.set(2020, Calendar.JANUARY, 1);
        BASE_CALENDAR.set(Calendar.HOUR_OF_DAY, 0);
        BASE_CALENDAR.set(Calendar.MINUTE, 0);
        BASE_CALENDAR.set(Calendar.SECOND, 0);
        BASE_CALENDAR.set(Calendar.MILLISECOND, 0);
    }

    public static void main(String[] args) throws Exception {
        // 当前时间
        Date currentDate = new Date();
        System.out.println("当前时间: " + getAStandardFormat().format(currentDate));

        // 转换当前时间到6字节数据
        byte[] timeBytes = convertCurrentTimeToBytes(currentDate);
        System.out.println("6字节时间数据: " + ByteFunc.ByteArray2HexString(timeBytes));

        // 通过 dateTimeSMs2Date 方法解码并验证
        Date decodedDate = dateTimeSMs2Date(timeBytes);
        System.out.println("解码后的时间: " + getAStandardFormat().format(decodedDate));
    }

    /**
     * 将当前时间转换为6字节时间数据
     */
    public static byte[] convertCurrentTimeToBytes(Date date) {
        // 当前时间减去基准时间，计算秒和毫秒差
        long diffMillis = date.getTime() - BASE_CALENDAR.getTimeInMillis();
        int seconds = (int) (diffMillis / 1000); // 秒
        int millis = (int) (diffMillis % 1000);  // 毫秒

        // 转换为6字节：前4字节表示秒，后2字节表示毫秒
        byte[] timeBytes = new byte[6];
        System.arraycopy(ByteFunc.IntToByteArrayBigEnd(seconds), 0, timeBytes, 0, 4);
        System.arraycopy(ByteFunc.ShortToByteArrayBigEnd((short) millis), 0, timeBytes, 4, 2);

        return timeBytes;
    }

    /**
     * 解码6字节时间数据为Date
     */
    public static Date dateTimeSMs2Date(byte[] src) throws Exception {
        if (src.length != 6) {
            throw new Exception("Func dateTimeSMs2Date need a byte[] of length:6. " +
                    "Real Parameter is" + ByteFunc.ByteArray2HexString(src));
        }
        int s = ByteFunc.ByteArray2IntBigEnd(ByteFunc.ExtractByteArray(src, 0, 4));
        int ms = ByteFunc.ByteArray2IntBigEnd(ByteFunc.ExtractByteArray(src, 4, 2));
        Calendar outCalendar = Calendar.getInstance();
        outCalendar.setTime(BASE_CALENDAR.getTime());
        outCalendar.add(Calendar.SECOND, s);
        outCalendar.add(Calendar.MILLISECOND, ms);
        return outCalendar.getTime();
    }

    /**
     * 标准时间格式化
     */
    static private SimpleDateFormat getAStandardFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    }
}
