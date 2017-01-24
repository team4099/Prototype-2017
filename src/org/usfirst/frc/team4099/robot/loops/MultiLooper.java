package org.usfirst.frc.team4099.robot.loops;

import java.util.Vector;

public class MultiLooper implements Loop {
    private Looper mLooper; // a single Looper
    private Vector mLoops = new Vector();
    private String name;

    public MultiLooper(double period, String name) {
        this.name = name;
        mLooper = new Looper(this, period);
    }

    public synchronized void onLoop() { // but on each call of the Looper, multiple loops are run
        for (int i = 0; i < mLoops.size(); ++i) {
            Loop loop = (Loop) mLoops.elementAt(i);
            if (loop != null) {
                loop.onLoop();
            }
        }
    }

    /**
     * Starts the MultiLooper's internal Looper, which
     * will call the MultiLooper's onStart() method (MultiLooper is a Loop)
     */
    public void start() {
        mLooper.start();
    }

    public void stop() {
        mLooper.stop();
    }

    public void onStart() {
        for (int i = 0; i < mLoops.size(); ++i) {
            Loop loop = (Loop) mLoops.elementAt(i);
            if (loop != null) {
                loop.onStart();
            }
        }
        System.out.println("[i] MultiLooper '" + name + "' started.");
    }

    public void onStop() {
        for (int i = 0; i < mLoops.size(); ++i) {
            Loop loop = (Loop) mLoops.elementAt(i);
            if (loop != null) {
                loop.onStop();
            }
        }
        System.out.println("[i] MultiLooper '" + name + "' stopped.");
    }

    public synchronized void addLoop(Loop loop) {
        mLoops.addElement(loop);
    }
}
