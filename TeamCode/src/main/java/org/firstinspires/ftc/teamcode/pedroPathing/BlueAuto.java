package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(group = "Autonomous", name = "BlueAuton")
public class BlueAuto extends OpMode {
        private Follower follower;
        private Path goToEnd;

        private final Pose startPose =
                new Pose(20.82138517618469, 129.65249088699878, Math.toRadians(180));
        private final Pose endPose =
                new Pose(47.59173754556501, 111.9805589307412, Math.toRadians(180));

        @Override
        public void init() {
            follower = Constants.createFollower(hardwareMap);
            follower.setStartingPose(startPose);

            goToEnd = new Path(new BezierLine(startPose, endPose));
            goToEnd.setConstantInterpolation(startPose.getHeading());
        }

        @Override
        public void start() {
            follower.followPath(goToEnd);
        }

        @Override
        public void loop() {
            follower.update();
        }
    }
}