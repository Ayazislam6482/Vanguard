package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {

    public DcMotorEx outtakeMotor;

    private final double FORWARD_POWER = 1.0;   // full speed forward
    private final double REVERSE_POWER = -0.5;  // slower reverse

    public Outtake(HardwareMap hardwareMap) {
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "Outtake");
    }

    public void initialize() {
        stop(); // ensure motor is not running

        // Set motor direction
        outtakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        // Brake when power is zero
        outtakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    // Spin forward at full speed
    public void shootForward() {
        outtakeMotor.setPower(FORWARD_POWER);
    }

    // Spin backward at slower speed
    public void shootReverse() {
        outtakeMotor.setPower(REVERSE_POWER);
    }

    // Stop the motor
    public void stop() {
        outtakeMotor.setPower(0);
    }
}
