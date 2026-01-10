package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;

public class Outtake {

    // ---------- Hardware ----------
    public DcMotorEx turretMotor;
    public Servo shooterServo;
    public DcMotorEx shooterMotor;

    // ---------- Shooter angle presets ----------
    private final double ANGLE_1 = 0.2; // start
    private final double ANGLE_2 = 0.4;
    private final double ANGLE_3 = 0.6;
    private final double ANGLE_4 = 0.8;

    // ---------- Turret rotation speed ----------
    private final double TURRET_SPEED = 0.6;

    // ---------- Shooter motor power ----------
    private final double SHOOTER_POWER = 1.0;

    public Outtake(HardwareMap hardwareMap) {
        turretMotor = hardwareMap.get(DcMotorEx.class, "turretMotor");
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");
    }

    public void initialize() {
        // Stop everything initially
        turretMotor.setPower(0);
        shooterMotor.setPower(0);
        shooterServo.setPosition(ANGLE_1); // default angle

        // Directions
        turretMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        // Brake when power is zero
        turretMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        shooterMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    // Call this in teleop loop
    public void update(Gamepad gamepad) {

        // ---------- Turret rotation ----------
        if (gamepad.left_trigger > 0.1) {
            turretMotor.setPower(-TURRET_SPEED); // rotate left
        } else if (gamepad.right_trigger > 0.1) {
            turretMotor.setPower(TURRET_SPEED);  // rotate right
        } else {
            turretMotor.setPower(0);              // stop
        }

        // ---------- Shooter angle ----------
        if (gamepad.y) {
            shooterServo.setPosition(ANGLE_1);
        } else if (gamepad.b) {
            shooterServo.setPosition(ANGLE_2);
        } else if (gamepad.a) {
            shooterServo.setPosition(ANGLE_3);
        } else if (gamepad.x) {
            shooterServo.setPosition(ANGLE_4);
        }

        // ---------- Shooter motor ----------
        if (gamepad.right_bumper) {
            shooterMotor.setPower(SHOOTER_POWER); // spin
        } else if (gamepad.left_bumper) {
            shooterMotor.setPower(0);             // stop
        }
    }
}
