package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by alex.fanat on 12/3/2016.
 */

@TeleOp(name="Servo Control Test",group="Linear OpMode")
public class Servo_Control_Test extends LinearOpMode
{
    Servo armServoLeft;
    Servo armServoRight;

    @Override public void runOpMode() throws InterruptedException
    {
        armServoRight = hardwareMap.servo.get("armServoRight");
        armServoLeft = hardwareMap.servo.get("armServoLeft");
        armServoLeft.setDirection(Servo.Direction.REVERSE);

        telemetry.addLine("The servos have been found in the hardwareMap.");
        telemetry.update();

        GamepadValueMonitor valueMonitor = new GamepadValueMonitor(gamepad1, new GamepadValueMonitor.Fetchable<Boolean>() { @Override public Boolean fetch() { return opModeIsActive(); } }, new GamepadValueMonitor.Fetchable<Telemetry>() { @Override public Telemetry fetch() { return telemetry; } })
        {{
            dPadUp.updateInformer = new Runnable() { @Override public void run() { if (dPadUp.currentValue) { armServoLeft.setPosition(Servo.MAX_POSITION); armServoRight.setPosition(Servo.MAX_POSITION); } } };
            dPadUp.activeUpdateMode = MonitoredValue.UpdateMode.CHANGE;
            dPadUp.active = true;

            dPadDown.updateInformer = new Runnable() { @Override public void run() { if (dPadDown.currentValue) { armServoLeft.setPosition(Servo.MIN_POSITION); armServoRight.setPosition(Servo.MIN_POSITION); } } };
            dPadDown.activeUpdateMode = MonitoredValue.UpdateMode.CHANGE;
            dPadDown.active = true;

            start.updateInformer = new Runnable() { @Override public void run() { if (start.currentValue) { armServoLeft.setPosition(0.5); armServoRight.setPosition(0.5); } } };
            start.activeUpdateMode = MonitoredValue.UpdateMode.CHANGE;
            start.active = true;
        }};

        waitForStart();
        valueMonitor.startMonitoring(true);
        while (opModeIsActive());
    }
}
