import java.util.*;

/**
 * Manages the root node and
 * provides the primary interface for inserting flowers and finding the nearest flower
 */
public class QuadTree implements IQuadTree {
    private Node root;
    private Boundary boundary;
    public int insertNum;

    /**
     * Handles the spatial boundaries of each node
     */
    private static class Boundary {
        double xMin, xMax, yMin, yMax;

        public Boundary(double xMin, double xMax, double yMin, double yMax) {
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
        }

        public boolean inRange(double x, double y) {
            // Adjust by a very small epsilon to handle precision issues at the upper boundary
            double epsilon = 1e-10;
            boolean inRange = (x >= xMin && x < xMax + epsilon && y >= yMin && y < yMax + epsilon);

            // Debugging output
            if (!inRange) {
                System.out.println("Out of range: (" + x + ", " + y + ") not in ["
                        + xMin + ", " + xMax + "] x ["
                        + yMin + ", " + yMax + "]");
            }

            return inRange;
        }


    }


    /**
     * Represents a quadtree node
     * contains a flower's name and its coordinates, and pointer to children nodes
     */
    private static class Node {
        String flowerName;
        double x, y;
        Node[] children = new Node[4];
        Node parent; // Pointer to the parent node
        Boundary boundary;

        Node(String flowerName, double x, double y, Boundary boundary, Node parent) {
            this.flowerName = flowerName;
            this.x = x;
            this.y = y;
            this.boundary = boundary;
            this.parent = parent;
        }
    }

    // constructor

    /**
     * Constructs a new QuadTree with specified spatial boundary.
     *
     * @param xMin The minimum x-coordinate of the boundary.
     * @param xMax The maximum x-coordinate of the boundary.
     * @param yMin The minimum y-coordinate of the boundary.
     * @param yMax The maximum y-coordinate of the boundary.
     */
    public QuadTree(double xMin, double xMax, double yMin, double yMax) {
        // System.out.println("QuadTree: x from " + xMin + " to " + xMax + ", y from " + yMin + " to " + yMax);
        this.boundary = new Boundary(xMin, xMax, yMin, yMax);
        this.root = new Node(null, (xMin + xMax) / 2, (yMin + yMax) / 2, this.boundary, null);
        this.insertNum = 0;
    }


    /**
     * Checks if the point to be inserted falls within the boundary of the node
     * @param flowerName - primary Name of flower
     * @param x - x value
     * @param y - y value
     */
    public boolean insert(String flowerName, double x, double y) {
        Node currentNode = root;
        while (true) {
            if (currentNode.boundary.inRange(x, y)) {
                if (insertAtNode(currentNode, flowerName, x, y)) {
                    insertNum++;  // Increment the counter upon successful insertion
                    return true;
                }
            } else {
                // Move up to find a suitable parent
                if (currentNode.parent == null) {
                    // reach here means there's no suitable parent in the tree
                    // System.out.println("No suitable parent found. Node out of overall boundary.");
                    return false;
                }
                currentNode = currentNode.parent;
            }
        }
    }

    private boolean insertAtNode(Node node, String flowerName, double x, double y) {
        if (node.children[0] == null && node.flowerName == null) {
            node.flowerName = flowerName;
            node.x = x;
            node.y = y;
            insertNum++;
            return true;
        }

        if (node.children[0] == null) {
            subdivide(node);
        }

        int pos = findQuadrant(node, x, y);
        return insertAtNode(node.children[pos], flowerName, x, y);
    }

    private void subdivide(Node node) {
        double midX = (node.boundary.xMin + node.boundary.xMax) / 2;
        double midY = (node.boundary.yMin + node.boundary.yMax) / 2;

        node.children[0] = new Node(null, midX, midY, new Boundary(midX, node.boundary.xMax, midY, node.boundary.yMax), node);
        node.children[1] = new Node(null, midX, midY, new Boundary(node.boundary.xMin, midX, midY, node.boundary.yMax), node);
        node.children[2] = new Node(null, midX, midY, new Boundary(node.boundary.xMin, midX, node.boundary.yMin, midY), node);
        node.children[3] = new Node(null, midX, midY, new Boundary(midX, node.boundary.xMax, node.boundary.yMin, midY), node);
    }

