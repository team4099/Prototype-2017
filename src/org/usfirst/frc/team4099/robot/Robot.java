package org.usfirst.frc.team4099.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import org.usfirst.frc.team4099.robot.loops.MultiLooper;
import org.usfirst.frc.team4099.robot.subsystems.Drive;

public class Robot extends IterativeRobot {

    private Drive mDrive = Drive.getInstance();
    private MultiLooper mSystemEnabled100HzLooper =
            new MultiLooper(Constants.Loopers.LOOPER_DT, "enabledLooper");
    private MultiLooper mSystemDisabled100HzLooper =
            new MultiLooper(Constants.Loopers.LOOPER_DT, "disabledLooper");

    public Robot() {

    }

    public void robotInit() {

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

    }
}
