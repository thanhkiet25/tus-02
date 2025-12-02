package com.ra.base_spring_boot.repository;
import com.ra.base_spring_boot.dto.resp.PopularBusCompanyResponse;
import com.ra.base_spring_boot.model.BusCompany;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  IBusCompanyRepository extends JpaRepository<BusCompany,Integer> {
    @Query("""
select new com.ra.base_spring_boot.dto.resp.PopularBusCompanyResponse(
  bc.id,
  bc.companyName,
  count(t.id)
)
from Ticket t
join t.schedule sch
join sch.bus b
join b.busCompany bc
group by bc.id,bc.companyName
order by count(t.id) desc
""")
    List<PopularBusCompanyResponse> getPopularBusCompany(Pageable pageable);

    @Query("select count(b) > 0 from Bus b  where b.busCompany.id = :busId")
    boolean  hasBusCompany(@Param("busId") Integer busId);

    Page<BusCompany> findByCompanyNameContainingIgnoreCase(String search, Pageable pageable);

    boolean existsByCompanyName(String search);

    boolean existsByCompanyNameAndIdNot(String search,Integer id);

    BusCompany findBusCompanyById(Integer id);


}
