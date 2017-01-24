package org.usfirst.frc.team4099.robot.loops;

import org.usfirst.frc.team4099.robot.subsystems.Drive;

public class DashboardUpdater implements Loop {

    private Drive mDrive = Drive.getInstance();
    private static DashboardUpdater sInstance = new DashboardUpdater();

    public static DashboardUpdater getInstance() {
        return sInstance;
    }

    private DashboardUpdater() {}

    public void onStart() {}
    public void onStop() {}

    public synchronized void onLoop() {
        mDrive.outputToSmartDashboard();
    }

}
