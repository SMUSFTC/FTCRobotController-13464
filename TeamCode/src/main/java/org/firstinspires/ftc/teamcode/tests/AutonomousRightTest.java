package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
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

@Autonomous(name = "org.firstinspires.ftc.teamcode.tests.AutonomousRightTest", group = "Tests")
public class AutonomousRightTest extends LinearOpMode {

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

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "AcTvLN3/////AAAAGeubf7NV7EeMqnpJ9vg/MD49t+FAEYVyIWlCfs04wEfNWVdY9GH8kQP+Q6mp/+1hMLEfBu8aFsNPMVOZTHIziNniPzX19W/QeBqUL4fn/F/+/DgqHnxIh5H/XTdsh7VbTi32XWtB44k5fxRK4ya+mAqDlkbZDSM/0/+P9cl6WZ1M9s8NJ6y0wAO39GpkLdWqHXS/XgmSSg82iCozVSwUzKn8/7ITiXxjlXCTNJwFS1BMkehgC7u3IT5HuR4l5bvYF/87pNnVnQ/azQEB0jzTv1Yww4VaVJ7KOC25MhWB5u8KqTNAwJyvY24mav+Jg5Kdg3ibRp0zybRQuP0WEnktTHk+ajU5jSR9uGC77d9zU586";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        relicTrackables.activate();

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        telemetry.addData("VuMark", "%s visible", vuMark);
        telemetry.update();

        // Initialize servos to start position
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(opModeIsActive()) {
                    leftArmServo.setPosition(0.5);
                    rightArmServo.setPosition(0.5);
                }
            }
        }).start();

        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

        }
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}
