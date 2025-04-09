package dao;

import entity.Appointment;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;


//Implementation of IHospitalService interface with database interactions.

public class IHospitalServiceImpl implements IHospitalService {

    private Connection conn;

    public IHospitalServiceImpl(String propertyFile) {
        try {
            this.conn = DBConnection.getConnection(propertyFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database connection from: " + propertyFile, e);
        }
    }

    public IHospitalServiceImpl() {
        this.conn = DBConnection.getConnection();        
    }
    
    
    //Retrieves an appointment based on the given appointment ID
    @Override
    public Appointment getAppointmentById(int appointmentId) {
        Appointment appt = null;
        String query = "SELECT * FROM appointment WHERE appointmentId = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, appointmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                appt = extractAppointment(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appt;
    }
    
    
   //Retrieves all appointments for a given patient ID
    @Override
    public List<Appointment> getAppointmentsForPatient(int patientId) {
        List<Appointment> list = new ArrayList<>();
        String query = "SELECT * FROM appointment WHERE patientId = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, patientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //Retrieves all appointments for a given doctor ID
    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) {
        List<Appointment> list = new ArrayList<>();
        String query = "SELECT * FROM appointment WHERE doctorId = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractAppointment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    //Schedules a new appointment by inserting it into the database
    @Override
    public boolean scheduleAppointment(Appointment appointment) {
        String query = "INSERT INTO appointment(patientId, doctorId, appointmentDate, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDate()));
            ps.setString(4, appointment.getDescription());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
   //Updates an existing appointment based on its ID
    @Override
    public boolean updateAppointment(Appointment appointment) {
    	String query = "UPDATE appointment SET patientId=?, doctorId=?, appointmentDate=?, description=? WHERE appointmentId=?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentDate()));
            ps.setString(4, appointment.getDescription());
            ps.setInt(5, appointment.getAppointmentId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    //Cancels an appointment by deleting it from the database
    @Override
    public boolean cancelAppointment(int appointmentId) {
        String query = "DELETE FROM appointment WHERE appointmentId = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, appointmentId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    //Utility method to extract Appointment object from ResultSet.
     
    private Appointment extractAppointment(ResultSet rs) throws SQLException {
        return new Appointment(
                rs.getInt("appointmentId"),
                rs.getInt("patientId"),
                rs.getInt("doctorId"),
                rs.getTimestamp("appointmentdate").toLocalDateTime(),
                rs.getString("description")
        );
    }
}
