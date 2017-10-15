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
    Servo armServoLeft;
    Servo armServoRight;

    @Override public void runOpMode() throws InterruptedException
    {
        armServoLeft = hardwareMap.servo.get("armServoLeft");
        armServoRight = hardwareMap.servo.get("armServoRight");

        GamepadValueMonitor valueMonitor = new GamepadValueMonitor(gamepad1, new GamepadValueMonitor.Fetchable<Boolean>() { @Override public Boolean fetch() { return opModeIsActive(); } })
        {{
            dPadUp.updateInformer = new Runnable() { @Override public void run() { armServoLeft.setPosition(Servo.MAX_POSITION); armServoRight.setPosition(Servo.MIN_POSITION); } };
            dPadUp.active = true;

            dPadDown.updateInformer = new Runnable() { @Override public void run() { armServoLeft.setPosition(Servo.MIN_POSITION); armServoRight.setPosition(Servo.MAX_POSITION); } };
            dPadDown.active = true;

            start.updateInformer = new Runnable() { @Override public void run() { armServoLeft.setPosition(0.5144); armServoRight.setPosition(0.39); } };
            start.active = true;
        }};

        waitForStart();

        valueMonitor.start.updateInformer.run();
        valueMonitor.startMonitoring(true);
    }
}
