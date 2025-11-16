package org.firstinspires.ftc.teamcode.Decode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Decode.AutonomousPLUS;

@Autonomous(group = "Basic", name = "Blue Auton") //TODO name your auto
public class _auton_blue extends AutonomousPLUS {

    public void runOpMode() { 

        super.runOpMode(); // Robot constructor

        waitForStart(); 

        //TODO add your step-by step code here using functions from AutonomousPLUS
        
        // Starting pose: (36.61, 135.74) heading 90 degrees
        // Path 1: Move to (68, 83)
        moveToPosition(68, 83, 180, 0.5);
        prepareNextAction(1000);
        
        // Path 2: Move to (20.53, 83.97)
        moveToPosition(20.53, 83.97, 180, 0.5);
        prepareNextAction(1000);
        
        // Path 3: Move to (68, 83)
        moveToPosition(68, 83, -180, 0.5);
        prepareNextAction(1000);
        
        // Path 4: Move to (44.79, 60.03)
        moveToPosition(44.79, 60.03, -180, 0.5);
        prepareNextAction(1000);
        
        // Path 5: Move to (19.90, 59.40)
        moveToPosition(19.90, 59.40, -180, 0.5);
        prepareNextAction(1000);
        
        // Path 6: Move to (68, 83)
        moveToPosition(68, 83, -180, 0.5);
        prepareNextAction(1000);
        
        // Path 7: Move to (43.37, 35.11)
        moveToPosition(43.37, 35.11, -180, 0.5);
        prepareNextAction(1000);
        
        // Path 8: Move to (18.40, 35.30)
        moveToPosition(18.40, 35.30, -180, 0.5);
        prepareNextAction(1000);
        
        // Path 9: Move to (68, 83)
        moveToPosition(68, 83, -180, 0.5);
        prepareNextAction(1000);

        // end auto

    }

    // if you want to add any functions unique to this auto, do it here

}
