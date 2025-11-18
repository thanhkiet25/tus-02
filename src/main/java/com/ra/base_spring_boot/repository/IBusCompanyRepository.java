package com.ra.base_spring_boot.repository;

import com.ra.base_spring_boot.dto.resp.PopularBusCompanyDTO;
import com.ra.base_spring_boot.model.BusCompany;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBusCompanyRepository extends JpaRepository<BusCompany,Integer> {
    @Query("""
select new com.ra.base_spring_boot.dto.resp.PopularBusCompanyDTO(
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
    List<PopularBusCompanyDTO> getPopularBusCompany(Pageable pageable);

}
