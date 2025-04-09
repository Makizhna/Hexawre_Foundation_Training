package main;

import dao.IHospitalServiceImpl;
import entity.Appointment;
import exception.PatientNumberNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class MainModule {

    public static void main(String[] args) {
        IHospitalServiceImpl service = new IHospitalServiceImpl("src/db.properties");
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n----- Hospital Appointment System -----");
            System.out.println("1. Get appointment by ID");
            System.out.println("2. Get all appointments for a patient");
            System.out.println("3. Get all appointments for a doctor");
            System.out.println("4. Schedule an appointment");
            System.out.println("5. Update an appointment");
            System.out.println("6. Cancel an appointment");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Appointment ID: ");
                        int apptId = sc.nextInt();
                        Appointment appt = service.getAppointmentById(apptId);
                        if (appt != null) {
                            System.out.println(appt);
                        } else {
                            System.out.println("Appointment not found.");
                        }
                        break;

                    case 2:
                        System.out.print("Enter Patient ID: ");
                        int patientId = sc.nextInt();
                        List<Appointment> patientAppointments = service.getAppointmentsForPatient(patientId);
                        if (patientAppointments.isEmpty()) {
                            throw new PatientNumberNotFoundException("No appointments found for patient ID: " + patientId);
                        }
                        patientAppointments.forEach(System.out::println);
                        break;

                    case 3:
                        System.out.print("Enter Doctor ID: ");
                        int doctorId = sc.nextInt();
                        List<Appointment> doctorAppointments = service.getAppointmentsForDoctor(doctorId);
                        doctorAppointments.forEach(System.out::println);
                        break;

                    case 4:
                        System.out.print("Enter Patient ID: ");
                        int newPatientId = sc.nextInt();
                        System.out.print("Enter Doctor ID: ");
                        int newDoctorId = sc.nextInt();
                        sc.nextLine(); // consume leftover newline
                        System.out.print("Enter Appointment DateTime (YYYY-MM-DDTHH:MM:SS): ");
                        String dateTimeStr = sc.nextLine();
                        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
                        System.out.print("Enter Description: ");
                        String desc = sc.nextLine();
                        Appointment newAppt = new Appointment(0, newPatientId, newDoctorId, dateTime, desc);
                        if (service.scheduleAppointment(newAppt)) {
                            System.out.println("Appointment scheduled successfully.");
                        } else {
                            System.out.println("Failed to schedule appointment.");
                        }
                        break;

                    case 5:
                        System.out.print("Enter Appointment ID to update: ");
                        int updateId = sc.nextInt();
                        System.out.print("Enter Patient ID: ");
                        int updPatientId = sc.nextInt();
                        System.out.print("Enter Doctor ID: ");
                        int updDoctorId = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter new Appointment DateTime (format: YYYY-MM-DDTHH:MM:SS): ");
                        String updDateTimeStr = sc.nextLine();
                        LocalDateTime updDateTime = LocalDateTime.parse(updDateTimeStr);
                        System.out.print("Enter new Description: ");
                        String updDesc = sc.nextLine();
                        Appointment updAppt = new Appointment(updateId, updPatientId, updDoctorId, updDateTime, updDesc);
                        if (service.updateAppointment(updAppt)) {
                            System.out.println("Appointment updated.");
                        } else {
                            System.out.println("Update failed.");
                        }
                        break;

                    case 6:
                        System.out.print("Enter Appointment ID to cancel: ");
                        int delId = sc.nextInt();
                        if (service.cancelAppointment(delId)) {
                            System.out.println("Appointment canceled.");
                        } else {
                            System.out.println("Cancellation failed.");
                        }
                        break;

                    case 0:
                        System.out.println("Exiting... Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }

            } catch (PatientNumberNotFoundException e) {
                System.out.println("❗ Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("❗ General Error: " + e.getMessage());
            }

        } while (choice != 0);

        sc.close();
    }
}
