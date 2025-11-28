package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.DriveTrain;
import org.firstinspires.ftc.teamcode.Components.Intake;
import org.firstinspires.ftc.teamcode.Components.Outtake;
import org.firstinspires.ftc.teamcode.Components.Pusher;

@TeleOp(name = "TestTeleOp1")
public class TestTeleOp1 extends LinearOpMode {

    private DriveTrain drivetrain;
    private Intake intake;
    private Outtake outtake;
    private Pusher pusher;

    @Override
    public void runOpMode() {

        // ----------------------------
        // Initialize all subsystems
        // ----------------------------
        drivetrain = new DriveTrain(hardwareMap);
        intake = new Intake(hardwareMap);
        outtake = new Outtake(hardwareMap);
        pusher = new Pusher(hardwareMap);

        drivetrain.initialize();
        intake.initialize();
        outtake.initialize();
        pusher.initialize();

        telemetry.addLine("Ready. Waiting for start...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // ----------------------------
            // DRIVE TRAIN CONTROL
            // ----------------------------
            double movement = gamepad1.left_stick_y;
            double rotation = gamepad1.right_stick_x;
            double strafe = gamepad1.left_stick_x;
            boolean precision = gamepad1.right_bumper;

            drivetrain.TeleOpControl(precision, movement, rotation, strafe);

            // ----------------------------
            // INTAKE CONTROL
            // ----------------------------
            if (gamepad2.dpad_up) {
                intake.intakeIn();  // pull in
            } else if (gamepad2.dpad_down) {
                intake.intakeOut(); // push out
            } else {
                intake.stop();
            }

            // ----------------------------
            // OUTTAKE CONTROL (revised)
            // ----------------------------
            if (gamepad2.y) {
                outtake.shootForward(); // full speed forward
            } else if (gamepad2.a) {
                outtake.shootReverse(); // slower reverse
            } else {
                outtake.stop();
            }

            // ----------------------------
            // PUSHER CONTROL
            // ----------------------------
            if (gamepad2.right_trigger > 0.1) {
                pusher.pushUp();
            } else if (gamepad2.left_trigger > 0.1) {
                pusher.pushDown();
            }

            // ----------------------------
            // TELEMETRY
            // ----------------------------
            telemetry.addData("Pusher Pos", pusher.pusher.getPosition());
            telemetry.addData("Intake Power", intake.intake.getPower());
            telemetry.addData("Outtake Power", outtake.outtakeMotor.getPower());
            telemetry.update();
        }
    }
}
