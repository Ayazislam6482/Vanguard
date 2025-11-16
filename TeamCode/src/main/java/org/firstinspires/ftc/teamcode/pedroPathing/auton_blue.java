package org.firstinspires.ftc.teamcode.pedroPathing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Vector2d;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Pose2d;

@Autonomous(name = "auton_blue")
public class _auton_blue extends LinearOpMode {
    @Override
    public void runOpMode() {
        // TODO: Initialize your drive system here
        // Example: Follower follower = new Follower(hardwareMap);
        
        waitForStart();
        
        // Starting pose from your trajectory
        Pose2d startPose = new Pose2d(36.61, 135.74, Math.toRadians(90));
        
        // Build and execute paths
        Path path1 = new PathBuilder(startPose)
            .lineTo(new Vector2d(68, 83))
            .setTangent(Math.toRadians(180))
            .build();
        // follower.followPath(path1);
        
        Path path2 = new PathBuilder(path1.getEndPose())
            .lineTo(new Vector2d(20.53, 83.97))
            .setTangent(Math.toRadians(180))
            .build();
        // follower.followPath(path2);
        
        Path path3 = new PathBuilder(path2.getEndPose())
            .lineTo(new Vector2d(68, 83))
            .setTangent(Math.toRadians(-180))
            .build();
        // follower.followPath(path3);
        
        Path path4 = new PathBuilder(path3.getEndPose())
            .lineTo(new Vector2d(44.79, 60.03))
            .setTangent(Math.toRadians(-180))
            .build();
        // follower.followPath(path4);
        
        Path path5 = new PathBuilder(path4.getEndPose())
            .lineTo(new Vector2d(19.90, 59.40))
            .setTangent(Math.toRadians(-180))
            .build();
        // follower.followPath(path5);
        
        Path path6 = new PathBuilder(path5.getEndPose())
            .lineTo(new Vector2d(68, 83))
            .setTangent(Math.toRadians(-180))
            .build();
        // follower.followPath(path6);
        
        Path path7 = new PathBuilder(path6.getEndPose())
            .lineTo(new Vector2d(43.37, 35.11))
            .setTangent(Math.toRadians(-180))
            .build();
        // follower.followPath(path7);
        
        Path path8 = new PathBuilder(path7.getEndPose())
            .lineTo(new Vector2d(18.40, 35.30))
            .setTangent(Math.toRadians(-180))
            .build();
        // follower.followPath(path8);
        
        Path path9 = new PathBuilder(path8.getEndPose())
            .lineTo(new Vector2d(68, 83))
            .setTangent(Math.toRadians(-180))
            .build();
        // follower.followPath(path9);

        telemetry.addData("Status", "Autonomous Complete");
        telemetry.update();
    }
}
