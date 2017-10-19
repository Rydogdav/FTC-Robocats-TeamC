
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


@TeleOp (name="Test Bed Colorsensor", group=" Iterative Opmode")  // @Autonomous(...) is the other common choice



public class Test extends LinearOpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime liftTimer = new ElapsedTime();
    private ElapsedTime angerTimer = new ElapsedTime();
    private DcMotor motorBackLeft = null;                         //variables set to null before action
    private DcMotor motorFrontLeft = null;
    private DcMotor motorFrontRight = null;
    private DcMotor motorBackRight = null;
    private ColorSensor testColor = null;
    private Servo servoJewel = null;
    private double servoToAngle = 1/255; //the Ryan algorithm
    private int gear = 0;   //gear set to zero before action
    private int color = 0;  //color set to zero before action


    /*
     * Code to run ONCE when the driver hits INIT
     */


    @Override
    public void runOpMode() {
        telemetry.addLine("Power online. All systems ready to roll. TestBed V0.83");   //all systems go message
        motorFrontLeft = hardwareMap.dcMotor.get("Front Left Motor");                  //connects motors to phone
        motorFrontRight = hardwareMap.dcMotor.get("Front Right Motor");
        motorBackLeft = hardwareMap.dcMotor.get("Back Left Motor");
        motorBackRight = hardwareMap.dcMotor.get("Back Right Motor");
        testColor = hardwareMap.colorSensor.get("Color Sensor");                        //connects sensors to phone
        servoJewel = hardwareMap.servo.get("Jewel Arm");                                  //connects servo to phone
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.FORWARD);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        testColor.enableLed(true); //we're looking for colors, not beacons






        waitForStart();
        //if the code starts early you get disqualified
        runtime.reset();
    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
        while (opModeIsActive()) {


            telemetry.addData("It's been ", "running Setup1 " + runtime.toString(), " seconds without an issue."); //more telemetry feedback

            double leftPower;
            double rightPower;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            leftPower = -gamepad1.left_stick_y;
            rightPower = -gamepad1.right_stick_y;
            telemetry.addData("Left", motorFrontLeft.getCurrentPosition());
            telemetry.addData("Right", motorFrontRight.getCurrentPosition());

            //for testing purposes only, will only function in autonomous in final
            telemetry.addData("Blue value: ", testColor.blue()); //blue value detector
            telemetry.addData("Red value: ", testColor.red()); //red value detector

            telemetry.update();


                //since this is a test bed and not the final code, these controls test out the jewel arm, which will be used in autonomous
                if (gamepad1.dpad_up){  //up and out of the way
                    servoJewel.setPosition(0);
                }
                if (gamepad1.dpad_down){  //down, ready to knock over the jewel
                    servoJewel.setPosition(servoToAngle * 128);
                }



            if (gamepad1.right_bumper == false && gamepad1.left_bumper == false) {   //lowest gear, speed at .15 percent power
                motorFrontLeft.setPower(leftPower * .15);
                motorBackLeft.setPower(leftPower * .15);
                motorFrontRight.setPower(rightPower * .15);
                motorBackRight.setPower(rightPower * .15);
            }
            if (gamepad1.right_bumper == false && gamepad1.left_bumper == true) {    //middle gear, speed set at half power
                motorFrontLeft.setPower(leftPower * .5);
                motorBackLeft.setPower(leftPower * .5);
                motorFrontRight.setPower(rightPower * .5);
                motorBackRight.setPower(rightPower * .5);
            }
            if (gamepad1.right_bumper == true && gamepad1.left_bumper == true) {   //highest gear, speed at full power
                motorFrontLeft.setPower(leftPower);
                motorBackLeft.setPower(leftPower);
                motorFrontRight.setPower(rightPower);
                motorBackRight.setPower(rightPower);
            } 

            if (gamepad1.y){   //motor reset
                motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }


            telemetry.update();  //feedback is good


            //lift system code coming soon.

            //
        }
    }
}