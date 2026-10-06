package org.yourcompany.yourproject.compatibility_reservation;

import com.bloodbank.common.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLReservationRepository implements ReservationRepository {

    @Override
    public boolean save(int patientId, int unitId) {

        String query = """
                INSERT INTO reservations
                (patient_id, unit_id, reservation_date, status)
                VALUES (?, ?, ?, ?)
                """;

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)
        ) {

            pstmt.setInt(1, patientId);
            pstmt.setInt(2, unitId);
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));
            pstmt.setString(4, "RESERVED");

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cancel(int reservationId) {

        String query = """
                UPDATE reservations
                SET status = 'CANCELLED'
                WHERE reservation_id = ?
                """;

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)
        ) {

            pstmt.setInt(1, reservationId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ReservationRecord findById(int reservationId) {

        String query =
                "SELECT * FROM reservations WHERE reservation_id = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)
        ) {

            pstmt.setInt(1, reservationId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    return new ReservationRecord(
                            rs.getInt("reservation_id"),
                            rs.getInt("patient_id"),
                            rs.getInt("unit_id"),
                            rs.getDate("reservation_date").toLocalDate(),
                            rs.getString("status")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ReservationRecord> findAll() {

        List<ReservationRecord> reservations = new ArrayList<>();

        String query = "SELECT * FROM reservations";

        try (
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)
        ) {

            while (rs.next()) {

                ReservationRecord record =
                        new ReservationRecord(
                                rs.getInt("reservation_id"),
                                rs.getInt("patient_id"),
                                rs.getInt("unit_id"),
                                rs.getDate("reservation_date").toLocalDate(),
                                rs.getString("status")
                        );

                reservations.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
}