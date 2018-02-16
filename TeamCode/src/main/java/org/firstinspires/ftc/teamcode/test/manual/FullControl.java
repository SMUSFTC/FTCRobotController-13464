package org.firstinspires.ftc.teamcode.test.manual;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.GamepadValueMonitor;

/**
 * Created by Le Fanatics of the Alexander on 11/18/2017.
 */

public class FullControl extends LinearOpMode
{
    private static final double LINEARACTUATOR_MAX = 0.82;
    private static final double LINEARACTUATOR_MIN = 0.17;
    private static final double LINEARACTUATOR_INCREMENT = 0.001;

    private Servo leftArmServo, rightArmServo, linearActuator;
    private DcMotor frontLeftDriveMotor, backLeftDriveMotor, frontRightDriveMotor, backRightDriveMotor;

    public void runOpMode() throws InterruptedException
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

        GamepadValueMonitor valueMonitorGamepad1 = new GamepadValueMonitor(gamepad1, this::opModeIsActive, telemetry)
        {{
            leftStickY.active = leftStickX.active = dPadRight.active = dPadLeft.active = true;

            dPadRight.updateInformer = () ->
            {
                frontLeftDriveMotor.setPower(0.4);
                backLeftDriveMotor.setPower(0.4);
                frontRightDriveMotor.setPower(0.2);
                backRightDriveMotor.setPower(0.2);
            };
            dPadRight.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            dPadRight.customUpdateCondition = () -> dPadRight.currentValue;

            dPadLeft.updateInformer = () ->
            {
                frontLeftDriveMotor.setPower(0.2);
                backLeftDriveMotor.setPower(0.2);
                frontRightDriveMotor.setPower(0.4);
                backRightDriveMotor.setPower(0.4);
            };
            dPadLeft.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            dPadLeft.customUpdateCondition = () -> dPadLeft.currentValue;


            loop = () ->
            {
                frontLeftDriveMotor.setPower((-leftStickY.currentValue + leftStickX.currentValue) * 0.6);
                backLeftDriveMotor.setPower(frontLeftDriveMotor.getPower());
                frontRightDriveMotor.setPower((-leftStickY.currentValue - leftStickX.currentValue) * 0.6);
                backRightDriveMotor.setPower(frontRightDriveMotor.getPower());
            };
        }};

        GamepadValueMonitor valueMonitorGamepad2 = new GamepadValueMonitor(gamepad2, this::opModeIsActive, telemetry)
        {{
            dPadUp.updateInformer = () ->
            {
                leftArmServo.setPosition(Servo.MAX_POSITION);
                rightArmServo.setPosition(Servo.MAX_POSITION);
            };
            dPadUp.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            dPadUp.customUpdateCondition = () -> dPadUp.currentValue;
            dPadUp.active = true;

            dPadDown.updateInformer = () ->
            {
                leftArmServo.setPosition(Servo.MIN_POSITION);
                rightArmServo.setPosition(Servo.MIN_POSITION);
            };
            dPadDown.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            dPadDown.customUpdateCondition = () -> dPadDown.currentValue;
            dPadDown.active = true;

            start.updateInformer = () ->
            {
                leftArmServo.setPosition(0.5);
                rightArmServo.setPosition(0.5);
            };
            start.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            start.customUpdateCondition = () -> start.currentValue;
            start.active = true;

            // For some reason, the right stick's values are spread across the triggers; the y-axis is the right trigger, and the x-axis is the other trigger.
            rightStickY.updateInformer = () -> leftArmServo.setPosition((rightStickY.currentValue / 2) + .5);
            rightStickY.activeUpdateMode = MonitoredValue.UpdateMode.CHANGE;
            rightStickY.active = true;

            rightStickX.updateInformer = () -> rightArmServo.setPosition((rightStickX.currentValue / 2) + .5);
            rightStickX.activeUpdateMode = MonitoredValue.UpdateMode.CHANGE;
            rightStickX.active = true;

            y.updateInformer = () ->
            {
                linearActuator.setPosition(linearActuator.getPosition() + LINEARACTUATOR_INCREMENT > LINEARACTUATOR_MAX ? linearActuator.getPosition() : linearActuator.getPosition() + LINEARACTUATOR_INCREMENT);
            };
            y.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            y.customUpdateCondition = () -> y.currentValue;
            y.active = true;

            a.updateInformer = () ->
            {
                linearActuator.setPosition(linearActuator.getPosition() - LINEARACTUATOR_INCREMENT < LINEARACTUATOR_MIN ? linearActuator.getPosition() : linearActuator.getPosition() - LINEARACTUATOR_INCREMENT);
            };
            a.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            a.customUpdateCondition = () -> a.currentValue;
            a.active = true;
        }};


        telemetry.addLine("The handlers have been initialized.");
        telemetry.addLine("Awaiting start...");
        telemetry.update();

        waitForStart();
        valueMonitorGamepad1.startMonitoring(true);
        valueMonitorGamepad2.startMonitoring(true);
        while (opModeIsActive()) ;


    }
}
