package com.ra.base_spring_boot.services.impl;
import com.ra.base_spring_boot.dto.resp.BusCompanyResponse;
import com.ra.base_spring_boot.dto.resp.PageResponse;
import com.ra.base_spring_boot.dto.resp.PopularBusCompanyResponse;
import com.ra.base_spring_boot.converter.BusCompanyConverter;
import com.ra.base_spring_boot.dto.req.BusCompanyRequest;
import com.ra.base_spring_boot.exception.HttpBadRequest;
import com.ra.base_spring_boot.exception.HttpConflict;
import com.ra.base_spring_boot.exception.HttpNotFound;
import com.ra.base_spring_boot.model.BusCompany;
import com.ra.base_spring_boot.repository.IBusCompanyRepository;
import com.ra.base_spring_boot.services.IBusCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class BusCompanyServiceImpl implements IBusCompanyService {
    @Autowired
    private  IBusCompanyRepository busCompanyRepository;
    @Autowired
    private BusCompanyConverter busCompanyConverter;

    public PageResponse<BusCompanyResponse> getCompanyNames(Pageable pageable) {
        Page<BusCompanyResponse> page = busCompanyRepository.findAll(pageable)
                .map(company -> BusCompanyResponse
                        .builder()
                        .id(company.getId())
                        .companyName(company.getCompanyName())
                        .build()
                );
        return PageResponse.<BusCompanyResponse>builder()
                .data(page.getContent())
                .currentPage(page.getNumber())
                .totalElements(page.getTotalElements())
                .build();
    }


    public BusCompanyResponse getCompanyById(int id) {
        BusCompany company = busCompanyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà xe với id:" + id));

        return BusCompanyResponse.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .image(company.getImage())
                .descriptions(company.getDescriptions())
                .build();
    }

    public List<PopularBusCompanyResponse> getPopularBusCompanyDTO(int limit) {
        return busCompanyRepository.getPopularBusCompany(PageRequest.of(0, limit));
    }


    @Override
    public Page<BusCompanyResponse> findBusCompanyByNameAndGetAllAndSort(String search,int page,int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")?
                Sort.by(sortBy).descending():
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page,size,sort);

         Page<BusCompany> companyPage =
                 (search==null || search.trim().isEmpty()) ?
                 busCompanyRepository.findAll(pageable)    :
                 busCompanyRepository.findByCompanyNameContainingIgnoreCase(search, pageable);


         return companyPage.map(c ->
                 BusCompanyResponse.builder()
                         .id(c.getId())
                         .companyName(c.getCompanyName())
                         .image(c.getImage())
                         .descriptions(c.getDescriptions())
                         .build()
           );

    }

    @Override
    public BusCompany createBusCompany(BusCompanyRequest busCompanyRequest) {
        if(busCompanyRequest.getCompanyName()==null || busCompanyRequest.getCompanyName().isBlank()){
            throw new HttpBadRequest("CompanyName cannot be empty");
        }

        if(busCompanyRepository.existsByCompanyName(busCompanyRequest.getCompanyName())){
            throw new HttpConflict("CompanyName already exists");
        }
           return busCompanyRepository.save(busCompanyConverter.toBusCompany(busCompanyRequest));
    }

    @Override
    public BusCompany findBusCompanyById(int id) {
        return busCompanyRepository.findBusCompanyById(id);
    }

    @Override
    public BusCompany updateBusCompany(int id,BusCompanyRequest busCompanyRequest) {
      if(busCompanyRepository.existsByCompanyNameAndIdNot(busCompanyRequest.getCompanyName(),id)){
          throw new HttpBadRequest("CompanyName already exists");
      }
      try{
          BusCompany existingCompany = busCompanyRepository.findBusCompanyById(id);
          existingCompany.setCompanyName(busCompanyRequest.getCompanyName());
          existingCompany.setImage(busCompanyRequest.getCompanyImage());
          existingCompany.setDescriptions(busCompanyRequest.getDescription());
          return busCompanyRepository.save(existingCompany);
      }catch (HttpNotFound httpNotFound){
          throw new HttpNotFound("CompanyName not found");

     }

    }
    @Override
    public void deleteBusCompany(int id) {
        try{
            BusCompany existingCompany = busCompanyRepository.findBusCompanyById(id);
            busCompanyRepository.delete(existingCompany);
        }catch (HttpNotFound httpNotFound){
            throw new HttpNotFound("CompanyName not found");
        }
    }

}







