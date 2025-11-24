package org.firstinspires.ftc.teamcode.pedroPathing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Components.DriveTrain;

@Autonomous(name = "park_auton")
public class park_auton extends LinearOpMode {

    private DriveTrain drivetrain;

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new DriveTrain(hardwareMap);
        drivetrain.initialize();

        telemetry.addLine("Ready for start");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {

            // ----------------------
            // Calculate forward distance along Y
            // ----------------------
            double startY = 8.112676056338033;
            double endY = 26.076458752515087;
            double distanceInches = endY - startY; // positive forward

            double wheelCircumference = 4 * Math.PI; // 4-inch wheels
            double ticksPerRev = 537.7; // REV HD Hex Encoder
            double gearRatio = 1.0;

            double rotations = distanceInches / wheelCircumference;
            double ticks = rotations * ticksPerRev * gearRatio;

            // ----------------------
            // Reset encoders
            // ----------------------
            drivetrain.leftFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            drivetrain.leftBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            drivetrain.rightFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            drivetrain.rightBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

            // ----------------------
            // Set target positions
            // ----------------------
            drivetrain.leftFront.setTargetPosition((int) ticks);
            drivetrain.leftBack.setTargetPosition((int) ticks);
            drivetrain.rightFront.setTargetPosition((int) ticks);
            drivetrain.rightBack.setTargetPosition((int) ticks);

            // ----------------------
            // Run to position
            // ----------------------
            drivetrain.leftFront.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            drivetrain.leftBack.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            drivetrain.rightFront.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            drivetrain.rightBack.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

            // Set motor power
            drivetrain.leftFront.setPower(0.5);
            drivetrain.leftBack.setPower(0.5);
            drivetrain.rightFront.setPower(0.5);
            drivetrain.rightBack.setPower(0.5);

            // ----------------------
            // Wait until all motors reach target OR timeout
            // ----------------------
            long startTime = System.currentTimeMillis();
            long timeout = 5000; // 5 seconds

            while (opModeIsActive() &&
                    (drivetrain.leftFront.isBusy() ||
                            drivetrain.leftBack.isBusy() ||
                            drivetrain.rightFront.isBusy() ||
                            drivetrain.rightBack.isBusy()) &&
                    (System.currentTimeMillis() - startTime < timeout)) {

                telemetry.addData("LF", drivetrain.leftFront.getCurrentPosition());
                telemetry.addData("LB", drivetrain.leftBack.getCurrentPosition());
                telemetry.addData("RF", drivetrain.rightFront.getCurrentPosition());
                telemetry.addData("RB", drivetrain.rightBack.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motors
            drivetrain.leftFront.setPower(0);
            drivetrain.leftBack.setPower(0);
            drivetrain.rightFront.setPower(0);
            drivetrain.rightBack.setPower(0);

            telemetry.addLine("Reached target Y");
            telemetry.update();
            sleep(1000);
        }
    }
}
