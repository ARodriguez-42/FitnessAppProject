package com.example.fitnessapp;



public class Exercise {

    String exerciseName, muscleGroupName, equipmentName, image, video;

    public Exercise(String exerciseName, String muscleGroupName, String equipmentName, String image, String video) {
        this.exerciseName = exerciseName;
        this.muscleGroupName = muscleGroupName;
        this.equipmentName = equipmentName;
        this.image = image;
        this.video = video;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getMuscleGroupName() {

        return muscleGroupName;
    }

    public String getEquipmentName() {

        return equipmentName;
    }

    public String getVideo() {

        return video;
    }

    public String getImage() {

        return image;
    }
}
