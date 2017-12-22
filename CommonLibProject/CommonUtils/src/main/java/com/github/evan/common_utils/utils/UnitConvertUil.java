package com.github.evan.common_utils.utils;

import java.math.BigDecimal;

/**
 * Created by Evan on 2017/12/22.
 */

public class UnitConvertUil {

    public enum FileSizeUnit {
        BYTE, KB, MB, GB, TB
    }

    public static double byte2Kb(long value, int decimalCountToSave) {
        if (value <= 0)
            return 0;
        double d = value / 1024;
        BigDecimal bigDecimal = new BigDecimal(d).setScale(decimalCountToSave, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static double byte2Mb(long value, int decimalCountToSave) {
        if (value <= 0)
            return 0;
        double d = value / 1024 / 1024;
        BigDecimal bigDecimal = new BigDecimal(d).setScale(decimalCountToSave, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static double byte2Gb(long value, int decimalCountToSave) {
        if (value <= 0)
            return 0;
        double d = value / 1024 / 1024 / 1024;
        BigDecimal bigDecimal = new BigDecimal(d).setScale(decimalCountToSave, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static double byte2Tb(long value, int decimalCountToSave) {
        if (value <= 0)
            return 0;
        double d = value / 1024 / 1024 / 1024 / 1024;
        BigDecimal bigDecimal = new BigDecimal(d).setScale(decimalCountToSave, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    public static void byte2MaxUnit(long value, int decimalCountToSave, Object[] result) {
        if (value <= 0)
            return;

        if (null == result || result.length != 2) {
            return;
        }

        double returnValue = 0;
        returnValue = byte2Tb(value, decimalCountToSave);
        if (returnValue <= 0) {
            returnValue = byte2Gb(value, decimalCountToSave);
            if (returnValue <= 0) {
                returnValue = byte2Mb(value, decimalCountToSave);
                if (returnValue <= 0) {
                    returnValue = byte2Kb(value, decimalCountToSave);
                    if (returnValue <= 0) {
                        returnValue = value;
                        result[1] = FileSizeUnit.BYTE;
                    } else {
                        result[1] = FileSizeUnit.KB;
                    }
                } else {
                    result[1] = FileSizeUnit.MB;
                }
            } else {
                result[1] = FileSizeUnit.GB;
            }
        } else {
            result[1] = FileSizeUnit.TB;
        }

        result[0] = returnValue;
    }



}
