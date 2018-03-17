package com.evm.project.schapp.helper;

public class GradeHelper {

    public static String calculate(double gr) {
        if ((gr <= 73)&&(gr >= 60)) {
            return "5.0";
        } else if ((gr <= 75.49)&&(gr >= 74.50)) {
            return "3.0";
        } else if ((gr <= 76.49)&&(gr >=75.50)){
            return "2.9";
        }else if ((gr <= 77.49)&&(gr >=76.50)){
            return "2.8";
        }else if ((gr <= 78.49)&&(gr >=77.50)){
            return "2.7";
        }else if ((gr <= 79.49)&&(gr >=78.50)){
            return "2.6";
        }else if ((gr <= 80.49)&&(gr >= 79.50)){
            return "2.5";
        }else if ((gr <= 81.49)&&(gr >= 80.50)){
            return "2.4";
        }else if ((gr <= 82.49)&&(gr >= 81.50)){
            return "2.3";
        }else if ((gr <= 83.49)&&(gr >= 82.50)){
            return "2.2";
        }else if ((gr <= 84.49)&&(gr >= 83.50)){
            return "2.1";
        }else if ((gr <= 85.49)&&(gr >=84.50)){
            return "2.0";
        }else if ((gr <= 86.49)&&(gr >=85.50)){
            return "1.9";
        }else if ((gr <= 87.49)&&(gr >=86.50)){
            return "1.8";
        }else if ((gr <= 88.49)&&(gr >= 87.50)){
            return "1.7";
        }else if ((gr <= 89.49)&&(gr >=88.50)){
            return "1.6";
        }else if ((gr <= 90.49)&&(gr >=89.5)){
            return "1.5";
        }else if((gr >= 90.50)&&(gr <=92.49)){
            return "1.4";
        }else if((gr >= 92.50)&&(gr <=94.49)){
            return "1.3";
        }else if((gr >= 94.50)&&(gr <=96.49)){
            return "1.2";
        }else if((gr >= 96.50)&&(gr <=98.49)){
            return "1.1";
        }else if((gr >= 98.50)&&(gr <=100)){
            return "1.0";
        }else
            return "0";
    }
}
