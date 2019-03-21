public class Utility {

    static int indexOf(byte[] arr, byte target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unused")
    public static Integer[] toIntArray(String str) {
        String[] tokens = str.split(",");
        Integer[] arr = new Integer[tokens.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(tokens[i]);
        }
        return arr;
    }

    @SuppressWarnings("unused")
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