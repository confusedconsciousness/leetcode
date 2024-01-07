package medium;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * You are given two positive integers x and y. <br/>
 *
 * In one operation, you can do one of the four following operations:<br/>
 *
 * Divide x by 11 if x is a multiple of 11.<br/>
 * Divide x by 5 if x is a multiple of 5.<br/>
 * Decrement x by 1.<br/>
 * Increment x by 1.<br/>
 * Return the minimum number of operations required to make x and y equal
 */
public class MinimumOperationsToMakeEqual {
    Set<String> visited = new HashSet<>(); // this will prevent us from running indefinitely

    record Node(int x, int y) {
    }

    public int minimumOperationsToMakeEqual(int x, int y) {
        // if we think about this question, it will come out as a BFS.
        // at any node we'll be having four options mentioned above
        // each option will take us to a different node. We have to stop when we find the first node where x and y are equal
        // make sure x and y are within bounds
        // 1 <= x, y <= 10^4

        Node seed = new Node(x, y);
        Queue<Node> queue = new LinkedList<>();
        queue.add(seed);
        queue.add(null); // this signifies that we have covered one depth

        int operations = 0; // operation in other word will tell us how far we have come from the seed node
        while (!queue.isEmpty()) {
            Node node = queue.poll();

            // check if we have covered one depth, if yes increase the operation by 1
            if (node == null) {
                // this means no other elements are left, so we should terminate the process
                if (queue.isEmpty()) {
                    return operations;
                }
                operations += 1;
                queue.add(null);
                continue;
            }

            // base case
            if (node.x() == node.y()) {
                return operations;
            }

            // if the node is already visited no need to put it in the queue again
            if (visited.contains(getSignature(node))) {
                continue;
            }
            visited.add(getSignature(node));

            // otherwise we'll have to create four different node based on our four different operations
            if (node.x() % 11 == 0) {
                queue.add(new Node(node.x() / 11, node.y()));
            }

            if (node.x() % 5 == 0) {
                queue.add(new Node(node.x() / 5, node.y()));
            }

            if (node.x() < 10_000) {
                // otherwise we would create a node (10_0001, y) which is not inside the bounds
                queue.add(new Node(node.x() + 1, node.y()));
            }

            if (node.x() > 1) {
                // otherwise we would create a node (0, y) which is not inside the bounds
                queue.add(new Node(node.x() - 1, node.y()));
            }
        }

        return operations;
    }

    private String getSignature(Node node) {
        return node.x() + ":" + node.y();
    }

    public static void main(String[] args) {
        MinimumOperationsToMakeEqual test = new MinimumOperationsToMakeEqual();
        System.out.println(test.minimumOperationsToMakeEqual(1, 10000));
    }
}
