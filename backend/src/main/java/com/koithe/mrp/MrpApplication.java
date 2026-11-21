package com.koithe.mrp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// things for SmokeTestRunner
// import com.koithe.mrp.repository.CongThucBomRepository;
// import com.koithe.mrp.repository.NguyenVatLieuRepository;
// import com.koithe.mrp.repository.ThanhPhamRepository;
// import lombok.RequiredArgsConstructor;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;
// import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class MrpApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrpApplication.class, args);
    }

	// Testing entity and repository reading data from postgresql
    // @Component
    // @RequiredArgsConstructor
    // static class SmokeTestRunner implements CommandLineRunner {

    //     private final ThanhPhamRepository thanhPhamRepo;
    //     private final NguyenVatLieuRepository nvlRepo;
    //     private final CongThucBomRepository bomRepo;

    //     @Override
    //     @Transactional(readOnly = true)
    //     public void run(String... args) {
    //         System.out.println("\n================ SMOKE TEST PHASE 2 ================");
    //         System.out.println("1. Danh sach Thanh pham:");
    //         thanhPhamRepo.findAll().forEach(tp ->
    //             System.out.println("   - " + tp.getMaTP() + ": " + tp.getTenTP() + " (Ton: " + tp.getSlTon() + " " + tp.getDvt() + ")")
    //         );

    //         System.out.println("2. Danh sach Nguyen vat lieu:");
    //         nvlRepo.findAll().forEach(nvl ->
    //             System.out.println("   - " + nvl.getMaNVL() + ": " + nvl.getTenNVL() + " (Ton: " + nvl.getSlTon() + " " + nvl.getDvt() + ")")
    //         );

    //         System.out.println("3. Dinh muc BOM mon TP01 (Tra sua Hoang Kim):");
    //         bomRepo.findByIdMaTP("TP01").forEach(bom ->
    //             System.out.println("   - " + bom.getNguyenVatLieu().getTenNVL() + ": " + bom.getSlCanDung() + " " + bom.getNguyenVatLieu().getDvt())
    //         );
    //         System.out.println("==================================================\n");
    //     }
    // }
}