package ru.spiso4nik.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spiso4nik.accountservice.models.orderList.GlobalSpisokModel;
import ru.spiso4nik.accountservice.models.orderList.GoodsModel;
import ru.spiso4nik.accountservice.models.orderList.RoleOfStatus;

import java.util.List;
import java.util.Optional;

public interface GoodsRepository extends JpaRepository<GoodsModel, Long> {

    Optional<GoodsModel> findByName(String name);

    List<GoodsModel> findByRoleOfStatus(RoleOfStatus role);

    List<GoodsModel> findAllByGlobalSpisokModel_Id(Long id);

    List<GoodsModel> findAllByGlobalSpisokModel_IdAndRoleOfStatus(Long id, RoleOfStatus role);

    Optional<GlobalSpisokModel> findByGlobalSpisokModelId(Long id);

}
