package org.usfirst.frc.team4099.robot.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4099.lib.drive.DriveSignal;
import org.usfirst.frc.team4099.lib.util.PIDOutputReceiver;
import org.usfirst.frc.team4099.robot.Constants;
import org.usfirst.frc.team4099.robot.loops.Loop;

public class Drive implements Subsystem, Loop {

    private static Drive sInstance = new Drive();
    private int currentState = DriveControlState.OPEN_LOOP;
    private Talon leftFrontTalonSR, leftBackTalonSR;
    private Talon rightFrontTalonSR, rightBackTalonSR;
    private Encoder leftEncoder;
    private Encoder rightEncoder;
    private Gyro gyro;
    private double lastRecordedTime;
    private double dt;

    private PIDController turnController;
    private PIDController leftController;
    private PIDController rightController;

    private PIDOutputReceiver turnReceiver;
    private PIDOutputReceiver leftReceiver;
    private PIDOutputReceiver rightReceiver;

    public class DriveControlState { // the struggle when no enums exist...
        public static final int OPEN_LOOP = 0;
        public static final int AUTONOMOUS_DRIVING = 1;
        public static final int AUTONOMOUS_TURNING = 2;
    }

    public Drive() {
        leftFrontTalonSR = new Talon(Constants.Drive.LEFT_FRONT_TALON_PORT);
        leftBackTalonSR = new Talon(Constants.Drive.LEFT_BACK_TALON_PORT);
        rightFrontTalonSR = new Talon(Constants.Drive.RIGHT_FRONT_TALON_PORT);
        rightBackTalonSR = new Talon(Constants.Drive.RIGHT_BACK_TALON_PORT);

        gyro = new Gyro(0);

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

        turnReceiver = new PIDOutputReceiver();
        turnController = new PIDController(Constants.Gains.TURN_P, Constants.Gains.TURN_I, Constants.Gains.TURN_D, Constants.Gains.TURN_F, gyro, turnReceiver);
        turnController.setInputRange(-180, 180);
        turnController.setOutputRange(-Constants.Drive.AUTO_TURN_MAX_POWER, Constants.Drive.AUTO_TURN_MAX_POWER);
        turnController.setAbsoluteTolerance(Constants.Drive.TURN_TOLERANCE_DEGREES);
        turnController.setContinuous(true);

        leftReceiver = new PIDOutputReceiver();
        leftController = new PIDController(Constants.Gains.FORWARD_P, Constants.Gains.FORWARD_I, Constants.Gains.FORWARD_D, Constants.Gains.FORWARD_F, leftEncoder, leftReceiver);
        leftController.setOutputRange(-Constants.Drive.AUTO_FORWARD_MAX_POWER, Constants.Drive.AUTO_FORWARD_MAX_POWER);
        leftController.setAbsoluteTolerance(Constants.Drive.FORWARD_TOLERANCE_METERS);
        leftController.setContinuous(false);

        rightReceiver = new PIDOutputReceiver();
        rightController = new PIDController(Constants.Gains.FORWARD_P, Constants.Gains.FORWARD_I, Constants.Gains.FORWARD_D, Constants.Gains.FORWARD_F, rightEncoder, rightReceiver);
        rightController.setOutputRange(-Constants.Drive.AUTO_FORWARD_MAX_POWER, Constants.Drive.AUTO_FORWARD_MAX_POWER);
        rightController.setAbsoluteTolerance(Constants.Drive.FORWARD_TOLERANCE_METERS);
        rightController.setContinuous(false);

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

    public synchronized void setAutonomousDriving() {
        currentState = DriveControlState.AUTONOMOUS_DRIVING;
    }

    private synchronized void setLeftRightPower(double left, double right) {
        leftBackTalonSR.set(left);
        leftFrontTalonSR.set(left);
        rightBackTalonSR.set(right);
        rightFrontTalonSR.set(-right);
    }

    public void setForwardSetpoint(double distance) {
        System.out.println("Set setpoint to " + distance);
        setAutonomousDriving();
        leftController.enable();
        rightController.enable();
        leftController.setSetpoint(distance);
        rightController.setSetpoint(distance);
    }

    public boolean goForward() {
        System.out.println("Moving rate of right: " + rightReceiver.getOutput() + " Distance: " + rightEncoder.getDistance() + " Time: " + Timer.getFPGATimestamp());
        System.out.println("Moving rate of left: " + leftReceiver.getOutput() + " Distance: " + leftEncoder.getDistance() + " Time: " + Timer.getFPGATimestamp());
//        double leftError = Math.abs(leftController.getSetpoint())
//        lastForwardErrors.add(Math.abs(left))
        if(leftController.onTarget() || rightController.onTarget() || currentState != DriveControlState.AUTONOMOUS_DRIVING){
            return true;
        }
        setLeftRightPower(leftReceiver.getOutput(), rightReceiver.getOutput());
        return false;
    }

    public void finishForward() {
        leftController.disable();
        rightController.disable();
        setOpenLoop(DriveSignal.NEUTRAL);
    }

    public synchronized void setAutonomousTurning() {
        currentState = DriveControlState.AUTONOMOUS_TURNING;
    }

    public void setAngleSetpoint(double angle) {
        System.out.println("Set setpoint to " + angle);
        setAutonomousTurning();
        turnController.enable();
        turnController.setSetpoint(angle);
    }

    public boolean turnAngle() {
        System.out.println("Turn setpoint: " + turnController.getSetpoint() + " Angle: " + gyro.getAngle() + " Time: " + Timer.getFPGATimestamp());
//        lastTurnErrors.add(Math.abs(turnController.getSetpoint() - ahrs.getYaw()));
        if (turnController.onTarget() || currentState != DriveControlState.AUTONOMOUS_TURNING) {
            return true;
        }
        setLeftRightPower(-turnReceiver.getOutput(), turnReceiver.getOutput());
        return false;
    }

    public void finishTurn() {
        turnController.disable();
        setOpenLoop(DriveSignal.NEUTRAL);
    }



}
