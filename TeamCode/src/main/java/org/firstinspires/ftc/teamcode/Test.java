
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


@TeleOp (name="Template: Iterative OpMode", group=" Iterative Opmode")  // @Autonomous(...) is the other common choice



public class Test extends LinearOpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime liftTimer = new ElapsedTime();
    private ElapsedTime angerTimer = new ElapsedTime();
    private DcMotor backleftMotor = null;
    private DcMotor frontleftMotor = null;
    private DcMotor frontrightMotor = null;
    private DcMotor backrightMotor = null;
    private int gear = 0;


    /*
     * Code to run ONCE when the driver hits INIT
     */


    @Override
    public void runOpMode() {
        telemetry.addLine("Power online. All systems ready to roll. TestBed V0.83");
        frontleftMotor = hardwareMap.dcMotor.get("Front Left Motor");
        frontrightMotor = hardwareMap.dcMotor.get("Front Right Motor");
        backleftMotor = hardwareMap.dcMotor.get("Back Left Motor");
        backrightMotor = hardwareMap.dcMotor.get("Back Right Motor");


        backrightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontrightMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
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
            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            leftPower = Range.clip(drive + turn, -1.0, 1.0);
            rightPower = Range.clip(drive - turn, -1.0, 1.0);
            if (gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0) {
                frontleftMotor.setPower(leftPower * .15);
                backleftMotor.setPower(leftPower * .15);
                frontrightMotor.setPower(rightPower * .15);
                backrightMotor.setPower(rightPower * .15);
            }
            if (gamepad1.left_trigger == 0 && gamepad1.right_trigger > 1) {
                frontleftMotor.setPower(leftPower * .5);
                backleftMotor.setPower(leftPower * .5);
                frontrightMotor.setPower(rightPower * .5);
                backrightMotor.setPower(rightPower * .5);
            }
            if (gamepad1.left_trigger > 1 && gamepad1.right_trigger > 1) {
                frontleftMotor.setPower(leftPower);
                backleftMotor.setPower(leftPower);
                frontrightMotor.setPower(rightPower);
                backrightMotor.setPower(rightPower);
            }












    /*
     * Code to run ONCE after the driver hits STOP
     */


        }
    }
}





