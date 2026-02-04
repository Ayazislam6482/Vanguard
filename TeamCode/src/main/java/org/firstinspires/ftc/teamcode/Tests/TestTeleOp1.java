package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Components.DriveTrain;
import org.firstinspires.ftc.teamcode.Components.Intake;
import org.firstinspires.ftc.teamcode.Components.Pusher;
import org.firstinspires.ftc.teamcode.Components.ToucherXD;
import org.firstinspires.ftc.teamcode.Components.Outtake;

@TeleOp(name = "TestTeleOp1")
public class TestTeleOp1 extends LinearOpMode {

    private DriveTrain drivetrain;
    private Intake intake;
    private Pusher pusher;
    private ToucherXD toucherXD;
    private Outtake outtake;

    private DcMotorEx turretMotor;

    @Override
    public void runOpMode() {

        // ----------------------------
        // Initialize Components
        // ----------------------------
        drivetrain = new DriveTrain(hardwareMap);
        intake = new Intake(hardwareMap);
        pusher = new Pusher(hardwareMap);
        toucherXD = new ToucherXD(hardwareMap);
        outtake = new Outtake(hardwareMap);

        drivetrain.initialize();
        intake.initialize();
        pusher.initialize();
        toucherXD.initialize();

        turretMotor = hardwareMap.get(DcMotorEx.class, "turretMotor");
        turretMotor.setPower(0);

        telemetry.addLine("READY...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // ----------------------------
            // DRIVING
            // ----------------------------
            double movement = -gamepad1.left_stick_y;
            double rotation = gamepad1.right_stick_x;
            double strafe   = gamepad1.left_stick_x;
            boolean precision = gamepad1.right_bumper;

            drivetrain.TeleOpControl(precision, movement, rotation, strafe);

            // ----------------------------
            // INTAKE
            // ----------------------------
            if (gamepad2.dpad_up) intake.intakeIn();
            else if (gamepad2.dpad_down) intake.intakeOut();
            else intake.stop();

            // ----------------------------
            // TURRET ROTATION
            // ----------------------------
            if (gamepad2.left_trigger > 0.1) turretMotor.setPower(-0.5);
            else if (gamepad2.right_trigger > 0.1) turretMotor.setPower(0.5);
            else turretMotor.setPower(0);

            // ----------------------------
            // PUSHER
            // ----------------------------
            pusher.update(gamepad2);

            // ----------------------------
            // TOUCHERXD UPDATE
            // ----------------------------
            toucherXD.update();

            // ----------------------------
            // OUTTAKE UPDATE
            // ----------------------------
            outtake.update(gamepad2);

            // ----------------------------
            // TELEMETRY
            // ----------------------------
            telemetry.addData("Turret Power", turretMotor.getPower());
            telemetry.addData("Shooter Motor Power", outtake.getMotorPower());
            telemetry.addData("Shooter Servo Angle", outtake.getShooterServoPosition());
            telemetry.addData("Lazy Susan Power", outtake.getLazySusanPower());
            telemetry.addData("Disk State", toucherXD.getCurrentState());
            telemetry.addData("Pusher State", pusher.getStateString());
            telemetry.addData("Pusher Position", pusher.getCurrentPosition());

            // ----------------------------
            // TOUCHER DIAGNOSTIC TELEMETRY
            // ----------------------------
            telemetry.addLine("=== TOUCHER DIAGNOSTIC ===");
            telemetry.addData("Touch Sensor Pressed", toucherXD.isTouchPressed());
            telemetry.addData("Target Position", toucherXD.getTargetPosition());
            telemetry.addData("Current Position", toucherXD.getCurrentEncoderPosition());
            telemetry.addData("Motor Power", toucherXD.getMotorPower());
            telemetry.addData("Spacing Ticks", toucherXD.getSpacingTicks());

            telemetry.update();
        }
    }
}
