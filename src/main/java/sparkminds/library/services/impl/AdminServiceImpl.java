package sparkminds.library.services.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sparkminds.library.entities.Admin;
import sparkminds.library.repository.AdminRepository;
import sparkminds.library.services.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin insert(Admin admin) {
       return adminRepository.save(admin);
    }

    @Override
    public Page<Admin> findAll(Integer number, Integer size) {
        Pageable pageable = PageRequest.of(number, size);
        return adminRepository.findAll(pageable);
    }

    @Override
    public Boolean delete(Admin Admin) {
        return null;
    }

    @Override
    public Admin update(Admin admin) {
        return null;
    }
}
