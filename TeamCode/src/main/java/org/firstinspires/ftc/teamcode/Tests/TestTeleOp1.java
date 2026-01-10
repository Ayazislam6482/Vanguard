package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

    // Turret Hardware (legacy)
    private com.qualcomm.robotcore.hardware.DcMotorEx turretMotor;

    @Override
    public void runOpMode() {

        // ----------------------------
        // Initialize Components
        // ----------------------------
        drivetrain = new DriveTrain(hardwareMap);
        intake = new Intake(hardwareMap);
        pusher = new Pusher(hardwareMap);
        toucherXD = new ToucherXD(hardwareMap);
        outtake = new Outtake(hardwareMap); // NEW Outtake class

        drivetrain.initialize();
        intake.initialize();
        pusher.initialize();
        toucherXD.initialize();
        outtake.initialize();

        // Turret motor (legacy)
        turretMotor = hardwareMap.get(com.qualcomm.robotcore.hardware.DcMotorEx.class, "turretMotor");
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
            if (gamepad2.dpad_up)      intake.intakeIn();
            else if (gamepad2.dpad_down) intake.intakeOut();
            else intake.stop();

            // ----------------------------
            // TURRET ROTATION (legacy)
            // ----------------------------
            if (gamepad2.left_trigger > 0.1)
                turretMotor.setPower(-0.5);
            else if (gamepad2.right_trigger > 0.1)
                turretMotor.setPower(0.5);
            else
                turretMotor.setPower(0);

            // ----------------------------
            // PUSHER
            // ----------------------------
            if (gamepad2.right_stick_y > 0.1) pusher.pushUp();
            else if (gamepad2.right_stick_y < -0.1) pusher.pushDown();

            // ----------------------------
            // TOUCHERXD UPDATE
            // ----------------------------
            toucherXD.update();

            // ----------------------------
            // OUTTAKE UPDATE (shooter + Lazy Susan)
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
            telemetry.update();
        }
    }
}
