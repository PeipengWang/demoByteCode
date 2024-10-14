/**
 * 补码获取
 */
public class ComplementExample {
    public static void main(String[] args) {
        int number = -65535;

        // 只保留低 8 位
        int mask8Bit = 0xFF; // 8位掩码
        int complement8 = number & mask8Bit;

        // 显示 8 位补码表示
        String binaryString = Integer.toBinaryString(complement8);
        System.out.println("原数: " + number);
        System.out.println("8位补码表示: " + binaryString);

        int mask24Bit = 0xFFFFFF; // 24 位掩码

        int complement24 = number & mask24Bit;

        String binary24String = Integer.toBinaryString(complement24);

        System.out.println(binary24String.length());
        System.out.println("24位补码表示：" + binary24String);

    }
}
