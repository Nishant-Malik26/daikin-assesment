package daikin.building_climate_control.repository;

import daikin.building_climate_control.entity.CommonRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonRoomRepository extends JpaRepository<CommonRoom, Long> {
}
