package dev.nurlan.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Procedure(procedureName = "PACK_MONEY_TRANSFERS.TEST_BR_CARD", outputParameterName = "P_OUTPUT")
    String methodTest(@Param("P_ID") Long id,
                      @Param("P_NAME") String name);
}
