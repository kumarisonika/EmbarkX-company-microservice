package com.embarkx.companyms.company;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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


}
