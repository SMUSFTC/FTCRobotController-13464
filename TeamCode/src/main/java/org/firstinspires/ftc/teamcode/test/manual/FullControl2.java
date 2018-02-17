package org.firstinspires.ftc.teamcode.test.manual;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Rashid on 2/17/2018.
 */

@TeleOp(name = "Full Control (No Gamepad manager)")
public class FullControl2 extends LinearOpMode {

    private static final double LINEAR_ACTUATOR_MAX = 0.82;
    private static final double LINEAR_ACTUATOR_MIN = 0.17;

    private static final double SERVO_INCREMENT = 0.005;

    private Servo leftArmServo, rightArmServo, linearActuator, rightLiftServo, leftLiftServo, armLiftServo;
    private DcMotor frontLeftDriveMotor, backLeftDriveMotor, frontRightDriveMotor, backRightDriveMotor;

    private double motorPowerMultiplier = 0.6;

    public void runOpMode() {

        rightLiftServo = hardwareMap.servo.get("rightLiftServo");
        leftLiftServo = hardwareMap.servo.get("leftLiftServo");

        leftLiftServo.setDirection(Servo.Direction.REVERSE);

        rightArmServo = hardwareMap.servo.get("rightArmHingeServo");
        leftArmServo = hardwareMap.servo.get("leftArmHingeServo");

        leftArmServo.setDirection(Servo.Direction.REVERSE);

        armLiftServo = hardwareMap.servo.get("armLiftServo");

        linearActuator = hardwareMap.servo.get("linearActuator");

        frontLeftDriveMotor = hardwareMap.dcMotor.get("frontLeftDriveMotor");
        backLeftDriveMotor = hardwareMap.dcMotor.get("backLeftDriveMotor");
        frontRightDriveMotor = hardwareMap.dcMotor.get("frontRightDriveMotor");
        backRightDriveMotor = hardwareMap.dcMotor.get("backRightDriveMotor");

        frontLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addLine("The connected devices have been initialized.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // Drive train
            frontLeftDriveMotor.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x) * motorPowerMultiplier);
            backLeftDriveMotor.setPower(frontLeftDriveMotor.getPower());
            frontRightDriveMotor.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x) * motorPowerMultiplier);
            backRightDriveMotor.setPower(frontRightDriveMotor.getPower());

            // Right turning
            if(gamepad1.dpad_right) {
                frontLeftDriveMotor.setPower(0.4);
                backLeftDriveMotor.setPower(0.4);
                frontRightDriveMotor.setPower(0.2);
                backRightDriveMotor.setPower(0.2);
            }

            // Left turning
            if(gamepad1.dpad_left) {
                frontLeftDriveMotor.setPower(0.2);
                backLeftDriveMotor.setPower(0.2);
                frontRightDriveMotor.setPower(0.4);
                backRightDriveMotor.setPower(0.4);
            }

            if(gamepad2.start) {
                leftArmServo.setPosition(0);
                rightArmServo.setPosition(0);
                armLiftServo.setPosition(0);
                linearActuator.setPosition(0.5);
            }

            // Open arm
            if(gamepad2.dpad_down) {
                leftArmServo.setPosition(Servo.MIN_POSITION);
                rightArmServo.setPosition(Servo.MIN_POSITION);
            }

            // Close arm
            if(gamepad2.dpad_up) {
                leftArmServo.setPosition(Servo.MAX_POSITION);
                rightArmServo.setPosition(Servo.MAX_POSITION);
            }

            // Push lift upwards
            if(gamepad2.y) {
                leftLiftServo.setPosition((leftLiftServo.getPosition() + SERVO_INCREMENT) > 1 ? 1 : (leftLiftServo.getPosition() + SERVO_INCREMENT));
                rightLiftServo.setPosition(leftLiftServo.getPosition());
            }

            // Push lift downwards
            if(gamepad2.x) {
                leftLiftServo.setPosition((leftLiftServo.getPosition() - SERVO_INCREMENT) < 0 ? 0 : (leftLiftServo.getPosition() - SERVO_INCREMENT));
                rightLiftServo.setPosition(leftLiftServo.getPosition());
            }

            // Push arm lift upwards
            if(gamepad2.b) {
                armLiftServo.setPosition((armLiftServo.getPosition() + SERVO_INCREMENT) > 1 ? 1 : (armLiftServo.getPosition() +  SERVO_INCREMENT));
            }

            // Push arm lift downwards
            if(gamepad2.a) {
                armLiftServo.setPosition((armLiftServo.getPosition() - SERVO_INCREMENT) < 0 ? 0 : (armLiftServo.getPosition() - SERVO_INCREMENT));
            }

            // Push lift forwards (gallow arm)
            if(gamepad2.dpad_right) {
                linearActuator.setPosition(LINEAR_ACTUATOR_MIN);
            }

            // Push lift backwards (gallow arm)
            if(gamepad2.dpad_left) {
                linearActuator.setPosition(LINEAR_ACTUATOR_MAX);
            }
        }

    }

}
