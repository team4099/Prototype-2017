package org.usfirst.frc.team4099.robot;

public class Constants {

    public class Loopers {
        public static final double LOOPER_DT = 1.0 / 100.0;
    }

    public class Drive {
        public static final int LEFT_BACK_TALON_PORT = 1;
        public static final int LEFT_FRONT_TALON_PORT = 2;
        public static final int RIGHT_FRONT_TALON_PORT = 3;
        public static final int RIGHT_BACK_TALON_PORT = 4;

        public static final int RIGHT_ENC_A_CHAN = 1;
        public static final int RIGHT_ENC_B_CHAN = 2;

        public static final int LEFT_ENC_A_CHAN = 4;
        public static final int LEFT_ENC_B_CHAN = 5;

        public static final double LEFT_ENCODER_DISTANCE_PER_PULSE = 1;
        public static final double RIGHT_ENCODER_DISTANCE_PER_PULSE = 1;

        public static final double TURN_TOLERANCE_DEGREES = 2;
        public static final double FORWARD_TOLERANCE_METERS = .05;

        public static final double AUTO_TURN_MAX_POWER = .35;
        public static final double AUTO_FORWARD_MAX_POWER = .5;
    }

    public class Joysticks {
        public static final int DRIVER_PORT = 1;
    }

    public class Gains {
        public static final double TURN_P = 0.0115;
        public static final double TURN_I = 0.0000;
        public static final double TURN_D = 0.0000;
        public static final double TURN_F = 0.0000;

        public static final double FORWARD_P = 0.0115;
        public static final double FORWARD_I = 0.0000;
        public static final double FORWARD_D = 0.0000;
        public static final double FORWARD_F = 0.0000;

    }

}
