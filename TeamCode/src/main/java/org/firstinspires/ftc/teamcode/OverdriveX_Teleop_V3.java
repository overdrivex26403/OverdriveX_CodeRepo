package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "OverdriveX_Teleop_V3", group = "Robot")
public class OverdriveX_Teleop_V3 extends LinearOpMode {

    private DcMotor FL      = null;
    private DcMotor FR      = null;
    private DcMotor BL      = null;
    private DcMotor BR      = null;
    private double temperFactor = 0.6;
    private DcMotorEx Viperslide1  = null;
    private DcMotorEx Viperslide2  = null;
    public Servo LeftArm;
    public Servo RightArm;
    public Servo Wrist;
    public Servo Claw;
    private static final double VIPERSLIDES_FULLY_COLLAPSED = 0;
    private double viperslidePosition = 0;
    private double wristPosition = 0;
    private double wristFineTune = 0.1;
    private double OPEN_CLAW = 0.8;
    private double CLOSED_CLAW = 1;
    private double armPosition = 0;
    
    private double INIT_ARM_POS_VERTICAL = 0.55;
    private double INIT_WRIST_POS_VERTICAL = 0;
    
    private double ARM_CENTER_POS = 0.45;
    private double ARM_SCORE_POS = 0.4;
    private double ARM_PICKUP_POS = 0.75;
    private double WRIST_PICKUP_POS = 0.3;
    private double WRIST_SCORE_POS = 0.7;

    
    private double VIPER_TEMPER_FACTOR = 0.5;
    private static int INIT_VIPER_POSITION = 200;
    private double MAX_VIPER_HEIGHT = 90;
    private double VIPER_HOLD_POWER = 0.05;

    private static final int TICKS_PER_MOTOR_REV = 537;
    private static final int MAX_POSITION = 3100;
    private static final int MIN_POSITION = 0;
    private static final int POSITION_TOLERANCE = 10;

    private boolean motorReset = false; 
    
