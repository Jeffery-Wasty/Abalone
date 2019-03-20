package ca.bcit.abalone.game;

public class Utility {

    public static int indexOf(byte[] arr, byte target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static String[] splitByFirstIndexOf(String str, String pattern) {
        int splitIndex = str.indexOf(pattern);
        String firstHalf;
        String secondHalf = null;
        if (splitIndex != -1) {
            firstHalf = str.substring(0, splitIndex);
            secondHalf = str.substring(splitIndex + 1);
        } else {
            firstHalf = str;
        }
        return new String[]{firstHalf, secondHalf};
    }

}
