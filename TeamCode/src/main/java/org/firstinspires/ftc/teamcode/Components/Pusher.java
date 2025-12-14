//package org.firstinspires.ftc.teamcode.Components;
//
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
//
//public class Pusher {
//
//    public Servo pusher;
//
//    private final double PUSH_UP_POS = 0.9;    // adjust for your robot
//    private final double PUSH_DOWN_POS = 0.3;   // adjust for your robot
//
//    public Pusher(HardwareMap hardwareMap) {
//        pusher = hardwareMap.get(Servo.class, "pusher");
//    }
//
//    public void initialize() {
//        pusher.setPosition(PUSH_DOWN_POS);  // start down
//    }
//
//    // Move to preset positions
//    public void pushUp() {
//        pusher.setPosition(PUSH_UP_POS);
//    }
//
//    public void pushDown() {
//        pusher.setPosition(PUSH_DOWN_POS);
//    }
//}
package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Pusher {

    public Servo pusher;

    // Position constants - adjust these for your robot
    private final double PUSH_UP_POS = 0.9;      // R1 position
    private final double PUSH_DOWN_POS = 0.3;    // L1 position
    private final double PUSH_MIDDLE_POS = 0.6;  // Default/off position

    // Current position state
    private double currentPosition;

    // Button state tracking for edge detection
    private boolean lastR1State = false;
    private boolean lastL1State = false;

    // Track which position we're in
    private enum PusherState {
        UP,
        DOWN,
        MIDDLE
    }
    private PusherState currentState = PusherState.MIDDLE;

    public Pusher(HardwareMap hardwareMap) {
        pusher = hardwareMap.get(Servo.class, "pusher");
    }

    public void initialize() {
        // Start at middle position
        currentPosition = PUSH_MIDDLE_POS;
        currentState = PusherState.MIDDLE;
        pusher.setPosition(currentPosition);
    }

    /**
     * Update method to be called in OpMode loop
     * R1 (right_bumper) toggles between UP and MIDDLE
     * L1 (left_bumper) toggles between DOWN and MIDDLE
     */
    public void update(Gamepad gamepad) {
        boolean r1Pressed = gamepad.right_bumper;
        boolean l1Pressed = gamepad.left_bumper;

        // R1 rising edge detection - toggle UP/MIDDLE
        if (r1Pressed && !lastR1State) {
            if (currentState == PusherState.UP) {
                // Already up, move to middle
                pushMiddle();
            } else {
                // Move to up position
                pushUp();
            }
        }

        // L1 rising edge detection - toggle DOWN/MIDDLE
        if (l1Pressed && !lastL1State) {
            if (currentState == PusherState.DOWN) {
                // Already down, move to middle
                pushMiddle();
            } else {
                // Move to down position
                pushDown();
            }
        }

        // Update button states for next loop
        lastR1State = r1Pressed;
        lastL1State = l1Pressed;
    }

    // Move to preset positions
    public void pushUp() {
        currentPosition = PUSH_UP_POS;
        currentState = PusherState.UP;
        pusher.setPosition(currentPosition);
    }

    public void pushDown() {
        currentPosition = PUSH_DOWN_POS;
        currentState = PusherState.DOWN;
        pusher.setPosition(currentPosition);
    }

    public void pushMiddle() {
        currentPosition = PUSH_MIDDLE_POS;
        currentState = PusherState.MIDDLE;
        pusher.setPosition(currentPosition);
    }

    // Get current position for telemetry
    public double getCurrentPosition() {
        return currentPosition;
    }

    // Get current state as string for telemetry
    public String getStateString() {
        switch (currentState) {
            case UP:
                return "UP (R1)";
            case DOWN:
                return "DOWN (L1)";
            case MIDDLE:
                return "MIDDLE";
            default:
                return "UNKNOWN";
        }
    }

    // Get the servo object directly if needed
    public Servo getServo() {
        return pusher;
    }
}
