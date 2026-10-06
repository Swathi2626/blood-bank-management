package org.yourcompany.yourproject.hospital;

import com.bloodbank.common.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcHospitalRequestRepository
        implements HospitalRequestRepository {

    @Override
    public void save(HospitalRequest request) {

        String sql =
                "INSERT INTO hospital_requests " +
                "(hospital_name, blood_group, component, " +
                "units_requested, urgency_level, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    request.getHospitalName()
            );

            statement.setString(
                    2,
                    request.getBloodGroup()
            );

            statement.setString(
                    3,
                    request.getComponent()
            );

            statement.setInt(
                    4,
                    request.getUnitsRequired()
            );

            statement.setString(
                    5,
                    request.getUrgency()
            );

            statement.setString(
                    6,
                    request.getStatus()
            );

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<HospitalRequest> findAll() {

        List<HospitalRequest> requests =
                new ArrayList<>();

        String sql =
                "SELECT * FROM hospital_requests";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             Statement statement =
                     connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(sql)) {

            while (resultSet.next()) {

                HospitalRequest request =
                        createRequestFromResultSet(resultSet);

                requests.add(request);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return requests;
    }


    @Override
    public HospitalRequest findById(int requestId) {

        String sql =
                "SELECT * FROM hospital_requests " +
                "WHERE request_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, requestId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (resultSet.next()) {
                    return createRequestFromResultSet(
                            resultSet
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void update(HospitalRequest request) {

        String sql =
                "UPDATE hospital_requests " +
                "SET hospital_name = ?, " +
                "blood_group = ?, " +
                "component = ?, " +
                "units_requested = ?, " +
                "urgency_level = ?, " +
                "status = ? " +
                "WHERE request_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(
                    1,
                    request.getHospitalName()
            );

            statement.setString(
                    2,
                    request.getBloodGroup()
            );

            statement.setString(
                    3,
                    request.getComponent()
            );

            statement.setInt(
                    4,
                    request.getUnitsRequired()
            );

            statement.setString(
                    5,
                    request.getUrgency()
            );

            statement.setString(
                    6,
                    request.getStatus()
            );

            statement.setInt(
                    7,
                    request.getRequestId()
            );

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(int requestId) {

        String sql =
                "DELETE FROM hospital_requests " +
                "WHERE request_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, requestId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private HospitalRequest createRequestFromResultSet(
            ResultSet resultSet) throws SQLException {

        HospitalRequest request =
                new HospitalRequest(
                        resultSet.getInt("request_id"),
                        resultSet.getString("hospital_name"),
                        resultSet.getString("blood_group"),
                        resultSet.getString("component"),
                        resultSet.getInt("units_requested"),
                        resultSet.getString("urgency_level")
                );

        request.setStatus(
                resultSet.getString("status")
        );

        return request;
    }
}