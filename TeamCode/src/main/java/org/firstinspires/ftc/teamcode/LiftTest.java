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

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;


@TeleOp (name="Test Bed Teleop(3 with lift)", group=" Iterative Opmode")  // @Autonomous(...) is the other common choice



public class LiftTest extends LinearOpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime liftTimer = new ElapsedTime();
    private ElapsedTime angerTimer = new ElapsedTime();
    private DcMotor motorBackLeft = null;                         //variables set to null before action
    private DcMotor motorFrontLeft = null;
    private DcMotor motorFrontRight = null;
    private DcMotor motorBackRight = null;
    private DcMotor motorIntakeOne = null;
    private DcMotor motorIntakeTwo = null;
    private Servo servoJewel = null;
    private double servoToAngle = 1/255; //the Ryan algorithm
    private DcMotor motorLift = null;

    //Need a servo variable here
    private int gear = 0;                                         //gear set to zero before action


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

        //need hardware map for drop servo


        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        //servo stuff needed
        double leftPower;
        double rightPower;
        int bheight = 0;
        int cheight = 0;
        int dheight = 0;
        waitForStart();
        //if the code starts early you get disqualified
        runtime.reset();

        while (opModeIsActive()) {

           leftPower = -gamepad1.left_stick_y;
            rightPower = -gamepad1.right_stick_y;

            if (gamepad1.dpad_up){  //up and out of the way
                servoJewel.setPosition(0);
            }
            if (gamepad1.dpad_down){  //down, ready to knock over the jewel
                servoJewel.setPosition(servoToAngle * 128);
            }

            motorFrontLeft.setPower(leftPower);
            motorBackLeft.setPower(leftPower);
            motorFrontRight.setPower(rightPower);
            motorBackRight.setPower(rightPower);
            if (gamepad1.a) {
                motorLift.setPower(.15);
            }
            if (gamepad1.b){
                motorLift.setPower(-.15);
            }
            if (!gamepad1.a && !gamepad1.b){
                motorLift.setPower(0);
            }
            if (gamepad1.a && gamepad1.b){
                motorLift.setPower(0);
            }
            if (gamepad1.x){
                hello();
                hello2();
                hello3();
            }


            if (gamepad1.y){   //motor reset
                motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
            }
            if (gamepad1.right_bumper) {
                motorIntakeOne.setPower(-1);
                motorIntakeTwo.setPower(1);
            }

            if (!gamepad1.left_bumper && !gamepad1.right_bumper) {
                motorIntakeOne.setPower(0);
                motorIntakeTwo.setPower(0);
            }

            telemetry.addData("Left", motorFrontLeft.getCurrentPosition());
            telemetry.addData("Right", motorFrontRight.getCurrentPosition());
            telemetry.update();

        /*if (gamepad1.right_bumper == false && gamepad1.left_bumper == false) {   //lowest gear, speed at .15 percent power
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
*/       idle();
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
