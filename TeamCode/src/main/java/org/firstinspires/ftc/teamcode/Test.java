
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
    private DcMotor backleftMotor = null;                         //variables set to null before action
    private DcMotor frontleftMotor = null;
    private DcMotor frontrightMotor = null;
    private DcMotor backrightMotor = null;
    private ColorSensor testColor = null;
    private Servo jewelArm = null;
    private int gear = 0;   //gear set to zero before action
    private int color = 0;


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
        testColor = hardwareMap.colorSensor.get("Color Sensor");
        jewelArm = hardwareMap.servo.get("Jewel Arm");
        frontleftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontrightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontleftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontrightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontleftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontrightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        testColor.enableLed(true);






        waitForStart();
        //if the code starts early you get disqualified
        runtime.reset();
    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
        while (opModeIsActive()) {


            telemetry.addData("It's been ", "running Setup1 " + runtime.toString(), " seconds without an issue.");

            double leftPower;
            double rightPower;

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            leftPower = -gamepad1.left_stick_y;
            rightPower = -gamepad1.right_stick_y;
            telemetry.addData("Left", frontleftMotor.getCurrentPosition());
            telemetry.addData("Right", frontrightMotor.getCurrentPosition());
            telemetry.addData("Blue value: ", testColor.blue());
            telemetry.addData("Red value: ", testColor.red());
            telemetry.update();



                if (gamepad1.dpad_up){
                    jewelArm.setPosition(0);
                }
                if (gamepad1.dpad_down){
                    jewelArm.setPosition(.50);
                }




            if (gamepad1.right_bumper == false && gamepad1.left_bumper == false) {   //lowest gear, speed at .15 percent power
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
            } 

            if (gamepad1.y){   //motor reset
                frontleftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                frontrightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                frontleftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                frontrightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            telemetry.update();













    /*
     * Code to run ONCE after the driver hits STOP
     */


        }
    }
}





