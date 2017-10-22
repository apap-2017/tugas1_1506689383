package com.example.tugas1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.service.KeluargaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class KeluargaController {
	@Autowired
	KeluargaService keluargaDAO;

	@RequestMapping("/keluarga")
	public String view(Model model, @RequestParam(value="nkk", required= true) String nkk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		
		if (keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("title","Lihat Keluarga");
			return "view-keluarga";
		} else {
			model.addAttribute("nkk", nkk);
			model.addAttribute("title","Not Found");
			model.addAttribute("error", "Keluarga Tidak Ditemukan");
			return "not-found";
		}
	}
	
	@RequestMapping("/keluarga/tambah")
	public String tambah(Model model) {
		model.addAttribute("title","Tambah Keluarga");
		return "tambah-keluarga";
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
	public String submitTambah(Model model, KeluargaModel keluarga) {
		keluargaDAO.tambahKeluarga(keluarga);
		
		String sukses = "Keluarga dengan NKK " + keluarga.getNomor_kk() + " berhasil ditambahkan";
		model.addAttribute("sukses", sukses);
		model.addAttribute("title","Tambah Keluarga");
		return "sukses";
	}
	
	@RequestMapping("/keluarga/ubah/{nkk}")
	public String update(Model model, @PathVariable(value = "nkk") String nkk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		if (keluarga != null) {
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("title","Ubah Keluarga");
			return "ubah-keluarga";
		} else {
			model.addAttribute("nkk", nkk);
			model.addAttribute("title","Not Found");
			model.addAttribute("error", "Keluarga Tidak Ditemukan");
			return "not-found";
		}
	}
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
	public String submitUbah(Model model, @PathVariable(value = "nkk") String nkk, KeluargaModel keluarga) {
		String sukses = "Keluarga dengan NKK " + keluarga.getNomor_kk() + " berhasil diubah";
		model.addAttribute("sukses", sukses);
		keluargaDAO.ubahKeluarga(keluarga);
		
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("title", "Ubah Keluarga");
		
		return "sukses";
	}
}
