package org.usfirst.frc.team4099.robot.loops;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Constantly measures battery voltage before the match begins. */

public class VoltageEstimator implements Loop {

    private static VoltageEstimator sInstance = new VoltageEstimator();

    private double running_avg = 12;

    public static VoltageEstimator getInstance() {
        return sInstance;
    }

    public void onStart() {
        System.out.println("Robot disabled: computing avg voltage");
    }

    public synchronized void onLoop() {
        double weight = 15.0;
        double cur_voltage = DriverStation.getInstance().getBatteryVoltage();

        running_avg = (cur_voltage + weight * running_avg) / (1.0 + weight);
    }

    public void onStop() {
        System.out.println("Robot enabled: last avg voltage: " + running_avg);
    }

    public synchronized double getAverageVoltage() {
        return running_avg;
    }
}
