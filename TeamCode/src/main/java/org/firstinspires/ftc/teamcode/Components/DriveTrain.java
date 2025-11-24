package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveTrain {

    public DcMotorEx leftFront;
    public DcMotorEx leftBack;
    public DcMotorEx rightBack;
    public DcMotorEx rightFront;

    public double leftErrorAdjustment = 1.0;
    public double rightErrorAdjustment = 1.0;
/*
    public LinearOpMode parent;

    public Telemetry telemetry;

 */

    public DriveTrain(HardwareMap hardwareMap) {

        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");

    }

    public void initialize() {

        rightFront.setPower(0);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);

        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

    }

    public void TeleOpControl(boolean precision, double movement, double rotation, double strafe) {
        double magnitude = Math.sqrt(Math.pow(strafe, 2) + Math.pow(movement, 2));
        double direction = Math.atan2(strafe, -movement);

        double lf = magnitude * Math.sin(direction + Math.PI / 4) + rotation;
        double lb = magnitude * Math.cos(direction + Math.PI / 4) + rotation;
        double rf = magnitude * Math.cos(direction + Math.PI / 4) - rotation;
        double rb = magnitude * Math.sin(direction + Math.PI / 4) - rotation;

        double hypot = Math.hypot(movement, strafe);
        double ratio;
        if (movement == 0 && strafe == 0)
            ratio = 1;
        else if (precision)
            ratio = hypot / (Math.max(Math.max(Math.max(Math.abs(lf), Math.abs(lb)), Math.abs(rb)), Math.abs(rf))) / 2;
        else
            ratio = hypot / (Math.max(Math.max(Math.max(Math.abs(lf), Math.abs(lb)), Math.abs(rb)), Math.abs(rf)));

        leftFront.setPower(ratio * lf * leftErrorAdjustment);
        leftBack.setPower(ratio * lb * leftErrorAdjustment);
        rightFront.setPower(ratio * rf * rightErrorAdjustment);
        rightBack.setPower(ratio * rb * rightErrorAdjustment);
    }
}