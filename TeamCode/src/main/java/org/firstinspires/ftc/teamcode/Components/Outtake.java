package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Outtake class for controlling:
 * - 1 shooter motor (ON/OFF with D-Pad)
 * - 1 shooter servo (4 preset angles)
 * - 1 continuous rotation servo (Lazy Susan L2/R2)
 */
public class Outtake {

    // ---------- Hardware ----------
    private final DcMotorEx shooterMotor;  // spins shooter
    public final Servo shooterServo;       // angle adjustment
    public final Servo lazySusanServo;     // continuous rotation

    // ---------- Shooter servo angle presets ----------
    private final double ANGLE_1 = 0.2; // X button
    private final double ANGLE_2 = 0.4; // Y button
    private final double ANGLE_3 = 0.6; // A button
    private final double ANGLE_4 = 0.8; // B button

    // ---------- Lazy Susan speed ----------
    private final double LAZY_SPEED = 0.5; // range 0.0-1.0, 0.5 = stop, >0.5 = forward, <0.5 = reverse

    // ---------- Shooter motor power ----------
    private final double MOTOR_POWER = 1.0;

    /**
     * Constructor: initialize hardware
     */
    public Outtake(HardwareMap hardwareMap) {
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shooterMotor");
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");
        lazySusanServo = hardwareMap.get(Servo.class, "lazySusanServo");
    }

    /**
     * Initialize hardware
     */
    public void initialize() {
        // Shooter motor
        shooterMotor.setPower(0);
        shooterMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        // Shooter servo
        shooterServo.setPosition(ANGLE_1);

        // Lazy Susan stop
        lazySusanServo.setPower(0); // continuous servo stop
    }

    /**
     * Call this every loop in TeleOp
     */
    public void update(Gamepad gamepad) {

        // ---------- Shooter motor ON/OFF ----------
        if (gamepad.dpad_up) shooterMotor.setPower(MOTOR_POWER);
        else if (gamepad.dpad_down) shooterMotor.setPower(0);

        // ---------- Shooter servo angles ----------
        if (gamepad.x) shooterServo.setPosition(ANGLE_1);
        else if (gamepad.y) shooterServo.setPosition(ANGLE_2);
        else if (gamepad.a) shooterServo.setPosition(ANGLE_3);
        else if (gamepad.b) shooterServo.setPosition(ANGLE_4);

        // ---------- Lazy Susan continuous rotation ----------
        if (gamepad.left_trigger > 0.1) {
            lazySusanServo.setPower(-LAZY_SPEED); // rotate left
        } else if (gamepad.right_trigger > 0.1) {
            lazySusanServo.setPower(LAZY_SPEED);  // rotate right
        } else {
            lazySusanServo.setPower(0);           // stop
        }
    }

    /**
     * Optional getters
     */
    public double getMotorPower() {
        return shooterMotor.getPower();
    }

    public double getShooterServoPosition() {
        return shooterServo.getPosition();
    }

    public double getLazySusanPower() {
        return lazySusanServo.getPower();
    }
}
