package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class ToucherXD {

    private final DcMotorEx diskMotor;
    private final TouchSensor touch;

    // Encoder positions for each disk hole
    private final int POS_0 = 100;
    private final int POS_1 = 350;
    private final int POS_2 = 620;

    private int currentState = 0; // 0 → 1st hole, 1 → 2nd hole, 2 → 3rd hole
    private boolean wasPressedLastLoop = false;

    public ToucherXD(HardwareMap hardwareMap) {
        diskMotor = hardwareMap.get(DcMotorEx.class, "diskMotor");
        touch = hardwareMap.get(TouchSensor.class, "toucherSensor");
    }

    public void initialize() {
        diskMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        diskMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        currentState = 0;
        diskMotor.setTargetPosition(POS_0);
        diskMotor.setPower(0.4);
    }

    public void update() {
        boolean isPressed = touch.isPressed();

        // Detect rising edge
        if (isPressed && !wasPressedLastLoop) {
            advancePosition();
        }

        wasPressedLastLoop = isPressed;
    }

    private void advancePosition() {
        currentState = (currentState + 1) % 3;

        int target;
        switch (currentState) {
            case 0: target = POS_0; break;
            case 1: target = POS_1; break;
            case 2: target = POS_2; break;
            default: target = POS_0; break;
        }

        diskMotor.setTargetPosition(target);
        diskMotor.setPower(0.4);
    }

    public int getCurrentState() {
        return currentState;
    }
}
