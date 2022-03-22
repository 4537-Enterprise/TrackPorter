package org.firstinspires.ftc.teamcode.subsystems.roadrunner.drive;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.Localizer;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Transform2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.ChassisSpeeds;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.spartronics4915.lib.T265Camera;

public class T265TrackingLocalizer implements Localizer {

    private Pose2d poseOffset = new Pose2d();
    private static Pose2d mPoseEstimate = new Pose2d();
    private Pose2d rawPose = new Pose2d();
    private T265Camera.CameraUpdate cameraUpdate;

    public static T265Camera t265Camera;

    private static T265Camera.PoseConfidence poseConfidence;

    public T265TrackingLocalizer(HardwareMap hardwareMap) {
        new T265TrackingLocalizer(hardwareMap, true);
    }

    public T265TrackingLocalizer(HardwareMap hardwareMap, boolean resetPos) {
        if (t265Camera == null) {
            t265Camera = new T265Camera(new Transform2d(new Translation2d(0,0), new Rotation2d(0)), 0, hardwareMap.appContext);
            setPoseEstimate(new Pose2d(0,0,0));
        }
        try {
            startRealsense();
        } catch (Exception ignored) {
            if (resetPos) {
                t265Camera.setPose(new com.arcrobotics.ftclib.geometry.Pose2d(0,0, new Rotation2d(0)));
            }
        }

    }

    @NonNull
    @Override
    public Pose2d getPoseEstimate() {
        if (cameraUpdate != null) {
            Translation2d oldPose = cameraUpdate.pose.getTranslation();
            Rotation2d oldRotation = cameraUpdate.pose.getRotation();

            rawPose = new Pose2d(oldPose.getX() / 0.0254, oldPose.getY() / 0.0254, oldRotation.getRadians());
            mPoseEstimate = rawPose.plus(poseOffset);
        }

        return mPoseEstimate;
    }

    @Override
    public void setPoseEstimate(@NonNull Pose2d pose2d) {
        pose2d = new Pose2d(pose2d.getX(),pose2d.getY(),0);

        poseOffset = pose2d.minus(rawPose);
        poseOffset = new Pose2d(poseOffset.getX(), poseOffset.getY(), Math.toRadians(0));
    }

    public static T265Camera.PoseConfidence getConfidence() {
        return poseConfidence;
    }

    /**
     * @return the heading of the robot (in radains)
     */
    public static double getHeading() {
        return mPoseEstimate.getHeading();
    }

    @Nullable
    @Override
    public Pose2d getPoseVelocity() {
        ChassisSpeeds velocity = cameraUpdate.velocity;
        return new Pose2d(velocity.vxMetersPerSecond /.0254,velocity.vyMetersPerSecond /.0254,velocity.omegaRadiansPerSecond);
    }

    @Override
    public void update() {
        cameraUpdate = t265Camera.getLastReceivedCameraUpdate();
        poseConfidence = cameraUpdate.confidence;
    }

    private void startRealsense() {
        t265Camera.start();
    }
}
