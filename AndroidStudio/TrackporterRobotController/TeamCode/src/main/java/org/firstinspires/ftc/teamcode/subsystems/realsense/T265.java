package org.firstinspires.ftc.teamcode.subsystems.realsense;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.spartronics4915.lib.T265Camera;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class T265 {

    private Transform2d cameraToRobot;
    private Pose2d startingPose;
    private T265Camera realsenseCamera = null;
    private T265Camera.CameraUpdate updatedPose = null;

    public Translation2d location;
    public Rotation2d rotation;

    public T265(HardwareMap hardwareMap) {
        cameraToRobot = new Transform2d(new Translation2d(0, 0), new Rotation2d(0));
        startingPose = new Pose2d(0, 0, new Rotation2d(0));

        realsenseCamera = new T265Camera(cameraToRobot, 0, hardwareMap.appContext);

        realsenseCamera.setPose(startingPose);
        start();
    }

    public void updatePose() {
        realsenseCamera.getLastReceivedCameraUpdate();
        updatedPose = realsenseCamera.getLastReceivedCameraUpdate();

        location = new Translation2d(updatedPose.pose.getX() / 0.0254, updatedPose.pose.getY() / 0.0254);
        rotation = updatedPose.pose.getRotation();
    }

    public void start() {
        realsenseCamera.start();
    }

    public void stop() {
        realsenseCamera.stop();
    }

    public boolean isStarted() {
        return realsenseCamera.isStarted();
    }
}
