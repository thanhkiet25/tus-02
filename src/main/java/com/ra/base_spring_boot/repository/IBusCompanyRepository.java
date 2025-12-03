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


    @Query(value = """
      SELECT
      bc.id as company_id,
      bc.company_name as company_name,
      sum(case when br.rating >= 3 then 1 else 0 end) as positive_review,
      sum(case when br.rating <3 then 1 else 0 end) as negative_review
      from bus_companies bc
      join buses b on bc.id = b.company_id
      join bus_reviews br on br.bus_id = b.id
      group by bc.id,bc.company_name
    """,nativeQuery = true)
    Page<Object[]> getStatisticBusCompany(Pageable pageable);

    BusCompany findByCompanyName(String busCompanyName);

}
