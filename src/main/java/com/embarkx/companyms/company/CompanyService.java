package com.embarkx.companyms.company;

import com.embarkx.companyms.company.clients.ReviewClient;
import com.embarkx.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyService(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    public List<Company> findAll(){
        return companyRepository.findAll();
    }

    public void createCompany(Company company){
        companyRepository.save(company);
    }

    public Company getCompanyById(Long id){
        return companyRepository.findById(id).orElseThrow(()-> new RuntimeException("Company not found!"));
    }

    public boolean deleteCompanyById(Long id){
        if(!companyRepository.existsById(id)){return false;}
        companyRepository.deleteById(id);
        return true;
    }

    public Company updateCompany(Long id, Company updatedCompany) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found!"));

        // update fields (example – adjust as per your Company fields)
        existingCompany.setName(updatedCompany.getName());
        existingCompany.setDescription(updatedCompany.getDescription());

        return companyRepository.save(existingCompany);
    }

    public void updateCompanyRating(ReviewMessage reviewMessage){
        System.out.println(reviewMessage.getDescription());
        Company company =  companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(()-> new NotFoundException("Company not found"+ reviewMessage.getCompanyId()));
        double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }


}
