package org.usfirst.frc.team4099.robot.loops;

import java.util.Timer;
import java.util.TimerTask;

public class Looper {
    private double kPeriod;
    private Loop mLoop;
    private Timer mLoopUpdater;

    public Looper(Loop loop, double period) {
        kPeriod = period;
        mLoop = loop;
    }

    /**
     * Start the TimerTask that periodically runs the loop task.
     */
    public void start() {
        if (mLoopUpdater == null) {
            mLoop.onStart();
            mLoopUpdater = new Timer();
            System.out.println("period: " + kPeriod);
            mLoopUpdater.schedule(new UpdaterTask(this), 0L, (long) (kPeriod * 1000));
        }
    }

    public void stop() {
        if (mLoopUpdater != null) {
            mLoop.onStop();
            mLoopUpdater.cancel();
            mLoopUpdater = null;
        }
    }

    private synchronized void update() {
        mLoop.onLoop();
    }

    private class UpdaterTask extends TimerTask { // the actual thread that will run the tasks
        private Looper mLooper;

        public UpdaterTask(Looper looper) {
            if (looper == null) {
                throw new NullPointerException("Given looper was null.");
            }
            mLooper = looper;
        }

        public void run() {
            mLooper.update();
        }
    }
}
