package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by alex.fanat on 12/3/2016.
 */

@TeleOp(name="Servo Control Test",group="Linear OpMode")
public class Servo_Control_Test extends LinearOpMode
{
    /*Servo leftArmServo;
    Servo rightArmServo;*/

    DcMotor frontLeftDriveMotor;
    DcMotor backLeftDriveMotor;
    DcMotor frontRightDriveMotor;
    DcMotor backRightDriveMotor;

    @Override public void runOpMode() throws InterruptedException
    {
        /*rightArmServo = hardwareMap.servo.get("rightArmHingeServo");
        leftArmServo = hardwareMap.servo.get("leftArmHingeServo");

        leftArmServo.setDirection(Servo.Direction.REVERSE);*/

        frontLeftDriveMotor = hardwareMap.dcMotor.get("frontLeftDriveMotor");
        backLeftDriveMotor = hardwareMap.dcMotor.get("backLeftDriveMotor");
        frontRightDriveMotor = hardwareMap.dcMotor.get("frontRightDriveMotor");
        backRightDriveMotor = hardwareMap.dcMotor.get("backRightDriveMotor");

        frontLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addLine("The connected devices have been initialized.");
        telemetry.update();
        telemetry.setAutoClear(false);

        GamepadValueMonitor valueMonitor = new GamepadValueMonitor(gamepad1, () -> opModeIsActive(), () -> telemetry)
        {{
            /*dPadUp.updateInformer = () ->
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
            rightStickX.active = true;*/

            leftStickY.active = leftStickX.active = true;

            loop = () ->
            {
                frontLeftDriveMotor.setPower(-leftStickY.currentValue + leftStickX.currentValue);
                backLeftDriveMotor.setPower(-leftStickY.currentValue + leftStickX.currentValue);
                frontRightDriveMotor.setPower(-leftStickY.currentValue - leftStickX.currentValue);
                backRightDriveMotor.setPower(-leftStickY.currentValue - leftStickX.currentValue);
            };
        }};

        telemetry.addLine("The handlers have been initialized.");
        telemetry.addLine("Awaiting start...");
        telemetry.update();

        waitForStart();
        valueMonitor.startMonitoring(true);
        while (opModeIsActive());
    }
}
