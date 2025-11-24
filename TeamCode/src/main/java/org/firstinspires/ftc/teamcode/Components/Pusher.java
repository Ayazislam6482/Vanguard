package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Pusher {

    public Servo pusher;

    private final double PUSH_UP_POS = 0.9;    // adjust for your robot
    private final double PUSH_DOWN_POS = 0.3;   // adjust for your robot

    public Pusher(HardwareMap hardwareMap) {
        pusher = hardwareMap.get(Servo.class, "pusher");
    }

    public void initialize() {
        pusher.setPosition(PUSH_DOWN_POS);  // start down
    }

    // Move to preset positions
    public void pushUp() {
        pusher.setPosition(PUSH_UP_POS);
    }

    public void pushDown() {
        pusher.setPosition(PUSH_DOWN_POS);
    }
}
