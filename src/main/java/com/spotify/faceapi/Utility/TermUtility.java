package com.spotify.faceapi.Utility;

public class TermUtility {
    public static String getTerm(final Integer term) {
        try{
        if (term == 0) {
            return "Past Month";
        } else if (term == 1) {
            return "Past 6 Months";
        } else {
            return "All Time";
        }
    }catch(NullPointerException e)
        {
            System.out.print("NullPointerException Caught");
        }
        return "Past Month";
    }

}
