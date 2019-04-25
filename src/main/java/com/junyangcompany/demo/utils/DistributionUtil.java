package com.junyangcompany.demo.utils;

public class DistributionUtil {

    public double NORMSDIST(double a)
    {
        double p = 0.2316419;
        double b1 = 0.31938153;
        double b2 = -0.356563782;
        double b3 = 1.781477937;
        double b4 = -1.821255978;
        double b5 = 1.330274429;

        double x = Math.abs(a);
        double t = 1/(1+p*x);

        double val = 1 - (1/(Math.sqrt(2*Math.PI))  * Math.exp(-1*Math.pow(a, 2)/2)) * (b1*t + b2 * Math.pow(t,2) + b3*Math.pow(t,3) + b4 * Math.pow(t,4) + b5 * Math.pow(t,5) );

        if ( a < 0 )
        {
            val = 1- val;
        }

        return val;
    }

//    public static void main(String[] args) {
//        DistributionUtil util = new DistributionUtil();
//        System.out.println(util.NORMSDIST(2.95));
//        System.out.println(util.NORMSDIST(0));
//        System.out.println(util.NORMSDIST(-1));
//    }
}
