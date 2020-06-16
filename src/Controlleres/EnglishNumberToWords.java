package Controlleres;

import java.text.DecimalFormat;

public class EnglishNumberToWords {

    private static final String[] tensNames = {
        "",
        " عشرة",
        " عشرين",
        " ثلاثون",
        " اربعون",
        " خمسون",
        " ستون",
        " سبعون",
        " ثمانون",
        " تسعون"
    };

    private static final String[] numNames = {
        "",
        " واحد",
        " اثنين",
        " ثلاثة",
        " اربعة",
        " خمسة",
        " ستة",
        " سبعة",
        " ثمانية",
        " تسعة",
        " عشرة",
        " احدى عشر",
        " اثناعشر",
        " ثلاثة عشر",
        " اربعة عشر",
        " خمسة عشر",
        " ستة عشر",
        " سبعة عشر",
        " ثمانية عشر",
        " تسعة عشر"
    };

    private EnglishNumberToWords() {
    }

    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20) {
            soFar = numNames[number % 100];
            number /= 100;
        } else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = "و" + soFar + " " + "و" + tensNames[number % 10];
            number /= 10;
        }
        if (number == 0) {
            return soFar;
        }
        return numNames[number] + " مائة" + soFar;
    }

    public static String convert(long number) {
        // 0 to 999 999 999 999
        if (number == 0) {
            return "صفر";
        }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = " ";
                break;
            case 1:
                tradBillions = convertLessThanOneThousand(billions)
                        + " باليون ";
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions)
                        + " بالايين ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = " ";
                break;
            case 1:
                tradMillions = convertLessThanOneThousand(millions)
                        + " مليون ";
                break;
            default:
                tradMillions = convertLessThanOneThousand(millions)
                        + " ملايين ";
        }
        result = result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = " ";
                break;
            case 1:
                tradHundredThousands = "الف ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                        + " الاف ";
        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    /**
     * testing
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("*** " + EnglishNumberToWords.convert(5115));
    }
}
