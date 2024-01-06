package medium;


/**
 * 5. Longest Palindromic Substring
 * <a href="https://leetcode.com/problems/longest-palindromic-substring/">...</a>
 */
public class LongestPalindromicSubstring {

    // s = "babad", o/p = "bab" or "aba"
    // if we go via brute force: total substring possible is n * n = (10^6) and to check if the substring is palindrome requires additional n
    // total time complexity: n * n * n = (10 ^ 9) which will definitely result in TLE

    // another solution is to expand from a given position and keep on expanding untill the palindrome condition breaches
    // for e.g. say we are given a s = "babad" and we are at 2 position s[2] = 'b'. We'll check the left element and right element
    // if they are equal well and good, we have found a substring that is palindrome with a total character of 3. Since the condition didn't
    // breach we will expand, but s[0] != s[4] so we break.
    // the beauty of this solution is that it takes n * n complexity.

    public String longestPalindrome(String string) {
        if (string.isEmpty()) {
            return "";
        }
        long start = System.currentTimeMillis();
        // START CODING
        // Default case, a single character is a palindrome
        String longestPalindromeFoundSoFar = String.valueOf(string.charAt(0));
        int lengthOfLongestPalindromeFoundSoFar = 1;

        // note: the above solution will work for palindromes that are odd in length
        // for palindromes that are even in length, we'll have to take the two consecutive elements first.
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            int lengthOfSubstring = 1; // by default

            // odd case
            int left = i - 1;
            int right = i + 1;
            // notice this loops actually maintains the palindrome condition
            while (left > -1 && right < n && string.charAt(left) == string.charAt(right)) {
                lengthOfSubstring = (right - left + 1);
                if (lengthOfSubstring > lengthOfLongestPalindromeFoundSoFar) {
                    longestPalindromeFoundSoFar = string.substring(left, right + 1);
                    lengthOfLongestPalindromeFoundSoFar = lengthOfSubstring;
                }
                left -= 1;
                right += 1;
            }

            // even case
            left = i;
            right = i + 1;
            while (left > -1 && right < n && string.charAt(left) == string.charAt(right)) {
                lengthOfSubstring = (right - left + 1);
                if (lengthOfSubstring > lengthOfLongestPalindromeFoundSoFar) {
                    longestPalindromeFoundSoFar = string.substring(left, right + 1);
                    lengthOfLongestPalindromeFoundSoFar = lengthOfSubstring;
                }
                left -= 1;
                right += 1;
            }
        }

        // END
        long end = System.currentTimeMillis();
        System.out.println("Took: " + (end - start) + "ms");
        return longestPalindromeFoundSoFar;
    }


    public static void main(String[] args) {
        LongestPalindromicSubstring longestPalindromicSubstring = new LongestPalindromicSubstring();
        System.out.println(longestPalindromicSubstring.longestPalindrome("aaaaa"));
    }
}
