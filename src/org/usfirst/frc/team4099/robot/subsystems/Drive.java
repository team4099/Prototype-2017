package org.usfirst.frc.team4099.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import org.usfirst.frc.team4099.lib.drive.DriveSignal;
import org.usfirst.frc.team4099.robot.Constants;
import org.usfirst.frc.team4099.robot.ControlBoard;
import org.usfirst.frc.team4099.lib.drive.CDriveHelper;
import org.usfirst.frc.team4099.robot.loops.Loop;

public class Drive implements Subsystem, Loop {

    private static Drive sInstance = new Drive();
    private ControlBoard mControls = ControlBoard.getInstance();
    private int currentState = DriveControlState.OPEN_LOOP;
    private CDriveHelper mCDrive = CDriveHelper.getInstance();
    private Talon leftTalonSR, rightTalonSR;

    public class DriveControlState {
        public static final int OPEN_LOOP = 0;
    }

    private Drive() {
        leftTalonSR = new Talon(Constants.Drive.LEFT_TALON_PORT);
        rightTalonSR = new Talon(Constants.Drive.RIGHT_TALON_PORT);
    }

    public static Drive getInstance() {
        return sInstance;
    }

    public void outputToSmartDashboard() {

    }

    public synchronized void stop() {
        setOpenLoop(DriveSignal.NEUTRAL);
    }

    public synchronized void zeroSensors() {

    }

    public void onStart() {}
    public void onStop() {}

    public synchronized void onLoop() {
        switch (currentState) {
            case DriveControlState.OPEN_LOOP:
                double throttle = mControls.getThrottle();
                double wheel = mControls.getWheel();
                boolean isQuickTurn = mControls.getQuickTurn();

                setOpenLoop(mCDrive.curvatureDrive(throttle, wheel, isQuickTurn));
                return;

            default:
                System.out.println("Unknown 'Drive' control state reached!");
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
