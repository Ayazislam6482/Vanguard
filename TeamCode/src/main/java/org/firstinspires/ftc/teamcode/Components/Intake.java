package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
/*
    private LinearOpMode parent;
    private Telemetry telemetry;

 */

    public DcMotorEx intake;

    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, "intake");
    }

    public void initialize() {

        // ensure motor is not moving during init
        stop();

        // Set direction (change FORWARD ↔ REVERSE if intake spins wrong way)
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        intake.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        // Uncomment if you ever want encoder-based control:
        // intake.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        // intake.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }

    // Pull game pieces in
    public void intakeIn() {
        intake.setPower(1.0);
    }

    // Push game pieces out
    public void intakeOut() {
        intake.setPower(-1.0);
    }

    // Stop the intake motor
    public void stop() {
        intake.setPower(0);
    }
}
