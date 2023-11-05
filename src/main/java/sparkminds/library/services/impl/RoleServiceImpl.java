package sparkminds.library.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import sparkminds.library.entities.RoleUser;
import sparkminds.library.repository.RoleRepository;
import sparkminds.library.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleUser insert() {
        return null;
    }

    @Override
    public Page<RoleUser> findAll(Integer number, Integer size) {
        return null;
    }

    @Override
    public Boolean delete(RoleUser roleUser) {
        return null;
    }

    @Override
    public RoleUser update(RoleUser roleUser) {
        return null;
    }
}
