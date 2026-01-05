package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Components.DriveTrain;
import org.firstinspires.ftc.teamcode.Components.Intake;
import org.firstinspires.ftc.teamcode.Components.Pusher;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TestTeleOp1")
public class TestTeleOp1 extends LinearOpMode {

    private DriveTrain drivetrain;
    private Intake intake;
    private Pusher pusher;

    // Turret parts
    private DcMotorEx turretMotor;    // rotates platform
    private Servo shooterServo;       // changes shooting angle
    private DcMotorEx shooterMotor;   // single motor on/off for firing

    // Shooter servo angles
    private final double ANGLE_1 = 0.2;  // lowest, default
    private final double ANGLE_2 = 0.4;
    private final double ANGLE_3 = 0.6;
    private final double ANGLE_4 = 0.8;

    private double currentAngle;

    @Override
    public void runOpMode() {

        // ----------------------------
        // Initialize subsystems
        // ----------------------------
        drivetrain = new DriveTrain(hardwareMap);
        intake = new Intake(hardwareMap);
        pusher = new Pusher(hardwareMap);

        drivetrain.initialize();
        intake.initialize();
        pusher.initialize();

        // Turret setup
        turretMotor = hardwareMap.get(DcMotorEx.class, "turretMotor");
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");

        turretMotor.setPower(0);
        shooterMotor.setPower(0);

        // Start shooter servo at lowest angle
        currentAngle = ANGLE_1;
        shooterServo.setPosition(currentAngle);

        telemetry.addLine("Ready. Waiting for start...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // ----------------------------
            // DRIVE TRAIN CONTROL
            // ----------------------------
            double movement = -gamepad1.left_stick_y;
            double rotation = -gamepad1.right_stick_x;
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
            // TURRET ROTATION CONTROL
            // ----------------------------
            if (gamepad2.left_trigger > 0.1) {
                turretMotor.setPower(-0.5); // rotate left
            } else if (gamepad2.right_trigger > 0.1) {
                turretMotor.setPower(0.5);  // rotate right
            } else {
                turretMotor.setPower(0);
            }

            // ----------------------------
            // SHOOTER ANGLE CONTROL (4 presets)
            // ----------------------------
            if (gamepad2.y) currentAngle = ANGLE_1;
            else if (gamepad2.b) currentAngle = ANGLE_2;
            else if (gamepad2.a) currentAngle = ANGLE_3;
            else if (gamepad2.x) currentAngle = ANGLE_4;

            shooterServo.setPosition(currentAngle);

            // ----------------------------
            // SHOOTER MOTOR ON/OFF
            // ----------------------------
            if (gamepad2.right_bumper) {
                shooterMotor.setPower(1.0);  // motor on
            } else {
                shooterMotor.setPower(0);    // motor off
            }

            // ----------------------------
            // PUSHER CONTROL
            // ----------------------------
            if (gamepad2.right_stick_y > 0.1) pusher.pushUp();
            else if (gamepad2.right_stick_y < -0.1) pusher.pushDown();

            // ----------------------------
            // TELEMETRY
            // ----------------------------
            telemetry.addData("Turret Power", turretMotor.getPower());
            telemetry.addData("Shooter Servo Angle", currentAngle);
            telemetry.addData("Shooter Motor Power", shooterMotor.getPower());
            telemetry.addData("Intake Power", intake.intake.getPower());
            telemetry.addData("Pusher Pos", pusher.pusher.getPosition());
            telemetry.update();
        }
    }
}
