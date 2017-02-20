package org.usfirst.frc.team4099.robot;

import org.usfirst.frc.team4099.lib.joystick.DualShock4Gamepad;
import org.usfirst.frc.team4099.lib.joystick.LogitechF310Gamepad;

public class ControlBoard {
    private static ControlBoard sInstance = new ControlBoard();

    public static ControlBoard getInstance() {
        return sInstance;
    }

    private final LogitechF310Gamepad driver;
//    private final DualShock4Gamepad driver;


    private ControlBoard() {
        driver = new LogitechF310Gamepad(Constants.Joysticks.DRIVER_PORT);
//        driver = new DualShock4Gamepad(Constants.Joysticks.DRIVER_PORT);
    }

    public double getThrottle() {
        return driver.getLeftYAxis();
    }

    public double getWheel() {
        return -driver.getRightXAxis();
    }

    /**
     * Should the bot drive in quick turn mode?
     * @return  true/false, depending on if the joystick is depressed
     */
    public boolean getQuickTurn() {
        return driver.getRightJoystickButton();
    }

    public double getTankLeft() {
        return driver.getLeftYAxis();
    }

    public double getTankRight() {
        return driver.getRightYAxis();
    }
}
