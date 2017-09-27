package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by alex.fanat on 12/3/2016.
 */

@TeleOp(name="Single Controller",group="Linear OpMode")
public class GameControl_Implementation_A extends LinearOpMode
{
    DcMotor left;
    DcMotor right;
    //DcMotor flipper;
    @Override
    public void runOpMode() throws InterruptedException
    {
        boolean wheelsInverse=false;

        right=hardwareMap.dcMotor.get("right");
        left=hardwareMap.dcMotor.get("left");
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();

        while(opModeIsActive())
        {
            if (gamepad1.b) wheelsInverse=!wheelsInverse;
            left.setPower((wheelsInverse?-1:1)*(gamepad1.left_stick_y)+gamepad1.left_stick_x);
            right.setPower((wheelsInverse?-1:1)*(gamepad1.left_stick_y)-gamepad1.left_stick_x);
        }
    }
}