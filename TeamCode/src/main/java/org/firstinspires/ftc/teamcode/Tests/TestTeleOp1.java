package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Components.DriveTrain;
import org.firstinspires.ftc.teamcode.Components.Intake;
import org.firstinspires.ftc.teamcode.Components.Pusher;
import org.firstinspires.ftc.teamcode.Components.ToucherXD;

@TeleOp(name = "TestTeleOp1")
public class TestTeleOp1 extends LinearOpMode {

    private DriveTrain drivetrain;
    private Intake intake;
    private Pusher pusher;
    private ToucherXD toucherXD;

    // Turret Hardware
    private DcMotorEx turretMotor;
    private Servo shooterServo;
    private DcMotorEx shooterMotor;

    // Shooter Preset Angles
    private final double ANGLE_1 = 0.2;
    private final double ANGLE_2 = 0.4;
    private final double ANGLE_3 = 0.6;
    private final double ANGLE_4 = 0.8;

    private double currentAngle;

    @Override
    public void runOpMode() {

        // Initialize Components
        drivetrain = new DriveTrain(hardwareMap);
        intake = new Intake(hardwareMap);
        pusher = new Pusher(hardwareMap);
        toucherXD = new ToucherXD(hardwareMap);

        drivetrain.initialize();
        intake.initialize();
        pusher.initialize();
        toucherXD.initialize();

        // Turret Hardware
        turretMotor = hardwareMap.get(DcMotorEx.class, "turretMotor");
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");

        turretMotor.setPower(0);
        shooterMotor.setPower(0);

        currentAngle = ANGLE_1;
        shooterServo.setPosition(currentAngle);

        telemetry.addLine("READY...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // ----------------------------
            // DRIVING
            // ----------------------------
            double movement = -gamepad1.left_stick_y; // Forward/back
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
            // TURRET ROTATION
            // ----------------------------
            if (gamepad2.left_trigger > 0.1)
                turretMotor.setPower(-0.5);
            else if (gamepad2.right_trigger > 0.1)
                turretMotor.setPower(0.5);
            else
                turretMotor.setPower(0);

            // ----------------------------
            // SHOOTER SERVO ANGLES
            // ----------------------------
            if (gamepad2.y)      currentAngle = ANGLE_1;
            else if (gamepad2.b) currentAngle = ANGLE_2;
            else if (gamepad2.a) currentAngle = ANGLE_3;
            else if (gamepad2.x) currentAngle = ANGLE_4;

            shooterServo.setPosition(currentAngle);

            // ----------------------------
            // SHOOTER MOTOR ON/OFF
            // ----------------------------
            shooterMotor.setPower(gamepad2.right_bumper ? 1.0 : 0.0);

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
            // TELEMETRY
            // ----------------------------
            telemetry.addData("Turret Power", turretMotor.getPower());
            telemetry.addData("Shooter Servo Angle", currentAngle);
            telemetry.addData("Shooter Motor", shooterMotor.getPower());
            telemetry.addData("Disk State", toucherXD.getCurrentState());
            telemetry.update();
        }
    }
}
