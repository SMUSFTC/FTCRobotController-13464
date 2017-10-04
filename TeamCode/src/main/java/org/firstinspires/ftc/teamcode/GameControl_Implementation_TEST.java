package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by alex.fanat on 12/3/2016.
 */

@TeleOp(name="Single Controller",group="Linear OpMode")
public class GameControl_Implementation_TEST extends LinearOpMode
{
    DcMotor left;
    DcMotor right;
    @Override public void runOpMode() throws InterruptedException
    {
        boolean wheelsInverse=false;

        right=hardwareMap.dcMotor.get("right");
        left=hardwareMap.dcMotor.get("left");
        left.setDirection(DcMotorSimple.Direction.REVERSE);

        GamepadEventManager.Configuration eventManagerConfiguration = new GamepadEventManager.Configuration();
        eventManagerConfiguration.dPadUpPressionHandler = new GamepadEventManager.GamepadEventHandler<Boolean>() { @Override public void handleGamepadEvent(Boolean currentValue, Boolean previousValue)
        {
            right.setPower(1);
            left.setPower(right.getPower());
        }};
        eventManagerConfiguration.dPadDownPressionHandler = new GamepadEventManager.GamepadEventHandler<Boolean>() { @Override public void handleGamepadEvent(Boolean currentValue, Boolean previousValue)
        {
            right.setPower(0);
            left.setPower(right.getPower());
        }};


        GamepadEventManager gamepadEventManager = new GamepadEventManager(gamepad1, eventManagerConfiguration);
        waitForStart();

        while(opModeIsActive()) gamepadEventManager.runEventHandlers(false);
    }
}

/*

if (gamepad1.b) wheelsInverse=!wheelsInverse;
            left.setPower((wheelsInverse?-1:1)*(gamepad1.left_stick_y)+gamepad1.left_stick_x);
            right.setPower((wheelsInverse?-1:1)*(gamepad1.left_stick_y)-gamepad1.left_stick_x);


* */