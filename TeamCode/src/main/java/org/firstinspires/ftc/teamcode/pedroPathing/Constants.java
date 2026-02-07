package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.control.PIDCoefficients;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {

    public static Follower createFollower(HardwareMap hardwareMap) {

        FollowerConstants constants = new FollowerConstants();

        // Translation PID
        constants.translationPID = new PIDCoefficients(
                0.045,   // kP
                0.0,     // kI
                0.003    // kD
        );

        // Heading PID
        constants.headingPID = new PIDCoefficients(
                2.0,     // kP
                0.0,     // kI
                0.08     // kD
        );

        // Feedforward
        constants.kV = 0.015;
        constants.kA = 0.002;

        // Motion constraints
        constants.maxVelocity = 70;
        constants.maxAcceleration = 70;
        constants.maxAngularVelocity = Math.toRadians(270);
        constants.maxAngularAcceleration = Math.toRadians(360);

        return new Follower(hardwareMap, constants);
    }
}