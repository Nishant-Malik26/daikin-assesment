package daikin.building_climate_control;

import daikin.building_climate_control.config.TemperatureConfig;
import daikin.building_climate_control.entity.Apartment;
import daikin.building_climate_control.entity.Building;
import daikin.building_climate_control.entity.CommonRoom;
import daikin.building_climate_control.repository.BuildingRepository;
import daikin.building_climate_control.util.RoomType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BuildingClimateControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuildingClimateControlApplication.class, args);
	}

	@Bean
	CommandLineRunner run(BuildingRepository buildingRepository, TemperatureConfig temperatureConfig) {
		return args -> {
			if(buildingRepository.count() > 0){
				return;
			}
			Building building = new Building();
			building.setRequestedTemperature(22f);

			Apartment apartment101 = new Apartment();
			apartment101.setUnitNumber("101");
			apartment101.setOwnersName("Chester Bennington");

			Apartment apartment102 = new Apartment();
			apartment102.setUnitNumber("102");
			apartment102.setOwnersName("Jennifer Aniston");

			building.addApartment(apartment101);
			building.addApartment(apartment102);

			CommonRoom gym = new CommonRoom();
			gym.setRoomType(RoomType.GYM);

			CommonRoom library = new CommonRoom();
			library.setRoomType(RoomType.LIBRARY);

			building.addRoom(gym);
			building.addRoom(library);

			for (Apartment apartment : building.getApartments()) {
				apartment.updateMode(building.getRequestedTemperature(), temperatureConfig.getThreshold());
			}
			for (CommonRoom room : building.getRooms()) {
				room.updateMode(building.getRequestedTemperature(),temperatureConfig.getThreshold());
			}
			buildingRepository.save(building);
		};
	}

}
