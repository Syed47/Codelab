
public final class Grade {

    private final int total;
    private final int count;
    private final int grade;
    private int acheived;

    protected Grade(int total, int count) {
        this.acheived = 0;
        this.total = total;
        this.count = count;
        this.grade = this.total/this.count;
    }
    
    protected void increment() {
        this.acheived += this.acheived < this.total ? this.grade : 0;
    }

    protected int getCount() {
        return this.count;
    }

    protected int getTotal() {
        return this.total;
    }

    protected int finalGrade() {
        return this.acheived;
    }
}