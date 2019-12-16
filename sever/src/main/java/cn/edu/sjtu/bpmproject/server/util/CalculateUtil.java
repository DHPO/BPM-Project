package cn.edu.sjtu.bpmproject.server.util;

import java.math.BigDecimal;

public class CalculateUtil {

    public static double divide(int a, int b){
        return new BigDecimal((float)a/b).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
