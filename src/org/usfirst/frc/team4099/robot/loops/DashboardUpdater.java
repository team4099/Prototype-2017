package org.usfirst.frc.team4099.robot.loops;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4099.robot.subsystems.Drive;

public class DashboardUpdater implements Loop {

    private Drive mDrive = Drive.getInstance();
    private VoltageEstimator mVoltage = VoltageEstimator.getInstance();
    private static DashboardUpdater sInstance = new DashboardUpdater();

    public static DashboardUpdater getInstance() {
        return sInstance;
    }

    private DashboardUpdater() {}

    public void onStart() {}
    public void onStop() {}

    public synchronized void onLoop() {
        mDrive.outputToSmartDashboard();
        SmartDashboard.putNumber("Average Voltage:", mVoltage.getAverageVoltage());
    }

}
