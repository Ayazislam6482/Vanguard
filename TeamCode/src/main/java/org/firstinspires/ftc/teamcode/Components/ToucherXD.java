package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ToucherXD {

    private final DcMotorEx diskMotor;
    private final TouchSensor touch;

    // Encoder positions for each hole
    private final int POS_0 = 100;
    private final int POS_1 = 350;
    private final int POS_2 = 620;

    private int currentState = 0;
    private boolean wasPressedLastLoop = false;

    private final double MOTOR_POWER = 0.4;

    public ToucherXD(HardwareMap hardwareMap) {
        diskMotor = hardwareMap.get(DcMotorEx.class, "diskMotor");
        touch = hardwareMap.get(TouchSensor.class, "toucherSensor");
    }

    public void initialize() {
        diskMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        diskMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        diskMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        diskMotor.setPower(0);

        currentState = 0;
        diskMotor.setTargetPosition(POS_0);
    }

    public void update() {
        boolean pressed = touch.isPressed();

        if (pressed && !wasPressedLastLoop) {
            advancePosition();
        }

        if (diskMotor.getMode() == DcMotorEx.RunMode.RUN_TO_POSITION && !diskMotor.isBusy()) {
            diskMotor.setPower(0);
            diskMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }

        wasPressedLastLoop = pressed;
    }

    private void advancePosition() {
        currentState = (currentState + 1) % 3;

        int target;
        switch (currentState) {
            case 1: target = POS_1; break;
            case 2: target = POS_2; break;
            default: target = POS_0; break;
        }

        // --- REV-safe command order ---
        diskMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        diskMotor.setTargetPosition(target);
        diskMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        diskMotor.setPower(MOTOR_POWER);
    }

    public int getCurrentState() {
        return currentState;
    }

    public void moveToState(int state) {
        if (state < 0 || state > 2) return;

        currentState = state;

        int target;
        switch (currentState) {
            case 1: target = POS_1; break;
            case 2: target = POS_2; break;
            default: target = POS_0; break;
        }

        diskMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        diskMotor.setTargetPosition(target);
        diskMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        diskMotor.setPower(MOTOR_POWER);
    }
}
