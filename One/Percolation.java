
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    final private int n;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF bw;
    private boolean[] flag;
    private int count;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must > 0!");
        }
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n);
        bw = new WeightedQuickUnionUF(n * n);

        flag = new boolean[n * n];
        for (int i = 0; i < n * n; i++) {
            flag[i] = false;
        }
        for (int i = 1; i < n; i++) {
            uf.union(0, i);
            bw.union(0, i);
        }
        for (int i = n*(n-1); i < n*n-1; i++) {
            uf.union(i, n*n-1);
        }
    }

    public    void open(int row, int col) {
        if (!isOpen(row, col)){

            int index =  index(row, col);
            flag[index] = true;

            if (row - 1 > 0 && isOpen(row - 1, col)){
                uf.union(index, index(row - 1, col));
                bw.union(index, index(row - 1, col));
            }
            if (row + 1 <= n && isOpen(row + 1, col)){
                uf.union(index, index(row + 1, col));
                bw.union(index, index(row + 1, col));
            }
            if (col - 1 > 0 && isOpen(row, col - 1)){
                uf.union(index, index(row, col - 1));
                bw.union(index, index(row, col - 1));
            }
            if (col + 1 <= n && isOpen(row, col + 1)){
                uf.union(index, index(row, col + 1));
                bw.union(index, index(row, col + 1));
            }
            count++;
        }
    }

    private int index(int row, int col) {
        if (row <= 0 || row > n) {
            throw new IndexOutOfBoundsException(" row index out of bounds!");
        }
        if (col <= 0 || col > n) {
            throw new IndexOutOfBoundsException(" col index out of bounds!");
        }
        return (row - 1) * n + col - 1;
    }


    public boolean isOpen(int row, int col) {
        int index = index(row, col);
        return flag[index];
    }

    public boolean isFull(int row, int col) {
        int index = index(row, col);
        return bw.connected(0, index) && isOpen(row, col);
    }

    public boolean percolates() {
        return (uf.connected(0, n * n - 1) && count != 0);
    }

    public int numberOfOpenSites() {
        return count;
    }

}
