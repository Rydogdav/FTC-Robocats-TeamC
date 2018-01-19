package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotHardware;



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;



@Autonomous(name="AutoBlue", group="Autonomous")  // @Autonomous(...) is the other common choice
public class AutoBlu extends LinearOpMode {

    public ColorSensor colorSensor = null;


    RobotHardware robot = new RobotHardware();

    public static final double COUNTS_PER_REV = 1024;    // eg: TETRIX robot.motor Encoder
    // static final double DRIVE_GEAR_REDUCTION = 2.0 ;     // This is < 1.0 if geared UP
    public static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    public static final double COUNTS_PER_INCH = (COUNTS_PER_REV) / (WHEEL_DIAMETER_INCHES * Math.PI);
    public static final double DRIVE_SPEED = 0.6;
    public static final double TURN_SPEED = 0.5;

    public int gear = 0;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        colorSensor = hardwareMap.colorSensor.get("Color Sensor");

        waitForStart();


        robot.servoJewel.setPosition(0.1);
        sleep(2000);     // pause for servos to move
        if (colorSensor.blue() < colorSensor.red()) { // if the sensor picks up more blue then red, we can assume the red jewel is on the right, so we drive forward
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
        if (colorSensor.blue() > colorSensor.red()) { // if the sensor picks up more red then blue, we can assume the red jewel is on the left, so we drive backward
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
        encoderDrive(.6, 25.0, 25.0, 10.0);
        telemetry.addData("Path", "Complete");
        telemetry.update();
    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newBackLeftTarget;
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to robot.motor controller
            newBackLeftTarget = robot.motorBackLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newFrontLeftTarget = robot.motorFrontLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newBackRightTarget = robot.motorBackRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            newFrontRightTarget = robot.motorFrontRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);

            robot.motorBackLeft.setTargetPosition(newBackLeftTarget);
            robot.motorBackRight.setTargetPosition(newBackRightTarget);
            robot.motorBackLeft.setTargetPosition(newFrontLeftTarget);
            robot.motorBackLeft.setTargetPosition(newFrontRightTarget);

            // Turn On RUN_TO_POSITION
            robot.motorFrontLeft.setTargetPosition(newFrontLeftTarget);
            robot.motorFrontRight.setTargetPosition(newFrontRightTarget);
            robot.motorBackRight.setTargetPosition(newBackLeftTarget);
            robot.motorBackLeft.setTargetPosition(newFrontRightTarget);

            // reset the timeout time and start motion.
            robot.runtime.reset();
            robot.motorBackLeft.setPower(speed);
            robot.motorBackRight.setPower(speed);
            robot.motorFrontLeft.setPower(speed);
            robot.motorFrontRight.setPower(speed);


            while (opModeIsActive() &&
                    (robot.runtime.seconds() < timeoutS) &&
                    (robot.motorBackLeft.isBusy() && robot.motorFrontRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newBackLeftTarget, newBackRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d", newFrontLeftTarget, newFrontRightTarget);

                telemetry.update();
            }

            // Stop all motion;
            robot.motorBackLeft.setPower(0);
            robot.motorBackRight.setPower(0);
            robot.motorFrontRight.setPower(0);
            robot.motorFrontLeft.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}
