package medium;

import java.util.*;

/**
 * You have a lock in front of you with 4 circular wheels. <br/>
 * Each wheel has 10 slots: '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'. <br/>
 * The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or '0' to be '9'.<br/>
 * Each move consists of turning one wheel one slot.<br/>
 * <br/>
 * The lock initially starts at '0000', a string representing the state of the 4 wheels.<br/>
 * <br/>
 * You are given a list of deadends dead ends, meaning if the lock displays any of these codes, <br/>
 * the wheels of the lock will stop turning, and you will be unable to open it.<br/>
 * <br/>
 * Given a target representing the value of the wheels that will unlock the lock, <br/>
 * return the minimum total number of turns required to open the lock, or -1 if it is impossible.
 * <a href="https://leetcode.com/problems/open-the-lock/description/">...</a>
 */
public class OpenTheLock {



    record Node(int w1, int w2, int w3, int w4) {
        String getCombination() {
            return w1 + "" + w2 + w3 + w4;
        }
    }

    public int openLock(String[] deadends, String target) {
        // at any time we can only rotate one of the four wheel. So we have to choose any one of the four wheel
        // if we are at 0000 -> (0001, 0010, 0100, 1000, 9000, 0900, 0090, 0009) these are the 8 states that we can achieve
        // note: if only we are 0 we are given the liberty to reach 9, we cannot jump from say 1 to 8.

        Set<String> visited = new HashSet<>(Arrays.asList(deadends));


        // looking at the problem this looks like BFS
        int turns = 0;
        Node seed = new Node(0,0,0,0);
        Queue<Node> queue = new LinkedList<>();

        queue.add(seed);
        queue.add(null); // this will help us in knowing the distance that we have covered from seed

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node == null) {
                if (queue.isEmpty()) {
                    return -1;
                }
                queue.add(null);
                turns += 1;
                continue;
            }

            if (visited.contains(node.getCombination())) {
                continue;
            }
            visited.add(node.getCombination());

            if (node.getCombination().equals(target)) {
                return turns;
            }

            // explore the options using the current state
            int w1 = node.w1;
            int w2 = node.w2;
            int w3 = node.w3;
            int w4 = node.w4;

            // -------------------- cranking first wheel --------------------------
            // cranking to the left
            queue.add(new Node(w1 == 0 ? 9 : w1 - 1, w2, w3, w4));
            // cranking to the right
            queue.add(new Node(w1 == 9 ? 0 : w1 + 1, w2, w3, w4));

            // -------------------- cranking second wheel --------------------------
            // cranking to the left
            queue.add(new Node(w1, w2 == 0 ? 9 : w2 - 1, w3, w4));
            // cranking to the right
            queue.add(new Node(w1, w2 == 9 ? 0 : w2 + 1, w3, w4));

            // -------------------- cranking third wheel --------------------------
            // cranking to the left
            queue.add(new Node(w1, w2, w3 == 0 ? 9 : w3 - 1, w4));
            // cranking to the right
            queue.add(new Node(w1, w2, w3 == 9 ? 0 : w3 + 1, w4));

            // -------------------- cranking fourth wheel --------------------------
            // cranking to the left
            queue.add(new Node(w1, w2, w3, w4 == 0 ? 9 : w4 - 1));
            // cranking to the right
            queue.add(new Node(w1, w2, w3, w4 == 9 ? 0 : w4 + 1));
        }

        return -1;
    }



    public static void main(String[] args) {
        String[] deadends = new String[] {"8887","8889","8878","8898","8788","8988","7888","9888"};
        String target = "8888";
        OpenTheLock openTheLock = new OpenTheLock();
        System.out.println(openTheLock.openLock(deadends, target));
    }

}