    /**
     * Determines which quadrant a point belongs to relative to the node’s center
     * @param node - node we look up
     * @param x - x of point
     * @param y - y of point
     * @return - direction (represented by an int)
     */
    private int findQuadrant(Node node, double x, double y) {
        boolean right = x >= node.x; // Consider >= to correctly handle the midpoint itself
        boolean above = y >= node.y; // Consider >= to correctly handle the midpoint itself

        if (right) {
            return above ? 0 : 3; // NE or SE
        } else {
            return above ? 1 : 2; // NW or SW
        }
    }

    /**
     * Finds the k approximate nearest neighbors to a given flower using a BFS-like approach starting from the parent node.
     * once BFS done with all siblings and childs, recursively search parent'parent
     *
     * @param flowerName The name of the target flower.
     * @param k          The number of neighbors to find.
     * @return A list of flower names that are approximate nearest neighbors.
     */
    public List<String> findKApproximateNearestNeighbors(String flowerName, int k) {
        Node targetNode = findNode(root, flowerName);
        // System.out.println("targetNode: " + targetNode);
        if (targetNode == null || targetNode.parent == null) {
            // System.out.println("flower not found");
            return Collections.emptyList();
        }

        List<String> neighbors = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();  // To keep track of visited nodes

        // Start BFS from the parent node to include all siblings and the target node itself
        Node parentNode = targetNode.parent;
        while (parentNode != null && neighbors.size() < k) {
            for (Node sibling : parentNode.children) {
                if (sibling != null && visited.add(sibling)) {
                    queue.add(sibling);
                }
            }

            while (!queue.isEmpty() && neighbors.size() < k) {
                Node currentNode = queue.poll();
                if (currentNode.flowerName != null && !currentNode.flowerName.equals(flowerName)) {
                    neighbors.add(currentNode.flowerName);
                }

                // Enqueue children of the current node
                for (Node child : currentNode.children) {
                    if (child != null && visited.add(child)) {
                        queue.add(child);
                    }
                }
            }

            // Move to the parent node of the current level to widen the search area
            parentNode = parentNode.parent;
        }

        return neighbors;
    }

    /**
     * Find the node containing the target flower
     * @param root - the node we are currently visiting
     * @param flowerName the flower name to search
     * @return the target node
     */
    Node findNode(Node root, String flowerName) {
//        printQuadTree(root, 0);
        if (root == null) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            // System.out.println("Visiting node: " + (current.flowerName == null ? "Unnamed" : current.flowerName));

            // Check if the current node is the target
            if (flowerName.equals(current.flowerName)) {
                // System.out.println("Found the target node: " + flowerName);
                return current;
            }

            // Enqueue children
            for (Node child : current.children) {
                if (child != null) {
                    // System.out.println("Adding child node to queue: " + (child.flowerName == null ? "Unnamed" : child.flowerName));
                    queue.add(child);
                }
            }
        }

        // System.out.println("No node found with the name: " + flowerName);
        return null;
    }

    /**
     * a helper method to print the entire tree
     * @param node
     * @param level
     */
    private void printQuadTree(Node node, int level) {
        if (node == null) return;
        String indent = " ";
        System.out.println(indent + "Node at level " + level + ": " + (node.flowerName == null ? "Unnamed" : node.flowerName));
        for (Node child : node.children) {
            printQuadTree(child, level + 1);
        }
    }

    public int countNodes(Node node) {
        if (node == null) return 0;
        int count = (node.flowerName != null ? 1 : 0); // count this node only if it has a flower
        for (Node child : node.children) {
            count += countNodes(child);
        }
        return count;
    }

    public Node getRoot() {
        return  this.root;
    }

}
