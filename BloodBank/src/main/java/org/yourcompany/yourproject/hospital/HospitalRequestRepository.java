package org.yourcompany.yourproject.hospital;

import java.util.List;

public interface HospitalRequestRepository {

    void save(HospitalRequest request);

    List<HospitalRequest> findAll();
    List<HospitalRequest> findByHospitalName(String hospitalName);

    HospitalRequest findById(int requestId);

    void update(HospitalRequest request);

    void delete(int requestId);
}
