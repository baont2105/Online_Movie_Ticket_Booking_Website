package com.poly.demo.service;

import com.poly.demo.entity.Branch;
import com.poly.demo.entity.Movie;
import com.poly.demo.repository.BranchRepository;
import com.poly.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }
    

    public Optional<Branch> getBranchById(Long id) {
        return branchRepository.findById(id);
    }

    public Branch addBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    // Xóa chi nhánh theo ID
    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }
}
