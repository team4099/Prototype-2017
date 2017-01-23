package org.usfirst.frc.team4099.lib.joystick;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Controller Settings for Correct Mappings
 * ----------------------------------------
 * X Emulation Mode (switch on back = X)
 * Flight Mode (Mode Light = Off)
 *
 * NOTE: Buttons start (for the 2014 control system) at the 0 (ZERO) index, not 1,
 *       which is used in the new control system.
 */

public class LogitechF310Gamepad extends Joystick {

    public LogitechF310Gamepad(int port) {
        super(port);
    }

    public double getLeftXAxis() {
        return this.getRawAxis(0);
    }

    public double getLeftYAxis() {
        return this.getRawAxis(1);
    }

    public double getLeftTriggerAxis() {
        return this.getRawAxis(2);
    }

    public double getRightTriggerAxis() {
        return this.getRawAxis(3);
    }

    public double getRightXAxis() {
        return this.getRawAxis(4);
    }

    public double getRightYAxis() {
        return this.getRawAxis(5);
    }

    public boolean getAButton() {
        return this.getRawButton(0);
    }

    public boolean getBButton() {
        return this.getRawButton(1);
    }

    public boolean getXButton() {
        return this.getRawButton(2);
    }

    public boolean getYButton() {
        return this.getRawButton(3);
    }

    public boolean getLeftJoystickButton() {
        return this.getRawButton(8);
    }

    public boolean getRightJoystickButton() {
        return this.getRawButton(9);
    }
}
