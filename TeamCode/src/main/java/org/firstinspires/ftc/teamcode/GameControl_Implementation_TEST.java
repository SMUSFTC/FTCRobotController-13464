package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by alex.fanat on 12/3/2016.
 */

@TeleOp(name="Single Controller",group="Linear OpMode")
public class GameControl_Implementation_TEST extends LinearOpMode
{
    Servo armServoLeft;
    Servo armServoRight;
    @Override public void runOpMode() throws InterruptedException
    {
        armServoLeft = hardwareMap.servo.get("armServoLeft");
        armServoRight = hardwareMap.servo.get("armServoRight");

        armServoLeft.setPosition(0.5144);
        armServoRight.setPosition(0.39);

        GamepadValueMonitor valueMonitor = new GamepadValueMonitor(gamepad1, new GamepadValueMonitor.Fetchable<Boolean>() { @Override public Boolean fetch() { return opModeIsActive(); } })
        {{
            dPadUp.updateInformer = new Runnable() { @Override public void run() { /* Code goes here. */ } };
            dPadUp.active = true;
        }};
        waitForStart();
        valueMonitor.startMonitoring(true);
    }
}
