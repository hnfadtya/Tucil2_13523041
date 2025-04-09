public class QuadTreeNode {
    public int x, y, width, height;
    public int averageR, averageG, averageB;
    public boolean isLeaf;
    public QuadTreeNode[] children;

    public QuadTreeNode(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.isLeaf = true;
        this.children = new QuadTreeNode[4]; // NW, NE, SW, SE
    }
}
