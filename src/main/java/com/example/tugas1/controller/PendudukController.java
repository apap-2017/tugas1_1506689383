package com.example.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.service.DaerahService;
import com.example.tugas1.service.PendudukService;

@Controller
public class PendudukController {
	@Autowired
	PendudukService pendudukDAO;
	@Autowired
	DaerahService daerahDAO;

	@RequestMapping("/penduduk")
	public String view(Model model, @RequestParam(value = "nik", required = true) String nik) {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		String tes = (String) model.asMap().get("sukses");

		if (tes != null) {
			return "sukses";
		}
		if (penduduk != null) {
			String lahir = penduduk.getTanggal_lahir();

			model.addAttribute("lahir", lahir);
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("title", "Lihat Penduduk");
			return "view-penduduk";
		} else {
			model.addAttribute("nik", nik);
			model.addAttribute("title", "Not Found");
			model.addAttribute("error", "Penduduk tidak ditemukan");
			return "not-found";
		}
	}

	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("title", "SI Kependudukan");
		return "view";
	}

	@RequestMapping("/penduduk/tambah")
	public String tambah(Model model) {
		model.addAttribute("title", "Tambah Penduduk");
		return "tambah-penduduk";
	}

	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
	public String submitTambah(Model model, PendudukModel penduduk) {
		if (pendudukDAO.tambahPenduduk(penduduk)) {
			String sukses = "Penduduk dengan NIK " + penduduk.getNik() + " berhasil ditambahkan";
			model.addAttribute("sukses", sukses);
			model.addAttribute("title", "Tambah Penduduk");
			return "sukses";
		} else {
			String error = "Penduduk gagal ditambahkan. Silahkan isi semua form.";
			model.addAttribute("error", error);
			model.addAttribute("title", "Tambah Penduduk");
			return "tambah-penduduk";
		}

	}

	@RequestMapping("/penduduk/ubah/{nik}")
	public String update(Model model, @PathVariable(value = "nik") String nik) {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		if (penduduk != null) {
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("title", "Ubah Penduduk");
			return "ubah-penduduk";
		} else {
			model.addAttribute("nik", nik);
			model.addAttribute("title", "Not Found");
			model.addAttribute("error", "Penduduk Tidak Ditemukan");
			return "not-found";
		}
	}

	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
	public String submitUbah(Model model, @PathVariable(value = "nik") String nik, PendudukModel penduduk) {
		if (pendudukDAO.ubahPenduduk(penduduk)) {
			String sukses = "Penduduk dengan NIK " + penduduk.getNik() + " berhasil diubah";
			model.addAttribute("sukses", sukses);

			model.addAttribute("penduduk", penduduk);
			model.addAttribute("title", "Ubah Penduduk");

			return "sukses";
		}
		else {
			String error = "Penduduk gagal diubah. Silahkan refresh halaman ini.";
			model.addAttribute("error", error);
			model.addAttribute("nik", nik);
			model.addAttribute("title", "Tambah Penduduk");
			return "ubah-penduduk";
		}
	}

	@RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
	public ModelAndView nonaktif(RedirectAttributes redir, PendudukModel penduduk) {
		ModelAndView modelAndView = new ModelAndView();
		pendudukDAO.nonaktifPenduduk(penduduk);
		String sukses = "Penduduk dengan NIK " + penduduk.getNik() + " sudah tidak aktif";
		modelAndView.setViewName("redirect:/penduduk?nik=" + penduduk.getNik());
		redir.addFlashAttribute("sukses", sukses);

		return modelAndView;
	}

	@RequestMapping("/penduduk/cari")
	public String cari(Model model, @RequestParam(value = "kt", required = false) String kt,
			@RequestParam(value = "kc", required = false) String kc,
			@RequestParam(value = "kl", required = false) String kl) {
		List<KotaModel> kotas = daerahDAO.selectAllKota();
		String akhir = "cari-1";

		if (kt != null) {
			List<KecamatanModel> kecamatans = daerahDAO.selectKecamatan(kt);
			KotaModel kota = daerahDAO.getKota(kt);

			model.addAttribute("kota", kota);
			model.addAttribute("kt", kt);
			model.addAttribute("kecamatans", kecamatans);

			akhir = "cari-2";

			if (kc != null) {
				List<KelurahanModel> kelurahans = daerahDAO.selectKelurahan(kc);
				KecamatanModel kecamatan = daerahDAO.getKecamatan(kc);

				model.addAttribute("kecamatan", kecamatan);
				model.addAttribute("kc", kc);
				model.addAttribute("kelurahans", kelurahans);

				akhir = "cari-3";

				if (kl != null) {
					KelurahanModel kelurahan = daerahDAO.getKelurahan(kl);

					String judul = "Lihat Data Penduduk di " + kota.getNama_kota() + ", Kecamatan "
							+ kecamatan.getNama_kecamatan() + ", Kelurahan " + kelurahan.getNama_kelurahan();
					List<PendudukModel> penduduks = pendudukDAO.pendudukKelurahan(kl);
					model.addAttribute("judul", judul);
					model.addAttribute("penduduks", penduduks);
					model.addAttribute("kl", kl);

					akhir = "hasil-cari";
				}
			}

		}

		model.addAttribute("kotas", kotas);
		model.addAttribute("title", "Cari Penduduk");
		return akhir;
	}

}
