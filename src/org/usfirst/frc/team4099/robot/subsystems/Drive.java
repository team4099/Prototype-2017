package org.usfirst.frc.team4099.robot.subsystems;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4099.lib.drive.DriveSignal;
import org.usfirst.frc.team4099.robot.Constants;
import org.usfirst.frc.team4099.robot.loops.Loop;

public class Drive implements Subsystem, Loop {

    private static Drive sInstance = new Drive();
    private int currentState = DriveControlState.OPEN_LOOP;
    private Talon leftFrontTalonSR, leftBackTalonSR;
    private Talon rightFrontTalonSR, rightBackTalonSR;
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private double lastRecordedTime;
    private double dt;

    public class DriveControlState { // the struggle when no enums exist...
        public static final int OPEN_LOOP = 0;
    }

    public Drive() {
        leftFrontTalonSR = new Talon(Constants.Drive.LEFT_FRONT_TALON_PORT);
        leftBackTalonSR = new Talon(Constants.Drive.LEFT_BACK_TALON_PORT);
        rightFrontTalonSR = new Talon(Constants.Drive.RIGHT_FRONT_TALON_PORT);
        rightBackTalonSR = new Talon(Constants.Drive.RIGHT_BACK_TALON_PORT);

        leftEncoder = new Encoder(Constants.Drive.LEFT_ENC_A_CHAN,
                                  Constants.Drive.LEFT_ENC_B_CHAN,
                false, CounterBase.EncodingType.k4X);
        rightEncoder = new Encoder(Constants.Drive.RIGHT_ENC_A_CHAN,
                                   Constants.Drive.RIGHT_ENC_B_CHAN,
                false, CounterBase.EncodingType.k4X);

        leftEncoder.start();
        rightEncoder.start();
        leftEncoder.reset();
        rightEncoder.reset();

        leftEncoder.setSamplesToAverage(10);
        rightEncoder.setSamplesToAverage(10);
    }

    public static Drive getInstance() {
        return sInstance;
    }

    public void outputToSmartDashboard() {
        SmartDashboard.putNumber("leftFrontTalonSR", leftFrontTalonSR.get());
        SmartDashboard.putNumber("leftBackTalonSR", leftBackTalonSR.get());
        SmartDashboard.putNumber("rightFrontTalonSR", rightFrontTalonSR.get());
        SmartDashboard.putNumber("rightBackTalonSR", rightBackTalonSR.get());

        SmartDashboard.putNumber("dt", dt);

        SmartDashboard.putNumber("leftEncoder", leftEncoder.get());
        SmartDashboard.putNumber("rightEncoder", rightEncoder.get());
        SmartDashboard.putNumber("leftEncoderDistance", leftEncoder.getDistance());
        SmartDashboard.putNumber("rightEncoderDistance", rightEncoder.getDistance());
        SmartDashboard.putNumber("leftEncoderRate", leftEncoder.getRate());
        SmartDashboard.putNumber("rightEncoderRate", rightEncoder.getRate());
        SmartDashboard.putNumber("leftEncoderRaw", leftEncoder.getRaw());
        SmartDashboard.putNumber("rightEncoderRaw", rightEncoder.getRaw());
    }

    public synchronized void stop() {
        setOpenLoop(DriveSignal.NEUTRAL);
    }

    public synchronized void zeroSensors() {
        leftEncoder.reset();
        rightEncoder.reset();
    }

    public void onStart() {}
    public void onStop() {}

    public synchronized void onLoop() {
        double currentTime = Timer.getFPGATimestamp();
        dt = currentTime - lastRecordedTime;
        lastRecordedTime = currentTime;

        switch (currentState) {
            case DriveControlState.OPEN_LOOP:
                //System.out.println(dt);
                return;

            default:
                System.out.println("Unknown Drive subsystem control state reached!");
        }
    }

    public synchronized void setOpenLoop(DriveSignal signal) {
        if (currentState != DriveControlState.OPEN_LOOP) {
            currentState = DriveControlState.OPEN_LOOP;
        }

        setLeftRightPower(signal.getLeftMotor(), signal.getRightMotor());
    }

    private synchronized void setLeftRightPower(double left, double right) {
        leftBackTalonSR.set(left);
        leftFrontTalonSR.set(left);
        rightBackTalonSR.set(right);
        rightFrontTalonSR.set(-right);
    }
}
