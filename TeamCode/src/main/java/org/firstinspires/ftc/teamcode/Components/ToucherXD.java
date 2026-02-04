package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ToucherXD {

    private final DcMotorEx diskMotor;
    private final TouchSensor touch;

    private static final int SPACING_TICKS = 250; // distance between positions
    private static final int NUM_POSITIONS = 3;

    private int currentState = 0;
    private boolean wasPressedLastLoop = false;

    private static final double MOTOR_POWER = 0.4;

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
        moveToState(0);
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
        currentState = (currentState + 1) % NUM_POSITIONS;
        moveToState(currentState);
    }

    public void moveToState(int state) {
        if (state < 0 || state >= NUM_POSITIONS) return;

        int targetPosition = state * SPACING_TICKS;

        diskMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        diskMotor.setTargetPosition(targetPosition);
        diskMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        diskMotor.setPower(MOTOR_POWER);
    }

    public int getCurrentState() {
        return currentState;
    }
}
