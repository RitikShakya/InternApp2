
package com.company.internapp2;
import java.util.ArrayList;
import java.util.List;



public class TestCases {

    Validations validations = new Validations();
    public static void main(String[] args) {


        TestCases testCases = new TestCases();
        //List<String> emails = new ArrayList<>();
        String arr[] = new String[]{"user.name@domain.com",
                "user-name@domain.com",
                "username@domain.co.in.",
                "username@domain.co.in",
                "user_name@domain.com",".user.name@domain.com",
                "user-name@domain.com.",
                "username#@domain.co.in"};

        for(String e: arr){


            boolean result =testCases.validations.validemail(e);
            System.out.print(" "+result);

        }
        System.out.println();

        String nums[] = new String[]{"9818623375","03566","09818623375","919818623375","91556661"};

        for(String e : nums){
            boolean result = testCases.validations.validnum(e);
            System.out.print(" "+result);

        }
        System.out.println();

        String users[] = new String[]{" ","shakya","shdggduiashdjiosjiojdijisdiashu","123","skdksajbad"};

        for(String e : users){
            boolean result = testCases.validations.validusername(e);
            System.out.print(" "+result);
        }


        System.out.println();

        String passes[] = new String[]{"SSW@09sss","sS@09sss","sSW@09sss","sSW@0sss","sSW@09shakya"};

        for(String e : passes){
            boolean result = testCases.validations.validpass(e);
            System.out.print(" "+result);
        }
    }



}




