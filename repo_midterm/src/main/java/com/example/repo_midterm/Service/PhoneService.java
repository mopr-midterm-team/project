    package com.example.repo_midterm.Service;

    import com.example.repo_midterm.Entity.Phone;
    import com.example.repo_midterm.Repository.CategoryRepository;
    import com.example.repo_midterm.Repository.PhoneRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;

    @Service
    public class PhoneService {

        @Autowired
        private PhoneRepository phoneRepository;
        @Autowired
        private CategoryRepository categoryRepository;


        public List<Phone> getPhonesByCategory(Long categoryId) {
            return phoneRepository.findByCategory(categoryRepository.findById(categoryId).get());
        }


    }
