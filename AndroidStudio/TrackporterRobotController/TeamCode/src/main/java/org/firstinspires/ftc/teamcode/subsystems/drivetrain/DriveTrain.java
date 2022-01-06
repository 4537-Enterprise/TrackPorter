package org.firstinspires.ftc.teamcode.subsystems.drivetrain;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.controller.wpilibcontroller.SimpleMotorFeedforward;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class DriveTrain {

    CRServo leftMotor;
    CRServo rightMotor;

    SimpleMotorFeedforward driveFeedForward;
    public static double kS = 0;
    public static double kV = 0;
    public static double kA = 0;

    public static double MAX_VELOCITY = 48;
    public static double MAX_ACCELERATION = 24;

    Telemetry telemetry;
    TelemetryPacket packet = new TelemetryPacket();
    FtcDashboard dashboard = FtcDashboard.getInstance();

    public DriveTrain(HardwareMap map, Telemetry telemetry) {
        this.telemetry = telemetry;

        leftMotor = map.get(CRServo.class, "leftMotor");
        rightMotor = map.get(CRServo.class, "rightMotor");

        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        driveFeedForward = new SimpleMotorFeedforward(kS, kV, kA);
    }

    public void updateFeedForward() {
        driveFeedForward = new SimpleMotorFeedforward(kS, kV, kA);
    }

    public void setLeftMotor(double power) {
        leftMotor.setPower(power);
    }

    public void setRightMotor(double power) {
        rightMotor.setPower(power);
    }

    public void drive(double leftMotorPower, double rightMotorPower) {
        leftMotorPower = Math.pow(leftMotorPower, 3);
        rightMotorPower = Math.pow(rightMotorPower, 3);

        setLeftMotor(leftMotorPower);
        setRightMotor(rightMotorPower);

        telemetry.addData("Left Motor Power", leftMotorPower);
        telemetry.addData("Right Motor Power", rightMotorPower);
        telemetry.update();

        packet.put("Left Motor Power", leftMotorPower);
        packet.put("Right Motor Power", rightMotorPower);
        dashboard.sendTelemetryPacket(packet);
    }

    public void feedForwardDrive(double leftMotorInput, double rightMotorInput) {
        double leftMotorVelocity = MAX_VELOCITY * leftMotorInput;
        double rightMotorVelocity = MAX_VELOCITY * rightMotorInput;

        double leftMotorFeedForwardOutput = driveFeedForward.calculate(leftMotorVelocity, MAX_ACCELERATION);
        double rightMotorFeedForwardOutput = driveFeedForward.calculate(rightMotorVelocity, MAX_ACCELERATION);

        //setLeftMotor(leftMotorFeedForwardOutput);
        //setRightMotor(rightMotorFeedForwardOutput);

        packet.put("Left Motor FeedForward", leftMotorFeedForwardOutput);
        packet.put("Right Motor FeedForward", rightMotorFeedForwardOutput);
        dashboard.sendTelemetryPacket(packet);
    }
}
