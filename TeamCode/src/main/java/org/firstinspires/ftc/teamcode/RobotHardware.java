package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Gus Teter on 12/5/2017.
 */

public class RobotHardware {
    //Public OpMode members
    public DcMotor motorBackLeft = null;                         //variables set to null before action
    public DcMotor motorFrontLeft = null;
    public DcMotor motorFrontRight = null;
    public DcMotor motorBackRight = null;
    public DcMotor motorIntakeOne = null;
    public DcMotor motorIntakeTwo = null;
    public Servo servoJewel = null;
    public DcMotor motorLift = null;
    
    //Local OpMode members
    public ElapsedTime runtime = new ElapsedTime();
    public ElapsedTime liftTimer = new ElapsedTime();
    public ElapsedTime angerTimer = new ElapsedTime();
    HardwareMap hwMap  = null;
    
    public RobotHardware(){
    }
    
    public void init(HardwareMap ahwMap) {
        
        hwMap = ahwMap;

        motorFrontLeft = hwMap.dcMotor.get("Front Left Motor");                  //connects motor to phone
        motorFrontRight = hwMap.dcMotor.get("Front Right Motor");
        motorBackLeft = hwMap.dcMotor.get("Back Left Motor");
        motorBackRight = hwMap.dcMotor.get("Back Right Motor");
        motorIntakeOne = hwMap.dcMotor.get("Intake 1");
        motorIntakeTwo = hwMap.dcMotor.get("Intake 2");
        motorLift = hwMap.dcMotor.get("Lift Motor");
        servoJewel = hwMap.servo.get("Jewel Servo");

        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);                      //encoder connections
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

    }
    
}
