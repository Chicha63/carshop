package com.chicha.carshop.data.repos;

import com.chicha.carshop.data.enities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface GoodRepository extends JpaRepository<Good, Integer> {
    @Query(value = "EXEC GetFilteredGoodsEntityOnly " +
            "@PriceMin = :priceMin, " +
            "@PriceMax = :priceMax, " +
            "@ColorId = :colorId, " +
            "@CountryId = :countryId, " +
            "@AvailabilityId = :availabilityId, " +
            "@ManufacturerId = :manufacturerId, " +
            "@ModelName = :modelName, " +
            "@ModelYear = :modelYear, " +
            "@EngineVolumeMin = :engineVolumeMin, " +
            "@EngineVolumeMax = :engineVolumeMax, " +
            "@EngineName = :engineName, " +
            "@BodyTypeId = :bodyTypeId, " +
            "@EnginePlacementId = :enginePlacementId, " +
            "@DoorsCount = :doorsCount, " +
            "@PlacesCount = :placesCount", nativeQuery = true)
    List<Good> findFilteredGoods(
            @Param("priceMin") BigDecimal priceMin,
            @Param("priceMax") BigDecimal priceMax,
            @Param("colorId") Integer colorId,
            @Param("countryId") Integer countryId,
            @Param("availabilityId") Integer availabilityId,
            @Param("manufacturerId") Integer manufacturerId,
            @Param("modelName") String modelName,
            @Param("modelYear") Integer modelYear,
            @Param("engineVolumeMin") Float engineVolumeMin,
            @Param("engineVolumeMax") Float engineVolumeMax,
            @Param("engineName") String engineName,
            @Param("bodyTypeId") Integer bodyTypeId,
            @Param("enginePlacementId") Integer enginePlacementId,
            @Param("doorsCount") Integer doorsCount,
            @Param("placesCount") Integer placesCount
    );
}
