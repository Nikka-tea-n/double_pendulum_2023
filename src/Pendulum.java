import java.awt.*;
import java.util.LinkedList;


import static java.lang.Math.sqrt;
public class Pendulum {
    private double m = 1; // масса в килограммах
    private double r; // в пикселях

    double omega = 0; // Угл скорость
    double beta = 0;  // Угл ускорение
    double phi = 0;   // Угол

    double length; // в метрах ~ 100 пикселей
    public LinkedList<Point> trace;

    double attachmentPointX; // точка крепления (х)
    double attachmentPointY; // точка крепления (у)

    private double ballX; // положение груза (Х)
    private double ballY; // положение груза (У)

    //зависимый маятник - тот, который прикреплен к шару данного маятника
    Pendulum dependentPendulum;

    public Pendulum(double x0, double y0, double x, double y) {
        this.attachmentPointX = x0;
        this.attachmentPointY = y0;
        this.ballX = x;
        this.ballY = y;
        recalculateLength();
        recalculatePhi();
        this.r = 13;
        this.trace = new LinkedList<>();
        omega = 0;
        beta = 0;
    }

    public void recalculateLength() {
        this.length = sqrt(Math.pow(ballX - attachmentPointX, 2) + Math.pow(ballY - attachmentPointY, 2)) / 100;
    }

    public void recalculatePhi() {
        phi = Math.atan2(ballX - attachmentPointX, ballY - attachmentPointY);
    }

    public double getBallX() {
        return ballX;
    }

    public void setNewBallPosition(double ballX, double ballY) {
        if (dependentPendulum != null) {
            dependentPendulum.attachmentPointX = ballX;
            dependentPendulum.attachmentPointY = ballY;
            dependentPendulum.recalculateLength();
            dependentPendulum.recalculatePhi();
        }
        this.ballX = ballX;
        this.ballY = ballY;
        recalculatePhi();
        recalculateLength();
    }
    public void setBallX(double ballX) {
        if (dependentPendulum != null)
            dependentPendulum.attachmentPointX = ballX;
        this.ballX = ballX;
    }

    public double getBallY() {
        return ballY;
    }

    public double getMass() {
        return m;
    }

    public void setBallY(double ballY) {
        if (dependentPendulum != null)
            dependentPendulum.attachmentPointY = ballY;
        this.ballY = ballY;
    }

    public void setMass(double m) {
        this.m = m;
    }

    public void setDependentPendulum(Pendulum dependentPendulum) {
        this.dependentPendulum = dependentPendulum;
        this.dependentPendulum.attachmentPointX = this.ballX;
        this.dependentPendulum.attachmentPointY = this.ballY;
    }
    public void paintTrace(Graphics g) {
        // отрисовка следа
        Point prevPoint = null;
        int i = 0;
        for (Point p: trace) {
            i++;
            if (prevPoint == null) {
                prevPoint = p;
                continue;
            }
            g.drawLine(prevPoint.x, prevPoint.y, p.x, p.y);
            prevPoint = p;
        }
    }
    // отрисовка маятника
    public void paintBall(Graphics g) {
        g.fillOval((int) (ballX - r), (int) (ballY - r), (int) (2 * r), (int) (2 * r));
    }
    public void paintLine(Graphics g) {
        g.drawLine((int) attachmentPointX, (int) attachmentPointY, (int) ballX, (int) ballY);
    }
}
