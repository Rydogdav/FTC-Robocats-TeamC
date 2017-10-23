
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@Autonomous (name="AutoRedNearJewel", group=" Iterative Opmode")  // @Autonomous(...) is the other common choice



public class AutoRedNearJewel extends LinearOpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime liftTimer = new ElapsedTime();
    private ElapsedTime angerTimer = new ElapsedTime();
    private DcMotor motorBackLeft = null;                         //variables set to null before action
    private DcMotor motorFrontLeft = null;
    private DcMotor motorFrontRight = null;
    private DcMotor motorBackRight = null;
    private Servo servoJewel = null;
    private ColorSensor testColor = null;
    private int gear = 0;                                         //gear set to zero before action
    OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia;

    /*
     * Code to run ONCE when the driver hits INIT
     */


    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addLine("Power online. All systems ready to roll. TestBed V0.83");   //all systems go message
        motorFrontLeft = hardwareMap.dcMotor.get("Front Left Motor");                  //connects motor to phone
        motorFrontRight = hardwareMap.dcMotor.get("Front Right Motor");
        motorBackLeft = hardwareMap.dcMotor.get("Back Left Motor");
        motorBackRight = hardwareMap.dcMotor.get("Back Right Motor");
        servoJewel = hardwareMap.servo.get("Jewel Servo");
        testColor.enableLed(true);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();  //if the code starts early you get disqualified
        /*
        servoJewel.setPosition(.5);
        sleep(500);

        if(testColor.blue() > testColor.red()){ // if the sensor picks up more blue then red, we can assume the red jewel is on the right, so we drive forward
            motorFrontLeft.setPower(1);
            motorBackLeft.setPower(1);
            motorFrontRight.setPower(1);
            motorBackRight.setPower(1);
            sleep(500);
            motorFrontLeft.setPower(0);
            motorBackLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackRight.setPower(0);
        }
        if(testColor.blue() < testColor.red()){ // if the sensor picks up more red then blue, we can assume the red jewel is on the left, so we drive backward
            motorFrontLeft.setPower(-1);
            motorBackLeft.setPower(-1);
            motorFrontRight.setPower(-1);
            motorBackRight.setPower(-1);
            sleep(500);
            motorFrontLeft.setPower(0);
            motorBackLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackRight.setPower(0);
        }
        int direction = 0;
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        while(direction == 0) {
            if (vuMark == RelicRecoveryVuMark.LEFT) direction = 1;
            if (vuMark == RelicRecoveryVuMark.CENTER) direction = 2;
            if (vuMark == RelicRecoveryVuMark.RIGHT) direction = 3;
        }
    */

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */













    /*
     * Code to run ONCE after the driver hits STOP
     */


        }
    }

