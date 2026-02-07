package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ToucherXD {

    public final DcMotorEx diskMotor;  // made public so teleop can access if needed
    private final DigitalChannel touch;

    private static final int SPACING_TICKS = 50; // distance per step
    private static final double MOTOR_POWER = 0.4;

    private boolean wasPressedLastLoop = false; // for touch sensor edge detection

    public ToucherXD(HardwareMap hardwareMap) {
        diskMotor = hardwareMap.get(DcMotorEx.class, "diskMotor");
        touch = hardwareMap.get(DigitalChannel.class, "toucherSensor");
        touch.setMode(DigitalChannel.Mode.INPUT);
    }

    public void initialize() {
        diskMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        diskMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        diskMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        diskMotor.setPower(0);
    }

    // Call every loop to check touch sensor
    public void update() {
        boolean pressed = !touch.getState(); // sensor active low

        // Move one step on rising edge
        if (pressed && !wasPressedLastLoop) {
            moveOneStep();
        }

        // Stop motor when target reached
        if (diskMotor.getMode() == DcMotorEx.RunMode.RUN_TO_POSITION && !diskMotor.isBusy()) {
            diskMotor.setPower(0);
            diskMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }

        wasPressedLastLoop = pressed;
    }

    /**
     * Move diskMotor forward by SPACING_TICKS
     * Can be called by teleop button or touch sensor
     */
    public void moveOneStep() {
        int currentPOS = diskMotor.getCurrentPosition();
        int targetPOS = currentPOS + SPACING_TICKS;

        diskMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        diskMotor.setTargetPosition(targetPOS);
        diskMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        diskMotor.setPower(MOTOR_POWER);
    }

    // -------------------------
    // Diagnostic getters
    // -------------------------
    public int getCurrentEncoderPosition() {
        return diskMotor.getCurrentPosition();
    }

    public int getTargetPosition() {
        return diskMotor.getTargetPosition();
    }

    public boolean isTouchPressed() {
        return !touch.getState();
    }

    public double getMotorPower() {
        return diskMotor.getPower();
    }

    public int getSpacingTicks() {
        return SPACING_TICKS;
    }
}