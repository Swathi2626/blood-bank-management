package org.yourcompany.yourproject.compatibility_reservation;

import com.bloodbank.common.DatabaseConnection;

import org.yourcompany.yourproject.inventory.BloodGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLPatientRepository implements PatientRepository {

    @Override
    public boolean save(Patient patient) {

        String query = """
                INSERT INTO patients
                (patient_name, blood_group, contact_number, hospital_name)
                VALUES (?, ?, ?, ?)
                """;

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {

            pstmt.setString(1, patient.getPatientName());
            pstmt.setString(2, patient.getBloodGroup().name());
            pstmt.setString(3, patient.getContactNumber());
            pstmt.setString(4, patient.getHospitalName());

            int rows = pstmt.executeUpdate();

            if (rows == 0) {
                return false;
            }

            // Get the AUTO_INCREMENT patient_id
            try (ResultSet rs = pstmt.getGeneratedKeys()) {

                if (rs.next()) {
                    patient.setPatientId(rs.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Patient findById(int patientId) {

        String query =
                "SELECT * FROM patients WHERE patient_id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)
        ) {

            pstmt.setInt(1, patientId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    return new Patient(
                            rs.getInt("patient_id"),
                            rs.getString("patient_name"),
                            BloodGroup.valueOf(rs.getString("blood_group")),
                            rs.getString("contact_number"),
                            rs.getString("hospital_name")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Patient> findAll() {

        List<Patient> patients = new ArrayList<>();

        String query = "SELECT * FROM patients";

        try (
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)
        ) {

            while (rs.next()) {

                Patient patient = new Patient(
                        rs.getInt("patient_id"),
                        rs.getString("patient_name"),
                        BloodGroup.valueOf(rs.getString("blood_group")),
                        rs.getString("contact_number"),
                        rs.getString("hospital_name")
                );

                patients.add(patient);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    @Override
    public boolean update(Patient patient) {

        String query = """
                UPDATE patients
                SET patient_name = ?,
                    blood_group = ?,
                    contact_number = ?,
                    hospital_name = ?
                WHERE patient_id = ?
                """;

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)
        ) {

            pstmt.setString(1, patient.getPatientName());
            pstmt.setString(2, patient.getBloodGroup().name());
            pstmt.setString(3, patient.getContactNumber());
            pstmt.setString(4, patient.getHospitalName());
            pstmt.setInt(5, patient.getPatientId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int patientId) {

        String query =
                "DELETE FROM patients WHERE patient_id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)
        ) {

            pstmt.setInt(1, patientId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}