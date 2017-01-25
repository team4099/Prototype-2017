package org.usfirst.frc.team4099.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4099.lib.drive.DriveSignal;
import org.usfirst.frc.team4099.robot.Constants;
import org.usfirst.frc.team4099.robot.loops.Loop;

public class Drive implements Subsystem, Loop {

    private static Drive sInstance = new Drive();
    private int currentState = DriveControlState.OPEN_LOOP;
    private Talon leftTalonSR, rightTalonSR;
    private double lastRecordedTime;
    private double dt;

    public class DriveControlState { // the struggle when no enums exist...
        public static final int OPEN_LOOP = 0;
    }

    public Drive() {
        leftTalonSR = new Talon(Constants.Drive.LEFT_TALON_PORT);
        rightTalonSR = new Talon(Constants.Drive.RIGHT_TALON_PORT);
    }

    public static Drive getInstance() {
        return sInstance;
    }

    public void outputToSmartDashboard() {
        SmartDashboard.putNumber("leftTalon", leftTalonSR.get());
        SmartDashboard.putNumber("rightTalon", rightTalonSR.get());
        SmartDashboard.putNumber("dt", dt);
    }

    public synchronized void stop() {
        setOpenLoop(DriveSignal.NEUTRAL);
    }

    public synchronized void zeroSensors() {

    }

    public void onStart() {}
    public void onStop() {}

    public synchronized void onLoop() {
        double currentTime = Timer.getFPGATimestamp();
        dt = currentTime - lastRecordedTime;
        lastRecordedTime = currentTime;

        switch (currentState) {
            case DriveControlState.OPEN_LOOP:
                System.out.println(dt);
                return;

            default:
                System.out.println("Unknown Drive subsystem control state reached!");
        }
    }

    public synchronized void setOpenLoop(DriveSignal signal) {
        if (currentState != DriveControlState.OPEN_LOOP) {
            currentState = DriveControlState.OPEN_LOOP;
        }

        setLeftRightPower(signal.getLeftMotor(), signal.getRightMotor());
    }

    private synchronized void setLeftRightPower(double left, double right) {
        leftTalonSR.set(left);
        rightTalonSR.set(right);
    }
}
