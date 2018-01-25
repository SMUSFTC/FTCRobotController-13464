package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@Autonomous(name = "Autonomous")
public class AutonomousLeftTest extends LinearOpMode
{
    Servo leftArmServo, rightArmServo, linearActuator;
    DcMotor frontLeftDriveMotor, backLeftDriveMotor, frontRightDriveMotor, backRightDriveMotor;

    @Override public void runOpMode() throws InterruptedException
    {

        rightArmServo = hardwareMap.servo.get("rightArmHingeServo");
        leftArmServo = hardwareMap.servo.get("leftArmHingeServo");

        leftArmServo.setDirection(Servo.Direction.REVERSE);

        linearActuator = hardwareMap.servo.get("linearActuator");

        frontLeftDriveMotor = hardwareMap.dcMotor.get("frontLeftDriveMotor");
        backLeftDriveMotor = hardwareMap.dcMotor.get("backLeftDriveMotor");
        frontRightDriveMotor = hardwareMap.dcMotor.get("frontRightDriveMotor");
        backRightDriveMotor = hardwareMap.dcMotor.get("backRightDriveMotor");

        frontLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addLine("The connected devices have been initialized.");
        telemetry.update();
        telemetry.setAutoClear(false);

        waitForStart();

        /**
         * Read the pictogram
         */

        frontLeftDriveMotor.setPower(-1);
        backLeftDriveMotor.setPower(-1);
        frontRightDriveMotor.setPower(-1);
        backRightDriveMotor.setPower(-1);

        sleep(1000);

        frontLeftDriveMotor.setPower(0);
        backLeftDriveMotor.setPower(0);
        frontRightDriveMotor.setPower(0);
        backRightDriveMotor.setPower(0);



    }

}
