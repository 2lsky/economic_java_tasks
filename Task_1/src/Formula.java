public class Formula{
    private int t;
    private int R;
    private int I;

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public int getI() {
        return I;
    }

    public void setI(int i) {
        I = i;
    }

    public double thermal_energy(){
        return Math.pow(this.I, 2)* this.R * this.t;
    }
}
