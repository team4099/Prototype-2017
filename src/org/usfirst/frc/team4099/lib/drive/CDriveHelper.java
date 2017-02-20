package org.usfirst.frc.team4099.lib.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4099.lib.drive.DriveSignal;
import org.usfirst.frc.team4099.lib.joystick.JoystickUtils;
import org.usfirst.frc.team4099.lib.util.Utils;

/**
 * Curvature Drive -
 *   The left joystick controls speed (throttle)
 *   The right joystick controls the curvature of the path.
 *
 * Credits: Team 254
 */

public class CDriveHelper {

    private static CDriveHelper sInstance = new CDriveHelper();

    private double mQuickStopAccumulator;
    private double negativeInertia;

    private static final double kThrottleDeadband = 0.02;
    private static final double kWheelDeadband= 0.02;
    private static final double kTurnSensitivity = 0.7;

    private double lastThrottle;
    private static final double kMaxThrottleDelta = 1.0 / 40.0;

    private DriveSignal mSignal = new DriveSignal(0, 0);

    public static CDriveHelper getInstance() {
        return sInstance;
    }

    public DriveSignal curvatureDrive(double throttle, double wheel, boolean isQuickTurn) {
        throttle = JoystickUtils.deadband(throttle, kThrottleDeadband);
        wheel = -JoystickUtils.deadbandNoShape(wheel, kWheelDeadband);

//        double wheelNonLinearity = 0.5;
//        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
//        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);
//        wheel = Math.sin(Math.PI / 2.0 * wheelNonLinearity * wheel) / Math.sin(Math.PI / 2.0 * wheelNonLinearity);

        /* ramps up the joystick throttle when magnitude increases
         * if magnitude decreases (1.0 to 0.0, or -1.0 to 0.0), allow anything
         * kMaxThrottleDelta is the maximum allowed change in throttle
         *   per iteration (~50 Hz)
         * attempt to limit the current draw when accelerating
         *
         * The acceleration time is quite apparent, but not unresponsive.
         */
        if (!Utils.sameSign(throttle, lastThrottle)) {
            throttle = 0.0;
        } else {
            double throttleMagnitude = Math.abs(throttle);
            double lastThrottleMagnitude = Math.abs(lastThrottle);

            // only if an increase in magnitude
            if (throttleMagnitude > lastThrottleMagnitude) {
                // for + to more + increases
                if (throttle > lastThrottle + kMaxThrottleDelta)
                    throttle = lastThrottle + kMaxThrottleDelta;
                    // for - to more - decreases
                else if (throttle < lastThrottle - kMaxThrottleDelta)
                    throttle = lastThrottle - kMaxThrottleDelta;
            }
        }
        SmartDashboard.putNumber("joystickThrottle", throttle);
        lastThrottle = throttle;


        // negative inertia!
        if (throttle > 0.1 && Utils.around(wheel, 0.0, 0.15)) { // moving straight, fast
            double beta = 0.1;
            negativeInertia = (1 - beta) * negativeInertia +
                    beta * Utils.limit(throttle, 1.0) * 2;
        } else if (Utils.around(throttle, 0.0, 0.075)) { // if wanting to brake (low throttle)
            throttle -= negativeInertia;

            if (negativeInertia > 1) {
                negativeInertia -= 0.1;
            } else if (negativeInertia < -1) {
                negativeInertia += 0.1;
            } else {
                negativeInertia = 0.0;
            }
        }

        double overPower;
        double angularPower;

        if (isQuickTurn) {
            if (Math.abs(throttle) < 0.2) {
                double alpha = 0.1;
                mQuickStopAccumulator = (1 - alpha) * mQuickStopAccumulator + // used for "negative inertia"
                        alpha * Utils.limit(wheel, 1.0) * 2;
            }
            overPower = 1.0;
            angularPower = wheel;
        } else {
            overPower = 0.0;
            angularPower = Math.abs(throttle) * wheel * kTurnSensitivity - mQuickStopAccumulator;

            if (mQuickStopAccumulator > 1) {
                mQuickStopAccumulator -= 1.0;
            } else if (mQuickStopAccumulator < -1) {
                mQuickStopAccumulator += 1.0;
            } else {
                mQuickStopAccumulator = 0.0;
            }
        }

        double rightPWM = throttle - angularPower;
        double leftPWM = throttle + angularPower;
        if (leftPWM > 1.0) {
            rightPWM -= overPower * (leftPWM - 1.0);
            leftPWM = 1.0;
        } else if (rightPWM > 1.0) {
            leftPWM -= overPower * (rightPWM - 1.0);
            rightPWM = 1.0;
        } else if (leftPWM < -1.0) {
            rightPWM += overPower * (-1.0 - leftPWM);
            leftPWM = -1.0;
        } else if (rightPWM < -1.0) {
            leftPWM += overPower * (-1.0 - rightPWM);
            rightPWM = -1.0;
        }

        mSignal.setRightMotor(rightPWM);
        mSignal.setLeftMotor(leftPWM);

        return mSignal;
    }
}
