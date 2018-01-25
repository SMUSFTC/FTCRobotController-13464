package org.firstinspires.ftc.teamcode;

import android.hardware.SensorManager;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Rashid on 1/12/2018.
 * NOTE TO SELF: FUSION TOOK A CORNER BEAM THING
 */

public class Jones extends Robot {

    /**
     * Configuration
     */
    private final static String FRONT_LEFT_DRIVE = "frontLeftDriveMotor";
    private final static String FRONT_RIGHT_DRIVE = "frontRightDriveMotor";
    private final static String BACK_LEFT_DRIVE = "backLeftDriveMotor";
    private final static String BACK_RIGHT_DRIVE = "backRightDriveMotor";
    private final static String LEFT_ARM_SERVO = "leftArmHingeServo";
    private final static String RIGHT_ARM_SERVO = "rightArmHingeServo";
    private final static String LINEAR_ACTUATOR = "linearActuator";
    private final static String LEFT_LIFT_SERVO = "leftLiftServo";
    private final static String RIGHT_LIFT_SERVO = "rightLiftServo";

    private final static double LINEAR_ACTUATOR_MAX_POS = 0.7;
    private final static double LINEAR_ACTUATOR_MIN_POS = 0.2;

    private Servo leftArmServo;
    private Servo rightArmServo;

    private Servo linearActuator;

    private Servo leftLiftServo;
    private Servo rightLiftServo;

    public Jones() {
        super(RobotController.currentHardwareMap.dcMotor.get(FRONT_LEFT_DRIVE), RobotController.currentHardwareMap.dcMotor.get(FRONT_RIGHT_DRIVE), RobotController.currentHardwareMap.dcMotor.get(BACK_LEFT_DRIVE), RobotController.currentHardwareMap.dcMotor.get(BACK_RIGHT_DRIVE));

        leftArmServo = RobotController.currentHardwareMap.servo.get(LEFT_ARM_SERVO);
        rightArmServo = RobotController.currentHardwareMap.servo.get(RIGHT_ARM_SERVO);
        leftArmServo.setDirection(Servo.Direction.REVERSE);

        leftLiftServo = RobotController.currentHardwareMap.servo.get(LEFT_LIFT_SERVO);
        rightLiftServo = RobotController.currentHardwareMap.servo.get(RIGHT_LIFT_SERVO);
        leftLiftServo.setDirection(Servo.Direction.REVERSE);
        rightLiftServo.setDirection(Servo.Direction.FORWARD);

        linearActuator = RobotController.currentHardwareMap.servo.get(LINEAR_ACTUATOR);
    }

    public void armSetPosition(double position) {
        leftArmServo.setPosition(position);
        rightArmServo.setPosition(position);
    }

    public void armMaxPosition() {
        armSetPosition(0.9);
    }

    public void armMinPosition() {
        armSetPosition(Servo.MIN_POSITION);
    }

    public void setLinearActuatorPosition(double position) {
        linearActuator.setPosition(position);
    }

    public void linearActuatorMax() {
        linearActuator.setPosition(LINEAR_ACTUATOR_MAX_POS);
    }

    public void linearActuatorMin() {
        linearActuator.setPosition(LINEAR_ACTUATOR_MIN_POS);
    }

    public void setLiftPosition(double position) {
        leftLiftServo.setPosition(position);
        rightLiftServo.setPosition(position);
    }

    public void addToLiftPosition(double increment) {
        if (leftLiftServo.getPosition() + increment <= 1 && leftLiftServo.getPosition() + increment >= 0)
            setLiftPosition(leftLiftServo.getPosition() + increment);
    }

    public void liftMaxPosition() {
        setLiftPosition(Servo.MAX_POSITION);
    }

    public void liftMinPosition() {
        setLiftPosition(Servo.MIN_POSITION);
    }

}
