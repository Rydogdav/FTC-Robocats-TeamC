package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;


@TeleOp (name="Test Bed Teleop(3 with lift)", group=" Iterative Opmode")  // @Autonomous(...) is the other common choice



public class LiftTest extends LinearOpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime liftTimer = new ElapsedTime();
    private ElapsedTime angerTimer = new ElapsedTime();
    private DcMotor backleftMotor = null;                         //variables set to null before action
    private DcMotor frontleftMotor = null;
    private DcMotor frontrightMotor = null;
    private DcMotor backrightMotor = null;
    private DcMotor intake1 = null;
    private DcMotor intake2 = null;
    private DcMotor liftmotor = null;
    //Need a servo variable here
    private int gear = 0;                                         //gear set to zero before action


    /*
     * Code to run ONCE when the driver hits INIT
     */


    @Override
    public void runOpMode() {
        telemetry.addLine("Power online. All systems ready to roll. TestBed V0.83");   //all systems go message
        frontleftMotor = hardwareMap.dcMotor.get("Front Left Motor");                  //connects motor to phone
        frontrightMotor = hardwareMap.dcMotor.get("Front Right Motor");
        backleftMotor = hardwareMap.dcMotor.get("Back Left Motor");
        backrightMotor = hardwareMap.dcMotor.get("Back Right Motor");
        intake1 = hardwareMap.dcMotor.get("Intake 1");
        intake2 = hardwareMap.dcMotor.get("Intake 2");
        liftmotor = hardwareMap.dcMotor.get("Lift Motor");
        liftmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //need hardware map for drop servo


        backrightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontrightMotor.setDirection(DcMotor.Direction.REVERSE);
        //servo stuff needed
        double leftPower;
        double rightPower;
        int bheight = 0;
        int cheight = 0;
        int dheight = 0;
        waitForStart();
        //if the code starts early you get disqualified
        runtime.reset();

        leftPower = -gamepad1.left_stick_y;
        rightPower = -gamepad1.right_stick_y;

        frontleftMotor.setPower(leftPower);
        backleftMotor.setPower(leftPower);
        frontrightMotor.setPower(rightPower);
        backleftMotor.setPower(rightPower);

        /*button mapping is as follows
            a = level 1 of the cryptobox
            b = level 2 of the cryptobox
            x = level 3 of the cryptobox
            y = level 4 of the cryptobox
            left bumper = intake glyph
            right bumper = spit out glyph

        */


        if(gamepad1.a){
            while(liftmotor.getCurrentPosition() > 10){
                liftmotor.setPower(-.20);
            }
        }
        if(gamepad1.b){
            while(liftmotor.getCurrentPosition() < bheight){
                liftmotor.setPower(.20);
            }
        }
        if(gamepad1.x){
            while(liftmotor.getCurrentPosition() < cheight){
                liftmotor.setPower(.20);
            }
        }
        if(gamepad1.y){
            while(liftmotor.getCurrentPosition() < dheight){
                liftmotor.setPower(.20);
            }
        }
        if(gamepad1.left_bumper){
            intake1.setPower(1);
            intake2.setPower(1);
        }
        if(gamepad1.right_bumper){
            intake1.setPower(-1);
            intake2.setPower(-1);
        }
        if(!gamepad1.left_bumper && !gamepad1.right_bumper){
            intake1.setPower(0);
            intake2.setPower(0);
        }
           /* if (gamepad1.right_bumper == false && gamepad1.left_bumper == false) {   //lowest gear, speed at .15 percent power
                frontleftMotor.setPower(leftPower * .15);
                backleftMotor.setPower(leftPower * .15);
                frontrightMotor.setPower(rightPower * .15);
                backrightMotor.setPower(rightPower * .15);
            }
            if (gamepad1.right_bumper == false && gamepad1.left_bumper == true) {    //middle gear, speed set at half power
                frontleftMotor.setPower(leftPower * .5);
                backleftMotor.setPower(leftPower * .5);
                frontrightMotor.setPower(rightPower * .5);
                backrightMotor.setPower(rightPower * .5);
            }
            if (gamepad1.right_bumper == true && gamepad1.left_bumper == true) {   //highest gear, speed at full power
                frontleftMotor.setPower(leftPower);
                backleftMotor.setPower(leftPower);
                frontrightMotor.setPower(rightPower);
                backrightMotor.setPower(rightPower);
            } */


    }
}