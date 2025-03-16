package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "OverdriveX_Teleop", group = "Robot")
public class OverdriveX_Teleop extends LinearOpMode {

    // Motor and Servo declarations
    private DcMotor FL      = null; // front left   motor
    private DcMotor FR      = null; // front right  motor
    private DcMotor BL      = null; // back  left   motor
    private DcMotor BR      = null; // back  right  motor
    private DcMotor arm     = null; // arm          motor
    private CRServo intake  = null; // intake       servo

    // Constants for arm positions
    private static final double ARM_TICKS_PER_DEGREE     = 28 * 250047.0 / 4913.0 * 100.0 / 20.0 * (1 / 360.0); // ~19.79
    private static final double ARM_COLLAPSED            = 0;
    private static final double ARM_COLLECT              = 247.5 * ARM_TICKS_PER_DEGREE;
    private static final double ARM_CLEAR_BARRIER        = 210 * ARM_TICKS_PER_DEGREE;
    private static final double ARM_SCORE_SPECIMEN       = 130 * ARM_TICKS_PER_DEGREE;
    private static final double ARM_SCORE_SAMPLE_IN_LOW  = 130 * ARM_TICKS_PER_DEGREE;
    private static final double ARM_ATTACH_HANGING_HOOK  = 120 * ARM_TICKS_PER_DEGREE;
    private static final double ARM_WINCH_ROBOT          = 15  * ARM_TICKS_PER_DEGREE;

    //this is the change-------------------------------------------------------------------------------------------------------------
    private static final double ARM_FUDGE_FACTOR         = 0.5  * ARM_TICKS_PER_DEGREE;
    private static final double ARM_MAX_ANGLE            = 249 * ARM_TICKS_PER_DEGREE;
    // Constants for intake servo positions
    private static final double INTAKE_COLLECT           = 1.0;
    private static final double INTAKE_OFF               = 0.0;
    private static final double INTAKE_DEPOSIT           = -1;
    // Variables for remembering arm and wrist positions
    private              double armPosition              = ARM_COLLAPSED;
    private              double intakePosition           = INTAKE_OFF;
    private              double temperFactor             = 0.6;
    private              double fudgeFactor              = 0.0;
    private static final double ARMTEMPER                = 0.8;

    @Override
    public void runOpMode() {
        // Initialize motors and servos
        FL      = hardwareMap.get(DcMotor.class, "FL");
        FR      = hardwareMap.get(DcMotor.class, "FR");
        BL      = hardwareMap.get(DcMotor.class, "BL");
        BR      = hardwareMap.get(DcMotor.class, "BR");
        arm     = hardwareMap.get(DcMotor.class, "arm"); 
        intake  = hardwareMap.get(CRServo.class, "intake");

        // Setup motor amd servo behaviors
        setupMotorsAndServos();

        // Wait for driver to hit START
        telemetry.addLine("Robot Ready.");
        telemetry.update();
        waitForStart();
        // Main control loop
        while (opModeIsActive()) {
            controlDriving();
            controlScoring();
            updateTelemetry();
        }
    }

