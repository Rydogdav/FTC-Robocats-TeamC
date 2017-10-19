
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;



@Autonomous (name="AutoBlueAway", group=" Iterative Opmode")  // @Autonomous(...) is the other common choice



public class AutoBlueAway extends LinearOpMode {
    /* Declare OpMode members. */
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime liftTimer = new ElapsedTime();
    private ElapsedTime angerTimer = new ElapsedTime();
    private DcMotor motorBackLeft = null;                         //variables set to null before action
    private DcMotor motorFrontLeft = null;
    private DcMotor motorBackRight = null;
    private DcMotor motorFrontRight = null;
    private Servo servoJewel = null;
    private ColorSensor testColor = null;
    private int gear = 0;                                         //gear set to zero before action


    /*
     * Code to run ONCE when the driver hits INIT
     */


    @Override
    public void runOpMode() {
        telemetry.addLine("Power online. All systems ready to roll. TestBed V0.83");   //all systems go message
        motorFrontLeft = hardwareMap.dcMotor.get("Front Left Motor");                  //connects motor to phone
        motorFrontRight = hardwareMap.dcMotor.get("Front Right Motor");
        motorBackLeft = hardwareMap.dcMotor.get("Back Left Motor");
        motorBackRight = hardwareMap.dcMotor.get("Back Right Motor");
        servoJewel = hardwareMap.servo.get("Jewel Arm");
        testColor.enableLed(true);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();  //if the code starts early you get disqualified


    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */












    /*
     * Code to run ONCE after the driver hits STOP
     */


        }
    }

