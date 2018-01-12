package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * This OpMode illustrates the basics of using the Vuforia engine to determine
 * the identity of Vuforia VuMarks encountered on the field. The code is structured as
 * a LinearOpMode. It shares much structure with {@link ConceptVuforiaNavigation}; we do not here
 * duplicate the core Vuforia documentation found there, but rather instead focus on the
 * differences between the use of Vuforia for navigation vs VuMark identification.
 *
 * @see ConceptVuforiaNavigation
 * @see VuforiaLocalizer
 * @see VuforiaTrackableDefaultListener
 * see  ftc_app/doc/tutorial/FTC_FieldCoordinateSystemDefinition.pdf
 * <p>
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 * <p>
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained in {@link ConceptVuforiaNavigation}.
 */

@Autonomous(name = "DistanceTest", group = "Tests")
public class DistanceTest extends LinearOpMode {

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    Servo leftArmServo;
    Servo rightArmServo;

    Servo linearActuator;

    DcMotor frontLeftDriveMotor;
    DcMotor backLeftDriveMotor;
    DcMotor frontRightDriveMotor;
    DcMotor backRightDriveMotor;

    @Override
    public void runOpMode() {

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
        telemetry.setAutoClear(false);

        telemetry.update();
        waitForStart();

        frontLeftDriveMotor.setPower(1.0);
        backLeftDriveMotor.setPower(1.0);
        frontRightDriveMotor.setPower(1.0);
        backRightDriveMotor.setPower(1.0);

        sleep(1000);

        frontLeftDriveMotor.setPower(0);
        backLeftDriveMotor.setPower(0);
        frontRightDriveMotor.setPower(0);
        backRightDriveMotor.setPower(0);
    }

}
