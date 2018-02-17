package org.firstinspires.ftc.teamcode.test.manual;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.GamepadValueMonitor;

/**
 * Created by Le Fanatics of the Alexander on 11/18/2017
 */

@TeleOp(name = "Full Control")
public class FullControl extends LinearOpMode
{
    private static final double LINEAR_ACTUATOR_MAX = 0.82;
    private static final double LINEAR_ACTUATOR_MIN = 0.17;

    private Servo leftArmServo, rightArmServo, linearActuator, rightLiftServo, leftLiftServo, armLiftServo;
    private DcMotor frontLeftDriveMotor, backLeftDriveMotor, frontRightDriveMotor, backRightDriveMotor;

    public void runOpMode() throws InterruptedException
    {
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

        GamepadValueMonitor valueMonitorGamepad1 = new GamepadValueMonitor(gamepad1, this::opModeIsActive, telemetry, "Gamepad 1 Monitor")
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

        GamepadValueMonitor valueMonitorGamepad2 = new GamepadValueMonitor(gamepad2, this::opModeIsActive, telemetry, "Gamepad 2 Monitor")
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
                linearActuator.setPosition(0.5);
                armLiftServo.setPosition(0);
            };
            start.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            start.customUpdateCondition = () -> start.currentValue;
            start.active = true;

            // Push lift forwards
            dPadRight.updateInformer = () ->
            {
                linearActuator.setPosition(LINEAR_ACTUATOR_MIN);
            };
            dPadRight.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            dPadRight.customUpdateCondition = () -> dPadRight.currentValue;
            dPadRight.active = true;

            // Pull lift backwards
            dPadLeft.updateInformer = () ->
            {
                linearActuator.setPosition(LINEAR_ACTUATOR_MAX);
            };
            dPadLeft.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            dPadLeft.customUpdateCondition = () -> dPadLeft.currentValue;
            dPadLeft.active = true;

            // Push lift down
            x.updateInformer = () ->
            {
                leftLiftServo.setPosition((leftLiftServo.getPosition() - 0.01) < 0 ? 0 : (leftLiftServo.getPosition() - 0.01));
                rightLiftServo.setPosition((rightLiftServo.getPosition() - 0.01) < 0 ? 0 : (rightLiftServo.getPosition() - 0.01));
            };
            x.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            x.customUpdateCondition = () -> x.currentValue;
            x.active = true;

            // Pull lift up
            y.updateInformer = () ->
            {
                leftLiftServo.setPosition((leftLiftServo.getPosition() + 0.01) > 1 ? 1 : (leftLiftServo.getPosition() + 0.01));
                rightLiftServo.setPosition((rightLiftServo.getPosition() + 0.01) > 1 ? 1 : (rightLiftServo.getPosition() + 0.01));
            };
            y.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            y.customUpdateCondition = () -> y.currentValue;
            y.active = true;

            // Push arm lift down
            a.updateInformer = () ->
            {
                armLiftServo.setPosition((armLiftServo.getPosition() - 0.01) < 0 ? 0 : (armLiftServo.getPosition() - 0.01));
            };
            a.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            a.customUpdateCondition = () -> a.currentValue;
            a.active = true;

            // Pull arm lift up  v
            b.updateInformer = () ->
            {
                armLiftServo.setPosition((armLiftServo.getPosition() + 0.01) > 1 ? 1 : (armLiftServo.getPosition() +  0.01));
            };
            b.activeUpdateMode = MonitoredValue.UpdateMode.CUSTOM;
            b.customUpdateCondition = () -> b.currentValue;
            b.active = true;

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
