package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
//import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;


@TeleOp (name="Test Bed Teleop(3 with lift)", group=" Iterative Opmode")  // @Autonomous(...) is the other common choice



public class LiftTest extends LinearOpMode {
    /* Declare OpMode members. */
    public ElapsedTime runtime = new ElapsedTime();
    public ElapsedTime liftTimer = new ElapsedTime();
    public ElapsedTime angerTimer = new ElapsedTime();
    public DcMotor motorBackLeft = null;                         //variables set to null before action
    public DcMotor motorFrontLeft = null;
    public DcMotor motorFrontRight = null;
    public DcMotor motorBackRight = null;
    public DcMotor motorIntakeOne = null;
    public DcMotor motorIntakeTwo = null;
    public Servo servoJewel = null;
   // public double servoToAngle = 1.0/255.0; //the Ryan algorithm
    public DcMotor motorLift = null;

    //Need a servo variable here


    /*
     * Code to run ONCE when the driver hits INIT
     */


    @Override
    public void runOpMode() {
        telemetry.addLine("Power online. All systems ready to roll. TestBed V0.9 (Almost lift)");   //all systems go message
        motorFrontLeft = hardwareMap.dcMotor.get("Front Left Motor");                  //connects motor to phone
        motorFrontRight = hardwareMap.dcMotor.get("Front Right Motor");
        motorBackLeft = hardwareMap.dcMotor.get("Back Left Motor");
        motorBackRight = hardwareMap.dcMotor.get("Back Right Motor");
        motorIntakeOne = hardwareMap.dcMotor.get("Intake 1");
        motorIntakeTwo = hardwareMap.dcMotor.get("Intake 2");
        motorLift = hardwareMap.dcMotor.get("Lift Motor");
        servoJewel = hardwareMap.servo.get("Jewel Servo");

        motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);                      //encoder connections
        motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //need hardware map for drop servo


        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        //servo stuff needed
        double leftPower;
        double rightPower;

        double speedVar = 0.7;
        waitForStart();
        //if the code starts early you get disqualified
        runtime.reset();

        while (opModeIsActive()) {

            leftPower = -gamepad1.left_stick_y;
            rightPower = -gamepad1.right_stick_y;

          /*  if (gamepad1.dpad_up){  //up and out of the way
                servoJewel.setPosition(0);
                idle();
            }
            if (gamepad1.dpad_down){  //down, ready to knock over the jewel
                servoJewel.setPosition(servoToAngle * 128.0);
                idle();

            }
*/
            motorFrontLeft.setPower(leftPower * speedVar);
            motorBackLeft.setPower(leftPower * speedVar);
            motorFrontRight.setPower(rightPower * speedVar);
            motorBackRight.setPower(rightPower * speedVar);

            if (gamepad1.a) {
                motorLift.setPower(.15);
                idle();
            }
            if (gamepad1.b){
                motorLift.setPower(-.15);
                idle();
            }
            if (!gamepad1.a && !gamepad1.b){
                motorLift.setPower(0);
                idle();
            }
            if (gamepad1.a && gamepad1.b){
                motorLift.setPower(0);
                idle();
            }
            if (gamepad1.x){
                hello();
                hello2();
                hello3();
            }

           if (gamepad1.dpad_right){
               servoJewel.setPosition(0);
           }
            if (gamepad1.dpad_left){
                servoJewel.setPosition(.65);
            }


            if (gamepad1.y){   //motor reset
                motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                idle();
            }

        /*button mapping is as follows
            a = level 1 of the cryptobox
            b = level 2 of the cryptobox
            x = level 3 of the cryptobox
            y = level 4 of the cryptobox
            left bumper = intake glyph
            right bumper = spit out glyph

        */

/*
          /  if (gamepad1.a) {
                while (motorLift.getCurrentPosition() > 10) {
                    motorLift.setPower(-.20);
                }
                motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motorLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            if (gamepad1.b) {
                while (motorLift.getCurrentPosition() < bheight) {
                    motorLift.setPower(.20);
                }
            }
            if (gamepad1.x) {
                while (motorLift.getCurrentPosition() < cheight) {
                    motorLift.setPower(.20);
                }
            }
            if (gamepad1.y) {
                while (motorLift.getCurrentPosition() < dheight) {
                    motorLift.setPower(.20);
                }
            }
*/
            if (gamepad1.left_bumper) {
                motorIntakeOne.setPower(1);
                motorIntakeTwo.setPower(-1);
                idle();
            }
            if (gamepad1.right_bumper) {
                motorIntakeOne.setPower(-1);
                motorIntakeTwo.setPower(1);
                idle();
            }
            if (!gamepad1.left_bumper && !gamepad1.right_bumper) {
                motorIntakeOne.setPower(0);
                motorIntakeTwo.setPower(0);
                idle();
            }

            telemetry.addData("FrontLeftPos", motorFrontLeft.getCurrentPosition());
            telemetry.addData("FrontRightPos", motorFrontRight.getCurrentPosition());
            telemetry.addData("BackLeftPos", motorBackLeft.getCurrentPosition());
            telemetry.addData("BackRightPos", motorBackRight.getCurrentPosition());
            telemetry.update();

            if(gamepad1.left_trigger >= 0.5){                                     //Eventually trigger shift will be working,  as of 11/28/17, motors are not running. fix it!!!!!!!
                speedVar = speedVar * 0.6;

            }

            else if(gamepad1.right_trigger >= 0.5){
                speedVar = speedVar * 1.4;

            }
            else{
                speedVar = 0.7;
            }

      idle();
        }


    }
    public void hello() {
        telemetry.addLine("Hello, world!");
    }
    public void hello2() {
        telemetry.addLine("Bonjour le monde!");
    }
    public void hello3() {
        telemetry.addLine("你好，世界!");
    }
    }
