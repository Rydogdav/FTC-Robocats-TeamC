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

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@Autonomous (name="VuforiaTest", group=" Iterative Opmode")  // @Autonomous(...) is the other common choice



public class VuforiaTest extends LinearOpMode {
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

    public static final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    public ColorSensor colorSensor = null;
    RobotHardware robot = new RobotHardware();
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

        RobotHardware robot = new RobotHardware();

        testColor.enableLed(true);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AW7cBtn/////AAAAGYMkfBwkBk+RtHulA/YtafKMtOkKRJGL0uSzzGVGQDzIzhkvOQY4lJnPIwwP7MBF5k8AKuhF0QwYx4nlBgTOlMv23OSHNfvy9tE" +
                "0Egigzpbi7p0zUS3bgP9XPd8IsdyQQhwdQQFY64eZiNxqstMPQqOhCyZ+MuQjWWiW1gAeGaPNxD8sUUSFbcP0F0LfTWY8JYNFDjr7vUfB8koqwsWCYrB8gQH9ZAwVRDQlHbpx4z1ZE" +
                "ySLnDCRXX0Ns4R1PktDJeHJq4Xj0wInNVNPLwCSXaw/yLDkPGa+IWws63uWwr3h4TaxJEs4zgqqbTyCVDPgeAtjmwT6KCrrlsS6kW1czPF8bNWq4U5BdinbKBZGrKS7\n";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");

        VuforiaTrackable relicTemplate = relicTrackables.get(0);

        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        waitForStart();  //if the code starts early you get disqualified

        relicTrackables.activate();

        while(opModeIsActive()){
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

            if(vuMark == RelicRecoveryVuMark.CENTER || vuMark == RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addLine("Center");
                telemetry.update();

               /* robot.colorSensor.enableLed(true);
                KnockJewel();
                robot.colorSensor.enableLed(false);
                sleep(1000);
                DriveToMark1(distanceToCenterBox);
                Turn30(-3);
                encoderDrive(robot.DRIVE_SPEED_LEFT, robot.DRIVE_SPEED_RIGHT, 6,6, 5);
                robot.gripServo1.setPosition(.39);
                robot.gripServo2.setPosition(.55);
                encoderDrive(robot.DRIVE_SPEED_LEFT, robot.DRIVE_SPEED_RIGHT, -3, -3, 5);*/
                robot.init(hardwareMap);
                colorSensor = hardwareMap.colorSensor.get("Color Sensor");





                robot.servoJewel.setPosition(0.1);
                sleep(1000);     // pause for servos to move
                if(colorSensor.blue() > colorSensor.red()){ // if the sensor picks up more blue then red, we can assume the red jewel is on the right, so we drive forward
                    robot.motorFrontLeft.setPower(-1);
                    robot.motorBackLeft.setPower(-1);
                    robot.motorFrontRight.setPower(-1);
                    robot.motorBackRight.setPower(-1);
                    sleep(500);
                    robot.motorFrontLeft.setPower(0);
                    robot.motorBackLeft.setPower(0);
                    robot.motorFrontRight.setPower(0);
                    robot.motorBackRight.setPower(0);
                }
                if(colorSensor.blue() < colorSensor.red()) { // if the sensor picks up more red then blue, we can assume the red jewel is on the left, so we drive backward
                    robot.motorFrontLeft.setPower(1);
                    robot.motorBackLeft.setPower(1);
                    robot.motorFrontRight.setPower(1);
                    robot.motorBackRight.setPower(1);
                    sleep(500);
                    robot.motorFrontLeft.setPower(0);
                    robot.motorBackLeft.setPower(0);
                    robot.motorFrontRight.setPower(0);
                    robot.motorBackRight.setPower(0);
                }
                robot.servoJewel.setPosition(.8);
                sleep(2000);
                telemetry.addLine("Heyo");
                telemetry.update();

                telemetry.addData("Path", "Complete");
                telemetry.update();
            }

            if (vuMark == RelicRecoveryVuMark.LEFT) {
                telemetry.addLine("Left");
                telemetry.update();

               /* robot.colorSensor.enableLed(true);
                KnockJewel();
                robot.colorSensor.enableLed(false);
                sleep(1000);
                DriveToMark1(distanceToCenterBox - 5);
                Turn30(-3);
                encoderDrive(robot.DRIVE_SPEED_LEFT, robot.DRIVE_SPEED_RIGHT, 6,6, 5);
                robot.gripServo1.setPosition(.39);
                robot.gripServo2.setPosition(.55);
                encoderDrive(robot.DRIVE_SPEED_LEFT, robot.DRIVE_SPEED_RIGHT, -3, -3, 5);*/
            }
            if (vuMark == RelicRecoveryVuMark.RIGHT) {
                telemetry.addLine("Right");
                telemetry.update();

                /*robot.colorSensor.enableLed(true);
                KnockJewel();
                robot.colorSensor.enableLed(false);
                sleep(1000);
                DriveToMark1(distanceToCenterBox + 5);
                Turn30(-3);
                encoderDrive(robot.DRIVE_SPEED_LEFT, robot.DRIVE_SPEED_RIGHT, 6,6, 5);
                robot.gripServo1.setPosition(.39);
                robot.gripServo2.setPosition(.55);
                encoderDrive(robot.DRIVE_SPEED_LEFT, robot.DRIVE_SPEED_RIGHT, -3, -3, 5);*/
            }

            motorFrontLeft.setPower(.5);
            motorFrontRight.setPower(.5);
            motorBackLeft.setPower(.5);
            motorBackRight.setPower(.5);
            sleep(2000);
            motorFrontLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackLeft.setPower(0);
            motorBackRight.setPower(0);



        }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    }
}
