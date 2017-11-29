
package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;



@Autonomous (name="AutoRed", group="Autonomous")  // @Autonomous(...) is the other common choice
public class AutoBlu extends LinearOpMode {
    
    public ElapsedTime liftTimer = new ElapsedTime();
    public ElapsedTime angerTimer = new ElapsedTime();
    public DcMotor motorBackLeft = null;                         //variables set to null before action
    public DcMotor motorFrontLeft = null;
    public DcMotor motorFrontRight = null;
    public DcMotor motorBackRight = null;
    public Servo servoJewel = null;
    public ColorSensor colorSensor = null;
    public int gear = 0;                                         //gear set to zero before action
    public ElapsedTime     runtime = new ElapsedTime();

    public static final double COUNTS_PER_MOTOR_REV  = 1024 ;    // eg: TETRIX Motor Encoder
    // static final double DRIVE_GEAR_REDUCTION = 2.0 ;     // This is < 1.0 if geared UP
    public static final double WHEEL_DIAMETER_INCHES = 4.0 ;     // For figuring circumference
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV )/(WHEEL_DIAMETER_INCHES * Math.PI);
    public static final double     DRIVE_SPEED             = 0.6;
    public static final double     TURN_SPEED              = 0.5;

    @Override
    public void runOpMode() {

            motorFrontLeft = hardwareMap.get(DcMotor.class, "Front Left Motor");
            motorBackLeft = hardwareMap.get(DcMotor.class, "Back Left Motor");
            motorFrontRight = hardwareMap.get(DcMotor.class, "Front Right Motor");
            motorBackRight = hardwareMap.get(DcMotor.class, "Back Right Motor");

            servoJewel = hardwareMap.get(Servo.class, "Jewel Servo");

            colorSensor = hardwareMap.get(ColorSensor.class, "Color Sensor");


            telemetry.addData("Status", "Resetting Encoders");    //
            telemetry.update();

      //  motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       // motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      //  motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      //  motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorFrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);

            waitForStart();

            // Step through each leg of the path,
            // Note: Reverse movement is obtained by setting a negative distance (not speed)
           // encoderDrive(DRIVE_SPEED,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
            //encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
            //encoderDrive(DRIVE_SPEED, -24, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout
            servoJewel.setPosition(.5);
            sleep(1000);     // pause for servos to move
        if(colorSensor.red() > colorSensor.blue()){ // if the sensor picks up more blue then red, we drive backward
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
        if(colorSensor.red() < colorSensor.blue()){ // if the sensor picks up more red then blue, we drive forward
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

                // Determine new target position, and pass to motor controller
                newBackLeftTarget = motorBackLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
                newFrontLeftTarget = motorFrontLeft.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
                newBackRightTarget = motorBackRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
                newFrontRightTarget = motorFrontRight.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);


                // Turn On RUN_TO_POSITION
                motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                // reset the timeout time and start motion.
                runtime.reset();
                motorBackLeft.setPower(Math.abs(speed));
                motorBackRight.setPower(Math.abs(speed));
                motorFrontLeft.setPower(Math.abs(speed));
                motorFrontRight.setPower(Math.abs(speed));


                while (opModeIsActive() &&
                        (runtime.seconds() < timeoutS) &&
                        (motorBackLeft.isBusy() && motorFrontRight.isBusy())) {

                    // Display it for the driver.
                    telemetry.addData("Path1",  "Running to %7d :%7d", newBackLeftTarget,  newBackRightTarget);
                    telemetry.addData("Path2",  "Running at %7d :%7d",
                            motorBackLeft.getCurrentPosition(),
                            motorFrontRight.getCurrentPosition());
                    telemetry.update();
                }

                // Stop all motion;
                motorBackLeft.setPower(0);
                motorBackRight.setPower(0);
                motorFrontRight.setPower(0);
                motorFrontLeft.setPower(0);

                // Turn off RUN_TO_POSITION
                motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                //  sleep(250);   // optional pause after each move
            }
        }
}
    