    private void setupMotorsAndServos() {
        FL.setDirection(DcMotor.Direction.FORWARD);
        FR.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.FORWARD);
        BR.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.FORWARD);
        
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        ((DcMotorEx)arm).setCurrentAlert(5, CurrentUnit.AMPS);
        collapseArm();
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setPower(INTAKE_OFF);
    }

    public void moveServoGradually(Servo servo, double targetPosition, double step) {
      double currentPosition = servo.getPosition();
      while (Math.abs(currentPosition - targetPosition) > step) {
          currentPosition += (targetPosition > currentPosition) ? step : -step;
          servo.setPosition(currentPosition);
          sleep(20); // Adjust delay as needed
      }
      servo.setPosition(targetPosition);
    }

    // Gamepad 1 is only used for DRIVING - move forward, backward, sideways, diagonally, rotate 
    private void controlDriving() {
        // Gamepad 1's left joystick - controls all of the robot's 
        // straight line movements - intuitively follows joystick
        double y  =  -1.0 * gamepad1.left_stick_y;       // Forward/backward
        double x  =  gamepad1.left_stick_x * 1.1; // Strafing (left/right)
        // Gamepad 1's right joystick - only uses right/left control of
        // the joystick; controls right/left rotation around robot center
        double rx =  gamepad1.right_stick_x;      // Rotation
        // Calculate motor powers using above 3 sensor values
        double FLPower = y + x + rx;
        double FRPower = y - x - rx;
        double BLPower = y - x + rx;
        double BRPower = y + x - rx;
        // Normalize motor powers
        double maxPower = Math.max(Math.abs(FLPower), 
                          Math.max(Math.abs(FRPower),
                          Math.max(Math.abs(BLPower),  Math.abs(BRPower))));
        if (maxPower > 1.0) {
            FLPower /= maxPower;
            FRPower /= maxPower;
            BLPower /= maxPower;
            BRPower /= maxPower;
        }
        // Apply motor powers
        FL.setPower(FLPower * temperFactor);
        FR.setPower(FRPower * temperFactor);
        BL.setPower(BLPower * temperFactor);
        BR.setPower(BRPower * temperFactor);
    }

    // Gamepad 2 is only used for SCORING - arm, wrist, intake control 
    private void controlScoring() {
        if (gamepad2.b) {                    // Gamepad 2's A button starts intake rotation
            intakePosition = INTAKE_DEPOSIT;
        } else if (gamepad2.x) {             // Gamepad 2's X button stops intake rotation
            intakePosition = INTAKE_OFF;
        } else if (gamepad2.a) {             // Gamepad 2's B button reverses intake rotation
            intakePosition = INTAKE_COLLECT;
        }
        if (gamepad2.right_bumper) {
            armPosition    = ARM_COLLECT;
            intakePosition = INTAKE_COLLECT;
        }
        else if (gamepad2.left_bumper) {
            armPosition   = ARM_CLEAR_BARRIER;
        } else if (gamepad2.y) {
            armPosition   = ARM_SCORE_SAMPLE_IN_LOW;
        } else if (gamepad2.dpad_left) {
            armPosition   = ARM_COLLAPSED;
            intakePosition = INTAKE_OFF;
        } else if (gamepad2.dpad_right) {
            armPosition   = ARM_SCORE_SPECIMEN;
        } else if (gamepad2.dpad_up) {
            armPosition   = ARM_ATTACH_HANGING_HOOK;
            intakePosition = INTAKE_OFF;
        } else if (gamepad2.dpad_down) {
            armPosition   = ARM_WINCH_ROBOT;
            intakePosition = INTAKE_OFF;
        }
        //arm
        fudgeFactor = ARM_FUDGE_FACTOR * (gamepad2.right_trigger - gamepad2.left_trigger);
        armPosition += fudgeFactor;
        telemetry.update();
        
        //this is the addition
        if (armPosition > ARM_MAX_ANGLE)
            armPosition = ARM_MAX_ANGLE;
        if (armPosition < 0)
            armPosition = 0;
        
        arm.setTargetPosition((int) (armPosition));
        ((DcMotorEx) arm).setVelocity(2100 * temperFactor);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //intake
        intake.setPower(intakePosition);
    }
    private void collapseArm(){
      arm.setTargetPosition((int) (ARM_COLLAPSED));
      ((DcMotorEx) arm).setVelocity(1000 * ARMTEMPER);
      arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      arm.setPower(ARMTEMPER);
      while(arm.isBusy()){
        sleep(100);
      }
    }
    private void updateTelemetry() {
        if (((DcMotorEx) arm).isOverCurrent()) {
            telemetry.addLine("MOTOR EXCEEDED CURRENT LIMIT!");
        }
        telemetry.addData("armTarget: ", arm.getTargetPosition());
        telemetry.addData("arm Encoder: ", arm.getCurrentPosition());
        telemetry.update();
    }
}
