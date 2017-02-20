package org.usfirst.frc.team4099.lib.drive;

import org.usfirst.frc.team4099.lib.joystick.JoystickUtils;

public class TankDriveHelper {

    private static TankDriveHelper sInstance = new TankDriveHelper();

    private double kLeftDeadband = 0.02;
    private double kRightDeadband = 0.02;

    private DriveSignal mSignal = new DriveSignal(0, 0);

    private TankDriveHelper() {

    }

    public static TankDriveHelper getInstance() {
        return sInstance;
    }

    public DriveSignal tankDrive(double left, double right) {
        left = JoystickUtils.deadband(left, kLeftDeadband);
        right = JoystickUtils.deadband(right, kRightDeadband);

        mSignal.setLeftMotor(left);
        mSignal.setRightMotor(right);

        return mSignal;
    }
}
