package org.usfirst.frc.team4099.lib.drive;

public class DriveSignal {

    private double leftMotor, rightMotor;
    private boolean brakeMode;

    public static DriveSignal NEUTRAL = new DriveSignal(0.0, 0.0);
    public static DriveSignal BRAKE = new DriveSignal(0.0, 0.0, true);

    public DriveSignal(double left, double right) {
        this(left, right, false);
    }

    public DriveSignal(double left, double right, boolean brake) {
        leftMotor = left;
        rightMotor = right;
        brakeMode = brake;
    }

    public double getLeftMotor() {
        return leftMotor;
    }

    public double getRightMotor() {
        return rightMotor;
    }

    public boolean getBrakeMode() {
        return brakeMode;
    }

    public void setLeftMotor(double speed) {
        leftMotor = speed;
    }

    public void setRightMotor(double speed) {
        rightMotor = speed;
    }

    public void setBrakeMode(boolean brake) {
        brakeMode = brake;
    }

    public String toString() {
        return "L: " + leftMotor + ", R: " + rightMotor + ", Brake: " + brakeMode;
    }
}
