package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Rashid on 1/12/2018.
 */

public class Robot {

    private DcMotor frontLeftDrive;

    private DcMotor frontRightDrive;

    private DcMotor backLeftDrive;

    private DcMotor backRightDrive;

    public Robot(DcMotor frontLeftDrive, DcMotor frontRightDrive, DcMotor backLeftDrive, DcMotor backRightDrive) {

        this.frontLeftDrive = frontLeftDrive;
        this.frontRightDrive = frontRightDrive;
        this.backLeftDrive = backLeftDrive;
        this.backRightDrive = backRightDrive;

        this.frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void setPowerLeftDrive(double power) {
        frontLeftDrive.setPower(power);
        backLeftDrive.setPower(power);
    }

    public void setPowerRightDrive(double power) {
        frontRightDrive.setPower(power);
        backRightDrive.setPower(power);
    }

    public void driveForward(double power) {
        setPowerLeftDrive(power);
        setPowerRightDrive(power);
    }

    public void driveForward() {
        driveForward(1.0);
    }

    public void driveBackward(double power) {
        setPowerLeftDrive(power);
        setPowerRightDrive(power);
    }

    public void driveBackward() {
        driveBackward(1.0);
    }


    public void turnLeft(double power) {
        setPowerRightDrive(power);
        setPowerLeftDrive(-power);
    }

    public void turnLeft() {
        turnLeft(1.0);
    }

    public void turnRight(double power) {
        setPowerRightDrive(-power);
        setPowerLeftDrive(power);
    }

    public void turnRight() {
        turnLeft(1.0);
    }

    public static void wait(int time, LinearOpMode opMode) {
        opMode.sleep(time);
    }
}
