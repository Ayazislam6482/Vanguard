package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ToucherXD {

    private final DcMotorEx diskMotor;
    private final TouchSensor touch;

    // Target positions for the 3 holes
    private final int POS_0 = 100;
    private final int POS_1 = 350;
    private final int POS_2 = 620;

    private int currentState = 0;           // 0 → 1st hole, 1 → 2nd hole, 2 → 3rd hole
    private boolean wasPressedLastLoop = false;

    private final double MOTOR_POWER = 0.4; // adjustable motor power

    public ToucherXD(HardwareMap hardwareMap) {
        diskMotor = hardwareMap.get(DcMotorEx.class, "diskMotor");
        touch = hardwareMap.get(TouchSensor.class, "toucherSensor");
    }

    public void initialize() {
        // Reset encoder
        diskMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        // Start in RUN_USING_ENCODER mode initially
        diskMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        // Set initial target position (but don't enable RUN_TO_POSITION yet)
        currentState = 0;
        diskMotor.setTargetPosition(POS_0);

        // Set initial power to 0 - motor will be off
        diskMotor.setPower(0);
    }

    public void update() {
        boolean isPressed = touch.isPressed();

        // Rising edge detection: button pressed now, not last loop
        if (isPressed && !wasPressedLastLoop) {
            advancePosition();
        }

        // Check if motor has reached target and stop it
        if (diskMotor.getMode() == DcMotorEx.RunMode.RUN_TO_POSITION) {
            if (!diskMotor.isBusy()) {
                // Motor reached target, turn it off
                diskMotor.setPower(0);
                // Switch back to RUN_USING_ENCODER mode for next move
                diskMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            }
        }

        wasPressedLastLoop = isPressed;
    }

    private void advancePosition() {
        // Move to next hole
        currentState = (currentState + 1) % 3;

        int target = POS_0; // default
        switch (currentState) {
            case 0: target = POS_0; break;
            case 1: target = POS_1; break;
            case 2: target = POS_2; break;
        }

        // CORRECT ORDER for RUN_TO_POSITION:
        // 1. FIRST set the target position
        diskMotor.setTargetPosition(target);

        // 2. THEN set motor power
        diskMotor.setPower(MOTOR_POWER);

        // 3. FINALLY enable RUN_TO_POSITION mode
        diskMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    public int getCurrentState() {
        return currentState;
    }

    // Optional: Manually move to a specific state
    public void moveToState(int state) {
        if (state < 0 || state > 2) return;

        currentState = state;
        int target = POS_0;
        switch (currentState) {
            case 0: target = POS_0; break;
            case 1: target = POS_1; break;
            case 2: target = POS_2; break;
        }

        diskMotor.setTargetPosition(target);
        diskMotor.setPower(MOTOR_POWER);
        diskMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }
}