    @Override
    public void runOpMode() {
        FL = hardwareMap.get(DcMotor.class, "FL");
        FR = hardwareMap.get(DcMotor.class, "FR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        BR = hardwareMap.get(DcMotor.class, "BR");

        setupMotorsAndServos();
        Viperslide1 = hardwareMap.get(DcMotorEx.class, "Viperslide1");
        Viperslide2 = hardwareMap.get(DcMotorEx.class, "Viperslide2");
        LeftArm = hardwareMap.get(Servo.class, "LeftArm");
        Wrist = hardwareMap.get(Servo.class, "Wrist");
        Claw = hardwareMap.get(Servo.class, "Claw");

        setupViperslideMotors();
        //takeArmAndWristToCenter();
    initArmAndWrist();
        initViperSlidePostion();

        telemetry.addLine("Robot Ready.");
        telemetry.update();
        waitForStart();
    
        while (opModeIsActive()) {
            controlDriving();
            controlScoring();
            updateTelemetry();
        }
    }

    private void setupViperslideMotors() {
        Viperslide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Viperslide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        Viperslide1.setTargetPositionTolerance(POSITION_TOLERANCE);
        Viperslide2.setTargetPositionTolerance(POSITION_TOLERANCE);
        
        Viperslide1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Viperslide2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        Viperslide1.setDirection(DcMotor.Direction.REVERSE);
        Viperslide2.setDirection(DcMotor.Direction.FORWARD);
    
        Viperslide1.setTargetPosition(INIT_VIPER_POSITION);
    Viperslide2.setTargetPosition(INIT_VIPER_POSITION);
        Viperslide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Viperslide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    
    public void initViperSlidePostion() {
        telemetry.addData("VS1 encoder start position: ", Viperslide1.getCurrentPosition());
        telemetry.addData("VS2 encoder start position: ", Viperslide2.getCurrentPosition());
        telemetry.update();
        
        setViperslidePosition(INIT_VIPER_POSITION);  // Initial position
        
        telemetry.addData("VS1 encoder target position: ", Viperslide1.getCurrentPosition());
        telemetry.addData("VS2 encoder target position: ", Viperslide2.getCurrentPosition());
        telemetry.update();
    }

    public void initArmAndWrist() {
    LeftArm.setPosition(INIT_ARM_POS_VERTICAL);
    Wrist.setPosition(INIT_WRIST_POS_VERTICAL);
    }

    public void takeArmAndWristToCenter() {
    Claw.setPosition(CLOSED_CLAW);
        moveServoGradually(LeftArm, INIT_ARM_POS_VERTICAL);
        moveServoGradually(Wrist, INIT_WRIST_POS_VERTICAL);
    }

    public void motorsToZero() {
        FL.setPower(0);
        FR.setPower(0);
        BL.setPower(0);
        BR.setPower(0);
    telemetry.addLine("Motors stopped");
    updateTelemetry();
    }

    
    public void moveServoGradually(Servo servoToMove, double finalPos) {
    double initPos = servoToMove.getPosition();
        telemetry.addData("Initial pos", initPos);
        telemetry.update();
        double currentPos = initPos;
        double deltaToMove = finalPos - currentPos;
        
        int stepsToMove = 60;
        int sleepTime = 10;
        int counter = 0;
        while (counter < stepsToMove) {
            telemetry.addData("Initial pos", initPos);
            telemetry.addData("current pos", currentPos);
            telemetry.update();
            currentPos += deltaToMove/stepsToMove;
            servoToMove.setPosition(currentPos);
            counter += 1;
            //sleep(sleepTime);
        }
    }

    private void setupMotorsAndServos() {
        FL.setDirection(DcMotor.Direction.FORWARD);
        FR.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.FORWARD);
        
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private void controlDriving() {

    
        double y = -1.0 * gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x * 1.1;
        double rx = gamepad1.right_stick_x;
        
        double FLPower = y + x + rx;
        double FRPower = y - x - rx;
        double BLPower = y - x + rx;
        double BRPower = y + x - rx;
        
        double maxPower = Math.max(Math.abs(FLPower), 
                   Math.max(Math.abs(FRPower),
                        Math.max(Math.abs(BLPower),
                             Math.abs(BRPower))));

     // double maxPower = Math.max(Math.abs(FLPower), 
     //                 Math.abs(FRPower),
     //                 Math.abs(BLPower),
     //                 Math.abs(BRPower));

    
        if (maxPower > 1.0) {
            FLPower /= maxPower;
            FRPower /= maxPower;
            BLPower /= maxPower;
            BRPower /= maxPower;
        }

    if (motorReset) {
            FLPower = 0;
            FRPower = 0;
            BLPower = 0;
            BRPower = 0;
    }
        
        FL.setPower(FLPower * temperFactor);
        FR.setPower(FRPower * temperFactor);
        BL.setPower(BLPower * temperFactor);
        BR.setPower(BRPower * temperFactor);
    telemetry.addData("BR: ", FLPower);
    telemetry.addData("FR: ", FRPower);
    telemetry.addData("BL: ", BLPower);
    telemetry.addData("BR: ", BRPower);
    telemetry.addData("y: ", y);
    telemetry.addData("x: ", x);
    telemetry.addData("rx: ", rx);
    }

    private void controlScoring() {
        if (gamepad2.right_stick_y < 0) {
            armPosition += 0.05;
            LeftArm.setPosition(armPosition);
            telemetry.addData("armPosition", armPosition);
            telemetry.update();
        }
        if (gamepad2.right_stick_y > 0) {
            armPosition -= 0.05;
            moveServoGradually(LeftArm, armPosition);
        }
        if (gamepad2.dpad_down) {
            moveServoGradually(LeftArm, ARM_PICKUP_POS - 0.2);
            moveServoGradually(Wrist, WRIST_PICKUP_POS);
        moveServoGradually(LeftArm, ARM_PICKUP_POS);
        Claw.setPosition(CLOSED_CLAW);
        }
        if (gamepad2.dpad_up) {
            moveServoGradually(LeftArm, ARM_SCORE_POS);
            moveServoGradually(Wrist, WRIST_SCORE_POS);
        }
        if (gamepad2.dpad_right) {
            takeArmAndWristToCenter();
        }

    if (gamepad1.dpad_left) {
        motorReset = true;
        sleep(1000);
        motorReset = false;
    }
    
        if (gamepad2.a) {
            Claw.setPosition(CLOSED_CLAW);
        }
        if (gamepad2.b) {
            Claw.setPosition(OPEN_CLAW);
        }
        
        if (gamepad2.left_stick_y < 0) {
            wristPosition += wristFineTune;
            Wrist.setPosition(wristPosition);
            telemetry.addData("wristposition", wristPosition);
            telemetry.update();
        }
        if (gamepad2.left_stick_y > 0) {
            wristPosition -= wristFineTune;
            Wrist.setPosition(wristPosition);
            telemetry.addData("wristposition", wristPosition);
            telemetry.update();
        }

    if (gamepad2.left_bumper) {
        setViperslidePosition(MIN_POSITION + 400);
    }

    if (gamepad2.right_bumper) {
        setViperslidePosition(MAX_POSITION);
    }

        moveViperSlides();
        telemetry.addData("arm position", viperslidePosition);
        telemetry.update();
        //sleep(100);
    }
      
    private void moveViperSlides() {
        int currentPosition = Viperslide1.getCurrentPosition();
        telemetry.addData("Current Position", currentPosition);
        
        if (gamepad2.right_trigger > 0) {
            int newPosition = Math.min(currentPosition + 50, MAX_POSITION);
            setViperslidePosition(newPosition);
        } 
        else if (gamepad2.left_trigger > 0) {
            int newPosition = Math.max(currentPosition - 50, MIN_POSITION);
            setViperslidePosition(newPosition);
        }
        
        telemetry.addData("Target Position", Viperslide1.getTargetPosition());
    }
    
    private void setViperslidePosition(int position) {
        Viperslide1.setTargetPosition(position);
        Viperslide2.setTargetPosition(position);
        
        Viperslide1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Viperslide2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        Viperslide1.setPower(0.8);
        Viperslide2.setPower(0.8);
    }
    
    private void updateTelemetry() {
        telemetry.update();
    }
}
