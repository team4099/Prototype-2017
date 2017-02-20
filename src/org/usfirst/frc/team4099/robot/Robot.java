package org.usfirst.frc.team4099.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.usfirst.frc.team4099.lib.drive.CDriveHelper;
import org.usfirst.frc.team4099.lib.drive.TankDriveHelper;
import org.usfirst.frc.team4099.robot.loops.DashboardUpdater;
import org.usfirst.frc.team4099.robot.loops.MultiLooper;
import org.usfirst.frc.team4099.robot.loops.VoltageEstimator;
import org.usfirst.frc.team4099.robot.subsystems.Drive;

public class Robot extends IterativeRobot {

    // operator interface
    private ControlBoard mControls = ControlBoard.getInstance();

    // subsystems
    private Drive mDrive = Drive.getInstance();

    // joystick drive
    private CDriveHelper mCDrive = CDriveHelper.getInstance();
    private TankDriveHelper mTankDrive =TankDriveHelper.getInstance();

    // multiloopers
    private MultiLooper mSystemEnabled100HzLooper =
            new MultiLooper(Constants.Loopers.LOOPER_DT, "enabledLooper");
    private MultiLooper mSystemDisabled100HzLooper =
            new MultiLooper(Constants.Loopers.LOOPER_DT, "disabledLooper");

    private DashboardUpdater mDashUpdater = DashboardUpdater.getInstance();
    private VoltageEstimator mVoltageEstimator = VoltageEstimator.getInstance();


    public void robotInit() {

        mSystemEnabled100HzLooper.addLoop(mDrive);
        mSystemEnabled100HzLooper.addLoop(mDashUpdater);

        mSystemDisabled100HzLooper.addLoop(mDashUpdater);
        mSystemDisabled100HzLooper.addLoop(mVoltageEstimator);

        System.out.println("[i] Robot initialized.");
    }

    public void disabledInit() {
        mSystemDisabled100HzLooper.start();
        mSystemEnabled100HzLooper.stop();
    }

    public void autonomousInit() {
        mSystemDisabled100HzLooper.stop();
        mSystemEnabled100HzLooper.start();
    }

    public void teleopInit() {
        mSystemDisabled100HzLooper.stop();
        mSystemEnabled100HzLooper.start();
    }

    public void disabledPeriodic() {

    }

    public void autonomousPeriodic() {

    }

    public void teleopPeriodic() {
        double throttle = mControls.getThrottle();
        double wheel = mControls.getWheel();
        boolean isQuickTurn = mControls.getQuickTurn();

        mDrive.setOpenLoop(mCDrive.curvatureDrive(throttle, wheel, isQuickTurn));

//        double left = mControls.getTankLeft();
//        double right = mControls.getTankRight();
//        mDrive.setOpenLoop(mTankDrive.tankDrive(left, right));

    }

    public void testPeriodic() {

    }
}
