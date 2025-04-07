package com.poly.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.demo.entity.Voucher;
import com.poly.demo.repository.VoucherRepository;

@Service
public class VoucherService {

	@Autowired
	private VoucherRepository voucherRepository;

	// Lấy tất cả voucher
	public List<Voucher> getAllVouchers() {
		return voucherRepository.findAll();
	}

	// Lấy tất cả voucher phân trang ()
	public Page<Voucher> getPageVouchers(Pageable pageable) {
		return voucherRepository.findAll(pageable); // Lấy danh sách vouchers với phân trang
	}

	// Tìm voucher theo ID
	public Optional<Voucher> getVoucherById(Integer voucherId) {
		return voucherRepository.findById(voucherId);
	}

	// Tìm voucher theo mã voucher
	public Optional<Voucher> getVoucherByCode(String code) {
		return voucherRepository.findByCode(code);
	}

	// Lưu hoặc cập nhật voucher
	public Voucher saveVoucher(Voucher voucher) {
		return voucherRepository.save(voucher);
	}

	// Xóa voucher theo ID
	public void deleteVoucher(Integer voucherId) {
		voucherRepository.deleteById(voucherId);
	}
}
