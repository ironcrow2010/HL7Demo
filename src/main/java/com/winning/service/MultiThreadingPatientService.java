package com.winning.service;

import com.winning.model.Patient;

import java.util.ArrayList;
import java.util.Random;


public class MultiThreadingPatientService extends Thread {
    private ArrayList<Patient> patientList = null;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "线程运行中...");
        patientList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            patientList.add(new Patient(generateRandomInt(101), "name-" + i, generateRandomInt(5)));
        }

        printCurrentWardPatientList();
    }

    private int generateRandomInt(int bound) {
        Random ageGenerateRandom = new Random();
        return ageGenerateRandom.nextInt(bound);
    }

    private void printCurrentWardPatientList() {
        System.out.println("打印当前病区患者列表:");
        for (Patient patient : patientList) {

            System.out.println("Age:" + patient.getAge()
                    + ", Name:" + patient.getName()
                    + ", Status:" + patient.getStatus());
        }
        System.out.println("打印完成");
    }
}
