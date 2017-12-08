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
import com.qualcomm.robotcore.hardware.HardwareMap;
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
   // public double servoToAngle = 1.0/255.0; //the Ryan algorithm
    RobotHardware robot = new RobotHardware();

    //Need a servo variable here


    /*
     * Code to run ONCE when the driver hits INIT
     */


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        telemetry.addLine("Power online. All systems ready to roll. TestBed V3");   //all systems go message
     /*   motorFrontLeft = hardwareMap.dcMotor.get("Front Left Motor");                  //connects motor to phone
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
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE); */


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


            robot.motorFrontLeft.setPower(leftPower);
            robot.motorBackLeft.setPower(leftPower);
            robot.motorFrontRight.setPower(rightPower);
            robot.motorBackRight.setPower(rightPower);

            /*BUTTON MAPPING
            LSTICK -------- LEFT MOTORS
            RSTICK -------- RIGHT MOTORS
            LBUMPER ------- SPEED UP
            RBUMPER ------- SLOW DOWN
            LTRIGGER ------ LIFT WHEEL INTAKE
            RTRIGGER ------ LIFT WHEEL OUTTAKE
            A ------------- LIFT SYSTEM UP
            B ------------- LIFT SYSTEM DOWN
            X ------------- HELLO WORLD
            Y ------------- MOTOR RESET


             */

            if (gamepad1.a) {
                robot.motorLift.setPower(.15);
                idle();
            }
            if (gamepad1.b){
                robot.motorLift.setPower(-.15);
                idle();
            }
            if (!gamepad1.a && !gamepad1.b){
                robot.motorLift.setPower(0);
                idle();
            }
            if (gamepad1.a && gamepad1.b){
                robot.motorLift.setPower(0);
                idle();
            }
            if (gamepad1.x){
                hello();
                hello2();
                hello3();
            }

           if (gamepad1.dpad_right){
               robot.servoJewel.setPosition(0);
           }
            if (gamepad1.dpad_left){
                robot.servoJewel.setPosition(.65);
            }


            if (gamepad1.y){   //motor reset
                robot.motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.motorBackRight.setDirection(DcMotor.Direction.REVERSE);
                robot.motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
                idle();
            }



            if (gamepad1.left_bumper) {
                robot.motorIntakeOne.setPower(1);
                robot.motorIntakeTwo.setPower(-1);
                idle();
            }
            if (gamepad1.right_bumper) {
                robot.motorIntakeOne.setPower(-1);
                robot.motorIntakeTwo.setPower(1);
                idle();
            }
            if (!gamepad1.left_bumper && !gamepad1.right_bumper) {
                robot.motorIntakeOne.setPower(0);
                robot.motorIntakeTwo.setPower(0);
                idle();
            }

            telemetry.addData("FrontLeftPos", robot.motorFrontLeft.getCurrentPosition());
            telemetry.addData("FrontRightPos", robot.motorFrontRight.getCurrentPosition());
            telemetry.addData("BackLeftPos", robot.motorBackLeft.getCurrentPosition());
            telemetry.addData("BackRightPos", robot.motorBackRight.getCurrentPosition());
            telemetry.update();
    /*
            if(gamepad1.left_trigger >= 0.5){                                     //Eventually trigger shift will be working,  as of 11/28/17, motors are not running. fix it!!!!!!!
                speedVar = speedVar * 0.6;
                idle();
            }

            else if(gamepad1.right_trigger >= 0.5){
                speedVar = speedVar * 1.4;
                idle();
            }
            else{
                speedVar = 0.7;
                idle();
            }
    */
      idle();
        }


    }
    public void hello() {
        telemetry.addLine("Hello, world!");
    }
    public void hello2() {
        telemetry.addLine("Bonjour le monde!");
    }
    public void hello3() { telemetry.addLine("你好，世界!");
    }
    }
